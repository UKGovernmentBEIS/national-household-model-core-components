package uk.org.cse.nhm.simulator.factories;

import java.util.List;
import java.util.Set;

import org.joda.time.Period;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.commons.Glob;
import uk.org.cse.nhm.energycalculator.api.IHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.types.EnergyCalculatorType;
import uk.org.cse.nhm.energycalculator.api.types.SiteExposureType;
import uk.org.cse.nhm.simulator.action.CaseAction;
import uk.org.cse.nhm.simulator.action.CaseAction.Case;
import uk.org.cse.nhm.simulator.action.ConstructHousesAction;
import uk.org.cse.nhm.simulator.action.DelayedAction;
import uk.org.cse.nhm.simulator.action.DemolishHousesAction;
import uk.org.cse.nhm.simulator.action.FlaggedComponentsAction;
import uk.org.cse.nhm.simulator.action.FlaggedStateAction;
import uk.org.cse.nhm.simulator.action.HeatingScheduleAction;
import uk.org.cse.nhm.simulator.action.IUnifiedReport;
import uk.org.cse.nhm.simulator.action.ModifyFlagsAction;
import uk.org.cse.nhm.simulator.action.OrderedChoiceAction;
import uk.org.cse.nhm.simulator.action.OrderedPackageAction;
import uk.org.cse.nhm.simulator.action.ReducedInternalGainsAction;
import uk.org.cse.nhm.simulator.action.RepeatAction;
import uk.org.cse.nhm.simulator.action.SetInterzoneSpecificHeatTransferAction;
import uk.org.cse.nhm.simulator.action.SetLivingAreaFractionAction;
import uk.org.cse.nhm.simulator.action.SetSiteExposureAction;
import uk.org.cse.nhm.simulator.action.SetThermalBridgingFactorAction;
import uk.org.cse.nhm.simulator.action.SometimesAction;
import uk.org.cse.nhm.simulator.action.TemperaturesAction;
import uk.org.cse.nhm.simulator.action.choices.ChoiceAction;
import uk.org.cse.nhm.simulator.action.choices.FallbackPicker;
import uk.org.cse.nhm.simulator.action.choices.FilterPicker;
import uk.org.cse.nhm.simulator.action.choices.MaximumPicker;
import uk.org.cse.nhm.simulator.action.choices.WeightedRandomPicker;
import uk.org.cse.nhm.simulator.action.fuels.ChangeTariffsAction;
import uk.org.cse.nhm.simulator.action.fuels.extracharges.ExtraChargeAction;
import uk.org.cse.nhm.simulator.action.fuels.extracharges.RemoveChargeAction;
import uk.org.cse.nhm.simulator.action.hypothetical.EnergyCalculatorAction;
import uk.org.cse.nhm.simulator.action.hypothetical.HypotheticalSetAction;
import uk.org.cse.nhm.simulator.action.hypothetical.SapOccupancyAction;
import uk.org.cse.nhm.simulator.action.hypothetical.UseUncalibratedEnergyAction;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope.IPicker;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.sequence.ChangeValue;
import uk.org.cse.nhm.simulator.sequence.ChangeValue.Variable;
import uk.org.cse.nhm.simulator.sequence.ConsumeAction;
import uk.org.cse.nhm.simulator.sequence.FailUnless;
import uk.org.cse.nhm.simulator.sequence.ISequenceSpecialAction;
import uk.org.cse.nhm.simulator.sequence.SequenceAction;
import uk.org.cse.nhm.simulator.sequence.SnapshotAction;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.ICarbonFactors;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.IExtraCharge;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.ITariff;
import uk.org.cse.nhm.simulator.state.dimensions.weather.IWeather;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public interface IActionFactory {
	public ModifyFlagsAction createFlagsAction(final boolean setFlag,
			final String name);

	public DemolishHousesAction createDestroyAction();

	public ConstructHousesAction createConstructAction();

	public TemperaturesAction createTemperaturesAction(
			@Assisted("livingArea") final Optional<IComponentsFunction<Number>> livingAreaTemperature,
			@Assisted("threshold") final Optional<IComponentsFunction<Number>> thresholdTemperature,
			@Assisted("delta") final Optional<IComponentsFunction<Number>> deltaTemperature,
			@Assisted("rest") final Optional<IComponentsFunction<Number>> restTemperature,
			@Assisted("restHeatedProportion") final Optional<IComponentsFunction<Number>> restHeatedProportion,
			@Assisted final Optional<boolean[]> heatingMonths);
	
	public HeatingScheduleAction createHeatingScheduleAction(final IHeatingSchedule iHeatingSchedule);
	
	public SetLivingAreaFractionAction createLivingAreaFractionAction(final double fraction);
	
	public SetSiteExposureAction createSiteExposureAction(
			@Assisted("siteExposure") SiteExposureType siteExposure
			);
	
	public SetInterzoneSpecificHeatTransferAction createInterzoneSpecificHeatTransferAction(
			@Assisted("interzoneSpecificHeatTransfer") IComponentsFunction<Number> interzoneSpecificHeatTransfer
			);

	public ChangeTariffsAction createChangeTariffsAction(@Assisted final List<ITariff> tariffs);

	public FlaggedComponentsAction createFlaggedComponentsAction(
			@Assisted("test") final List<Glob> testFlags,
			@Assisted("update") final List<Glob> updateFlags,
			@Assisted final List<IUnifiedReport> reports,
			@Assisted final IComponentsAction delegate
			);

	public FlaggedStateAction createFlaggedStateAction(
		@Assisted("test") final List<Glob> testFlags,
		@Assisted("update") final List<Glob> updateFlags,
		@Assisted final List<IUnifiedReport> reports,
		@Assisted final IStateAction delegate);
	
	public HypotheticalSetAction<IWeather> createSetWeather(
			@Assisted final IWeather weather
			);
	
	public HypotheticalSetAction<ICarbonFactors> createSetCarbon(
			@Assisted final ICarbonFactors factors
			);
	
	public SapOccupancyAction createSapOccupancy();

	public ChoiceAction createChoice(
			@Assisted final IPicker selector,
			@Assisted final List<IComponentsAction> alternatives);

	public FilterPicker createSelectByFilter(
			@Assisted final List<ISequenceSpecialAction> bindings,
			@Assisted final IComponentsFunction<Boolean> test,
			@Assisted final IPicker delegate);

	public MaximumPicker createSelectMaximum(
			@Assisted final List<ISequenceSpecialAction> bindings,
			@Assisted final IComponentsFunction<Number> objective);

	public WeightedRandomPicker createSelectRandomWeighted(
			@Assisted final List<ISequenceSpecialAction> bindings,
			@Assisted final IComponentsFunction<Number> weight);

	public DelayedAction createDelayedAction(
			@Assisted final IComponentsAction action,
			@Assisted final Period delay);

	public RemoveChargeAction createRemoveChargeAction(
			@Assisted final Optional<IExtraCharge> extraCharge);

	public ExtraChargeAction createExtraChargeAction(
			@Assisted final IExtraCharge charge);

	public FallbackPicker createSelectFallback(
			@Assisted final List<ISequenceSpecialAction> bindings,
			@Assisted final List<IPicker> delegates);

	public SometimesAction createSometimesAction(
			@Assisted final IComponentsFunction<Number> chance, 
			@Assisted final IComponentsAction delegate);

	public UseUncalibratedEnergyAction createDecalibrationAction();

	public CaseAction createCaseAction(
			@Assisted final List<Case> cases, 
			@Assisted final IComponentsAction defaultAction
			);
	
	public SequenceAction createSequenceAction(final List<IComponentsAction> delegates, @Assisted("all") final boolean allMode, @Assisted("hide") final Set<String> immutableSet);
	
	public ChangeValue.Setter createValueSetter(@Assisted final List<Variable> variables,
                                                @Assisted final List<IComponentsFunction<Number>> values,
                                                @Assisted final List<IComponentsAction> hypotheses);

	public ChangeValue.Increaser createValueIncreaser(@Assisted final List<Variable> variables,
                                                        @Assisted final List<IComponentsFunction<Number>> values,
                                                        @Assisted final List<IComponentsAction> hypotheses);
    
	public ChangeValue.Decreaser createValueDecreaser(@Assisted final List<Variable> variables,
                                                        @Assisted final List<IComponentsFunction<Number>> values,
                                                        @Assisted final List<IComponentsAction> hypotheses);
    
	public SnapshotAction createSnapshotAction(final String name,
			final List<IComponentsAction> changes);

	public RepeatAction createRepeatAction(@Assisted final int times,
			@Assisted("while") final Optional<IComponentsFunction<Boolean>> whilst,
			@Assisted final IComponentsAction delegate,
			@Assisted("until") final Optional<IComponentsFunction<Boolean>> until);
	
	public OrderedPackageAction createOrderedPackageAction(
			@Assisted final IComponentsFunction<Number> objective,
			@Assisted final boolean ascending, 
			@Assisted final List<IComponentsAction> actions
			);
	
	public OrderedChoiceAction createOrderedChoiceAction(
			@Assisted final IComponentsFunction<Number> objective,
			@Assisted final boolean ascending, 
			@Assisted final List<IComponentsAction> actions
			);

    public ConsumeAction createConsumeAction(Variable variable, IComponentsFunction<Number> amount);
    public FailUnless createFailUnless(IComponentsFunction<Boolean> amount);
    
    public EnergyCalculatorAction createEnergyCalculatorAction(
    		@Assisted final EnergyCalculatorType calculatorType);

	public SetThermalBridgingFactorAction createThermalBridgingFactorAction(
			@Assisted IComponentsFunction<Number> thermalBridgingFactor);

	public ReducedInternalGainsAction createReducedInternalGainsAction();
}
