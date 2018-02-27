package uk.org.cse.stockimport.imputation.rdsap.walls;
import static uk.org.cse.nhm.energycalculator.api.types.WallConstructionType.Cavity;
import static uk.org.cse.nhm.energycalculator.api.types.WallConstructionType.Cob;
import static uk.org.cse.nhm.energycalculator.api.types.WallConstructionType.GraniteOrWhinstone;
import static uk.org.cse.nhm.energycalculator.api.types.WallConstructionType.MetalFrame;
import static uk.org.cse.nhm.energycalculator.api.types.WallConstructionType.Sandstone;
import static uk.org.cse.nhm.energycalculator.api.types.WallConstructionType.SolidBrick;
import static uk.org.cse.nhm.energycalculator.api.types.WallConstructionType.SystemBuild;
import static uk.org.cse.nhm.energycalculator.api.types.WallConstructionType.TimberFrame;
import static uk.org.cse.nhm.energycalculator.api.types.WallInsulationType.External;
import static uk.org.cse.nhm.energycalculator.api.types.WallInsulationType.Internal;
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

import java.util.Collections;
import java.util.Set;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;
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
					i.getUValue(band, England, GraniteOrWhinstone, none),
					0
					);
		}
		Assert.assertEquals(1.7, i.getUValue(E, England, GraniteOrWhinstone, none), 0);
		Assert.assertEquals(1.0, i.getUValue(F, England, GraniteOrWhinstone, none), 0);
		Assert.assertEquals(0.6, i.getUValue(G, England, GraniteOrWhinstone, none), 0);
		Assert.assertEquals(0.6, i.getUValue(H, England, GraniteOrWhinstone, none), 0);
		Assert.assertEquals(0.45, i.getUValue(I, England, GraniteOrWhinstone, none), 0);
		Assert.assertEquals(0.35, i.getUValue(J, England, GraniteOrWhinstone, none), 0);
		Assert.assertEquals(0.30, i.getUValue(K, England, GraniteOrWhinstone, none), 0);
	}

	@Test
	public void testSandstoneUValues() {
		final Set<WallInsulationType> none = Collections.emptySet();
		for (final Band band : between(A, D)) {
			Assert.assertEquals(
					2.0, //TODO CHM says 2.1, but it is wrong by RDSAP; check thickness?
					i.getUValue(band, England, Sandstone, none),
					0
					);
		}
		Assert.assertEquals(1.7, i.getUValue(E, England, Sandstone, none), 0);
		Assert.assertEquals(1.0, i.getUValue(F, England, Sandstone, none), 0);
		Assert.assertEquals(0.6, i.getUValue(G, England, Sandstone, none), 0);
		Assert.assertEquals(0.6, i.getUValue(H, England, Sandstone, none), 0);
		Assert.assertEquals(0.45, i.getUValue(I, England, Sandstone, none), 0);
		Assert.assertEquals(0.35, i.getUValue(J, England, Sandstone, none), 0);
		Assert.assertEquals(0.30, i.getUValue(K, England, Sandstone, none), 0);
	}

	@Test
	public void testSolidBrickUValues() {
		final Set<WallInsulationType> none = Collections.emptySet();
		for (final Band band : between(A, D)) {
			Assert.assertEquals(2.1, i.getUValue(band, England, SolidBrick, none), 0);
		}
		Assert.assertEquals(1.7, i.getUValue(E, England, SolidBrick, none), 0);
		Assert.assertEquals(1.0, i.getUValue(F, England, SolidBrick, none), 0);
		Assert.assertEquals(0.6, i.getUValue(G, England, SolidBrick, none), 0);
		Assert.assertEquals(0.6, i.getUValue(H, England, SolidBrick, none), 0);
		Assert.assertEquals(0.45, i.getUValue(I, England, SolidBrick, none), 0);
		Assert.assertEquals(0.35, i.getUValue(J, England, SolidBrick, none), 0);
		Assert.assertEquals(0.30, i.getUValue(K, England, SolidBrick, none), 0);
	}

	@Test
	public void testInsulatedBrickOrStoneUValues() {
		for (final WallConstructionType ct : new WallConstructionType[] {SolidBrick, GraniteOrWhinstone, Sandstone}) {
			final Set<WallInsulationType> external = ImmutableSet.of(WallInsulationType.External);
			for (final Band band : between(A, D)) {
				Assert.assertEquals(0.6, i.getUValue(band, England, ct, external), 0);
			}
			Assert.assertEquals(0.55, i.getUValue(E, England, ct, external), 0);
			Assert.assertEquals(0.45, i.getUValue(F, England, ct, external), 0);
			Assert.assertEquals(0.35, i.getUValue(G, England, ct, external), 0);
			Assert.assertEquals(0.35, i.getUValue(H, England, ct, external), 0);
			Assert.assertEquals(0.30, i.getUValue(I, England, ct, external), 0);
			Assert.assertEquals(0.25, i.getUValue(J, England, ct, external), 0);
			Assert.assertEquals(0.21, i.getUValue(K, England, ct, external), 0);

			final Set<WallInsulationType> internal = ImmutableSet.of(WallInsulationType.External);
			for (final Band band : between(A, D)) {
				Assert.assertEquals(0.6,i.getUValue(band, England, SolidBrick, internal), 0);
			}
			Assert.assertEquals(0.55, i.getUValue(E, England, ct, internal), 0);
			Assert.assertEquals(0.45, i.getUValue(F, England, ct, internal), 0);
			Assert.assertEquals(0.35, i.getUValue(G, England, ct, internal), 0);
			Assert.assertEquals(0.35, i.getUValue(H, England, ct, internal), 0);
			Assert.assertEquals(0.30, i.getUValue(I, England, ct, internal), 0);
			Assert.assertEquals(0.25, i.getUValue(J, England, ct, internal), 0);
			Assert.assertEquals(0.21, i.getUValue(K, England, ct, internal), 0);
		}
	}

	@Test
	public void testCobUValues() {
		final Set<WallInsulationType> none = Collections.emptySet();
		for (final Band band : between(A, F)) {
			Assert.assertEquals(0.8,i.getUValue(band, England, Cob, none), 0);
		}
		Assert.assertEquals(0.60, i.getUValue(G, England, Cob, none), 0);
		Assert.assertEquals(0.60, i.getUValue(H, England, Cob, none), 0);
		Assert.assertEquals(0.45, i.getUValue(I, England, Cob, none), 0);
		Assert.assertEquals(0.35, i.getUValue(J, England, Cob, none), 0);
		Assert.assertEquals(0.30, i.getUValue(K, England, Cob, none), 0);
	}

	@Test
	public void testInsulatedCobUValues() {
		for (final WallInsulationType wit : new WallInsulationType[] {Internal, External}) {
			final Set<WallInsulationType> insulation = ImmutableSet.of(wit);
			for (final Band band : between(A, F)) {
				Assert.assertEquals(0.4,i.getUValue(band, England, Cob, insulation), 0);
			}
			Assert.assertEquals(0.35, i.getUValue(G, England, Cob, insulation), 0);
			Assert.assertEquals(0.35, i.getUValue(H, England, Cob, insulation), 0);
			Assert.assertEquals(0.30, i.getUValue(I, England, Cob, insulation), 0);
			Assert.assertEquals(0.25, i.getUValue(J, England, Cob, insulation), 0);
			Assert.assertEquals(0.21, i.getUValue(K, England, Cob, insulation), 0);
		}
	}

	@Test
	public void testCavityUValues() {
		final Set<WallInsulationType> none = Collections.emptySet();
		Assert.assertEquals(2.1, i.getUValue(A, England, Cavity, none), 0);
		for (final Band band : between(B, E)) {
			Assert.assertEquals(1.6,i.getUValue(band, England, Cavity, none), 0);
		}
		Assert.assertEquals(1.0, i.getUValue(F, England, Cavity, none), 0);
		Assert.assertEquals(0.6, i.getUValue(G, England, Cavity, none), 0);
		Assert.assertEquals(0.6, i.getUValue(H, England, Cavity, none), 0);
		Assert.assertEquals(0.45, i.getUValue(I, England, Cavity, none), 0);
		Assert.assertEquals(0.35, i.getUValue(J, England, Cavity, none), 0);
		Assert.assertEquals(0.30, i.getUValue(K, England, Cavity, none), 0);
	}

	@Test
	public void testInsulatedCavityUValues() {
		for (final WallInsulationType wit : WallInsulationType.values()) {
			final Set<WallInsulationType> insulation = ImmutableSet.of(wit);
			for (final Band band : between(A, F)) {
				Assert.assertEquals(0.65,i.getUValue(band, England, Cavity, insulation), 0);
			}
			Assert.assertEquals(0.35, i.getUValue(G, England, Cavity, insulation), 0);
			Assert.assertEquals(0.35, i.getUValue(H, England, Cavity, insulation), 0);
			Assert.assertEquals(0.45, i.getUValue(I, England, Cavity, insulation), 0);
			Assert.assertEquals(0.35, i.getUValue(J, England, Cavity, insulation), 0);
			Assert.assertEquals(0.30, i.getUValue(K, England, Cavity, insulation), 0);
		}
	}

	@Test
	public void testTimberFrameUValues() {
		final Set<WallInsulationType> none = Collections.emptySet();
		Assert.assertEquals(2.5, i.getUValue(A, England, TimberFrame, none), 0);
		Assert.assertEquals(1.9, i.getUValue(B, England, TimberFrame, none), 0);
		Assert.assertEquals(1.9, i.getUValue(C, England, TimberFrame, none), 0);
		Assert.assertEquals(1.0, i.getUValue(D, England, TimberFrame, none), 0);
		Assert.assertEquals(0.8, i.getUValue(E, England, TimberFrame, none), 0);
		Assert.assertEquals(0.45, i.getUValue(F, England, TimberFrame, none), 0);
		Assert.assertEquals(0.4, i.getUValue(G, England, TimberFrame, none), 0);
		Assert.assertEquals(0.4, i.getUValue(H, England, TimberFrame, none), 0);
		Assert.assertEquals(0.4, i.getUValue(I, England, TimberFrame, none), 0);
		Assert.assertEquals(0.35, i.getUValue(J, England, TimberFrame, none), 0);
		Assert.assertEquals(0.3, i.getUValue(K, England, TimberFrame, none), 0);
	}

	@Test
	public void testSystemBuildUValues() {
		final Set<WallInsulationType> none = Collections.emptySet();
		Assert.assertEquals(2.0, i.getUValue(A, England, SystemBuild, none), 0);
		Assert.assertEquals(2.0, i.getUValue(B, England, SystemBuild, none), 0);
		Assert.assertEquals(2.0, i.getUValue(C, England, SystemBuild, none), 0);
		Assert.assertEquals(2.0, i.getUValue(D, England, SystemBuild, none), 0);
		Assert.assertEquals(1.7, i.getUValue(E, England, SystemBuild, none), 0);
		Assert.assertEquals(1.0, i.getUValue(F, England, SystemBuild, none), 0);
		Assert.assertEquals(0.6, i.getUValue(G, England, SystemBuild, none), 0);
		Assert.assertEquals(0.6, i.getUValue(H, England, SystemBuild, none), 0);
		Assert.assertEquals(0.45, i.getUValue(I, England, SystemBuild, none), 0);
		Assert.assertEquals(0.35, i.getUValue(J, England, SystemBuild, none), 0);
		Assert.assertEquals(0.3, i.getUValue(K, England, SystemBuild, none), 0);
	}

	@Test
	public void testInsulatedSystemBuildUValues() {
		Set<WallInsulationType> insulation = ImmutableSet.of(External);
		Assert.assertEquals(0.6, i.getUValue(A, England, SystemBuild, insulation), 0);
		Assert.assertEquals(0.6, i.getUValue(B, England, SystemBuild, insulation), 0);
		Assert.assertEquals(0.6, i.getUValue(C, England, SystemBuild, insulation), 0);
		Assert.assertEquals(0.6, i.getUValue(D, England, SystemBuild, insulation), 0);
		Assert.assertEquals(0.55, i.getUValue(E, England, SystemBuild, insulation), 0);
		Assert.assertEquals(0.45, i.getUValue(F, England, SystemBuild, insulation), 0);
		Assert.assertEquals(0.35, i.getUValue(G, England, SystemBuild, insulation), 0);
		Assert.assertEquals(0.35, i.getUValue(H, England, SystemBuild, insulation), 0);
		Assert.assertEquals(0.30, i.getUValue(I, England, SystemBuild, insulation), 0);
		Assert.assertEquals(0.25, i.getUValue(J, England, SystemBuild, insulation), 0);
		Assert.assertEquals(0.21, i.getUValue(K, England, SystemBuild, insulation), 0);
		insulation = ImmutableSet.of(Internal);
		Assert.assertEquals(0.6, i.getUValue(A, England, SystemBuild, insulation), 0);
		Assert.assertEquals(0.6, i.getUValue(B, England, SystemBuild, insulation), 0);
		Assert.assertEquals(0.6, i.getUValue(C, England, SystemBuild, insulation), 0);
		Assert.assertEquals(0.6, i.getUValue(D, England, SystemBuild, insulation), 0);
		Assert.assertEquals(0.55, i.getUValue(E, England, SystemBuild, insulation), 0);
		Assert.assertEquals(0.45, i.getUValue(F, England, SystemBuild, insulation), 0);
		Assert.assertEquals(0.35, i.getUValue(G, England, SystemBuild, insulation), 0);
		Assert.assertEquals(0.35, i.getUValue(H, England, SystemBuild, insulation), 0);
		Assert.assertEquals(0.30, i.getUValue(I, England, SystemBuild, insulation), 0);
		Assert.assertEquals(0.25, i.getUValue(J, England, SystemBuild, insulation), 0);
		Assert.assertEquals(0.21, i.getUValue(K, England, SystemBuild, insulation), 0);
	}

	@Test
	public void testMetalFrameUValues() {
		final Set<WallInsulationType> none = Collections.emptySet();
		Assert.assertEquals(2.2, i.getUValue(A, England, MetalFrame, none), 0);
		Assert.assertEquals(2.2, i.getUValue(B, England, MetalFrame, none), 0);
		Assert.assertEquals(2.2, i.getUValue(C, England, MetalFrame, none), 0);
		Assert.assertEquals(0.86, i.getUValue(D, England, MetalFrame, none), 0);
		Assert.assertEquals(0.86, i.getUValue(E, England, MetalFrame, none), 0);
		Assert.assertEquals(0.53, i.getUValue(F, England, MetalFrame, none), 0);
		Assert.assertEquals(0.53, i.getUValue(G, England, MetalFrame, none), 0);
		Assert.assertEquals(0.53, i.getUValue(H, England, MetalFrame, none), 0);
		Assert.assertEquals(0.45, i.getUValue(I, England, MetalFrame, none), 0);
		Assert.assertEquals(0.35, i.getUValue(J, England, MetalFrame, none), 0);
		Assert.assertEquals(0.3, i.getUValue(K, England, MetalFrame, none), 0);
	}
}
