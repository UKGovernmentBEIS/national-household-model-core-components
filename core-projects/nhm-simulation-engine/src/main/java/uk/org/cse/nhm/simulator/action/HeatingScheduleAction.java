package uk.org.cse.nhm.simulator.action;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.energycalculator.api.IHeatingSchedule;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.behaviour.IHeatingBehaviour;

public class HeatingScheduleAction extends AbstractHeatingAction {

    private final IHeatingSchedule schedule;

    @Inject
    public HeatingScheduleAction(
            final IDimension<IHeatingBehaviour> heatingDimension,
            @Assisted final IHeatingSchedule schedule) {
        super(heatingDimension);
        this.schedule = schedule;
    }

    @Override
    protected void doApply(final ISettableComponentsScope scope, final ILets lets) {
        scope.modify(heatingDimension, new IModifier<IHeatingBehaviour>() {

            @Override
            public boolean modify(final IHeatingBehaviour current) {
                current.setHeatingSchedule(schedule);
                return true;
            }
        });
    }
}
