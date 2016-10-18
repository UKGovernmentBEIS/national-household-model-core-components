package uk.org.cse.nhm.simulator.state.impl;

import uk.org.cse.nhm.simulator.transactions.ITransaction;
import uk.org.cse.nhm.simulator.transactions.ITransactionHistory;

public class GlobalTransactionHistory implements ITransactionHistory<GlobalTransactionHistory> {

	private final double quantum;
	private double balance;
	int generation;

	public GlobalTransactionHistory(final double quantum) {
		this(quantum, 0, 0);
	}
	
	public GlobalTransactionHistory(final double quantum, final double balance, final int generation) {
		this.quantum = quantum;
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
		
		if (item.isForDwelling()) {
			balance += item.getAmount() * quantum * sign;			
		} else {
			balance += item.getAmount() * sign;
		}
		
		generation++;
	}

	@Override
	public GlobalTransactionHistory branch() {
		return new GlobalTransactionHistory(quantum, balance, generation);
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + generation;
		temp = Double.doubleToLongBits(quantum);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final GlobalTransactionHistory other = (GlobalTransactionHistory) obj;
		if (Double.doubleToLongBits(balance) != Double
				.doubleToLongBits(other.balance))
			return false;
		if (generation != other.generation)
			return false;
		if (Double.doubleToLongBits(quantum) != Double
				.doubleToLongBits(other.quantum))
			return false;
		return true;
	}

	public int getGeneration() {
		return generation;
	}
}
