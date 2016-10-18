package uk.org.cse.nhm.hom.constants;

import uk.org.cse.nhm.energycalculator.api.ConstantDescription;
import uk.org.cse.nhm.energycalculator.api.IConstant;

@ConstantDescription("The constants describing ventilation rates for chimneys and flues.")
public enum VentilationConstants implements IConstant {
	@ConstantDescription("Ventilation rate for open flue")
	OPEN_FLUE_VENTILATION_RATE(20.0), 
	
	@ConstantDescription("Ventilation rate for chimney.")
	CHIMNEY_VENTILATION_RATE(40.0)
	;
	
	private final double[] values;
	
	VentilationConstants(final double... values) {
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
