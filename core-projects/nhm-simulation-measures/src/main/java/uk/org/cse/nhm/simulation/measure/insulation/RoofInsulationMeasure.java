package uk.org.cse.nhm.simulation.measure.insulation;

import java.util.Set;

import javax.inject.Inject;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.energycalculator.api.types.RoofConstructionType;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * A measure which insulates a loft.
 *
 * pitched roof
 *
 * if (insulation thickness < critical) lots()
 * some();
 *
 * r value by thickness
 *
 * @author hinton
 *
 */
public class RoofInsulationMeasure extends InsulationMeasure {
	private final Set<RoofConstructionType> suitableRoofConstructionTypes;
	private final IDimension<StructureModel> structureDimension;
	private final boolean topup;
	private final Optional<IComponentsFunction<Number>> uValueFunction;
	private final IComponentsFunction<Number> resistanceFunction;
	private final double insulationThickness;

	/**
	 * TODO injecting loft insulation technology, which is not strictly correct.
	 */
	@Inject
	public RoofInsulationMeasure(
			final IDimension<StructureModel> structureDimension,
			@Assisted("thickness") final double insulationThickness,
			@Assisted("rvalue") final IComponentsFunction<Number> resistanceFunction,
			@Assisted("uvalue") final Optional<IComponentsFunction<Number>> uValueFunction,
			@Assisted("capex") final IComponentsFunction<Number> capitalCostFunction,
			@Assisted final Set<RoofConstructionType> suitableRoofConstructionTypes,
			@Assisted final boolean topup
			) {
		super(capitalCostFunction, TechnologyType.loftInsulation());
		this.insulationThickness = insulationThickness;
		this.resistanceFunction = resistanceFunction;
		this.uValueFunction = uValueFunction;
		this.suitableRoofConstructionTypes = suitableRoofConstructionTypes;
		this.structureDimension = structureDimension;
		this.topup = topup;
	}


	static class ResistanceModifier implements IModifier<StructureModel> {
		private final double resistance;
		private final double thickness;


		ResistanceModifier(final double resistance, final double thickness) {
			super();
			this.resistance = resistance;
			this.thickness = thickness;
		}

		@Override
		public boolean modify(final StructureModel modifiable) {
			final double extraInsulation = thickness - modifiable.getRoofInsulationThickness();

			final double totalRvalue = resistance * extraInsulation;

			for (final Storey storey : modifiable.getStoreys()) {
				storey.addCeilingInsulation(totalRvalue);
			}

			modifiable.setRoofInsulationThickness(thickness);
			return true;
		}

	}

	static class UValueModifier implements IModifier<StructureModel> {
		private final double uvalue;
		private final double thickness;

		UValueModifier(final double uvalue, final double thickness) {
			super();
			this.uvalue = uvalue;
			this.thickness = thickness;
		}

		@Override
		public boolean modify(final StructureModel modifiable) {
			for (final Storey s : modifiable.getStoreys()) {
				s.setCeilingUValue(uvalue);
			}

			modifiable.setRoofInsulationThickness(thickness);
			return true;
		}
	}

	/**
	 * @issue 3 : although loft insulation was being installed, it was not being flagged as such and so not counted in reports.
	 */
	@Override
	public boolean apply(final ISettableComponentsScope components, final ILets lets) throws NHMException {
		if (isSuitable(components, lets)) {
			if (uValueFunction.isPresent()) {
				components.modify(structureDimension, new UValueModifier(uValueFunction.get().compute(components, lets).doubleValue(), insulationThickness));
			} else {
				components.modify(structureDimension, new ResistanceModifier(resistanceFunction.compute(components, lets).doubleValue(), insulationThickness));
			}

			final double area = components.get(structureDimension).getExternalRoofArea();

			addCapitalCosts(components, lets, area);

			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isSuitable(final IComponentsScope components, final ILets lets) {
		final StructureModel sm = components.get(structureDimension);
		if (sm.getHasLoft() &&
				suitableRoofConstructionTypes.contains(sm.getRoofConstructionType()) &&
				sm.hasExternalRoof()) {
			if (topup) {
				return sm.getRoofInsulationThickness() < insulationThickness
						&& sm.getExternalRoofArea() > 0;
			} else {
				return sm.getRoofInsulationThickness() == 0
						&& sm.getExternalRoofArea() > 0;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean isAlwaysSuitable() {
		return false;
	}

	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}
}
