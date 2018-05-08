package uk.org.cse.nhm.simulator.transactions;

import java.util.Arrays;

public class DwellingTransactionHistory implements ITransactionHistory<DwellingTransactionHistory> {

    private double balance;
    private final ITransactionRunningTotal runningTotals;
    private double[] accumulatedValues;

    public DwellingTransactionHistory(final ITransactionRunningTotal runningTotals) {
        this.runningTotals = runningTotals;
        balance = 0.0;
        this.accumulatedValues = null;
    }

    protected DwellingTransactionHistory(final DwellingTransactionHistory parent) {
        this.runningTotals = parent.runningTotals;
        balance = parent.getBalance();
        this.accumulatedValues = parent.accumulatedValues;
    }

    @Override
    public void pay(final ITransaction item) {
        balance -= item.getAmount();

        accumulatedValues = runningTotals.update(accumulatedValues, item);
    }

    @Override
    public void receive(final ITransaction transaction) {
    }

    @Override
    public DwellingTransactionHistory branch() {
        return new DwellingTransactionHistory(this);
    }

    @Override
    public DwellingTransactionHistory copy() {
        return branch();
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void merge(final DwellingTransactionHistory child) {
        this.balance = child.getBalance();
        this.accumulatedValues = child.accumulatorCache();
    }

    @Override
    public double[] accumulatorCache() {
        return accumulatedValues;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(accumulatedValues);
        long temp;
        temp = Double.doubleToLongBits(balance);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result
                + ((runningTotals == null) ? 0 : runningTotals.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DwellingTransactionHistory other = (DwellingTransactionHistory) obj;
        if (!Arrays.equals(accumulatedValues, other.accumulatedValues)) {
            return false;
        }
        if (Double.doubleToLongBits(balance) != Double
                .doubleToLongBits(other.balance)) {
            return false;
        }
        if (runningTotals == null) {
            if (other.runningTotals != null) {
                return false;
            }
        } else if (!runningTotals.equals(other.runningTotals)) {
            return false;
        }
        return true;
    }
}
