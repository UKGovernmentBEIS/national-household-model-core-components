package uk.org.cse.nhm.language.builder.context;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import com.google.common.base.Optional;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.inject.Injector;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IAdaptingScope;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.FromScope;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.adapt.impl.PutScope;
import uk.org.cse.nhm.language.adapt.impl.ReflectingAdapter;
import uk.org.cse.nhm.language.builder.TopLevelAdapter;
import uk.org.cse.nhm.language.builder.function.MapEnum;
import uk.org.cse.nhm.language.definition.context.XCarbonFactorContext;
import uk.org.cse.nhm.language.definition.context.XCarbonFactorContext.XCarbonGroup;
import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.language.definition.fuel.XFullTariff;
import uk.org.cse.nhm.language.definition.fuel.XMethodOfPayment;
import uk.org.cse.nhm.language.definition.fuel.XSimpleTariff;
import uk.org.cse.nhm.language.definition.fuel.XSimpleTariff.XSimpleTariffFuel;
import uk.org.cse.nhm.language.definition.fuel.XTariffBase;
import uk.org.cse.nhm.language.definition.fuel.XTariffCharge;
import uk.org.cse.nhm.language.definition.fuel.XTariffFuel;
import uk.org.cse.nhm.language.definition.fuel.XTariffs;
import uk.org.cse.nhm.language.definition.fuel.extracharges.XExtraCharge;
import uk.org.cse.nhm.language.definition.fuel.extracharges.XExtraChargeAction;
import uk.org.cse.nhm.language.definition.fuel.extracharges.XRemoveChargeAction;
import uk.org.cse.nhm.language.definition.tags.Tag;
import uk.org.cse.nhm.simulator.action.fuels.extracharges.ExtraCharge;
import uk.org.cse.nhm.simulator.factories.IActionFactory;
import uk.org.cse.nhm.simulator.factories.IObjectFunctionFactory;
import uk.org.cse.nhm.simulator.main.Initializable;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.state.dimensions.IFunctionDimension;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.ICarbonFactors;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.FunctionTariff;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.FunctionTariff.FuelRule;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.FunctionTariff.FuelRule.Transactor;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.IExtraCharge;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.ITariff;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.ITariffRegistry;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.ITransaction;

/**
 * Builder adapter for {@link XCarbonFactorContext}
 * 
 * @author hinton
 *
 */
public class FuelPropertyAdapter extends ReflectingAdapter {
	public static final String TARIFF_FUEL_TYPE = "FuelPropertyAdapter.TarriffFuelType";
	final Injector injector;
	final IObjectFunctionFactory functionFactory;
	private final IActionFactory actionFactory;
	
	@Inject
	public FuelPropertyAdapter(final Set<IConverter> delegates, final Injector injector, final IObjectFunctionFactory functionFactory, final IActionFactory actionFactory, final Set<IAdapterInterceptor> interceptors) {
		super(delegates, interceptors);
		this.injector = injector;
		this.functionFactory = functionFactory;
		this.actionFactory = actionFactory;
	}

	@Adapt(XTariffs.class)
	public Initializable buildTariffs(
			@Prop(XTariffs.P.defaultTariffs) final List<ITariff> defaults,
			@Prop(XTariffs.P.otherTariffs) final List<ITariff> others
			) {
		
		final ITariffRegistry registry = injector.getInstance(ITariffRegistry.class);
		for(final ITariff tariff : defaults) {
			registry.addDefaultTariff(tariff);
		}
		
		return Initializable.NOP;
	}
	
	@Adapt(XTariffCharge.class)
	public IComponentsFunction<Number> getCharge(
			@Prop(XTariffCharge.P.VALUE) final IComponentsFunction<Number> value) {
		return value;
	}
	
	@Adapt(XFullTariff.class)
	public ITariff buildStandardTariff(
			@Prop(XTariffBase.P.methodOfPayment) final XMethodOfPayment mop,
			@Prop(XFullTariff.P.fuels) final List<FuelRule> rules
			) {
		return new FunctionTariff(mop, rules);
	}
	
	@Adapt(XTariffFuel.class)
	public FuelRule buildTariffFuel(
			@PutScope(TARIFF_FUEL_TYPE) @Prop(XTariffFuel.P.fuel) final XFuelType fuelType,
			@Prop(XTariffFuel.P.components) final List<Transactor> transactors
			) {
		return new FuelRule(MapEnum.fuel(fuelType), transactors);
	}
	
	@Adapt(XSimpleTariff.class)
	public ITariff buildSimpleTariff(
			@Prop(XTariffBase.P.methodOfPayment) final XMethodOfPayment mop,
			@Prop(XSimpleTariff.P.fuels) final List<FuelRule> rules
			) {
		return buildStandardTariff(mop, rules);
	}
	
