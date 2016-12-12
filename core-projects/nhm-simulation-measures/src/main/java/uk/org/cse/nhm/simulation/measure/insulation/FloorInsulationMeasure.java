package uk.org.cse.nhm.simulation.measure.insulation;

import java.util.EnumSet;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.energycalculator.api.types.AreaType;
import uk.org.cse.nhm.energycalculator.api.types.FloorConstructionType;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class FloorInsulationMeasure extends InsulationMeasure {
	final IDimension<StructureModel> structureDimension;
	final Optional<IComponentsFunction<Number>> resistance;
	final Optional<IComponentsFunction<Number>> uValue;
	final double thickness;
	final boolean isSolidFloor;

	@AssistedInject
	public FloorInsulationMeasure(final IDimension<StructureModel> structureDimension,
								  @Assisted("capex") final IComponentsFunction<Number> capex,
								  @Assisted("resistance") final Optional<IComponentsFunction<Number>>resistance,
								  @Assisted("uvalue") final Optional<IComponentsFunction<Number>> uvalue,
								  @Assisted final double thickness,
								  @Assisted final boolean isSolidFloor) {
		super(capex, TechnologyType.floorInsulation());
		this.resistance = resistance;
		this.uValue = uvalue;
		this.thickness = thickness;
		this.structureDimension = structureDimension;
		this.isSolidFloor = isSolidFloor;
	}

	@Override
	public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
		final StructureModel sm = scope.get(structureDimension);

		return (sm.getGroundFloorConstructionType() == FloorConstructionType.Solid) == isSolidFloor;
	}

	@Override
	public boolean isAlwaysSuitable() {
		return false;
	}

	private void addCapitalCosts(final ISettableComponentsScope scope, final ILets lets) {
		final AreaAccumulator acc = new AreaAccumulator(EnumSet.of(AreaType.ExternalFloor));
		scope.get(structureDimension).accept(acc);

		addCapitalCosts(scope, lets, acc.getTotalArea());
	}


	@Override
   	public boolean apply(final ISettableComponentsScope scope, final ILets lets) {
		if (isSuitable(scope, lets)) {
			if (this.uValue.isPresent()) {
				final double uvalue = this.uValue.get().compute(scope, lets).doubleValue();
				scope.modify(structureDimension,
							 new IBranch.IModifier<StructureModel>() {
								 @Override
								 public boolean modify(final StructureModel sm) {
									 for (final Storey storey : sm.getStoreys()) {
										 storey.setFloorUValue(uvalue);
									 }

									 sm.setFloorInsulationThickness(thickness);

									 return true;
								 }
							 });
				addCapitalCosts(scope, lets);
			} else if (this.resistance.isPresent()) {
				final double resistance = this.resistance.get().compute(scope, lets).doubleValue() * thickness;
				scope.modify(structureDimension,
							 new IBranch.IModifier<StructureModel>() {
								 @Override
								 public boolean modify(final StructureModel sm) {
									 for (final Storey storey : sm.getStoreys()) {
										 final double newUValue =
											 1/(resistance +
												(1/storey.getFloorUValue()));
										 storey.setFloorUValue(newUValue);
									 }

									 sm.setFloorInsulationThickness(thickness);

									 return true;
								 }
							 });
				addCapitalCosts(scope, lets);
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}
}
