package otus.atm;

import java.util.*;

public class Storage<E extends Enum<E>> {

    private final NavigableMap<E, Long> store = new TreeMap<>();

    public Storage(Collection<E> supportedKeys) {
        supportedKeys.forEach(element -> store.put(element, 0L));
    }

    public Map<E, Long> showAvailable() {
        return Collections.unmodifiableMap(store);
    }

    public Collection<E> getDescendingKeys() {
        return Collections.unmodifiableNavigableSet(store.descendingKeySet());
    }

    public boolean has(E key, Long amount) {
        return store.getOrDefault(key, 0L).equals(amount);
    }

    public void put(E key, Long amount) throws IllegalArgumentException {
        checkArgumentsAndThrow(key, amount);
        Long newAmount = store.get(key) + amount;
        store.put(key, newAmount);
    }

    public Long popRequestedOrAll(E key, Long requestedAmount) {
        checkArgumentsAndThrow(key, requestedAmount);
        Long currentAmount = store.get(key);
        if (requestedAmount > currentAmount) requestedAmount = currentAmount;
        store.put(key, currentAmount - requestedAmount);
        return requestedAmount;
    }

    private void checkArgumentsAndThrow(E key, Long amount) throws IllegalArgumentException {
        if (!store.containsKey(key) || amount < 0) {
            throw new IllegalArgumentException();
        }
    }

}
