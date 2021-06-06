package ru.otus.processor;

import ru.otus.model.Message;

import java.time.LocalTime;

public class TimedProcessor implements Processor{
    @Override
    public Message process(Message message) {
        if (LocalTime.now().getSecond() % 2 == 0) {
            throw new IllegalStateException("You cannot use this processor during an even second.");
        }
        return message;
    }
}
