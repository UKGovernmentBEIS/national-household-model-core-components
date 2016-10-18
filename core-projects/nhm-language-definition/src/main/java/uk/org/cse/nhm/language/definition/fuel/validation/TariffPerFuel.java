package uk.org.cse.nhm.language.definition.fuel.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TariffPerFuelValidator.class)
public @interface TariffPerFuel {
	String message();
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
