package uk.org.cse.stockimport.imputation.rdsap.walls;
import static uk.org.cse.nhm.energycalculator.api.types.RegionType.Country.England;
import static uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band.A;
import static uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band.B;
import static uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band.C;
import static uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band.D;
import static uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band.E;
import static uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band.F;
import static uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band.G;
import static uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band.H;
import static uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band.I;
import static uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band.J;
import static uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band.K;
import static uk.org.cse.nhm.energycalculator.api.types.WallConstructionType.Cavity;
import static uk.org.cse.nhm.energycalculator.api.types.WallConstructionType.Cob;
import static uk.org.cse.nhm.energycalculator.api.types.WallConstructionType.GraniteOrWhinstone;
import static uk.org.cse.nhm.energycalculator.api.types.WallConstructionType.MetalFrame;
import static uk.org.cse.nhm.energycalculator.api.types.WallConstructionType.Sandstone;
import static uk.org.cse.nhm.energycalculator.api.types.WallConstructionType.SolidBrick;
import static uk.org.cse.nhm.energycalculator.api.types.WallConstructionType.SystemBuild;
import static uk.org.cse.nhm.energycalculator.api.types.WallConstructionType.TimberFrame;
import static uk.org.cse.nhm.energycalculator.api.types.WallInsulationType.External;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import junit.framework.Assert;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;
import uk.org.cse.stockimport.imputation.walls.WallPropertyImputer;

/**
 * Test wall thicknesses
 * @author hinton
 *
 */
public class WallThicknessImputationTest {
	private WallPropertyImputer i;

