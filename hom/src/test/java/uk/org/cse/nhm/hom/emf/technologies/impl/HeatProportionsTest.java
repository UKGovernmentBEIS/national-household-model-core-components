package uk.org.cse.nhm.hom.emf.technologies.impl;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import uk.org.cse.nhm.hom.IHeatProportions;
import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.IPrimarySpaceHeater;
import uk.org.cse.nhm.hom.emf.technologies.IRoomHeater;
import uk.org.cse.nhm.hom.emf.technologies.IStorageHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersFactory;

public class HeatProportionsTest {
	
	private static ICentralHeatingSystem boiler = ITechnologiesFactory.eINSTANCE.createCentralHeatingSystem();
	private static IStorageHeater oldStorage = ITechnologiesFactory.eINSTANCE.createStorageHeater();
	private static IStorageHeater fanStorage = ITechnologiesFactory.eINSTANCE.createStorageHeater();
	private static IRoomHeater secondary = ITechnologiesFactory.eINSTANCE.createRoomHeater();
	
	static {
		final IBoiler boilerSource = IBoilersFactory.eINSTANCE.createBoiler();
		boiler.setHeatSource(boilerSource);
		
		oldStorage.setType(StorageHeaterType.OLD_LARGE_VOLUME);
		fanStorage.setType(StorageHeaterType.FAN);
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	private void testHeatProportions(IPrimarySpaceHeater primary, IRoomHeater secondary, double primaryProportion, double secondaryProportion, double assumedElectricProportion) {
		ITechnologyModel tech = ITechnologiesFactory.eINSTANCE.createTechnologyModel();

		if (primary != null) {
			tech.setPrimarySpaceHeater(primary);
		}
		
		if (secondary != null) {
			tech.setSecondarySpaceHeater(secondary);
		}
		
		IHeatProportions proportions = tech.getHeatProportions();

		Assert.assertEquals(primaryProportion, proportions.spaceHeatingProportion(primary), 0.0);
		Assert.assertEquals(secondaryProportion, proportions.spaceHeatingProportion(secondary), 0.0);
		Assert.assertEquals(assumedElectricProportion, proportions.spaceHeatingProportion(TechnologyModelImpl.assumedElectricSpaceHeater), 0.0);
	}

	@Test
	public void noHeaters() {
		// All assumed electric
		testHeatProportions(null, null, 0.0, 0.0, 1.0);
	}

	@Test
	public void primaryBoilerNoSecondary() {
		// All primary heater
		testHeatProportions(boiler, null, 1.0, 0.0, 0.0);
	}
	
	@Test
	public void primaryBoilerWithSecondary() {
		testHeatProportions(boiler, secondary, 0.9, 0.1, 0.0);
	}		

	@Test
	public void primaryStorageNoSecondary() {
		// Assume secondary is electric
		testHeatProportions(oldStorage, null, 0.85, 0.0, 0.15);
	}

	@Test
	public void primaryStorageWithSecondary() {
		testHeatProportions(oldStorage, secondary, 0.85, 0.15, 0.0);
	}

	@Test
	public void primaryFanStorageWithSecondary() {
		testHeatProportions(fanStorage, secondary, 0.9, 0.1, 0.0);
	}

	@Test
	public void secondaryOnly() {
		// Primary assumed electric
		testHeatProportions(null, secondary, 0.0, 0.1, 0.9);
	}
}
