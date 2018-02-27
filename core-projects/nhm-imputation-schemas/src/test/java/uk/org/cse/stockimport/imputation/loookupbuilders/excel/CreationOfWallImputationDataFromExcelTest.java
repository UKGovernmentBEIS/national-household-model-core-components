package uk.org.cse.stockimport.imputation.loookupbuilders.excel;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static uk.org.cse.nhm.energycalculator.api.types.WallConstructionType.Cavity;
import static uk.org.cse.nhm.energycalculator.api.types.WallConstructionType.Cob;
import static uk.org.cse.nhm.energycalculator.api.types.WallConstructionType.GraniteOrWhinstone;
import static uk.org.cse.nhm.energycalculator.api.types.WallConstructionType.MetalFrame;
import static uk.org.cse.nhm.energycalculator.api.types.WallConstructionType.Sandstone;
import static uk.org.cse.nhm.energycalculator.api.types.WallConstructionType.SolidBrick;
import static uk.org.cse.nhm.energycalculator.api.types.WallConstructionType.SystemBuild;
import static uk.org.cse.nhm.energycalculator.api.types.WallConstructionType.TimberFrame;
import static uk.org.cse.nhm.energycalculator.api.types.WallInsulationType.External;
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

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import org.junit.Assert;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;
import uk.org.cse.stockimport.imputation.ImputationSchema;
import uk.org.cse.stockimport.imputation.lookupbuilders.excel.WallPropertyTablesBuilder;
import uk.org.cse.stockimport.imputation.walls.IWallInfiltrationImputer;
import uk.org.cse.stockimport.imputation.walls.IWallThicknessImputer;
import uk.org.cse.stockimport.imputation.walls.IWallUValueImputer;

public class CreationOfWallImputationDataFromExcelTest extends AbsImputationFromExcelTest{

	private final ImputationSchema imputationSchema  = new ImputationSchema("123123");
	private final WallPropertyTablesBuilder builder = new WallPropertyTablesBuilder();

	@Before
	public void initialiseTests() throws Exception {
		imputationSchema.setWallPropertyTables(builder.buildTables(getExcelWorkBook()));
	}

	@Test
	public void EnsureWallPropertiesTableIsCreated() throws Exception {
		assertThat("property table", imputationSchema.getWallPropertyTables(), is(notNullValue()));
	}

	@Test
	public void EnsureInfiltrationValuesAreaReadCorrectlyFromExcelSheet() throws Exception {
		final IWallInfiltrationImputer infiltrationImputer = imputationSchema.getWallPropertyTables().getWallInfiltrationImputer();

		assertThat("infiltrationImputer", infiltrationImputer, is(notNullValue()));
		for (final WallConstructionType wct : WallConstructionType.values()) {
			switch (wct) {
			case Cavity:
			case Cob:
			case GraniteOrWhinstone:
			case Sandstone:
			case SystemBuild:
			case SolidBrick:
				Assert.assertEquals(0.35, infiltrationImputer.getAirChangeRate(wct), 0);
				break;
			case TimberFrame:
			case MetalFrame:
				Assert.assertEquals(0.25, infiltrationImputer.getAirChangeRate(wct), 0);
				break;
			default:
				break;
			}
		}
	}

	@Test
	public void EnsureWallThicknessTablesAreReadFromExcelSheet() throws Exception {
		final IWallThicknessImputer imputer = imputationSchema.getWallPropertyTables().getWallThicknessImputer();
		assertThat("imputer", imputer, is(notNullValue()));
	}

