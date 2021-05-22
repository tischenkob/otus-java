package otus.atm;

import java.util.Collection;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Storage<E extends Enum<E>> {

    private final NavigableMap<E, Long> store = new TreeMap<>();

    public Storage(Collection<E> supportedKeys) {
        supportedKeys.forEach(element -> store.put(element, 0L));
    }

    public void put(E key, Long amount) throws IllegalArgumentException {
        checkArgumentsAndThrow(key, amount);
        Long newAmount = store.get(key) + amount;
        store.put(key, newAmount);
    }

    public Long pop(E key, Long requestedAmount) throws IllegalArgumentException, IllegalStateException {
        checkArgumentsAndThrow(key, requestedAmount);
        Long currentAmount = store.get(key);
        if (requestedAmount > currentAmount) return 0L;
        store.put(key, currentAmount - requestedAmount);
        return requestedAmount;
    }

    private void checkArgumentsAndThrow(E key, Long amount) throws IllegalArgumentException {
        if (!store.containsKey(key) || amount < 0) {
            throw new IllegalArgumentException();
        }
    }

}
