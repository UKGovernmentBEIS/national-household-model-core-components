package uk.org.cse.nhm.energycalculator.impl;

import org.junit.Test;
import org.junit.Assert;

import uk.org.cse.nhm.energycalculator.api.types.MonthType;

public class SAPSeasonalParametersTest {
	@Test
	public void testSolar() {
		// see InsolationPlaneUtil for origin of this; hand-cranked in R.
		final double[] expectation = new double[] {
				46.75207,  76.56784,  97.53384 ,110.23441 ,114.87108 ,110.54778 ,108.01191 ,104.89453 ,101.88561 , 82.58559 , 55.41714,
				40.39809
		};
		
		for (final MonthType mt : MonthType.values()) {
			final SAPSeasonalParameters params = new SAPSeasonalParameters(mt);
			
			final double flux = params.getSolarFlux(Math.PI/2, Math.PI);
			Assert.assertEquals(expectation[mt.ordinal()], flux, 0.1);
		}
	}
	
}
