package otus.atm;

import java.util.Collection;
import java.util.Collections;


public class ATM {

    private final MoneyConsumer consumer;
    private final MoneyProducer producer;

    public ATM(MoneyConsumer consumer, MoneyProducer producer) {
        this.consumer = consumer;
        this.producer = producer;
    }

    public void store(Collection<Money> moneyCollection) {
        try {
            moneyCollection.forEach(consumer);
        } catch (IllegalArgumentException e) {
            System.out.println("Sorry, your input is not supported");
        }
    }

    public Collection<Money> request(long value) {
        try {
            return producer.get(value);
        } catch (IllegalStateException e) {
            System.out.println("Sorry, this amount of money is not available");
        }
        return Collections.emptySet();
    }

    public String showAvailable() {
        StringBuilder builder = new StringBuilder();
        for (var money: producer.showAvailable()) {
            builder.append(money.toString()).append("\n");
        }
        return builder.toString();
    }

}
