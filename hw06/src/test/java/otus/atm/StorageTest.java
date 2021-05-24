package otus.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    Storage<Banknote> storage;

    @BeforeEach
    void setUp() {
        storage = new Storage(List.of(Banknote.ONE, Banknote.FIVE, Banknote.TEN));
    }

    @Test
    void putUnsupportedAssertThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> storage.put(Banknote.FIFTY, 5L)
        );
    }

    @Test
    void putWrongAmountAssertThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> storage.put(Banknote.ONE, -1L));
    }

    @Test
    void putGoodValueAssertDoesntThrow() {
        assertDoesNotThrow(
                () -> {
                    storage.put(Banknote.ONE, 3L);
                    storage.put(Banknote.FIVE, 1000L);
                    storage.put(Banknote.TEN, 0L);
                }
        );
    }

    @Test
    void popEmptyAssertReturnsZero() {
        var actual = storage.popRequestedOrAll(Banknote.ONE, 5L);
        Long expected = 0L;

        assertEquals(expected, actual);
    }

    @Test
    void popNegativeAssertThrows() {
        storage.put(Banknote.TEN, 10L);
        assertThrows(IllegalArgumentException.class,
                () -> storage.popRequestedOrAll(Banknote.TEN, -1L));
    }

    @Test
    void popUnsupportedAssertThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> storage.popRequestedOrAll(Banknote.FIFTY, 5L));
    }

    @Test
    void putAndPop() {

        storage.put(Banknote.TEN, 5L);
        storage.put(Banknote.ONE, 7L);

        var fourTens = storage.popRequestedOrAll(Banknote.TEN, 4L);
        var oneOne = storage.popRequestedOrAll(Banknote.ONE, 1L);

        assertEquals(fourTens, Long.valueOf(4L));
        assertEquals(oneOne, Long.valueOf(1L));

    }


}
