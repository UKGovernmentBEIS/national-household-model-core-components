package uk.org.cse.nhm.language.builder.function;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import uk.org.cse.commons.Glob;
import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.energycalculator.api.types.AreaType;
import uk.org.cse.nhm.energycalculator.api.types.steps.EnergyCalculationStep;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.FromScope;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.adapt.impl.ReflectingAdapter;
import uk.org.cse.nhm.language.builder.context.CalibrationAdapter;
import uk.org.cse.nhm.language.builder.context.FuelPropertyAdapter;
import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.language.definition.enums.XEnergyCalculationStep;
import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.language.definition.enums.XServiceType;
import uk.org.cse.nhm.language.definition.fuel.XSimpleTariff.XSimpleTariffFuel;
import uk.org.cse.nhm.language.definition.function.bool.XNumberSequence;
import uk.org.cse.nhm.language.definition.function.lookup.LookupRule;
import uk.org.cse.nhm.language.definition.function.lookup.XLookupFunction;
import uk.org.cse.nhm.language.definition.function.lookup.XLookupFunction.XLookupEntry;
import uk.org.cse.nhm.language.definition.function.npv.XFutureValue;
import uk.org.cse.nhm.language.definition.function.num.*;
import uk.org.cse.nhm.language.definition.function.num.XAverageUValue.XSurfaceType;
import uk.org.cse.nhm.language.definition.function.num.XNumberCase.XNumberCaseWhen;
import uk.org.cse.nhm.language.definition.function.num.XPredictObligations.XObligationSource;
import uk.org.cse.nhm.language.definition.function.num.XStepwiseFunction.XRoundingType;
import uk.org.cse.nhm.language.definition.function.num.basic.XBasicNumberFunction;
import uk.org.cse.nhm.language.definition.function.num.basic.XDifference;
import uk.org.cse.nhm.language.definition.function.num.basic.XExp;
import uk.org.cse.nhm.language.definition.function.num.basic.XLog;
import uk.org.cse.nhm.language.definition.function.num.basic.XMaximum;
import uk.org.cse.nhm.language.definition.function.num.basic.XMinimum;
import uk.org.cse.nhm.language.definition.function.num.basic.XPower;
import uk.org.cse.nhm.language.definition.function.num.basic.XProduct;
import uk.org.cse.nhm.language.definition.function.num.basic.XRatio;
import uk.org.cse.nhm.language.definition.function.num.basic.XRound;
import uk.org.cse.nhm.language.definition.function.num.basic.XRound.XRoundDirection;
import uk.org.cse.nhm.language.definition.function.num.basic.XSum;
import uk.org.cse.nhm.language.definition.sequence.XFunctionDeclaration;
import uk.org.cse.nhm.language.definition.sequence.XNumberDeclaration;
import uk.org.cse.nhm.language.definition.sequence.XSnapshotDeclaration;
import uk.org.cse.nhm.simulator.IProfilingStack;
import uk.org.cse.nhm.simulator.SimulatorConfigurationConstants;
import uk.org.cse.nhm.simulator.factories.IObjectFunctionFactory;
import uk.org.cse.nhm.simulator.main.Initializable;
import uk.org.cse.nhm.simulator.measure.Units;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.lookup.LookupEntry;
import uk.org.cse.nhm.simulator.state.functions.impl.num.Polynomial.PolynomialTerm;
import uk.org.cse.nhm.simulator.state.functions.impl.num.SnapshotDeltaFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.SteppedFunction.Direction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.UnderFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.var.GetGlobal;
import uk.org.cse.nhm.simulator.state.functions.impl.num.var.GetYield;
import uk.org.cse.nhm.simulator.transactions.ITransaction;


/**
 * Adapter for builder which handles things in uk.org.cse.nhm.language.definition.function.num
 *
 * @author hinton
 *
 */
public class NumberFunctionAdapter extends ReflectingAdapter {
	final IObjectFunctionFactory factory;
    final Provider<IProfilingStack> profiler;

