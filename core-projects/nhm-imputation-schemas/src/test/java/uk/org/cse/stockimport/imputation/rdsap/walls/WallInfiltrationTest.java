package uk.org.cse.stockimport.imputation.rdsap.walls;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.stockimport.imputation.walls.WallPropertyImputer;

public class WallInfiltrationTest {
	private WallPropertyImputer i;

	@Before
	public void create() {
		this.i = new WallPropertyImputer();
	}
	
	@Test
	public void testWallInfiltration() {
		for (final WallConstructionType wct : WallConstructionType.values()) {
			switch (wct) {
			case Cavity:
			case Cob:
			case GraniteOrWhinstone:
			case Sandstone:
			case SystemBuild:
			case SolidBrick:
				Assert.assertEquals(0.35, i.getAirChangeRate(wct));
				break;
			case TimberFrame:
			case MetalFrame:
				Assert.assertEquals(0.25, i.getAirChangeRate(wct));
				break;
			default:
				break;
			}
		}
	}
}
