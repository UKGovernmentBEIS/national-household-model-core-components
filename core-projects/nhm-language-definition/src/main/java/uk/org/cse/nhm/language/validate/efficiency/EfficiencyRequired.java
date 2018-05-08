package uk.org.cse.nhm.language.validate.efficiency;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = {BoilerEfficiencyRequiredValidator.class, RoomHeaterEfficiencyRequiredValidator.class})
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
/**
 * Apply to a type which is fuel-consuming device. Guarantees that it has an
 * efficiency unless it is using Electricity as a fuel.
 */
public @interface EfficiencyRequired {

    String message() default "{uk.org.cse.nhm.language.validate.efficiency.EfficiencyRequired.nope}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String element() default "this element";
}