	@SuppressWarnings("unused")
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(NumberFunctionAdapter.class);

	@Inject
	public NumberFunctionAdapter(final Provider<IProfilingStack> profiler, final Set<IConverter> delegates, final IObjectFunctionFactory factory, final Set<IAdapterInterceptor> interceptors) {
		super(delegates, interceptors);
        this.profiler = profiler;
		this.factory = factory;
	}

	@Adapt(XInflatedFunction.class)
	public IComponentsFunction<? extends Number> buildInflatedFunction(
			@Prop("foresight") final Optional<XForesightLevel> foresight,
			@Prop(XInflatedFunction.P.starting)
			final Optional<DateTime> startDate,
			@Prop(XInflatedFunction.P.rate)
			final double rate,
			@Prop(XInflatedFunction.P.value)
			final IComponentsFunction<? extends Number> value
			) {
		return factory.createInflatedFunction(foresight, startDate, 1 + rate, value);
	}

	@Adapt(XHeatCapacity.class)
	public IComponentsFunction<? extends Number> buildHeatCapacity() {
		return factory.createSizingResultFunction(Units.KILOWATTS);
	}

	@Adapt(XInsulationArea.class)
	public IComponentsFunction<? extends Number> buildInsulationArea() {
		return factory.createSizingResultFunction(Units.SQUARE_METRES);
	}

	@Adapt(XNetPresentValue.class)
	public IComponentsFunction<? extends Number> buildNetPresentValue(
			@Prop(XNetPresentValue.P.DISCOUNT) final double discount,
			@Prop(XNetPresentValue.P.HORIZON) final int horizon,
            @Prop(XNetPresentValue.P.TAGS) final List<Glob> tags
			) {
		return factory.createNetPresentValue(discount, horizon,
                                             false,
                                             Glob.requireAndForbid(tags));
	}

	@Adapt(XNumberConstant.class)
	public IComponentsFunction<? extends Number> buildNumberConstant(
			final Name name,
			@Prop(XNumberConstant.P.VALUE) final double value
			) {
		return ConstantComponentsFunction.<Number>of(name, value);
	}

	@Adapt(XPeakHeatDemand.class)
	public IComponentsFunction<? extends Number> buildPeakHeatDemand(
			@Prop(XPeakHeatDemand.P.internalTemperature) final double internal,
			@Prop(XPeakHeatDemand.P.externalTemperature) final double external
			) {
		return factory.createPeakHeatLoadFunction(internal, external, 1000);
	}

	@Adapt(XHeatLoss.class)
    public IComponentsFunction<? extends Number> buildHeatLoss(
        @Prop(XHeatLoss.P.TYPE) final XHeatLoss.XHeatLossType type
        ) {
        return factory.createHeatLossFunction(type);
    }

	@Adapt(XStepwiseFunction.class)
	public IComponentsFunction<Number> buildStepwiseFunction(
			@Prop(XStepwiseFunction.P.mode) final XRoundingType direction,
			@Prop(XStepwiseFunction.P.steps) final List<Double> steps,
			@Prop(XStepwiseFunction.P.value) final IComponentsFunction<Number> delegate
			) {
		switch (direction) {
		case Down:
			return factory.createSteppedFunction(Direction.DOWN, delegate, steps);
		case Up:
			return factory.createSteppedFunction(Direction.UP, delegate, steps);
		case Closest:
		default:
			return factory.createSteppedFunction(Direction.NEAREST, delegate, steps);
		}
	}

	@Adapt(XQuadratic.class)
	public IComponentsFunction<? extends Number> buildQuadratic(
			final Name name,
			@Prop(XQuadratic.P.x) final IComponentsFunction<? extends Number> x,
			@Prop(XQuadratic.P.a) final double a,
			@Prop(XQuadratic.P.b) final double b,
			@Prop(XQuadratic.P.c) final double c) {
		final List<PolynomialTerm> terms = new ArrayList<PolynomialTerm>();

		if (a != 0) {
			terms.add(new PolynomialTerm(2, ConstantComponentsFunction.<Double>of(name, a)));
		}

		if (b != 0) {
			terms.add(new PolynomialTerm(1, ConstantComponentsFunction.<Double>of(name, b)));
		}

		if (c != 0) {
			terms.add(new PolynomialTerm(0, ConstantComponentsFunction.<Double>of(name, c)));
		}

		return buildPolynomial(x, terms);
	}

