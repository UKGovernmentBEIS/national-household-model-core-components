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
import static uk.org.cse.nhm.hom.components.fabric.types.WallInsulationType.Internal;
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

import java.util.Collections;
import java.util.Set;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType;
import uk.org.cse.nhm.hom.components.fabric.types.WallInsulationType;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;
import uk.org.cse.stockimport.imputation.walls.WallPropertyImputer;

import com.google.common.collect.ImmutableSet;

/**
 * Just checks u values, as there are a lot of them and the test is quite big as a result.
 * @author hinton
 *
 */
public class WallUValueTest {
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
	public void testGraniteUValues() {
		final Set<WallInsulationType> none = Collections.emptySet();
		for (final Band band : between(A, D)) {
			Assert.assertEquals(
					2.3, //TODO CHM says 2.4, but it is wrong by RDSAP; check thickness?
					i.getUValue(band, London, GraniteOrWhinstone, none)
					);
		}
		Assert.assertEquals(1.7, i.getUValue(E, London, GraniteOrWhinstone, none));
		Assert.assertEquals(1.0, i.getUValue(F, London, GraniteOrWhinstone, none));
		Assert.assertEquals(0.6, i.getUValue(G, London, GraniteOrWhinstone, none));
		Assert.assertEquals(0.6, i.getUValue(H, London, GraniteOrWhinstone, none));
		Assert.assertEquals(0.45, i.getUValue(I, London, GraniteOrWhinstone, none));
		Assert.assertEquals(0.35, i.getUValue(J, London, GraniteOrWhinstone, none));
		Assert.assertEquals(0.30, i.getUValue(K, London, GraniteOrWhinstone, none));
	}
	
	@Test
	public void testSandstoneUValues() {
		final Set<WallInsulationType> none = Collections.emptySet();
		for (final Band band : between(A, D)) {
			Assert.assertEquals(
					2.0, //TODO CHM says 2.1, but it is wrong by RDSAP; check thickness?
					i.getUValue(band, London, Sandstone, none)
					);
		}
		Assert.assertEquals(1.7, i.getUValue(E, London, Sandstone, none));
		Assert.assertEquals(1.0, i.getUValue(F, London, Sandstone, none));
		Assert.assertEquals(0.6, i.getUValue(G, London, Sandstone, none));
		Assert.assertEquals(0.6, i.getUValue(H, London, Sandstone, none));
		Assert.assertEquals(0.45, i.getUValue(I, London, Sandstone, none));
		Assert.assertEquals(0.35, i.getUValue(J, London, Sandstone, none));
		Assert.assertEquals(0.30, i.getUValue(K, London, Sandstone, none));
	}
	
	@Test
	public void testSolidBrickUValues() {
		final Set<WallInsulationType> none = Collections.emptySet();
		for (final Band band : between(A, D)) {
			Assert.assertEquals(2.1, i.getUValue(band, London, SolidBrick, none));
		}
		Assert.assertEquals(1.7, i.getUValue(E, London, SolidBrick, none));
		Assert.assertEquals(1.0, i.getUValue(F, London, SolidBrick, none));
		Assert.assertEquals(0.6, i.getUValue(G, London, SolidBrick, none));
		Assert.assertEquals(0.6, i.getUValue(H, London, SolidBrick, none));
		Assert.assertEquals(0.45, i.getUValue(I, London, SolidBrick, none));
		Assert.assertEquals(0.35, i.getUValue(J, London, SolidBrick, none));
		Assert.assertEquals(0.30, i.getUValue(K, London, SolidBrick, none));
	}
	
	@Test
	public void testInsulatedBrickOrStoneUValues() {
		for (final WallConstructionType ct : new WallConstructionType[] {SolidBrick, GraniteOrWhinstone, Sandstone}) {
			final Set<WallInsulationType> external = ImmutableSet.of(WallInsulationType.External);
			for (final Band band : between(A, D)) {
				Assert.assertEquals(0.6, i.getUValue(band, London, ct, external));
			}
			Assert.assertEquals(0.55, i.getUValue(E, London, ct, external));
			Assert.assertEquals(0.45, i.getUValue(F, London, ct, external));
			Assert.assertEquals(0.35, i.getUValue(G, London, ct, external));
			Assert.assertEquals(0.35, i.getUValue(H, London, ct, external));
			Assert.assertEquals(0.30, i.getUValue(I, London, ct, external));
			Assert.assertEquals(0.25, i.getUValue(J, London, ct, external));
			Assert.assertEquals(0.21, i.getUValue(K, London, ct, external));
			
			final Set<WallInsulationType> internal = ImmutableSet.of(WallInsulationType.External);
			for (final Band band : between(A, D)) {
				Assert.assertEquals(0.6,i.getUValue(band, London, SolidBrick, internal));
			}
			Assert.assertEquals(0.55, i.getUValue(E, London, ct, internal));
			Assert.assertEquals(0.45, i.getUValue(F, London, ct, internal));
			Assert.assertEquals(0.35, i.getUValue(G, London, ct, internal));
			Assert.assertEquals(0.35, i.getUValue(H, London, ct, internal));
			Assert.assertEquals(0.30, i.getUValue(I, London, ct, internal));
			Assert.assertEquals(0.25, i.getUValue(J, London, ct, internal));
			Assert.assertEquals(0.21, i.getUValue(K, London, ct, internal));
		}
	}
	
