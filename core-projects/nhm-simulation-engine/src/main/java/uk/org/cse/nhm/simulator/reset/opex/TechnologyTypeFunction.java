package uk.org.cse.nhm.simulator.reset.opex;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.hom.emf.technologies.IBackBoiler;
import uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IOperationalCost;
import uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.IStorageHeater;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.ICPSU;
import uk.org.cse.nhm.hom.emf.technologies.boilers.ICombiBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IInstantaneousCombiBoiler;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.language.definition.action.reset.XTechnologyType.XTechnologyTypeValue;
import uk.org.cse.nhm.logging.logentry.errors.WarningLogEntry;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class TechnologyTypeFunction extends AbstractNamed implements IComponentsFunction<XTechnologyTypeValue> {

    private final ILogEntryHandler log;

    @Inject
    protected TechnologyTypeFunction(final ILogEntryHandler log) {
        this.log = log;
    }

    @Override
    public XTechnologyTypeValue compute(final IComponentsScope scope, final ILets lets) {
        final Optional<IOperationalCost> currentThing = lets.get(ResetOpexAction.CURRENT_OPCOST, IOperationalCost.class);

        if (currentThing.isPresent()) {
            final IOperationalCost op = currentThing.get();
            if (op instanceof IInstantaneousCombiBoiler) {
                return XTechnologyTypeValue.InstantCombi;
            } else if (op instanceof ICombiBoiler) {
                return XTechnologyTypeValue.StorageCombi;
            } else if (op instanceof ICPSU) {
                return XTechnologyTypeValue.CPSU;
            } else if (op instanceof IBackBoiler) {
                return XTechnologyTypeValue.BackBoiler;
            } else if (op instanceof IBoiler) {
                return XTechnologyTypeValue.StandardBoiler;
            } else if (op instanceof ICommunityHeatSource) {
                return XTechnologyTypeValue.DistrictHeat;
            } else if (op instanceof ISolarWaterHeater) {
                return XTechnologyTypeValue.SolarDHW;
            } else if (op instanceof IStorageHeater) {
                return XTechnologyTypeValue.StorageHeater;
            }
        } else {
            log.acceptLogEntry(new WarningLogEntry("Invalid outside of action.reset-opex", ImmutableMap.of("element", this.getIdentifier().getName())));
        }
        return null;
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
