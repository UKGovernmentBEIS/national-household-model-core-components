package uk.org.cse.nhm.simulation.measure.adjustment;

import java.util.Iterator;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.AdjusterType;
import uk.org.cse.nhm.hom.emf.technologies.IEnergyUseAdjuster;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.language.definition.action.measure.adjust.XSetAdjustmentTerms;
import uk.org.cse.nhm.simulation.measure.AbstractMeasure;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * SetAdjustmentTermsMeasure.
 *
 * @author trickyBytes
 */
public class SetAdjustmentTermsMeasure extends AbstractMeasure {
    private final IDimension<ITechnologyModel> technologies;
    private final IComponentsFunction<Number> constantTerm;
    private final IComponentsFunction<Number> linearFactor;
    private final AdjusterType adjusterType;
    
    @AssistedInject
    public SetAdjustmentTermsMeasure(
            final IDimension<ITechnologyModel> technologies,
            @Assisted(XSetAdjustmentTerms.P.constantTerm) final IComponentsFunction<Number> constantTerm,
            @Assisted(XSetAdjustmentTerms.P.linearFactor) final IComponentsFunction<Number> linearFactor,
            @Assisted final AdjusterType adjusterType){
        this.technologies = technologies;
        this.constantTerm = constantTerm;
        this.linearFactor = linearFactor;
        this.adjusterType = adjusterType;
    }
    
    /**
     * @param scope
     * @param lets
     * @return
     * @throws NHMException
     * @see uk.org.cse.nhm.simulator.scope.IComponentsAction#apply(uk.org.cse.nhm.simulator.scope.ISettableComponentsScope, uk.org.cse.nhm.simulator.let.ILets)
     */
    @Override
    public boolean apply(ISettableComponentsScope scope, ILets lets) throws NHMException {
        if(isSuitable(scope, lets)){
            scope.modify(technologies, new IModifier<ITechnologyModel>() {
                @Override
                public boolean modify(ITechnologyModel modifiable) {
                    double constantTermActual = constantTerm.compute(scope, lets).doubleValue();
                    double linearFactorActual = linearFactor.compute(scope, lets).doubleValue();
                    
                    final Iterator<IEnergyUseAdjuster> it = modifiable.getEnergyUseAdjusters().iterator();
                    while(it.hasNext()){
                        if(it.next().getAdjustmentType().equals(adjusterType)){
                            it.remove();
                        }
                    }
                   
                   IEnergyUseAdjuster adjuster = ITechnologiesFactory.eINSTANCE.createEnergyUseAdjuster();
                   adjuster.setAdjustmentType(adjusterType);
                   adjuster.setConstantTerm(constantTermActual);
                   adjuster.setLinearFactor(linearFactorActual);
                   
                   modifiable.getEnergyUseAdjusters().add(adjuster);
                   
                   return true;
                }
            });
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param scope
     * @param lets
     * @return
     * @see uk.org.cse.nhm.simulator.scope.IComponentsAction#isSuitable(uk.org.cse.nhm.simulator.scope.IComponentsScope, uk.org.cse.nhm.simulator.let.ILets)
     */
    @Override
    public boolean isSuitable(IComponentsScope scope, ILets lets) {
        return true;
    }

    /**
     * @return
     * @see uk.org.cse.nhm.simulator.scope.IComponentsAction#isAlwaysSuitable()
     */
    @Override
    public boolean isAlwaysSuitable() {
        return true;
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
