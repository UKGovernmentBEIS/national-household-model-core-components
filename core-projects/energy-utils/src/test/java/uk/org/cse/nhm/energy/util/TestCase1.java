package uk.org.cse.nhm.energy.util;

import java.awt.Polygon;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculationResult;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculator;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.ISeasonalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.impl.DailyHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.impl.ExternalParameters;
import uk.org.cse.nhm.energycalculator.api.impl.GraphvizEnergyState;
import uk.org.cse.nhm.energycalculator.api.impl.SeasonalParameters;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.SiteExposureType;
import uk.org.cse.nhm.energycalculator.impl.EnergyCalculatorCalculator;
import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;
import uk.org.cse.nhm.hom.components.fabric.types.FloorLocationType;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.hom.emf.technologies.ILight;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.impl.CookerImpl;
import uk.org.cse.nhm.hom.structure.Glazing;
import uk.org.cse.nhm.hom.structure.IMutableWall;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Elevation;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.hom.types.BuiltFormType;
import uk.org.cse.nhm.hom.types.MorphologyType;
import uk.org.cse.nhm.hom.types.RegionType;
import uk.org.cse.nhm.hom.types.TenureType;

/**
 * An integration test for the heat loss parameter. 
 * Test values are calculated in heat_loss_testcase_1.rws
 * 
 * @author hinton
 *
 */
