package uk.org.cse.nhm.housepropertystore;

import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableMap;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.hom.housepropertystore.IHouseProperties;
import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.errors.WarningLogEntry;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class HousePropertyNumber extends AbstractNamed implements IComponentsFunction<Number> {

	private final IDimension<IHouseProperties> constants;
	private final String name;
	private final ILogEntryHandler log;
	private final IDimension<BasicCaseAttributes> basic;

	@AssistedInject
	public HousePropertyNumber(@Assisted final String name,
			final IDimension<IHouseProperties> constants,
			final IDimension<BasicCaseAttributes> basic,
			final ILogEntryHandler log) {
		this.name = name;
		this.constants = constants;
		this.basic = basic;
		this.log = log;
	}

	@Override
	public Number compute(final IComponentsScope scope, final ILets lets) {
		final String var = scope.get(constants).get(name);
		
		try {
			return Double.parseDouble(var);
			
		} catch (final NumberFormatException e) {
			log.acceptLogEntry(new WarningLogEntry(
					"Static property was not a number for this house. Using 0 instead.", 
					ImmutableMap.of(
							"aacode", scope.get(basic).getAacode(),
							"property", name,
							"value", var)));			
			
			return 0d;
		} catch (final NullPointerException e) {
			log.acceptLogEntry(new WarningLogEntry(
					"Static property was not a number for this house. Using 0 instead.", 
					ImmutableMap.of(
							"aacode", scope.get(basic).getAacode(),
							"property", name,
							"value", "")));
			
			return 0d;
		}
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return Collections.<IDimension<?>>singleton(constants);
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.emptySet();
	}
}