	@Before
	public void create() {
		this.i = new WallPropertyImputer();
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

	@Test
	public void testWallThickness() {
		final Set<WallInsulationType> none = ImmutableSet.of();
		// granite
		for (final WallConstructionType wc : new WallConstructionType[] {GraniteOrWhinstone, Sandstone}) {
			for (final SAPAgeBandValue.Band band : between(A, D))
				Assert.assertEquals("Band " + band, 500d, i.getWallThickness(band, England, wc, none));
			Assert.assertEquals(450d, i.getWallThickness(E, England, wc, none));
			for (final SAPAgeBandValue.Band band : between(F, H))
				Assert.assertEquals("Band " + band, 420d, i.getWallThickness(band, England, wc, none));
			for (final SAPAgeBandValue.Band band : between(I, K))
				Assert.assertEquals("Band " + band, 450d, i.getWallThickness(band, England, wc, none));
		}

		// solid brick

		for (final Band band : between(A, D))
			Assert.assertEquals("Band " + band, 220d, i.getWallThickness(band, England, SolidBrick, none));

		Assert.assertEquals(240d, i.getWallThickness(E, England, SolidBrick, none));
		Assert.assertEquals(250d, i.getWallThickness(F, England, SolidBrick, none));
		Assert.assertEquals(270d, i.getWallThickness(G, England, SolidBrick, none));
		Assert.assertEquals(270d, i.getWallThickness(H, England, SolidBrick, none));
		Assert.assertEquals(300d, i.getWallThickness(I, England, SolidBrick, none));
		Assert.assertEquals(300d, i.getWallThickness(J, England, SolidBrick, none));
		Assert.assertEquals(300d, i.getWallThickness(K, England, SolidBrick, none));

		Assert.assertEquals(1000*0.54, i.getWallThickness(A, England, Cob, none));
		Assert.assertEquals(1000*0.54, i.getWallThickness(B, England, Cob, none));
		Assert.assertEquals(1000*0.54, i.getWallThickness(C, England, Cob, none));
		Assert.assertEquals(1000*0.54, i.getWallThickness(D, England, Cob, none));
		Assert.assertEquals(1000*0.54, i.getWallThickness(E, England, Cob, none));
		Assert.assertEquals(1000*0.54, i.getWallThickness(F, England, Cob, none));
		Assert.assertEquals(1000*0.56, i.getWallThickness(G, England, Cob, none));
		Assert.assertEquals(1000*0.56, i.getWallThickness(H, England, Cob, none));
		Assert.assertEquals(1000*0.59, i.getWallThickness(I, England, Cob, none));
		Assert.assertEquals(1000*0.59, i.getWallThickness(J, England, Cob, none));
		Assert.assertEquals(1000*0.59, i.getWallThickness(K, England, Cob, none));

		Assert.assertEquals(1000*0.25, i.getWallThickness(A, England, Cavity, none));
		Assert.assertEquals(1000*0.25, i.getWallThickness(B, England, Cavity, none));
		Assert.assertEquals(1000*0.25, i.getWallThickness(C, England, Cavity, none));
		Assert.assertEquals(1000*0.25, i.getWallThickness(D, England, Cavity, none));
		Assert.assertEquals(1000*0.25, i.getWallThickness(E, England, Cavity, none));
		Assert.assertEquals(1000*0.26, i.getWallThickness(F, England, Cavity, none));
		Assert.assertEquals(1000*0.27, i.getWallThickness(G, England, Cavity, none));
		Assert.assertEquals(1000*0.27, i.getWallThickness(H, England, Cavity, none));
		Assert.assertEquals(1000*0.30, i.getWallThickness(I, England, Cavity, none));
		Assert.assertEquals(1000*0.30, i.getWallThickness(J, England, Cavity, none));
		Assert.assertEquals(1000*0.30, i.getWallThickness(K, England, Cavity, none));

		Assert.assertEquals(1000*0.15, i.getWallThickness(A, England, TimberFrame, none));
		Assert.assertEquals(1000*0.15, i.getWallThickness(B, England, TimberFrame, none));
		Assert.assertEquals(1000*0.15, i.getWallThickness(C, England, TimberFrame, none));
		Assert.assertEquals(1000*0.25, i.getWallThickness(D, England, TimberFrame, none));
		Assert.assertEquals(1000*0.27, i.getWallThickness(E, England, TimberFrame, none));
		Assert.assertEquals(1000*0.27, i.getWallThickness(F, England, TimberFrame, none));
		Assert.assertEquals(1000*0.27, i.getWallThickness(G, England, TimberFrame, none));
		Assert.assertEquals(1000*0.27, i.getWallThickness(H, England, TimberFrame, none));
		Assert.assertEquals(1000*0.30, i.getWallThickness(I, England, TimberFrame, none));
		Assert.assertEquals(1000*0.30, i.getWallThickness(J, England, TimberFrame, none));
		Assert.assertEquals(1000*0.30, i.getWallThickness(K, England, TimberFrame, none));

		Assert.assertEquals(1000*0.25, i.getWallThickness(A, England, SystemBuild, none));
		Assert.assertEquals(1000*0.25, i.getWallThickness(B, England, SystemBuild, none));
		Assert.assertEquals(1000*0.25, i.getWallThickness(C, England, SystemBuild, none));
		Assert.assertEquals(1000*0.25, i.getWallThickness(D, England, SystemBuild, none));
		Assert.assertEquals(1000*0.25, i.getWallThickness(E, England, SystemBuild, none));
		Assert.assertEquals(1000*0.30, i.getWallThickness(F, England, SystemBuild, none));
		Assert.assertEquals(1000*0.30, i.getWallThickness(G, England, SystemBuild, none));
		Assert.assertEquals(1000*0.30, i.getWallThickness(H, England, SystemBuild, none));
		Assert.assertEquals(1000*0.30, i.getWallThickness(I, England, SystemBuild, none));
		Assert.assertEquals(1000*0.30, i.getWallThickness(J, England, SystemBuild, none));
		Assert.assertEquals(1000*0.30, i.getWallThickness(K, England, SystemBuild, none));

		Assert.assertEquals(1000*0.10, i.getWallThickness(A, England, MetalFrame, none));
		Assert.assertEquals(1000*0.10, i.getWallThickness(B, England, MetalFrame, none));
		Assert.assertEquals(1000*0.10, i.getWallThickness(C, England, MetalFrame, none));
		Assert.assertEquals(1000*0.20, i.getWallThickness(D, England, MetalFrame, none));
		Assert.assertEquals(1000*0.22, i.getWallThickness(E, England, MetalFrame, none));
		Assert.assertEquals(1000*0.22, i.getWallThickness(F, England, MetalFrame, none));
		Assert.assertEquals(1000*0.22, i.getWallThickness(G, England, MetalFrame, none));
		Assert.assertEquals(1000*0.22, i.getWallThickness(H, England, MetalFrame, none));
		Assert.assertEquals(1000*0.25, i.getWallThickness(I, England, MetalFrame, none));
		Assert.assertEquals(1000*0.25, i.getWallThickness(J, England, MetalFrame, none));
		Assert.assertEquals(1000*0.25, i.getWallThickness(K, England, MetalFrame, none));
	}

	@Test
	public void testInsulatedThickness() {
		final Set<WallInsulationType> ins = ImmutableSet.of(External);
		for (final WallConstructionType wc : new WallConstructionType[] {GraniteOrWhinstone, Sandstone}) {
			Assert.assertEquals(wc.toString(), 1000*0.57, i.getWallThickness(A, England, wc, ins));
			Assert.assertEquals(wc.toString(), 1000*0.57, i.getWallThickness(B, England, wc, ins));
			Assert.assertEquals(wc.toString(), 1000*0.57, i.getWallThickness(C, England, wc, ins));
			Assert.assertEquals(wc.toString(), 1000*0.57, i.getWallThickness(D, England, wc, ins));
			Assert.assertEquals(wc.toString(), 1000*0.52, i.getWallThickness(E, England, wc, ins));
			Assert.assertEquals(wc.toString(), 1000*0.49, i.getWallThickness(F, England, wc, ins));
			Assert.assertEquals(wc.toString(), 1000*0.49, i.getWallThickness(G, England, wc, ins));
			Assert.assertEquals(wc.toString(), 1000*0.49, i.getWallThickness(H, England, wc, ins));
			Assert.assertEquals(wc.toString(), 1000*0.52, i.getWallThickness(I, England, wc, ins));
			Assert.assertEquals(wc.toString(), 1000*0.52, i.getWallThickness(J, England, wc, ins));
			Assert.assertEquals(wc.toString(), 1000*0.52, i.getWallThickness(K, England, wc, ins));
		}

		Assert.assertEquals(1000*0.29, i.getWallThickness(A, England, SolidBrick, ins));
		Assert.assertEquals(1000*0.29, i.getWallThickness(B, England, SolidBrick, ins));
		Assert.assertEquals(1000*0.29, i.getWallThickness(C, England, SolidBrick, ins));
		Assert.assertEquals(1000*0.29, i.getWallThickness(D, England, SolidBrick, ins));
		Assert.assertEquals(1000*0.31, i.getWallThickness(E, England, SolidBrick, ins));
		Assert.assertEquals(1000*0.32, i.getWallThickness(F, England, SolidBrick, ins));
		Assert.assertEquals(1000*0.34, i.getWallThickness(G, England, SolidBrick, ins));
		Assert.assertEquals(1000*0.34, i.getWallThickness(H, England, SolidBrick, ins));
		Assert.assertEquals(1000*0.37, i.getWallThickness(I, England, SolidBrick, ins));
		Assert.assertEquals(1000*0.37, i.getWallThickness(J, England, SolidBrick, ins));
		Assert.assertEquals(1000*0.37, i.getWallThickness(K, England, SolidBrick, ins));

		Assert.assertEquals(1000*0.32, i.getWallThickness(A, England, SystemBuild, ins));
		Assert.assertEquals(1000*0.32, i.getWallThickness(B, England, SystemBuild, ins));
		Assert.assertEquals(1000*0.32, i.getWallThickness(C, England, SystemBuild, ins));
		Assert.assertEquals(1000*0.32, i.getWallThickness(D, England, SystemBuild, ins));
		Assert.assertEquals(1000*0.32, i.getWallThickness(E, England, SystemBuild, ins));
		Assert.assertEquals(1000*0.37, i.getWallThickness(F, England, SystemBuild, ins));
		Assert.assertEquals(1000*0.37, i.getWallThickness(G, England, SystemBuild, ins));
		Assert.assertEquals(1000*0.37, i.getWallThickness(H, England, SystemBuild, ins));
		Assert.assertEquals(1000*0.37, i.getWallThickness(I, England, SystemBuild, ins));
		Assert.assertEquals(1000*0.37, i.getWallThickness(J, England, SystemBuild, ins));
		Assert.assertEquals(1000*0.37, i.getWallThickness(K, England, SystemBuild, ins));

		Assert.assertEquals(1000*0.59, i.getWallThickness(A, England, Cob, ins));
		Assert.assertEquals(1000*0.59, i.getWallThickness(B, England, Cob, ins));
		Assert.assertEquals(1000*0.59, i.getWallThickness(C, England, Cob, ins));
		Assert.assertEquals(1000*0.59, i.getWallThickness(D, England, Cob, ins));
		Assert.assertEquals(1000*0.59, i.getWallThickness(E, England, Cob, ins));
		Assert.assertEquals(1000*0.59, i.getWallThickness(F, England, Cob, ins));
		Assert.assertEquals(1000*0.61, i.getWallThickness(G, England, Cob, ins));
		Assert.assertEquals(1000*0.61, i.getWallThickness(H, England, Cob, ins));
		Assert.assertEquals(1000*0.64, i.getWallThickness(I, England, Cob, ins));
		Assert.assertEquals(1000*0.64, i.getWallThickness(J, England, Cob, ins));
		Assert.assertEquals(1000*0.64, i.getWallThickness(K, England, Cob, ins));
	}
}
