package uk.org.cse.nhm.simulator.state.functions.impl.house;

import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.IPrimarySpaceHeater;
import uk.org.cse.nhm.hom.emf.technologies.IRoomHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class MatchHasHeatingControl extends AbstractNamed implements IComponentsFunction<Boolean> {
    private final IDimension<ITechnologyModel> technologies;
    private final HeatingSystemControlType controlType;

    @AssistedInject
    public MatchHasHeatingControl(
                                  final IDimension<ITechnologyModel> technologies,
                                  @Assisted final HeatingSystemControlType controlType) {
        super();
        this.technologies = technologies;
        this.controlType = controlType;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Boolean compute(IComponentsScope scope, ILets lets) {
        ITechnologyModel technologyModel = scope.get(technologies);
        IPrimarySpaceHeater primary = technologyModel.getPrimarySpaceHeater();
        if (primary != null) {
            if (primary.getControls().contains(controlType)) {
                return true;
            }
        }

        IRoomHeater secondarySpaceHeater = technologyModel.getSecondarySpaceHeater();

        if (secondarySpaceHeater != null) {
            return controlType == HeatingSystemControlType.APPLIANCE_THERMOSTAT &&
                secondarySpaceHeater.isThermostatFitted();
        }

        return false;
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return Collections.<IDimension<?>>singleton(technologies);
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return Collections.emptySet();
    }
}
