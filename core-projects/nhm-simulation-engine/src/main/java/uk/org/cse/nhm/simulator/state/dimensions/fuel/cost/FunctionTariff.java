package uk.org.cse.nhm.simulator.state.dimensions.fuel.cost;

import java.util.EnumMap;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.language.definition.fuel.XMethodOfPayment;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITime;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.ITransaction;
import uk.org.cse.nhm.simulator.transactions.Payment;

public class FunctionTariff extends AbstractNamed implements ITariff {
	private static final ILets TARIFF_TIME_LETS = ILets.EMPTY.withBinding(ITime.TIME_KEY, XForesightLevel.Tariffs);
	
	public static class FuelRule extends AbstractNamed {
		public static class Transactor {
			private final IComponentsFunction<Number> function;
			private final String counterparty;
			private final Set<String> tags;
			
			public Transactor(final IComponentsFunction<Number> function,
					final String counterparty, final Set<String> tags) {
				this.function = function;
				this.counterparty = counterparty;
				this.tags = ImmutableSet.<String>builder().addAll(tags).add(ITransaction.Tags.FUEL).build();
			}

			public void apply(final ISettableComponentsScope output) {
				final Double value = function.compute(output, TARIFF_TIME_LETS).doubleValue();
				
				if (value != null && value != 0) {
					output.addTransaction(Payment.of(counterparty, value, tags));
				}
			}
		}

		private final List<Transactor> transactors;
		private final FuelType fuelType;
		
		public FuelRule(final FuelType fuelType, final List<Transactor> transactors) {
			this.fuelType = fuelType;
			this.transactors = ImmutableList.copyOf(transactors);
		}

		public void apply(final ISettableComponentsScope output) {
			for (final Transactor t : transactors) {
				t.apply(output);
			}
		}
		
		public FuelType getFuelType() {
			return fuelType;
		}
	}
	
	private final EnumMap<FuelType, FuelRule> rules = new EnumMap<>(FuelType.class);
	private final XMethodOfPayment mop;
	
	public FunctionTariff(final XMethodOfPayment mop, final List<FuelRule> rules) {
		this.mop = mop;
		for (final FuelRule rule : rules) {
			this.rules.put(rule.getFuelType(), rule);
		}
	}
	
	@Override
	public XMethodOfPayment getMethodOfPayment() {
		return mop;
	}
	
	@Override
	public Set<FuelType> getFuelTypes() {
		return rules.keySet();
	}

	@Override
	public void apply(final FuelType fuelType, final ISettableComponentsScope output) {	
		if (rules.containsKey(fuelType)) {
			rules.get(fuelType).apply(output);
		}
	}
	
	@Override
	public Name getIdentifierForFuel(final FuelType ft) {
		if (rules.containsKey(ft)) {
			return rules.get(ft).getIdentifier();
		} else {
			return null;
		}
	}
}
