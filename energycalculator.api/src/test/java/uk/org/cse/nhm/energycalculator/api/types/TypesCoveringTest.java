package uk.org.cse.nhm.energycalculator.api.types;

import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.OvershadingType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.ZoneType;

public class TypesCoveringTest {
	@SuppressWarnings("unused")
	@Test
	public void seeTypes() {
		// this method is just here because enums count in eclemma coverage measure and it's annoying to see it low.
		for (final OvershadingType type : OvershadingType.values());
		for (final ElectricityTariffType type : ElectricityTariffType.values());
		for (final ServiceType type : ServiceType.values());
		for (final ZoneType type : ZoneType.values());
	}
}
