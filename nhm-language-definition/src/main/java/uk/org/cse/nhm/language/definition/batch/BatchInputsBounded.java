package uk.org.cse.nhm.language.definition.batch;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Constraint(validatedBy = BatchInputsBoundedValidator.class)
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BatchInputsBounded {
	public Class<?>[] value() default {};
	
	String message() default "Inputs for batch element must be bounded (cannot produce infinite elements).";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default {};
}
