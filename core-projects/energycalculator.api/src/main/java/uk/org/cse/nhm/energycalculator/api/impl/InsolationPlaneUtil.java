package uk.org.cse.nhm.energycalculator.api.impl;

public class InsolationPlaneUtil {

    public static double getSolarFluxMultiplier(final double solarDeclination,
            final double latitude,
            final double angleFromHorizontal,
            final double angleFromNorth) {
        final double[] k = interpolateTableU5(angleFromNorth);

        final double sin1 = Math.sin(angleFromHorizontal / 2);
        final double sin2 = Math.pow(sin1, 2);
        final double sin3 = Math.pow(sin1, 3);

        /*
		BEISDOC
		NAME: Solar orientation parameters
		DESCRIPTION: Orientation parameters for computing solar flux based on orientation
		TYPE: formula
		UNIT: Dimensionless
		SAP: (U3)
                SAP_COMPLIANT: Yes
		BREDEM: 2.4.1B, 2.4.1C, 2.4.1D
                BREDEM_COMPLIANT: Yes
		DEPS: solar-flux-constants, angle-from-north,glazing-angle
		ID: solar-orientation-parameters
		CODSIEB
         */
        final double A = k[0] * sin3 + k[1] * sin2 + k[2] * sin1;
        final double B = k[3] * sin3 + k[4] * sin2 + k[5] * sin1;
        final double C = k[6] * sin3 + k[7] * sin2 + k[8] * sin1 + 1;

        /*
		BEISDOC
		NAME: Solar height factor
		DESCRIPTION: A factor which alters the solar flux received based on the angle of the sun and the latitude.
		TYPE: formula
		UNIT: Dimensionless
		SAP: (U2)
                SAP_COMPLIANT: Yes
		BREDEM: 2.4.1E
                BREDEM_COMPLIANT: Yes
		DEPS: solar-declination, latitude
		ID: solar-height-factor
		CODSIEB
         */
        final double cosSun = Math.cos(latitude - solarDeclination);

        /*
		BEISDOC
		NAME: Solar flux adjustment
		DESCRIPTION: The combined solar flux adjustment caused by the height of the sun, latitude, and orientation
		TYPE: formula
		UNIT: Dimensionless
		SAP: (U2)
                SAP_COMPLIANT: Yes
		BREDEM: 2.4.1F
                BREDEM_COMPLIANT: Yes
		DEPS: solar-height-factor, solar-orientation-parameters
		ID: solar-flux-adjustment
		CODSIEB
         */
        final double R = A * Math.pow(cosSun, 2) + B * cosSun + C;

        return R;
    }

    /**
     * Given @angle in radians from North, generate the three constants A, B and
     * C per table U5 (p176 of SAP2012)
     */
    private static final double[] interpolateTableU5(final double angle) {
        // the rows in table U5T below go from 0 to 2PI
        // our angle could be in any range, so we put it into the desired range
        final double clampedAngle = clampAngle(angle);
        // then we scale it to be between 0 and the number of rows in that table
        final double rowInU5T = (TABLE_U5T.length - 1) * clampedAngle / _2PI;
        // then we find the rows above and below and interpolate between them
        final double rowBelow = Math.floor(rowInU5T);

        if (rowBelow == rowInU5T) {
            return TABLE_U5T[(int) rowBelow];
        } else {
            return interpolateRows(rowInU5T);
        }
    }

    private static final double _2PI = 2 * Math.PI;

    /**
     * @return an angle in radians between 0 and 2PI which points in the same
     * direction as @angle
     */
    private static final double clampAngle(double angle) {
        while (angle < 0) {
            angle += _2PI;
        }
        while (angle > _2PI) {
            angle -= _2PI;
        }
        return angle;
    }

    /**
     * @return an array of 9 elements, each produced by interpolating the
     * corresponding elements from the two rows above and below rowNumber
     * TABLE_U5T
     */
    private static final double[] interpolateRows(final double rowNumber) {
        final int rowBelow = (int) Math.floor(rowNumber);
        final int rowAbove = (int) Math.ceil(rowNumber);

        final double[] low = TABLE_U5T[rowBelow];
        final double[] high = TABLE_U5T[rowAbove];

        final double fabove = rowNumber - rowBelow;
        final double fbelow = rowAbove - rowNumber;

        final double[] result = new double[low.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = low[i] * fbelow + high[i] * fabove;
        }

        return result;
    }

    /*
	BEISDOC
	NAME: Constants for calculation of solar flux
	DESCRIPTION: Constants for calculation of solar flux, based on orientation.
	TYPE: table
	UNIT: Dimensionless
	SAP: Table U5
        SAP_COMPLIANT: Yes
	BREDEM: Table 15
        BREDEM_COMPLIANT: Yes
	ID: solar-flux-constants
	CODSIEB
     */
    private static final double[][] TABLE_U5T
            = {
                // copied, pasted, and transposed
                // and then repeated
                // k1 ,     k2 ,   k3 ,     k4 ,     k5 ,     k6 ,     k7 ,     k8 ,     k9 }
                //N
                {26.3, -38.5, 14.8, -16.5, 27.3, -11.9, -1.06, 0.0872, -0.191},
                // NE (and NW)
                {0.165, -3.68, 3.0, 6.38, -4.53, -0.405, -4.38, 4.89, -1.99},
                // East (and W)
                {1.44, -2.36, 1.07, -0.514, 1.89, -1.64, -0.542, -0.757, 0.604},
                // SE (and SW)
                {-2.95, 2.89, 1.17, 5.67, -3.54, -4.28, -2.72, -0.25, 3.07},
                // South
                {-0.66, -0.106, 2.93, 3.63, -0.374, -7.4, -2.71, -0.991, 4.59},
                // SW
                {-2.95, 2.89, 1.17, 5.67, -3.54, -4.28, -2.72, -0.25, 3.07},
                // W
                {1.44, -2.36, 1.07, -0.514, 1.89, -1.64, -0.542, -0.757, 0.604},
                // NW
                {0.165, -3.68, 3.0, 6.38, -4.53, -0.405, -4.38, 4.89, -1.99},
                // N again
                {26.3, -38.5, 14.8, -16.5, 27.3, -11.9, -1.06, 0.0872, -0.191}
            };

}
