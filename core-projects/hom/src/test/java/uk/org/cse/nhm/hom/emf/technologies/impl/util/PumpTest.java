package uk.org.cse.nhm.hom.emf.technologies.impl.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;

public class PumpTest {
	@Test
	public void testPumpWithNoGains() {
		final Pump p = new Pump("Test Pump", ServiceType.COOKING, 100, 0, null);

		final IInternalParameters parameters = mock(IInternalParameters.class);
		final IEnergyState state = mock(IEnergyState.class);

		when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);
		when(parameters.getTarrifType()).thenReturn(ElectricityTariffType.FLAT_RATE);

		p.generate(null, parameters, null, state);

		verify(state).increaseElectricityDemand(1, 100);

		verifyNoMoreInteractions(state);

		Assert.assertEquals(ServiceType.COOKING, p.getServiceType());
	}

	@Test
	public void testPumpWithGains() {
		final Pump p = new Pump("Test Pump", ServiceType.COOKING, 100, 100, null);

		final IInternalParameters parameters = mock(IInternalParameters.class);
		final IEnergyState state = mock(IEnergyState.class);

		when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);
		when(parameters.getTarrifType()).thenReturn(ElectricityTariffType.FLAT_RATE);

		p.generate(null, parameters, null, state);

		verify(state).increaseElectricityDemand(1, 100);
		verify(state).increaseSupply(EnergyType.GainsPUMP_AND_FAN_GAINS, 100d);

		verifyNoMoreInteractions(state);

		Assert.assertEquals(ServiceType.COOKING, p.getServiceType());
	}
}