	@SuppressWarnings("unchecked")
	@Adapt(XSimpleTariff.XSimpleTariffFuel.class)
	public FuelRule buildSimpleTariffFuel(
			final IAdaptingScope theScope,
			final XSimpleTariffFuel theFuel) {
		return new FuelRule(
				MapEnum.fuel(theFuel.getFuel()), 
				ImmutableList.of(buildTariffCharge(theFuel.getFuel(), Optional.<String>absent(), 
						theFuel.adapt(IComponentsFunction.class, theScope)
						)));
	}
	
	@Adapt(XTariffCharge.class)
	public Transactor buildTariffCharge(
			@FromScope(TARIFF_FUEL_TYPE) final XFuelType fuelType,
			@Prop(XTariffCharge.P.payee) final Optional<String> payee,
			@Prop(XTariffCharge.P.VALUE) final IComponentsFunction<Number> charge
			) {
		return new Transactor(charge, payee.or(ITransaction.Counterparties.ENERGY_COMPANIES), ImmutableSet.<String>of(MapEnum.fuel(fuelType).getName()));
	}
	
	@Adapt(XCarbonFactorContext.class)
	public Initializable buildFuelProperties(
			@Prop(XCarbonFactorContext.P.groups) final List<FuelGroup> groups
			) {
		// create a fuel price function setter, which is a bit of a monster.
		final FuelPriceFunctionSetter fpfs = injector.getInstance(FuelPriceFunctionSetter.class);
		
		final Multimap<IComponentsFunction<Number>, FuelType> multimap = HashMultimap.create();
		
		for (final FuelGroup fg : groups) {
			multimap.putAll(fg.carbonFactor, fg.fuels);
		}
		
		fpfs.setFunction(new CarbonFactorFunction(multimap));

		return Initializable.NOP;
	}

	public static class FuelPriceFunctionSetter implements Initializable {
		private final IFunctionDimension<ICarbonFactors> dimension;
		private IComponentsFunction<ICarbonFactors> function;
		
		@Inject
		public FuelPriceFunctionSetter(final IFunctionDimension<ICarbonFactors> dimension) {
			this.dimension = dimension;
		}

		public void setFunction(final IComponentsFunction<ICarbonFactors> function) {
			this.function = function;
		}
		
		@Override
		public void initialize() throws NHMException {
			dimension.setFunction(function);
		}
	}
	
	public static final class FuelGroup {
		public final Set<FuelType> fuels;
		public final IComponentsFunction<Number> carbonFactor;
		public FuelGroup(final Set<FuelType> fuels, final IComponentsFunction<Number> carbonFactor) {		
			this.fuels = fuels;
			this.carbonFactor = carbonFactor;
		}
	}
	
	@Adapt(XCarbonGroup.class)
	public FuelGroup buildFuelGroup(
			@Prop(XCarbonFactorContext.P.group_fuels) final List<XFuelType> fuels,
			@Prop(XCarbonFactorContext.P.group_factor) final IComponentsFunction<Number> factor
			) {
		
		final EnumSet<FuelType> internalFuels = EnumSet.noneOf(FuelType.class);
		
		for (final XFuelType xft : fuels) {
			internalFuels.add(MapEnum.fuel(xft));
		}
		
		return new FuelGroup(internalFuels, factor);
	}
	
	@Adapt(XExtraCharge.class)
	public IExtraCharge buildExtraCharge(
			@FromScope(TopLevelAdapter.POLICY_NAME) final Optional<String> policyName,
			@Prop(XExtraCharge.P.fuel) final Optional<XFuelType> fuel, 
			@Prop(XExtraCharge.P.tags) final List<Tag> tags,
			@Prop(XTariffCharge.P.payee) final Optional<String> payee,
			@Prop(XTariffCharge.P.VALUE) final IComponentsFunction<Number> charge,
			@Prop(XExtraCharge.P.dependsOn) final List<IExtraCharge> dependencies
			) {
		
		final Optional<FuelType> maybeFuel = fuel.isPresent() ?
				Optional.of(MapEnum.fuel(fuel.get())) : 
				Optional.<FuelType>absent();
		
		return new ExtraCharge(maybeFuel, payee.or(policyName.or(ITransaction.Counterparties.ENERGY_COMPANIES)), Tag.asSet(tags), charge, ImmutableSet.copyOf(dependencies));
	}
	
	@Adapt(XRemoveChargeAction.class)
	public IComponentsAction buildRemoveChargeAction(
		@Prop(XRemoveChargeAction.P.charge) final Optional<IExtraCharge> extraCharge
			) {
		return actionFactory.createRemoveChargeAction(extraCharge);
	}
	
	@Adapt(XExtraChargeAction.class)
	public IComponentsAction buildExtraChargeAction(
			@Prop(XExtraChargeAction.P.charge) final IExtraCharge charge) { 
		return actionFactory.createExtraChargeAction(charge);
	}
	
}
