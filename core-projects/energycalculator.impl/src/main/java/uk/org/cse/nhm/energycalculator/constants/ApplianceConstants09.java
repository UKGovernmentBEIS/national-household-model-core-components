package uk.org.cse.nhm.energycalculator.constants;

import uk.org.cse.nhm.energycalculator.api.ConstantDescription;
import uk.org.cse.nhm.energycalculator.api.IConstant;

@ConstantDescription("Coefficients for the appliance demand model")
public enum ApplianceConstants09 implements IConstant {
	
	/*
	BEISDOC
	NAME: Appliance demand coefficient
	DESCRIPTION: The coefficient in the appliance demand formula
	TYPE: value
	UNIT: Watts
	SAP: (L10)
	BREDEM: 1I
	SET: context.energy-constants
	CONVERSION: From kWh/year to W (1000 / (365.25 * 24))
	NOTES: The SAP and BREDEM values are different here, but it is not clear why.
	ID: appliance-demand-coefficient
	CODSIEB
	*/
	@ConstantDescription("The coefficient in the appliance demand formula (watts) for BREDEM 2012")
	APPLIANCE_DEMAND_COEFFICIENT_BREDEM(21.081451),
	@ConstantDescription("The coefficient in the appliance demand formula (watts) for SAP 2012")
	APPLIANCE_DEMAND_COEFFICIENT_SAP(23.705225),
	
	/*
	BEISDOC
	NAME: Appliance demand exponent
	DESCRIPTION: The exponent in the appliance demand formula
	TYPE: value
	UNIT: Dimensionless
	SAP: (L10)
	BREDEM: 1I
	SET: context.energy-constants
	ID: appliance-demand-exponent
	CODSIEB
	*/
	@ConstantDescription("The exponent in the appliance demand formula")
	APPLIANCE_DEMAND_EXPONENT(0.4714),
	
	/*
	BEISDOC
	NAME: Appliance Demand Cosine Coefficient
	DESCRIPTION: The coefficient of the cosine in the appliance demand monthly adjustment factor
	TYPE: value
	UNIT: Dimensionless
	SAP: (L11)
	BREDEM: 1J
	SET: context.energy-constants
	ID: appliance-adjustement-cosine-coefficient
	CODSIEB
	*/
	@ConstantDescription("The coefficient of the cosine in the appliance demand monthly adjustment factor")
	APPLIANCE_DEMAND_COSINE_COEFFICIENT(0.157),
	
	/*
	BEISDOC
	NAME: Appliance Demand Cosine Offset
	DESCRIPTION: The offset within the cosine in the appliance demand monthly adjustment factor
	TYPE: value
	UNIT: Unknown - probably Radians times a constant?
	SAP: (L11)
	BREDEM: 1J
	SET: context.energy-constants
	ID: appliance-adjustment-cosine-offset
	CODSIEB
	*/
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
