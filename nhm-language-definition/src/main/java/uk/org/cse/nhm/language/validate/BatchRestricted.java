package uk.org.cse.nhm.language.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = BatchRestrictionValidator.class)
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * Applied to enums, this takes a list of enum names written as strings.
 * Only enum names that are specified are allowed when in batch mode.
 * 
 * @author hinton
 *
 */
public @interface BatchRestricted {
	public Class<?>[] value() default {};
	
	String message() default "{uk.org.cse.nhm.language.validate.BatchRestricted.nope}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default {};
	
	String attribute();
}
