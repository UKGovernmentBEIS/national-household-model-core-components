package uk.org.cse.nhm.language.definition.batch.inputs.combinators.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import uk.org.cse.nhm.language.definition.batch.inputs.XInputs;
import uk.org.cse.nhm.language.definition.batch.inputs.combinators.XConcatenate;

public class DelegatePlaceholderConsistencyValidator implements ConstraintValidator<DelegatePlaceholdersConsistent, XConcatenate>{

	@Override
	public void initialize(DelegatePlaceholdersConsistent constraintAnnotation) {
		// no-op
	}

	@Override
	public boolean isValid(XConcatenate value, ConstraintValidatorContext context) {
		List<XInputs> delegates = value.getDelegates();
		List<String> expected = delegates.get(0).getPlaceholders();
		for(XInputs i : delegates) {
			if(!i.getPlaceholders().equals(expected)) {
				return false;
			}
		}
		return true;
	}
}
