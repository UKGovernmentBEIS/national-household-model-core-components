package uk.org.cse.nhm.simulator.transactions;

import org.joda.time.DateTime;

import uk.org.cse.nhm.language.definition.money.TransactionTags;

public interface ITransaction extends IPayment {
	String getPayer();

	DateTime getDate();
	
	boolean isForDwelling();

	double getWeight();
	
	public interface Counterparties {
		public static final String MARKET = ":market";
		public static final String MAINTENANCE = ":maintenance";
		public static final String ENERGY_COMPANIES = ":energy companies";
	}
	
	public interface Tags {
		public static final String CAPEX = TransactionTags.Internal.capex;
		public static final String OPEX = TransactionTags.Internal.opex;
		public static final String FUEL = ":fuel";
		public static final String SUBSIDY = TransactionTags.Internal.subsidy;
	}
}