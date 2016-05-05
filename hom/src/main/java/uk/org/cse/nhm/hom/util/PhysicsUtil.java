package uk.org.cse.nhm.hom.util;

/**
 * A utility class containing methods for working with physical values
 * 
 * @since 1.3.4
 * @author hinton
 *
 */
public class PhysicsUtil {
	/**
	 * Get a u-value by adding a u-value to an r-value
	 * @since 1.3.4
	 * @param uValue a u-value (specific heat per unit area)
	 * @param rValue an r-value (thermal resistance)
	 * @return a new u-value for a material with u-value uValue that has had a layer of insulation added of the given rValue.
	 */
	public static final double addRValueToUValue(double uValue, double rValue) {
		return 1 / (rValue + 1/uValue);
	}
}
