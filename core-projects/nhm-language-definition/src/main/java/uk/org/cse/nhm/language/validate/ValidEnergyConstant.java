package uk.org.cse.nhm.language.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = EnergyConstantValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnergyConstant {

    String message() default "{uk.org.cse.nhm.language.validate.ValidEnergyConstant.nope}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
