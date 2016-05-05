package uk.org.cse.nhm.energycalculator.api.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.impl.HeatTransducer;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;

public class HeatTransducerTest {
	@Test
	public void testHeatTransducer() {
		final HeatTransducer t = new HeatTransducer(EnergyType.FuelGAS, 0.85, ServiceType.PRIMARY_SPACE_HEATING);
		
		final IEnergyState state = mock(IEnergyState.class);
		
		when(state.getUnsatisfiedDemand(EnergyType.DemandsHEAT)).thenReturn(303d);
		
		t.generate(null, null, null, state);
	
		verify(state).increaseSupply(EnergyType.DemandsHEAT, 303d);
		verify(state).increaseDemand(EnergyType.FuelGAS, 303/0.85);
	}
	
	@Test
	public void testHeatTransducerProportionalBound() {
		final HeatTransducer t = new HeatTransducer(EnergyType.FuelGAS, 0.85, 0.5, true, 0, ServiceType.PRIMARY_SPACE_HEATING);
		
		final IEnergyState state = mock(IEnergyState.class);
		
		when(state.getBoundedTotalHeatDemand(0.5)).thenReturn(151.5d);
		
		t.generate(null, null, null, state);
	
		verify(state).increaseSupply(EnergyType.DemandsHEAT, 303d/2.0);
		verify(state).increaseDemand(EnergyType.FuelGAS, (303/2d)/0.85);
	}
	
	@Test
	public void testHeatTransducerAbsoluteBound() {
		final HeatTransducer t = new HeatTransducer(EnergyType.FuelGAS, 0.85, 100, false, 0, ServiceType.PRIMARY_SPACE_HEATING);
		
		final IEnergyState state = mock(IEnergyState.class);
		
		when(state.getUnsatisfiedDemand(EnergyType.DemandsHEAT)).thenReturn(303d);
		
		t.generate(null, null, null, state);
	
		verify(state).increaseSupply(EnergyType.DemandsHEAT, 100d);
		verify(state).increaseDemand(EnergyType.FuelGAS, 100d/0.85);
	}
}
