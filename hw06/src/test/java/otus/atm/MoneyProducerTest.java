package otus.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoneyProducerTest {

    MoneyProducer producer;
    Storage<Banknote> storage;

    @BeforeEach
    void setUp() {
        storage = new Storage<>(List.of(Banknote.ONE, Banknote.FIVE));
        producer = new MoneyProducer(storage);
    }

    @Test
    void get() {
        storage.put(Banknote.ONE, 4L);
        storage.put(Banknote.FIVE, 3L);

        var moneyCollection = producer.get(12L);
        assertTrue(moneyCollection.containsAll(List.of(
                new Money(Banknote.FIVE, 2L),
                new Money(Banknote.ONE, 2L)
        )));

        assertTrue(storage.has(Banknote.ONE, 2L));
        assertFalse(storage.has(Banknote.ONE, 3L));
        assertTrue(storage.has(Banknote.FIVE, 1L));
        assertFalse(storage.has(Banknote.FIVE, 2L));
    }
}
