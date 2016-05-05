package uk.org.cse.nhm.language.definition.fuel.validation;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.language.definition.fuel.XTariffBase;

public class TariffPerFuelValidator implements ConstraintValidator<TariffPerFuel, List<XTariffBase>>{

	@Override
	public void initialize(final TariffPerFuel constraintAnnotation) {
		// no-op
	}

	@Override
	public boolean isValid(final List<XTariffBase> value, final ConstraintValidatorContext context) {
		final Set<XFuelType> seenFuelTypes = new LinkedHashSet<>();
		
		for(final XTariffBase tariff : value) {
			for(final XFuelType tariffFuel : tariff.getFuelTypes()) {
				if(seenFuelTypes.contains(tariffFuel)) {
					return false;
				} else {
					seenFuelTypes.add(tariffFuel);
				}
			}
		}
		
		return true;
	}
}
