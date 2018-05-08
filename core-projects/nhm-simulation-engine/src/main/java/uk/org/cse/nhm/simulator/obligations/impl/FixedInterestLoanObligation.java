package uk.org.cse.nhm.simulator.obligations.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.obligations.ILoan;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.transactions.IPayment;
import uk.org.cse.nhm.simulator.transactions.Payment;

/**
 * This is an implementation of a simple loan which borrows a given principal
 * and repays it at a fixed annual rate.
 *
 * The interest is compounded annually on the remaining principal
 *
 * @author hinton
 *
 */
public class FixedInterestLoanObligation extends BaseObligation implements ILoan {

    private final int termInYears;
    private final String payee;
    private final Set<String> tags;
    private final double tilt;
    private final double initialAnnualRepayment;
    private final DateTime birthday;
    private IPayment standardPayment;

    @AssistedInject
    public FixedInterestLoanObligation(
            final ITimeDimension time,
            final ICanonicalState state,
            final ISimulator simulator,
            @Assisted final DateTime birthday,
            @Assisted final String payee,
            @Assisted final Set<String> tags,
            @Assisted("term") final int termInYears,
            @Assisted("index") final int index,
            @Assisted("principal") final double principal,
            @Assisted("rate") final double interestRate,
            @Assisted("tilt") final double tilt) {
        super(time, state, simulator, index);
        this.birthday = birthday;
        this.payee = payee;
        this.termInYears = termInYears;
        this.tilt = tilt;

        initialAnnualRepayment = getInitialAnnualRepayment(principal, interestRate, tilt, termInYears);

        if (tilt == 0) {
            standardPayment = Payment.of(payee, initialAnnualRepayment, tags);
        } else {
            standardPayment = null;
        }

        this.tags = tags;
    }

    private IPayment makePayment(final int years) {
        if (standardPayment != null) {
            return standardPayment;
        } else {
            return Payment.of(payee, calcAmount(years), tags);
        }
    }

    private double calcAmount(final int years) {
        return initialAnnualRepayment * Math.pow(1 + tilt, years - 1);
    }

    /**
     *
     * @param P0 the initial principal
     * @param a the interest rate as a proportion; interest = principal * a
     * @param b the tilt of repayments; repayment' = repayment * (1+b)
     * @param n the term of the loan
     * @return the first annual repayment
     */
    static double getInitialAnnualRepayment(final double P0, final double a, final double b, final int n) {
        final double compoundedInterest = Math.pow(a + 1, n);
        final double compoundedTilt = Math.pow(b + 1, n);
        if (Math.abs(b - a) < 0.001) {
            return P0 * (a + 1) / n;
        } else if (b == 0) {
            return P0 * compoundedInterest * a / (compoundedInterest - 1);
        } else {
            return P0 * compoundedInterest * (a - b) / (compoundedInterest - compoundedTilt);
        }
    }

    @Override
    public Optional<DateTime> getNextTransactionDate(final DateTime now) {
        int years = now.getYear() - birthday.getYear();
        if (!birthday.plusYears(years).isAfter(now)) {
            years += 1;
        }

        years = Math.max(1, years);

        if (years > termInYears) {
            return Optional.absent();
        } else {

            return Optional.of(birthday.plusYears(years));
        }
    }

    @Override
    public Collection<IPayment> generatePayments(final DateTime date, final IComponentsScope state) {
        final int yearsIntoLoan = date.getYear() - birthday.getYear();

        if (yearsIntoLoan > 0 && yearsIntoLoan <= termInYears && birthday.plusYears(yearsIntoLoan).equals(date)) {
            return ImmutableSet.of(makePayment(yearsIntoLoan));
        } else {
            return Collections.emptySet();
        }
    }

    @Override
    public int getTerm() {
        return termInYears;
    }

    @Override
    public double getOutstandingBalance(final DateTime now) {
        final int yearsIntoLoan = Math.max(0, now.getYear() - birthday.getYear());

        double accum = 0.0;
        for (int i = yearsIntoLoan; i < termInYears; i++) {
            accum += calcAmount(i);
        }
        return accum;
    }

    @Override
    public String getPayee() {
        return payee;
    }

    @Override
    public Set<String> getTags() {
        return tags;
    }
}
