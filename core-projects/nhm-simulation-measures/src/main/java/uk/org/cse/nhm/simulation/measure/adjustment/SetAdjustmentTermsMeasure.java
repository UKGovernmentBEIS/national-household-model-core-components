package uk.org.cse.nhm.simulation.measure.adjustment;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.language.definition.action.measure.adjust.XSetAdjustmentTerms.XAdjustmentType;
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
public class SetAdjustmentTermsMeasure extends AbstractMeasure implements IModifier<ITechnologyModel> {
    private final IDimension<ITechnologyModel> technologies;
    private final IComponentsFunction<Number> constantTerm;
    private final IComponentsFunction<Number> linearFactor;
    private final AdjustmentType adjustmentType;
    
    public enum AdjustmentType {
        Appliances, Cooking
    }
    
    @AssistedInject
    public SetAdjustmentTermsMeasure(
            final IDimension<ITechnologyModel> technologies,
            @Assisted("adjustment-type") final AdjustmentType adjustmentType,
            @Assisted("constsant-term")final IComponentsFunction<Number> constantTerm,
            @Assisted("linear-factor") final IComponentsFunction<Number> linearFactor) {
        this.technologies = technologies;
        this.constantTerm = constantTerm;
        this.linearFactor = linearFactor;
        this.adjustmentType = adjustmentType;
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
        return true;
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

    /**
     * @param modifiable
     * @return
     * @see uk.org.cse.nhm.simulator.state.IBranch.IModifier#modify(java.lang.Object)
     */
    @Override
    public boolean modify(ITechnologyModel modifiable) {
        return true;
    }
}
