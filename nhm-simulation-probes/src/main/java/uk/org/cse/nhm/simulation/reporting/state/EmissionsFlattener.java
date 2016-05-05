package uk.org.cse.nhm.simulation.reporting.state;

import java.util.Set;

import javax.inject.Inject;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.logging.logentry.components.AbstractFuelServiceLogComponent;
import uk.org.cse.nhm.logging.logentry.components.EmissionsLogComponent;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.IEmissions;

public class EmissionsFlattener implements IComponentFlattener {

	private IDimension<IEmissions> emissionsDimension;

	@Inject
	public EmissionsFlattener(final IDimension<IEmissions> emissionsDimension) {
		this.emissionsDimension = emissionsDimension;
	}

	@Override
	public Object flatten(IState state, final IDwelling dwelling) {
		final IEmissions emissionsValue = state.get(emissionsDimension, dwelling);
		final AbstractFuelServiceLogComponent.MapBuilder b = AbstractFuelServiceLogComponent.MapBuilder.builder();
		for (final ServiceType es : ServiceType.values()) {
			for (final FuelType ft : FuelType.values()) {
				b.put(ft, es, emissionsValue.getAnnualEmissions(ft, es));
			}
		}
		return new EmissionsLogComponent(b.build());
	}

	@Override
	public Set<IDimension<?>> getComponents() {
		return ImmutableSet.<IDimension<?>>of(emissionsDimension);
	}
}
