package uk.org.cse.nhm.simulator.transactions;

import java.util.Set;

/**
 * Describes a partial payment; a transfer of money from a context-specific subject
 * to a certain payee. 
 * 
 * @since 3.7.0
 *
 */
public interface IPayment {
	String getPayee();
	double getAmount();
	Set<String> getTags();
}
