package uk.org.cse.nhm.language.definition.two.actions;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.language.definition.sequence.XNumberDeclaration;
import uk.org.cse.nhm.language.definition.sequence.XScope;

public class RequiresScopeValidator implements ConstraintValidator<RequireScope, XNumberDeclaration>{

	private ImmutableSet<XScope> ok;

	@Override
	public void initialize(final RequireScope constraintAnnotation) {
		ok = ImmutableSet.copyOf(constraintAnnotation.value());
	}

	@Override
	public boolean isValid(final XNumberDeclaration value,
			final ConstraintValidatorContext context) {
		if (value != null) {
			return ok.contains(value.getOn()); 
		} else {
			return true;
		}
	}
}
