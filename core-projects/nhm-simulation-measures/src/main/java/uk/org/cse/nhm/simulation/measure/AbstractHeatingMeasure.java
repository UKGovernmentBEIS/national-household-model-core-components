package uk.org.cse.nhm.simulation.measure;

import java.util.Set;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.language.builder.action.measure.wetheating.IWetHeatingMeasureFactory;
import uk.org.cse.nhm.language.builder.action.measure.wetheating.WetHeatingMeasure;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.ISizingResult;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.measure.Units;
import uk.org.cse.nhm.simulator.measure.impl.TechnologyInstallationDetails;
import uk.org.cse.nhm.simulator.measure.sizing.ISizingFunction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IComponents;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.Payment;

/**
 * A base class for heating measures which gets injected with the
 * {@link EnergyStateComponent} and uses it to provide utility methods to
 * subclasses around water and space heating demand.
 *
 * @author hinton
 *
 */
public abstract class AbstractHeatingMeasure extends AbstractMeasure {

    /**
     * the sizing function to use, or null if delegate to technology
     */
    private final ISizingFunction sizingFunction;

    /**
     * the capital cost function to use, or null if delegate to technology
     */
    private final IComponentsFunction<Number> capitalCostFunction;

    /**
     * the op cost function to use or null if delegate to technology
     */
    private final IComponentsFunction<Number> operationalCostFunction;

    /**
     * The technology for this measure
     */
    private final TechnologyType technology;
    /**
     * A measure which sort out wet heating installation.
     */
    private final WetHeatingMeasure wetHeatingMeasure;
    /**
     * Used only for get current year below
     */
    private final ITimeDimension time;

    private final IDimension<ITechnologyModel> technologies;

    private final InstallationYearUpdater yearmodifier;

    protected AbstractHeatingMeasure(
            final ITimeDimension time,
            final IDimension<ITechnologyModel> technologies,
            final ITechnologyOperations operations,
            final IWetHeatingMeasureFactory wetHeatingFactory,
            final TechnologyType technology,
            final ISizingFunction sizingFunction2,
            final IComponentsFunction<Number> capitalCostFunction, final IComponentsFunction<Number> operationalCostFunction, final IComponentsFunction<Number> wetHeatingCapex) {
        this.time = time;
        this.technologies = technologies;

        this.technology = technology;
        this.wetHeatingMeasure = wetHeatingFactory.create(wetHeatingCapex);
        this.sizingFunction = sizingFunction2;
        this.capitalCostFunction = capitalCostFunction;
        this.operationalCostFunction = operationalCostFunction;

        this.yearmodifier = new InstallationYearUpdater(operations);
    }

    @Override
    public final boolean doApply(final ISettableComponentsScope components, final ILets lets) throws NHMException {
        final ISizingResult result = sizingFunction.computeSize(components, ILets.EMPTY, Units.KILOWATTS);
        components.addNote(result);

        if (result.isSuitable() && doIsSuitable(components)) {
            final double size = result.getSize();
            final double capex = capitalCostFunction.compute(components, lets).doubleValue();
            final double opex = operationalCostFunction.compute(components, lets).doubleValue();
            final boolean didAnything = doApplyIncludingWetCentralHeating(components, lets, size, capex, opex);
            if (didAnything) {
                components.addNote(new TechnologyInstallationDetails(this, technology, result.getSize(), result.getUnits(), capex, opex));
                components.addTransaction(Payment.capexToMarket(capex));

                yearmodifier.year = components.get(time).get(lets).getYear();

                components.modify(technologies, yearmodifier);

                return true;
            }
        }
        return false;
    }

    static class InstallationYearUpdater implements IModifier<ITechnologyModel> {

        private final ITechnologyOperations operations;
        public int year;

        public InstallationYearUpdater(final ITechnologyOperations operations) {
            super();
            this.operations = operations;
        }

        @Override
        public boolean modify(final ITechnologyModel modifiable) {
            return operations.setInstallationYears(modifiable, year);
        }
    }

    private boolean doApplyIncludingWetCentralHeating(
            final ISettableComponentsScope components, final ILets lets,
            final double size, final double capex, final double opex) {
        if (isCentralHeatingSystemRequired()) {
            return wetHeatingMeasure.applyMayInstallWetHeating(components, lets, new HeatingMeasureApplication(this, size, capex, opex));
        } else {
            return doApply(components, lets, size, capex, opex);
        }
    }

    /**
     * Subclasses should implement this to actually do their job
     *
     * @param components
     * @param lets TODO
     * @param size the size of heating required (kW)
     * @param capex the capital cost (currency)
     * @param opex the operational cost (currency)
     * @return a measure applied result, or absent if can't work for some
     * reason.
     * @throws NHMException
     */
    protected abstract boolean doApply(final ISettableComponentsScope components, final ILets lets, final double size, final double capex, final double opex) throws NHMException;

    @Override
    public final boolean isSuitable(final IComponentsScope components, final ILets lets) {
        final ISizingResult result = sizingFunction.computeSize(components, ILets.EMPTY, Units.KILOWATTS);
        return result.isSuitable() && doIsSuitable(components);
    }

    @Override
    public boolean isAlwaysSuitable() {
        return false;
    }

    /**
     * Subclasses should implement this to test their suitability. This will
     * only be called if the sizing function says a size is available for the
     * house.
     *
     * @param components
     * @return suitability of components.
     */
    protected abstract boolean doIsSuitable(final IComponents components);

    protected abstract Set<HeatingSystemControlType> getHeatingSystemControlTypes();

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }

    protected boolean isCentralHeatingSystemRequired() {
        return true;
    }
}
