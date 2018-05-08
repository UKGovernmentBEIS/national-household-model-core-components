package uk.org.cse.nhm.simulation.measure.insulation;

import java.util.EnumSet;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.energycalculator.api.types.AreaType;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulation.measure.AbstractMeasure;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.measure.Units;
import uk.org.cse.nhm.simulator.measure.impl.SizingResult;
import uk.org.cse.nhm.simulator.measure.impl.TechnologyInstallationDetails;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.Payment;

/**
 * Measure for installing a certain proportion of draught proofing
 * 
 * @since 4.2.0
 */
public class DraughtProofingMeasure extends AbstractMeasure implements IModifier<StructureModel>  {
	private final IDimension<StructureModel> structureDimension;
	private final IComponentsFunction<Number> capitalCostFunction;
	/**
	 * The proportion of openings to install with draught stripping
	 */
	private final double proportion;
	private double quantityInstalled = 0;
	
	@AssistedInject
	public DraughtProofingMeasure(
			final IDimension<StructureModel> structureDimension,
			@Assisted("capex") final IComponentsFunction<Number> capitalCostFunction,
			@Assisted("proportion") final double proportion
			) {
		this.structureDimension = structureDimension;
		this.capitalCostFunction = capitalCostFunction;
		this.proportion = proportion;
	}
	
	@Override
	public boolean doApply(final ISettableComponentsScope scope, final ILets lets)
			throws NHMException {
        this.quantityInstalled = 0d;
        scope.modify(structureDimension, this);
        addCapitalCosts(scope, lets, this.quantityInstalled);

        return true;
	}

	/**
	 * @see uk.org.cse.nhm.simulator.scope.IComponentsAction#isSuitable(uk.org.cse.nhm.simulator.scope.IComponentsScope, ILets)
	 * @Assumption We assume that households are suitable for draught proofing if they current have a smaller proportion of draught proofing than we are attempting to install.
	 */
	@Override
	public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
		final StructureModel structure = scope.get(structureDimension);
		return structure.getDraughtStrippedProportion() < proportion;
	}
	
	@Override
	public boolean isAlwaysSuitable() {
		return false;
	}

	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}

	@Override
	public boolean modify(final StructureModel modifiable) {
		// TODO: Get the area of openings to properly calculate the quantity installed and thus the capex
		final double areaOfOpenings = getAreaOfOpenings(modifiable);
		final double existingStrippedProportion = modifiable.getDraughtStrippedProportion();
		this.quantityInstalled += (proportion-existingStrippedProportion) * areaOfOpenings;
		modifiable.setDraughtStrippedProportion(proportion);
		return true;
	}

	private double getAreaOfOpenings(final StructureModel modifiable) {
		final AreaAccumulator a = new AreaAccumulator(EnumSet.of(
				AreaType.DoorGlazed, AreaType.DoorSolid,
				AreaType.GlazingWood, AreaType.GlazingMetal, AreaType.GlazingUPVC));
		modifiable.accept(a);
		return a.getTotalArea();
	}

	/**
	 * Adds to the capex for this measure. Assumes no opex cost.
	 * 
	 * @since 4.2.0
	 * @param components
	 * @param lets TODO
	 * @param quantityInstalled
	 * @Assumption Operational expenditure of installation measures is zero.
	 */
	protected void addCapitalCosts(final ISettableComponentsScope components,
			final ILets lets, final double quantityInstalled) {
		final double opex = 0;
		components.addNote(SizingResult.suitable(quantityInstalled,
				Units.SQUARE_METRES));
		final double capex = capitalCostFunction.compute(components, lets).doubleValue();
		components.addNote(new TechnologyInstallationDetails(this,
				TechnologyType.draughtProofing(), quantityInstalled, Units.SQUARE_METRES, capex,
				opex));
		components.addTransaction(Payment.capexToMarket(capex));
	}

	/**
	 * Returns the proportion to install for testing purposes.
	 * 
	 * @since 4.2.0
	 * @return
	 */
	protected double getProportion() {
		return proportion;
	}

}
