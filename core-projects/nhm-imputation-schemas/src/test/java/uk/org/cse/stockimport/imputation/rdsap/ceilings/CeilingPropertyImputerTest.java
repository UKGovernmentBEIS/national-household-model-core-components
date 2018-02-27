package uk.org.cse.stockimport.imputation.rdsap.ceilings;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import org.junit.Assert;
import uk.org.cse.nhm.energycalculator.api.types.RoofConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue;
import uk.org.cse.stockimport.imputation.ceilings.CeilingPropertyImputer;
import uk.org.cse.stockimport.imputation.ceilings.ICeilingPropertyImputer;
import uk.org.cse.stockimport.imputation.ceilings.ICeilingUValueTables;
import uk.org.cse.stockimport.imputation.ceilings.RdSAPCeilingUValues;
import uk.org.cse.stockimport.imputation.lookupbuilders.ILookUpTableBuilder;

public class CeilingPropertyImputerTest {

	final ICeilingUValueTables ceilingUValues = new RdSAPCeilingUValues();

	ICeilingPropertyImputer i;

	@Before
	public void initialiseTests(){
		i = new RdSAPCeilingLookUpBuilder().buildTables(StringUtils.EMPTY);
	}

	@Test
	public void testKnownInsulation() {
		final CeilingPropertyImputer i = new CeilingPropertyImputer(ceilingUValues);

		Assert.assertEquals(0.4, i.getRoofUValue(RoofConstructionType.PitchedSlateOrTiles, 100, false), 0);
		Assert.assertEquals(0.5, i.getRoofUValue(RoofConstructionType.PitchedSlateOrTiles, 75, false), 0);
		Assert.assertEquals(2.3, i.getRoofUValue(RoofConstructionType.PitchedSlateOrTiles, 0, false), 0);
		Assert.assertEquals(0.1, i.getRoofUValue(RoofConstructionType.PitchedSlateOrTiles, 900, false), 0);

		Assert.assertEquals(0.4, i.getRoofUValue(RoofConstructionType.Thatched, 0, false), 0);
		Assert.assertEquals(0.2, i.getRoofUValue(RoofConstructionType.Thatched, 100, false), 0);
		Assert.assertEquals(0.1, i.getRoofUValue(RoofConstructionType.Thatched, 400, false), 0);
		Assert.assertEquals(0.3, i.getRoofUValue(RoofConstructionType.Thatched, 50, false), 0);


		Assert.assertEquals(0.4, i.getRoofUValue(RoofConstructionType.PitchedSlateOrTiles, 100, true), 0);
		Assert.assertEquals(0.5, i.getRoofUValue(RoofConstructionType.PitchedSlateOrTiles, 75, true), 0);
		Assert.assertEquals(2.3, i.getRoofUValue(RoofConstructionType.PitchedSlateOrTiles, 0, true), 0);
		Assert.assertEquals(0.1, i.getRoofUValue(RoofConstructionType.PitchedSlateOrTiles, 900, true), 0);

		Assert.assertEquals(0.4, i.getRoofUValue(RoofConstructionType.Thatched, 0, true), 0);
		Assert.assertEquals(0.2, i.getRoofUValue(RoofConstructionType.Thatched, 100, true), 0);
		Assert.assertEquals(0.1, i.getRoofUValue(RoofConstructionType.Thatched, 400, true), 0);
		Assert.assertEquals(0.3, i.getRoofUValue(RoofConstructionType.Thatched, 50, true), 0);
	}


