package ru.otus.processor;

import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TimedProcessorTest {

    @Test
    void processTest() throws InterruptedException {
        var message = new Message.Builder(1L).build();
        var processor = new TimedProcessor();

        Runnable code = () -> {

            System.gc(); // Попытаться избежать STW

            // Попытаться дождаться начала четной секунды
            while ( //Thread.interrupted() || С этим, наверное, никогда завершения не дождешься
                    ((LocalTime.now().getSecond() % 2) != 0) ||
                    (LocalTime.now().getNano() > 5_000_000)) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // Здесь всё ещё может произойти прерывание или сборка мусора, но они, надеюсь, не превзойдут половину секунды
            // Да и внутри вызова метода между if и throw оно тоже может случиться
            processor.process(message);
        };

        assertThatThrownBy(code::run).isInstanceOf(IllegalStateException.class);
    }
}
