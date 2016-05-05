package uk.org.cse.nhm.simulation.reporting.extension;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import com.google.common.base.Optional;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.adapt.impl.ReflectingAdapter;
import uk.org.cse.nhm.language.definition.reporting.XAggregateActionProbe;
import uk.org.cse.nhm.language.definition.reporting.XDwellingActionProbe;
import uk.org.cse.nhm.language.definition.reporting.XDwellingsReport;
import uk.org.cse.nhm.language.definition.reporting.XFuelCostsReport;
import uk.org.cse.nhm.language.definition.reporting.XGlobalAccountReport;
import uk.org.cse.nhm.language.definition.reporting.XGroupTransitionCase;
import uk.org.cse.nhm.language.definition.reporting.XGroupTransitionReport;
import uk.org.cse.nhm.language.definition.reporting.XHouseCountReport;
import uk.org.cse.nhm.language.definition.reporting.XInstallationLogReport;
import uk.org.cse.nhm.language.definition.reporting.XMeasureCostsReport;
import uk.org.cse.nhm.language.definition.reporting.XNationalPowerReport;
import uk.org.cse.nhm.language.definition.reporting.XNumberProbe;
import uk.org.cse.nhm.language.definition.reporting.XProbedNetPresentValue;
import uk.org.cse.nhm.language.definition.reporting.XSequenceReport;
import uk.org.cse.nhm.language.definition.reporting.XStateReport;
import uk.org.cse.nhm.language.definition.reporting.XStockReport;
import uk.org.cse.nhm.language.definition.reporting.XTransactionReport;
import uk.org.cse.nhm.language.definition.reporting.two.XColumnAggregation;
import uk.org.cse.nhm.language.definition.reporting.two.XReportColumn;
import uk.org.cse.nhm.language.definition.reporting.two.XReportCut;
import uk.org.cse.nhm.language.definition.reporting.two.XReportDefinition;
import uk.org.cse.nhm.language.definition.reporting.two.XSendToReport;
import uk.org.cse.nhm.language.definition.tags.Tag;
import uk.org.cse.nhm.simulation.reporting.aggregates.modes.IReportMode;
import uk.org.cse.nhm.simulation.reporting.probes.IProbingFunction;
import uk.org.cse.nhm.simulation.reporting.two.Accumulator;
import uk.org.cse.nhm.simulation.reporting.two.Column;
import uk.org.cse.nhm.simulation.reporting.two.Cut;
import uk.org.cse.nhm.simulation.reporting.two.IReportPart;
import uk.org.cse.nhm.simulation.reporting.two.SendToReportAction;
import uk.org.cse.nhm.simulation.reporting.two.UnifiedReport;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.factories.IGroupFactory;
import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.hooks.IDwellingSet;
import uk.org.cse.nhm.simulator.impl.StockLogger;
import uk.org.cse.nhm.simulator.main.Initializable;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.state.functions.IAggregationFunction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class ReportingAdapter extends ReflectingAdapter {
	private final IReportingFactory reportFactory;
	private final IGroupFactory groupFactory;
	private final Provider<StockLogger> stockLogger;

	@Inject
	public ReportingAdapter(final Provider<StockLogger> stockLogger, final Set<IConverter> delegates, final IReportingFactory probeFactory, final IGroupFactory groupFactory, final Set<IAdapterInterceptor> interceptors) {
		super(delegates, interceptors);
		this.stockLogger = stockLogger;
		this.reportFactory = probeFactory;
		this.groupFactory = groupFactory;
	}

	@Adapt(XGroupTransitionReport.class)
	public Initializable buildExplain(
			final Name newName, 
			@Prop(XGroupTransitionReport.P.group) final IDwellingGroup source,
			@Prop(XGroupTransitionReport.P.cases) final List<ExplainCase> cases
			) {
		final List<String> conditionNames = new ArrayList<String>();
		final List<IComponentsFunction<Boolean>> tests = new ArrayList<IComponentsFunction<Boolean>>();
		
		for (final ExplainCase ec : cases) {
			conditionNames.add(ec.getIdentifier().getName());
			tests.add(ec.test);
		}
		
		reportFactory.createExplain(
				conditionNames,
				groupFactory.createCondition(source, tests)
				).setIdentifier(newName);
		
		return Initializable.NOP;
	}
	
	@Adapt(XGroupTransitionCase.class)
	public ExplainCase buildExplainCase(
			final Name name,
			@Prop(XGroupTransitionCase.P.test) final IComponentsFunction<Boolean> test
			) {
		return new ExplainCase(test);
	}
	
	public static class ExplainCase extends AbstractNamed {
		public final IComponentsFunction<Boolean> test;
		
		public ExplainCase(final IComponentsFunction<Boolean> test) {
			this.test = test;
		}
	}
	
	@Adapt(XNumberProbe.class)
	public IComponentsFunction<Number> buildNumberProbe(
			@Prop(XNumberProbe.P.delegate) final IComponentsFunction<Number> delegate,
			@Prop(XNumberProbe.P.probedValues) final List<IComponentsFunction<?>> capture
			) {
		return reportFactory.createNumberProbe(delegate, capture);
	}
	
	@Adapt(XProbedNetPresentValue.class)
	public IProbingFunction buildNetPresentValueProbe() {
		return reportFactory.createNetPresentValueProbingFunction();
	}

	@Adapt(XDwellingActionProbe.class)
	public IComponentsAction buildDwellingActionProbe(
			@Prop(XDwellingActionProbe.P.delegate) final Optional<IComponentsAction> delegate,
			@Prop(XDwellingActionProbe.P.probedValues) final List<IComponentsFunction<?>> capture) {
		return reportFactory.createComponentsActionProbe(
				delegate,
				capture);
	}
	
	@Adapt(XAggregateActionProbe.class)
	public IStateAction buildAggregateActionProbe(
			@Prop(XAggregateActionProbe.P.delegate) final IStateAction delegate, 
			@Prop(XAggregateActionProbe.P.divisions) final List<IComponentsFunction<?>> divisions, 
			@Prop(XAggregateActionProbe.P.aggregations) final List<IAggregationFunction> aggregations) {
		return reportFactory.createAggregateActionProbe(delegate, divisions, aggregations);
	}
	
	@Adapt(XGlobalAccountReport.class)
	public Initializable buildGlobalAccountReport() {
		reportFactory.createGlobalAccounReport();
		return Initializable.NOP;
	}
	
	@Adapt(XTransactionReport.class)
	public Initializable buildDwellingTransactionReport(
			final Name newName, 
			@Prop(XTransactionReport.P.group) final IDwellingSet group,
			@Prop(XTransactionReport.P.tags) final List<Tag> tags
			) {
		reportFactory.createTransactionReport(group, 
				Tag.asSet(tags))
			.setIdentifier(newName);
		return Initializable.NOP;
	}
	
	@Adapt(XFuelCostsReport.class)
	public Initializable buildFuelCostsReport() {
		reportFactory.createFuelCostsReport();
		return Initializable.NOP;
	}
	
	@Adapt(XHouseCountReport.class)
	public Initializable buildHouseCountReport() {
		reportFactory.createHouseCountReport();
		return Initializable.NOP;
	}
	
	@Adapt(XInstallationLogReport.class)
	public Initializable buildInstallationLogReport() {
		reportFactory.createInstallationLogReport();
		return Initializable.NOP;
	}
	
	@Adapt(XMeasureCostsReport.class)
	public Initializable buildMeasureCostsReport() {
		reportFactory.createMeasureCostsReport();
		return Initializable.NOP;
	}
	
	@Adapt(XNationalPowerReport.class)
	public Initializable buildNationalPowerReport() {
		reportFactory.createNationalPowerReport();
		return Initializable.NOP;
	}
	
	@Adapt(XStateReport.class)
	public Initializable buildStateReport() {
		reportFactory.createStateReport();
		return Initializable.NOP;
	}
	
	@Adapt(XDwellingsReport.class)
	public Initializable buildDwellingsReport(
			final Name name,
			@Prop(XDwellingsReport.P.GROUP) final IDwellingGroup group,
			@Prop(XDwellingsReport.P.MODE) final IReportMode mode,
			@Prop(XDwellingsReport.P.FIELDS) final List<IComponentsFunction<?>> fields) {
		
		reportFactory.createDwellingsReport(group, mode, fields).setIdentifier(name);
		return Initializable.NOP;
	}
	
	@Adapt(XStockReport.class) 
	public Initializable buildStockReport() {
		stockLogger.get().shouldLog();
		
		return Initializable.NOP;
	}
	
	@Adapt(XSequenceReport.class)
	public Initializable createSequenceReport() {
		reportFactory.createSequenceLogger();
		return Initializable.NOP;
	}
	
	@Adapt(value = XReportDefinition.class, cache = true)
	public UnifiedReport createReportDefinition(
			@Prop(XReportDefinition.P.contents) final List<IReportPart> parts
			) {
		return reportFactory.createUnifiedReport(parts);
	}
	
	@Adapt(XReportColumn.class)
	public IReportPart createUnifiedColumn(
			@Prop(XReportColumn.P.value) IComponentsFunction<?> value, 
			@Prop(XReportColumn.P.aggregations) List<Accumulator> accumulators
			) {
		return new Column(value, accumulators);
	}
	
	@Adapt(XReportCut.class)
	public IReportPart createUnifiedCut(final XReportCut input) {
		return new Cut(input.getValues());
	}
	
	@Adapt(XSendToReport.class)
	public IComponentsAction createSendToReport(
			@Prop(XSendToReport.P.reports) final List<UnifiedReport> reports
			) {
		return new SendToReportAction(reports);
	}
	
	@Adapt(XColumnAggregation.XMax.class)
	public Accumulator createMax(XColumnAggregation.XMax m) {
		return new Accumulator.Max(m.hasName());
	}
	
	@Adapt(XColumnAggregation.XMin.class)
	public Accumulator createMin(XColumnAggregation.XMin m) {
		return new Accumulator.Min(m.hasName());
	}
	
	@Adapt(XColumnAggregation.XMean.class)
	public Accumulator createMean(XColumnAggregation.XMean m) {
		return new Accumulator.Mean(m.hasName());
	}
	
	@Adapt(XColumnAggregation.XSum.class)
	public Accumulator createSum(XColumnAggregation.XSum m) {
		return new Accumulator.Sum(m.hasName());
	}
	
	@Adapt(XColumnAggregation.XVariance.class)
	public Accumulator createVariance(XColumnAggregation.XVariance m) {
		return new Accumulator.Variance(m.hasName());
	}
	
	@Adapt(XColumnAggregation.XTile.class)
	public Accumulator createTile(final XColumnAggregation.XTile in) {
		return new Accumulator.Tile(in.hasName(), Math.min(Math.max(0, in.getP()), 1));
	}
	
	@Adapt(XColumnAggregation.XCount.class)
	public Accumulator createCount(final XColumnAggregation.XCount in) {
		return new Accumulator.Count(in.hasName());
	}
	
	@Adapt(XColumnAggregation.XIs.class)
	public Accumulator createIn(final XColumnAggregation.XIs in) {
		return new Accumulator.CountIs(in.hasName(), in.getValue());
	}
}
