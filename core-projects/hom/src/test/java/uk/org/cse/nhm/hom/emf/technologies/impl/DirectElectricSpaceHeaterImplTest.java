package uk.org.cse.nhm.hom.emf.technologies.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
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
		final IInternalParameters p = mock(IInternalParameters.class);
		when(p.getConstants()).thenReturn(DefaultConstants.INSTANCE);
		
		system.setFuel(FuelType.ELECTRICITY);
		
		assertEquals(0.3, rhhs.getDemandTemperatureAdjustment(p), 0d);
		system.setThermostatFitted(true);
		assertEquals(0d, rhhs.getDemandTemperatureAdjustment(p), 0d);
	}

}
