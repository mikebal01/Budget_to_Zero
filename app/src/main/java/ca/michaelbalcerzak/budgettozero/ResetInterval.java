package ca.michaelbalcerzak.budgettozero;

public enum ResetInterval {
    NEVER(-1),
    MONTHLY(31),
    BI_MONTHLY(15),
    BI_WEEKLY(14),
    BIWEEKLY(14),
    WEEKLY(7),
    DAILY(1);

    private final int daysPerInterval;

    ResetInterval(int daysPerInterval) {
        this.daysPerInterval = daysPerInterval;
    }

    public int getDaysPerInterval() {
        return daysPerInterval;
    }
}
