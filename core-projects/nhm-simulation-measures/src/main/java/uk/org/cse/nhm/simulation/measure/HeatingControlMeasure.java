package uk.org.cse.nhm.simulation.measure;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.IPrimarySpaceHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.measure.Units;
import uk.org.cse.nhm.simulator.measure.impl.TechnologyInstallationDetails;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.Payment;

public class HeatingControlMeasure extends AbstractMeasure {

    private final IDimension<ITechnologyModel> technologies;
    private final HeatingSystemControlType controlType;
    private final IComponentsFunction<Number> capex;

    private final boolean isCentralHeatingControl;
    private final boolean isWarmAirControl;
    private final boolean isRoomHeaterControl;

    private static boolean isCentralHeatingControl(final HeatingSystemControlType controlType) {
        switch (controlType) {
            case APPLIANCE_THERMOSTAT:
            case ROOM_THERMOSTAT:
            case THERMOSTATIC_RADIATOR_VALVE:
            case TIME_TEMPERATURE_ZONE_CONTROL:
            case DELAYED_START_THERMOSTAT:
            case PROGRAMMER:
            case BYPASS:
                return true;
            default:
                return false;
        }
    }

    private static boolean isRoomHeaterControl(final HeatingSystemControlType controlType) {
        return controlType == HeatingSystemControlType.APPLIANCE_THERMOSTAT;
    }

    private static boolean isWarmAirControl(final HeatingSystemControlType controlType) {
        switch (controlType) {
            case APPLIANCE_THERMOSTAT:
            case TIME_TEMPERATURE_ZONE_CONTROL:
            case THERMOSTATIC_RADIATOR_VALVE:
            case ROOM_THERMOSTAT:
                return true;
            default:
                return false;
        }
    }

    @AssistedInject
    HeatingControlMeasure(
            final IDimension<ITechnologyModel> technologies,
            @Assisted final HeatingSystemControlType controlType,
            @Assisted final IComponentsFunction<Number> capex) {
        super();
        this.technologies = technologies;
        this.controlType = controlType;
        this.capex = capex;
        this.isCentralHeatingControl = isCentralHeatingControl(controlType);
        this.isWarmAirControl = isWarmAirControl(controlType);
        this.isRoomHeaterControl = isRoomHeaterControl(controlType);
    }

    static class Modifier implements IModifier<ITechnologyModel> {

        private final HeatingSystemControlType controlType;

        public Modifier(final HeatingSystemControlType controlType) {
            this.controlType = controlType;
        }

        @Override
        public boolean modify(final ITechnologyModel modifiable) {
            final IPrimarySpaceHeater primary = modifiable.getPrimarySpaceHeater();

            if (isCentralHeatingControl(controlType) && primary instanceof ICentralHeatingSystem) {
                return ((ICentralHeatingSystem) primary).getControls().add(controlType);
            } else if (isWarmAirControl(controlType) && primary instanceof IWarmAirSystem) {
                return ((IWarmAirSystem) primary).getControls().add(controlType);
            } else if (isRoomHeaterControl(controlType)) {
                if (modifiable.getSecondarySpaceHeater() != null) {
                    modifiable.getSecondarySpaceHeater().setThermostatFitted(true);
                    return true;
                }
            }

            return false;
        }
    }

    @Override
    public boolean doApply(final ISettableComponentsScope scope, final ILets lets) throws NHMException {
        final double cost = capex.compute(scope, lets).doubleValue();

        scope.modify(technologies, new Modifier(controlType));
        scope.addTransaction(Payment.capexToMarket(cost));
        scope.addNote(
                new TechnologyInstallationDetails(
                        this,
                        TechnologyType.heatingControls(controlType),
                        1, Units.Units, cost, 0));
        return true;
    }

    @Override
    public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
        final ITechnologyModel tech = scope.get(technologies);

        final IPrimarySpaceHeater primary = tech.getPrimarySpaceHeater();
        if (isCentralHeatingControl && primary instanceof ICentralHeatingSystem) {
            return ((ICentralHeatingSystem) primary).getControls().contains(controlType) == false;
        } else if (isWarmAirControl && primary instanceof IWarmAirSystem) {
            return ((IWarmAirSystem) primary).getControls().contains(controlType) == false;
        } else if (isRoomHeaterControl) {
            if (tech.getSecondarySpaceHeater() != null) {
                return !tech.getSecondarySpaceHeater().isThermostatFitted();
            }
        }

        return false;
    }

    @Override
    public boolean isAlwaysSuitable() {
        return false;
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }
}
