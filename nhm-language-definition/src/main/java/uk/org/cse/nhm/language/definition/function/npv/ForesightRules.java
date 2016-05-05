package uk.org.cse.nhm.language.definition.function.npv;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ForesightRulesValidator.class)
public @interface ForesightRules {
	String message();
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
