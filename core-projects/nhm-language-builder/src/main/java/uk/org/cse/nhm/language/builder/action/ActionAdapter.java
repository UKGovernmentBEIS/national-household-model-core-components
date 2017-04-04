package uk.org.cse.nhm.language.builder.action;

import java.util.EnumMap;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.Period;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;

import uk.org.cse.commons.names.ISettableIdentified;
import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.energycalculator.api.IHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.IWeather;
import uk.org.cse.nhm.energycalculator.api.impl.DailyHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.impl.SevenDayHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.types.MonthType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.adapt.impl.ReflectingAdapter;
import uk.org.cse.nhm.language.builder.function.MapEnum;
import uk.org.cse.nhm.language.definition.action.XCaseAction;
import uk.org.cse.nhm.language.definition.action.XCaseAction.XCaseActionWhen;
import uk.org.cse.nhm.language.definition.action.XConstructAction;
import uk.org.cse.nhm.language.definition.action.XDelayedAction;
import uk.org.cse.nhm.language.definition.action.XDestroyAction;
import uk.org.cse.nhm.language.definition.action.XDoNothingAction;
import uk.org.cse.nhm.language.definition.action.XFailAction;
import uk.org.cse.nhm.language.definition.action.XHeatingScheduleAction;
import uk.org.cse.nhm.language.definition.action.XHeatingScheduleAction.XHeatingDays;
import uk.org.cse.nhm.language.definition.action.XHeatingScheduleAction.XHeatingDays.HeatingInterval;
import uk.org.cse.nhm.language.definition.action.XHeatingTemperaturesAction;
import uk.org.cse.nhm.language.definition.action.XHeatingTemperaturesAction.XMonth;
import uk.org.cse.nhm.language.definition.action.XOrderedAction;
import uk.org.cse.nhm.language.definition.action.XReducedInternalGainsAction;
import uk.org.cse.nhm.language.definition.action.XRepeatAction;
import uk.org.cse.nhm.language.definition.action.XSetInterzoneSpecificHeatTransferAction;
import uk.org.cse.nhm.language.definition.action.XSetLivingAreaFractionAction;
import uk.org.cse.nhm.language.definition.action.XSetSiteExposureAction;
import uk.org.cse.nhm.language.definition.action.XSetThermalBridgingFactorAction;
import uk.org.cse.nhm.language.definition.action.XSometimesAction;
import uk.org.cse.nhm.language.definition.action.hypothetical.XCounterfactualCalculator;
import uk.org.cse.nhm.language.definition.action.hypothetical.XCounterfactualCarbon;
import uk.org.cse.nhm.language.definition.action.hypothetical.XCounterfactualWeather;
import uk.org.cse.nhm.language.definition.action.hypothetical.XDecalibrateEnergyAction;
import uk.org.cse.nhm.language.definition.action.hypothetical.XSapOccupancyAction;
import uk.org.cse.nhm.language.definition.fuel.XChangeTariffsAction;
import uk.org.cse.nhm.language.definition.sequence.XActionDeclaration;
import uk.org.cse.nhm.language.definition.sequence.XFunctionDeclaration;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.action.CaseAction;
import uk.org.cse.nhm.simulator.action.CaseAction.Case;
import uk.org.cse.nhm.simulator.action.DoNothingAction;
import uk.org.cse.nhm.simulator.action.FailAction;
import uk.org.cse.nhm.simulator.factories.IActionFactory;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.Initializable;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.CarbonFactors;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.ITariff;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;

public class ActionAdapter extends ReflectingAdapter {
	final IActionFactory measureFactory;

	@Inject
	public ActionAdapter(final Set<IConverter> delegates, final IActionFactory measureFactory, final Set<IAdapterInterceptor> interceptors) {
		super(delegates, interceptors);
		this.measureFactory = measureFactory;
	}

	@Adapt(XDoNothingAction.class)
	public IComponentsAction buildDoNothing() {
		return new DoNothingAction();
	}

	@Adapt(XConstructAction.class)
	public IStateAction buildConstructAction() {
		return measureFactory.createConstructAction();
	}

	@Adapt(XDestroyAction.class)
	public IStateAction buildDestroyAction() {
		return measureFactory.createDestroyAction();
	}

