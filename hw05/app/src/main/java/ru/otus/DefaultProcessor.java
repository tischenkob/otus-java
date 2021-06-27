package ru.otus;

import ru.otus.aop.Log;

public class DefaultProcessor implements Processor {


    @Log
    @Override
    public void process(Object instance) {
        System.out.println("Processing: " + instance);
    }


}
