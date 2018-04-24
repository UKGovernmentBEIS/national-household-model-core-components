package uk.org.cse.nhm.simulation.reporting.extension;

import java.util.List;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.function.num.XSummaryFunction;
import uk.org.cse.nhm.language.definition.reporting.aggregate.XCountAggregation;
import uk.org.cse.nhm.language.definition.reporting.aggregate.XGroupDivision;
import uk.org.cse.nhm.language.definition.reporting.aggregate.XMeanAggregation;
import uk.org.cse.nhm.language.definition.reporting.aggregate.XNTileAggregation;
import uk.org.cse.nhm.language.definition.reporting.aggregate.XSumAggregation;
import uk.org.cse.nhm.language.definition.reporting.aggregate.XWhereAggregation;
import uk.org.cse.nhm.simulation.reporting.accounts.GlobalAccountsReport;
import uk.org.cse.nhm.simulation.reporting.accounts.TransactionReport;
import uk.org.cse.nhm.simulation.reporting.aggregates.*;
import uk.org.cse.nhm.simulation.reporting.aggregates.modes.IReportMode;
import uk.org.cse.nhm.simulation.reporting.dwellings.DwellingsReport;
import uk.org.cse.nhm.simulation.reporting.fuel.FuelCostsReport;
import uk.org.cse.nhm.simulation.reporting.probes.ActionProbe;
import uk.org.cse.nhm.simulation.reporting.probes.AggregateActionProbe;
import uk.org.cse.nhm.simulation.reporting.probes.NetPresentValueProbingFunction;
import uk.org.cse.nhm.simulation.reporting.probes.NumberProbe;
import uk.org.cse.nhm.simulation.reporting.standard.EnergyAggregator;
import uk.org.cse.nhm.simulation.reporting.standard.InstallationDetailsLogger;
import uk.org.cse.nhm.simulation.reporting.standard.MeasureCostLogger;
import uk.org.cse.nhm.simulation.reporting.standard.SequenceLogger;
import uk.org.cse.nhm.simulation.reporting.standard.StockSizeAggregator;
import uk.org.cse.nhm.simulation.reporting.state.StateChangeLogger;
import uk.org.cse.nhm.simulation.reporting.transitions.Explain;
import uk.org.cse.nhm.simulation.reporting.two.IReportPart;
import uk.org.cse.nhm.simulation.reporting.two.UnifiedReport;
import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.groups.impl.Condition;
import uk.org.cse.nhm.simulator.hooks.IDwellingSet;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.state.functions.IAggregationFunction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public interface IReportingFactory {
	public Explain createExplain(final List<String> conditionNames, final Condition condition);

	public NumberProbe createNumberProbe(
			final IComponentsFunction<Number> delegate,
			final List<IComponentsFunction<? extends Object>> probeFunctions
			);

	public NetPresentValueProbingFunction createNetPresentValueProbingFunction();

	public ActionProbe createComponentsActionProbe(
			final Optional<IComponentsAction> delegate,
			final List<IComponentsFunction<? extends Object>> probeFunctions);

	public AggregateActionProbe createAggregateActionProbe(
			@Assisted final IStateAction delegate,
			@Assisted final List<IComponentsFunction<?>> cuts,
			@Assisted final List<IAggregationFunction> values);

	public AggregateReport createAggregateReport(final IReportMode mode, final List<IAggregationFunction> functions);

    @Adapt(XGroupDivision.class)
	public GroupGroups createGroupDivision(@Prop(XGroupDivision.P.groups)
                                           final List<IDwellingGroup> groups);

    @Adapt(XCountAggregation.class)
	public Count createCount();

    @Adapt(XSumAggregation.class)
	public Sum createSum(@Prop(XSumAggregation.P.value) final IComponentsFunction<Number> fn);

    @Adapt(XMeanAggregation.class)
	public Mean createMean(@Prop(XMeanAggregation.P.value) final IComponentsFunction<Number> fn);

    @Adapt(XWhereAggregation.class)
	public Where createWhere(@Prop(XWhereAggregation.P.test) final IComponentsFunction<Boolean> fn,
                             @Prop(XWhereAggregation.P.aggregation) final IAggregationFunction delegate);

	public CrossGroups createCross(final IDwellingGroup source,
			final List<IComponentsFunction<?>> categories);

	public GlobalAccountsReport createGlobalAccounReport();

	public TransactionReport createTransactionReport(
			@Assisted final IDwellingSet group,
			@Assisted final Set<String> requiredTags);

	public FuelCostsReport createFuelCostsReport();

	public StockSizeAggregator createHouseCountReport();

	public InstallationDetailsLogger createInstallationLogReport();

	public MeasureCostLogger createMeasureCostsReport();

	public EnergyAggregator createNationalPowerReport();

	public StateChangeLogger createStateReport();

	public ReportTrigger createReportTrigger(final IReportMode mode, final IGroups division);

	public DwellingsReport createDwellingsReport(
			@Assisted final IDwellingGroup group,
			@Assisted final IReportMode mode,
			@Assisted final List<IComponentsFunction<?>> fields);

    @Adapt(XNTileAggregation.class)
	public NTile createNTile(
        @Prop(XNTileAggregation.P.function) final IComponentsFunction<Number> function,
        @Prop(XNTileAggregation.P.n) final double tile);

    @Adapt(XSummaryFunction.class)
	public GroupSummary createSummaryFunction(
        @Prop(XSummaryFunction.P.group)
        @Assisted final IDwellingSet group,
        @Prop(XSummaryFunction.P.aggregation)
        @Assisted final IAggregationFunction aggregation);

	public SequenceLogger createSequenceLogger();

	public UnifiedReport createUnifiedReport(final boolean recordChange, final List<IReportPart> contents);
}
