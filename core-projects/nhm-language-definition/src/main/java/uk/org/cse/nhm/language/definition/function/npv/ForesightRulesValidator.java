package uk.org.cse.nhm.language.definition.function.npv;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import uk.org.cse.nhm.language.definition.action.XForesightLevel;

public class ForesightRulesValidator implements ConstraintValidator<ForesightRules, List<XForesightLevel>>{
	@Override
	public void initialize(final ForesightRules constraintAnnotation) {}

	@Override
	public boolean isValid(final List<XForesightLevel> value, final ConstraintValidatorContext context) {
		if (value == null) return false;
		if (value.contains(XForesightLevel.Never)) return false;
		return true;
	}
}
