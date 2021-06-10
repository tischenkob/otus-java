package ru.otus.processor;

import ru.otus.model.Message;

public class TimedProcessor implements Processor {

    private final Time time;

    public TimedProcessor(Time instance) {
        time = instance;
    }

    @Override
    public Message process(Message message) {
        if (time.getSeconds() % 2 == 0) {
            throw new IllegalStateException("You cannot use this processor during an even second.");
        }
        return message;
    }
}

interface Time {

    int getSeconds();

}