	 @Adapt(XHeatingTemperaturesAction.class)
	 public IComponentsAction buildHeatingTemperatureAction(
			 @Prop(XHeatingTemperaturesAction.P.livingAreaTemperature) final Optional<IComponentsFunction<Number>> livingAreaTemperature,
			 @Prop(XHeatingTemperaturesAction.P.temperatureDifference) final Optional<IComponentsFunction<Number>> deltaTemperature,
			 @Prop(XHeatingTemperaturesAction.P.restofDwellingTemperature) final Optional<IComponentsFunction<Number>> restTemperature,
			 @Prop(XHeatingTemperaturesAction.P.restOfDwellingHeatedProportion) final Optional<IComponentsFunction<Number>> restHeatedProportion,
			 @Prop(XHeatingTemperaturesAction.P.desiredHeatingMonths) final List<XMonth> heatingMonths
					 ) {

		 final Set<MonthType> months;
		 if (heatingMonths.isEmpty()) {
			 months = null;
		 } else {

			 final Builder<MonthType> builder = ImmutableSet.<MonthType>builder();
			 for (final XMonth m : heatingMonths) {
				 builder.add(MapEnum.month(m));
			 }
			 months = builder.build();
		 }

		 return measureFactory.createTemperaturesAction(
				 livingAreaTemperature,
				 deltaTemperature,
				 restTemperature,
				 restHeatedProportion,
				 Optional.fromNullable(months)
				 );
	 }

	 @Adapt(XSetLivingAreaFractionAction.class)
	 public IComponentsAction buildSetLivingAreaFractionAction(@Prop(XSetLivingAreaFractionAction.P.fraction) final double value) {
		 return measureFactory.createLivingAreaFractionAction(value);
	 }

	 @Adapt(XSetSiteExposureAction.class)
	 public IComponentsAction buildSetSiteExposureAction(
			 @Prop(XSetSiteExposureAction.P.siteExposure) final XSetSiteExposureAction.XSiteExposureType siteExposure
			 ) {
		 return measureFactory.createSiteExposureAction(MapEnum.siteExposure(siteExposure));
	 }

	 @Adapt(XSetThermalBridgingFactorAction.class)
	 public IComponentsAction buildSetThermalBridgingFactorAction(
			 @Prop(XSetThermalBridgingFactorAction.P.thermalBridgingFactor) final IComponentsFunction<Number> factor
			 ) {
		 return measureFactory.createThermalBridgingFactorAction(factor);
	 }

	 @Adapt(XSetInterzoneSpecificHeatTransferAction.class)
	 public IComponentsAction buildSetInterzoneSpecificHeatTransferAction(
			 @Prop(XSetInterzoneSpecificHeatTransferAction.P.interzoneSpecificHeatTransfer) final IComponentsFunction<Number> interzoneSpecificHeatTransfer
			 ) {
		 return measureFactory.createInterzoneSpecificHeatTransferAction(interzoneSpecificHeatTransfer);
	 }

	 @Adapt(XReducedInternalGainsAction.class)
	 public IComponentsAction buildReducedInternalGainsAction() {
		 return measureFactory.createReducedInternalGainsAction();
	 }

	 @Adapt(XHeatingScheduleAction.class)
	 public IComponentsAction buildHeatingScheduleAction(final Name id, final XHeatingScheduleAction x) {
		 return measureFactory.createHeatingScheduleAction(buildHeatingSchedule(id, x.getSchedule()));
	 }

	static IHeatingSchedule buildHeatingSchedule(final Name name, final List<XHeatingDays> schedule) {
		final DailyHeatingSchedule[] days = new DailyHeatingSchedule[7];
		// initialize to the off schedule
		for (int i = 0; i<days.length; i++) {
			days[i] = new DailyHeatingSchedule();
		}

		for (final XHeatingDays d : schedule) {
			switch (d.getOn()) {
			case Monday:
			case Tuesday:
			case Wednesday:
			case Thursday:
			case Friday:
			case Saturday:
			case Sunday:
				unionDaySchedule(d.getHeating(), days[d.getOn().ordinal()]);
				break;
			case Weekdays:
				for (int i = 0; i<5; i++) unionDaySchedule(d.getHeating(), days[i]);;
				break;
			case Weekends:
				unionDaySchedule(d.getHeating(), days[5]);
				unionDaySchedule(d.getHeating(), days[6]);
				break;
			case AllDays:
				for (int i = 0; i<7; i++) unionDaySchedule(d.getHeating(), days[i]);
				break;
			default:
				throw new RuntimeException("Do not know how to handle "+d + ". This should never happen");
			}
		}

		return new SevenDayHeatingSchedule(name.getName(), days);
	}

