package ru.otus.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

class DelegatingInvocationHandler implements InvocationHandler {

    private final Object instance;
    private final Map<Method, Collection<AnnotatedMethodHandler>> handlersMap = new HashMap<>();

    public DelegatingInvocationHandler(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        instance = clazz.getDeclaredConstructor().newInstance();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final Object[] finalArgs = (args == null) ? new Object[0] : args;
        var handlers= handlersMap.computeIfAbsent(method, this::collectHandlers);
        handlers.forEach(handler -> handler.handle(method, finalArgs));
        return method.invoke(instance, args);
    }

    private Collection<AnnotatedMethodHandler> collectHandlers(Method method) {
        List<AnnotatedMethodHandler> handlers = new ArrayList<>();
        try {
            var instanceMethod = instance.getClass().getMethod(method.getName(), method.getParameterTypes());
            for (var annotation : instanceMethod.getAnnotations()) {
                var annotationHandler = AnnotatedMethodHandlerFactory.getHandlerFor(annotation);
                annotationHandler.ifPresent(handlers::add);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return handlers;
    }
}
