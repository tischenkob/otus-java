package ru.otus;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hobbit {

    private static final Logger logger = LoggerFactory.getLogger(Hobbit.class);

    private final TurnHolder turnHolder;

    Hobbit(TurnHolder instance) {
        turnHolder = instance;
    }

    public static void main(String[] args) {
        TurnHolder holder = new TurnHolder();
        var bilbo = new Hobbit(holder);
        var frodo = new Hobbit(holder);
        holder.setWhoseTurnNext(frodo);

        var theHobbit = adventureFor(bilbo);
        var theLordOfTheRings = adventureFor(frodo);

        List<Thread> adventures = List.of(theHobbit, theLordOfTheRings);

        adventures.forEach(Thread::start);
        adventures.forEach(adventure -> {
            try{
                adventure.join();
            }catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        });
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
        synchronized (turnHolder) {
            logger.info("Lived happily ever after");
            turnHolder.setWhoseTurnNext(this);
            turnHolder.notifyAll();
        }
    }

    private void step(int i) throws InterruptedException {
        synchronized (turnHolder) {
            logger.info(String.valueOf(i));
            turnHolder.setWhoseTurnNext(this);
            turnHolder.notifyAll();
            while (turnHolder.whoseTurnNext == this){
                turnHolder.wait();
            }
        }
    }

    @Setter
    @Getter
    static class TurnHolder {
        private volatile Hobbit whoseTurnNext;
    }
}
