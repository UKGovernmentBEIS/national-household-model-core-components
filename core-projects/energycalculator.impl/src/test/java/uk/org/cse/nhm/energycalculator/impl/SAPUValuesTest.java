package uk.org.cse.nhm.energycalculator.impl;

import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.RegionType.Country;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;

public class SAPUValuesTest {

	@Test
	public void wallFailure() {
		SAPUValues.Walls.get(
				Country.England,
				WallConstructionType.Cavity,
				0,
				true,
				Band.D,
				0
			);
	}

}
