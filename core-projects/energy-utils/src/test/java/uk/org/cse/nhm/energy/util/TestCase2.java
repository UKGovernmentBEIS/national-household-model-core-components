package uk.org.cse.nhm.energy.util;

import java.awt.Polygon;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculationResult;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.ISeasonalParameters;
import uk.org.cse.nhm.energycalculator.api.impl.BredemExternalParameters;
import uk.org.cse.nhm.energycalculator.api.impl.ClassEnergyState;
import uk.org.cse.nhm.energycalculator.api.impl.DailyHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.impl.GraphvizEnergyState;
import uk.org.cse.nhm.energycalculator.api.impl.WeeklyHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.MonthType;
import uk.org.cse.nhm.energycalculator.api.types.SiteExposureType;
import uk.org.cse.nhm.energycalculator.impl.BredemSeasonalParameters;
import uk.org.cse.nhm.energycalculator.impl.EnergyCalculatorCalculator;
import uk.org.cse.nhm.energycalculator.impl.EnergyCalculatorCalculator.IEnergyStateFactory;
import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;
import uk.org.cse.nhm.hom.components.fabric.types.FloorLocationType;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.ILight;
import uk.org.cse.nhm.hom.emf.technologies.IMainWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersFactory;
import uk.org.cse.nhm.hom.emf.technologies.impl.CookerImpl;
import uk.org.cse.nhm.hom.emf.util.Efficiency;
import uk.org.cse.nhm.hom.structure.Glazing;
import uk.org.cse.nhm.hom.structure.IMutableWall;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Elevation;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.hom.types.BuiltFormType;
import uk.org.cse.nhm.hom.types.MorphologyType;
import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.hom.types.TenureType;

public class TestCase2 {
	private void addStructure(final SurveyCase sc) {
		final StructureModel sm = sc.getStructure();

		final Polygon plan = new Polygon(
				new int[] {0, 6, 6,  0},
				new int[] {0, 0, 10, 10},
				4);

		final Storey ground = new Storey();
		final Storey first = new Storey();
		final Storey second = new Storey();

		final Elevation backElevation = new Elevation();
		final Elevation frontElevation = new Elevation();
		final Elevation leftElevation = new Elevation();
		final Elevation rightElevation = new Elevation();

		sm.setElevation(ElevationType.BACK, backElevation);
		sm.setElevation(ElevationType.FRONT, frontElevation);
		sm.setElevation(ElevationType.LEFT, leftElevation);
		sm.setElevation(ElevationType.RIGHT, rightElevation);

		for (final Storey storey : new Storey[] {ground, first, second}) {
			storey.setHeight(2);
			storey.setPerimeter(plan);
			storey.setFloorLocationType(FloorLocationType.GROUND);//hax
			for (final IMutableWall wall : storey.getWalls()) {
				if (wall.getLength() == 6) {
					wall.setWallConstructionType(WallConstructionType.SolidBrick);
					wall.setUValue(2.1);
					wall.setAirChangeRate(0.3);
				} else {
					wall.setWallConstructionType(WallConstructionType.SolidBrick);
					wall.setUValue(2.1);
					wall.setAirChangeRate(0.2);
				}
			}
			sm.addStorey(storey);
		}

		final Glazing frontWindow = new Glazing();
		frontWindow.setUValue(1.5);
		frontWindow.setFrameFactor(0.7);
		frontWindow.setGainsTransmissionFactor(0.76);
		frontWindow.setLightTransmissionFactor(0.77);

		frontWindow.setGlazedProportion(1);
		leftElevation.setOpeningProportion(1.0/6.0);

		final Glazing backWindow = new Glazing();
		backWindow.setUValue(1.5);
		backWindow.setFrameFactor(0.7);
		backWindow.setGainsTransmissionFactor(0.76);
		backWindow.setLightTransmissionFactor(0.77);

		backWindow.setGlazedProportion(1);

		rightElevation.setOpeningProportion(1.0/6.0);

		rightElevation.addGlazing(backWindow);
		leftElevation.addGlazing(frontWindow);

		rightElevation.setAngleFromNorth(0);
		leftElevation.setAngleFromNorth(Math.PI/2);

		ground.setFloorUValue(1);

		second.setCeilingUValue(0.5);
		second.setHeight(2.095);
		//TODO need to set room in roof correctly.
//		final Floor floor = new Floor(6, 10, 1, 75, 0);
//		final Roof roof = new Roof(10.4495, 10.4495, 0.5, 9);
	}

