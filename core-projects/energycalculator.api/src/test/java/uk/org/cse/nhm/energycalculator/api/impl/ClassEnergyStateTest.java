package uk.org.cse.nhm.energycalculator.api.impl;

import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.impl.ClassEnergyState;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;

/**
 * Check the {@link ClassEnergyState} behaves according to its contract
 * @author hinton
 *
 */
public class ClassEnergyStateTest {
	private ClassEnergyState state;

	@Before
	public void createState() {
		state = new ClassEnergyState();
		setServiceType(ServiceType.INTERNALS);
	}
	
	private void setServiceType(final ServiceType st) { 
		state.setCurrentServiceType(st, "Test Case");
	}
	
	@After
	public void clear() {
		this.state = null;
	}
	
	@Test
	public void testInitiallyZero() {
		// check for a few energy types
		for (final EnergyType et : new EnergyType[] {EnergyType.FuelBIOMASS_WOODCHIP, EnergyType.FuelGAS, EnergyType.GainsCOOKING_GAINS}) {
			Assert.assertEquals(0d, state.getTotalSupply(et), 0.01);
			Assert.assertEquals(0d, state.getUnsatisfiedDemand(et), 0.01);
			Assert.assertEquals(0d, state.getTotalDemand(et), 0.01);
		}
	}
	
	@Test
	public void testTotalForService() {
	    state.setCurrentServiceType(ServiceType.APPLIANCES, "");
	    state.increaseDemand(EnergyType.CommunityBIOMASS, 10d);
	    Assert.assertEquals(10, state.getTotalDemand(ServiceType.APPLIANCES), 0);
	    state.increaseDemand(EnergyType.FuelGAS, 12d);
	    Assert.assertEquals(22, state.getTotalDemand(ServiceType.APPLIANCES), 0);
	}
	
	@Test
	public void testUnsatisfiedDemand() {
		state.increaseDemand(EnergyType.FuelGAS, 100);
		Assert.assertEquals(100d, state.getUnsatisfiedDemand(EnergyType.FuelGAS), 0.01);
		Assert.assertEquals(0d, state.getTotalSupply(EnergyType.FuelGAS), 0.01);
		Assert.assertEquals(100d, state.getTotalDemand(EnergyType.FuelGAS), 0.01);
		state.increaseSupply(EnergyType.FuelGAS, 50);
		Assert.assertEquals(50d, state.getUnsatisfiedDemand(EnergyType.FuelGAS), 0.01);
		Assert.assertEquals(50d, state.getTotalSupply(EnergyType.FuelGAS), 0.01);
		Assert.assertEquals(100d, state.getTotalDemand(EnergyType.FuelGAS), 0.01);
		state.increaseSupply(EnergyType.FuelGAS, 50);
		Assert.assertEquals(0d, state.getUnsatisfiedDemand(EnergyType.FuelGAS), 0.01);
		Assert.assertEquals(100d, state.getTotalSupply(EnergyType.FuelGAS), 0.01);
		Assert.assertEquals(100d, state.getTotalDemand(EnergyType.FuelGAS), 0.01);
		state.increaseSupply(EnergyType.FuelGAS, 50);
		Assert.assertEquals(0d, state.getUnsatisfiedDemand(EnergyType.FuelGAS), 0.01);
		Assert.assertEquals(150d, state.getTotalSupply(EnergyType.FuelGAS), 0.01);
		Assert.assertEquals(100d, state.getTotalDemand(EnergyType.FuelGAS), 0.01);
	}
	
	@Test
	public void testServiceBreakdown() {
		setServiceType(ServiceType.PRIMARY_SPACE_HEATING);
		state.increaseDemand(EnergyType.FuelGAS, 100);
		state.increaseSupply(EnergyType.DemandsHEAT, 40);
		setServiceType(ServiceType.WATER_HEATING);
		state.increaseDemand(EnergyType.FuelGAS, 200);
		
		Assert.assertEquals(300d, state.getUnsatisfiedDemand(EnergyType.FuelGAS), 0.01);
		Assert.assertEquals(100d, state.getTotalDemand(EnergyType.FuelGAS, ServiceType.PRIMARY_SPACE_HEATING), 0.01);
		Assert.assertEquals(200d, state.getTotalDemand(EnergyType.FuelGAS, ServiceType.WATER_HEATING), 0.01);
	
		Assert.assertEquals(40d, state.getTotalSupply(EnergyType.DemandsHEAT, ServiceType.PRIMARY_SPACE_HEATING), 0.01);
	}
	
	@Test
	public void testElectricitySpecificMethod() {
		state.increaseElectricityDemand(0.123, 100);
		Assert.assertEquals(12.3, state.getUnsatisfiedDemand(EnergyType.FuelPEAK_ELECTRICITY), 0.01);
		Assert.assertEquals(100-12.3, state.getUnsatisfiedDemand(EnergyType.FuelOFFPEAK_ELECTRICITY), 0.01);
	}
	
	@Test
	public void testToString() {
		// to make to string go away in coverage.
		state.toString();
	}
}
