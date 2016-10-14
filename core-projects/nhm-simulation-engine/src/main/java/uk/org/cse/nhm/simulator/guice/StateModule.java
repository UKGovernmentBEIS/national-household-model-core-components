package uk.org.cse.nhm.simulator.guice;

import java.util.Collections;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.PrivateModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;
import com.google.inject.util.Providers;

import uk.org.cse.commons.names.Name;
import uk.org.cse.hom.housepropertystore.HouseProperties;
import uk.org.cse.hom.housepropertystore.IHouseProperties;
import uk.org.cse.hom.money.FinancialAttributes;
import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.people.People;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.simulator.obligations.DefaultObligationHistoryProvider;
import uk.org.cse.nhm.simulator.obligations.IObligationHistory;
import uk.org.cse.nhm.simulator.obligations.impl.AnnualMaintenanceObligation;
import uk.org.cse.nhm.simulator.obligations.impl.ObligationScheduler;
import uk.org.cse.nhm.simulator.profile.IProfiler;
import uk.org.cse.nhm.simulator.profile.Profiler;
import uk.org.cse.nhm.simulator.scope.ScopeFactory;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.components.IFlags;
import uk.org.cse.nhm.simulator.state.components.impl.Flags;
import uk.org.cse.nhm.simulator.state.components.impl.Weather;
import uk.org.cse.nhm.simulator.state.dimensions.DimensionCounter;
import uk.org.cse.nhm.simulator.state.dimensions.IFunctionDimension;
import uk.org.cse.nhm.simulator.state.dimensions.behaviour.DefaultHeatingBehaviourProvider;
import uk.org.cse.nhm.simulator.state.dimensions.behaviour.IHeatingBehaviour;
import uk.org.cse.nhm.simulator.state.dimensions.energy.AnnualFuelObligation;
import uk.org.cse.nhm.simulator.state.dimensions.energy.EnergyCalculatorBridgeProvider;
import uk.org.cse.nhm.simulator.state.dimensions.energy.EnergyMeterDimension;
import uk.org.cse.nhm.simulator.state.dimensions.energy.Gasman;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IEnergyCalculatorBridge;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IEnergyMeter;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.dimensions.energy.PowerDimension;
import uk.org.cse.nhm.simulator.state.dimensions.energy.calibration.CalibratedPowerDimension;
import uk.org.cse.nhm.simulator.state.dimensions.energy.calibration.IEnergyCalibrations;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.CarbonFactors;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.ICarbonFactors;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.EmissionsDimension;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.IEmissions;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.ITariffRegistry;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.ITariffs;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.TariffRegistry;
import uk.org.cse.nhm.simulator.state.dimensions.impl.FunctionDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITime;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.RealTimeDimension;
import uk.org.cse.nhm.simulator.state.dimensions.weather.IWeather;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;
import uk.org.cse.nhm.simulator.state.impl.CanonicalState;
import uk.org.cse.nhm.simulator.state.impl.IInternalDimension;
import uk.org.cse.nhm.simulator.transactions.DefaultTransactionHistoryProvider;
import uk.org.cse.nhm.simulator.transactions.DwellingTransactionHistory;
import uk.org.cse.nhm.simulator.transactions.ITransactionRunningTotal;
import uk.org.cse.nhm.simulator.transactions.TransactionRunningTotal;

/**
 * Binds dimensions and state
 * @author hinton
 *
 */
public class StateModule extends AbstractModule  {
	private static final Class<SimulationScoped> SimulationScope = SimulationScoped.class;
	
