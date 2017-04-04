package uk.org.cse.nhm.simulator.state.functions.impl;

import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableMap;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.errors.WarningLogEntry;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class DefaultPricingFunction implements IComponentsFunction<Number> {
	private final Name measure;
	private final ILogEntryHandler log;
	private boolean warned = false;
	
	@AssistedInject
	DefaultPricingFunction(@Assisted final Name measure, final ILogEntryHandler log) {
		super();
		this.measure = measure;
		this.log = log;
	}

	@Override
	public Name getIdentifier() {
		return measure;
	}

	@Override
	public Double compute(final IComponentsScope scope, final ILets lets) {
		if (!warned) {
			warned = true;
			log.acceptLogEntry(new WarningLogEntry("Using default pricing function.", ImmutableMap.of("measure", this.getIdentifier().getName())));
		}
		return 0d;
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return Collections.emptySet();
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.emptySet();
	}

}
