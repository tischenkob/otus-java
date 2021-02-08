package bjunit;

import bjunit.anno.After;
import bjunit.anno.Before;
import bjunit.anno.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BJUnit {

    private final List<Method> beforeMethods;
    private final List<Method> testMethods;
    private final List<Method> afterMethods;

    private final Class<?> testClass;

    private final String exceptionMessageTemplate;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public BJUnit(Class<?> testClass) {
        this.testClass = testClass;
        beforeMethods = collectMethodsAnnotatedWith(Before.class);
        testMethods = collectMethodsAnnotatedWith(Test.class);
        afterMethods = collectMethodsAnnotatedWith(After.class);

    }

    {
        exceptionMessageTemplate = "%s method could not get invoked because " + "of %s%n";
    }

    public static void main(String[] args)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        String className = args[0];
        Class<?> testClass = Class.forName(className);
        BJUnit bjUnit = new BJUnit(testClass);
        bjUnit.execute();
    }

    public void execute() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?> constructor = testClass.getDeclaredConstructors()[0];
        int passed = 0;
        int failed = 0;
        for (Method test : testMethods) {
            String testName = test.getAnnotation(Test.class).value();
            testName = testName.isBlank() ? test.getName() : testName;
            var instance = constructor.newInstance();
            try {
                beforeMethods.forEach(m -> {
                    try {
                        m.invoke(instance);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        System.err.printf("@Before" + exceptionMessageTemplate, m.getName(), e.getMessage());
                    }
                });
                test.invoke(instance);
                passed += 1;
                System.out.println(testName + ANSI_GREEN + ": PASSED" + ANSI_RESET);
            } catch (Exception e) {
                failed += 1;
                System.err.printf("@Test " + exceptionMessageTemplate, testName, e.getMessage());
            } finally {
                afterMethods.forEach(m -> {
                    try {
                        m.invoke(instance);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        System.err.printf("@After" + exceptionMessageTemplate, m.getName(), e.getMessage());
                    }
                });
            }
        }
        System.out.print(ANSI_GREEN + "PASSED: " + passed + ANSI_RESET + "\n" + ANSI_RED + "FAILED: " + failed
                + ANSI_RESET + "\n");
    }

    private List<Method> collectMethodsAnnotatedWith(Class<? extends Annotation> annotationClass) {
        return Arrays.stream(testClass.getDeclaredMethods())
                .filter(method -> method.getAnnotation(annotationClass) != null).collect(Collectors.toList());
    }
}
