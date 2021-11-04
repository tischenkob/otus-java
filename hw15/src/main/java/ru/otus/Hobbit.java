package ru.otus;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hobbit {

    private static final Logger logger = LoggerFactory.getLogger(Hobbit.class);

    private final Object link;

    Hobbit(Object instance) {
        link = instance;
    }

    public static void main(String[] args) {
        Object link = new Object();

        var bilbo = new Hobbit(link);
        var theHobbit = adventureFor(bilbo);
        theHobbit.start();

        var frodo = new Hobbit(link);
        var theLordOfTheRings = adventureFor(frodo);
        theLordOfTheRings.start();
    }

    static Thread adventureFor(Hobbit hobbit) {
        return new Thread(hobbit::thereAndBackAgain);
    }

    @SneakyThrows
    void thereAndBackAgain() {
        for (int i = 1; i <= 10; i++) {
            step(i);
        }
        for (int i = 10; i >= 1; i--) {
            step(i);
        }
    }

    private void step(int i) throws InterruptedException {
        synchronized (link) {
            logger.info(String.valueOf(i));
            link.notifyAll();
            link.wait();
        }
    }
}
