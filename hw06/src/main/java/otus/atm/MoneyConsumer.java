package otus.atm;

import java.util.function.Consumer;

public class MoneyConsumer implements Consumer<Money> {

    Storage<Banknote> storage;

    public MoneyConsumer(Storage<Banknote> storage) {
        this.storage = storage;
    }

    @Override
    public void accept(Money money) throws IllegalArgumentException {
        storage.put(money.getBanknote(), money.getAmount());
    }

}
