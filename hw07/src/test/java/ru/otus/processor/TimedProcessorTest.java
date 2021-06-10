package ru.otus.processor;

import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TimedProcessorTest {

    private final Message message = new Message.Builder(1L).build();

    @Test
    void supplyEvenSeconds_shouldThrow() throws InterruptedException {
        var timeMock = mock(Time.class);
        when(timeMock.getSeconds()).thenReturn(2);

        var processor = new TimedProcessor(timeMock);
        Runnable code = () -> processor.process(message);

        assertThatThrownBy(code::run).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void supplyOddSeconds_shouldNotThrow() throws InterruptedException {
        var timeMock = mock(Time.class);
        when(timeMock.getSeconds()).thenReturn(1);

        var processor = new TimedProcessor(timeMock);
        Runnable code = () -> processor.process(message);

        assertThatNoException().isThrownBy(code::run);
    }
}
