package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Collection;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;
import com.google.common.collect.Multimap;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.obligations.IObligationHistory;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IEnergyMeter;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.transactions.IPayment;
import uk.org.cse.nhm.simulator.transactions.ITransaction;

/**
 * A ranking function which computes the net present value of a house, given a certain discount rate
 * and time horizon.
 * 
 * The NPV is calculated as
 * 
 * <ol>
 *	<li>Capital costs (installation costs etc)</li>
 *	<li>Operational costs which are sum for year from 0 to {@link #horizonInYears}-1
 *		<ul>
 			<li>cost in year * discountRate ^ year</li>
 			<li>annual cost is fuel cost at current energy usage for 1 year + annual maintenance costs of current state</li>
 		</ul>
 	</li>
 * </ol>
 * 
 * @author hinton
 *
 */
public class NetPresentValueFunction extends ObligationForecastingFunction {
	private static final Logger log = LoggerFactory.getLogger(NetPresentValueFunction.class);
	
	/**
	 * This is how many years the NPV is summed over
	 */
	private final int horizonInYears;
	/**
	 * This is the discount rate per annum, so year n is priced at (cost)*(discountRate)^(n-1) (if this year n = 1)
	 */
	private final double[] multipliers;

	private final Set<IDimension<?>> allDimensions;
	private final IDimension<IObligationHistory> obligationsDimension;
	private final ITimeDimension time;
    private final boolean debug;
	
	@Inject
	public NetPresentValueFunction(
			final Set<IDimension<?>> allDimensions,
			final IDimension<IEnergyMeter> meterDimension,
			final IDimension<IPowerTable> powerDimension,
			final IDimension<IObligationHistory> obligationsDimension,
			final ITimeDimension time,
            @Assisted final boolean debug,
			@Assisted final double discountRate,
			@Assisted final int horizonInYears,
            @Assisted final Predicate<Collection<String>> allowedTags) {
		super(allDimensions, meterDimension, powerDimension, allowedTags);
		this.allDimensions = allDimensions;
		
		this.obligationsDimension = obligationsDimension;
		this.time = time;
		this.horizonInYears = horizonInYears;
		this.debug = debug;
        
		this.multipliers = new double[horizonInYears];
		{
			multipliers[0] = 1;
			for (int i = 1; i<multipliers.length; i++) {
				multipliers[i] = multipliers[i-1] / (1 + discountRate);
			}
		}
	}

    private void debug(final IPayment immediate) {
        if (debug) {
            log.info("now\t{}\t{}\t{}\t{}", immediate.getAmount(), immediate.getAmount(), immediate.getTags());
        }
    }

    private void debug(final IPayment payment, final DateTime date, final int deltaYears, final double mul) {
        if (debug) {
            log.info("{}\t+{}y\t{}\t{}\t{}", date, deltaYears, payment.getAmount(),
                     payment.getAmount() * mul,
                     payment.getTags());
        }
    }
    
	@Override
	public Double compute(final IComponentsScope scope, final ILets lets) {
		final double installationCosts = sumTransactionsInScope(scope);
		
		final DateTime now = scope.get(time).get(lets);
		final DateTime then = now.plus(Period.years(horizonInYears));
		
		final Multimap<DateTime, IPayment> futurePayments = predictObligations(scope, scope.get(obligationsDimension), now, then);
		
		double discountedFutureAccumulator = 0;
		double undiscountedAccumulator = 0;
		for (final Entry<DateTime, IPayment> entry : futurePayments.entries()) {
			final Period delta = new Period(now, entry.getKey());
			final int deltaYears;

			if (delta.equals(Period.years(delta.getYears()))) {
				deltaYears = delta.getYears() - 1;
			} else {
				deltaYears = delta.getYears();
			}

			discountedFutureAccumulator += multipliers[deltaYears] * entry.getValue().getAmount();
			undiscountedAccumulator += entry.getValue().getAmount();

            debug(entry.getValue(), entry.getKey(), deltaYears, multipliers[deltaYears]);
		}
		
		if (scope instanceof ISettableComponentsScope) {
			((ISettableComponentsScope) scope).addNote(
					NetPresentValueAnnotation.of(installationCosts, undiscountedAccumulator, discountedFutureAccumulator));
		}
		
		return discountedFutureAccumulator + installationCosts;
	}

	private double sumTransactionsInScope(final IComponentsScope result) {
		double capitalCost = 0;
		
		for (final ITransaction transaction : result.getAllNotes(ITransaction.class)) {
            if (includePayment(transaction)) {
                debug(transaction);
                capitalCost += transaction.getAmount();
            }
		}
		
		return capitalCost;
	}
	
	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.emptySet();
	}
	
	/**
	 * TODO This is grim; since NPV depends on evaluating obligations, it could depend on any of the dimensions on a house
	 *      so for correctness we must report that we depend on all dimensions. Hopefully NPV will not be used in any performance
	 *      critical change-driven setting (like a group defn.)
	 */
	@Override
	public Set<IDimension<?>> getDependencies() {
		return allDimensions;
	}
}
