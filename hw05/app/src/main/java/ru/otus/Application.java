package ru.otus;

import ru.otus.aop.Engine;

import java.lang.reflect.InvocationTargetException;

public class Application {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Processor processor = Engine.createInstanceOf(DefaultProcessor.class);
        processor.process("Hello World!");

        DifferentInterface different = Engine.createInstanceOf(DifferentProcessor.class);
        different.a();
        different.a("otus");
        different.a(1, 3);

    }

}
