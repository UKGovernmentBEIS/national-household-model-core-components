package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.HashSet;

import com.google.common.base.Optional;

import uk.org.cse.nhm.hom.emf.technologies.EmitterType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IHeatPump;
import uk.org.cse.nhm.hom.emf.technologies.IHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IPointOfUseWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.IPrimarySpaceHeater;
import uk.org.cse.nhm.hom.emf.technologies.IRoomHeater;
import uk.org.cse.nhm.hom.emf.technologies.ISpaceHeater;
import uk.org.cse.nhm.hom.emf.technologies.IStorageHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirCirculator;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem;
import uk.org.cse.nhm.hom.emf.technologies.IWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersFactory;
import uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilersFactoryImpl;
import uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesFactoryImpl;
import uk.org.cse.nhm.hom.emf.util.Efficiency;
import uk.org.cse.nhm.hom.emf.util.impl.TechnologyOperations;

public class TechnologyTestBuilder {
	
	private final ITechnologiesFactory factory;
	private final ITechnologyModel model;
	private final TechnologyOperations operations;
	private final IBoilersFactory boilerFactory;

	public TechnologyTestBuilder() {
		factory = TechnologiesFactoryImpl.init();
		boilerFactory = BoilersFactoryImpl.init();
		operations = new TechnologyOperations();
		model = factory.createTechnologyModel();
	}
	
	public TechnologyTestBuilder addCentralWaterHeating(final CentralWaterHeater type) {
		return addCentralWaterHeating(type, Optional.<Double>absent());
	}
	
	public TechnologyTestBuilder addCentralWaterHeating(final CentralWaterHeater type, final double efficiency) {
		return addCentralWaterHeating(type, Optional.of(efficiency));
	}
	
	private TechnologyTestBuilder addCentralWaterHeating(final CentralWaterHeater type, final Optional<Double> maybeEfficiency) {
		final ICentralWaterSystem centralHotWater = factory.createCentralWaterSystem();
		centralHotWater.setPrimaryWaterHeater(createCentralWaterHeater(type, maybeEfficiency));
		model.setCentralWaterSystem(centralHotWater);
		
		return this;
	}
	
	private ICentralWaterHeater createCentralWaterHeater(final CentralWaterHeater type, final Optional<Double> maybeEfficiency) {
		switch(type) {
		case Immersion:
			return factory.createImmersionHeater();
		case Solar:
			return factory.createSolarWaterHeater();
		case WarmAir:
			final IWarmAirCirculator circulator = factory.createWarmAirCirculator();
			final IWarmAirSystem system = factory.createWarmAirSystem();
			if (maybeEfficiency.isPresent()) {
				system.setEfficiency(Efficiency.fromDouble(maybeEfficiency.get()));
			}
			circulator.setWarmAirSystem(system);
			return circulator;
		default:
			throw new IllegalArgumentException("Unknown central water heater type " + type);
		}
	}

	public void addWaterHeating(final WaterHeater type) {
		addWaterHeating(type, Optional.<Double>absent());
	}
	
	public void addWaterHeating(final WaterHeater type, final double efficiency) {
		addWaterHeating(type, Optional.of(efficiency));
	}
	
	private void addWaterHeating(final WaterHeater type, final Optional<Double> maybeEfficiency) {
		final IWaterHeater waterHeater = createWaterHeater(type, maybeEfficiency);
		
		if (type.isPrimary()) {
			model.setCentralWaterSystem((ICentralWaterSystem) waterHeater);
		} else {
			model.setSecondaryWaterHeater(waterHeater);
		}
	}
	
	private IWaterHeater createWaterHeater(final WaterHeater type, final Optional<Double> maybeEfficiency) {
		switch (type) {
		case CentralHotWater:
			return factory.createCentralWaterSystem();
		case ElectricShower:
			return factory.createElectricShower();
		case PointOfUse:
			final IPointOfUseWaterHeater pointOfUse = factory.createPointOfUseWaterHeater();
			if (maybeEfficiency.isPresent()) {
				pointOfUse.setEfficiency(Efficiency.fromDouble(maybeEfficiency.get()));
			}
			return pointOfUse;
		default:
			throw new IllegalArgumentException("Unknown water heater type " + type);
		}
	}

	public TechnologyTestBuilder addSpaceHeating(final SpaceHeater type) {
		return addSpaceHeating(type, Optional.<FuelType>absent(), Optional.<Double>absent());
	}
	
	public TechnologyTestBuilder addSpaceHeating(final SpaceHeater type, final double responsiveness) {
		return addSpaceHeating(type, Optional.<FuelType>absent(), Optional.of(responsiveness));
	}
	
	public TechnologyTestBuilder addSpaceHeating(final SpaceHeater type, final FuelType fuel) {
		return addSpaceHeating(type, Optional.of(fuel), Optional.<Double>absent());
	}
	
	private TechnologyTestBuilder addSpaceHeating(final SpaceHeater type, final Optional<FuelType> maybeFuel, final Optional<Double> maybeResponsiveness) {
		final ISpaceHeater spaceHeater = createSpaceHeater(type, maybeFuel, maybeResponsiveness);
		
		if (type.isPrimary()) {
			model.setPrimarySpaceHeater((IPrimarySpaceHeater) spaceHeater);
		} else {
			model.setSecondarySpaceHeater((IRoomHeater) spaceHeater);
		}
		
		return this;
	}
	
	public TechnologyTestBuilder addHeatSource(final HeatSource type) {
		return addHeatSource(type, Optional.<FuelType>absent(), EmitterType.RADIATORS, Optional.<Double>absent());
	}
	
