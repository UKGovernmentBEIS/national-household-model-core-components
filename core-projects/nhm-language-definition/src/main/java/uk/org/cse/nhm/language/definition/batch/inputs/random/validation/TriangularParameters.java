package uk.org.cse.nhm.language.definition.batch.inputs.random.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TriangularParameterValidator.class)
public @interface TriangularParameters {
	String message();
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
