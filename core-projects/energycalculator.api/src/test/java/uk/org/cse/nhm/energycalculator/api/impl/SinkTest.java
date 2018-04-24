package uk.org.cse.nhm.energycalculator.api.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;

public class SinkTest {
	@Test
	public void testSink() {
		final Sink sink = new Sink(EnergyType.FuelBIOMASS_WOOD) {
			@Override
			public int getPriority() {
				return 0;
			}
			
			@Override
			protected double getDemand(final IEnergyCalculatorHouseCase house, final IInternalParameters parameters, final ISpecificHeatLosses losses, final IEnergyState state) {
				return 123;
			}
		};
		
		final IEnergyState state = mock(IEnergyState.class);
		
		sink.generate(null, null, null, state);
		
		verify(state).increaseDemand(EnergyType.FuelBIOMASS_WOOD, 123d);
	}
}
