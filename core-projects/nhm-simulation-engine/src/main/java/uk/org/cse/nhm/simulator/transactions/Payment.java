package uk.org.cse.nhm.simulator.transactions;

import java.util.Set;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

@AutoProperty
public class Payment implements IPayment {

    final String payee;
    final double amount;
    final Set<String> tags;

    static Interner<IPayment> interner = Interners.newWeakInterner();

    public static IPayment of(final String payee, final double amount, final Set<String> tags) {
        return interner.intern(new Payment(payee, amount, tags));
    }

    public static IPayment capexToMarket(final double amount, final Set<String> tags) {
        return of(
                ITransaction.Counterparties.MARKET,
                amount,
                ImmutableSet.<String>builder().add(ITransaction.Tags.CAPEX).addAll(tags).build());
    }

    public static IPayment capexToMarket(final double amount) {
        return of(
                ITransaction.Counterparties.MARKET,
                amount,
                ImmutableSet.of(ITransaction.Tags.CAPEX));
    }

    private Payment(final String payee, final double amount, final Set<String> tags) {
        super();
        this.payee = payee;
        this.amount = amount;
        final ImmutableSet.Builder<String> mangledTags = ImmutableSet.builder();
        for (final String s : tags) {
            mangledTags.add(s);
            if (s.startsWith(":")) {
                mangledTags.add(s.substring(1));
            }
        }
        this.tags = mangledTags.build();
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
}
