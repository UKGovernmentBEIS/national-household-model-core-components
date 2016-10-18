package uk.org.cse.nhm.simulator.integration.tests.guice;

import java.util.Set;

import org.joda.time.DateTime;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class FunctionAssertion extends AbstractNamed implements IComponentsFunction<Number> {
	final IComponentsFunction<Number> delegate;
	final String name;
	private final IntegrationTestOutput integrationHandle;
	
	@AssistedInject
	public FunctionAssertion(
			final IntegrationTestOutput integrationHandle,
			@Assisted final IComponentsFunction<Number> delegate, @Assisted final String name) {
		super();
		this.integrationHandle = integrationHandle;
		this.delegate = delegate;
		this.name = name;
	}

	@Override
	public Double compute(final IComponentsScope scope, final ILets lets) {
		try {
			final IFunctionAssertion assertion = integrationHandle.asserts.get(name);
			final double value = delegate.compute(scope, lets).doubleValue();
			if (assertion == null) {
				throw new RuntimeException("Missing assertion for name " + name);
			}
			assertion.evaluate(name, integrationHandle, scope, lets, value);
			return value;
		} catch (final RuntimeException e ) {
			throw new RuntimeException("Error evaluating assertion " + name, e);
		}
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return delegate.getDependencies();
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return delegate.getChangeDates();
	}
}