	public IComponentsFunction<? extends Number> buildPolynomial(
			final IComponentsFunction<? extends Number> value,
			final List<PolynomialTerm> terms
			) {
		return factory.createPolynomial(value, terms);
	}

	public PolynomialTerm buildTerm(
			final int degree,
			final IComponentsFunction<Number> coefficient
			) {
		return new PolynomialTerm(degree, coefficient);
	}

	@Adapt(XNumberCase.class)
	public IComponentsFunction<Number> buildNumberCase(
			@Prop(XNumberCase.P.cases) final List<NumberCaseWhen> cases,
			@Prop(XNumberCase.P.otherwise) final IComponentsFunction<Number> defaultValue
			) {

		final List<IComponentsFunction<Boolean>> tests = new ArrayList<IComponentsFunction<Boolean>>();
		final List<IComponentsFunction<Number>> values = new ArrayList<IComponentsFunction<Number>>();

		for (final NumberCaseWhen c : cases) {
			tests.add(c.test);
			values.add(c.value);
		}

		return factory.createDoubleCondition(tests, values, defaultValue);
	}

	@Adapt(XNumberCaseWhen.class)
	public NumberCaseWhen buildNumberCaseWhen(
			@Prop(XNumberCaseWhen.P.test) final IComponentsFunction<Boolean> test,
			@Prop(XNumberCaseWhen.P.value) final IComponentsFunction<Number> value
			) {
		return new NumberCaseWhen(test, value);
	}

	public static class NumberCaseWhen {
		public final IComponentsFunction<Boolean> test;
		public final IComponentsFunction<Number> value;
		public NumberCaseWhen(final IComponentsFunction<Boolean> test,
				final IComponentsFunction<Number> value) {
			this.test = test;
			this.value = value;
		}
	}

	@Adapt(XTimeSeries.class)
	public IComponentsFunction<Number> buildTimeSeries(
			@Prop("foresight") final Optional<XForesightLevel> foresight,
			@Prop(XTimeSeries.P.INITIAL) final double initial,
		@Prop(XTimeSeries.P.ON) final List<TimeSeriesStep> later) {
		final ImmutableMap.Builder<DateTime, Number> laterMap = ImmutableMap.builder();
		for(final TimeSeriesStep step : later) {
			laterMap.put(step.when, step.value);
		}
		return factory.createTimeSeriesFunction(foresight, initial, laterMap.build());
	}

	@Adapt(XTimeSeries.XOn.class)
	public TimeSeriesStep buildStep(
			@Prop(XTimeSeries.XOn.P.DATE) final DateTime date,
			@Prop(XTimeSeries.XOn.P.VALUE) final double value) {
		return new TimeSeriesStep(date, value);
	}

	static class TimeSeriesStep {
		private final DateTime when;
		private final double value;

		TimeSeriesStep(final DateTime when, final double value) {
			this.when = when;
			this.value = value;
		}
	}

	private static Optional<FuelType> maybeFuel(final Optional<XFuelType> fuelType) {
		if (fuelType.isPresent()) {
			return Optional.of(MapEnum.fuel(fuelType.get()));
		} else {
			return Optional.absent();
		}
	}

	private static Optional<List<ServiceType>> maybeService(final Optional<XServiceType> serviceType) {
		if (serviceType.isPresent()) {
			return Optional.of(serviceType.get().getInternal());
		} else {
			return Optional.absent();
		}
	}

