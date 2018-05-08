package uk.org.cse.nhm.language.validate.efficiency;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import uk.org.cse.nhm.language.definition.action.measure.heating.XRoomHeaterMeasure;
import uk.org.cse.nhm.language.definition.enums.XFuelType;

public class RoomHeaterEfficiencyRequiredValidator implements ConstraintValidator<EfficiencyRequired, XRoomHeaterMeasure> {

    @Override
    public void initialize(EfficiencyRequired constraintAnnotation) {
        // noop
    }

    @Override
    public boolean isValid(XRoomHeaterMeasure value, ConstraintValidatorContext context) {
        if (value.getFuel() == XFuelType.Electricity) {
            return true;
        } else {
            return value.getEfficiency() != null;
        }
    }
}
