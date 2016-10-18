package uk.org.cse.nhm.energycalculator.constants;

import uk.org.cse.nhm.energycalculator.api.ConstantDescription;
import uk.org.cse.nhm.energycalculator.api.IConstant;

@ConstantDescription("Coefficients for the appliance demand model")
public enum ApplianceConstants09 implements IConstant {
	@ConstantDescription("The coefficient in the appliance demand formula (watts)")
	APPLIANCE_DEMAND_COEFFICIENT(23.70573),
	@ConstantDescription("The exponent in the appliance demand formula")
	APPLIANCE_DEMAND_EXPONENT(0.4714),
	@ConstantDescription("The coefficient of the cosine in the appliance demand monthly adjustment factor")
	APPLIANCE_DEMAND_COSINE_COEFFICIENT(0.157),
	@ConstantDescription("The offset within the cosine in the appliance demand monthly adjustment factor")
	APPLIANCE_DEMAND_COSINE_OFFSET(1.78), 
	@ConstantDescription("The high rate fraction for appliances, by tarrif type (an array)")
	APPLIANCE_HIGH_RATE_FRACTION(1.0, 0.9, 0.8)
	;

	private final double[] values;
	
	ApplianceConstants09(final double... values) {
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
