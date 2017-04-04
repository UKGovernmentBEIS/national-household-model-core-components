package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

import uk.org.cse.nhm.language.definition.function.num.XHeatLoss.XHeatLossType;

public class HeatLossFunction extends AbstractNamed implements IComponentsFunction<Double> {
    private final IDimension<IPowerTable> energy;
    private final XHeatLossType type;

	@Inject
    public HeatLossFunction(
			@Named("uncalibrated") final IDimension<IPowerTable> energy,
            @Assisted final XHeatLossType type) {
        this.energy = energy;
        this.type = type;
	}
	
	@Override
    public Double compute(final IComponentsScope scope, final ILets lets) {
        switch (type) {
        case Total:           return Double.valueOf(scope.get(energy).getSpecificHeatLoss());
        case Fabric:          return Double.valueOf(scope.get(energy).getFabricHeatLoss());
        case Ventilation:     return Double.valueOf(scope.get(energy).getVentilationHeatLoss());
        case ThermalBridging: return Double.valueOf(scope.get(energy).getThermalBridgingHeatLoss());
        default:         throw new IllegalArgumentException("Unknown type of heat loss: "+type);
        }
    }

	@Override
	public Set<IDimension<?>> getDependencies() {
		return Collections.<IDimension<?>>singleton(energy);
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.emptySet();
	}
}
