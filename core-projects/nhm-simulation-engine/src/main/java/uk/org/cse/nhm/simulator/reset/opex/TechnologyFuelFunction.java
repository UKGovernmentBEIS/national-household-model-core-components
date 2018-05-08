package uk.org.cse.nhm.simulator.reset.opex;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.IFuelAndFlue;
import uk.org.cse.nhm.hom.emf.technologies.IOperationalCost;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.errors.WarningLogEntry;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class TechnologyFuelFunction extends AbstractNamed implements IComponentsFunction<FuelType> {

    private final ILogEntryHandler log;

    @Inject
    protected TechnologyFuelFunction(final ILogEntryHandler log) {
        this.log = log;
    }

    @Override
    public FuelType compute(final IComponentsScope scope, final ILets lets) {
        final Optional<IOperationalCost> currentThing = lets.get(ResetOpexAction.CURRENT_OPCOST, IOperationalCost.class);

        if (currentThing.isPresent()) {
            final IOperationalCost op = currentThing.get();
            if (op instanceof IFuelAndFlue) {
                return ((IFuelAndFlue) op).getFuel();
            } else {
                return null;
            }
        } else {
            log.acceptLogEntry(new WarningLogEntry("Used element outside of action.reset-opex",
                    ImmutableMap.of("element", this.getIdentifier().getName())));
            return null;
        }
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return Collections.emptySet();
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return Collections.emptySet();
    }

}
