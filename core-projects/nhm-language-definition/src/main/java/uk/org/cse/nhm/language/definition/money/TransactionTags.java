package uk.org.cse.nhm.language.definition.money;

public class TransactionTags {

    public static class Internal {
        //TODO duplicated in ILoan
        //TODO pass from adapter instead

        public static final String loan = ":loan";
        public static final String repayment = ":repayment";
        public static final String principal = ":principal";

        public static final String capex = ":CAPEX";
        public static final String opex = ":OPEX";
        public static final String subsidy = ":subsidy";
    }

    public static class Default {

    }
}
