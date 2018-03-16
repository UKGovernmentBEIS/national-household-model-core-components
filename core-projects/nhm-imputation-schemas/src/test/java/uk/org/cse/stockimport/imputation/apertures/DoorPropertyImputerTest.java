package uk.org.cse.stockimport.imputation.apertures;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import uk.org.cse.nhm.energycalculator.api.types.DoorType;
import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;
import uk.org.cse.stockimport.imputation.apertures.doors.DoorPropertyImputer;
import uk.org.cse.stockimport.imputation.apertures.doors.IDoorPropertyImputer;
import uk.org.cse.stockimport.imputation.lookupbuilders.ILookUpTableBuilder;

public class DoorPropertyImputerTest {

	private IDoorPropertyImputer dpi;

	@Before
	public void beforeTest(){
		dpi = new RdSAPDoorPropertyBuilder().buildTables("VOID");
	}

	@Test
	public void testArea() {
		for (final DoorType dt : DoorType.values()) {
			Assert.assertEquals(1.85, dpi.getArea(dt), 0);
		}
	}

	@Test
	public void testUValue() {
		for (final DoorType dt : DoorType.values()) {
			Assert.assertEquals(3.0, dpi.getUValue(SAPAgeBandValue.fromYear(1900, RegionType.SouthWest).getName(), dt), 0);
		}

		for (final DoorType dt : DoorType.values()) {
			Assert.assertEquals(2.0, dpi.getUValue(SAPAgeBandValue.fromYear(2007, RegionType.SouthWest).getName(), dt), 0);
		}
	}

	private static SAPAgeBandValue.Band[] between(final SAPAgeBandValue.Band f, final SAPAgeBandValue.Band t) {
		final Band[] result = new Band[1 + t.ordinal() - f.ordinal()];

		for (final SAPAgeBandValue.Band b : SAPAgeBandValue.Band.values()) {
			if (b.ordinal() >= f.ordinal() && b.ordinal() <= t.ordinal()) {
				result[b.ordinal() - f.ordinal()] = b;
			}
		}
		return result;
	}

	private class RdSAPDoorPropertyBuilder implements ILookUpTableBuilder<IDoorPropertyImputer, String>{

		@Override
		public IDoorPropertyImputer buildTables(final String dataSource) {
			final DoorPropertyImputer doorPropertyImputer = new DoorPropertyImputer();

			doorPropertyImputer.addDoorArea(DoorType.Glazed, 1.85);
			doorPropertyImputer.addDoorArea(DoorType.Solid, 1.85);

			for(final DoorType doorType : DoorType.values()){
				for(final Band ageBandValue : between(SAPAgeBandValue.Band.A, SAPAgeBandValue.Band.J)){
					doorPropertyImputer.addDoorUValue(doorType, ageBandValue, 3.0);
				}
				doorPropertyImputer.addDoorUValue(doorType, SAPAgeBandValue.Band.K, 2.0);
			}

			return doorPropertyImputer;
		}
	}
}
