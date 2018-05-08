package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IHeatingSystem;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.behaviour.IHeatingBehaviour;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class HeatingResponsivenessFunction extends AbstractNamed implements IComponentsFunction<Double> {

    private final IDimension<ITechnologyModel> techDimension;
    private final IConstants constants;
    private final IDimension<IHeatingBehaviour> behaviourDimension;

    @AssistedInject
    public HeatingResponsivenessFunction(
            final IConstants constants,
            final IDimension<ITechnologyModel> techDimension,
            final IDimension<IHeatingBehaviour> behaviourDimension) {
        this.constants = constants;
        this.techDimension = techDimension;
        this.behaviourDimension = behaviourDimension;
    }

    @Override
    public Double compute(final IComponentsScope scope, final ILets lets) {
        final ITechnologyModel tech = scope.get(techDimension);
        final IHeatingBehaviour heatingBehaviour = scope.get(behaviourDimension);

        if (tech.getPrimarySpaceHeater() == null) {
            return 1.0;
        } else {
            IHeatingSystem heatingSystem = (IHeatingSystem) tech.getPrimarySpaceHeater();

            return heatingSystem.getResponsiveness(
                    constants,
                    heatingBehaviour.getEnergyCalculatorType(),
                    ElectricityTariffType.ECONOMY_7
            );
        }
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return ImmutableSet.<IDimension<?>>of(techDimension);
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return ImmutableSet.of();
    }
}
