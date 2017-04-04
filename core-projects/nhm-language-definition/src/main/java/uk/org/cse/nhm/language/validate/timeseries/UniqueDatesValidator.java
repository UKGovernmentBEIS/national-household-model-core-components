package uk.org.cse.nhm.language.validate.timeseries;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.joda.time.DateTime;

import uk.org.cse.nhm.language.definition.function.num.XTimeSeries;
import uk.org.cse.nhm.language.definition.function.num.XTimeSeries.XOn;

public class UniqueDatesValidator implements ConstraintValidator<UniqueDates, XTimeSeries>{

	@Override
	public void initialize(UniqueDates constraintAnnotation) {
		// NO-OP
	}

	@Override
	public boolean isValid(XTimeSeries timeSeries, ConstraintValidatorContext context) {
		Set<DateTime> onDates = new HashSet<DateTime>();
		for(XOn on : timeSeries.getOn()) {
			if(onDates.contains(on.getDate())) {
				return false;
			}
			onDates.add(on.getDate());
		}
		return true;
	}
}
