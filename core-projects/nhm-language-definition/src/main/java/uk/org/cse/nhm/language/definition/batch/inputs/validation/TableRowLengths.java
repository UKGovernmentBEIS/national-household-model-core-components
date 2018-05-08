package uk.org.cse.nhm.language.definition.batch.inputs.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TableLengthsValidator.class)
public @interface TableRowLengths {

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
