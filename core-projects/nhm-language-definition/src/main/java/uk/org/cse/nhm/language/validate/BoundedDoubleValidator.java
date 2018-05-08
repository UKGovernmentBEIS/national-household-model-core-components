package uk.org.cse.nhm.language.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BoundedDoubleValidator implements ConstraintValidator<BoundedDouble, Double> {

    private double min;
    private double max;

    @Override
    public void initialize(BoundedDouble constraintAnnotation) {
        setBounds(constraintAnnotation.lower(), constraintAnnotation.upper());
    }

    protected void setBounds(double lower, double upper) {
        this.min = lower;
        this.max = upper;
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value == null) {
            context.buildConstraintViolationWithTemplate("{uk.org.cse.nhm.language.validate.BoundedDouble.message}").addConstraintViolation();
            return false;
        }

        if (!(value >= min && value <= max)) {
            context.buildConstraintViolationWithTemplate("{uk.org.cse.nhm.language.validate.BoundedDouble.message}");
            return false;
        }

        return true;
    }
}
