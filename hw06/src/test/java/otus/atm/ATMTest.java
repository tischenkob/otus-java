package otus.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ATMTest {
    ATM atm;
    MoneyConsumer consumer;
    MoneyProducer producer;
    Storage<Banknote> storage;

    Collection<Money> moneyCollection = List.of(
            new Money(Banknote.FIVE, 5L),
            new Money(Banknote.ONE, 3L),
            new Money(Banknote.TEN, 1L)
    );


    @BeforeEach
    void setUp() {
        storage = new Storage<>(List.of(Banknote.ONE, Banknote.FIVE, Banknote.TEN, Banknote.FIFTY));
        consumer = new MoneyConsumer(storage);
        producer = new MoneyProducer(storage);
        atm = new ATM(consumer, producer);
    }

    @Test
    void store() {
        atm.store(moneyCollection);
        assertTrue(storage.has(Banknote.ONE, 3L));
    }

    @Test
    void request() {
        atm.store(moneyCollection);

        var received = atm.request(17L);

        assertTrue(
                received.containsAll(List.of(
                        new Money(Banknote.TEN, 1L),
                        new Money(Banknote.FIVE, 1L),
                        new Money(Banknote.ONE, 2L)
                ))
        );
    }
}
