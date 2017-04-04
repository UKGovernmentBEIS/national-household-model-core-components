package uk.org.cse.nhm.simulation.reporting.extension;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.Period;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSortedSet;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.FromScope;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.adapt.impl.ReflectingAdapter;
import uk.org.cse.nhm.language.definition.reporting.aggregate.XAggregateReport;
import uk.org.cse.nhm.language.definition.reporting.aggregate.XCaseDivision;
import uk.org.cse.nhm.language.definition.reporting.aggregate.XCrossDivision;
import uk.org.cse.nhm.language.definition.reporting.modes.XDateMode;
import uk.org.cse.nhm.language.definition.reporting.modes.XIntegralMode;
import uk.org.cse.nhm.language.definition.reporting.modes.XOnChangeMode;
import uk.org.cse.nhm.language.definition.reporting.modes.XSumMode;
import uk.org.cse.nhm.simulation.reporting.aggregates.AggregateReport;
import uk.org.cse.nhm.simulation.reporting.aggregates.IGroups;
import uk.org.cse.nhm.simulation.reporting.aggregates.modes.DateMode;
import uk.org.cse.nhm.simulation.reporting.aggregates.modes.IReportMode;
import uk.org.cse.nhm.simulation.reporting.aggregates.modes.IntegralMode;
import uk.org.cse.nhm.simulation.reporting.aggregates.modes.OnChangeMode;
import uk.org.cse.nhm.simulation.reporting.aggregates.modes.SumMode;
import uk.org.cse.nhm.simulator.SimulatorConfigurationConstants;
import uk.org.cse.nhm.simulator.factories.IGroupFactory;
import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.groups.impl.Condition;
import uk.org.cse.nhm.simulator.main.Initializable;
import uk.org.cse.nhm.simulator.state.functions.IAggregationFunction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class AggregatesAdapter extends ReflectingAdapter {
	private final IReportingFactory factory;
	private final IGroupFactory groupFactory;
	
	@Inject
	public AggregatesAdapter(final Set<IConverter> delegates, final IReportingFactory factory, final IGroupFactory groupFactory, final Set<IAdapterInterceptor> interceptors) {
		super(delegates, interceptors);
		this.factory = factory;
		this.groupFactory = groupFactory;
	}

	@Adapt(XAggregateReport.class)
	public Initializable buildAggregateReport(
			final Name name,
			@Prop(XAggregateReport.P.mode) final IReportMode mode,
			@Prop(XAggregateReport.P.division) final IGroups division,
			@Prop(XAggregateReport.P.aggregations) final List<IAggregationFunction> functions
			) {
		final AggregateReport ar = factory.createAggregateReport(mode, functions);
		ar.setIdentifier(name);
		
		factory.createReportTrigger(mode, division);
		
		// connect the report up to the division driving it.
		division.addListener(ar);
		
		return Initializable.NOP;
	}
	
	@Adapt(XOnChangeMode.class)
	public IReportMode buildOnChangeMode() {
		return new OnChangeMode();
	}
	
	@Adapt(XSumMode.class)
	public IReportMode buildSumMode() {
		return new SumMode();
	}
	
	@Adapt(XIntegralMode.class)
	public IReportMode buildIntegralMode() {
		return new IntegralMode();
	}
	
	@Adapt(XDateMode.class)
	public IReportMode buildDateMode(
		@FromScope(SimulatorConfigurationConstants.START_DATE) final DateTime scenarioStart,
		@FromScope(SimulatorConfigurationConstants.END_DATE) final DateTime scenarioEnd,
		@Prop(XDateMode.P.REPORTING_DATES) final List<SortedSet<DateTime>> dates
		
			) {
		final SortedSet<DateTime> reducedDates = new TreeSet<DateTime>();
		for(final SortedSet<DateTime> d : dates) {
			reducedDates.addAll(d);
		}
		return new DateMode(reducedDates.tailSet(scenarioStart).headSet(scenarioEnd.plusMillis(1)));
	}
	
	@Adapt(XDateMode.XScenarioStart.class)
	public SortedSet<DateTime> buildStartDate(
			@FromScope(SimulatorConfigurationConstants.START_DATE) final DateTime scenarioStart) {
		return ImmutableSortedSet.of(scenarioStart);
	}
	
	@Adapt(XDateMode.XScenarioEnd.class)
	public SortedSet<DateTime> buildEndDate(
		@FromScope(SimulatorConfigurationConstants.END_DATE) final DateTime scenarioEnd) {
		return ImmutableSortedSet.of(scenarioEnd);
	}
		
	@Adapt(XDateMode.XOn.class)
	public SortedSet<DateTime> buildSingleDate(
		@Prop(XDateMode.XOn.P.ON) final	DateTime on) {
		return ImmutableSortedSet.of(on);
	}
	
	@Adapt(XDateMode.XInterval.class)
	public SortedSet<DateTime> buildDateRange(
		@Prop(XDateMode.XInterval.P.START) final DateTime start,
		@Prop(XDateMode.XInterval.P.END) final DateTime end,
		@Prop(XDateMode.XInterval.P.INTERVAL) final Period interval) {
		final ImmutableSortedSet.Builder<DateTime> dates = ImmutableSortedSet.naturalOrder();
		DateTime current = start;
		while(current.isBefore(end)) {
			dates.add(current);
			current = current.plus(interval);
		}
		return dates.build();
	}
		
	@Adapt(XCaseDivision.class)
	public IGroups buildCaseDivision(
			@Prop(XCaseDivision.P.source) final Optional<IDwellingGroup> source,
			@Prop(XCaseDivision.P.cases) final List<IComponentsFunction<Boolean>> tests) {
		final IDwellingGroup s = source.or(groupFactory.getAllHouses());
		
		final Condition cond = groupFactory.createCondition(s, tests);
		
		final List<IDwellingGroup> subgroups = new ArrayList<IDwellingGroup>();
		for (final IDwellingGroup dg : cond.getGroups()) {
			subgroups.add(dg);
		}
		
		subgroups.add(cond.getDefaultGroup());
		
		return factory.createGroupDivision(subgroups);
	}
	
	@Adapt(XCrossDivision.class)
	public IGroups buildCross(
			@Prop(XCrossDivision.P.source) final Optional<IDwellingGroup> source,
			@Prop(XCrossDivision.P.categories) final List<IComponentsFunction<?>> categories) {
		return factory.createCross(source.or(groupFactory.getAllHouses()), categories);
	}
}
