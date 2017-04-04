package uk.org.cse.nhm.energycalculator.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.constants.EnergyCalculatorConstants;
import uk.org.cse.nhm.energycalculator.impl.IStructuralInfiltrationAccumulator;
import uk.org.cse.nhm.energycalculator.impl.StructuralInfiltrationAccumulator;

public class StructuralInfiltrationAccumulatorTest {
	private IStructuralInfiltrationAccumulator accumulator;
	private IEnergyCalculatorHouseCase houseCase;
	private IEnergyCalculatorParameters parameters;
	
	@Before
	public void createAccumulator() {
		accumulator = new StructuralInfiltrationAccumulator(DefaultConstants.INSTANCE);
		houseCase = mock(IEnergyCalculatorHouseCase.class);
		parameters = mock(IEnergyCalculatorParameters.class);
	}
	
	@After
	public void clearAccumulator() {
		accumulator = null;
		houseCase = null;
		parameters = null;
	}
	
	@Test
	public void testNothing() {
		when(houseCase.getNumberOfStoreys()).thenReturn(1);
		when(houseCase.hasDraughtLobby()).thenReturn(true);
		Assert.assertEquals("Infiltration is window only if nothing is presented",
                            0.25, accumulator.getAirChangeRate(houseCase, parameters), 0.01);
	}
	
	@Test
	public void testWindowAccumulation() {
		when(houseCase.hasDraughtLobby()).thenReturn(true);
		when(houseCase.getHouseVolume()).thenReturn(1.0d);
		
		when(houseCase.getNumberOfShelteredSides()).thenReturn(0);
		when(houseCase.getNumberOfStoreys()).thenReturn(1);
		
		Assert.assertEquals(
				"Window infiltration is constant",
				0.25
				, accumulator.getAirChangeRate(houseCase, parameters)
                , 0.01
				);
	}
	
	@Test
	public void testWindowAccumulation2() {
		when(houseCase.hasDraughtLobby()).thenReturn(true);
		when(houseCase.getHouseVolume()).thenReturn(1.0d);
	
		when(houseCase.getNumberOfShelteredSides()).thenReturn(0);
		when(houseCase.getNumberOfStoreys()).thenReturn(1);
		when(houseCase.getDraughtStrippedProportion()).thenReturn(0.5);
		Assert.assertEquals(
				"Window infiltration is constant",
				(0.25 + 0.05) / 2
				, accumulator.getAirChangeRate(houseCase, parameters)
                , 0.01
				);
	}
	
	@Test
	public void testWallAccumulation() {
		when(houseCase.hasDraughtLobby()).thenReturn(true);
		when(houseCase.getHouseVolume()).thenReturn(1.0d);
	
		when(houseCase.getNumberOfShelteredSides()).thenReturn(0);
		when(houseCase.getNumberOfStoreys()).thenReturn(1);
		
		accumulator.addWallInfiltration(10, 1);
		accumulator.addWallInfiltration(2, 2);
		
		Assert.assertEquals("Biggest wall infiltration wins",
                            1.25, accumulator.getAirChangeRate(houseCase, parameters),
                            0.01);
	}
	
	@Test
	public void testWallAccumulation2() {
		when(houseCase.hasDraughtLobby()).thenReturn(true);
		when(houseCase.getHouseVolume()).thenReturn(1.0d);
	
		when(houseCase.getNumberOfShelteredSides()).thenReturn(0);
		when(houseCase.getNumberOfStoreys()).thenReturn(1);
		
		accumulator.addWallInfiltration(10, 1);
		accumulator.addWallInfiltration(10, 2);
		
		accumulator.addWallInfiltration(9, 10);
		
		Assert.assertEquals("Biggest wall wins",
                            2.25, accumulator.getAirChangeRate(houseCase, parameters),
                            0.01);
	}
	
	@Test
	public void testStackEffect() {
		when(houseCase.hasDraughtLobby()).thenReturn(true);
		when(houseCase.getHouseVolume()).thenReturn(1.0d);
	
		when(houseCase.getNumberOfShelteredSides()).thenReturn(0);
		when(houseCase.getNumberOfStoreys()).thenReturn(4);
		
		
		
		Assert.assertEquals("Stack effect parameter applies",
                            0.25 + 3 * DefaultConstants.INSTANCE.get(EnergyCalculatorConstants.STACK_EFFECT_AIR_CHANGE_PARAMETER), accumulator.getAirChangeRate(houseCase, parameters),
                            0.01);
	}
	
	@Test
	public void testDraughtLobby() {
		when(houseCase.hasDraughtLobby()).thenReturn(false);
		when(houseCase.getHouseVolume()).thenReturn(1.0d);
	
		when(houseCase.getNumberOfShelteredSides()).thenReturn(0);
		when(houseCase.getNumberOfStoreys()).thenReturn(1);

		Assert.assertEquals("Draught lobby and windows are only effect",
				0.25 + 
				DefaultConstants.INSTANCE.get(EnergyCalculatorConstants.DRAUGHT_LOBBY_AIR_CHANGE_PARAMETER),
                            accumulator.getAirChangeRate(houseCase, parameters),
                            0.01);
	}
}