	static void unionDaySchedule(final List<HeatingInterval> heating, final DailyHeatingSchedule dhs) {
		// construct the array of on/off times from the list of heating intervals.
		final boolean[] hours = new boolean[24];
		for (final HeatingInterval hi : heating) {
			final int on = Math.min(hi.getBetween(), hi.getAnd());
			final int off = Math.max(hi.getBetween(), hi.getAnd());
			for (int i = on; i<off; i++) {
				hours[i] = true;
			}
		}
		boolean wasOn = false;
		int lastSwitchOn = 0;
		for (int i = 0; i<hours.length; i++) {
			if (hours[i] != wasOn) {
				if (wasOn == true) {
					dhs.addHeatingPeriod(60d*lastSwitchOn, 60d * i);
				} else {
					lastSwitchOn = i;
				}
			}
			wasOn = hours[i];
		}
		if (wasOn) {
			dhs.addHeatingPeriod(60d*lastSwitchOn, 60d*24);
		}
	}

	@Adapt(XChangeTariffsAction.class)
	public IComponentsAction buildChangeTariffsAction(
			@Prop(XChangeTariffsAction.P.tariffs) final List<ITariff> tariffs
			) {
		return measureFactory.createChangeTariffsAction(tariffs);
	}

	@Adapt(XCounterfactualCarbon.class)
	public IComponentsAction buildCounterfactualCarbonAction(final XCounterfactualCarbon carbon) {
		final EnumMap<FuelType, Double> m = new EnumMap<>(FuelType.class);

		m.put(FuelType.MAINS_GAS, 			carbon.getMainsGas());
		m.put(FuelType.BULK_LPG,  			carbon.getBulkLPG());
		m.put(FuelType.BOTTLED_LPG, 		carbon.getBottledLPG());
		// Electricity is already counted under PEAK and OFFPEAK, although 0 units should have been used anyway.
		m.put(FuelType.ELECTRICITY,			0d);
		m.put(FuelType.EXPORTED_ELECTRICITY,carbon.getExportedElectricity());
		m.put(FuelType.PEAK_ELECTRICITY, 	carbon.getPeakElectricity());
		m.put(FuelType.OFF_PEAK_ELECTRICITY,carbon.getOffPeakElectricity());
		m.put(FuelType.OIL, 				carbon.getOil());
		m.put(FuelType.HOUSE_COAL, 			carbon.getHouseCoal());
		m.put(FuelType.BIOMASS_WOOD, 		carbon.getBiomassWood());
		m.put(FuelType.BIOMASS_PELLETS, 	carbon.getBiomassPellets());
		m.put(FuelType.BIOMASS_WOODCHIP,	carbon.getBiomassWoodchip());
		m.put(FuelType.COMMUNITY_HEAT, 		carbon.getCommunityHeat());
		m.put(FuelType.PHOTONS, 			carbon.getPhotons());

		return measureFactory.createSetCarbon(CarbonFactors.of(m));
	}

	@Adapt(XCounterfactualCalculator.class)
	public IComponentsAction buildCounterFactualEnergyCalculatorAction(final XCounterfactualCalculator calculator) {
		return measureFactory.createEnergyCalculatorAction(
				MapEnum.energyCalc(calculator.getCalculatorType()));
	}

	@Adapt(XSapOccupancyAction.class)
	public IComponentsAction buildSapOccupancyAction() {
		return measureFactory.createSapOccupancy();
	}

	@Adapt(XDecalibrateEnergyAction.class)
	public IComponentsAction buildDecalibrateEnergyAction() {
		return measureFactory.createDecalibrationAction();
	}

