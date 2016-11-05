package uk.org.cse.nhm.simulator.factories;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.commons.Glob;
import uk.org.cse.nhm.energycalculator.api.IWeather;
import uk.org.cse.nhm.energycalculator.api.types.AreaType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.language.definition.enums.XHeatingSystem;
import uk.org.cse.nhm.language.definition.fuel.XRangeCharge;
import uk.org.cse.nhm.language.definition.fuel.XSteppedPricing;
import uk.org.cse.nhm.language.definition.function.house.XMatchingFlags;
import uk.org.cse.nhm.language.definition.function.lookup.XLookupFunction;
import uk.org.cse.nhm.language.definition.function.npv.XExponentialDiscount;
import uk.org.cse.nhm.language.definition.function.npv.XHyperbolicDiscount;
import uk.org.cse.nhm.language.definition.function.num.XAirChangeRate;
import uk.org.cse.nhm.language.definition.function.num.XAnnualMaintenance;
import uk.org.cse.nhm.language.definition.function.num.XEfficiencyMeasurement;
import uk.org.cse.nhm.language.definition.function.num.XFloorInsulationThickness;
import uk.org.cse.nhm.language.definition.function.num.XGlobalAccountBalance;
import uk.org.cse.nhm.language.definition.function.num.XHeatLoad;
import uk.org.cse.nhm.language.definition.function.num.XHeatingEfficiency;
import uk.org.cse.nhm.language.definition.function.num.XHouseBalance;
import uk.org.cse.nhm.language.definition.function.num.XHouseWeight;
import uk.org.cse.nhm.language.definition.function.num.XInterpolate;
import uk.org.cse.nhm.language.definition.function.num.XLoanBalance;
import uk.org.cse.nhm.language.definition.function.num.XLoanBalanceOutstanding;
import uk.org.cse.nhm.language.definition.function.num.XLoanBalancePaid;
import uk.org.cse.nhm.language.definition.function.num.XLoftInsulationThickness;
import uk.org.cse.nhm.language.definition.function.num.XMainHeatingTemperature;
import uk.org.cse.nhm.language.definition.function.num.XMeanInternalTemperature;
import uk.org.cse.nhm.language.definition.function.num.XNetCost;
import uk.org.cse.nhm.language.definition.function.num.XNumberOfAdults;
import uk.org.cse.nhm.language.definition.function.num.XNumberOfChildren;
import uk.org.cse.nhm.language.definition.function.num.XPrior;
import uk.org.cse.nhm.language.definition.function.num.XSapScore;
import uk.org.cse.nhm.language.definition.function.num.XSizingFunction;
import uk.org.cse.nhm.language.definition.function.num.XSpaceHeatingResponsiveness;
import uk.org.cse.nhm.language.definition.function.num.XYear;
import uk.org.cse.nhm.language.definition.function.num.random.XGaussian;
import uk.org.cse.nhm.language.definition.function.num.random.XTriangular;
import uk.org.cse.nhm.language.definition.function.num.random.XUniform;
import uk.org.cse.nhm.simulator.measure.Units;
import uk.org.cse.nhm.simulator.measure.sizing.impl.SizingFunction;
import uk.org.cse.nhm.simulator.sequence.ISequenceSpecialAction;
import uk.org.cse.nhm.simulator.sequence.SequenceFunction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.ConditionComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.TimeSeriesComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.house.GetFlags;
import uk.org.cse.nhm.simulator.state.functions.impl.lookup.LookupEntry;
import uk.org.cse.nhm.simulator.state.functions.impl.lookup.LookupFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.AirChangeRateFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.AnnualCostFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.AnnualMaintenanceFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.AverageUValueFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.CarbonEmissionsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.CostResultFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.CountAdults;
import uk.org.cse.nhm.simulator.state.functions.impl.num.CountChildren;
import uk.org.cse.nhm.simulator.state.functions.impl.num.EnergyUseFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.FloorInsulationThicknessFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.FuelCostFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.GeometricDiscount;
import uk.org.cse.nhm.simulator.state.functions.impl.num.GlobalAccountBalanceFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.HeatLoadFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.HeatingEfficiencyFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.HeatingResponsivenessFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.HouseBalanceFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.HouseWeightFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.HyperbolicDiscount;
import uk.org.cse.nhm.simulator.state.functions.impl.num.InflatedFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.InterpolateFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.LoanBalanceOutstandingFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.LoanBalancePaidFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.LoftInsulationThicknessFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.MainHeatingTemperatureFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.MeanInternalTemperatureFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.MeterReadingFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.NetPresentValueFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.PeakHeatLoadFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.Polynomial;
import uk.org.cse.nhm.simulator.state.functions.impl.num.Polynomial.PolynomialTerm;
import uk.org.cse.nhm.simulator.state.functions.impl.num.PredictObligationsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.PredictSum;
import uk.org.cse.nhm.simulator.state.functions.impl.num.PriorFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.RegisterGetFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.SapScoreFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.SimYearFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.SizingResultFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.SteppedFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.SteppedFunction.Direction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.SumOfTransactionsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.random.GaussianRandomFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.random.TriangularRandomFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.random.UniformRandomFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.steppedcharge.SteppedPricingFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.steppedcharge.SteppedPricingFunction.Range;
import uk.org.cse.nhm.simulator.state.functions.impl.num.var.GetRegister;

