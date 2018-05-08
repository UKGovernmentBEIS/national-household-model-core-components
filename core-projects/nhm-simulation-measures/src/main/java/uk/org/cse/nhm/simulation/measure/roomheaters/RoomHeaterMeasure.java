package uk.org.cse.nhm.simulation.measure.roomheaters;

import java.util.Collections;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.IRoomHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.util.Efficiency;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.language.builder.action.measure.wetheating.IWetHeatingMeasureFactory;
import uk.org.cse.nhm.simulation.measure.AbstractHeatingMeasure;
import uk.org.cse.nhm.simulator.IProfilingStack;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.measure.sizing.ISizingFunction;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IComponents;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.Undefined;

public class RoomHeaterMeasure extends AbstractHeatingMeasure {

    private final IDimension<ITechnologyModel> techDimension;
    private final ITechnologiesFactory techFactory;

    private final FuelType fuel;
    private final double efficiency;
    private final boolean replaceExisting;

    @AssistedInject
    protected RoomHeaterMeasure(
            final ITimeDimension time,
            final IWetHeatingMeasureFactory factory,
            final IDimension<ITechnologyModel> techDimension,
            final ITechnologyOperations operations,
            final IProfilingStack stack,
            @Assisted final ISizingFunction sizingFunction,
            @Assisted("capex") final IComponentsFunction<Number> capitalCostFunction,
            @Assisted("opex") final IComponentsFunction<Number> opexCostFunction,
            @Assisted final FuelType fuel,
            @Assisted final Optional<Double> efficiency,
            @Assisted("replaceExisting") final boolean replaceExisting) {
        super(time,
                techDimension,
                operations,
                factory,
                TechnologyType.roomHeater(fuel),
                sizingFunction,
                capitalCostFunction, opexCostFunction, Undefined.<Number>get(stack, "Room heater should not install wet central heating"));
        this.techDimension = techDimension;
        this.techFactory = ITechnologiesFactory.eINSTANCE;
        this.fuel = fuel;
        if (fuel == FuelType.ELECTRICITY) {
            this.efficiency = 1.0;
        } else {
            if (efficiency.isPresent()) {
                this.efficiency = efficiency.get();
            } else {
                throw new IllegalArgumentException("If Fuel is not electricity, then a value for efficiency is required.");
            }
        }
        this.replaceExisting = replaceExisting;
    }

    @Override
    protected boolean doApply(final ISettableComponentsScope components, final ILets lets, final double size, final double capex, final double opex) throws NHMException {
        components.modify(techDimension, new IModifier<ITechnologyModel>() {

            @Override
            public boolean modify(final ITechnologyModel modifiable) {
                final IRoomHeater roomHeater = techFactory.createRoomHeater();
                roomHeater.setEfficiency(Efficiency.fromDouble(efficiency));
                roomHeater.setFuel(fuel);

                modifiable.setSecondarySpaceHeater(roomHeater);

                return true;
            }
        });

        return true;
    }

    @Override
    protected boolean doIsSuitable(final IComponents components) {
        return replaceExisting || components.get(techDimension).getSecondarySpaceHeater() == null;
    }

    @Override
    protected Set<HeatingSystemControlType> getHeatingSystemControlTypes() {
        return Collections.emptySet();
    }
}