	@Test
	public void testCobUValues() {
		final Set<WallInsulationType> none = Collections.emptySet();
		for (final Band band : between(A, F)) {
			Assert.assertEquals(0.8,i.getUValue(band, London, Cob, none));
		}
		Assert.assertEquals(0.60, i.getUValue(G, London, Cob, none));
		Assert.assertEquals(0.60, i.getUValue(H, London, Cob, none));
		Assert.assertEquals(0.45, i.getUValue(I, London, Cob, none));
		Assert.assertEquals(0.35, i.getUValue(J, London, Cob, none));
		Assert.assertEquals(0.30, i.getUValue(K, London, Cob, none));
	}

	@Test
	public void testInsulatedCobUValues() {
		for (final WallInsulationType wit : new WallInsulationType[] {Internal, External}) {
			final Set<WallInsulationType> insulation = ImmutableSet.of(wit);
			for (final Band band : between(A, F)) {
				Assert.assertEquals(0.4,i.getUValue(band, London, Cob, insulation));
			}
			Assert.assertEquals(0.35, i.getUValue(G, London, Cob, insulation));
			Assert.assertEquals(0.35, i.getUValue(H, London, Cob, insulation));
			Assert.assertEquals(0.30, i.getUValue(I, London, Cob, insulation));
			Assert.assertEquals(0.25, i.getUValue(J, London, Cob, insulation));
			Assert.assertEquals(0.21, i.getUValue(K, London, Cob, insulation));
		}
	}
	
	@Test
	public void testCavityUValues() {
		final Set<WallInsulationType> none = Collections.emptySet();
		Assert.assertEquals(2.1, i.getUValue(A, London, Cavity, none));
		for (final Band band : between(B, E)) {
			Assert.assertEquals(1.6,i.getUValue(band, London, Cavity, none));
		}
		Assert.assertEquals(1.0, i.getUValue(F, London, Cavity, none));
		Assert.assertEquals(0.6, i.getUValue(G, London, Cavity, none));
		Assert.assertEquals(0.6, i.getUValue(H, London, Cavity, none));
		Assert.assertEquals(0.45, i.getUValue(I, London, Cavity, none));
		Assert.assertEquals(0.35, i.getUValue(J, London, Cavity, none));
		Assert.assertEquals(0.30, i.getUValue(K, London, Cavity, none));
	}
	
	@Test
	public void testInsulatedCavityUValues() {
		for (final WallInsulationType wit : WallInsulationType.values()) {
			final Set<WallInsulationType> insulation = ImmutableSet.of(wit);
			for (final Band band : between(A, F)) {
				Assert.assertEquals(0.65,i.getUValue(band, London, Cavity, insulation));
			}
			Assert.assertEquals(0.35, i.getUValue(G, London, Cavity, insulation));
			Assert.assertEquals(0.35, i.getUValue(H, London, Cavity, insulation));
			Assert.assertEquals(0.45, i.getUValue(I, London, Cavity, insulation));
			Assert.assertEquals(0.35, i.getUValue(J, London, Cavity, insulation));
			Assert.assertEquals(0.30, i.getUValue(K, London, Cavity, insulation));
		}
	}
	
	@Test
	public void testTimberFrameUValues() {
		final Set<WallInsulationType> none = Collections.emptySet();
		Assert.assertEquals(2.5, i.getUValue(A, London, TimberFrame, none));
		Assert.assertEquals(1.9, i.getUValue(B, London, TimberFrame, none));
		Assert.assertEquals(1.9, i.getUValue(C, London, TimberFrame, none));
		Assert.assertEquals(1.0, i.getUValue(D, London, TimberFrame, none));
		Assert.assertEquals(0.8, i.getUValue(E, London, TimberFrame, none));
		Assert.assertEquals(0.45, i.getUValue(F, London, TimberFrame, none));
		Assert.assertEquals(0.4, i.getUValue(G, London, TimberFrame, none));
		Assert.assertEquals(0.4, i.getUValue(H, London, TimberFrame, none));
		Assert.assertEquals(0.4, i.getUValue(I, London, TimberFrame, none));
		Assert.assertEquals(0.35, i.getUValue(J, London, TimberFrame, none));
		Assert.assertEquals(0.3, i.getUValue(K, London, TimberFrame, none));
	}
	