	@Adapt(XEnergyUse.class)
	public IComponentsFunction<? extends Number> buildEnergyUse(
			@FromScope(CalibrationAdapter.INSIDE_CALIBRATION) final Optional<Boolean> forceUnCalibrated,
			@Prop(XEnergyUse.P.calibrated) final boolean calibrated,
			@Prop(XEnergyUse.P.byFuel) final Optional<XFuelType> fuelType,
			@Prop(XEnergyUse.P.byService) final Optional<XServiceType> serviceType) {
		return factory.createEnergyUseFunction(
				(!forceUnCalibrated.or(false)) && calibrated,
				maybeFuel(fuelType),
				maybeService(serviceType));
	}

	@Adapt(XFuelCost.class)
	public IComponentsFunction<? extends Number> buildFuelCost(
			@Prop(XFuelCost.P.byFuel) final Optional<XFuelType> fuelType,
			@Prop(XFuelCost.P.excludeServices) final List<XServiceType> excludingServices) {
		final EnumSet<ServiceType> internal = EnumSet.noneOf(ServiceType.class);
		for (final XServiceType st : excludingServices) {
			internal.addAll(st.getInternal());
		}
		return factory.createFuelCostFunction(maybeFuel(fuelType), internal);
	}

	@Adapt(XEnergyCalculationStepFunction.class)
    public IComponentsFunction<? extends Number> buildEnergyCalculationStep(
            @Prop(XEnergyCalculationStepFunction.P.step) final XEnergyCalculationStep step,
            @Prop(XEnergyCalculationStepFunction.P.month) final Integer month
    ) {
	    final EnergyCalculationStep realStep = MapEnum.energyCalculationStep(step);

        return factory.createEnergyCalculatorStepFunction(realStep, java.util.Optional.ofNullable(month));
    }

	@Adapt(XSumTransactions.class)
	public IComponentsFunction<? extends Number> buildSumOfTransactions(
		@Prop(XSumTransactions.P.counterparty) final Optional<Glob> counterparty,
		@Prop(XSumTransactions.P.tags) final List<Glob> tags
			) {
		return factory.createSumOfTransactions(
				counterparty,
				Glob.requireAndForbid(tags));
	}

	@Adapt(XCarbonEmissions.class)
	public IComponentsFunction<? extends Number> buildCarbonEmissions(
			@Prop(XEnergyUse.P.byFuel) final Optional<XFuelType> fuelType,
			@Prop(XEnergyUse.P.byService) final Optional<XServiceType> serviceType) {
		return factory.createCarbonEmissionsFunction(maybeFuel(fuelType), maybeService(serviceType));
	}

	@Adapt(XCapex.class)
	public IComponentsFunction<? extends Number> buildCapexCost(
			) {
	  return factory.createCostResultFunction(
			  Glob.requireAndForbid(
					  ImmutableList.of(
							  Glob.of(ITransaction.Tags.CAPEX)
							  )
					  ));
	}

	@Adapt(XCapitalCost.class)
	public IComponentsFunction<? extends Number> buildCapitalCost() {
		return factory.createCostResultFunction(
				  Glob.requireAndForbid(
						  ImmutableList.of(
								  Glob.of(ITransaction.Tags.CAPEX)
								  )
						  ));
	}

	@Adapt(XSumOfCosts.class)
	public IComponentsFunction<? extends Number> buildNamedCost(
			@Prop(XSumOfCosts.P.tagged) final List<Glob> tagged
			) {
	  return factory.createCostResultFunction(Glob.requireAndForbid(tagged));
	}

