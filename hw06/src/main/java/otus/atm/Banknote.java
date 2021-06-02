package otus.atm;

public enum Banknote {
    ONE(1L), FIVE(5L), TEN(10L), FIFTY(50L);

    public Long getValue() {
        return value;
    }

    private final Long value;

    Banknote(Long value) {
        this.value = value;
    }
}
