package uk.org.cse.nhm.language.validate.timeseries;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = UniqueDatesValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueDates {

    String message() default "{uk.org.cse.nhm.language.validate.timeseries.UniqueDates.nope}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