	@Adapt(XAverageUValue.class)
	public IComponentsFunction<? extends Number> buildAverageUValue(
			@Prop(XAverageUValue.P.of) final XSurfaceType surface
			) {
		switch (surface) {
		case AllSurfaces:
			return factory.createAverageUValueFunction(
				EnumSet.of(AreaType.Door, AreaType.ExternalCeiling,
						AreaType.ExternalFloor, AreaType.ExternalWall, AreaType.Glazing));
		case Doors:
			return factory.createAverageUValueFunction(
					EnumSet.of(AreaType.Door));
		case Floors:
			return factory.createAverageUValueFunction(
					EnumSet.of(AreaType.ExternalFloor));
		case Roofs:
			return factory.createAverageUValueFunction(
					EnumSet.of(AreaType.ExternalCeiling));
		case Walls:
			return factory.createAverageUValueFunction(
					EnumSet.of(AreaType.ExternalWall));
		case Windows:
			return factory.createAverageUValueFunction(
					EnumSet.of(AreaType.Glazing));
		default:
			throw new IllegalArgumentException("Cannot build average u value function for " + surface);
		}
	}

	@Adapt(XHouseMeterReading.class)
	public IComponentsFunction<? extends Number> buildMeterReadingFunction(
			@FromScope(FuelPropertyAdapter.TARIFF_FUEL_TYPE) final Optional<XFuelType> outerFuel,
			@Prop(XHouseMeterReading.P.fuel) final Optional<XFuelType> fuel
			) {
		return factory.createHouseMeterReadingFunction(MapEnum.fuel(fuel.or(outerFuel).get()));
	}

	@Adapt(XDifference.class)
	public IComponentsFunction<? extends Number> buildDifference(
			@Prop(XBasicNumberFunction.P.children) final List<IComponentsFunction<? extends Number>> children) {
		return Maths.difference(children);
	}

	@Adapt(XMaximum.class)
	public IComponentsFunction<? extends Number> buildMaximum(
			@Prop(XBasicNumberFunction.P.children) final List<IComponentsFunction<? extends Number>> children) {
		return Maths.max(children);
	}
	@Adapt(XMinimum.class)
	public IComponentsFunction<? extends Number> buildMinimum(
			@Prop(XBasicNumberFunction.P.children) final List<IComponentsFunction<? extends Number>> children) {
		return Maths.min(children);
	}
	@Adapt(XProduct.class)
	public IComponentsFunction<? extends Number> buildProduct(
			@Prop(XBasicNumberFunction.P.children) final List<IComponentsFunction<? extends Number>> children) {
		return Maths.product(children);
	}
	@Adapt(XRatio.class)
	public IComponentsFunction<? extends Number> buildRatio(
			@Prop(XBasicNumberFunction.P.children) final List<IComponentsFunction<? extends Number>> children) {
		return Maths.ratio(children);
	}

	@Adapt(XSum.class)
	public IComponentsFunction<? extends Number> buildSum(
			@Prop(XBasicNumberFunction.P.children) final List<IComponentsFunction<? extends Number>> children) {
		return Maths.sum(children);
	}

	@Adapt(XGet.class)
	public IComponentsFunction<Number> buildGet(
			@Prop(XGet.P.var) final XNumberDeclaration var) {
		switch (var.getOn()) {
		case House:
			return factory.createGetRegister(var.getName(), Optional.fromNullable(var.getDefaultValue()));
		case Event:
			return new GetYield(profiler.get(), var.getName(), Optional.fromNullable(var.getDefaultValue()));
		case Simulation:
			return new GetGlobal(profiler.get(), var.getName(), Optional.fromNullable(var.getDefaultValue()));
		}
		throw new IllegalArgumentException("Unknown kind of scope " + var.getOn());
	}

	// equivalent
	@Adapt(XNumberDeclaration.class)
	public IComponentsFunction<Number> buildGetFromNumber(final XNumberDeclaration decl) {
		return buildGet(decl);
	}

	@Adapt(XUnderFunction.class)
	public IComponentsFunction<Number> buildUnderFunction(
			@Prop(XUnderFunction.P.DELEGATE) final IComponentsFunction<Number> delegate,
			@Prop(XUnderFunction.P.SNAPSHOT) final Optional<XSnapshotDeclaration> snapshotName,
			@Prop(XUnderFunction.P.HYPOTHESES) final List<IComponentsAction> hypotheses) {
		return new UnderFunction(delegate,
				snapshotName.isPresent() ? Optional.of(snapshotName.get().getName()) : Optional.<String>absent()
				, hypotheses);
	}

