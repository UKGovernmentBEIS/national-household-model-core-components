package uk.org.cse.nhm.simulation.measure.insulation;

import uk.org.cse.nhm.simulation.measure.AbstractMeasure;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.measure.Units;
import uk.org.cse.nhm.simulator.measure.impl.SizingResult;
import uk.org.cse.nhm.simulator.measure.impl.TechnologyInstallationDetails;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.Payment;

public abstract class InsulationMeasure extends AbstractMeasure {
	private final IComponentsFunction<Number> capitalCostFunction;
	
	private final TechnologyType technology;
	
	protected InsulationMeasure(
			final IComponentsFunction<Number> capitalCostFunction,
			final TechnologyType technology) {
		this.capitalCostFunction = capitalCostFunction;
		this.technology = technology;
	}

	/**
	 * @param components
	 * @param lets TODO
	 * @param quantityInstalled
	 * @Assumption Operational expenditure of instalation measures is zero.
	 */
	protected void addCapitalCosts(final ISettableComponentsScope components, final ILets lets, final double quantityInstalled) {
		components.addNote(SizingResult.suitable(quantityInstalled, Units.SQUARE_METRES));
		final double opex = 0;
		final double capex = capitalCostFunction.compute(components, lets).doubleValue();
		components.addNote(new TechnologyInstallationDetails(this, technology, quantityInstalled,
															 Units.SQUARE_METRES,
															 capex, opex));
		components.addTransaction(Payment.capexToMarket(capex));
	}
}
