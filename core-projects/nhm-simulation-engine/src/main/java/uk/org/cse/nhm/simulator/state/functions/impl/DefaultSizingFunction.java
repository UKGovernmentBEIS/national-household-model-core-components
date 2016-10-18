package uk.org.cse.nhm.simulator.state.functions.impl;

import com.google.common.collect.ImmutableMap;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.errors.WarningLogEntry;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.ISizingResult;
import uk.org.cse.nhm.simulator.measure.Units;
import uk.org.cse.nhm.simulator.measure.impl.SizingResult;
import uk.org.cse.nhm.simulator.measure.sizing.ISizingFunction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;

public class DefaultSizingFunction implements ISizingFunction {
	final ILogEntryHandler log;
	final Name owner;
	boolean warned;
	
	@AssistedInject
	DefaultSizingFunction(final ILogEntryHandler log, @Assisted final Name owner) {
		super();
		this.log = log;
		this.owner = owner;
	}



	@Override
	public ISizingResult computeSize(final IComponentsScope scope, final ILets lets, final Units units) {
		if (!warned) {
			warned = true;
			log.acceptLogEntry(
					new WarningLogEntry("No sizing function was defined so using a size of zero and assuming suitability", ImmutableMap.of("measure", owner.getName()))
					);
		}
		return SizingResult.suitable(0, Units.KILOWATTS);
	}

}