public class TestCase1 {
	@Test
	public void testHouseWithoutHeatingOrHotWater() {
		final SurveyCase sc = new SurveyCase();
		final StructureModel structure = new StructureModel(BuiltFormType.MidTerrace);
		final BasicCaseAttributes attrs = new BasicCaseAttributes("aacode", 1, 1, RegionType.London, 
				MorphologyType.HamletsAndIsolatedDwellings, TenureType.HousingAssociation,
				1983,
				SiteExposureType.Average);
		final ITechnologyModel tech = ITechnologiesFactory.eINSTANCE.createTechnologyModel();
		
		sc.setStructure(structure);
		sc.setTechnologies(tech);
		sc.setBasicAttributes(attrs);
		
		structure.setNumberOfShelteredSides(0);
		structure.setInterzoneSpecificHeatLoss(352.75);
		structure.setLivingAreaProportionOfFloorArea(0.25);
		structure.setZoneTwoHeatedProportion(1);

		final Polygon plan = new Polygon(
				new int[] {0, 7, 7,  0},
				new int[] {0, 0, 10, 10},
				4);
		
		final Storey ground = new Storey();
		ground.setPerimeter(plan);
		
		int partyWallCounter = 0;
		int nonPartyWallCounter = 0;
		
		for (final IMutableWall wall : ground.getWalls()) {
			if (wall.getLength() == 10d) {
				partyWallCounter++;
				wall.setWallConstructionType(WallConstructionType.Party_DensePlasterBothSidesDenseBlocksCavity);
				wall.setUValue(0.02);
			} else {
				nonPartyWallCounter++;
				wall.setWallConstructionType(WallConstructionType.SolidBrick);
				wall.setUValue(2.1);
				wall.setAirChangeRate(0.3);
			}
		}
		
		final Storey first = new Storey();
		first.setPerimeter(plan);
		
		for (final IMutableWall wall : first.getWalls()) {
			if (wall.getLength() == 10) {
				partyWallCounter++;
				wall.setWallConstructionType(WallConstructionType.Party_DensePlasterBothSidesDenseBlocksCavity);
				wall.setUValue(0.02);
			} else {
				nonPartyWallCounter++;
				wall.setWallConstructionType(WallConstructionType.SolidBrick);
				wall.setUValue(2.1);
				wall.setAirChangeRate(0.3);
			}
		}
		
		Assert.assertEquals(4, partyWallCounter);
		Assert.assertEquals(4, nonPartyWallCounter);
		
		final Elevation back = new Elevation();
		final Elevation front = new Elevation();
		final Elevation left = new Elevation();
		final Elevation right = new Elevation();
		
		final Glazing frontWindow = new Glazing();
		frontWindow.setUValue(2.5);
		frontWindow.setFrameFactor(1);
		frontWindow.setGainsTransmissionFactor(0.76);
		frontWindow.setLightTransmissionFactor(0.8);
		frontWindow.setGlazedProportion(1.0);
		
		final Glazing backWindow = new Glazing();
		backWindow.setUValue(2.5);
		backWindow.setFrameFactor(1);
		backWindow.setGainsTransmissionFactor(0.76);
		backWindow.setLightTransmissionFactor(0.8);
		backWindow.setGlazedProportion(1.0);
		
		left.addGlazing(frontWindow);
		right.addGlazing(backWindow);
		
		left.setOpeningProportion(2.0/7.0);
		right.setOpeningProportion(1.0/7.0);
		
		left.setAngleFromNorth(Math.PI);
//		back.setAngleFromNorth(Math.PI);
//		left.setAngleFromNorth(Math.PI/2);
//		right.setAngleFromNorth(Math.PI/2);
		
//		final HouseCase hc = new HouseCase();
//		// set basic house properties
//		hc.setNumberOfStoreys(2);
//		hc.setTotalFloorArea(140);
//		hc.setTotalVolume(560);
//		hc.setExposure(1.0);
//		hc.setInterZoneSpecificHeatLoss(352.75); //TODO add this as inference within house case? hmm.
//		hc.setZone1Proportion(0.25);
//		hc.setZoneTwoHeatedProportion(1);
//		
//		// create walls
//		// u k inf
//		final Wall frontWall = new Wall(7, 8, 2.1, 17, 0.3);
//		final Wall backWall = new Wall(7, 8, 2.1, 17, 0.3);
//		
//		// party walls have no ventilation coefficient
//		final Wall sideWall = new Wall(10, 8, 0.02, 180);
//		final Wall sideWall2 = new Wall(10, 8, 0.02, 180);
//		
//		hc.addComponent(frontWall);
//		hc.addComponent(backWall);
//		hc.addComponent(sideWall2);
//		hc.addComponent(sideWall);
		
		// create windows
//		final Window frontWindow = new Window(4, 4, 2.5, 0, 0.05, 1.0, 0.76, 0.8);
//		final Window backWindow = new Window(4, 2, 2.5, 0, 0.05, 1.0, 0.76, 0.8);
//		
//		frontWindow.setHorizontalOrientation(Math.PI / 2);
//		
//		frontWindow.setVerticalOrientation(Math.PI);
//		
//		// should window orientation be set by wall containing them?
//		
//		frontWall.addChildElement(frontWindow);
//		backWall.addChildElement(backWindow);
		ground.setFloorUValue(1);
		first.setCeilingUValue(0.67);
//		
//		// create roof and floor
//		final Roof roof = new Roof(7, 10, 0.67, 9);
//		final Floor floor = new Floor(7, 10, 1, 110, 0);
//		
//		hc.addComponent(roof);
//		hc.addComponent(floor);
		
//		ITechnologyModel tech = ITechnologiesFactory.eINSTANCE.createTechnologyModel();
		
//		hc.setTechnologies(tech);
		
		structure.setElevation(ElevationType.BACK, back);
		structure.setElevation(ElevationType.FRONT, front);
		structure.setElevation(ElevationType.RIGHT, right);
		structure.setElevation(ElevationType.LEFT, left);
		
		ground.setFloorLocationType(FloorLocationType.GROUND);
		first.setFloorLocationType(FloorLocationType.FIRST_FLOOR);
		
		ground.setHeight(4);
		first.setHeight(4);
		
		structure.addStorey(ground);
		structure.addStorey(first);
		
		Assert.assertEquals(140d, structure.getFloorArea(), 0d);
		
		tech.getCookers().add(
				CookerImpl.createMixed());
		
		final ILight light = ITechnologiesFactory.eINSTANCE.createLight();
		light.setEfficiency(ILight.INCANDESCENT_EFFICIENCY);
		light.setProportion(1);
		light.setName("Lights");
		tech.getLights().add(light);
		
		// set up parameters, heating schedule etc
		
		final ExternalParameters parameters = new ExternalParameters();
		final IHeatingSchedule schedule = new DailyHeatingSchedule();
		
		parameters.setZoneOneDemandTemperature(21);
		parameters.setNumberOfOccupants(2);
		parameters.setTarrifType(ElectricityTariffType.FLAT_RATE);
		
		parameters.setInterzoneTemperatureDifference(3);
		
		final ISeasonalParameters climate = new SeasonalParameters(1, -20.7 * Math.PI / 180,// 1, 30, 
				5, 4, 25, 52.0 * Math.PI / 180, schedule, Optional.<IHeatingSchedule>absent());
		
		final IEnergyCalculator calc = new EnergyCalculatorCalculator();
		
		final IEnergyCalculationResult state = calc.evaluate(sc, parameters, new ISeasonalParameters[] {climate})[0];
		
		final ISpecificHeatLosses heatLosses = state.getHeatLosses();
		
		// verify specific heat loss
		Assert.assertEquals(540.376, heatLosses.getSpecificHeatLoss(), 0.01);
		
		final double expectedVolume = 1.10 * (25 * 2 + 36.0);
		
		// verify hot water volume
		
		Assert.assertEquals(expectedVolume, state.getEnergyState().getTotalDemand(EnergyType.DemandsHOT_WATER_VOLUME), 0d);
		
		// verify hot water energy demand
		// and then adds in 15% in distribution losses.
		final double expectedEnergy = 0.85 * (4.19 * expectedVolume * 41.2 / 3600.0); // in kWh / day
		
		Assert.assertEquals(
				expectedEnergy * 41.66666// this is in kWh / day, convert to watts
				, state.getEnergyState().getTotalDemand(EnergyType.DemandsHOT_WATER), 0.1);
		
		// verify gains & usage for lights and cooking are correct
		final double electricityForLighting = state.getEnergyState().getTotalDemand(EnergyType.FuelPEAK_ELECTRICITY, ServiceType.LIGHTING)
				+ state.getEnergyState().getTotalDemand(EnergyType.FuelOFFPEAK_ELECTRICITY, ServiceType.LIGHTING);
		
		final double gasForCooking = state.getEnergyState().getTotalDemand(EnergyType.FuelGAS, ServiceType.COOKING);
		
		final double totalGas = state.getEnergyState().getTotalDemand(EnergyType.FuelGAS);
		
		Assert.assertEquals(76.843, gasForCooking, 0.01);
		Assert.assertEquals(135.6954, electricityForLighting, 0.01);
		
		Assert.assertEquals(gasForCooking, totalGas, 0d);
		
		Assert.assertEquals(38626d, state.getHeatLosses().getThermalMass(), 0d);
		Assert.assertEquals(275.9d, state.getHeatLosses().getThermalMassParameter(), 0d);
		
		Assert.assertEquals(308.46, state.getEnergyState().getUnsatisfiedDemand(EnergyType.DemandsHEAT), 0.05);
		
		final IEnergyState es = state.getEnergyState();
		if (es instanceof GraphvizEnergyState) {
			System.out.println(((GraphvizEnergyState) es).toDotFile());
		}
	}
}
