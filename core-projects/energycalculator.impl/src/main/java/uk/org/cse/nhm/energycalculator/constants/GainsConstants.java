package uk.org.cse.nhm.energycalculator.constants;

import uk.org.cse.nhm.energycalculator.api.ConstantDescription;
import uk.org.cse.nhm.energycalculator.api.IConstant;

@ConstantDescription("Factors determining how much of various different types of gains are used.")
public enum GainsConstants implements IConstant {

    /*
	BEISDOC
	NAME: Lighting Gains Utilisation
	DESCRIPTION: The proportion of lighting energy that provides useful heating gains
	TYPE: value
	UNIT: Dimensionless
	SAP: (L9)
        SAP_COMPLIANT: Yes
	BREDEM: 6B
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	ID: lighting-gains-utilisation
	CODSIEB
     */
    @ConstantDescription("The proportion of lighting energy that provides useful gains")
    LIGHTING_GAIN_USEFULNESS(0.85),
    /*
	BEISDOC
	NAME: Hot Water Direct Gains Usefulness
	DESCRIPTION: The proportion of hot water energy that provides useful gains
	TYPE: value
	UNIT: Dimensionless
	SAP: (65)
        SAP_COMPLIANT: Yes
	BREDEM: 6I
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	ID: hot-water-direct-gains-usefulness
	CODSIEB
     */
    @ConstantDescription("The proportion of hot water energy that provides useful gains")
    HOT_WATER_DIRECT_GAINS(0.25),
    /*
	BEISDOC
	NAME: Hot Water System Gains Usefulness
	DESCRIPTION: The proportion of hot water system losses (primary pipework, tank, distribution etc) that provides useful gains
	TYPE: value
	UNIT: Dimensionless
	SAP: (65)
        SAP_COMPLIANT: Yes
	BREDEM: 6I
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	ID: hot-water-system-gains-usefulness
	CODSIEB
     */
    @ConstantDescription("The proportion of hot water system losses (primary pipework, tank, distribution etc) that provides useful gains")
    HOT_WATER_SYSTEM_GAINS(0.8);

    private final double[] values;

    GainsConstants(final double... values) {
        this.values = values;
    }

    @Override
    public <T> T getValue(Class<T> clazz) {
        if (clazz.isAssignableFrom(double[].class)) {
            if (values == null) {
                throw new RuntimeException(this + " cannot be read as a double[]");
            }
            return clazz.cast(values);
        } else if (clazz.isAssignableFrom(Double.class)) {
            if (values.length != 1) {
                throw new RuntimeException(this + " cannot be read as a double");
            }
            return clazz.cast(values[0]);
        } else {
            throw new RuntimeException(this + " cannot be read as a " + clazz.getSimpleName());
        }
    }

}
