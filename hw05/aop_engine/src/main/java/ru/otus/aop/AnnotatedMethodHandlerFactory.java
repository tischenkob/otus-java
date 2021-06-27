package ru.otus.aop;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnnotatedMethodHandlerFactory {

    private static final Map<Class<? extends Annotation>, AnnotatedMethodHandler> handlerMap = new HashMap<>();

    static {
        // Анонимный класс, чтобы не создавать отдельный файл. Если б было несколько,
        // тогда бы, конечно, все вынес по разным местам
        AnnotatedMethodHandler logHandler = (method, args) -> {
            String arguments = Stream.of(args).map(Object::toString).collect(Collectors.joining(", "));
            System.out.printf("==> Called method {%s} with arguments [%s]\n", method.getName(), arguments);
        };
        handlerMap.put(Log.class, logHandler);
    }

    public static Optional<AnnotatedMethodHandler> getHandlerFor(Annotation instance) {
        return Optional.ofNullable(handlerMap.get(instance.annotationType()));
    }

}
