package uk.org.cse.nhm.energycalculator.constants;

import uk.org.cse.nhm.energycalculator.api.ConstantDescription;
import uk.org.cse.nhm.energycalculator.api.IConstant;
import uk.org.cse.nhm.energycalculator.api.types.OvershadingType;
import uk.org.cse.nhm.energycalculator.impl.demands.LightingDemand09;

/**
 * Constants controlling CHM lighting demand, used by {@link LightingDemand09}
 * 
 * @author hinton
 */
@ConstantDescription("Constants controlling CHM-style lighting demand")
public enum LightingConstants09 implements IConstant {
	@ConstantDescription("In the lighting demand equation A*(floor area * occupants) ^ B, this is B")
	LIGHT_DEMAND_EXPONENT(0.4714),

	@ConstantDescription("The maximum value for the daylight saving coefficient")
	DAYLIGHT_PARAMETER_MAXIMUM(0.095),
	/**
	 * The daylight saving coefficient, if below the threshold, is computed as Ax^2 + Bx + C; this is C
	 */
	@ConstantDescription("The daylight saving coefficient, if below the threshold, is computed as Ax^2 + Bx + C; this is C")
	DAYLIGHT_PARAMETER_0_COEFFICIENT(1.433),
	/**
	 * The daylight saving coefficient, if below the threshold, is computed as
	 * Ax^2 + Bx + C; this is B
	 */
	@ConstantDescription("The daylight saving coefficient, if below the threshold, is computed as Ax^2 + Bx + C; this is B")
	DAYLIGHT_PARAMETER_1_COEFFICIENT(-9.94),
	/**
	 * The daylight saving coefficient, if below the threshold, is computed as
	 * Ax^2 + Bx + C; this is A
	 */
	@ConstantDescription("The daylight saving coefficient, if below the threshold, is computed as Ax^2 + Bx + C; this is A")
	DAYLIGHT_PARAMETER_2_COEFFICIENT(52.2),
	
	/**
	 * This is the mean loss factor for overshading, by {@link OvershadingType},
	 * in the same order as {@link OvershadingType}.
	 * Source: SAP table 6d
	 */
	@ConstantDescription("This is the mean loss factor for overshading (an array)")
	OVERSHADING_ACCESS_FACTORS(new double[] {1.0, 1.0, 0.83, 0.67, 0.50}),
	
	@ConstantDescription("In the lighting monthly adjustment equation a + b * cos( 2 pi * (month - c) / 12, these are a, b, and c")
	ADJUSTMENT_FACTOR_TERMS(1, 0.5, 0.2)
	;
	
	private final Double defaultValue;
	private final double[] values;
	
	private LightingConstants09(final double value) {
		defaultValue = value;
		values = null;
	}
	
	private LightingConstants09(final double... values) {
		defaultValue = null;
		this.values = values;
	}

	@Override
	public <T> T getValue(Class<T> clazz) {
		if (Double.class.isAssignableFrom(clazz)) {
			return clazz.cast((Double) defaultValue);
		} else if (double[].class.isAssignableFrom(clazz)) {
			return clazz.cast(values);
		} else {
			throw new RuntimeException("CHM Lighting constant " + this + " has no representation as a "+ clazz.getSimpleName());
		}
	}

}