	@Test
	public void WallThicknessMatchThoseInExcelShee() {
		final IWallThicknessImputer i = imputationSchema.getWallPropertyTables().getWallThicknessImputer();

		final Set<WallInsulationType> none = ImmutableSet.of();
		// granite
		for (final WallConstructionType wc : new WallConstructionType[] {GraniteOrWhinstone, Sandstone}) {
			for (final Band band : new SAPAgeBandValue.Band[] {A, B, C, D})
				Assert.assertEquals("Band " + band, 500d, i.getWallThickness(band, England, wc, none), 0);
			Assert.assertEquals(450d, i.getWallThickness(E, England, wc, none), 0);
			for (final Band band : new SAPAgeBandValue.Band[] {F,G, H})
				Assert.assertEquals("Band " + band, 420d, i.getWallThickness(band, England, wc, none), 0);
			for (final Band band : new SAPAgeBandValue.Band[] {I, J, K})
				Assert.assertEquals("Band " + band, 450d, i.getWallThickness(band, England, wc, none), 0);
		}

		// solid brick

		for (final SAPAgeBandValue.Band band : new SAPAgeBandValue.Band[] {A, B, C, D})
		Assert.assertEquals("Band " + band, 220d, i.getWallThickness(band, England, SolidBrick, none), 0);

		Assert.assertEquals(240d, i.getWallThickness(E, England, SolidBrick, none), 0);
		Assert.assertEquals(250d, i.getWallThickness(F, England, SolidBrick, none), 0);
		Assert.assertEquals(270d, i.getWallThickness(G, England, SolidBrick, none), 0);
		Assert.assertEquals(270d, i.getWallThickness(H, England, SolidBrick, none), 0);
		Assert.assertEquals(300d, i.getWallThickness(I, England, SolidBrick, none), 0);
		Assert.assertEquals(300d, i.getWallThickness(J, England, SolidBrick, none), 0);
		Assert.assertEquals(300d, i.getWallThickness(K, England, SolidBrick, none), 0);

		Assert.assertEquals(1000*0.54, i.getWallThickness(A, England, Cob, none), 0);
		Assert.assertEquals(1000*0.54, i.getWallThickness(B, England, Cob, none), 0);
		Assert.assertEquals(1000*0.54, i.getWallThickness(C, England, Cob, none), 0);
		Assert.assertEquals(1000*0.54, i.getWallThickness(D, England, Cob, none), 0);
		Assert.assertEquals(1000*0.54, i.getWallThickness(E, England, Cob, none), 0);
		Assert.assertEquals(1000*0.54, i.getWallThickness(F, England, Cob, none), 0);
		Assert.assertEquals(1000*0.56, i.getWallThickness(G, England, Cob, none), 0);
		Assert.assertEquals(1000*0.56, i.getWallThickness(H, England, Cob, none), 0);
		Assert.assertEquals(1000*0.59, i.getWallThickness(I, England, Cob, none), 0);
		Assert.assertEquals(1000*0.59, i.getWallThickness(J, England, Cob, none), 0);
		Assert.assertEquals(1000*0.59, i.getWallThickness(K, England, Cob, none), 0);

		Assert.assertEquals(1000*0.25, i.getWallThickness(A, England, Cavity, none), 0);
		Assert.assertEquals(1000*0.25, i.getWallThickness(B, England, Cavity, none), 0);
		Assert.assertEquals(1000*0.25, i.getWallThickness(C, England, Cavity, none), 0);
		Assert.assertEquals(1000*0.25, i.getWallThickness(D, England, Cavity, none), 0);
		Assert.assertEquals(1000*0.25, i.getWallThickness(E, England, Cavity, none), 0);
		Assert.assertEquals(1000*0.26, i.getWallThickness(F, England, Cavity, none), 0);
		Assert.assertEquals(1000*0.27, i.getWallThickness(G, England, Cavity, none), 0);
		Assert.assertEquals(1000*0.27, i.getWallThickness(H, England, Cavity, none), 0);
		Assert.assertEquals(1000*0.30, i.getWallThickness(I, England, Cavity, none), 0);
		Assert.assertEquals(1000*0.30, i.getWallThickness(J, England, Cavity, none), 0);
		Assert.assertEquals(1000*0.30, i.getWallThickness(K, England, Cavity, none), 0);

		Assert.assertEquals(1000*0.15, i.getWallThickness(A, England, TimberFrame, none), 0);
		Assert.assertEquals(1000*0.15, i.getWallThickness(B, England, TimberFrame, none), 0);
		Assert.assertEquals(1000*0.15, i.getWallThickness(C, England, TimberFrame, none), 0);
		Assert.assertEquals(1000*0.25, i.getWallThickness(D, England, TimberFrame, none), 0);
		Assert.assertEquals(1000*0.27, i.getWallThickness(E, England, TimberFrame, none), 0);
		Assert.assertEquals(1000*0.27, i.getWallThickness(F, England, TimberFrame, none), 0);
		Assert.assertEquals(1000*0.27, i.getWallThickness(G, England, TimberFrame, none), 0);
		Assert.assertEquals(1000*0.27, i.getWallThickness(H, England, TimberFrame, none), 0);
		Assert.assertEquals(1000*0.30, i.getWallThickness(I, England, TimberFrame, none), 0);
		Assert.assertEquals(1000*0.30, i.getWallThickness(J, England, TimberFrame, none), 0);
		Assert.assertEquals(1000*0.30, i.getWallThickness(K, England, TimberFrame, none), 0);

		Assert.assertEquals(1000*0.25, i.getWallThickness(A, England, SystemBuild, none), 0);
		Assert.assertEquals(1000*0.25, i.getWallThickness(B, England, SystemBuild, none), 0);
		Assert.assertEquals(1000*0.25, i.getWallThickness(C, England, SystemBuild, none), 0);
		Assert.assertEquals(1000*0.25, i.getWallThickness(D, England, SystemBuild, none), 0);
		Assert.assertEquals(1000*0.25, i.getWallThickness(E, England, SystemBuild, none), 0);
		Assert.assertEquals(1000*0.30, i.getWallThickness(F, England, SystemBuild, none), 0);
		Assert.assertEquals(1000*0.30, i.getWallThickness(G, England, SystemBuild, none), 0);
		Assert.assertEquals(1000*0.30, i.getWallThickness(H, England, SystemBuild, none), 0);
		Assert.assertEquals(1000*0.30, i.getWallThickness(I, England, SystemBuild, none), 0);
		Assert.assertEquals(1000*0.30, i.getWallThickness(J, England, SystemBuild, none), 0);
		Assert.assertEquals(1000*0.30, i.getWallThickness(K, England, SystemBuild, none), 0);

		Assert.assertEquals(1000*0.10, i.getWallThickness(B, England, MetalFrame, none), 0);
		Assert.assertEquals(1000*0.10, i.getWallThickness(C, England, MetalFrame, none), 0);
		Assert.assertEquals(1000*0.20, i.getWallThickness(D, England, MetalFrame, none), 0);
		Assert.assertEquals(1000*0.22, i.getWallThickness(E, England, MetalFrame, none), 0);
		Assert.assertEquals(1000*0.22, i.getWallThickness(F, England, MetalFrame, none), 0);
		Assert.assertEquals(1000*0.22, i.getWallThickness(G, England, MetalFrame, none), 0);
		Assert.assertEquals(1000*0.22, i.getWallThickness(H, England, MetalFrame, none), 0);
		Assert.assertEquals(1000*0.25, i.getWallThickness(I, England, MetalFrame, none), 0);
		Assert.assertEquals(1000*0.25, i.getWallThickness(J, England, MetalFrame, none), 0);
		Assert.assertEquals(1000*0.25, i.getWallThickness(K, England, MetalFrame, none), 0);
	}