	@Adapt(XSnapshotDelta.class)
	public IComponentsFunction<? extends Number> buildSnapshotDelta(
			@Prop(XSnapshotDelta.P.DELEGATE) final IComponentsFunction<? extends Number> delegate,
			@Prop(XSnapshotDelta.P.BEFORE) final XSnapshotDeclaration before,
			@Prop(XSnapshotDelta.P.AFTER) final XSnapshotDeclaration after) {
		return new SnapshotDeltaFunction(delegate, before.getName(), after.getName());
	}

	/**
	 * When adapting a simple tariff fuel, we can just compose the function
	 * directly out of existing parts; consequently this actually builds
	 *
	 * sum(constant(standingRate), product(unitRate, meter(fuel)))
	 *
	 * @param fuel
	 * @param standingCharge
	 * @param unitRate
	 * @return a function which does the job
	 */
	@Adapt(XSimpleTariffFuel.class)
	public IComponentsFunction<? extends Number> buildSimplePricing(
			final Name name,
			@Prop(XSimpleTariffFuel.P.fuel) final XFuelType fuel,
			@Prop(XSimpleTariffFuel.P.standingCharge) final double standingCharge,
			@Prop(XSimpleTariffFuel.P.unitRate) final double unitRate
			) {
		IComponentsFunction<? extends Number> result =
				buildProduct(
						ImmutableList.of(
								buildNumberConstant(name, unitRate),
								buildMeterReadingFunction(Optional.of(fuel), Optional.of(fuel))
								));

		if (standingCharge != 0) {
			result = buildSum(
					ImmutableList.of(
							result,
							buildNumberConstant(name, standingCharge)
							));
		}

		return result;
	}

	@Adapt(XPredictObligations.class)
	public IComponentsFunction<? extends Number> buildPredictObligations(
			@Prop(XPredictObligations.P.years) final int years,
			@Prop(XPredictObligations.P.include) final XObligationSource source,
			@Prop(XPredictObligations.P.tags) final List<Glob> tags
			) {
		return factory.createPredictObligations(
				years,
				source != XObligationSource.Existing,
				source != XObligationSource.NewlyAdded,
				Glob.requireAndForbid(tags));
	}

	@Adapt(XPower.class)
	public IComponentsFunction<? extends Number> buildPow(
			@Prop(XBasicNumberFunction.P.children) final List<IComponentsFunction<? extends Number>> children) {
		return Maths.pow(children);
	}

	@Adapt(XLog.class)
	public IComponentsFunction<? extends Number> buildLog(@Prop(XLog.P.base) final double base, @Prop(XLog.P.delegate) final IComponentsFunction<? extends Number> delegate) {
		return Maths.log(delegate, base);
	}

	@Adapt(XExp.class)
	public IComponentsFunction<? extends Number> buildExp(
		@Prop(XExp.P.delegate) final IComponentsFunction<? extends Number> delegate) {
		return Maths.exp(delegate);
	}

	@Adapt(XNumberSequence.XGreater.class)
	public IComponentsFunction<Boolean> buildGreater(
			@Prop(XNumberSequence.VALUES) final List<IComponentsFunction<? extends Number>> values
			) {
		return Maths.greater(values);
	}

	@Adapt(XNumberSequence.XGreaterEq.class)
	public IComponentsFunction<Boolean> buildGreaterEq(
			@Prop(XNumberSequence.VALUES) final List<IComponentsFunction<? extends Number>> values
			) {
		return Maths.greaterEq(values);
	}

	@Adapt(XNumberSequence.XLess.class)
	public IComponentsFunction<Boolean> buildLess(
			@Prop(XNumberSequence.VALUES) final List<IComponentsFunction<? extends Number>> values
			) {
		return Maths.less(values);
	}

	@Adapt(XNumberSequence.XLessEq.class)
	public IComponentsFunction<Boolean> buildLessEq(
			@Prop(XNumberSequence.VALUES) final List<IComponentsFunction<? extends Number>> values
			) {
		return Maths.lessEq(values);
	}

