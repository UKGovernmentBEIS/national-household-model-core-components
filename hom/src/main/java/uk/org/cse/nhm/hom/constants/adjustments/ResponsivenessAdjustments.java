package uk.org.cse.nhm.hom.constants.adjustments;

import uk.org.cse.nhm.energycalculator.api.ConstantDescription;
import uk.org.cse.nhm.energycalculator.api.IConstant;

/**
 * This is SAP table 4d
 * @author hinton
 *
 */
@ConstantDescription("Responsiveness adjustments, from SAP table 4d.")
public enum ResponsivenessAdjustments implements IConstant {
	/**
	 * This is in the order : radiators, underfloor timber, underfloor screed, underfloor concrete, convectors
	 */
	@ConstantDescription("Wet system responsivness by emitter type (rads, underfloor timber, underfloor screed, underfloor concrete, fan convectors)")
	WET_SYSTEM_RESPONSIVENESS(1, 1, 0.75, 0.25, 1)
	;
	
	private final double[] values;
	
	ResponsivenessAdjustments(final double... values) {
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