	@Test
	public void InsulatedWallThicknessMatchThoseInExcelShee() {
		final IWallThicknessImputer i = imputationSchema.getWallPropertyTables().getWallThicknessImputer();

		final Set<WallInsulationType> ins = ImmutableSet.of(External);
		for (final WallConstructionType wc : new WallConstructionType[] {GraniteOrWhinstone, Sandstone}) {
			Assert.assertEquals(wc.toString(), 1000*0.57, i.getWallThickness(A, England, wc, ins), 0);
			Assert.assertEquals(wc.toString(), 1000*0.57, i.getWallThickness(B, England, wc, ins), 0);
			Assert.assertEquals(wc.toString(), 1000*0.57, i.getWallThickness(C, England, wc, ins), 0);
			Assert.assertEquals(wc.toString(), 1000*0.57, i.getWallThickness(D, England, wc, ins), 0);
			Assert.assertEquals(wc.toString(), 1000*0.52, i.getWallThickness(E, England, wc, ins), 0);
			Assert.assertEquals(wc.toString(), 1000*0.49, i.getWallThickness(F, England, wc, ins), 0);
			Assert.assertEquals(wc.toString(), 1000*0.49, i.getWallThickness(G, England, wc, ins), 0);
			Assert.assertEquals(wc.toString(), 1000*0.49, i.getWallThickness(H, England, wc, ins), 0);
			Assert.assertEquals(wc.toString(), 1000*0.52, i.getWallThickness(I, England, wc, ins), 0);
			Assert.assertEquals(wc.toString(), 1000*0.52, i.getWallThickness(J, England, wc, ins), 0);
			Assert.assertEquals(wc.toString(), 1000*0.52, i.getWallThickness(K, England, wc, ins), 0);
		}

		Assert.assertEquals(1000*0.29, i.getWallThickness(A, England, SolidBrick, ins), 0);
		Assert.assertEquals(1000*0.29, i.getWallThickness(B, England, SolidBrick, ins), 0);
		Assert.assertEquals(1000*0.29, i.getWallThickness(C, England, SolidBrick, ins), 0);
		Assert.assertEquals(1000*0.29, i.getWallThickness(D, England, SolidBrick, ins), 0);
		Assert.assertEquals(1000*0.31, i.getWallThickness(E, England, SolidBrick, ins), 0);
		Assert.assertEquals(1000*0.32, i.getWallThickness(F, England, SolidBrick, ins), 0);
		Assert.assertEquals(1000*0.34, i.getWallThickness(G, England, SolidBrick, ins), 0);
		Assert.assertEquals(1000*0.34, i.getWallThickness(H, England, SolidBrick, ins), 0);
		Assert.assertEquals(1000*0.37, i.getWallThickness(I, England, SolidBrick, ins), 0);
		Assert.assertEquals(1000*0.37, i.getWallThickness(J, England, SolidBrick, ins), 0);
		Assert.assertEquals(1000*0.37, i.getWallThickness(K, England, SolidBrick, ins), 0);

		Assert.assertEquals(1000*0.32, i.getWallThickness(A, England, SystemBuild, ins), 0);
		Assert.assertEquals(1000*0.32, i.getWallThickness(B, England, SystemBuild, ins), 0);
		Assert.assertEquals(1000*0.32, i.getWallThickness(C, England, SystemBuild, ins), 0);
		Assert.assertEquals(1000*0.32, i.getWallThickness(D, England, SystemBuild, ins), 0);
		Assert.assertEquals(1000*0.32, i.getWallThickness(E, England, SystemBuild, ins), 0);
		Assert.assertEquals(1000*0.37, i.getWallThickness(F, England, SystemBuild, ins), 0);
		Assert.assertEquals(1000*0.37, i.getWallThickness(G, England, SystemBuild, ins), 0);
		Assert.assertEquals(1000*0.37, i.getWallThickness(H, England, SystemBuild, ins), 0);
		Assert.assertEquals(1000*0.37, i.getWallThickness(I, England, SystemBuild, ins), 0);
		Assert.assertEquals(1000*0.37, i.getWallThickness(J, England, SystemBuild, ins), 0);
		Assert.assertEquals(1000*0.37, i.getWallThickness(K, England, SystemBuild, ins), 0);

		Assert.assertEquals(1000*0.59, i.getWallThickness(A, England, Cob, ins), 0);
		Assert.assertEquals(1000*0.59, i.getWallThickness(B, England, Cob, ins), 0);
		Assert.assertEquals(1000*0.59, i.getWallThickness(C, England, Cob, ins), 0);
		Assert.assertEquals(1000*0.59, i.getWallThickness(D, England, Cob, ins), 0);
		Assert.assertEquals(1000*0.59, i.getWallThickness(E, England, Cob, ins), 0);
		Assert.assertEquals(1000*0.59, i.getWallThickness(F, England, Cob, ins), 0);
		Assert.assertEquals(1000*0.61, i.getWallThickness(G, England, Cob, ins), 0);
		Assert.assertEquals(1000*0.61, i.getWallThickness(H, England, Cob, ins), 0);
		Assert.assertEquals(1000*0.64, i.getWallThickness(I, England, Cob, ins), 0);
		Assert.assertEquals(1000*0.64, i.getWallThickness(J, England, Cob, ins), 0);
		Assert.assertEquals(1000*0.64, i.getWallThickness(K, England, Cob, ins), 0);
	}

