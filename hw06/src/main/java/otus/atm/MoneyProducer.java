package otus.atm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MoneyProducer {

    private final Storage<Banknote> storage;

    public MoneyProducer(Storage<Banknote> storage) {
        this.storage = storage;
    }

    private List<Banknote> getAscendingBanknotes() {
        final Banknote[] banknotes = Banknote.values();
        return Arrays.stream(banknotes)
                .sorted((current, next) -> (int) (next.getValue() - current.getValue()))
                .collect(Collectors.toList());
    }

    public Collection<Money> get(final Long requestedValue) {
        var collectedMoney = tryCollectMoney(requestedValue);
        var collectedValue = collectedMoney.stream().mapToLong(Money::getAmount).sum();
        if (collectedValue != requestedValue) {
            collectedMoney.forEach(m -> storage.put(m.getBanknote(), m.getAmount())); // return to the storage
            throw new IllegalStateException();
        }
        return collectedMoney;
    }

    private Collection<Money> tryCollectMoney(final long requestedValue) {
        final var ascendingBanknotes = getAscendingBanknotes();
        final Collection<Money> collectedMoney = new ArrayList<>();
        long collectedValue = 0L;
        long neededValue = requestedValue;
        var indexOfCurrentBiggestBanknote = ascendingBanknotes.size() - 1;
        while (collectedValue < requestedValue) {
            var currentBiggestBanknote = ascendingBanknotes.get(indexOfCurrentBiggestBanknote);
            var neededAmountOfBanknotes = neededValue % currentBiggestBanknote.getValue();
            Money money = tryGetMoney(currentBiggestBanknote, neededAmountOfBanknotes);
            Long value = money.getValue();
            if (value != 0) {
                collectedMoney.add(money);
                collectedValue += value;
                neededValue -= value;
            }
            indexOfCurrentBiggestBanknote -= 1;
            if (indexOfCurrentBiggestBanknote == -1) break;
        }
        return collectedMoney;

    }

    private Money tryGetMoney(Banknote banknote, long neededAmount) {
        Long gottenAmount = 0L;
        while (gottenAmount == 0L) {
            gottenAmount = storage.pop(banknote, neededAmount); // returns 0 if doesn't have enough
            neededAmount -= 1;
            if (neededAmount == 0) break;
        }
        return new Money(banknote, gottenAmount);
    }

}
