package uk.org.cse.nhm.simulator.integration.tests.guice;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;

public interface IFunctionAssertion {
	public void evaluate(final String name, final IntegrationTestOutput output,
			final IComponentsScope scope, ILets lets, final double value);
}
