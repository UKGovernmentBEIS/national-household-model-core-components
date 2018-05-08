package uk.org.cse.nhm.language.definition.batch.inputs.combinators.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = DelegatePlaceholderConsistencyValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
/**
 * Applied to an @{link XCombinator}, ensures that all its delegate inputs
 * produce the same placeholders.
 */
public @interface DelegatePlaceholdersConsistent {

    String message() default "{uk.org.cse.nhm.language.definition.batch.inputs.combinators.validation.DelegatePlaceholdersConsistent.nope}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String element() default "this element";
}
