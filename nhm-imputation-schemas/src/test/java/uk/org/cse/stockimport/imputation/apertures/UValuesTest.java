package uk.org.cse.stockimport.imputation.apertures;

import junit.framework.Assert;

import org.junit.Test;

import uk.org.cse.nhm.hom.components.fabric.types.FrameType;
import uk.org.cse.nhm.hom.components.fabric.types.GlazingType;
import uk.org.cse.nhm.hom.components.fabric.types.WindowInsulationType;
import uk.org.cse.stockimport.imputation.apertures.windows.IWindowUValues;
import uk.org.cse.stockimport.imputation.apertures.windows.UValues;

public class UValuesTest {
	@Test
	public void testUValues() {
		final IWindowUValues uvalues = new UValues();
		
		testSingleGlazing(uvalues);

		testDoubleGlazing(uvalues);
		
		testTripleGlazing(uvalues);
		
		testSecondaryGlazing(uvalues);
	}

	private void testSecondaryGlazing(IWindowUValues uvalues) {
		Assert.assertEquals(2.2, uvalues.getUValue(FrameType.Wood, GlazingType.Secondary, null), 0.05);
		Assert.assertEquals(2.2, uvalues.getUValue(FrameType.uPVC, GlazingType.Secondary, null), 0.05);
	}

	private void testDoubleGlazing(final IWindowUValues uvalues) {
		Assert.assertEquals(2.8, uvalues.getUValue(FrameType.Wood, GlazingType.Double, WindowInsulationType.Air), 0.05);
		Assert.assertEquals(2.4, uvalues.getUValue(FrameType.Wood, GlazingType.Double, WindowInsulationType.LowEHardCoat), 0.05);
		Assert.assertEquals(2.4, uvalues.getUValue(FrameType.Wood, GlazingType.Double, WindowInsulationType.LowESoftCoat), 0.05);
		
		Assert.assertEquals(3.2, uvalues.getUValue(FrameType.Metal, GlazingType.Double, WindowInsulationType.Air), 0.05);
		Assert.assertEquals(2.9, uvalues.getUValue(FrameType.Metal, GlazingType.Double, WindowInsulationType.LowEHardCoat), 0.05);
		Assert.assertEquals(2.8, uvalues.getUValue(FrameType.Metal, GlazingType.Double, WindowInsulationType.LowESoftCoat), 0.05);

		Assert.assertEquals(2.8, uvalues.getUValue(FrameType.uPVC, GlazingType.Double, WindowInsulationType.Air), 0.05);
		Assert.assertEquals(2.4, uvalues.getUValue(FrameType.uPVC, GlazingType.Double, WindowInsulationType.LowEHardCoat), 0.05);
		Assert.assertEquals(2.4, uvalues.getUValue(FrameType.uPVC, GlazingType.Double, WindowInsulationType.LowESoftCoat), 0.05);
	}
	
	private void testTripleGlazing(final IWindowUValues uvalues) {
		Assert.assertEquals(2.2, uvalues.getUValue(FrameType.Wood, GlazingType.Triple, WindowInsulationType.Air), 0.05);
		Assert.assertEquals(1.9, uvalues.getUValue(FrameType.Wood, GlazingType.Triple, WindowInsulationType.LowEHardCoat), 0.05);
		Assert.assertEquals(1.9, uvalues.getUValue(FrameType.Wood, GlazingType.Triple, WindowInsulationType.LowESoftCoat), 0.05);
		
		Assert.assertEquals(2.6, uvalues.getUValue(FrameType.Metal, GlazingType.Triple, WindowInsulationType.Air), 0.05);
		Assert.assertEquals(2.4, uvalues.getUValue(FrameType.Metal, GlazingType.Triple, WindowInsulationType.LowEHardCoat), 0.05);
		Assert.assertEquals(2.3, uvalues.getUValue(FrameType.Metal, GlazingType.Triple, WindowInsulationType.LowESoftCoat), 0.05);

		Assert.assertEquals(2.2, uvalues.getUValue(FrameType.uPVC, GlazingType.Triple, WindowInsulationType.Air), 0.05);
		Assert.assertEquals(1.9, uvalues.getUValue(FrameType.uPVC, GlazingType.Triple, WindowInsulationType.LowEHardCoat), 0.05);
		Assert.assertEquals(1.9, uvalues.getUValue(FrameType.uPVC, GlazingType.Triple, WindowInsulationType.LowESoftCoat), 0.05);
	}

	private void testSingleGlazing(final IWindowUValues uvalues) {
		Assert.assertEquals(4.0, uvalues.getUValue(FrameType.Wood, GlazingType.Single, null), 0.05);
		Assert.assertEquals(4.6, uvalues.getUValue(FrameType.Metal, GlazingType.Single, null), 0.05);
		Assert.assertEquals(4.0, uvalues.getUValue(FrameType.uPVC, GlazingType.Single, null), 0.05);
	}
}
