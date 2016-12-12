package uk.org.cse.nhm.hom.emf.technologies.impl.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISeasonalParameters;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.hom.constants.HeatingSystemConstants;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;

public class CentralHotWaterTransducerTest {
	
	private ICentralWaterSystem system;
	private ICentralWaterHeater heater;
	private IInternalParameters parameters;
	private CentralHotWaterTransducer transducer;
	private ISeasonalParameters climate;
	private IWaterTank store;
	
	@Before
	public void setup() {
		system = mock(ICentralWaterSystem.class);
		heater = mock(ICentralWaterHeater.class);
		when(system.getPrimaryWaterHeater()).thenReturn(heater);
		
		parameters = mock(IInternalParameters.class);
		when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);
		
		climate = mock(ISeasonalParameters.class);
		when(parameters.getClimate()).thenReturn(climate);
		
		store = mock(IWaterTank.class);
		when(system.getStore()).thenReturn(store);
		
		transducer = new CentralHotWaterTransducer(system, parameters);
	}

	@Test
	public void testGenerateWithPrimary() {
		final IEnergyState state = mock(IEnergyState.class);
		
		when(heater.generateHotWaterAndPrimaryGains(
				parameters, 
				state, 
				store, 
				false, 
				0, 
				HeatingSystemConstants.CENTRAL_HEATING_DISTRIBUTION_LOSSES.getValue(Double.class), 1)
			).thenReturn(100d);
		
		when(state.getUnsatisfiedDemand(EnergyType.DemandsHOT_WATER)).thenReturn(100d,0d);
		
		when(heater.causesPipeworkLosses()).thenReturn(false);
		
		transducer.generate(null, parameters, null, state);
		
		verify(heater).generateHotWaterAndPrimaryGains(
				parameters, 
				state, 
				store, 
				false, 
				0,
				HeatingSystemConstants.CENTRAL_HEATING_DISTRIBUTION_LOSSES.getValue(Double.class),1
			);
		
		verify(heater).generateSystemGains(
				parameters, 
				state, 
				store, 
				false, 
				// this should be distribution losses + tank losses
				// which is just dist. losses here
				100 * HeatingSystemConstants.CENTRAL_HEATING_DISTRIBUTION_LOSSES.getValue(Double.class)
				);
	}
	
	@Test
	public void testHoursHot() {
		// Community
		assertEquals(3, transducer.hoursPerDayPrimaryHot(parameters, true, false), 0);
		
		// Summer
		when(climate.isHeatingOn()).thenReturn(false);
		assertEquals(3, transducer.hoursPerDayPrimaryHot(parameters, false, false), 0);

		// Winter
		when(climate.isHeatingOn()).thenReturn(true);
		
		// Winter no cylinder thermostat
		assertEquals(11, transducer.hoursPerDayPrimaryHot(parameters, false, false), 0);

		// Winter with cylinder thermostat
		assertEquals(5, transducer.hoursPerDayPrimaryHot(parameters, false, true), 0);
	}
	
	@Test
	public void checkPipeworkLosses() {
		/* The number in these tests verified by hand using the following Python code:
		 multiplier = 14 * 1000 / 24
		 pipework = lambda p, h: multiplier * ((((p * 0.0091) + ((1-p) * 0.0245)) * h) + 0.0263)
		 pipework(1, 3) ## fraction insulated = 1, hours per day heated = 3
		 */

		// Community should be the same regardless of season
		checkPipeworkLosses("Community summer", 31.27, false, true, true, false, 1);
		checkPipeworkLosses("Community winter", 31.27, false, true, false, false, 1);
		
		checkPipeworkLosses("Insulated summer", 31.27, true, false, true, false, 1);
		checkPipeworkLosses("Uninsulated summer", 58.22, false, false, true, false, 1);
		
		checkPipeworkLosses("Insulated winter thermostat", 41.88, true, false, false, true, 1);
		checkPipeworkLosses("Insulated winter no thermostat", 73.73, true, false, false, false, 1);
		
		checkPipeworkLosses("Uninsulated winter thermostat", 86.8, false, false, false, true, 1);
		checkPipeworkLosses("Uninsulated winter no thermostat", 172.55, false, false, false, false, 1);
		
		checkPipeworkLosses("Solar correction factor should halve value", 43.4, false, false, false, true, 0.5);
	}
	
	private void checkPipeworkLosses(String message, double expected, boolean insulated, boolean community, boolean summer, boolean cylinderThermostat, double solarCorrectionFactor) {
		when(climate.isHeatingOn()).thenReturn(!summer);
		when(store.isThermostatFitted()).thenReturn(cylinderThermostat);
		
		assertEquals(
				message,
				expected,
				transducer.getPrimaryPipeworkLosses(parameters, insulated, community, store, solarCorrectionFactor),
				0.1
		);
	}
}
