package uk.org.cse.stockimport.imputation.apertures;

import org.junit.Assert;

import org.junit.Test;

import uk.org.cse.nhm.hom.components.fabric.types.FrameType;
import uk.org.cse.stockimport.imputation.apertures.windows.FrameFactors;
import uk.org.cse.stockimport.imputation.apertures.windows.IWindowFrameFactor;

public class FrameFactorTest {

    @Test
    public void testFrameFactors() {
        final IWindowFrameFactor factors = new FrameFactors();

        Assert.assertEquals(0.7, factors.getFrameFactor(FrameType.Wood));
        Assert.assertEquals(0.8, factors.getFrameFactor(FrameType.Metal));
        Assert.assertEquals(0.7, factors.getFrameFactor(FrameType.uPVC));
    }
}
