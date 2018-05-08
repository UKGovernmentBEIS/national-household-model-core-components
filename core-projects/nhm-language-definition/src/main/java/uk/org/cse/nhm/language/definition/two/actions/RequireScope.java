package uk.org.cse.nhm.language.definition.two.actions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import uk.org.cse.nhm.language.definition.sequence.XScope;

/**
 * A method with this attribute must contain a variable declaration with the
 * correct scope.
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {RequiresScopeValidator.class})
public @interface RequireScope {

    XScope[] value() default {};

    String message() default "{javax.validation.constraints.NotNull.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
