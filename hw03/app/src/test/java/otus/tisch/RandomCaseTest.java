package otus.tisch;

import bjunit.anno.*;

public class RandomCaseTest {
    private RandomCase randomCase;

    @Before
    public void setup() {
        System.out.println("Before!");
        String testString = "Hello world";
        randomCase = new RandomCase(testString);
    }

    @Test
    public void firstTest() {
        System.out.println("First test: " + randomCase.randomize());
    }

    @Test
    public void secondTest() {
        System.out.println("Second test: " + randomCase.randomize());
    }

    @After
    public void teardown() {
        System.out.println("After!");
    }
}
