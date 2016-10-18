package uk.org.cse.stockimport.imputation.apertures;

import junit.framework.Assert;

import org.junit.Test;

import uk.org.cse.nhm.hom.components.fabric.types.GlazingType;
import uk.org.cse.nhm.hom.components.fabric.types.WindowInsulationType;
import uk.org.cse.stockimport.imputation.apertures.windows.ITransmittanceFactors;
import uk.org.cse.stockimport.imputation.apertures.windows.TransmittanceFactors;

public class TransmittanceFactorsTest {
	/*					g		l
	 * Single Glazing	0.85	0.9
Double Glazing (air filled)	0.76	0.8
Double Glazing (Low-E, hard-coat)	0.72	0.8
Double Glazing (Low-E, soft-coat)	0.63	0.8
Window with secondary glazing	0.76	0.8
Triple Glazing (air filled)	0.68	0.7
Triple glazed (Low-E, hard-coat)	0.64	0.7
Triple glazed (Low-E, soft-coat)	0.57	0.7

	 */
	
	@Test
	public void testLightTransmittance() {
		final ITransmittanceFactors factors = new TransmittanceFactors();
		Assert.assertEquals(0.9, factors.getLightTransmittance(GlazingType.Single, null));
		Assert.assertEquals(0.8, factors.getLightTransmittance(GlazingType.Double, WindowInsulationType.Air));
		Assert.assertEquals(0.8, factors.getLightTransmittance(GlazingType.Double, WindowInsulationType.LowEHardCoat));
		Assert.assertEquals(0.8, factors.getLightTransmittance(GlazingType.Double, WindowInsulationType.LowESoftCoat));
		Assert.assertEquals(0.8, factors.getLightTransmittance(GlazingType.Secondary, null));
		Assert.assertEquals(0.7, factors.getLightTransmittance(GlazingType.Triple, WindowInsulationType.Air));
		Assert.assertEquals(0.7, factors.getLightTransmittance(GlazingType.Triple, WindowInsulationType.LowEHardCoat));
		Assert.assertEquals(0.7, factors.getLightTransmittance(GlazingType.Triple, WindowInsulationType.LowESoftCoat));
	}
	
	@Test
	public void testGainsTransmittance() {
		final ITransmittanceFactors factors = new TransmittanceFactors();
		Assert.assertEquals(0.85, factors.getGainsTransmittance(GlazingType.Single, null));
		Assert.assertEquals(0.76, factors.getGainsTransmittance(GlazingType.Double, WindowInsulationType.Air));
		Assert.assertEquals(0.72, factors.getGainsTransmittance(GlazingType.Double, WindowInsulationType.LowEHardCoat));
		Assert.assertEquals(0.63, factors.getGainsTransmittance(GlazingType.Double, WindowInsulationType.LowESoftCoat));
		Assert.assertEquals(0.76, factors.getGainsTransmittance(GlazingType.Secondary, null));
		Assert.assertEquals(0.68, factors.getGainsTransmittance(GlazingType.Triple, WindowInsulationType.Air));
		Assert.assertEquals(0.64, factors.getGainsTransmittance(GlazingType.Triple, WindowInsulationType.LowEHardCoat));
		Assert.assertEquals(0.57, factors.getGainsTransmittance(GlazingType.Triple, WindowInsulationType.LowESoftCoat));
	}
}
