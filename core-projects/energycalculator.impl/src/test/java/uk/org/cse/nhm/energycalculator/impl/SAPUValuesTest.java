package uk.org.cse.nhm.energycalculator.impl;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.types.FloorConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.RegionType.Country;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.mode.SAPTables;

public class SAPUValuesTest {

	@Test
	public void wallFailure() {
		SAPTables.Walls.uValue(
				Country.England,
				WallConstructionType.Cavity,
				0,
				true,
				Band.D,
				0
			);
	}

	@Test
	public void upperExternalFloors() {
		assertUpperExternalFloor(1.20, SAPAgeBandValue.Band.A, 0);
		assertUpperExternalFloor(0.5, SAPAgeBandValue.Band.A, 50);
		assertUpperExternalFloor(0.3, SAPAgeBandValue.Band.A, 100);
		assertUpperExternalFloor(0.22, SAPAgeBandValue.Band.A, 150);

		assertUpperExternalFloor(0.51, SAPAgeBandValue.Band.H, 0);
		assertUpperExternalFloor(0.5, SAPAgeBandValue.Band.H, 50);
		assertUpperExternalFloor(0.3, SAPAgeBandValue.Band.H, 100);
		assertUpperExternalFloor(0.22, SAPAgeBandValue.Band.H, 150);

		assertUpperExternalFloor(0.25, SAPAgeBandValue.Band.J, 0);
		assertUpperExternalFloor(0.25, SAPAgeBandValue.Band.J, 50);
		assertUpperExternalFloor(0.25, SAPAgeBandValue.Band.J, 100);
		assertUpperExternalFloor(0.22, SAPAgeBandValue.Band.J, 150);

		assertUpperExternalFloor(0.22, SAPAgeBandValue.Band.K, 0);
		assertUpperExternalFloor(0.22, SAPAgeBandValue.Band.K, 50);
		assertUpperExternalFloor(0.22, SAPAgeBandValue.Band.K, 100);
		assertUpperExternalFloor(0.22, SAPAgeBandValue.Band.K, 150);
	}

	private void assertUpperExternalFloor(final double expected, final SAPAgeBandValue.Band ageBand, final double insulationThickness) {
		Assert.assertEquals("Non-ground floor u-value didn't match.",
				expected,
				SAPTables.Floors.get(
						false,
						false,
						1,
						0,
						0,
						FloorConstructionType.SuspendedTimberSealed,
						insulationThickness,
						ageBand,
						Country.England),
				0.0);
	}

}