	@Adapt(XCounterfactualWeather.class)
	public IComponentsAction buildCounterfactualWeatherAction(
			@Prop(XCounterfactualWeather.P.weather) final ConstantComponentsFunction<IWeather> weather) {
		return measureFactory.createSetWeather(weather.getValue());
	}

	@Adapt(XDelayedAction.class)
	public IComponentsAction adaptDelayedAction(
			@Prop(XDelayedAction.P.action) final IComponentsAction action,
			@Prop(XDelayedAction.P.delay) final Period delay) {
		return measureFactory.createDelayedAction(action, delay);
	}

	@Adapt(XSometimesAction.class)
	public IComponentsAction adaptSometimesAction(
			@Prop(XSometimesAction.P.chance) final IComponentsFunction<Number> chance,
			@Prop(XSometimesAction.P.delegate) final IComponentsAction delegate) {
		return measureFactory.createSometimesAction(chance, delegate);
	}

	@Adapt(XCaseAction.XCaseActionWhen.class)
	public CaseAction.Case adaptCaseActionCase(
			@Prop(XCaseActionWhen.P.test) final IComponentsFunction<Boolean> test,
			@Prop(XCaseActionWhen.P.action) final IComponentsAction action
			) {
		return new CaseAction.Case(test, action);
	}

	@Adapt(XCaseAction.class)
	public IComponentsAction adaptCaseAction(
			@Prop(XCaseAction.P.whens) final List<Case> cases,
			@Prop(XCaseAction.P.defaultAction) final IComponentsAction defaultAction) {
		return measureFactory.createCaseAction(cases, defaultAction);
	}

	@Adapt(XRepeatAction.class)
	public IComponentsAction buildRepeatAction(
			@Prop(XRepeatAction.P.times) final int times,
			@Prop(XRepeatAction.P.untilCondition) final Optional<IComponentsFunction<Boolean>> until,
			@Prop(XRepeatAction.P.whileCondition) final Optional<IComponentsFunction<Boolean>> whilst,
			@Prop(XRepeatAction.P.delegate) final IComponentsAction delegate
			) {
		return measureFactory.createRepeatAction(
				times,
				whilst,
				delegate,
				until
				);
	}

	@Adapt(XOrderedAction.class)
	public IStateAction buildOrderedAction(
			@Prop(XOrderedAction.P.objective) final IComponentsFunction<Number> objective,
			@Prop(XOrderedAction.P.ascending) final boolean ascending,
			@Prop(XOrderedAction.P.DELEGATES) final List<IComponentsAction> actions,
			@Prop(XOrderedAction.P.packages) final boolean packages
			) {
		if (packages) {
			return measureFactory.createOrderedPackageAction(objective, ascending, actions);
		} else {
			return measureFactory.createOrderedChoiceAction(objective, ascending, actions);
		}
	}

	@Adapt(XActionDeclaration.class)
	public Initializable ignoreActionDeclaration() {
		return Initializable.NOP;
	}

	@Adapt(value = XActionDeclaration.class, cache = true)
	public IComponentsAction buildActionDeclaration(
			@Prop(XFunctionDeclaration.VALUE) final IComponentsAction fn
			) {
		return new Wrapper(fn);
	}

	static class Wrapper extends AbstractNamed implements IComponentsAction {
		private final IComponentsAction fn;

		public Wrapper(final IComponentsAction fn) {
			this.fn = fn;
		}

		@Override
		public void setIdentifier(final Name newName) {
			super.setIdentifier(newName);
			if (!(fn instanceof Wrapper) && fn instanceof ISettableIdentified) {
				((ISettableIdentified) fn).setIdentifier(newName);
			}
		}

		@Override
		public StateChangeSourceType getSourceType() {
			return fn.getSourceType();
		}

		@Override
		public boolean apply(final ISettableComponentsScope scope, final ILets lets)
				throws NHMException {
			return fn.apply(scope, lets);
		}

		@Override
		public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
			return fn.isSuitable(scope, lets);
		}

		@Override
		public boolean isAlwaysSuitable() {
			return fn.isAlwaysSuitable();
		}
	}

	@Adapt(XFailAction.class)
	public IComponentsAction buildFailAction() {
		return new FailAction();
	}
}
