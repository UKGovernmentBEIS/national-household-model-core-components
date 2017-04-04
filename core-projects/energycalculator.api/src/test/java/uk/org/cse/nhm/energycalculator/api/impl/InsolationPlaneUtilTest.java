package uk.org.cse.nhm.energycalculator.api.impl;
import org.junit.Test;
import org.junit.Assert;

public class InsolationPlaneUtilTest {
    @Test
    public void fluxMuliplierIsOneWhenFlat() {
        Assert.assertEquals(1, InsolationPlaneUtil.getSolarFluxMultiplier(
                                0, 0, // declination = latitude means sun is overhead
                                0, 0  // facing up, and north but north is not important
                                ), 0.01);
        Assert.assertEquals(1, InsolationPlaneUtil.getSolarFluxMultiplier(
                                0, 0, // declination = latitude means sun is overhead
                                0, Math.PI  // facing up and south
                                ), 0.01);
        Assert.assertEquals(1, InsolationPlaneUtil.getSolarFluxMultiplier(
                                0, 0, // declination = latitude means sun is overhead
                                0, -1  // facing up and elsewhere
                                ), 0.01);
    }

    @Test
    public void westEqualsEast() {
    	Assert.assertEquals(
			InsolationPlaneUtil.getSolarFluxMultiplier(0, 0, 0, Math.PI * 0.5),
			InsolationPlaneUtil.getSolarFluxMultiplier(0, 0, 0, Math.PI * 1.5),
			0.001
		);
    }

    @Test
    public void southWestEqualsSouthEast() {
    	Assert.assertEquals(
    			InsolationPlaneUtil.getSolarFluxMultiplier(0, 0, 0, Math.PI * 0.75),
    			InsolationPlaneUtil.getSolarFluxMultiplier(0, 0, 0, Math.PI * 1.25),
    			0.001
    		);
    }

    @Test
    public void continuity() {
    	Double previous = null;
    	for (int i = 0; i < 360; i++) {

    		final double current = InsolationPlaneUtil.getSolarFluxMultiplier(18.8, 35.5, Math.PI / 2, Math.PI * i / 180d);

    		if (previous != null) {
    			Assert.assertEquals("Previous should be quite similar to current.", previous, current, 0.1);
    		}

    		previous = current;
    	}
    }

    @Test
    public void fluxMultiplierCalculatedByHand() {
        // doing the steps by hand for a real place:
        // say we take South West, with a representative latitude of 50.5 degrees
        // and May, with a declination of 18.8 degrees

        final double latitude = 50.8*Math.PI/180;
        final double declination = 18.8*Math.PI/180;

        // let us say we are facing south, and have an inclination of 30 deg from horizontal

        final double tilt = 30*Math.PI/180;
        final double sp2 = Math.sin(tilt/2);

        double A = -0.66*Math.pow(sp2, 3) - 0.106*Math.pow(sp2, 2) + 2.93 * sp2;
        double B = 3.63*Math.pow(sp2, 3) - 0.374*Math.pow(sp2, 2) -7.4 * sp2;
        double C = -2.71*Math.pow(sp2, 3) -0.991*Math.pow(sp2, 2) +4.59*sp2 + 1;

        double result =
            A*Math.pow(Math.cos(latitude - declination), 2) +
            B*Math.cos(latitude - declination) +
            C;

        Assert.assertEquals(
            result,
            InsolationPlaneUtil.getSolarFluxMultiplier(
                declination, latitude,
                tilt, Math.PI
                ), 0.01
            );

        A = 26.3*Math.pow(sp2, 3) - 38.5*Math.pow(sp2, 2) + 14.8 * sp2;
        B = -16.5*Math.pow(sp2, 3) + 27.3*Math.pow(sp2, 2) -11.9 * sp2;
        C = -1.06*Math.pow(sp2, 3) +0.0872*Math.pow(sp2, 2) -0.191*sp2 + 1;

        result =
            A*Math.pow(Math.cos(latitude - declination), 2) +
            B*Math.cos(latitude - declination) +
            C;

        Assert.assertEquals(
            result,
            InsolationPlaneUtil.getSolarFluxMultiplier(
                declination, latitude,
                tilt, 0
                ), 0.01
            );
    }
}