public interface IObjectFunctionFactory {
	public ConditionComponentsFunction.NumberCondition createDoubleCondition(
			final List<IComponentsFunction<Boolean>> tests,
			final List<IComponentsFunction<Number>> values,
			final IComponentsFunction<Number> defaultValue);
	
	public ConditionComponentsFunction<IWeather> createWeatherCondition(
			final List<IComponentsFunction<Boolean>> tests,
			final List<IComponentsFunction<IWeather>> values,
			final IComponentsFunction<IWeather> defaultValue);
	
	public SteppedFunction createSteppedFunction(
			final Direction direction,
			final IComponentsFunction<Number> delegate,
			final List<Double> steps);
	
	public PeakHeatLoadFunction createPeakHeatLoadFunction(
			@Assisted("internal") final double internalTemperature,
			@Assisted("external") final double externalTemperature, 
			@Assisted("scale") final double scale
			);
	
	public SizingResultFunction createSizingResultFunction(final Units units);
	
	public NetPresentValueFunction createNetPresentValue(
			final double discount,
			final int horizon,
            final boolean debug,
            final Predicate<Collection<String>> requiredTags);
	
	public Polynomial createPolynomial(
			final IComponentsFunction<? extends Number> x,
			final List<PolynomialTerm> terms);
	
	public EnergyUseFunction createEnergyUseFunction(
			final boolean isCalibrated,
			final Optional<FuelType> ft,
			final Optional<List<ServiceType>> st);
	
	public FuelCostFunction createFuelCostFunction(
			final Optional<FuelType> ft,
			final Set<ServiceType> excludedServices
			);
	
	public CarbonEmissionsFunction createCarbonEmissionsFunction(
			final Optional<FuelType> ft,
			final Optional<List<ServiceType>> st);
	
	@Adapt(XNetCost.class) 
	public CostResultFunction createCostResultFunction(
			@Prop(XNetCost.P.taggedPredicate)  final Predicate<Collection<String>> tagged);
	
	@Adapt(XLoftInsulationThickness.class)
	public LoftInsulationThicknessFunction createLoftInsulationThicknessFunction();
	
	public AverageUValueFunction createAverageUValueFunction(final Set<AreaType> areas);

	@Adapt(XSizingFunction.class)
	public SizingFunction createSizingFunction(
			@Prop(XSizingFunction.P.VALUE) final IComponentsFunction<Number> value, 
			@Prop(XSizingFunction.P.MINIMUM_SIZE) @Assisted("min") final double min, 
			@Prop(XSizingFunction.P.MAXIMUM_SIZE) @Assisted("max") final double max);

	@Adapt(XAnnualMaintenance.class)
	public AnnualMaintenanceFunction createAnnualMaintenanceFunction();

	@Adapt(XHouseBalance.class)
	public HouseBalanceFunction createHouseBalanceFunction();

	@Adapt(XLoanBalancePaid.class)
	public LoanBalancePaidFunction createLoanBalancePaidFunction(
			@Prop(XLoanBalance.P.creditor)
			@Assisted("creditor") final String creditor, 
			@Prop(XLoanBalance.P.tagged) 
			@Assisted("tagged") final String tagged);

