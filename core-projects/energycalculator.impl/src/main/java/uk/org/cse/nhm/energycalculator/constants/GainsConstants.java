package uk.org.cse.nhm.energycalculator.constants;

import uk.org.cse.nhm.energycalculator.api.ConstantDescription;
import uk.org.cse.nhm.energycalculator.api.IConstant;

@ConstantDescription("Factors determining how much of various different types of gains are used.")
public enum GainsConstants implements IConstant {
	
	/*
	BEISDOC
	NAME: Lighting Gains Utilisation
	DESCRIPTION: The proportion of lighting energy that provides useful heating gains
	TYPE: valyue
	UNIT: Dimensionless
	SAP: (L9)
	BREDEM: 6B
	SET: context.energy-constants
	ID: lighting-gains-utilisation
	CODSIEB
	*/
	@ConstantDescription("The proportion of lighting energy that provides useful gains")
	LIGHTING_GAIN_USEFULNESS(0.85),
	
	@ConstantDescription("The proportion of hot water energy that provides useful gains")
	HOT_WATER_DIRECT_GAINS(0.25),
	@ConstantDescription("The proportion of hot water system losses (primary pipework, tank, distribution etc) that provides useful gains")
	HOT_WATER_SYSTEM_GAINS(0.8);

	private final double[] values;
	
	GainsConstants(final double... values) {
		this.values = values;
	}
	
	@Override
	public <T> T getValue(Class<T> clazz) {
		if (clazz.isAssignableFrom(double[].class)) {
			if (values == null) throw new RuntimeException(this + " cannot be read as a double[]");
			return clazz.cast(values);
		} else if (clazz.isAssignableFrom(Double.class)) {
			if (values.length != 1) throw new RuntimeException(this + " cannot be read as a double");
			return clazz.cast(values[0]);
		} else {
			throw new RuntimeException(this + " cannot be read as a " + clazz.getSimpleName());
		}
	}

}
