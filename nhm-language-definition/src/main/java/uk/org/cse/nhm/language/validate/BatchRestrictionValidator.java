package uk.org.cse.nhm.language.validate;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import uk.org.cse.nhm.language.sexp.ScenarioParserFactory;

public class BatchRestrictionValidator implements ConstraintValidator<BatchRestricted, Object> {
	private final boolean enabled;
	private Class<?>[] allowedTypes;

	@Inject
	public BatchRestrictionValidator(final @Named(ScenarioParserFactory.IS_BATCH_MODE) boolean batch) {
		this.enabled = batch;
	}
	
	@Override
	public void initialize(final BatchRestricted value) {
		this.allowedTypes = value.value();
	}

	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext arg) {
		if (enabled) {
				for (final Class<?> t : allowedTypes) {
					if (t.isInstance(value)) {
						return true;
					}
				}
			
			arg.buildConstraintViolationWithTemplate("{uk.org.cse.nhm.language.validate.BatchRestriction.message}").addConstraintViolation();
			
			return false;
		} else {
			return true;
		}
	}

}
