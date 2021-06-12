package ru.otus.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class DelegatingInvocationHandler implements InvocationHandler {

    private final Class<?> clazz;

    public DelegatingInvocationHandler(Class<?> instance) {
        clazz = instance;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (args == null) args = new Object[0];
        Object[] finalArgs = args;

        final var instance = clazz.getDeclaredConstructor().newInstance();
        var instanceMethod = clazz.getMethod(method.getName(), method.getParameterTypes());
        for (var annotation : instanceMethod.getAnnotations()) {
            var annotationHandler = AnnotatedMethodHandlerFactory.getHandlerFor(annotation);
            annotationHandler.ifPresent(methodHandler -> methodHandler.handle(method, finalArgs));
        }
        return instanceMethod.invoke(instance, args);
    }
}
