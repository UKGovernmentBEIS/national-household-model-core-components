package uk.org.cse.nhm.energycalculator.api.types;

public enum MonthType {
    January(31), February(28), March(31), April(30), May(31), June(30), July(31), August(31), September(30), October(31), November(30), December(31);

    private final int standardDays;

    MonthType(final int standardDays) {
        this.standardDays = standardDays;
    }

    public int getStandardDays() {
        return standardDays;
    }

    /**
     * Allows for looping (so you can say "between October and May").
     *
     * @return whether this month is between the start and end months
     * (inclusive)
     */
    public boolean between(final MonthType startMonth, final MonthType endMonth) {
        final int start = startMonth.ordinal();
        final int end = endMonth.ordinal();

        if (start == end) {
            return this == startMonth;

        } else if (start < end) {
            return this.ordinal() >= start && this.ordinal() <= end;

        } else {
            return this.ordinal() <= end || this.ordinal() >= start;
        }
    }
}
