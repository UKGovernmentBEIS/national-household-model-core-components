package uk.org.cse.nhm.simulator.sequence;

import org.joda.time.DateTime;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

public interface ISequenceSpecialAction {
	ILets reallyApply(final ISettableComponentsScope scope, final ILets lets);
	
	ILets reallyApply(final IComponentsScope scope, final ILets lets);

	Iterable<? extends DateTime> getChangeDates();

	Iterable<? extends IDimension<?>> getDependencies();

	boolean needsIsolation();
}
