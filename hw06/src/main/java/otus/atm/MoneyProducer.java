package otus.atm;

import java.util.ArrayList;
import java.util.Collection;

public class MoneyProducer {

    private final Storage<Banknote> storage;

    public MoneyProducer(Storage<Banknote> storage) {
        this.storage = storage;
    }

    public Collection<Money> get(final Long requestedValue) {
        var collectedMoney = tryCollectMoney(requestedValue);
        var collectedValue = collectedMoney.stream().mapToLong(Money::getValue).sum();
        if (collectedValue != requestedValue) {
            collectedMoney.forEach(m -> storage.put(m.getBanknote(), m.getAmount())); // return to the storage
            throw new IllegalStateException();
        }
        return collectedMoney;
    }

    private Collection<Money> tryCollectMoney(final long requestedValue) {
        long neededValue = requestedValue;
        var keys = storage.getDescendingKeys();
        Collection<Money> collectedMoney = new ArrayList<>();
        var collectedValue = 0L;
        for (var key: keys) {
            if (collectedValue == requestedValue) break;
            Long requestedAmount = neededValue / key.getValue();
            Long poppedAmount = storage.popRequestedOrAll(key, requestedAmount);
            collectedMoney.add(new Money(key, poppedAmount));
            var poppedValue = poppedAmount * key.getValue();
            collectedValue += poppedValue;
            neededValue -= poppedValue;
        }
        return collectedMoney;
    }

}
