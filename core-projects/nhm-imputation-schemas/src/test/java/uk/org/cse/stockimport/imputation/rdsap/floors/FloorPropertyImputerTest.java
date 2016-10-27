package uk.org.cse.stockimport.imputation.rdsap.floors;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.hom.components.fabric.types.FloorConstructionType;
import uk.org.cse.nhm.hom.types.RegionType;
import uk.org.cse.nhm.hom.types.SAPAgeBandValue;
import uk.org.cse.nhm.hom.types.SAPAgeBandValue.Band;
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

		// these are just some test cases picked from the CHM spreadsheet

		Assert.assertEquals(0.56, i.getGroundFloorUValue(
				FloorConstructionType.SuspendedTimber, 250, 0, 26.09, 70.93),
				0.05);
		Assert.assertEquals(0.64, i.getGroundFloorUValue(
				FloorConstructionType.SuspendedTimber, 100, 0, 100, 200), 0.05);
		Assert.assertEquals(0.64, i.getGroundFloorUValue(
				FloorConstructionType.SuspendedTimber, 100, 100, 100, 200),
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
			Assert.assertEquals(1.2, i.getExposedFloorUValue(
					abv, false));
			Assert.assertEquals(0.5, i.getExposedFloorUValue(
					abv, true));
		}

		Assert.assertEquals(0.5, i.getExposedFloorUValue(
				SAPAgeBandValue.Band.H, true));

		Assert.assertEquals(0.5, i.getExposedFloorUValue(
				SAPAgeBandValue.Band.I, true));

		Assert.assertEquals(0.25, i.getExposedFloorUValue(
				SAPAgeBandValue.Band.J, true));

		Assert.assertEquals(0.22, i.getExposedFloorUValue(
				SAPAgeBandValue.Band.K, true));

		Assert.assertEquals(0.51, i.getExposedFloorUValue(
				SAPAgeBandValue.Band.H, false));

		Assert.assertEquals(0.51, i.getExposedFloorUValue(
				SAPAgeBandValue.Band.I, false));

		Assert.assertEquals(0.25, i.getExposedFloorUValue(
				SAPAgeBandValue.Band.J, false));

		Assert.assertEquals(0.22, i.getExposedFloorUValue(
				SAPAgeBandValue.Band.K, false));
	}

	@Test
	public void testFloorConstructionType() {
		for (final SAPAgeBandValue.Band value : SAPAgeBandValue.Band.values()) {
			final FloorConstructionType fct = i.getFloorConstructionType(value);
		
			Assert.assertEquals(
					(value == SAPAgeBandValue.Band.A || value == SAPAgeBandValue.Band.B) ?
							FloorConstructionType.SuspendedTimber : FloorConstructionType.Solid,
							fct);
		}
	}
	
	@Test
	public void testFloorInfiltration() {
		Assert.assertEquals(0.0, i.getFloorInfiltration(SAPAgeBandValue.Band.A, FloorConstructionType.Solid));
		Assert.assertEquals(0.2, i.getFloorInfiltration(SAPAgeBandValue.Band.E, FloorConstructionType.SuspendedTimber));
		Assert.assertEquals(0.1, i.getFloorInfiltration(SAPAgeBandValue.Band.F, FloorConstructionType.SuspendedTimber));
	}
	
	@Test
	public void testInsulationThickness() {
		Assert.assertEquals(100d, i.getFloorInsulationThickness(SAPAgeBandValue.Band.K, RegionType.London, FloorConstructionType.Solid));
		Assert.assertEquals(75d, i.getFloorInsulationThickness(SAPAgeBandValue.Band.J, RegionType.London, FloorConstructionType.Solid));
		Assert.assertEquals(25d, i.getFloorInsulationThickness(SAPAgeBandValue.Band.I, RegionType.London, FloorConstructionType.Solid));
		for (final SAPAgeBandValue.Band value : SAPAgeBandValue.Band.values()) {
			Assert.assertEquals(0d, i.getFloorInsulationThickness(value, RegionType.London, FloorConstructionType.Solid));
			if (value == Band.H) return;
		}
	}
}
