package uk.org.cse.nhm.simulation.measure.structure;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulation.measure.AbstractMeasure;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

/**
 * Modifies the number of passive vents in a dwelling
 *
 * @author trickyBytes
 */
public class AdjustNumberOfPassiveVentsMeasure extends AbstractMeasure implements IModifier<StructureModel> {
    private final IDimension<StructureModel> structureDimension;
    private final int numOfVents;
    
    @Inject
    public AdjustNumberOfPassiveVentsMeasure(final IDimension<StructureModel> structureDimension,
            @Assisted int numOfVents) {
        this.structureDimension = structureDimension;
        this.numOfVents = numOfVents;
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
            scope.modify(structureDimension, this);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Always suitable
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
    public boolean modify(StructureModel modifiable) {
        modifiable.setPassiveVents(modifiable.getPassiveVents() + numOfVents);
        return true;
    }
}