	@Test
	public void test() throws IOException {
		final SurveyCase sc = new SurveyCase();
		final StructureModel sm = new StructureModel(BuiltFormType.Detached);
		final ITechnologyModel technologies = ITechnologiesFactory.eINSTANCE.createTechnologyModel();
		final BasicCaseAttributes attrs = new BasicCaseAttributes("aacode", 1, 1, RegionType.London,
				MorphologyType.HamletsAndIsolatedDwellings, TenureType.HousingAssociation,
				1900,
				SiteExposureType.Average
				);
		sc.setStructure(sm);
		sc.setTechnologies(technologies);
		sc.setBasicAttributes(attrs);

//		final HouseCase hc = new HouseCase();

//		hc.setBuildYear(1900);
		sm.setNumberOfShelteredSides(0);
//		hc.setNumberOfStoreys(3);
//		hc.setTotalFloorArea(150);
//		hc.setTotalVolume(360d);
		sm.setLivingAreaProportionOfFloorArea(0.3);

//		hc.setZone1Proportion(0.3);
		sm.setZoneTwoHeatedProportion(1);
		sm.setInterzoneSpecificHeatLoss(352.75);

		addStructure(sc);

//		hc.setTechnologies(technologies);

		addLights(sc);

		addCookers(sc);

		final IBoiler boiler = IBoilersFactory.eINSTANCE.createBoiler();

		technologies.setIndividualHeatSource(boiler);

		boiler.setSummerEfficiency(Efficiency.fromDouble(0.8));
		boiler.setWinterEfficiency(Efficiency.fromDouble(0.85));
//		boiler.setSpaceHeatingBound(1.0);
//		boiler.setHotWaterBound(1.0);
//		boiler.setResponsiveness(0.9);
//		boiler.setPrimaryPipeworkInsulated(true);
//		boiler.setZoneTwoControlParameter(1); // this probably belongs on the system


		final ICentralHeatingSystem centralHeatingSystem = ITechnologiesFactory.eINSTANCE.createCentralHeatingSystem();
		centralHeatingSystem.setHeatSource(boiler);
		centralHeatingSystem.getControls().add(HeatingSystemControlType.TIME_TEMPERATURE_ZONE_CONTROL);

		technologies.setPrimarySpaceHeater(centralHeatingSystem);

		addWaterHeatingSystem(boiler, sc);

		final BredemExternalParameters ep = new BredemExternalParameters(
				ElectricityTariffType.FLAT_RATE,
				21,
				Optional.<Double>absent(),
				Optional.of(3.0),
				4
			);

		final WeeklyHeatingSchedule weeklyHeatingSchedule = new WeeklyHeatingSchedule(
						new DailyHeatingSchedule(7 * 60, 8*60, 18 * 60, 23 * 60),
						new DailyHeatingSchedule(7 * 60, 23 * 60)
						);
		final ISeasonalParameters climate = new BredemSeasonalParameters(
						MonthType.March,
						7.4,
						5 * 1.5,
						99,
						0.8988,
						weeklyHeatingSchedule,
						Optional.<IHeatingSchedule>absent()
					);

		final EnergyCalculatorCalculator calc = new EnergyCalculatorCalculator();
		calc.setStateFactory(new IEnergyStateFactory() {
			@Override
			public IEnergyState createEnergyState() {
				return new GraphvizEnergyState(new ClassEnergyState());
			}
		});
		final IEnergyCalculationResult energyCalculationResult = calc.evaluate(sc, ep, new ISeasonalParameters[] {climate})[0];
		final IEnergyState energyState = energyCalculationResult.getEnergyState();

		if (energyState instanceof GraphvizEnergyState) {
			final File createTempFile = File.createTempFile("energy", ".dot");
			System.out.println("Drawing flow diagram into " + createTempFile);
			final FileWriter fileWriter = new FileWriter(createTempFile);
			fileWriter.write(((GraphvizEnergyState) energyState).toDotFile());
			fileWriter.close();
		}

		Assert.assertEquals(5287, energyState.getTotalDemand(EnergyType.FuelGAS), 5);
	}

	private void addWaterHeatingSystem(final IBoiler boiler, final SurveyCase sc) {
		final IWaterTank tank = ITechnologiesFactory.eINSTANCE.createWaterTank();

		tank.setThermostatFitted(true);
		tank.setVolume(140);
		tank.setInsulation(50);
		tank.setFactoryInsulation(true);

		//cylinder.setDailyStandingLoss(0.6327160493827161); //forgot where this comes from

//		final SolarWaterHeater solar = new SolarWaterHeater();

		final ISolarWaterHeater solar = ITechnologiesFactory.eINSTANCE.createSolarWaterHeater();

		solar.setArea(2.8);
		solar.setUsefulAreaRatio(1);
		solar.setOrientation(Math.PI);
		solar.setPitch(0.5235987755982988); // 30 degrees
		solar.setZeroLossEfficiency(0.9);
		solar.setLinearHeatLossCoefficient(20);

		tank.setSolarStorageVolume(70);

		final ICentralWaterSystem centralWater = ITechnologiesFactory.eINSTANCE.createCentralWaterSystem();
		final IMainWaterHeater boilerHeater = ITechnologiesFactory.eINSTANCE.createMainWaterHeater();
		boilerHeater.setHeatSource(boiler);

		centralWater.setPrimaryPipeworkInsulated(true);
		centralWater.setStore(tank);
		centralWater.setPrimaryWaterHeater(boilerHeater);
		centralWater.setSolarWaterHeater(solar);

		sc.getTechnologies().setCentralWaterSystem(centralWater);
	}

	private void addLights(final SurveyCase sc) {
		final ILight badLights = ITechnologiesFactory.eINSTANCE.createLight();
		badLights.setName("Incandescents");
		final ILight goodLights = ITechnologiesFactory.eINSTANCE.createLight();
		goodLights.setName("CFLs");

		badLights.setProportion(0.4);
		badLights.setEfficiency(ILight.INCANDESCENT_EFFICIENCY);

		goodLights.setProportion(0.6);
		goodLights.setEfficiency(ILight.CFL_EFFICIENCY);

		sc.getTechnologies().getLights().add(badLights);
		sc.getTechnologies().getLights().add(goodLights);
	}

	private void addCookers(final SurveyCase sc) {
		sc.getTechnologies().getCookers().add(
				CookerImpl.createMixed());
	}
}