	@Test
	public void testSystemBuildUValues() {
		final Set<WallInsulationType> none = Collections.emptySet();
		Assert.assertEquals(2.0, i.getUValue(A, London, SystemBuild, none));
		Assert.assertEquals(2.0, i.getUValue(B, London, SystemBuild, none));
		Assert.assertEquals(2.0, i.getUValue(C, London, SystemBuild, none));
		Assert.assertEquals(2.0, i.getUValue(D, London, SystemBuild, none));
		Assert.assertEquals(1.7, i.getUValue(E, London, SystemBuild, none));
		Assert.assertEquals(1.0, i.getUValue(F, London, SystemBuild, none));
		Assert.assertEquals(0.6, i.getUValue(G, London, SystemBuild, none));
		Assert.assertEquals(0.6, i.getUValue(H, London, SystemBuild, none));
		Assert.assertEquals(0.45, i.getUValue(I, London, SystemBuild, none));
		Assert.assertEquals(0.35, i.getUValue(J, London, SystemBuild, none));
		Assert.assertEquals(0.3, i.getUValue(K, London, SystemBuild, none));
	}
	
	@Test
	public void testInsulatedSystemBuildUValues() {
		Set<WallInsulationType> insulation = ImmutableSet.of(External);
		Assert.assertEquals(0.6, i.getUValue(A, London, SystemBuild, insulation));
		Assert.assertEquals(0.6, i.getUValue(B, London, SystemBuild, insulation));
		Assert.assertEquals(0.6, i.getUValue(C, London, SystemBuild, insulation));
		Assert.assertEquals(0.6, i.getUValue(D, London, SystemBuild, insulation));
		Assert.assertEquals(0.55, i.getUValue(E, London, SystemBuild, insulation));
		Assert.assertEquals(0.45, i.getUValue(F, London, SystemBuild, insulation));
		Assert.assertEquals(0.35, i.getUValue(G, London, SystemBuild, insulation));
		Assert.assertEquals(0.35, i.getUValue(H, London, SystemBuild, insulation));
		Assert.assertEquals(0.30, i.getUValue(I, London, SystemBuild, insulation));
		Assert.assertEquals(0.25, i.getUValue(J, London, SystemBuild, insulation));
		Assert.assertEquals(0.21, i.getUValue(K, London, SystemBuild, insulation));
		insulation = ImmutableSet.of(Internal);
		Assert.assertEquals(0.6, i.getUValue(A, London, SystemBuild, insulation));
		Assert.assertEquals(0.6, i.getUValue(B, London, SystemBuild, insulation));
		Assert.assertEquals(0.6, i.getUValue(C, London, SystemBuild, insulation));
		Assert.assertEquals(0.6, i.getUValue(D, London, SystemBuild, insulation));
		Assert.assertEquals(0.55, i.getUValue(E, London, SystemBuild, insulation));
		Assert.assertEquals(0.45, i.getUValue(F, London, SystemBuild, insulation));
		Assert.assertEquals(0.35, i.getUValue(G, London, SystemBuild, insulation));
		Assert.assertEquals(0.35, i.getUValue(H, London, SystemBuild, insulation));
		Assert.assertEquals(0.30, i.getUValue(I, London, SystemBuild, insulation));
		Assert.assertEquals(0.25, i.getUValue(J, London, SystemBuild, insulation));
		Assert.assertEquals(0.21, i.getUValue(K, London, SystemBuild, insulation));
	}
	
	@Test
	public void testMetalFrameUValues() {
		final Set<WallInsulationType> none = Collections.emptySet();
		Assert.assertEquals(2.2, i.getUValue(A, London, MetalFrame, none));
		Assert.assertEquals(2.2, i.getUValue(B, London, MetalFrame, none));
		Assert.assertEquals(2.2, i.getUValue(C, London, MetalFrame, none));
		Assert.assertEquals(0.86, i.getUValue(D, London, MetalFrame, none));
		Assert.assertEquals(0.86, i.getUValue(E, London, MetalFrame, none));
		Assert.assertEquals(0.53, i.getUValue(F, London, MetalFrame, none));
		Assert.assertEquals(0.53, i.getUValue(G, London, MetalFrame, none));
		Assert.assertEquals(0.53, i.getUValue(H, London, MetalFrame, none));
		Assert.assertEquals(0.45, i.getUValue(I, London, MetalFrame, none));
		Assert.assertEquals(0.35, i.getUValue(J, London, MetalFrame, none));
		Assert.assertEquals(0.3, i.getUValue(K, London, MetalFrame, none));
	}
}
