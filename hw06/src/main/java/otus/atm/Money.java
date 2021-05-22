package otus.atm;

import java.util.Objects;

public class Money {
    private final Banknote banknote;
    private final Long amount;

    public Money(Banknote banknote, Long amount) {
        this.banknote = banknote;
        this.amount = amount;
    }

    public Banknote getBanknote() {
        return banknote;
    }

    public Long getValue() {
        return banknote.getValue() * amount;
    }

    public Long getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return banknote == money.banknote && amount.equals(money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(banknote, amount);
    }
}
