package uk.org.cse.nhm.simulator.state.impl;

import java.util.Objects;

import uk.org.cse.nhm.simulator.transactions.ITransaction;
import uk.org.cse.nhm.simulator.transactions.ITransactionHistory;

public class GlobalTransactionHistory implements ITransactionHistory<GlobalTransactionHistory> {

	private double balance;
	int generation;

	public GlobalTransactionHistory() {
		this(0, 0);
	}
	
	public GlobalTransactionHistory(final double balance, final int generation) {
		this.balance = balance;
		this.generation = generation;
	}

	@Override
	public void pay(final ITransaction item) {
		transact(-1, item);
	}
	

	@Override
	public void receive(final ITransaction item) {
		transact(1, item);
	}
	
	private void transact(final int sign, final ITransaction item) {
		if (item.getAmount() == 0) {
			return;
		}
		
		balance += item.getAmount() * item.getWeight() * sign;

		generation++;
	}

	@Override
	public GlobalTransactionHistory branch() {
		return new GlobalTransactionHistory(balance, generation);
	}

	@Override
	public void merge(final GlobalTransactionHistory child) {
		this.balance = child.getBalance();
		this.generation = child.generation;
	}

	@Override
	public double getBalance() {
		return balance;
	}

	@Override
	public double[] accumulatorCache() {
		return new double[0];
	}

	@Override
	public GlobalTransactionHistory copy() {
		return branch();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GlobalTransactionHistory that = (GlobalTransactionHistory) o;
		return Double.compare(that.balance, balance) == 0 &&
				generation == that.generation;
	}

	@Override
	public int hashCode() {
		return Objects.hash(balance, generation);
	}

	public int getGeneration() {
		return generation;
	}
}
