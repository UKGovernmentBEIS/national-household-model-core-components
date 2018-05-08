package uk.org.cse.nhm.simulator.obligations;

import java.util.Set;

import org.joda.time.DateTime;

/**
 * A marker interface which indicates that an obligation is a loan
 *
 * @since 3.7.0
 *
 */
public interface ILoan extends IObligation {

    public int getTerm();

    public double getOutstandingBalance(DateTime now);

    public String getPayee();

    public Set<String> getTags();

    public static class Tags {
        // TODO DUPLICATED IN TransactionTags

        public static final String Principal = ":principal";
        public static final String Repayment = ":repayment";
        public static final String Loan = ":loan";
    }
}
