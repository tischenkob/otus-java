package otus.tisch;

import java.lang.reflect.InvocationTargetException;

import bjunit.BJUnit;

public class Main {
    public static void main(String[] args)
            throws IllegalAccessException, InvocationTargetException, InstantiationException {
        var testRunner = new BJUnit(RandomCaseTest.class);
        testRunner.execute();
    }
}
