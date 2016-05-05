package uk.org.cse.nhm.hom.cookers;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.hom.emf.technologies.impl.util.SimpleCooker;

/**
 * Check that the simple cooker works.
 * 
 * @author hinton
 *
 */
public class SimpleCookerTest {
	@Test
	public void testSimpleCookerEmptyHouse() {
		final SimpleCooker cooker = new SimpleCooker();
		cooker.setSourceEnergyType(EnergyType.FuelGAS);
		cooker.setEfficiency(1);
		cooker.setBaseLoad(1);
		cooker.setPersonSensitivity(1);
		
		final IEnergyCalculatorHouseCase house = mock(IEnergyCalculatorHouseCase.class);
		final IEnergyState state = mock(IEnergyState.class);
		final IInternalParameters parameters = mock(IInternalParameters.class);
		final ISpecificHeatLosses losses = mock(ISpecificHeatLosses.class);
		
		cooker.generate(house, parameters, losses, state);
		
		verify(parameters).getNumberOfOccupants();
		verifyNoMoreInteractions(house);
		verifyZeroInteractions(state, parameters, losses);
	}
	
	@Test
	public void testCooker() {
		final int[] people = new int[] {
				1, 
				2, 
				3, 
				4, 
				10};
	
		final double[] efficiency = new double[] {
				0.1,
				0.2,
				0.3,
				0.4,
				0.9
		};
		
		final double[] baseLoad = new double[] {
				5,
				6,
				7,
				100,
				8
		};
		
		final double[] sensitivity = new double[] {
				10,
				15,
				3,
				9,
				4
		};
		
		final double[] demand = new double[] {
				15,
				36,
				16,
				136,
				48
		};
		
		for (int i = 0; i<people.length; i++) {
			runSimpleCooker(people[i], efficiency[i], baseLoad[i], sensitivity[i], demand[i], demand[i] * (1-efficiency[i]));
		}
	}
	
	public void runSimpleCooker(final double numPeople, final double efficiency, final double baseLoad, final double sensitivity, 
			final double expectedEnergyConsumption, final double expectedGains) {
		final SimpleCooker cooker = new SimpleCooker();
		cooker.setSourceEnergyType(EnergyType.FuelGAS);
		cooker.setEfficiency(efficiency);
		cooker.setBaseLoad(baseLoad);
		cooker.setPersonSensitivity(sensitivity);
		
		final IEnergyCalculatorHouseCase house = mock(IEnergyCalculatorHouseCase.class);
		final IEnergyState state = mock(IEnergyState.class);
		final IInternalParameters parameters = mock(IInternalParameters.class);
		final ISpecificHeatLosses losses = mock(ISpecificHeatLosses.class);
		
		when(parameters.getNumberOfOccupants()).thenReturn(numPeople);
		cooker.setHob(true);cooker.setOven(true);
		cooker.generate(house, parameters, losses, state);
		
		verify(parameters, atLeastOnce()).getNumberOfOccupants();
		
		verifyNoMoreInteractions(house);
		verifyZeroInteractions(parameters, losses);
		
		verify(state).increaseSupply(EnergyType.GainsCOOKING_GAINS, expectedGains);
		verify(state).increaseDemand(EnergyType.FuelGAS, expectedEnergyConsumption);
	}
}
