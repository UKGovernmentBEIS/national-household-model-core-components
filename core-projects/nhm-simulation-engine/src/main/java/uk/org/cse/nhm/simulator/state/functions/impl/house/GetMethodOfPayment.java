package uk.org.cse.nhm.simulator.state.functions.impl.house;

import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.language.definition.fuel.XMethodOfPayment;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.ITariffs;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class GetMethodOfPayment extends AbstractNamed implements IComponentsFunction<XMethodOfPayment> {
	private final FuelType fuelType;
	private final IDimension<ITariffs> tariffsDimension;

	@AssistedInject
	public GetMethodOfPayment(@Assisted final FuelType fuelType,final IDimension<ITariffs> tariffsDimension) {
		this.fuelType = fuelType;
		this.tariffsDimension = tariffsDimension;
	}

	@Override
	public XMethodOfPayment compute(final IComponentsScope scope, final ILets lets) {
		return scope.get(tariffsDimension).getTariff(fuelType).getMethodOfPayment();
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return Collections.<IDimension<?>> singleton(tariffsDimension);
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.emptySet();
	}

}
