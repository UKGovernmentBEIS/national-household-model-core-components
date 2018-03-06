package uk.org.cse.stockimport.imputation.rdsap.floors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.types.FloorConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.RegionType.Country;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;
import uk.org.cse.stockimport.imputation.floors.FloorPropertyImputer;
import uk.org.cse.stockimport.imputation.floors.RdSAPFloorPropertyTables;

public class FloorPropertyImputerTest {
	private FloorPropertyImputer i;

	@Before
	public void create() {
		i = new FloorPropertyImputer(new RdSAPFloorPropertyTables());
	}

	@Test
	public void testSuspendedFloorUValues() {

		// these were originally some test cases picked from the CHM spreadsheet
		// the ground floor u-value calculation changed from SAP 2009 to 2012, so we have updated the results
		// this means that floor insulation now has an effect

		Assert.assertEquals(
				0.63,
				i.getGroundFloorUValue(FloorConstructionType.SuspendedTimberUnsealed, 250, 0, 26.09, 70.93),
				0.05);

		Assert.assertEquals(
				0.76,
				i.getGroundFloorUValue(FloorConstructionType.SuspendedTimberUnsealed, 100, 0, 100, 200),
				0.05);

		Assert.assertEquals(
				0.23,
				i.getGroundFloorUValue(FloorConstructionType.SuspendedTimberUnsealed, 100, 100, 100, 200),
				0.05);
	}

	@Test
	public void testSolidFloorUValues() {
		Assert.assertEquals(0.59, i.getGroundFloorUValue(
				FloorConstructionType.Solid, 250, 0, 26.09, 70.93), 0.05);

		Assert.assertEquals(0.21, i.getGroundFloorUValue(
				FloorConstructionType.Solid, 250, 100, 26.09, 70.93), 0.05);

		Assert.assertEquals(0.03, i.getGroundFloorUValue(
				FloorConstructionType.Solid, 250, 1000, 26.09, 70.93), 0.05);
	}

	@Test
	public void testExposedFloorUValue() {
		for (final SAPAgeBandValue.Band abv : SAPAgeBandValue.Band.values()) {
			if (abv == SAPAgeBandValue.Band.G) break;
			Assert.assertEquals(
					1.2,
					i.getExposedFloorUValue(abv, false),
					0.0);

			Assert.assertEquals(
					0.5,
					i.getExposedFloorUValue(abv, true),
					0.0);
		}

		Assert.assertEquals(
				0.5,
				i.getExposedFloorUValue(SAPAgeBandValue.Band.H, true),
				0.0);

		Assert.assertEquals(
				0.5,
				i.getExposedFloorUValue(SAPAgeBandValue.Band.I, true),
				0.0);

		Assert.assertEquals(
				0.25,
				i.getExposedFloorUValue(SAPAgeBandValue.Band.J, true),
				0.0);

		Assert.assertEquals(
				0.22,
				i.getExposedFloorUValue(SAPAgeBandValue.Band.K, true),
				0.0);

		Assert.assertEquals(
				0.51,
				i.getExposedFloorUValue(SAPAgeBandValue.Band.H, false),
				0.0);

		Assert.assertEquals(
				0.51,
				i.getExposedFloorUValue(SAPAgeBandValue.Band.I, false),
				0.0);

		Assert.assertEquals(
				0.25,
				i.getExposedFloorUValue(SAPAgeBandValue.Band.J, false),
				0.0);

		Assert.assertEquals(
				0.22,
				i.getExposedFloorUValue(SAPAgeBandValue.Band.K, false),
				0.0);
	}

	@Test
	public void testInsulationThickness() {
		Assert.assertEquals(
				100d,
				i.getFloorInsulationThickness(SAPAgeBandValue.Band.K, Country.England, FloorConstructionType.Solid),
				0.0);

		Assert.assertEquals(
				75d,
				i.getFloorInsulationThickness(SAPAgeBandValue.Band.J, Country.England, FloorConstructionType.Solid),
				0.0);

		Assert.assertEquals(
				25d,
				i.getFloorInsulationThickness(SAPAgeBandValue.Band.I, Country.England, FloorConstructionType.Solid),
				0.0);

		for (final SAPAgeBandValue.Band value : SAPAgeBandValue.Band.values()) {
			Assert.assertEquals(
					0d,
					i.getFloorInsulationThickness(value, Country.England, FloorConstructionType.Solid),
					0.0);

			if (value == Band.H) return;
		}
	}
}
