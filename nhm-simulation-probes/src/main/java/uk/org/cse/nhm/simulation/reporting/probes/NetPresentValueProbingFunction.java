package uk.org.cse.nhm.simulation.reporting.probes;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.language.definition.reporting.XProbedNetPresentValue;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.impl.num.INetPresentValueAnnotation;

public class NetPresentValueProbingFunction extends AbstractNamed implements IProbingFunction {
	@Override
	public Map<String, Object> compute(final IComponentsScope scope, final ILets lets) {
		final Optional<INetPresentValueAnnotation> values = 
				scope.getNearestNote(INetPresentValueAnnotation.class);
		
		final Map<String, Object> result = new HashMap<String, Object>();
		
		if (!values.isPresent()) {
			result.put(XProbedNetPresentValue.FIELD_NPV, null);
			result.put(XProbedNetPresentValue.FIELD_CAPEX, null);
			result.put(XProbedNetPresentValue.FUTURE, null);
			result.put(XProbedNetPresentValue.DISCOUNTED_FUTURE, null);
		} else {
			final INetPresentValueAnnotation a = values.get();
			result.put(XProbedNetPresentValue.FIELD_NPV, a.getNpv());
			result.put(XProbedNetPresentValue.FIELD_CAPEX, a.getCapex());
			result.put(XProbedNetPresentValue.FUTURE, a.getUndiscountedFuture());
			result.put(XProbedNetPresentValue.DISCOUNTED_FUTURE, a.getDiscountedFuture());
		}
		
		return result;
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return Collections.emptySet();
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.emptySet();
	}

	@Override
	public ImmutableSet<String> getHeaders() {
		return ImmutableSet.of(
				XProbedNetPresentValue.FIELD_NPV, 
				XProbedNetPresentValue.FIELD_CAPEX, 
				XProbedNetPresentValue.FUTURE,
				XProbedNetPresentValue.DISCOUNTED_FUTURE);
	}
}