	@Adapt(XNumberSequence.XEqualNumbers.class)
	public IComponentsFunction<Boolean> buildAllEquals(
			@Prop(XNumberSequence.VALUES) final List<IComponentsFunction<? extends Number>> values
			) {
		return Maths.eq(values);
	}

	@Adapt(XLookupFunction.XLookupEntry.class)
	public LookupEntry buildLookupEntry(
			@Prop(XLookupEntry.P.coordinates) final List<LookupRule> rules,
			@Prop(XLookupEntry.P.value) final IComponentsFunction<Number> value
			) {
		return new LookupEntry(rules, value);
	}

	@Adapt(XSimQuantum.class)
	public IComponentsFunction<Number> buildSimQuantum(
		final Name name,
		@FromScope(SimulatorConfigurationConstants.GRANULARITY) final int quantum) {
		return ConstantComponentsFunction.<Number>of(name, quantum);
	}

	@Adapt(XFunctionDeclaration.class)
	public Initializable ignoreFunctionDeclaration() {
		return Initializable.NOP;
	}

	@Adapt(value = XFunctionDeclaration.class, cache = true)
	public IComponentsFunction<Number> buildNumberDeclaration(
			@Prop(XFunctionDeclaration.VALUE) final IComponentsFunction<Number> fn
			) {
		return fn;
	}

	@Adapt(XFutureValue.class)
	public IComponentsFunction<Number> buildPredictSum(
			@Prop(XFutureValue.P.horizon) final IComponentsFunction<Number> horizon,
			@Prop(XFutureValue.P.predict) final List<XForesightLevel> foresight,
			@Prop(XFutureValue.P.value) final IComponentsFunction<Number> value) {
		final EnumSet<XForesightLevel> e = EnumSet.of(XForesightLevel.Always);
		e.addAll(foresight);
		return factory.createPredictSum(horizon, e, value);
	}

	@Adapt(XAnnualCost.class)
	public IComponentsFunction<Number> buildAnnualCost(
			final XAnnualCost element
			) {

		// has a list of lists of globs
		// if any list of globs works, the tag is in
		// a list of globs works for a list of tags
		// if each positive glob matches a tag and no negative glob matches a tag.
        // this is total overkill, since globs also have disjunction in them.
        // would be better with just a list of tags

		final ArrayList<Predicate<Collection<String>>> tests = new ArrayList<>();
		for (final List<Glob> condition : element.getMatch()) {
			final Predicate<Collection<String>> test = Glob.requireAndForbid(condition);
			tests.add(test);
		}
        final Predicate<Collection<String>> or = tests.isEmpty() ? Predicates.<Collection<String>>alwaysTrue() : Predicates.or(tests);

		return factory.createAnnualCost(element.isContainsEnd(), or);
	}


	@Adapt(XRound.class)
	public IComponentsFunction<Number> buildRound(
			@Prop(XRound.P.value) final IComponentsFunction<Number> value,
			@Prop(XRound.P.precision) final IComponentsFunction<Number> precision,
			@Prop(XRound.P.direction) final XRoundDirection	direction
			) {
		return Maths.round(value, precision, direction);
    }

    @Adapt(XRiseIn.class)
    public IComponentsFunction<? extends Number> buildRiseIn(
        @Prop(XRiseIn.P.DELEGATE) final IComponentsFunction<? extends Number> value
        ) {

        final IComponentsFunction<? extends Number> priorValue = factory.createPrior(value);
        return Maths.difference(value, priorValue);
    }

    @Adapt(XFallIn.class)
    public IComponentsFunction<? extends Number> buildFallIn(
        @Prop(XRiseIn.P.DELEGATE) final IComponentsFunction<? extends Number> value
        ) {
        // (- (original value) value)
        final IComponentsFunction<? extends Number> priorValue = factory.createPrior(value);
        return Maths.difference(priorValue, value);
    }
}
