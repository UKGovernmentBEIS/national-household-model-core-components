package uk.org.cse.nhm.language.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import uk.org.cse.nhm.language.definition.context.XEnergyConstantsContext.XEnergyConstant;
import uk.org.cse.nhm.language.definition.context.XEnergyConstantsContext.XEnergyConstantType;

public class EnergyConstantValidator implements ConstraintValidator<ValidEnergyConstant, XEnergyConstant> {
	@Override
	public void initialize(ValidEnergyConstant constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(XEnergyConstant value, ConstraintValidatorContext context) {
		if (value.getConstant() == null) {
			context.buildConstraintViolationWithTemplate("{uk.org.cse.nhm.language.validate.ValidEnergyConstant.noname}").addConstraintViolation();
			return false;
		}
		if (value.getValue() == null || value.getValue().trim().isEmpty()) {
			context.buildConstraintViolationWithTemplate("{uk.org.cse.nhm.language.validate.ValidEnergyConstant.novalue}").addConstraintViolation();
			return false;
		}
		final XEnergyConstantType name = value.getConstant();
		final String[] parts = value.getValue().split(",");
		
		if (parts.length != name.getArity()) {
			context.buildConstraintViolationWithTemplate("{uk.org.cse.nhm.language.validate.ValidEnergyConstant.wrongarity}")
				.addConstraintViolation();
			return false;
		}
		
		for (final String s : parts) {
			try {
				Double.parseDouble(s);
			} catch (NumberFormatException nfe) {
				context.buildConstraintViolationWithTemplate("{uk.org.cse.nhm.language.validate.ValidEnergyConstant.nan}").addConstraintViolation();
				return false;
			}
		}
		
		return true;
	}
}
