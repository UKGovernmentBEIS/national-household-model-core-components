package uk.org.cse.nhm.simulation.measure.insulation;

import javax.inject.Inject;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class ModifyFloorInsulationMeasure extends AbstractModifyInsulationMeasure {

	protected final Optional<IComponentsFunction<? extends Number>> uValue;

	@Inject
	public ModifyFloorInsulationMeasure(
			final IDimension<StructureModel> structureDimension,
			@Assisted("insulationthickness") final IComponentsFunction<? extends Number> insulationThickness,
			@Assisted("uvalue") final Optional<IComponentsFunction<? extends Number>> uValue
			) {
		super(structureDimension, insulationThickness);
		this.uValue = uValue;
	}
	
	@Override
	protected IModifier<StructureModel> modifier(final ISettableComponentsScope scope, final ILets lets) {
		final double thickness = getInsulationThickness(scope, lets);
		final Number u = uValue.isPresent() ? uValue.get().compute(scope, lets) : null;
		return new IModifier<StructureModel>() {
			@Override
			public boolean modify(final StructureModel modifiable) {
				boolean modified = false;
				if(modifiable.getFloorInsulationThickness() != thickness) {
					modifiable.setFloorInsulationThickness(thickness);
					modified = true;
				}
				if(u != null) {
					for(final Storey s: modifiable.getStoreys()) {
						s.setFloorUValue(u.doubleValue());
					}
					modified = true;
				}
				return modified;
			}
		};
	}
}
