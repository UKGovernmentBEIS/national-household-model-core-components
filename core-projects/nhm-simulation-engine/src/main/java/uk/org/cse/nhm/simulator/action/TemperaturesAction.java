package uk.org.cse.nhm.simulator.action;

import java.util.Set;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.energycalculator.api.types.MonthType;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.behaviour.IHeatingBehaviour;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class TemperaturesAction extends AbstractHeatingAction {

    private final Optional<IComponentsFunction<Number>> livingAreaTemperature;
    private final Optional<IComponentsFunction<Number>> secondaryTemperature;
    private final Optional<IComponentsFunction<Number>> restHeatedProportion;
    private final boolean secondaryTemperatureIsDifference;
    private final IDimension<StructureModel> structureDimension;
    private final Optional<Set<MonthType>> desiredHeatingMonths;

    @AssistedInject
    public TemperaturesAction(
            final IDimension<IHeatingBehaviour> dimension,
            final IDimension<StructureModel> structureDimension,
            @Assisted("livingArea") final Optional<IComponentsFunction<Number>> livingAreaTemperature,
            @Assisted("delta") final Optional<IComponentsFunction<Number>> deltaTemperature,
            @Assisted("rest") final Optional<IComponentsFunction<Number>> restTemperature,
            @Assisted("restHeatedProportion") final Optional<IComponentsFunction<Number>> restHeatedProportion,
            @Assisted final Optional<Set<MonthType>> desiredHeatingMonths
    ) {
        super(dimension);
        this.structureDimension = structureDimension;
        this.livingAreaTemperature = livingAreaTemperature;
        this.restHeatedProportion = restHeatedProportion;
        if (deltaTemperature.isPresent()) {
            secondaryTemperature = deltaTemperature;
            secondaryTemperatureIsDifference = true;
        } else if (restTemperature.isPresent()) {
            secondaryTemperatureIsDifference = false;
            secondaryTemperature = restTemperature;
        } else {
            secondaryTemperatureIsDifference = false;
            secondaryTemperature = Optional.absent();
        }
        this.desiredHeatingMonths = desiredHeatingMonths;
    }

    @Override
    protected void doApply(final ISettableComponentsScope scope, final ILets lets) {
        scope.modify(heatingDimension, new IModifier<IHeatingBehaviour>() {

            @Override
            public boolean modify(final IHeatingBehaviour current) {
                boolean result = false;
                if (livingAreaTemperature.isPresent()) {
                    current.setLivingAreaDemandTemperature(
                            livingAreaTemperature.get().compute(scope, lets).doubleValue());
                    result = true;
                }
                if (secondaryTemperature.isPresent()) {
                    result = true;
                    if (secondaryTemperatureIsDifference) {
                        current.setTemperatureDifference(secondaryTemperature.get().compute(scope, lets).doubleValue());
                    } else {
                        current.setSecondAreaDemandTemperature(secondaryTemperature.get().compute(scope, lets).doubleValue());
                    }
                }

                if (desiredHeatingMonths.isPresent()) {
                    result = true;
                    current.setHeatingMonths(desiredHeatingMonths.get());
                    result = true;
                }

                return result;
            }
        });

        if (restHeatedProportion.isPresent()) {
            final double heatedProportion = restHeatedProportion.get().compute(scope, lets).doubleValue();

            if (heatedProportion < 0) {
                throw new RuntimeException("Rest of dwelling heated proportion should never be less than 0, was: " + heatedProportion);

            } else if (heatedProportion > 1) {
                throw new RuntimeException("Rest of dwelling heated proportion should never be greater than 1, was: " + heatedProportion);
            }

            scope.modify(structureDimension, new IModifier<StructureModel>() {
                @Override
                public boolean modify(final StructureModel structure) {
                    structure.setZoneTwoHeatedProportion(heatedProportion);
                    return true;
                }
            });
        }
    }
}
