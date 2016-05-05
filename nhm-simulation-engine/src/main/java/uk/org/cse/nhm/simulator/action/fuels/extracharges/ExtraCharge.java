package uk.org.cse.nhm.simulator.action.fuels.extracharges;

import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.IExtraCharge;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.ITransaction;
import uk.org.cse.nhm.simulator.transactions.Payment;

public class ExtraCharge implements IExtraCharge {
	private final String payee;
	private final Set<String> tags;
	private final IComponentsFunction<Number> charge;
	private final Optional<FuelType> maybeFuel;
	private final int order;

	public ExtraCharge(final Optional<FuelType> maybeFuel, final String payee,
			final Set<String> tags, final IComponentsFunction<Number> charge, final int order) {
		this.maybeFuel = maybeFuel;
		this.payee = payee;
		this.order = order;
		this.tags = withDefaultTags(maybeFuel, tags);
		this.charge = charge;
	}

	private ImmutableSet<String> withDefaultTags(final Optional<FuelType> maybeFuel, final Set<String> tags) {
		final Builder<String> builder = ImmutableSet.<String>builder()
				.addAll(tags)
				.add(ITransaction.Tags.FUEL);
		
		if (maybeFuel.isPresent()) {
			builder.add(maybeFuel.get().name());
		}
		return builder.build();
	}

	@Override
	public Optional<FuelType> getFuel() {
		return maybeFuel;
	}

	@Override
	public void apply(final ISettableComponentsScope scope, final ILets lets) {
		scope.addTransaction(Payment.of(payee, charge.compute(scope, lets).doubleValue(), tags));
	}

	@Override
	public int getOrder() {
		return order;
	}
}
