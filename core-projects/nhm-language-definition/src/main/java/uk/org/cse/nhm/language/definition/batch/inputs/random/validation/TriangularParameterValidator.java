package uk.org.cse.nhm.language.definition.batch.inputs.random.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import uk.org.cse.nhm.language.definition.function.num.random.ITriangular;

public class TriangularParameterValidator implements ConstraintValidator<TriangularParameters, ITriangular> {

    @Override
    public void initialize(final TriangularParameters constraintAnnotation) {
        // no-op
    }

    @Override
    public boolean isValid(final ITriangular value, final ConstraintValidatorContext context) {
        return value.getPeak() >= value.getStart()
                && value.getPeak() <= value.getEnd();
    }

}
