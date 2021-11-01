package ru.otus.appcontainer;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import lombok.SneakyThrows;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?>... initialConfigClass) {
        Arrays.stream(initialConfigClass).forEach(this::processConfig);
    }

    public AppComponentsContainerImpl(String packageName) {
        Reflections configScanner = new Reflections(packageName,
                new SubTypesScanner(),
                new TypeAnnotationsScanner());
        var configClasses = configScanner.getTypesAnnotatedWith(
                AppComponentsContainerConfig.class);
        configClasses.forEach(this::processConfig);
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        final Predicate<Object> hasTargetClassOrInterface =
                c -> c.getClass().equals(componentClass) ||
                        Arrays.asList(c.getClass().getInterfaces()).contains(componentClass);
        final Object component = appComponents.stream()
                .filter(hasTargetClassOrInterface)
                .findFirst()
                .orElse(null);
        return componentClass.cast(component);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }

    private static boolean isAppComponent(Method m) {
        return m.isAnnotationPresent(AppComponent.class);
    }

    @SneakyThrows
    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        // You code here...
        Object configInstance = configClass.getConstructor().newInstance();
        List<Method> annotatedMethods = Arrays.stream(configClass.getDeclaredMethods())
                .filter(AppComponentsContainerImpl::isAppComponent)
                .sorted(comparingInt(this::getComponentOrder))
                .collect(toList());

        for (Method method : annotatedMethods) {
            Object component = buildComponent(configInstance, method);
            appComponents.add(component);
            appComponentsByName.put(method.getName(), component);
        }
    }

    @SneakyThrows
    private Object buildComponent(Object config, Method method) {
        final Object[] componentArguments = Arrays.stream(method.getParameters())
                .map(Parameter::getType)
                .map(this::getAppComponent)
                .toArray();
        return method.invoke(config, componentArguments);
    }

    private int getComponentOrder(Method m) {
        return m.getAnnotation(AppComponent.class).order();
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(
                    String.format("Given class is not in config %s", configClass.getName()));
        }
    }
}
