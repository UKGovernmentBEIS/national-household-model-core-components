package uk.org.cse.nhm.simulator.transactions;

import uk.org.cse.nhm.hom.ICopyable;

public interface ITransactionHistory<T extends ITransactionHistory<T>> extends ICopyable<T> {

    public abstract T branch();

    void pay(ITransaction transaction);

    void receive(ITransaction transaction);

    void merge(final T child);

    /**
     * Gets the balance of transactions over the course of the simulation.
     */
    public abstract double getBalance();

    /**
     * The cached results of accumulating transactions. This is a bit yucky
     * because the indexes for the array are store elsewhere.
     *
     * @see ITransactionRunningTotal
     */
    public abstract double[] accumulatorCache();

}
