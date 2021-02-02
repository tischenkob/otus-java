package otus.tisch;

import java.util.Random;
import java.util.stream.Collectors;

public class RandomCase {
    private String str;
    private Random random = new Random();

    public RandomCase(String str) {
        this.str = str;
    }

    public String randomize() {
        return str.chars().mapToObj(c -> (char) c)
                .map(c -> coinToss() ? Character.toUpperCase(c) : Character.toLowerCase(c))
                .map(String::valueOf).collect(Collectors.joining(""));
    }

    private boolean coinToss() {
        return random.nextInt() % 2 == 0;
    }
}