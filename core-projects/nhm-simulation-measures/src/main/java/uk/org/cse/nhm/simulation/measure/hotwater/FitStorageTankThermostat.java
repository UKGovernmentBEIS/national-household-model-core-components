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
 * FitStorageTankThermostat.
 *
 * @author richardTiffin
 */
public class FitStorageTankThermostat extends HotWaterCylinderMeasurer {

    @AssistedInject
    public FitStorageTankThermostat(
            final IDimension<ITechnologyModel> technologyDimension,
            @Assisted("capex") final IComponentsFunction<Number> capitalCostFunction) {
        super(technologyDimension, capitalCostFunction);
    }

    /**
     * @param components
     * @param lets
     * @param size
     * @param capex
     * @param opex
     * @return
     * @throws NHMException
     * @see uk.org.cse.nhm.simulation.measure.AbstractHeatingMeasure#doApply(uk.org.cse.nhm.simulator.scope.ISettableComponentsScope,
     *      uk.org.cse.nhm.simulator.let.ILets, double, double, double)
     */
    public boolean doApply(ISettableComponentsScope components)
            throws NHMException {
        components.modify(getTechnologyDimension(), new Modifier());
        return true;
    }

    private class Modifier implements IModifier<ITechnologyModel> {
        /**
         * @param modifiable
         * @return
         * @see uk.org.cse.nhm.simulator.state.IBranch.IModifier#modify(java.lang.Object)
         */
        @Override
        public boolean modify(ITechnologyModel modifiable) {

            final ICentralWaterSystem centralWaterSystem = modifiable.getCentralWaterSystem();
            final IWaterTank waterTank = (centralWaterSystem != null) ? centralWaterSystem.getStore() : null;

            if ((centralWaterSystem != null) && (centralWaterSystem != null)) {
                waterTank.setThermostatFitted(true);
                return true;
            }

            return false;
        }
    }

    /**
     * @param scope
     * @param lets
     * @return
     * @see uk.org.cse.nhm.simulator.scope.IComponentsAction#isSuitable(uk.org.cse.nhm.simulator.scope.IComponentsScope,
     *      uk.org.cse.nhm.simulator.let.ILets)
     */
    @Override
    public boolean isSuitable(IComponentsScope components, ILets lets) {
        final ITechnologyModel structure = components.get(getTechnologyDimension());

        if (hasHotWaterCylinder(structure) 
                && ((ICentralWaterSystem)structure.getCentralWaterSystem()).getStore().isThermostatFitted() == false){
                return true;
        }

        return false;
    }

    /**
     * @return
     * @see uk.org.cse.nhm.simulation.measure.hotwater.HotWaterCylinderMeasurer#getTechnologyType()
     */
    @Override
    public TechnologyType getTechnologyType() {
        return TechnologyType.hotWaterCylinderThermostat();
    }
}
