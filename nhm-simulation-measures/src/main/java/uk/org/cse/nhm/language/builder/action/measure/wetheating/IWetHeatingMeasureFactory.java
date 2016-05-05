package uk.org.cse.nhm.language.builder.action.measure.wetheating;

import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public interface IWetHeatingMeasureFactory {
	public WetHeatingMeasure create(final IComponentsFunction<? extends Number> capex);
}
