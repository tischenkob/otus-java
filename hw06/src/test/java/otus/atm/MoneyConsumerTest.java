package otus.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoneyConsumerTest {

    MoneyConsumer consumer;
    Storage<Banknote> storage;

    @BeforeEach
    void setUp() {
        storage = new Storage(List.of(Banknote.ONE));
        consumer = new MoneyConsumer(storage);
    }

    @Test
    void assertAccepts() {
        Long deposit = 5L;

        Money money = new Money(Banknote.ONE, deposit);
        consumer.accept(money);

        Long result = storage.popRequestedOrAll(Banknote.ONE, deposit);

        assertEquals(deposit, result);
    }

    @Test
    void assertDoesntAccept() {
        assertThrows(IllegalArgumentException.class,
                () -> consumer.accept(new Money(Banknote.FIVE, 3L))
        );
    }
}