	@Adapt(XLoanBalanceOutstanding.class)
	public LoanBalanceOutstandingFunction createLoanBalanceOutstandingFunction(
			@Prop(XLoanBalance.P.creditor)
			@Assisted("creditor") final String creditor, 
			@Prop(XLoanBalance.P.tagged) 
			@Assisted("tagged") final String tagged);

	public MeterReadingFunction createHouseMeterReadingFunction(final FuelType fuel);

	@Adapt(XSteppedPricing.class) 
	public SteppedPricingFunction createSteppedPricingFunction(
			@Prop(XSteppedPricing.P.standingCharge) 
			@Assisted("standingCharge") final IComponentsFunction<Number> standingCharge,
			@Prop(XSteppedPricing.P.alwaysApply) 
			@Assisted final boolean alwaysApply, 
			@Prop(XSteppedPricing.P.unitsFunction) 
			@Assisted("unitsFunction") final IComponentsFunction<Number> unitsFunction, 
			@Prop(XSteppedPricing.P.ranges) 
			@Assisted final List<Range> ranges);

	@Adapt(XRangeCharge.class)
	public SteppedPricingFunction.Range buildRange(
			@Prop(XRangeCharge.P.to) final double upperBound, 
			@Prop(XRangeCharge.P.unitPrice) final IComponentsFunction<Number> unitPrice);
	
	public TimeSeriesComponentsFunction<Number> createTimeSeriesFunction(
			@Assisted Optional<XForesightLevel> foresight, 
			@Assisted("initial") final Number initial, 
			@Assisted("later") final Map<DateTime, Number> later);

	public RegisterGetFunction createRegisterGetFunction(
			@Assisted final String name, 
			@Assisted final Double defaultVal);
	
	public PredictObligationsFunction createPredictObligations(
			@Assisted final int years,
			@Assisted("includeScope") 	final boolean includeScope,
			@Assisted("includeHistory") final boolean includeHistory,
			@Assisted final Predicate<Collection<String>> requiredTags
			);
	
	@Adapt(XSapScore.class)
	public SapScoreFunction createSapScore(
			@Prop(XSapScore.P.deflator)
			@Assisted final double deflator);
	
	public InflatedFunction createInflatedFunction(
			Optional<XForesightLevel> foresight, final Optional<DateTime> startDate, final double rate,
			final IComponentsFunction<? extends Number> value);

	public SumOfTransactionsFunction createSumOfTransactions(
			@Assisted final Optional<Glob> counterparty, 
			@Assisted final Predicate<Collection<String>> requiredTags);

	@Adapt(XLookupFunction.class)
	public LookupFunction createLookupFunction(
			@Prop(XLookupFunction.P.keys) 
			final List<IComponentsFunction<?>> keys,
			@Prop(XLookupFunction.P.entries) 
			final List<LookupEntry> entries, 
			@Prop(XLookupFunction.P.defaultValue) 
			final IComponentsFunction<? extends Number> fallback,
			@Prop(XLookupFunction.P.warnOnFallback) 
			final boolean warnOnError);
			
	@Adapt(XHeatingEfficiency.class)
	public HeatingEfficiencyFunction createHeatingEfficiency(
			@Prop(XHeatingEfficiency.P.of) @Assisted final XHeatingSystem of,
			@Prop(XHeatingEfficiency.P.measurement) @Assisted final XEfficiencyMeasurement measurement);

	@Adapt(XSpaceHeatingResponsiveness.class)
	public HeatingResponsivenessFunction createHeatingResponsiveness();

	@Adapt(XGaussian.class)
	public GaussianRandomFunction createGaussian(
			@Prop(XGaussian.P.mean)
			@Assisted("mean") final double mean,
			@Prop(XGaussian.P.standardDeviation)
			@Assisted("standardDeviation") final double standardDeviation);

	@Adapt(XUniform.class)
	public UniformRandomFunction createUniform(
			@Prop(XUniform.P.start) 
			@Assisted("start") final double start, 
			@Prop(XUniform.P.end) 
			@Assisted("end") final double end);
	
