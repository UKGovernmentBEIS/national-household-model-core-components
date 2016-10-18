package uk.org.cse.nhm.simulator.integration.tests.guice;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.state.functions.IAggregationFunction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;


public interface ITestingFactory {
	public MeasureProbe createProbe(final String name);
	public ScenarioAssertion createAssertion(
			@Assisted String name,
			@Assisted int direction,
			@Assisted double bound, 
			@Assisted boolean continuous,
			@Assisted IDwellingGroup group,
			@Assisted IAggregationFunction function
			);
	
	public FunctionAssertion createFunctionAssertion(
			@Assisted String name,
			@Assisted IComponentsFunction<Number> delegate);
}