	@Test
	public void testUnknownInsulationWithRoomInRoof() {
		Assert.assertEquals(2.3,
				i.getRoofUValue(SAPAgeBandValue.Band.A,
						RoofConstructionType.PitchedSlateOrTiles, true), 0);

		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.A,
						RoofConstructionType.Thatched, true), 0);

		Assert.assertEquals(2.3,
				i.getRoofUValue(SAPAgeBandValue.Band.B,
						RoofConstructionType.PitchedSlateOrTiles, true), 0);

		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.B,
						RoofConstructionType.Thatched, true), 0);

		Assert.assertEquals(2.3,
				i.getRoofUValue(SAPAgeBandValue.Band.C,
						RoofConstructionType.PitchedSlateOrTiles, true), 0);

		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.C,
						RoofConstructionType.Thatched, true), 0);

		Assert.assertEquals(2.3,
				i.getRoofUValue(SAPAgeBandValue.Band.D,
						RoofConstructionType.PitchedSlateOrTiles, true), 0);

		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.D,
						RoofConstructionType.Thatched, true), 0);

		Assert.assertEquals(1.5,
				i.getRoofUValue(SAPAgeBandValue.Band.E,
						RoofConstructionType.PitchedSlateOrTiles, true), 0);

		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.E,
						RoofConstructionType.Thatched, true), 0);

		Assert.assertEquals(0.8,
				i.getRoofUValue(SAPAgeBandValue.Band.F,
						RoofConstructionType.PitchedSlateOrTiles, true), 0);

		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.F,
						RoofConstructionType.Thatched, true), 0);

		Assert.assertEquals(0.5,
				i.getRoofUValue(SAPAgeBandValue.Band.G,
						RoofConstructionType.PitchedSlateOrTiles, true), 0);

		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.G,
						RoofConstructionType.Thatched, true), 0);

		Assert.assertEquals(0.35,
				i.getRoofUValue(SAPAgeBandValue.Band.H,
						RoofConstructionType.PitchedSlateOrTiles, true), 0);

		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.H,
						RoofConstructionType.Thatched, true), 0);

		Assert.assertEquals(0.35,
				i.getRoofUValue(SAPAgeBandValue.Band.I,
						RoofConstructionType.PitchedSlateOrTiles, true), 0);

		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.I,
						RoofConstructionType.Thatched, true), 0);

		Assert.assertEquals(0.3,
				i.getRoofUValue(SAPAgeBandValue.Band.J,
						RoofConstructionType.PitchedSlateOrTiles, true), 0);

		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.J,
						RoofConstructionType.Thatched, true), 0);

		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.K,
						RoofConstructionType.PitchedSlateOrTiles, true), 0);

		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.K,
						RoofConstructionType.Thatched, true), 0);
	}

	@Test
	public void testUnknownInsulationWithoutRoomInRoof() {
		Assert.assertEquals(2.3,
				i.getRoofUValue(SAPAgeBandValue.Band.A,
						RoofConstructionType.PitchedSlateOrTiles, false), 0);

		Assert.assertEquals(0.35,
				i.getRoofUValue(SAPAgeBandValue.Band.A,
						RoofConstructionType.Thatched, false), 0);

		Assert.assertEquals(2.3,
				i.getRoofUValue(SAPAgeBandValue.Band.A,
						RoofConstructionType.Flat, false), 0);

		Assert.assertEquals(2.3,
				i.getRoofUValue(SAPAgeBandValue.Band.B,
						RoofConstructionType.PitchedSlateOrTiles, false), 0);

		Assert.assertEquals(0.35,
				i.getRoofUValue(SAPAgeBandValue.Band.B,
						RoofConstructionType.Thatched, false), 0);

		Assert.assertEquals(2.3,
				i.getRoofUValue(SAPAgeBandValue.Band.B,
						RoofConstructionType.Flat, false), 0);

		Assert.assertEquals(2.3,
				i.getRoofUValue(SAPAgeBandValue.Band.C,
						RoofConstructionType.PitchedSlateOrTiles, false), 0);

		Assert.assertEquals(0.35,
				i.getRoofUValue(SAPAgeBandValue.Band.C,
						RoofConstructionType.Thatched, false), 0);

		Assert.assertEquals(2.3,
				i.getRoofUValue(SAPAgeBandValue.Band.C,
						RoofConstructionType.Flat, false), 0);

		Assert.assertEquals(2.3,
				i.getRoofUValue(SAPAgeBandValue.Band.D,
						RoofConstructionType.PitchedSlateOrTiles, false), 0);

		Assert.assertEquals(0.35,
				i.getRoofUValue(SAPAgeBandValue.Band.D,
						RoofConstructionType.Thatched, false), 0);

		Assert.assertEquals(2.3,
				i.getRoofUValue(SAPAgeBandValue.Band.D,
						RoofConstructionType.Flat, false), 0);

		Assert.assertEquals(1.5,
				i.getRoofUValue(SAPAgeBandValue.Band.E,
						RoofConstructionType.PitchedSlateOrTiles, false), 0);

		Assert.assertEquals(0.35,
				i.getRoofUValue(SAPAgeBandValue.Band.E,
						RoofConstructionType.Thatched, false), 0);

		Assert.assertEquals(1.5,
				i.getRoofUValue(SAPAgeBandValue.Band.E,
						RoofConstructionType.Flat, false), 0);

		Assert.assertEquals(0.68,
				i.getRoofUValue(SAPAgeBandValue.Band.F,
						RoofConstructionType.PitchedSlateOrTiles, false), 0);

		Assert.assertEquals(0.35,
				i.getRoofUValue(SAPAgeBandValue.Band.F,
						RoofConstructionType.Thatched, false), 0);

		Assert.assertEquals(0.68,
				i.getRoofUValue(SAPAgeBandValue.Band.F,
						RoofConstructionType.Flat, false), 0);

		Assert.assertEquals(0.4,
				i.getRoofUValue(SAPAgeBandValue.Band.G,
						RoofConstructionType.PitchedSlateOrTiles, false), 0);

		Assert.assertEquals(0.35,
				i.getRoofUValue(SAPAgeBandValue.Band.G,
						RoofConstructionType.Thatched, false), 0);

		Assert.assertEquals(0.4,
				i.getRoofUValue(SAPAgeBandValue.Band.G,
						RoofConstructionType.Flat, false), 0);

		Assert.assertEquals(0.29,
				i.getRoofUValue(SAPAgeBandValue.Band.H,
						RoofConstructionType.PitchedSlateOrTiles, false), 0);

		Assert.assertEquals(0.35,
				i.getRoofUValue(SAPAgeBandValue.Band.H,
						RoofConstructionType.Thatched, false), 0);

		Assert.assertEquals(0.35,
				i.getRoofUValue(SAPAgeBandValue.Band.H,
						RoofConstructionType.Flat, false), 0);

		Assert.assertEquals(0.26,
				i.getRoofUValue(SAPAgeBandValue.Band.I,
						RoofConstructionType.PitchedSlateOrTiles, false), 0);

		Assert.assertEquals(0.35,
				i.getRoofUValue(SAPAgeBandValue.Band.I,
						RoofConstructionType.Thatched, false), 0);

		Assert.assertEquals(0.35,
				i.getRoofUValue(SAPAgeBandValue.Band.I,
						RoofConstructionType.Flat, false), 0);

		Assert.assertEquals(0.16,
				i.getRoofUValue(SAPAgeBandValue.Band.J,
						RoofConstructionType.PitchedSlateOrTiles, false), 0);

		Assert.assertEquals(0.30,
				i.getRoofUValue(SAPAgeBandValue.Band.J,
						RoofConstructionType.Thatched, false), 0);

		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.J,
						RoofConstructionType.Flat, false), 0);

		Assert.assertEquals(0.16,
				i.getRoofUValue(SAPAgeBandValue.Band.K,
						RoofConstructionType.PitchedSlateOrTiles, false), 0);

		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.K,
						RoofConstructionType.Thatched, false), 0);

		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.K,
						RoofConstructionType.Flat, false), 0);
	}

	private class RdSAPCeilingLookUpBuilder implements ILookUpTableBuilder<ICeilingPropertyImputer, String>{

		@Override
		public ICeilingPropertyImputer buildTables(final String dataSource) {
			return new CeilingPropertyImputer(new RdSAPCeilingUValues());
		}
	}
}
