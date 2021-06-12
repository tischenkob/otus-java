package ru.otus;

import ru.otus.aop.Log;

public class DifferentProcessor implements Processor, DifferentInterface{
    @Log
    @Override
    public void a() {
        System.out.println("Empty a");
    }

    @Override
    public void a(int a, int b) {
        System.out.println("A and B");
    }

    @Log
    @Override
    public void a(String s) {
        System.out.println("A String: " + s);
    }

    @Override
    public void process(Object instance) {
        System.out.println("Different processor");
    }
}
