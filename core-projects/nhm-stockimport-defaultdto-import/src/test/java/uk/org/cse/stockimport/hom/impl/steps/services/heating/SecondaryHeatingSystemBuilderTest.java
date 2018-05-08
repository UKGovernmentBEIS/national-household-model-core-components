package uk.org.cse.stockimport.hom.impl.steps.services.heating;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.IRoomHeater;
import uk.org.cse.nhm.hom.emf.technologies.ISpaceHeater;
import uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType;
import uk.org.cse.nhm.hom.emf.util.Efficiency;
import uk.org.cse.stockimport.domain.services.SecondaryHeatingSystemType;

public class SecondaryHeatingSystemBuilderTest {
	@Test
	public void testBuildNullSecondaryHeatingSystem() {
		final SecondaryHeatingSystemBuilder builder = new SecondaryHeatingSystemBuilder();

		Assert.assertNull(builder.createSecondaryHeatingSystem(SecondaryHeatingSystemType.NOT_KNOWN));
		Assert.assertNull(builder.createSecondaryHeatingSystem(SecondaryHeatingSystemType.NO_SECONDARY_SYSTEM));
	}

	@Test
	public void testBuildElectricSHS() {
		final SecondaryHeatingSystemBuilder builder = new SecondaryHeatingSystemBuilder();

		ISpaceHeater heater = builder.createSecondaryHeatingSystem(SecondaryHeatingSystemType.ELECTRIC_ROOM_HEATERS);

		Assert.assertTrue(heater instanceof IRoomHeater);

		final IRoomHeater rh = (IRoomHeater) heater;

		Assert.assertEquals(FuelType.ELECTRICITY, rh.getFuel());
		Assert.assertEquals(Efficiency.fromDouble(1d), rh.getEfficiency());
		Assert.assertEquals(FlueType.NOT_APPLICABLE, rh.getFlueType());
	}

	@Test
	public void testBuildLPGHeater() {
		final SecondaryHeatingSystemBuilder builder = new SecondaryHeatingSystemBuilder();

		ISpaceHeater heater = builder.createSecondaryHeatingSystem(SecondaryHeatingSystemType.LPG_HEATER);

		Assert.assertTrue(heater instanceof IRoomHeater);

		final IRoomHeater rh = (IRoomHeater) heater;

		Assert.assertEquals(FuelType.BOTTLED_LPG, rh.getFuel());
		Assert.assertEquals(Efficiency.fromDouble(0.58), rh.getEfficiency());
		Assert.assertEquals(FlueType.NOT_APPLICABLE, rh.getFlueType());
	}

	@Test
	public void testBuildGasFire() {
		final SecondaryHeatingSystemBuilder builder = new SecondaryHeatingSystemBuilder();

		ISpaceHeater heater = builder.createSecondaryHeatingSystem(SecondaryHeatingSystemType.GAS_FIRE);

		Assert.assertTrue(heater instanceof IRoomHeater);

		final IRoomHeater rh = (IRoomHeater) heater;

		Assert.assertEquals(FuelType.MAINS_GAS, rh.getFuel());
		Assert.assertEquals(Efficiency.fromDouble(0.58), rh.getEfficiency());
		Assert.assertEquals(FlueType.BALANCED_FLUE, rh.getFlueType());
	}

	@Test
	public void testBuildGasCEFire() {
		final SecondaryHeatingSystemBuilder builder = new SecondaryHeatingSystemBuilder();

		ISpaceHeater heater = builder.createSecondaryHeatingSystem(SecondaryHeatingSystemType.GAS_COAL_EFFECT_FIRE);

		Assert.assertTrue(heater instanceof IRoomHeater);

		final IRoomHeater rh = (IRoomHeater) heater;

		Assert.assertEquals(FuelType.MAINS_GAS, rh.getFuel());
		Assert.assertEquals(Efficiency.fromDouble(0.4), rh.getEfficiency());
		Assert.assertEquals(FlueType.BALANCED_FLUE, rh.getFlueType());
	}

	@Test
	public void testBuildOpenFire() {
		final SecondaryHeatingSystemBuilder builder = new SecondaryHeatingSystemBuilder();

		ISpaceHeater heater = builder.createSecondaryHeatingSystem(SecondaryHeatingSystemType.OPEN_FIRE);

		Assert.assertTrue(heater instanceof IRoomHeater);

		final IRoomHeater rh = (IRoomHeater) heater;

		Assert.assertEquals(FuelType.MAINS_GAS, rh.getFuel());
		Assert.assertEquals(Efficiency.fromDouble(0.45), rh.getEfficiency());
		Assert.assertEquals(FlueType.CHIMNEY, rh.getFlueType());
	}
}
