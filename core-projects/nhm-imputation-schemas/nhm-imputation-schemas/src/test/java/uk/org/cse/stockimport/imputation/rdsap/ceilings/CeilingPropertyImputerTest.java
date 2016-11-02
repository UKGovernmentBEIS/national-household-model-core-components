package uk.org.cse.stockimport.imputation.rdsap.ceilings;

import junit.framework.Assert;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

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
	public void testKValues() {
		Assert.assertEquals(13.5, i.getPartyCeilingKValue());
		
		Assert.assertEquals(9d, i.getRoofKValue(RoofConstructionType.Flat));
		Assert.assertEquals(9d, i.getRoofKValue(RoofConstructionType.PitchedSlateOrTiles));
		Assert.assertEquals(9d, i.getRoofKValue(RoofConstructionType.Thatched));
	}

	
	@Test
	public void testKnownInsulation() {
		final CeilingPropertyImputer i = new CeilingPropertyImputer(ceilingUValues);
		
		Assert.assertEquals(0.4, i.getRoofUValue(RoofConstructionType.PitchedSlateOrTiles, 100, false));
		Assert.assertEquals(0.5, i.getRoofUValue(RoofConstructionType.PitchedSlateOrTiles, 75, false));
		Assert.assertEquals(2.3, i.getRoofUValue(RoofConstructionType.PitchedSlateOrTiles, 0, false));
		Assert.assertEquals(0.1, i.getRoofUValue(RoofConstructionType.PitchedSlateOrTiles, 900, false));
		
		Assert.assertEquals(0.4, i.getRoofUValue(RoofConstructionType.Thatched, 0, false));
		Assert.assertEquals(0.2, i.getRoofUValue(RoofConstructionType.Thatched, 100, false));
		Assert.assertEquals(0.1, i.getRoofUValue(RoofConstructionType.Thatched, 400, false));
		Assert.assertEquals(0.3, i.getRoofUValue(RoofConstructionType.Thatched, 50, false));
		
		
		Assert.assertEquals(0.4, i.getRoofUValue(RoofConstructionType.PitchedSlateOrTiles, 100, true));
		Assert.assertEquals(0.5, i.getRoofUValue(RoofConstructionType.PitchedSlateOrTiles, 75, true));
		Assert.assertEquals(2.3, i.getRoofUValue(RoofConstructionType.PitchedSlateOrTiles, 0, true));
		Assert.assertEquals(0.1, i.getRoofUValue(RoofConstructionType.PitchedSlateOrTiles, 900, true));
		
		Assert.assertEquals(0.4, i.getRoofUValue(RoofConstructionType.Thatched, 0, true));
		Assert.assertEquals(0.2, i.getRoofUValue(RoofConstructionType.Thatched, 100, true));
		Assert.assertEquals(0.1, i.getRoofUValue(RoofConstructionType.Thatched, 400, true));
		Assert.assertEquals(0.3, i.getRoofUValue(RoofConstructionType.Thatched, 50, true));
	}
	
	
	@Test
	public void testUnknownInsulationWithRoomInRoof() {
		Assert.assertEquals(2.3,
				i.getRoofUValue(SAPAgeBandValue.Band.A, 
						RoofConstructionType.PitchedSlateOrTiles, true));
		
		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.A, 
						RoofConstructionType.Thatched, true));
		
		Assert.assertEquals(2.3,
				i.getRoofUValue(SAPAgeBandValue.Band.B, 
						RoofConstructionType.PitchedSlateOrTiles, true));
		
		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.B, 
						RoofConstructionType.Thatched, true));
		
		Assert.assertEquals(2.3,
				i.getRoofUValue(SAPAgeBandValue.Band.C, 
						RoofConstructionType.PitchedSlateOrTiles, true));
		
		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.C, 
						RoofConstructionType.Thatched, true));
		
		Assert.assertEquals(2.3,
				i.getRoofUValue(SAPAgeBandValue.Band.D, 
						RoofConstructionType.PitchedSlateOrTiles, true));
		
		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.D, 
						RoofConstructionType.Thatched, true));
		
		Assert.assertEquals(1.5,
				i.getRoofUValue(SAPAgeBandValue.Band.E, 
						RoofConstructionType.PitchedSlateOrTiles, true));
		
		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.E, 
						RoofConstructionType.Thatched, true));
		
		Assert.assertEquals(0.8,
				i.getRoofUValue(SAPAgeBandValue.Band.F, 
						RoofConstructionType.PitchedSlateOrTiles, true));
		
		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.F, 
						RoofConstructionType.Thatched, true));
		
		Assert.assertEquals(0.5,
				i.getRoofUValue(SAPAgeBandValue.Band.G, 
						RoofConstructionType.PitchedSlateOrTiles, true));
		
		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.G, 
						RoofConstructionType.Thatched, true));
		
		Assert.assertEquals(0.35,
				i.getRoofUValue(SAPAgeBandValue.Band.H, 
						RoofConstructionType.PitchedSlateOrTiles, true));
		
		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.H, 
						RoofConstructionType.Thatched, true));
		
		Assert.assertEquals(0.35,
				i.getRoofUValue(SAPAgeBandValue.Band.I, 
						RoofConstructionType.PitchedSlateOrTiles, true));
		
		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.I, 
						RoofConstructionType.Thatched, true));
		
		Assert.assertEquals(0.3,
				i.getRoofUValue(SAPAgeBandValue.Band.J, 
						RoofConstructionType.PitchedSlateOrTiles, true));
		
		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.J, 
						RoofConstructionType.Thatched, true));
		
		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.K, 
						RoofConstructionType.PitchedSlateOrTiles, true));
		
		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.K, 
						RoofConstructionType.Thatched, true));
	}
	
	@Test
	public void testUnknownInsulationWithoutRoomInRoof() {
		Assert.assertEquals(2.3,
				i.getRoofUValue(SAPAgeBandValue.Band.A, 
						RoofConstructionType.PitchedSlateOrTiles, false));
		
		Assert.assertEquals(0.35,
				i.getRoofUValue(SAPAgeBandValue.Band.A, 
						RoofConstructionType.Thatched, false));
		
		Assert.assertEquals(2.3,
				i.getRoofUValue(SAPAgeBandValue.Band.A, 
						RoofConstructionType.Flat, false));
		
		Assert.assertEquals(2.3,
				i.getRoofUValue(SAPAgeBandValue.Band.B, 
						RoofConstructionType.PitchedSlateOrTiles, false));
		
		Assert.assertEquals(0.35,
				i.getRoofUValue(SAPAgeBandValue.Band.B, 
						RoofConstructionType.Thatched, false));
		
		Assert.assertEquals(2.3,
				i.getRoofUValue(SAPAgeBandValue.Band.B, 
						RoofConstructionType.Flat, false));
		
		Assert.assertEquals(2.3,
				i.getRoofUValue(SAPAgeBandValue.Band.C, 
						RoofConstructionType.PitchedSlateOrTiles, false));
		
		Assert.assertEquals(0.35,
				i.getRoofUValue(SAPAgeBandValue.Band.C, 
						RoofConstructionType.Thatched, false));
		
		Assert.assertEquals(2.3,
				i.getRoofUValue(SAPAgeBandValue.Band.C, 
						RoofConstructionType.Flat, false));
		
		Assert.assertEquals(2.3,
				i.getRoofUValue(SAPAgeBandValue.Band.D, 
						RoofConstructionType.PitchedSlateOrTiles, false));
		
		Assert.assertEquals(0.35,
				i.getRoofUValue(SAPAgeBandValue.Band.D, 
						RoofConstructionType.Thatched, false));
		
		Assert.assertEquals(2.3,
				i.getRoofUValue(SAPAgeBandValue.Band.D, 
						RoofConstructionType.Flat, false));
		
		Assert.assertEquals(1.5,
				i.getRoofUValue(SAPAgeBandValue.Band.E, 
						RoofConstructionType.PitchedSlateOrTiles, false));
		
		Assert.assertEquals(0.35,
				i.getRoofUValue(SAPAgeBandValue.Band.E, 
						RoofConstructionType.Thatched, false));
		
		Assert.assertEquals(1.5,
				i.getRoofUValue(SAPAgeBandValue.Band.E, 
						RoofConstructionType.Flat, false));
		
		Assert.assertEquals(0.68,
				i.getRoofUValue(SAPAgeBandValue.Band.F, 
						RoofConstructionType.PitchedSlateOrTiles, false));
		
		Assert.assertEquals(0.35,
				i.getRoofUValue(SAPAgeBandValue.Band.F, 
						RoofConstructionType.Thatched, false));
		
		Assert.assertEquals(0.68,
				i.getRoofUValue(SAPAgeBandValue.Band.F, 
						RoofConstructionType.Flat, false));
		
		Assert.assertEquals(0.4,
				i.getRoofUValue(SAPAgeBandValue.Band.G, 
						RoofConstructionType.PitchedSlateOrTiles, false));
		
		Assert.assertEquals(0.35,
				i.getRoofUValue(SAPAgeBandValue.Band.G, 
						RoofConstructionType.Thatched, false));
		
		Assert.assertEquals(0.4,
				i.getRoofUValue(SAPAgeBandValue.Band.G, 
						RoofConstructionType.Flat, false));
		
		Assert.assertEquals(0.29,
				i.getRoofUValue(SAPAgeBandValue.Band.H, 
						RoofConstructionType.PitchedSlateOrTiles, false));
		
		Assert.assertEquals(0.35,
				i.getRoofUValue(SAPAgeBandValue.Band.H, 
						RoofConstructionType.Thatched, false));
		
		Assert.assertEquals(0.35,
				i.getRoofUValue(SAPAgeBandValue.Band.H, 
						RoofConstructionType.Flat, false));
		
		Assert.assertEquals(0.26,
				i.getRoofUValue(SAPAgeBandValue.Band.I, 
						RoofConstructionType.PitchedSlateOrTiles, false));
		
		Assert.assertEquals(0.35,
				i.getRoofUValue(SAPAgeBandValue.Band.I, 
						RoofConstructionType.Thatched, false));
		
		Assert.assertEquals(0.35,
				i.getRoofUValue(SAPAgeBandValue.Band.I, 
						RoofConstructionType.Flat, false));
		
		Assert.assertEquals(0.16,
				i.getRoofUValue(SAPAgeBandValue.Band.J, 
						RoofConstructionType.PitchedSlateOrTiles, false));
		
		Assert.assertEquals(0.30,
				i.getRoofUValue(SAPAgeBandValue.Band.J, 
						RoofConstructionType.Thatched, false));
		
		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.J, 
						RoofConstructionType.Flat, false));
		
		Assert.assertEquals(0.16,
				i.getRoofUValue(SAPAgeBandValue.Band.K, 
						RoofConstructionType.PitchedSlateOrTiles, false));
		
		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.K, 
						RoofConstructionType.Thatched, false));
		
		Assert.assertEquals(0.25,
				i.getRoofUValue(SAPAgeBandValue.Band.K, 
						RoofConstructionType.Flat, false));
	}
	
	private class RdSAPCeilingLookUpBuilder implements ILookUpTableBuilder<ICeilingPropertyImputer, String>{

		@Override
		public ICeilingPropertyImputer buildTables(final String dataSource) {
			return new CeilingPropertyImputer(new RdSAPCeilingUValues());
		}
	}
}
