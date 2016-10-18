package uk.org.cse.nhm.energycalculator.api.impl;

public class InsolationPlaneUtil {
	public static double getSolarFluxMultiplier(final double solarDeclination, final double latitude, final double angleFromHorizontal, final double angleFromNorth) {
		final int orientation = getOrientationFromNorth(angleFromNorth);
		
		final double[] kA = A_TERMS[orientation];
		final double[] kB = B_TERMS[orientation];
		final double[] kC = C_TERMS[orientation];
		
		final double sin1 = Math.sin(angleFromHorizontal);
		final double sin2 = Math.pow(sin1, 2);
		final double sin3 = Math.pow(sin1, 3);
		
		final double A = kA[0] * sin3 + kA[1] * sin2 + kA[2] * sin1;
		final double B = kB[0] * sin3 + kB[1] * sin2 + kB[2] * sin1;
		final double C = kC[0] * sin3 + kC[1] * sin2 + kC[2] * sin1 + 1;
		
		final double cosSun = Math.cos(latitude - solarDeclination);
		
		final double R = A * Math.pow(cosSun, 2) + B * cosSun + C;
		
		return R;
	}

	private static final int getOrientationFromNorth(double angle) {
		final int slices = A_TERMS.length;
		// so North = 0
		
		final double sliceWidth = Math.PI / slices;
		if (angle > Math.PI) {
			angle -= Math.PI;
		} else if (angle < 0) {
			angle += Math.PI;
		} else if (angle == Math.PI) { // flips over to slices at PI; alternatively could subtract a small delta I guess.
			return slices - 1;
		}
		
		// angle is in the positive half from north through south now
		int slice = (int) Math.floor(angle / sliceWidth);
		return slice;
	}
	
	private static final double A_TERMS[][] =
    {
        {0.056, -5.790, 6.230},
        {-1.397, -1.450, 3.264},
        {-2.850, 2.890, 0.298},
        {-1.546, 1.433, 0.325},
        {-0.241, -0.024, 0.351},
        {0.299, -0.314, 0.670},
        {0.839, -0.607, 0.989},
        {1.595, -1.787, 1.695},
        {2.350, -2.970, 2.400}
    };
	private static final double B_TERMS[][] = 
    {
        {3.320,-0.159,-3.740},
        {3.920,-3.220,-1.135},
        {4.520,-6.280,1.470},
        {2.562,-3.387,0.484},
        {0.604,-0.494,-0.502},
        {0.025,-0.122,-1.496},
        {-0.554,0.251,-2.490},
        {-1.797,2.066,-3.730},
        {-3.040,3.880,-4.970}
    };
	private static final double C_TERMS[][] = 
    {
        {-2.7, 3.450, -1.21},
        {-2.64, 3.705, -1.545},
        {-2.58, 3.960, -1.88},
        {-2.185, 3.010, -1.143},
        {-1.79, 2.06, -0.405},
        {-1.895, 2.17, 0.201},
        {-2.0, 2.28, 0.807},
        {-1.655, 1.775, 1.319},
        {-1.310, 1.27, 1.830}
    };

}