	@Test
	public void EnsureWallUValuesAreReadInCorrectly() throws Exception {
		final IWallUValueImputer imputer = imputationSchema.getWallPropertyTables().getWallUValueImputer();
		assertThat("imputer", imputer, is(notNullValue()));
	}

	@Test
	public void GraniteUValuesMustMatchThoseInExcelShee() {
		final IWallUValueImputer imputer = imputationSchema.getWallPropertyTables().getWallUValueImputer();

		final Map<WallInsulationType, Double> none = ImmutableMap.<WallInsulationType, Double>builder()
				.put(WallInsulationType.Internal, 0d)
				.put(WallInsulationType.External, 0d)
				.put(WallInsulationType.FilledCavity, 0d)
				.build();

		Assert.assertEquals(1.7, imputer.getUValue(E,England,GraniteOrWhinstone,none,0d), 0);
		Assert.assertEquals(0.45, imputer.getUValue(I,England,GraniteOrWhinstone,none,0d), 0);
		Assert.assertEquals(0.30, imputer.getUValue(K,England,GraniteOrWhinstone,none,0d), 0);
	}

	@Test
	public void SandstoneUValuesMustMatchThoseInExcelShee() {
		final IWallUValueImputer imputer = imputationSchema.getWallPropertyTables().getWallUValueImputer();

		final Map<WallInsulationType, Double> none = ImmutableMap.<WallInsulationType, Double>builder()
				.put(WallInsulationType.Internal, 0d)
				.put(WallInsulationType.External, 0d)
				.put(WallInsulationType.FilledCavity, 0d)
				.build();

		Assert.assertEquals(1.7, imputer.getUValue(E,England,Sandstone,none,0d), 0);
		Assert.assertEquals(0.6, imputer.getUValue(H,England,Sandstone,none,0d), 0);
		Assert.assertEquals(0.30, imputer.getUValue(K,England,Sandstone,none,0d), 0);
	}

