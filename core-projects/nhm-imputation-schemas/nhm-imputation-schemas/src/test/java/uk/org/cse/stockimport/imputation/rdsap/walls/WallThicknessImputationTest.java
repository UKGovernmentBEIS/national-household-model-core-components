package uk.org.cse.stockimport.imputation.rdsap.walls;
import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.Cavity;
import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.Cob;
import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.GraniteOrWhinstone;
import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.MetalFrame;
import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.Sandstone;
import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.SolidBrick;
import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.SystemBuild;
import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.TimberFrame;
import static uk.org.cse.nhm.hom.components.fabric.types.WallInsulationType.External;
import static uk.org.cse.nhm.energycalculator.api.types.RegionType.London;
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

import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType;
import uk.org.cse.nhm.hom.components.fabric.types.WallInsulationType;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;
import uk.org.cse.stockimport.imputation.walls.WallPropertyImputer;

import com.google.common.collect.ImmutableSet;

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
				Assert.assertEquals("Band " + band, 500d, i.getWallThickness(band, London, wc, none));
			Assert.assertEquals(450d, i.getWallThickness(E, London, wc, none));
			for (final SAPAgeBandValue.Band band : between(F, H)) 
				Assert.assertEquals("Band " + band, 420d, i.getWallThickness(band, London, wc, none));
			for (final SAPAgeBandValue.Band band : between(I, K)) 
				Assert.assertEquals("Band " + band, 450d, i.getWallThickness(band, London, wc, none));
		}
		
		// solid brick
		
		for (final Band band : between(A, D)) 
			Assert.assertEquals("Band " + band, 220d, i.getWallThickness(band, London, SolidBrick, none));
		
		Assert.assertEquals(240d, i.getWallThickness(E, London, SolidBrick, none));
		Assert.assertEquals(250d, i.getWallThickness(F, London, SolidBrick, none));
		Assert.assertEquals(270d, i.getWallThickness(G, London, SolidBrick, none));
		Assert.assertEquals(270d, i.getWallThickness(H, London, SolidBrick, none));
		Assert.assertEquals(300d, i.getWallThickness(I, London, SolidBrick, none));
		Assert.assertEquals(300d, i.getWallThickness(J, London, SolidBrick, none));
		Assert.assertEquals(300d, i.getWallThickness(K, London, SolidBrick, none));
		
		Assert.assertEquals(1000*0.54, i.getWallThickness(A, London, Cob, none));
		Assert.assertEquals(1000*0.54, i.getWallThickness(B, London, Cob, none));
		Assert.assertEquals(1000*0.54, i.getWallThickness(C, London, Cob, none));
		Assert.assertEquals(1000*0.54, i.getWallThickness(D, London, Cob, none));
		Assert.assertEquals(1000*0.54, i.getWallThickness(E, London, Cob, none));
		Assert.assertEquals(1000*0.54, i.getWallThickness(F, London, Cob, none));
		Assert.assertEquals(1000*0.56, i.getWallThickness(G, London, Cob, none));
		Assert.assertEquals(1000*0.56, i.getWallThickness(H, London, Cob, none));
		Assert.assertEquals(1000*0.59, i.getWallThickness(I, London, Cob, none));
		Assert.assertEquals(1000*0.59, i.getWallThickness(J, London, Cob, none));
		Assert.assertEquals(1000*0.59, i.getWallThickness(K, London, Cob, none));
		
		Assert.assertEquals(1000*0.25, i.getWallThickness(A, London, Cavity, none));
		Assert.assertEquals(1000*0.25, i.getWallThickness(B, London, Cavity, none));
		Assert.assertEquals(1000*0.25, i.getWallThickness(C, London, Cavity, none));
		Assert.assertEquals(1000*0.25, i.getWallThickness(D, London, Cavity, none));
		Assert.assertEquals(1000*0.25, i.getWallThickness(E, London, Cavity, none));
		Assert.assertEquals(1000*0.26, i.getWallThickness(F, London, Cavity, none));
		Assert.assertEquals(1000*0.27, i.getWallThickness(G, London, Cavity, none));
		Assert.assertEquals(1000*0.27, i.getWallThickness(H, London, Cavity, none));
		Assert.assertEquals(1000*0.30, i.getWallThickness(I, London, Cavity, none));
		Assert.assertEquals(1000*0.30, i.getWallThickness(J, London, Cavity, none));
		Assert.assertEquals(1000*0.30, i.getWallThickness(K, London, Cavity, none));

		Assert.assertEquals(1000*0.15, i.getWallThickness(A, London, TimberFrame, none));
		Assert.assertEquals(1000*0.15, i.getWallThickness(B, London, TimberFrame, none));
		Assert.assertEquals(1000*0.15, i.getWallThickness(C, London, TimberFrame, none));
		Assert.assertEquals(1000*0.25, i.getWallThickness(D, London, TimberFrame, none));
		Assert.assertEquals(1000*0.27, i.getWallThickness(E, London, TimberFrame, none));
		Assert.assertEquals(1000*0.27, i.getWallThickness(F, London, TimberFrame, none));
		Assert.assertEquals(1000*0.27, i.getWallThickness(G, London, TimberFrame, none));
		Assert.assertEquals(1000*0.27, i.getWallThickness(H, London, TimberFrame, none));
		Assert.assertEquals(1000*0.30, i.getWallThickness(I, London, TimberFrame, none));
		Assert.assertEquals(1000*0.30, i.getWallThickness(J, London, TimberFrame, none));
		Assert.assertEquals(1000*0.30, i.getWallThickness(K, London, TimberFrame, none));
		
		Assert.assertEquals(1000*0.25, i.getWallThickness(A, London, SystemBuild, none));
		Assert.assertEquals(1000*0.25, i.getWallThickness(B, London, SystemBuild, none));
		Assert.assertEquals(1000*0.25, i.getWallThickness(C, London, SystemBuild, none));
		Assert.assertEquals(1000*0.25, i.getWallThickness(D, London, SystemBuild, none));
		Assert.assertEquals(1000*0.25, i.getWallThickness(E, London, SystemBuild, none));
		Assert.assertEquals(1000*0.30, i.getWallThickness(F, London, SystemBuild, none));
		Assert.assertEquals(1000*0.30, i.getWallThickness(G, London, SystemBuild, none));
		Assert.assertEquals(1000*0.30, i.getWallThickness(H, London, SystemBuild, none));
		Assert.assertEquals(1000*0.30, i.getWallThickness(I, London, SystemBuild, none));
		Assert.assertEquals(1000*0.30, i.getWallThickness(J, London, SystemBuild, none));
		Assert.assertEquals(1000*0.30, i.getWallThickness(K, London, SystemBuild, none));
		
		Assert.assertEquals(1000*0.10, i.getWallThickness(A, London, MetalFrame, none));
		Assert.assertEquals(1000*0.10, i.getWallThickness(B, London, MetalFrame, none));
		Assert.assertEquals(1000*0.10, i.getWallThickness(C, London, MetalFrame, none));
		Assert.assertEquals(1000*0.20, i.getWallThickness(D, London, MetalFrame, none));
		Assert.assertEquals(1000*0.22, i.getWallThickness(E, London, MetalFrame, none));
		Assert.assertEquals(1000*0.22, i.getWallThickness(F, London, MetalFrame, none));
		Assert.assertEquals(1000*0.22, i.getWallThickness(G, London, MetalFrame, none));
		Assert.assertEquals(1000*0.22, i.getWallThickness(H, London, MetalFrame, none));
		Assert.assertEquals(1000*0.25, i.getWallThickness(I, London, MetalFrame, none));
		Assert.assertEquals(1000*0.25, i.getWallThickness(J, London, MetalFrame, none));
		Assert.assertEquals(1000*0.25, i.getWallThickness(K, London, MetalFrame, none));
	}
	
	@Test
	public void testInsulatedThickness() {
		final Set<WallInsulationType> ins = ImmutableSet.of(External);
		for (final WallConstructionType wc : new WallConstructionType[] {GraniteOrWhinstone, Sandstone}) {
			Assert.assertEquals(wc.toString(), 1000*0.57, i.getWallThickness(A, London, wc, ins));
			Assert.assertEquals(wc.toString(), 1000*0.57, i.getWallThickness(B, London, wc, ins));
			Assert.assertEquals(wc.toString(), 1000*0.57, i.getWallThickness(C, London, wc, ins));
			Assert.assertEquals(wc.toString(), 1000*0.57, i.getWallThickness(D, London, wc, ins));
			Assert.assertEquals(wc.toString(), 1000*0.52, i.getWallThickness(E, London, wc, ins));
			Assert.assertEquals(wc.toString(), 1000*0.49, i.getWallThickness(F, London, wc, ins));
			Assert.assertEquals(wc.toString(), 1000*0.49, i.getWallThickness(G, London, wc, ins));
			Assert.assertEquals(wc.toString(), 1000*0.49, i.getWallThickness(H, London, wc, ins));
			Assert.assertEquals(wc.toString(), 1000*0.52, i.getWallThickness(I, London, wc, ins));
			Assert.assertEquals(wc.toString(), 1000*0.52, i.getWallThickness(J, London, wc, ins));
			Assert.assertEquals(wc.toString(), 1000*0.52, i.getWallThickness(K, London, wc, ins));
		}
	
		Assert.assertEquals(1000*0.29, i.getWallThickness(A, London, SolidBrick, ins));
		Assert.assertEquals(1000*0.29, i.getWallThickness(B, London, SolidBrick, ins));
		Assert.assertEquals(1000*0.29, i.getWallThickness(C, London, SolidBrick, ins));
		Assert.assertEquals(1000*0.29, i.getWallThickness(D, London, SolidBrick, ins));
		Assert.assertEquals(1000*0.31, i.getWallThickness(E, London, SolidBrick, ins));
		Assert.assertEquals(1000*0.32, i.getWallThickness(F, London, SolidBrick, ins));
		Assert.assertEquals(1000*0.34, i.getWallThickness(G, London, SolidBrick, ins));
		Assert.assertEquals(1000*0.34, i.getWallThickness(H, London, SolidBrick, ins));
		Assert.assertEquals(1000*0.37, i.getWallThickness(I, London, SolidBrick, ins));
		Assert.assertEquals(1000*0.37, i.getWallThickness(J, London, SolidBrick, ins));
		Assert.assertEquals(1000*0.37, i.getWallThickness(K, London, SolidBrick, ins));
		
		Assert.assertEquals(1000*0.32, i.getWallThickness(A, London, SystemBuild, ins));
		Assert.assertEquals(1000*0.32, i.getWallThickness(B, London, SystemBuild, ins));
		Assert.assertEquals(1000*0.32, i.getWallThickness(C, London, SystemBuild, ins));
		Assert.assertEquals(1000*0.32, i.getWallThickness(D, London, SystemBuild, ins));
		Assert.assertEquals(1000*0.32, i.getWallThickness(E, London, SystemBuild, ins));
		Assert.assertEquals(1000*0.37, i.getWallThickness(F, London, SystemBuild, ins));
		Assert.assertEquals(1000*0.37, i.getWallThickness(G, London, SystemBuild, ins));
		Assert.assertEquals(1000*0.37, i.getWallThickness(H, London, SystemBuild, ins));
		Assert.assertEquals(1000*0.37, i.getWallThickness(I, London, SystemBuild, ins));
		Assert.assertEquals(1000*0.37, i.getWallThickness(J, London, SystemBuild, ins));
		Assert.assertEquals(1000*0.37, i.getWallThickness(K, London, SystemBuild, ins));
		
		Assert.assertEquals(1000*0.59, i.getWallThickness(A, London, Cob, ins));
		Assert.assertEquals(1000*0.59, i.getWallThickness(B, London, Cob, ins));
		Assert.assertEquals(1000*0.59, i.getWallThickness(C, London, Cob, ins));
		Assert.assertEquals(1000*0.59, i.getWallThickness(D, London, Cob, ins));
		Assert.assertEquals(1000*0.59, i.getWallThickness(E, London, Cob, ins));
		Assert.assertEquals(1000*0.59, i.getWallThickness(F, London, Cob, ins));
		Assert.assertEquals(1000*0.61, i.getWallThickness(G, London, Cob, ins));
		Assert.assertEquals(1000*0.61, i.getWallThickness(H, London, Cob, ins));
		Assert.assertEquals(1000*0.64, i.getWallThickness(I, London, Cob, ins));
		Assert.assertEquals(1000*0.64, i.getWallThickness(J, London, Cob, ins));
		Assert.assertEquals(1000*0.64, i.getWallThickness(K, London, Cob, ins));
	}
}
