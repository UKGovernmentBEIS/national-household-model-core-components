package uk.org.cse.nhm.simulation.measure.otherspaceheating;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem;
import uk.org.cse.nhm.hom.emf.util.Efficiency;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.simulation.measure.AbstractMeasure;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.ISizingResult;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.measure.Units;
import uk.org.cse.nhm.simulator.measure.impl.TechnologyInstallationDetails;
import uk.org.cse.nhm.simulator.measure.sizing.ISizingFunction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.Payment;

/**
 * WarmAirMeasure.
 *
 * @author richardTiffin
 */
public class WarmAirMeasure extends AbstractMeasure {

    private final IDimension<ITechnologyModel> technologyDimension;
    private final ITechnologyOperations operations;
    
    //Input Vars
    private final FuelType fuelType;
    private final ISizingFunction sizingFunction;
    private final IComponentsFunction<Number> capitalCostFunction;
    private final IComponentsFunction<Number> operationalCostFunction;
    private final IComponentsFunction<Number> efficiency;
    
    @AssistedInject
    public WarmAirMeasure(
            final ITechnologyOperations operations,
            final IDimension<ITechnologyModel> technologyDimension,
            @Assisted final FuelType fuelType,
            @Assisted final ISizingFunction sizingFunction,
            @Assisted("capex") final IComponentsFunction<Number> capitalCostFunction,
            @Assisted("opex") final IComponentsFunction<Number> operationalCostFunction,
            @Assisted("efficiency") final IComponentsFunction<Number> efficiency) {
        this.operations = operations;
        this.technologyDimension = technologyDimension;
    
        this.fuelType = fuelType;
        this.sizingFunction = sizingFunction;
        this.capitalCostFunction = capitalCostFunction;
        this.operationalCostFunction = operationalCostFunction;
        this.efficiency = efficiency;
    }

    protected class Modifier implements IModifier<ITechnologyModel> {
        private final double efficiency;

        public Modifier(final double efficiency) {
            this.efficiency = efficiency;
        }
        
        /**
         * @param modifiable
         * @return
         * @see uk.org.cse.nhm.simulator.state.IBranch.IModifier#modify(java.lang.Object)
         */
        @Override
        public boolean modify(ITechnologyModel modifiable) {
            final ITechnologiesFactory factory = ITechnologiesFactory.eINSTANCE;

            IWarmAirSystem warmAirSystem = factory.createWarmAirSystem();
            warmAirSystem.setCirculator(factory.createWarmAirCirculator());
            warmAirSystem.setFuelType(operations.getMainHeatingFuel(modifiable));
            warmAirSystem.setEfficiency(Efficiency.fromDouble(efficiency));
            
            operations.replacePrimarySpaceHeater(modifiable, warmAirSystem);

            return true;
        }
    }

    /**
     * @param scope
     * @param lets
     * @return
     * @throws NHMException
     * @see uk.org.cse.nhm.simulator.scope.IComponentsAction#apply(uk.org.cse.nhm.simulator.scope.ISettableComponentsScope,
     *      uk.org.cse.nhm.simulator.let.ILets)
     */
    @Override
    public boolean doApply(final ISettableComponentsScope components, ILets lets) throws NHMException {
        final ISizingResult result = sizingFunction.computeSize(components, ILets.EMPTY, Units.KILOWATTS);
        components.addNote(result);
                
        if(result.isSuitable() && isSuitable(components, lets)){
            final double capex = capitalCostFunction.compute(components, lets).doubleValue();
            final double opex = operationalCostFunction.compute(components, lets).doubleValue();
            
            if(makeItSo(components, lets)){
                components.addNote(new TechnologyInstallationDetails(
                        this, TechnologyType.warmAirHeatingSystem(), result.getSize(), result.getUnits(), capex, opex));
                components.addTransaction(Payment.capexToMarket(capex));
                
                return true;
            }
        }
        
        return false;
    }
    
    protected boolean makeItSo(final ISettableComponentsScope components, final ILets lets){
        components.modify(technologyDimension,
                          new Modifier(getEfficiency(components, lets)));
        return true;
    }

    /**
     * @param scope
     * @param lets
     * @return
     * @see uk.org.cse.nhm.simulator.scope.IComponentsAction#isSuitable(uk.org.cse.nhm.simulator.scope.IComponentsScope,
     *      uk.org.cse.nhm.simulator.let.ILets)
     */
    @Override
    public boolean isSuitable(IComponentsScope scope, ILets lets) {
        return (hasCorrectFuelType() &&
                hasValidWarmAirSystemFitted(scope.get(technologyDimension),
                                            scope,
                                            lets));
    }

    protected boolean hasCorrectFuelType() {
        if (fuelType.equals(FuelType.MAINS_GAS) || fuelType.equals(FuelType.BULK_LPG)
                || fuelType.equals(FuelType.BOTTLED_LPG)) {
            return true;
        }
        return false;
    }

    private double getEfficiency(final IComponentsScope scope, final ILets lets) {
        return this.efficiency.compute(scope, lets).doubleValue();
    }
    
    /**
     * Is valid if a warm air system is already fitted and the efficiency of this is less than the new one being fitted.
     * @param technologyModel
     * @param basicCaseAttributesModel
     * @return
     */
    protected boolean hasValidWarmAirSystemFitted(final ITechnologyModel technologyModel, final IComponentsScope scope, final ILets lets) {
        if (technologyModel.getPrimarySpaceHeater() instanceof IWarmAirSystem) {
            IWarmAirSystem system = (IWarmAirSystem) technologyModel.getPrimarySpaceHeater();
            if (system.getEfficiency().value < getEfficiency(scope, lets)){
                return true;
            }
        }

        return false;
    }

    /**
     * @return
     * @see uk.org.cse.nhm.simulator.scope.IComponentsAction#isAlwaysSuitable()
     */
    @Override
    public boolean isAlwaysSuitable() {
        return false;
    }

    /**
     * @return
     * @see uk.org.cse.nhm.simulator.state.IStateChangeSource#getSourceType()
     */
    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }
}
