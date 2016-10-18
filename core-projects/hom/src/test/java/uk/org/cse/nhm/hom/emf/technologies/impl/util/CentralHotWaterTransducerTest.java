package uk.org.cse.nhm.hom.emf.technologies.impl.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.hom.constants.HeatingSystemConstants;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;

public class CentralHotWaterTransducerTest {
	
	@Test
	public void testGenerateWithPrimary() {
		final ICentralWaterSystem system = mock(ICentralWaterSystem.class);
		
		final ICentralWaterHeater heater = mock(ICentralWaterHeater.class);
		when(system.getPrimaryWaterHeater()).thenReturn(heater);
		
		final IInternalParameters parameters = mock(IInternalParameters.class);
		when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);
		
		final IEnergyState state = mock(IEnergyState.class);
		
		when(heater.generateHotWaterAndPrimaryGains(parameters, state, null, false, 1, HeatingSystemConstants.CENTRAL_HEATING_DISTRIBUTION_LOSSES.getValue(Double.class), 1)).thenReturn(100d);
		when(state.getUnsatisfiedDemand(EnergyType.DemandsHOT_WATER)).thenReturn(100d,0d);
		
		final CentralHotWaterTransducer chwt = new CentralHotWaterTransducer(system, parameters);
		
		chwt.generate(null, parameters, null, state);
		
		verify(heater).generateHotWaterAndPrimaryGains(parameters, state, 
				null, false, 1,
				HeatingSystemConstants.CENTRAL_HEATING_DISTRIBUTION_LOSSES.getValue(Double.class),1);
		
		verify(heater).generateSystemGains(parameters, state, null, false, 
				// this should be distribution losses + tank losses
				// which is just dist. losses here
				100 * HeatingSystemConstants.CENTRAL_HEATING_DISTRIBUTION_LOSSES.getValue(Double.class)
				);
	}
}
