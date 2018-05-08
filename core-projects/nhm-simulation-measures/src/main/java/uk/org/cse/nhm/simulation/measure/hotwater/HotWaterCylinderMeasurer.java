package uk.org.cse.nhm.simulation.measure.hotwater;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.simulation.measure.AbstractMeasure;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.measure.Units;
import uk.org.cse.nhm.simulator.measure.impl.TechnologyInstallationDetails;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.Payment;

/**
 * HotWaterCylinderMeasure.
 *
 * @author richardTiffin
 */
public abstract class HotWaterCylinderMeasurer extends AbstractMeasure {

    private final IDimension<ITechnologyModel> technologyDimension;
    private final IComponentsFunction<Number> capitalCostFunction;

    public HotWaterCylinderMeasurer(final IDimension<ITechnologyModel> technologyDimension,
            IComponentsFunction<Number> capitalCostFunction) {
        this.technologyDimension = technologyDimension;
        this.capitalCostFunction = capitalCostFunction;
    }

    /**
     * @param scope
     * @param lets
     * @return
     * @throws NHMException
     * @see
     * uk.org.cse.nhm.simulator.scope.IComponentsAction#apply(uk.org.cse.nhm.simulator.scope.ISettableComponentsScope,
     * uk.org.cse.nhm.simulator.let.ILets)
     */
    @Override
    public boolean doApply(ISettableComponentsScope components, ILets lets) throws NHMException {
        final double capex = getCapitalCostFunction().compute(components, lets).doubleValue();
        final boolean didAnything = doApply(components);

        if (didAnything) {
            components.addNote(new TechnologyInstallationDetails(this, getTechnologyType(), 1, Units.Units, capex, 0d));
            components.addTransaction(Payment.capexToMarket(capex));

            return true;
        }
        return false;
    }

    public abstract boolean doApply(ISettableComponentsScope components) throws NHMException;

    boolean hasHotWaterCylinder(ITechnologyModel structure) {
        if ((structure.getCentralWaterSystem() != null)
                && (structure.getCentralWaterSystem() instanceof ICentralWaterSystem)) {
            ICentralWaterSystem hotWaterSystem = structure.getCentralWaterSystem();

            if (hotWaterSystem.getStore() != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return @see
     * uk.org.cse.nhm.simulator.scope.IComponentsAction#isAlwaysSuitable()
     */
    @Override
    public boolean isAlwaysSuitable() {
        return false;
    }

    /**
     * @return @see
     * uk.org.cse.nhm.simulator.state.IStateChangeSource#getSourceType()
     */
    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }

    /**
     * Return the technologyDimension.
     *
     * @return the technologyDimension
     */
    protected IDimension<ITechnologyModel> getTechnologyDimension() {
        return technologyDimension;
    }

    /**
     * Return the capitalCostFunction.
     *
     * @return the capitalCostFunction
     */
    protected IComponentsFunction<Number> getCapitalCostFunction() {
        return capitalCostFunction;
    }

    public abstract TechnologyType getTechnologyType();
}