	public TechnologyTestBuilder addHeatSource(final HeatSource type, final double efficiency) {
		return addHeatSource(type, Optional.<FuelType>absent(), EmitterType.RADIATORS, Optional.of(efficiency));
	}
	
	public TechnologyTestBuilder addHeatSource(final HeatSource type, final FuelType fuel) {
		return addHeatSource(type, Optional.of(fuel), EmitterType.RADIATORS, Optional.<Double>absent());
	}
	
	public TechnologyTestBuilder addHeatSource(final HeatSource type, final EmitterType emitters) {
		return addHeatSource(type, Optional.<FuelType>absent(), emitters, Optional.<Double>absent());
	}
	
	public TechnologyTestBuilder addHeatSource(final HeatSource type, final FuelType fuel, final EmitterType emitters) {
		return addHeatSource(type, Optional.of(fuel), emitters, Optional.<Double>absent());
	}
	
	private TechnologyTestBuilder addHeatSource(final HeatSource type, final Optional<FuelType> fuel, final EmitterType emitters, final Optional<Double> maybeEfficiency) {
		operations.installHeatSource(model, createHeatSource(type, maybeEfficiency), true, true, emitters, new HashSet<HeatingSystemControlType>(), 0.0, 0.0);
		
		return this;
	}

	private IHeatSource createHeatSource(final HeatSource type, final Optional<Double> maybeEfficiency) {
		switch (type) {
		case BackBoiler:
		case Boiler:
		case CPSU:
		case InstantaneousCombiBoiler:
		case StorageCombiBoiler:
			final IBoiler boiler = createBoiler(type);
			if (maybeEfficiency.isPresent()) {
				boiler.setSummerEfficiency(Efficiency.fromDouble(maybeEfficiency.get()));
				boiler.setWinterEfficiency(Efficiency.fromDouble(maybeEfficiency.get()));
			}
			return boiler;
		case Community:
		case CommunityCHP:
			final ICommunityHeatSource communitySource = createComunity(type);
			if (maybeEfficiency.isPresent()) {
				communitySource.setHeatEfficiency(Efficiency.fromDouble(maybeEfficiency.get()));
			}
			return communitySource;
		case HeatPump:
			final IHeatPump heatPump = factory.createHeatPump();
			if (maybeEfficiency.isPresent()) {
				heatPump.setCoefficientOfPerformance(Efficiency.fromDouble(maybeEfficiency.get()));
			}
			return heatPump;
		default:
			throw new IllegalArgumentException("Unknown heatsource " + type);
		}
	}
	
	private ICommunityHeatSource createComunity(final HeatSource type) {
		switch (type) {
		case Community:
			return factory.createCommunityHeatSource();
		case CommunityCHP:
			return factory.createCommunityCHP();
		default:
			throw new IllegalArgumentException("Not a community heat source " + type);
		}
	}
	
	private IBoiler createBoiler(final HeatSource type) {
		switch(type) {
		case BackBoiler:
			return factory.createBackBoiler();
		case Boiler:
			return boilerFactory.createBoiler();
		case CPSU:
			return boilerFactory.createCPSU();
		case InstantaneousCombiBoiler:
			return boilerFactory.createInstantaneousCombiBoiler();
		case StorageCombiBoiler:
			return boilerFactory.createStorageCombiBoiler();
		default:
			throw new IllegalArgumentException("Not a boiler " + type);
		}
	}

	private ISpaceHeater createSpaceHeater(final SpaceHeater type, final Optional<FuelType> maybeFuel, final Optional<Double> maybeResponsiveness) {
		switch (type) {
		
		case CentralHeating:
			return factory.createCentralHeatingSystem();
		case RoomHeater:
			final IRoomHeater roomHeater = factory.createRoomHeater();
			if (maybeFuel.isPresent()) {
				roomHeater.setFuel(maybeFuel.get());
			}
			if (maybeResponsiveness.isPresent()) {
				roomHeater.setResponsiveness(maybeResponsiveness.get());
			}
			return roomHeater;
		case StorageHeater:
			final IStorageHeater storageHeater = factory.createStorageHeater();
			if (maybeResponsiveness.isPresent()) {
				storageHeater.setResponsiveness(maybeResponsiveness.get());
			}
			return storageHeater;
		case WarmAir:
			final IWarmAirSystem warmAirSystem = factory.createWarmAirSystem();
			if (maybeFuel.isPresent()) {
				warmAirSystem.setFuelType(maybeFuel.get());
			}
			return warmAirSystem;
		default:
			throw new IllegalArgumentException("Unknown space heater type " + type);
		}
	}
	
	public ITechnologyModel build() {
		return model;
	}
	
	
	public enum HeatSource {
		Community,
		CommunityCHP,
		Boiler,
		BackBoiler,
		InstantaneousCombiBoiler,
		StorageCombiBoiler,
		CPSU,
		HeatPump;
	}
	
	enum Type {
		Primary,
		Secondary;
	}
	
	public enum SpaceHeater {
		CentralHeating(Type.Primary),
		RoomHeater(Type.Secondary),
		StorageHeater(Type.Primary),
		WarmAir(Type.Primary);
		
		private final Type p;
		
		SpaceHeater(final Type p) {
			this.p = p;
		}
		
		public boolean isPrimary() {
			return p == Type.Primary;
		}
	}
	
	public enum WaterHeater {
		CentralHotWater(Type.Primary),
		ElectricShower(Type.Secondary),
		PointOfUse(Type.Secondary);
		
		private final Type p;

		public boolean isPrimary() {
			return p == Type.Primary;
		}

		WaterHeater(final Type p) {
			this.p = p;
		}
	}
	
	public enum CentralWaterHeater {
		Immersion,
		Solar,
		WarmAir;
	}
}
