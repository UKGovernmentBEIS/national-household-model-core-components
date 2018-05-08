package uk.org.cse.nhm.simulation.measure.hotwater;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * InstallHotWateCylinderInsulation, tops up the insulation for a given
 * hot-water cylinder to a given amount.
 *
 * @author richardTiffin
 */
public class InstallHotWaterCylinderInsulation extends HotWaterCylinderMeasurer {

    private final double maxCurrentFactoryInsulation = 25d;
    private final double maxJacketInsulation = 80d;

    @AssistedInject
    public InstallHotWaterCylinderInsulation(
            final IDimension<ITechnologyModel> technologyDimension,
            @Assisted("capex") final IComponentsFunction<Number> capitalCostFunction) {
        super(technologyDimension, capitalCostFunction);
    }

    /**
     * @param scope
     * @param lets
     * @return
     * @see
     * uk.org.cse.nhm.simulator.scope.IComponentsAction#isSuitable(uk.org.cse.nhm.simulator.scope.IComponentsScope,
     * uk.org.cse.nhm.simulator.let.ILets)
     */
    @Override
    public boolean isSuitable(IComponentsScope scope, ILets lets) {
        final ITechnologyModel structure = scope.get(getTechnologyDimension());

        if (super.hasHotWaterCylinder(structure)) {
            IWaterTank hotWaterCylinder = ((ICentralWaterSystem) structure.getCentralWaterSystem()).getStore();

            //If is factory insulated then use max factory insulation, otherwise use max jacket insulation
            final double maxAllowedThickness
                    = (hotWaterCylinder.isFactoryInsulation() ? getMaxCurrentFactoryInsulation() : getMaxJacketInsulation());

            if (hotWaterCylinder.getInsulation() < maxAllowedThickness) {
                return true;
            }
        }

        return false;
    }

    /**
     * Return the maxCurrentFactoryInsulation.
     *
     * @return the maxCurrentFactoryInsulation
     */
    protected double getMaxCurrentFactoryInsulation() {
        return maxCurrentFactoryInsulation;
    }

    /**
     * Return the maxJacketInsulation.
     *
     * @return the maxJacketInsulation
     */
    protected double getMaxJacketInsulation() {
        return maxJacketInsulation;
    }

    /**
     * @param components
     * @return
     * @throws NHMException
     * @see
     * uk.org.cse.nhm.simulation.measure.hotwater.HotWaterCylinderMeasurer#doApply(uk.org.cse.nhm.simulator.scope.ISettableComponentsScope)
     */
    @Override
    public boolean doApply(ISettableComponentsScope components) throws NHMException {
        components.modify(getTechnologyDimension(), new Modifier());
        return true;
    }

    protected class Modifier implements IModifier<ITechnologyModel> {

        /**
         * @param modifiable
         * @return
         * @see
         * uk.org.cse.nhm.simulator.state.IBranch.IModifier#modify(java.lang.Object)
         */
        @Override
        public boolean modify(ITechnologyModel modifiable) {

            final ICentralWaterSystem centralWaterSystem = modifiable.getCentralWaterSystem();
            final IWaterTank waterTank = (centralWaterSystem != null) ? centralWaterSystem.getStore() : null;

            if ((centralWaterSystem != null) && (waterTank != null)) {
                if (waterTank.isFactoryInsulation()) {
                    waterTank.setInsulation(getMaxCurrentFactoryInsulation());
                } else {
                    waterTank.setInsulation(getMaxJacketInsulation());
                }
                return true;
            }

            return false;
        }
    }

    /**
     * @return @see
     * uk.org.cse.nhm.simulation.measure.hotwater.HotWaterCylinderMeasurer#getTechnologyType()
     */
    @Override
    public TechnologyType getTechnologyType() {
        return TechnologyType.hotWaterCylinderInsulation();
    }
}
