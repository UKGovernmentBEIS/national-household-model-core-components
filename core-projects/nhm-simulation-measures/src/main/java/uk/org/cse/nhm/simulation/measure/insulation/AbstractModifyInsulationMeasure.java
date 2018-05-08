package uk.org.cse.nhm.simulation.measure.insulation;

import javax.inject.Inject;

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
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * Provides the base for specialisations of the action to set insulation (and
 * any additional parameters depending on the type of insulation)
 *
 * @since 4.2.0
 */
public abstract class AbstractModifyInsulationMeasure extends AbstractMeasure {

    final IDimension<StructureModel> structureDimension;
    private final IComponentsFunction<? extends Number> insulationThickness;

    @Inject
    public AbstractModifyInsulationMeasure(
            final IDimension<StructureModel> structureDimension,
            @Assisted("thickness") final IComponentsFunction<? extends Number> insulationThickness
    ) {
        this.structureDimension = structureDimension;
        this.insulationThickness = insulationThickness;
    }

    @Override
    public boolean doApply(final ISettableComponentsScope scope, final ILets lets)
            throws NHMException {
        scope.modify(structureDimension, modifier(scope, lets));
        return true;
    }

    protected abstract IModifier<StructureModel> modifier(final ISettableComponentsScope scope, final ILets lets);

    /**
     * By default, everything is suitable for insulation thickness modifications
     *
     * @see
     * uk.org.cse.nhm.simulator.scope.IComponentsAction#isSuitable(uk.org.cse.nhm.simulator.scope.IComponentsScope,
     * ILets)
     */
    @Override
    public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
        return true;
    }

    @Override
    public boolean isAlwaysSuitable() {
        return true;
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }

    protected double getInsulationThickness(final IComponentsScope scope, final ILets lets) {
        return insulationThickness.compute(scope, lets).doubleValue();
    }

    /*	@Override
	public boolean modify(final StructureModel modifiable) {
		// Could also pass in the r-value and/or the u-value for additional overrides
		switch(insulationType) {
		case FLOOR:
			modifiable.setFloorInsulationThickness(insulationThickness);
			return true;
		case LOFT:
			modifiable.setRoofInsulationThickness(insulationThickness);
			for(final Storey s: modifiable.getStoreys()) {
				s.setCeilingUValue(uValue);
			}
		case WALL:
			for(final Storey s: modifiable.getStoreys()) {
				for(final IMutableWall w: s.getWalls()) {
					Set<WallInsulationType> wallInsulationTypes = w.getWallInsulationTypes(); // This could be passed as an optional
					for(final WallInsulationType t : wallInsulationTypes) {
						w.setWallInsulationThicknessAndAddOrRemoveInsulation(t, insulationThickness);
					}
				}
			}
		default:
			throw new UnsupportedOperationException(String.format("%s is not supported by %s", insulationType.toString(), this.getClass().toString()));
		}
	}
     */
}
