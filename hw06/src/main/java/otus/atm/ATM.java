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
        moneyCollection.forEach(consumer);
    }

    public Collection<Money> request(long value) {
        try {
            return producer.get(value);
        } catch (IllegalStateException e) {
            System.out.println("Sorry, this amount of money is not available");
        }
        return Collections.emptySet();
    }


}