	@Adapt(XTriangular.class)
	public TriangularRandomFunction createTriangular(
			@Prop(XTriangular.P.start) 
			@Assisted("start") final double start,
			@Prop(XTriangular.P.peak) 
			@Assisted("peak") final double peak, 
			@Prop(XTriangular.P.end) 
			@Assisted("end") final double end);

	@Adapt(XFloorInsulationThickness.class)
	public FloorInsulationThicknessFunction createFloorInsulationThickness();

	@Adapt(XInterpolate.class)
	public InterpolateFunction createInterpolate(
			@Prop(XInterpolate.P.x) 
			@Assisted final IComponentsFunction<? extends Number> x,
			@Prop(XInterpolate.P.extrapolate) 
            @Assisted final boolean extrapolate,
            @Prop(XInterpolate.P.xCoordinates) 
			@Assisted("xs") final List<Double> xs, 
			@Prop(XInterpolate.P.yCoordinates) 
			@Assisted("ys") final List<Double> ys);
	
	@Adapt(XGlobalAccountBalance.class)
	public GlobalAccountBalanceFunction createGlobalAccountBalance(
			@Prop(XGlobalAccountBalance.P.account)  final String account);
	
	@Adapt(XNumberOfChildren.class)
	public CountChildren createCountChildren();
	
	@Adapt(XNumberOfAdults.class)
	public CountAdults createCountAdults();

	public SequenceFunction createSequenceFunction(
			final IComponentsFunction<Number> delegate,
			final List<ISequenceSpecialAction> actions);

	public GetRegister createGetRegister(
			final String name,
			final Optional<Double> defaultValue);
	
	@Adapt(XMainHeatingTemperature.class)
	public MainHeatingTemperatureFunction createMainHeatingTemperature();

	@Adapt(XYear.class)
	public SimYearFunction createSimYear(
			@Prop("foresight") Optional<XForesightLevel> foresight);
	
	@Adapt(XHouseWeight.class)
	public HouseWeightFunction createHouseWeight();
	
	@Adapt(XMeanInternalTemperature.class)
	public MeanInternalTemperatureFunction createMeanInternalTemperature();
	
	@Adapt(XAirChangeRate.class)
	public AirChangeRateFunction createAirChangeRate();

	@Adapt(XHeatLoad.class)
    public HeatLoadFunction createHeatLoadFunction(
    		@Prop(XHeatLoad.P.weights) 
    		List<Double> weights, 
    		@Prop(XHeatLoad.P.space) 
    		@Assisted("space") boolean space, 
    		@Prop(XHeatLoad.P.water) 
    		@Assisted("water") boolean water);

	public PredictSum createPredictSum(
			@Assisted("horizon") IComponentsFunction<Number> horizon,
			@Assisted Set<XForesightLevel> foresight, 
			@Assisted("value") IComponentsFunction<Number> value);
	
	@Adapt(XExponentialDiscount.class)
	public GeometricDiscount createGeometricDiscount(
			@Prop(XExponentialDiscount.P.rate) 
			@Assisted("rate") IComponentsFunction<Number> rate, 
			@Prop(XExponentialDiscount.P.value)
			@Assisted("value") IComponentsFunction<Number> value);

	@Adapt(XHyperbolicDiscount.class)
	public HyperbolicDiscount createHyperbolicDiscount(
			@Prop(XHyperbolicDiscount.P.beta) 
			@Assisted("beta") IComponentsFunction<Number> beta,
			@Prop(XHyperbolicDiscount.P.delta) 
			@Assisted("delta") IComponentsFunction<Number> delta, 
			@Prop(XHyperbolicDiscount.P.value) 
			@Assisted("value") IComponentsFunction<Number> value);
	
	public AnnualCostFunction createAnnualCost(
			@Assisted final boolean includeYearEnd,
			@Assisted final Predicate<Collection<String>> tagTest
			);

    @Adapt(XMatchingFlags.class)
    public GetFlags createGetFlags(
        @Prop(XMatchingFlags.P.glob) final Glob glob);


    @Adapt(XPrior.class)
    public PriorFunction createPrior(
        @Prop(XPrior.P.DELEGATE) final IComponentsFunction<? extends Number> delegate
        );
}
