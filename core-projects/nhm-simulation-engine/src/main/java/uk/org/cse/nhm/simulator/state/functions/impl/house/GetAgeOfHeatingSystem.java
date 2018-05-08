package uk.org.cse.nhm.simulator.state.functions.impl.house;

import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.Period;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;

import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.IHasInstallationYear;
import uk.org.cse.nhm.hom.emf.technologies.IHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IPrimarySpaceHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.errors.WarningLogEntry;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class GetAgeOfHeatingSystem extends AbstractNamed implements IComponentsFunction<Double> {

    private final IDimension<BasicCaseAttributes> basicAttributes;
    final private IDimension<ITechnologyModel> technologies;
    final private ITimeDimension time;
    final private ILogEntryHandler handler;

    @Inject
    public GetAgeOfHeatingSystem(final IDimension<ITechnologyModel> technologies, final ITimeDimension time,
            final ILogEntryHandler handler, final IDimension<BasicCaseAttributes> basicAttributes) {
        this.technologies = technologies;
        this.time = time;
        this.handler = handler;
        this.basicAttributes = basicAttributes;
    }

    @Override
    public Double compute(final IComponentsScope scope, final ILets lets) {
        final ITechnologyModel techModel = scope.get(technologies);

        return getHeatSourceAge(techModel, scope.get(time).get(lets), scope);
    }

    /**
     * Returns the age of the Heat Source
     *
     * @param technologyModel
     * @param simYear
     * @return
     */
    protected double getHeatSourceAge(final ITechnologyModel technologyModel, final DateTime simYear, final IComponentsScope scope) {
        final IPrimarySpaceHeater primarySpaceHeater = technologyModel.getPrimarySpaceHeater();

        IHasInstallationYear thing = null;

        if (primarySpaceHeater instanceof ICentralHeatingSystem) {
            final ICentralHeatingSystem ch = (ICentralHeatingSystem) primarySpaceHeater;
            final IHeatSource source = ch.getHeatSource();
            if (source instanceof IHasInstallationYear) {
                thing = (IHasInstallationYear) source;
            }
        } else if (primarySpaceHeater instanceof IHasInstallationYear) {
            thing = (IHasInstallationYear) primarySpaceHeater;
        }

        if (thing != null && thing.isSetInstallationYear()) {
            final int installationYear = thing.getInstallationYear();
            if (installationYear > 0) {
                return new Period(new DateTime(installationYear, 1, 1, 1, 1), simYear).getYears();
            }
        }

        handler.acceptLogEntry(new WarningLogEntry(
                "Could not get installation year for heat source.",
                ImmutableMap.of("aacode", scope.get(basicAttributes).getAacode())));

        return 0;
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return ImmutableSet.<IDimension<?>>builder()
                .add(technologies, time)
                .build();
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return Collections.emptySet();
    }
}
