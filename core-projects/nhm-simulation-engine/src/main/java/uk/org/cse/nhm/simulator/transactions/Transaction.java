package uk.org.cse.nhm.simulator.transactions;

import java.util.Set;

import org.joda.time.DateTime;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

import uk.org.cse.nhm.simulator.state.IDwelling;

@AutoProperty
public class Transaction implements ITransaction {
	private final String payer;
	private final String payee;
	private final double amount;
	private final double weight;
	private final DateTime date;
	private final Set<String> tags;
	private final boolean isForDwelling;

	private static Interner<Transaction> interner = Interners.newWeakInterner();
	private static Interner<String> idInterner = Interners.newWeakInterner();
	private static Interner<DateTime> dtInterner = Interners.newWeakInterner();
	private static Interner<Set<String>> tagsInterner = Interners.newWeakInterner();
	
	public static ITransaction dwelling(final IDwelling payer, final String payee, final double amount, final DateTime date, final Set<String> tags) {
		return interner.intern(new Transaction("dwelling " + Integer.toString(payer.getID()), payee, amount, date, tags, true, payer.getWeight()));
	}
	
	public static ITransaction global(final String payer, final String payee, final double amount, final DateTime date, final Set<String> tags) {
		return interner.intern(new Transaction(payer, payee, amount, date, tags, false, 1));
	}
	
	private Transaction(final String payer, final String payee, final double amount, final DateTime date,
			final Set<String> tags, final boolean isForDwelling, final double weight) {
		super();
		this.payer = idInterner.intern(payer);
		this.payee = idInterner.intern(payee);
		this.amount = amount;
		this.date = dtInterner.intern(date);
		this.isForDwelling = isForDwelling;
		this.weight = weight;
		this.tags = tagsInterner.intern(ImmutableSet.copyOf(tags));
	}

	@Override
	public String getPayer() {
		return payer;
	}

	@Override
	public String getPayee() {
		return payee;
	}

	@Override
	public double getAmount() {
		return amount;
	}

	@Override
	public DateTime getDate() {
		return date;
	}

	@Override
	public Set<String> getTags() {
		return tags;
	}
	
	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
	
	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}
	
	@Override
	public boolean equals(final Object obj) {
		return Pojomatic.equals(this, obj);
	}

	@Override
	public boolean isForDwelling() {
		return isForDwelling;
	}

	@Override
	public double getWeight() {
		return weight;
	}
}
