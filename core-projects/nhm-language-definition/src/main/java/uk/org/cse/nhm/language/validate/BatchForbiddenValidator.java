package uk.org.cse.nhm.language.validate;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import uk.org.cse.nhm.language.sexp.ScenarioParserFactory;

public class BatchForbiddenValidator implements ConstraintValidator<BatchForbidden, Object> {
	private final boolean enabled;

	@Inject
	public BatchForbiddenValidator(final @Named(ScenarioParserFactory.IS_BATCH_MODE) boolean batch) {
		this.enabled = batch;
	}
	
	@Override
	public void initialize(final BatchForbidden value) {
		
	}

	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext arg) {
		if (enabled) {
			arg.buildConstraintViolationWithTemplate("{uk.org.cse.nhm.language.validate.BatchForbidden.message}").addConstraintViolation();
			return false;
		} else {
			return true;
		}
	}

}
