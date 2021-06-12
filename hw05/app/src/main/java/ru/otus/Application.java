package ru.otus;

import ru.otus.aop.Engine;

import java.lang.reflect.InvocationTargetException;

public class Application {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Processor processor = Engine.createInstanceOf(DefaultProcessor.class);
        processor.process("Hello World!");
    }

}
