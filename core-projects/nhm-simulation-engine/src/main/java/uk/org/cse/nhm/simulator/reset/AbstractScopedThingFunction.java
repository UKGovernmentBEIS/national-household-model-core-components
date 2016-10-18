package uk.org.cse.nhm.simulator.reset;

import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.errors.WarningLogEntry;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public abstract class AbstractScopedThingFunction<T, Q> extends AbstractNamed implements IComponentsFunction<T> {
	/**
	 * Although we aren't going to use the dimension, we need to have it around
	 * because it is strictly a dependency.
	 */
	private final IDimension<StructureModel> structureDimension;
	private final ILogEntryHandler log;
	private final Object thingKey;
	private final Class<Q> thingClass;

	protected AbstractScopedThingFunction(
			final Object thingKey,
			final Class<Q> clazz,
			final ILogEntryHandler log,
			final IDimension<StructureModel> structureDimension) {
		this.thingKey = thingKey;
		this.structureDimension = structureDimension;
		this.log = log;
		this.thingClass = clazz;
	}

	protected abstract T doFail();
	protected abstract T doCompute(final Q wall);

	protected <R> Optional<R> getAndWarn(final ILets lets, final Object key, final Class<R> thingType) {
		final Optional<R> thing = lets.get(key, thingType);
		
		if (!thing.isPresent()) {
			log.acceptLogEntry(new WarningLogEntry(
					"Element used in an incorrect location (for example, using a wall function outside a per-wall action)",
					ImmutableMap.of("element", this.getIdentifier().getName())));
		}	
				
		return thing;
	}
	
	@Override
	public T compute(final IComponentsScope scope, final ILets lets) {
		final Optional<Q> wall = 
				getAndWarn(lets, thingKey, thingClass);

		if (wall.isPresent()) {
			return doCompute(wall.get());
		} else {			
			return doFail();
		}
	}
	
	@Override
	public Set<IDimension<?>> getDependencies() {
		return ImmutableSet.<IDimension<?>>of(structureDimension);
	}
	
	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.<DateTime>emptySet();
	}
}