	@Test
	public void SolidBrickUValuesMustMatchThoseInExcelSheet() {
		final IWallUValueImputer imputer = imputationSchema.getWallPropertyTables().getWallUValueImputer();

		final Map<WallInsulationType, Double> none = ImmutableMap.<WallInsulationType, Double>builder()
				.put(WallInsulationType.Internal, 0d)
				.put(WallInsulationType.External, 0d)
				.put(WallInsulationType.FilledCavity, 0d)
				.build();

		Assert.assertEquals(1.7, imputer.getUValue(E,England,SolidBrick,none,0d), 0);
		Assert.assertEquals(0.6, imputer.getUValue(H,England,SolidBrick,none,0d), 0);
		Assert.assertEquals(0.30, imputer.getUValue(K,England,SolidBrick,none,0d), 0);
	}

	@Test
	public void InsulatedBrickOrStoneUValuesMustMatchThoseInExcelShee() {
		final IWallUValueImputer imputer = imputationSchema.getWallPropertyTables().getWallUValueImputer();

		final Map<WallInsulationType, Double> none = ImmutableMap.<WallInsulationType, Double>builder()
				.put(WallInsulationType.Internal, 0d)
				.put(WallInsulationType.External, 50d)
				.put(WallInsulationType.FilledCavity, 0d)
				.build();

		Assert.assertEquals(0.55, imputer.getUValue(E, England, SolidBrick, none, 0d), 0);
		Assert.assertEquals(0.45, imputer.getUValue(F, England, SolidBrick, none, 0d), 0);
	}

	@Test
	public void CobUValuesMustMatchThoseInExcelSheet() {
		final IWallUValueImputer imputer = imputationSchema.getWallPropertyTables().getWallUValueImputer();
		final Map<WallInsulationType, Double> none = new TreeMap<>();

		none.put(WallInsulationType.Internal, 0d);
		none.put(WallInsulationType.External, 0d);
		none.put(WallInsulationType.FilledCavity, 0d);
		Assert.assertEquals(0.80, imputer.getUValue(A, England, Cob, none, 0d), 0);

		none.put(WallInsulationType.Internal, 50d);
		Assert.assertEquals(0.40, imputer.getUValue(B, England, Cob, none, 0d), 0);

		none.put(WallInsulationType.Internal, 100d);
		Assert.assertEquals(0.26, imputer.getUValue(C, England, Cob, none, 0d), 0);

		none.put(WallInsulationType.Internal, 150d);
		Assert.assertEquals(0.20, imputer.getUValue(D, England, Cob, none, 0d), 0);
	}

