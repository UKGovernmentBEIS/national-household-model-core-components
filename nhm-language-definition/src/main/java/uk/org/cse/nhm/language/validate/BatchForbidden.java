package uk.org.cse.nhm.language.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = BatchForbiddenValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
/**
 * Applied to types, this indicates that types may never be present in a batch scenario.
 * @author hinton
 *
 */
public @interface BatchForbidden {
	String message() default "{uk.org.cse.nhm.language.validate.BatchForbidden.nope}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default {};
	
	String element() default "this element";
}
