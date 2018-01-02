package uk.org.cse.nhm.simulation.measure.structure;

import javax.inject.Inject;

import org.pojomatic.annotations.AutoProperty;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.structure.IMutableWall;
import uk.org.cse.nhm.hom.structure.IStorey;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulation.measure.AbstractMeasure;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

/**
 * AlterWallHeatLossMeasure.
 *
 * @author richardt
 * @version $Id: cse-eclipse-codetemplates.xml 94 2010-09-30 15:39:21Z richardt $
 * @since 3.2.0
 */
@AutoProperty
public class AlterWallHeatLossMeasure extends AbstractMeasure implements IModifier<StructureModel> {
    private final IDimension<StructureModel> structureDimension;
    private final double uValue;

    @Inject
    public AlterWallHeatLossMeasure(
    		final IDimension<StructureModel> structureDimension,
    		@Assisted final double uValue) {
        this.structureDimension = structureDimension;
        this.uValue = uValue;
    }
    
    /**
     * @return
     * @see uk.org.cse.nhm.simulator.state.IStateChangeSource#getSourceType()
     */
    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }

    @Override
    public boolean modify(final StructureModel alteration) {
    	 for (final IStorey story : alteration.getStoreys()){
             for(final IMutableWall wall : story.getWalls()){
                 wall.setUValue(uValue);
             }
         }
    	return true;
    }
    
    /**
     * @param components
     * @return
     * @throws NHMException
     * @see uk.org.cse.nhm.simulator.measure.IComponentsAction#apply(IStateChangeSource, IEvaluationContext, uk.org.cse.nhm.simulator.state.ISettableComponents, ILets)
     */
    @Override
    public boolean doApply(final ISettableComponentsScope components, final ILets lets) throws NHMException {
        components.modify(structureDimension, this);
        return true;        
    }

    /**
     * @param components
     * @return
     * @see uk.org.cse.nhm.simulator.measure.IComponentsAction#isSuitable(IEvaluationContext, uk.org.cse.nhm.simulator.state.IComponents)
     */
    @Override
    public boolean isSuitable(final IComponentsScope components, final ILets lets) {
        return true;
    }
    
    @Override
	public boolean isAlwaysSuitable() {
		return true;
	}
	
}
