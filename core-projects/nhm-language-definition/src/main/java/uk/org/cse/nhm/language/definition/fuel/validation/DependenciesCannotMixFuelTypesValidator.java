package uk.org.cse.nhm.language.definition.fuel.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import uk.org.cse.nhm.language.definition.fuel.extracharges.XExtraCharge;

public class DependenciesCannotMixFuelTypesValidator implements ConstraintValidator<DependenciesCannotMixFuelTypes, XExtraCharge> {

    @Override
    public void initialize(final DependenciesCannotMixFuelTypes constraintAnnotation) {
        // No-op
    }

    @Override
    public boolean isValid(final XExtraCharge value,
            final ConstraintValidatorContext context) {
        for (final XExtraCharge dependency : value.getDependsOn()) {
            if (dependency.getFuel() == null) {
                if (value.getFuel() != null) {
                    return false;
                }

            } else if (!dependency.getFuel().equals(value.getFuel())) {
                return false;
            }
        }

        return true;
    }

}
