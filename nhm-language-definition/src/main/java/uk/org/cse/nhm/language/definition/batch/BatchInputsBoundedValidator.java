package uk.org.cse.nhm.language.definition.batch;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import uk.org.cse.nhm.language.definition.batch.inputs.XInputs;

public class BatchInputsBoundedValidator implements ConstraintValidator<BatchInputsBounded, XInputs>{

	@Override
	public void initialize(final BatchInputsBounded constraintAnnotation) {
	}

	@Override
	public boolean isValid(final XInputs value, final ConstraintValidatorContext context) {
		return value.hasBound();
	}
}
