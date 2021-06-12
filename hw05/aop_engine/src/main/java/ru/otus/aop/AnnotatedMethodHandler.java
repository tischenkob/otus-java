package ru.otus.aop;

import java.lang.reflect.Method;

public interface AnnotatedMethodHandler {

    void handle(Method method, Object[] args);

}
