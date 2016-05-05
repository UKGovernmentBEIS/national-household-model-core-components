package uk.org.cse.nhm.language.validate.efficiency;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import uk.org.cse.nhm.language.definition.action.measure.heating.XBoilerMeasure;
import uk.org.cse.nhm.language.definition.enums.XFuelType;


public class BoilerEfficiencyRequiredValidator implements ConstraintValidator<EfficiencyRequired, XBoilerMeasure>{

	@Override
	public void initialize(EfficiencyRequired constraintAnnotation) {
		// noop
	}

	@Override
	public boolean isValid(XBoilerMeasure value, ConstraintValidatorContext context) {
		if(value.getFuel() == XFuelType.Electricity) {
			return true;
		} else {
			return !(value.getWinterEfficiency() == null || value.getSummerEfficiency() == null);
		}
	}
}