	@Override
	protected void configure() {
		bind(ScopeFactory.class).in(SimulationScope);
		
		bind(CanonicalState.class).in(SimulationScope);
		
		bind(DimensionCounter.class).in(SimulationScope);
		
		bind(IState.class).to(CanonicalState.class);
		bind(ICanonicalState.class).to(CanonicalState.class);
		
		final Multibinder<IDimension<?>> dimensions = Multibinder.newSetBinder(binder(), new TypeLiteral<IDimension<?>>() {});
		/**
		 * the dimensions which should be copied when duplicating a house
		 * (rather than getting reset)
		 */
		final Multibinder<IDimension<?>> copyDimensions = Multibinder.newSetBinder(binder(), new TypeLiteral<IDimension<?>>() {}, Names.named(IDimension.CONSTRUCT_COPY));
		final Multibinder<IInternalDimension<?>> internalDimensions = Multibinder.newSetBinder(binder(), new TypeLiteral<IInternalDimension<?>>() {});
		
		// these dimensions come from the database
		// they all go into copydimensions because we want to copy them
		// in cons houses action
		
		installDimension(new StandardDimensionModule<StructureModel>("Structure"){})
			.bindDimension(dimensions)
			.bindDimension(copyDimensions)
			.bindInternal(internalDimensions);
		
		installDimension(new StandardDimensionModule<BasicCaseAttributes>("Basics") {})
			.bindDimension(dimensions)
			.bindDimension(copyDimensions)
			.bindInternal(internalDimensions);
		
		installDimension(new StandardDimensionModule<ITechnologyModel>("Technologies") {
		})
			
//			@Override
//			protected Key<?> getManagerType() {
//				// internalize technologies properly
//				return Key.get(TechnologyManager.class);
//			}
//		})
			.bindDimension(dimensions)
			.bindDimension(copyDimensions)
			.bindInternal(internalDimensions);
		
		installDimension(new StandardDimensionModule<People>("People") {}).bindDimension(dimensions)
			.bindDimension(copyDimensions)
			.bindInternal(internalDimensions);
		
		installDimension(new StandardDimensionModule<FinancialAttributes>("Finances") {})
			.bindDimension(dimensions)
			.bindDimension(copyDimensions)
			.bindInternal(internalDimensions);
		
		// these are not from the database
		
		// these next all take simple default values
		// flags do not get copied
		installDimension(new StandardDimensionModule<IFlags>("Flags", new Flags()) {})
			.bindDimension(dimensions)
			.bindInternal(internalDimensions);
		
		installDimension(new StandardDimensionModule<IHouseProperties>("Additional Properties", new HouseProperties(Collections.<String, String>emptyMap())) {})
			.bindDimension(dimensions)
			.bindDimension(copyDimensions)
			.bindInternal(internalDimensions);
		
		// transaction history does not get copied
		bind(ITransactionRunningTotal.class).to(TransactionRunningTotal.class).in(SimulationScope);
		installDimension(new StandardDimensionModule<DwellingTransactionHistory>("Transactions", DefaultTransactionHistoryProvider.class) {})
			.bindDimension(dimensions)
			.bindInternal(internalDimensions);

		// finally these take some more interesting default values which are configured at runtime
		// they both have bindings to providers, which are simulation scoped, and are configured during
		// the simulator builder's setup process.
		// obligations do not get copied
		installDimension(new StandardDimensionModule<IObligationHistory>("Obligations", DefaultObligationHistoryProvider.class) {})
			.bindDimension(dimensions)
			.bindInternal(internalDimensions);
		
		// tariffs /do/ get copied
		installDimension(new StandardDimensionModule<ITariffs>("Tariffs", ITariffRegistry.class) {})
			.bindDimension(dimensions)
			.bindDimension(copyDimensions)
			.bindInternal(internalDimensions);
		
		// heating behaviour does get copied
		installDimension(new StandardDimensionModule<IHeatingBehaviour>("Heating Behaviour", DefaultHeatingBehaviourProvider.class) {})
			.bindDimension(dimensions)
			.bindDimension(copyDimensions)
			.bindInternal(internalDimensions);
    	
		// the next lot of dimensions are all special-cases which require different setup
		// they are all derived values as well.
		
		// none of these ought to get copied
		
    	bind(EnergyMeterDimension.class).in(SimulationScope);
    	internalDimensions.addBinding().to(EnergyMeterDimension.class);
    	bind(new TypeLiteral<IDimension<IEnergyMeter>>(){}).to(EnergyMeterDimension.class);
    	dimensions.addBinding().to(new TypeLiteral<IDimension<IEnergyMeter>>() {});
    	
    	// these are things which arise from running the simulator.
    	bind(PowerDimension.class).in(SimulationScope);
    	internalDimensions.addBinding().to(PowerDimension.class);
    	bind(new TypeLiteral<IDimension<IPowerTable>>() {})
    		.annotatedWith(Names.named("uncalibrated"))
    		.to(PowerDimension.class);
    	dimensions.addBinding()
    		.to(Key.get(new TypeLiteral<IDimension<IPowerTable>>() {},
    				Names.named("uncalibrated")));
    	
    	bind(CalibratedPowerDimension.class).in(SimulationScope);
    	bind(IEnergyCalibrations.class).to(CalibratedPowerDimension.class);
    	internalDimensions.addBinding().to(CalibratedPowerDimension.class);
    	bind(new TypeLiteral<IDimension<IPowerTable>>() {})
    		.to(CalibratedPowerDimension.class);
    	dimensions.addBinding().to(new TypeLiteral<IDimension<IPowerTable>>(){});
    	
    	{
    		install(
    				new PrivateModule() {
						@Override
						protected void configure() {
							bind(String.class).toInstance("Weather");
							
							bind(new TypeLiteral<IComponentsFunction<IWeather>>() {}).toInstance(ConstantComponentsFunction.of(
									Name.of("SAP Weather"),
									Weather.DEFAULT_WEATHER));
							
							final TypeLiteral<FunctionDimension<IWeather>> implementationType = new TypeLiteral<FunctionDimension<IWeather>>() {};
							final TypeLiteral<IFunctionDimension<IWeather>> fDimType = new TypeLiteral<IFunctionDimension<IWeather>>() {};
							final TypeLiteral<IDimension<IWeather>> dimType = new TypeLiteral<IDimension<IWeather>>() {};
							final TypeLiteral<IInternalDimension<IWeather>> internalDimType = new TypeLiteral<IInternalDimension<IWeather>>() {};
							
							bind(implementationType).in(SimulationScope);
				    		
							bind(dimType).to(implementationType);
							bind(fDimType).to(implementationType);
							bind(internalDimType).to(implementationType);
							bind(XForesightLevel.class).toInstance(XForesightLevel.Weather);
				    		
				    		expose(dimType);
				    		expose(fDimType);
				    		expose(internalDimType);
				    		internalDimensions.addBinding().to(internalDimType);
				    		dimensions.addBinding().to(dimType);
						}
					}
    			);
    	}
    	{
    		install(
    				new PrivateModule() {
						@Override
						protected void configure() {
							bind(String.class).toInstance("Carbon Factors");
							
							bind(new TypeLiteral<IComponentsFunction<ICarbonFactors>>() {}).toInstance(ConstantComponentsFunction.of(
																		Name.of("SAP Carbon"),
																		(ICarbonFactors)new CarbonFactors()));
							
							final TypeLiteral<FunctionDimension<ICarbonFactors>> implementationType = new TypeLiteral<FunctionDimension<ICarbonFactors>>() {};
							final TypeLiteral<IFunctionDimension<ICarbonFactors>> fDimType = new TypeLiteral<IFunctionDimension<ICarbonFactors>>() {};
							final TypeLiteral<IDimension<ICarbonFactors>> dimType = new TypeLiteral<IDimension<ICarbonFactors>>() {};
							final TypeLiteral<IInternalDimension<ICarbonFactors>> internalDimType = new TypeLiteral<IInternalDimension<ICarbonFactors>>() {};
							
							
							bind(implementationType).in(SimulationScope);
				    		
							bind(dimType).to(implementationType);
							bind(fDimType).to(implementationType);
				    		bind(internalDimType).to(implementationType);
				    		bind(XForesightLevel.class).toInstance(XForesightLevel.CarbonFactors);
							
				    		expose(dimType);
				    		expose(fDimType);
				    		expose(internalDimType);
				    		internalDimensions.addBinding().to(internalDimType);
				    		dimensions.addBinding().to(dimType);
						}
					}
    			);
    		
    		
    	}
    	
    	bind(RealTimeDimension.class).in(SimulationScope);
    	internalDimensions.addBinding().to(RealTimeDimension.class);
    	bind(new TypeLiteral<IDimension<ITime>>(){}).to(RealTimeDimension.class);
    	bind(ITimeDimension.class).to(RealTimeDimension.class);
    	dimensions.addBinding().to(ITimeDimension.class);
    	
    	bind(EmissionsDimension.class).in(SimulationScope);
    	internalDimensions.addBinding().to(EmissionsDimension.class);
    	bind(new TypeLiteral<IDimension<IEmissions>>() {}).to(EmissionsDimension.class);
    	dimensions.addBinding().to(new TypeLiteral<IDimension<IEmissions>>() {});

    	// these are some energy calculator bits
    	// question here is: can we share the cache between runs
		// since we seed the constants into the scope, it seems like yes
		//        bind(IEnergyCalculatorBridge.class).to(EnergyCalculatorBridge.class).in(SimulationScope);
        //bind(IEnergyCalculator.class).to(EnergyCalculator.class).in(SimulationScope);

		// this should allow us to share an energy calculator bridge /between runs/
		bind(IEnergyCalculatorBridge.class)
			.toProvider(EnergyCalculatorBridgeProvider.class)
			.in(Scopes.SINGLETON);

        bind(IConstants.class).toProvider(Providers.of(DefaultConstants.INSTANCE)).in(SimulationScope);
        
        // and these are a couple of parts of the simulation which should be automatically created
        // that just do some maintenance.
        
        bind(ObligationScheduler.class).in(SimulationScope);
        final Multibinder<Object> eager = Multibinder.newSetBinder(binder(), Object.class, Eager.class);
		eager.addBinding().to(ObligationScheduler.class);
		
		bind(Gasman.class).in(SimulationScope);
		eager.addBinding().to(Gasman.class);
		
		bind(TariffRegistry.class).in(SimulationScope);
		
		bind(ITariffRegistry.class).to(TariffRegistry.class);
		
		bind(IProfiler.class).to(Profiler.class).in(Scopes.SINGLETON);
		
		// default obligations should only be present singly
		
		bind(AnnualMaintenanceObligation.class).in(SimulationScope);
		bind(AnnualFuelObligation.class).in(SimulationScope);
	}
	
	private StandardDimensionModule<?> installDimension(
			final StandardDimensionModule<?> dimensionModule) {
		install(dimensionModule);
		return dimensionModule;
				
	}
}
