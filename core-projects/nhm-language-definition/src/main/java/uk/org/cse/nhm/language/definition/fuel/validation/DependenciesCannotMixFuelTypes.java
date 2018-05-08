package uk.org.cse.nhm.language.definition.fuel.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DependenciesCannotMixFuelTypesValidator.class)
public @interface DependenciesCannotMixFuelTypes {

    String message() default "The dependencies of an extra-charge must all have the same fuel type as the charge itself (or must all be empty if it is empty).";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
