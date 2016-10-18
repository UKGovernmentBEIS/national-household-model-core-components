package uk.org.cse.nhm.simulation.measure;

import uk.org.cse.nhm.language.builder.action.measure.wetheating.WetHeatingMeasure;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;

/**
 * This class is intended to decouple {@link WetHeatingMeasure} and {@link AbstractHeatingMeasure}.
 * It has no behaviour, it just hides some information which WetHeatingMeasure shouldn't really have to know about.
 */
public class HeatingMeasureApplication {
	private final AbstractHeatingMeasure measure;
	private final double size;
	private final double capex;
	private final double opex;

	public HeatingMeasureApplication(AbstractHeatingMeasure measure, double size, double capex, double opex) {
		this.measure = measure;
		this.size = size;
		this.capex = capex;
		this.opex = opex;
	}
	
	public boolean apply(ISettableComponentsScope scope, ILets lets) {
		return measure.doApply(scope, lets, size, capex, opex);
	}
}