	@Test
	public void UnfilledCavityUValuesMustMatchThoseInExcelSheet() {
		final IWallUValueImputer imputer = imputationSchema.getWallPropertyTables().getWallUValueImputer();
		final Map<WallInsulationType, Double> none = new TreeMap<>();

		none.put(WallInsulationType.Internal, 0d);
		none.put(WallInsulationType.External, 0d);
		none.put(WallInsulationType.FilledCavity, 0d);

		//No insulation
		Assert.assertEquals(2.10, imputer.getUValue(A, England, Cavity, none, 0d), 0);
		//Add 50mm of internal insulation to unfilled cavity
		none.put(WallInsulationType.Internal, 50d);
		Assert.assertEquals(0.35, imputer.getUValue(G, England, Cavity, none, 0d), 0);
	}

	@Test
	public void FilledCavityUValuesMustMatchThoseInExcelSheet() {
		final IWallUValueImputer imputer = imputationSchema.getWallPropertyTables().getWallUValueImputer();
		final Map<WallInsulationType, Double> none = new TreeMap<>();

		none.put(WallInsulationType.Internal, 0d);
		none.put(WallInsulationType.External, 0d);
		none.put(WallInsulationType.FilledCavity, 50d);

		Assert.assertEquals(0.65, imputer.getUValue(A, England, Cavity, none, 0d), 0);

		none.put(WallInsulationType.Internal, 50d);
		Assert.assertEquals(0.31, imputer.getUValue(B, England, Cavity, none, 0d), 0);

		none.put(WallInsulationType.Internal, 100d);
		Assert.assertEquals(0.22, imputer.getUValue(C, England, Cavity, none, 0d), 0);

		none.put(WallInsulationType.Internal, 150d);
		Assert.assertEquals(0.17, imputer.getUValue(D, England, Cavity, none, 0d), 0);
	}

	@Test
	public void TimberFrameUValuesMustMatchThoseInExcelSheet() {
		final IWallUValueImputer imputer = imputationSchema.getWallPropertyTables().getWallUValueImputer();
		final Map<WallInsulationType, Double> none = new TreeMap<>();

		none.put(WallInsulationType.Internal, 0d);
		none.put(WallInsulationType.External, 0d);
		none.put(WallInsulationType.FilledCavity, 0d);

		Assert.assertEquals(2.5, imputer.getUValue(A, England, TimberFrame, none, 0d), 0);

		none.put(WallInsulationType.Internal, 50d);
		Assert.assertEquals(0.30, imputer.getUValue(K, England, Cavity, none, 0d), 0);
	}

	@Test
	public void SystemBuildUValuesMustMatchThoseInExcelSheet() {
		final IWallUValueImputer imputer = imputationSchema.getWallPropertyTables().getWallUValueImputer();
		final Map<WallInsulationType, Double> none = new TreeMap<>();

		none.put(WallInsulationType.Internal, 0d);
		none.put(WallInsulationType.External, 0d);
		none.put(WallInsulationType.FilledCavity, 0d);

		Assert.assertEquals(2.0, imputer.getUValue(A, England, SystemBuild, none, 0d), 0);

		none.put(WallInsulationType.Internal, 50d);
		Assert.assertEquals(0.60, imputer.getUValue(B, England, SystemBuild, none, 0d), 0);

		none.put(WallInsulationType.Internal, 100d);
		Assert.assertEquals(0.35, imputer.getUValue(C, England, SystemBuild, none, 0d), 0);

		none.put(WallInsulationType.Internal, 150d);
		Assert.assertEquals(0.14, imputer.getUValue(K, England, SystemBuild, none, 0d), 0);
	}

	@Test
	public void MetalFrameUValuesMustMatchThoseInExcelSheet() {
		final IWallUValueImputer imputer = imputationSchema.getWallPropertyTables().getWallUValueImputer();
		final Map<WallInsulationType, Double> none = new TreeMap<>();

		none.put(WallInsulationType.Internal, 0d);
		none.put(WallInsulationType.External, 0d);
		none.put(WallInsulationType.FilledCavity, 0d);

		Assert.assertEquals(2.2, imputer.getUValue(A, England, MetalFrame, none, 0d), 0);

		none.put(WallInsulationType.Internal, 50d);
		Assert.assertEquals(2.2, imputer.getUValue(A, England, MetalFrame, none, 0d), 0);

		Assert.assertEquals(0.86, imputer.getUValue(E, England, MetalFrame, none, 0d), 0);
		Assert.assertEquals(0.30, imputer.getUValue(K, England, MetalFrame, none, 0d), 0);
	}
}
