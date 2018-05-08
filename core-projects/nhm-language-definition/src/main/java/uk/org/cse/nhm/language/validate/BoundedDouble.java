package uk.org.cse.nhm.language.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = BoundedDoubleValidator.class)
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * Verify that a double lies in a range; this will pass iff the validated value
 * x satisfies:
 *
 * {@link #lower()} <= x <= {@link #upper()} @author hinton
 *
 *
 */
public @interface BoundedDouble {

    /**
     * The lower bound; the validated value should be greater than or equal to
     * this value
     *
     * @return
     */
    public double lower() default Double.NEGATIVE_INFINITY;

    /**
     * The upper bound; value should be less than or equal to this value.
     *
     * @return
     */
    public double upper() default Double.POSITIVE_INFINITY;

    public String property() default "value";

    String message() default "{uk.org.cse.nhm.language.validate.BoundedDouble.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
