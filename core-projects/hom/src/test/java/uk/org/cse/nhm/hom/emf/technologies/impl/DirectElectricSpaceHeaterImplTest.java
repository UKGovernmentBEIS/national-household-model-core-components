package uk.org.cse.nhm.hom.emf.technologies.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.IRoomHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.impl.util.RoomHeaterHeatingSystem;

public class DirectElectricSpaceHeaterImplTest {
	@Test
	public void testHeatingSystemProperties() {
		IRoomHeater system = (IRoomHeater) ITechnologiesFactory.eINSTANCE.createRoomHeater();
		final RoomHeaterHeatingSystem rhhs = new RoomHeaterHeatingSystem(system);
		final IEnergyState state = mock(IEnergyState.class);
		final ISpecificHeatLosses losses = mock(ISpecificHeatLosses.class);
		final IInternalParameters p = mock(IInternalParameters.class);
		when(p.getConstants()).thenReturn(DefaultConstants.INSTANCE);
		
		system.setFuel(FuelType.ELECTRICITY);
		system.setResponsiveness(1);
		
		final double[] backgroundTemperatures = rhhs.getBackgroundTemperatures(new double[] {21, 20}, new double[] {10, 9}, new double[] {100, 200}, p, 
				state,
				losses);
		
		// this should have a responsiveness of 1, so the result should just be ideal
		
		assertEquals("Zone 1 bg is ideal", 10.0, backgroundTemperatures[0], 0d);
		assertEquals("Zone 12 bg is ideal", 9.0, backgroundTemperatures[1], 0d);
		
		assertEquals(0.3, rhhs.getDemandTemperatureAdjustment(p), 0d);
		system.setThermostatFitted(true);
		assertEquals(0d, rhhs.getDemandTemperatureAdjustment(p), 0d);
	}

}
