package ru.otus.appcontainer;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public AppComponentsContainerImpl(Class<?>... configClasses) {
        processConfigsInOrder(configClasses);
    }

    public AppComponentsContainerImpl(String packageName) {
        Class<?>[] configClasses = findConfigurationClasses(packageName);
        processConfigsInOrder(configClasses);
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        Object component = findComponentAssignableFrom(componentClass);
        return componentClass.cast(component);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }

    @SneakyThrows
    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        Object configInstance = configClass.getConstructor().newInstance();
        List<Method> annotatedMethods = Arrays.stream(configClass.getDeclaredMethods())
                                              .filter(this::isAppComponent)
                                              .sorted(comparingInt(this::getComponentOrder))
                                              .collect(toList());

        for (Method method : annotatedMethods) {
            Object component = buildComponent(configInstance, method);
            appComponents.add(component);
            appComponentsByName.put(method.getName(), component);
        }
    }

    private void processConfigsInOrder(Class<?>[] configClasses) {
        Class<?>[] sortedConfigs = Arrays.copyOf(configClasses, configClasses.length);
        Arrays.sort(sortedConfigs, comparingInt(this::getConfigOrder));
        for (Class<?> config : sortedConfigs) {
            processConfig(config);
        }
    }

    private Class<?>[] findConfigurationClasses(String packageName) {
        Reflections configScanner = new Reflections(packageName,
                                                    new SubTypesScanner(),
                                                    new TypeAnnotationsScanner());
        return configScanner
                .getTypesAnnotatedWith(AppComponentsContainerConfig.class)
                .toArray(new Class[]{});
    }

    private <C> Object findComponentAssignableFrom(Class<C> componentClass) {
        for (Object component : appComponents) {
            if (componentClass.isAssignableFrom(component.getClass())) {
                return component;
            }
        }
        return null;
    }

    @SneakyThrows
    private Object buildComponent(Object config, Method method) {
        Parameter[] parameters = method.getParameters();
        var arguments = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Object appComponent = getAppComponent(parameters[i].getType());
            if (appComponent == null) {
                throw new RuntimeException("Component not found for type " +
                                           parameters[i].getType());
            }
            arguments[i] = appComponent;
        }
        return method.invoke(config, arguments);
    }

    // Эти методы не static, чтобы ссылка на них не требовала длинного имени класса
    private int getComponentOrder(Method method) {
        return method.getAnnotation(AppComponent.class).order();
    }

    private int getConfigOrder(Class<?> config) {
        return config.getAnnotation(AppComponentsContainerConfig.class).order();
    }

    private boolean isAppComponent(Method m) {
        return m.isAnnotationPresent(AppComponent.class);
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException("Given class is not in config " +
                                               configClass.getName());
        }
    }
}
