package uk.org.cse.stockimport.imputation.loookupbuilders.excel;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
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

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType;
import uk.org.cse.nhm.hom.components.fabric.types.WallInsulationType;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;
import uk.org.cse.stockimport.imputation.ImputationSchema;
import uk.org.cse.stockimport.imputation.lookupbuilders.excel.WallPropertyTablesBuilder;
import uk.org.cse.stockimport.imputation.walls.IWallInfiltrationImputer;
import uk.org.cse.stockimport.imputation.walls.IWallKValueImputer;
import uk.org.cse.stockimport.imputation.walls.IWallThicknessImputer;
import uk.org.cse.stockimport.imputation.walls.IWallUValueImputer;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

public class CreationOfWallImputationDataFromExcelTest extends AbsImputationFromExcelTest {

    private final ImputationSchema imputationSchema = new ImputationSchema("123123");
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
                    Assert.assertEquals(0.35, infiltrationImputer.getAirChangeRate(wct));
                    break;
                case TimberFrame:
                case MetalFrame:
                    Assert.assertEquals(0.25, infiltrationImputer.getAirChangeRate(wct));
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
        for (final WallConstructionType wc : new WallConstructionType[]{GraniteOrWhinstone, Sandstone}) {
            for (final Band band : new SAPAgeBandValue.Band[]{A, B, C, D}) {
                Assert.assertEquals("Band " + band, 500d, i.getWallThickness(band, London, wc, none));
            }
            Assert.assertEquals(450d, i.getWallThickness(E, London, wc, none));
            for (final Band band : new SAPAgeBandValue.Band[]{F, G, H}) {
                Assert.assertEquals("Band " + band, 420d, i.getWallThickness(band, London, wc, none));
            }
            for (final Band band : new SAPAgeBandValue.Band[]{I, J, K}) {
                Assert.assertEquals("Band " + band, 450d, i.getWallThickness(band, London, wc, none));
            }
        }

        // solid brick
        for (final SAPAgeBandValue.Band band : new SAPAgeBandValue.Band[]{A, B, C, D}) {
            Assert.assertEquals("Band " + band, 220d, i.getWallThickness(band, London, SolidBrick, none));
        }

        Assert.assertEquals(240d, i.getWallThickness(E, London, SolidBrick, none));
        Assert.assertEquals(250d, i.getWallThickness(F, London, SolidBrick, none));
        Assert.assertEquals(270d, i.getWallThickness(G, London, SolidBrick, none));
        Assert.assertEquals(270d, i.getWallThickness(H, London, SolidBrick, none));
        Assert.assertEquals(300d, i.getWallThickness(I, London, SolidBrick, none));
        Assert.assertEquals(300d, i.getWallThickness(J, London, SolidBrick, none));
        Assert.assertEquals(300d, i.getWallThickness(K, London, SolidBrick, none));

        Assert.assertEquals(1000 * 0.54, i.getWallThickness(A, London, Cob, none));
        Assert.assertEquals(1000 * 0.54, i.getWallThickness(B, London, Cob, none));
        Assert.assertEquals(1000 * 0.54, i.getWallThickness(C, London, Cob, none));
        Assert.assertEquals(1000 * 0.54, i.getWallThickness(D, London, Cob, none));
        Assert.assertEquals(1000 * 0.54, i.getWallThickness(E, London, Cob, none));
        Assert.assertEquals(1000 * 0.54, i.getWallThickness(F, London, Cob, none));
        Assert.assertEquals(1000 * 0.56, i.getWallThickness(G, London, Cob, none));
        Assert.assertEquals(1000 * 0.56, i.getWallThickness(H, London, Cob, none));
        Assert.assertEquals(1000 * 0.59, i.getWallThickness(I, London, Cob, none));
        Assert.assertEquals(1000 * 0.59, i.getWallThickness(J, London, Cob, none));
        Assert.assertEquals(1000 * 0.59, i.getWallThickness(K, London, Cob, none));

        Assert.assertEquals(1000 * 0.25, i.getWallThickness(A, London, Cavity, none));
        Assert.assertEquals(1000 * 0.25, i.getWallThickness(B, London, Cavity, none));
        Assert.assertEquals(1000 * 0.25, i.getWallThickness(C, London, Cavity, none));
        Assert.assertEquals(1000 * 0.25, i.getWallThickness(D, London, Cavity, none));
        Assert.assertEquals(1000 * 0.25, i.getWallThickness(E, London, Cavity, none));
        Assert.assertEquals(1000 * 0.26, i.getWallThickness(F, London, Cavity, none));
        Assert.assertEquals(1000 * 0.27, i.getWallThickness(G, London, Cavity, none));
        Assert.assertEquals(1000 * 0.27, i.getWallThickness(H, London, Cavity, none));
        Assert.assertEquals(1000 * 0.30, i.getWallThickness(I, London, Cavity, none));
        Assert.assertEquals(1000 * 0.30, i.getWallThickness(J, London, Cavity, none));
        Assert.assertEquals(1000 * 0.30, i.getWallThickness(K, London, Cavity, none));

        Assert.assertEquals(1000 * 0.15, i.getWallThickness(A, London, TimberFrame, none));
        Assert.assertEquals(1000 * 0.15, i.getWallThickness(B, London, TimberFrame, none));
        Assert.assertEquals(1000 * 0.15, i.getWallThickness(C, London, TimberFrame, none));
        Assert.assertEquals(1000 * 0.25, i.getWallThickness(D, London, TimberFrame, none));
        Assert.assertEquals(1000 * 0.27, i.getWallThickness(E, London, TimberFrame, none));
        Assert.assertEquals(1000 * 0.27, i.getWallThickness(F, London, TimberFrame, none));
        Assert.assertEquals(1000 * 0.27, i.getWallThickness(G, London, TimberFrame, none));
        Assert.assertEquals(1000 * 0.27, i.getWallThickness(H, London, TimberFrame, none));
        Assert.assertEquals(1000 * 0.30, i.getWallThickness(I, London, TimberFrame, none));
        Assert.assertEquals(1000 * 0.30, i.getWallThickness(J, London, TimberFrame, none));
        Assert.assertEquals(1000 * 0.30, i.getWallThickness(K, London, TimberFrame, none));

        Assert.assertEquals(1000 * 0.25, i.getWallThickness(A, London, SystemBuild, none));
        Assert.assertEquals(1000 * 0.25, i.getWallThickness(B, London, SystemBuild, none));
        Assert.assertEquals(1000 * 0.25, i.getWallThickness(C, London, SystemBuild, none));
        Assert.assertEquals(1000 * 0.25, i.getWallThickness(D, London, SystemBuild, none));
        Assert.assertEquals(1000 * 0.25, i.getWallThickness(E, London, SystemBuild, none));
        Assert.assertEquals(1000 * 0.30, i.getWallThickness(F, London, SystemBuild, none));
        Assert.assertEquals(1000 * 0.30, i.getWallThickness(G, London, SystemBuild, none));
        Assert.assertEquals(1000 * 0.30, i.getWallThickness(H, London, SystemBuild, none));
        Assert.assertEquals(1000 * 0.30, i.getWallThickness(I, London, SystemBuild, none));
        Assert.assertEquals(1000 * 0.30, i.getWallThickness(J, London, SystemBuild, none));
        Assert.assertEquals(1000 * 0.30, i.getWallThickness(K, London, SystemBuild, none));

        Assert.assertEquals(1000 * 0.10, i.getWallThickness(A, London, MetalFrame, none));
        Assert.assertEquals(1000 * 0.10, i.getWallThickness(B, London, MetalFrame, none));
        Assert.assertEquals(1000 * 0.10, i.getWallThickness(C, London, MetalFrame, none));
        Assert.assertEquals(1000 * 0.20, i.getWallThickness(D, London, MetalFrame, none));
        Assert.assertEquals(1000 * 0.22, i.getWallThickness(E, London, MetalFrame, none));
        Assert.assertEquals(1000 * 0.22, i.getWallThickness(F, London, MetalFrame, none));
        Assert.assertEquals(1000 * 0.22, i.getWallThickness(G, London, MetalFrame, none));
        Assert.assertEquals(1000 * 0.22, i.getWallThickness(H, London, MetalFrame, none));
        Assert.assertEquals(1000 * 0.25, i.getWallThickness(I, London, MetalFrame, none));
        Assert.assertEquals(1000 * 0.25, i.getWallThickness(J, London, MetalFrame, none));
        Assert.assertEquals(1000 * 0.25, i.getWallThickness(K, London, MetalFrame, none));
    }

    @Test
    public void InsulatedWallThicknessMatchThoseInExcelShee() {
        final IWallThicknessImputer i = imputationSchema.getWallPropertyTables().getWallThicknessImputer();

        final Set<WallInsulationType> ins = ImmutableSet.of(External);
        for (final WallConstructionType wc : new WallConstructionType[]{GraniteOrWhinstone, Sandstone}) {
            Assert.assertEquals(wc.toString(), 1000 * 0.57, i.getWallThickness(A, London, wc, ins));
            Assert.assertEquals(wc.toString(), 1000 * 0.57, i.getWallThickness(B, London, wc, ins));
            Assert.assertEquals(wc.toString(), 1000 * 0.57, i.getWallThickness(C, London, wc, ins));
            Assert.assertEquals(wc.toString(), 1000 * 0.57, i.getWallThickness(D, London, wc, ins));
            Assert.assertEquals(wc.toString(), 1000 * 0.52, i.getWallThickness(E, London, wc, ins));
            Assert.assertEquals(wc.toString(), 1000 * 0.49, i.getWallThickness(F, London, wc, ins));
            Assert.assertEquals(wc.toString(), 1000 * 0.49, i.getWallThickness(G, London, wc, ins));
            Assert.assertEquals(wc.toString(), 1000 * 0.49, i.getWallThickness(H, London, wc, ins));
            Assert.assertEquals(wc.toString(), 1000 * 0.52, i.getWallThickness(I, London, wc, ins));
            Assert.assertEquals(wc.toString(), 1000 * 0.52, i.getWallThickness(J, London, wc, ins));
            Assert.assertEquals(wc.toString(), 1000 * 0.52, i.getWallThickness(K, London, wc, ins));
        }

        Assert.assertEquals(1000 * 0.29, i.getWallThickness(A, London, SolidBrick, ins));
        Assert.assertEquals(1000 * 0.29, i.getWallThickness(B, London, SolidBrick, ins));
        Assert.assertEquals(1000 * 0.29, i.getWallThickness(C, London, SolidBrick, ins));
        Assert.assertEquals(1000 * 0.29, i.getWallThickness(D, London, SolidBrick, ins));
        Assert.assertEquals(1000 * 0.31, i.getWallThickness(E, London, SolidBrick, ins));
        Assert.assertEquals(1000 * 0.32, i.getWallThickness(F, London, SolidBrick, ins));
        Assert.assertEquals(1000 * 0.34, i.getWallThickness(G, London, SolidBrick, ins));
        Assert.assertEquals(1000 * 0.34, i.getWallThickness(H, London, SolidBrick, ins));
        Assert.assertEquals(1000 * 0.37, i.getWallThickness(I, London, SolidBrick, ins));
        Assert.assertEquals(1000 * 0.37, i.getWallThickness(J, London, SolidBrick, ins));
        Assert.assertEquals(1000 * 0.37, i.getWallThickness(K, London, SolidBrick, ins));

        Assert.assertEquals(1000 * 0.32, i.getWallThickness(A, London, SystemBuild, ins));
        Assert.assertEquals(1000 * 0.32, i.getWallThickness(B, London, SystemBuild, ins));
        Assert.assertEquals(1000 * 0.32, i.getWallThickness(C, London, SystemBuild, ins));
        Assert.assertEquals(1000 * 0.32, i.getWallThickness(D, London, SystemBuild, ins));
        Assert.assertEquals(1000 * 0.32, i.getWallThickness(E, London, SystemBuild, ins));
        Assert.assertEquals(1000 * 0.37, i.getWallThickness(F, London, SystemBuild, ins));
        Assert.assertEquals(1000 * 0.37, i.getWallThickness(G, London, SystemBuild, ins));
        Assert.assertEquals(1000 * 0.37, i.getWallThickness(H, London, SystemBuild, ins));
        Assert.assertEquals(1000 * 0.37, i.getWallThickness(I, London, SystemBuild, ins));
        Assert.assertEquals(1000 * 0.37, i.getWallThickness(J, London, SystemBuild, ins));
        Assert.assertEquals(1000 * 0.37, i.getWallThickness(K, London, SystemBuild, ins));

        Assert.assertEquals(1000 * 0.59, i.getWallThickness(A, London, Cob, ins));
        Assert.assertEquals(1000 * 0.59, i.getWallThickness(B, London, Cob, ins));
        Assert.assertEquals(1000 * 0.59, i.getWallThickness(C, London, Cob, ins));
        Assert.assertEquals(1000 * 0.59, i.getWallThickness(D, London, Cob, ins));
        Assert.assertEquals(1000 * 0.59, i.getWallThickness(E, London, Cob, ins));
        Assert.assertEquals(1000 * 0.59, i.getWallThickness(F, London, Cob, ins));
        Assert.assertEquals(1000 * 0.61, i.getWallThickness(G, London, Cob, ins));
        Assert.assertEquals(1000 * 0.61, i.getWallThickness(H, London, Cob, ins));
        Assert.assertEquals(1000 * 0.64, i.getWallThickness(I, London, Cob, ins));
        Assert.assertEquals(1000 * 0.64, i.getWallThickness(J, London, Cob, ins));
        Assert.assertEquals(1000 * 0.64, i.getWallThickness(K, London, Cob, ins));
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

        Assert.assertEquals(1.7, imputer.getUValue(E, London, GraniteOrWhinstone, none, 0d));
        Assert.assertEquals(0.45, imputer.getUValue(I, London, GraniteOrWhinstone, none, 0d));
        Assert.assertEquals(0.30, imputer.getUValue(K, London, GraniteOrWhinstone, none, 0d));
    }

    @Test
    public void SandstoneUValuesMustMatchThoseInExcelShee() {
        final IWallUValueImputer imputer = imputationSchema.getWallPropertyTables().getWallUValueImputer();

        final Map<WallInsulationType, Double> none = ImmutableMap.<WallInsulationType, Double>builder()
                .put(WallInsulationType.Internal, 0d)
                .put(WallInsulationType.External, 0d)
                .put(WallInsulationType.FilledCavity, 0d)
                .build();

        Assert.assertEquals(1.7, imputer.getUValue(E, London, Sandstone, none, 0d));
        Assert.assertEquals(0.6, imputer.getUValue(H, London, Sandstone, none, 0d));
        Assert.assertEquals(0.30, imputer.getUValue(K, London, Sandstone, none, 0d));
    }

    @Test
    public void SolidBrickUValuesMustMatchThoseInExcelSheet() {
        final IWallUValueImputer imputer = imputationSchema.getWallPropertyTables().getWallUValueImputer();

        final Map<WallInsulationType, Double> none = ImmutableMap.<WallInsulationType, Double>builder()
                .put(WallInsulationType.Internal, 0d)
                .put(WallInsulationType.External, 0d)
                .put(WallInsulationType.FilledCavity, 0d)
                .build();

        Assert.assertEquals(1.7, imputer.getUValue(E, London, SolidBrick, none, 0d));
        Assert.assertEquals(0.6, imputer.getUValue(H, London, SolidBrick, none, 0d));
        Assert.assertEquals(0.30, imputer.getUValue(K, London, SolidBrick, none, 0d));
    }

    @Test
    public void InsulatedBrickOrStoneUValuesMustMatchThoseInExcelShee() {
        final IWallUValueImputer imputer = imputationSchema.getWallPropertyTables().getWallUValueImputer();

        final Map<WallInsulationType, Double> none = ImmutableMap.<WallInsulationType, Double>builder()
                .put(WallInsulationType.Internal, 0d)
                .put(WallInsulationType.External, 50d)
                .put(WallInsulationType.FilledCavity, 0d)
                .build();

        Assert.assertEquals(0.55, imputer.getUValue(E, London, SolidBrick, none, 0d));
        Assert.assertEquals(0.45, imputer.getUValue(F, London, SolidBrick, none, 0d));
    }

    @Test
    public void CobUValuesMustMatchThoseInExcelSheet() {
        final IWallUValueImputer imputer = imputationSchema.getWallPropertyTables().getWallUValueImputer();
        final Map<WallInsulationType, Double> none = new TreeMap<>();

        none.put(WallInsulationType.Internal, 0d);
        none.put(WallInsulationType.External, 0d);
        none.put(WallInsulationType.FilledCavity, 0d);
        Assert.assertEquals(0.80, imputer.getUValue(A, London, Cob, none, 0d));

        none.put(WallInsulationType.Internal, 50d);
        Assert.assertEquals(0.40, imputer.getUValue(B, London, Cob, none, 0d));

        none.put(WallInsulationType.Internal, 100d);
        Assert.assertEquals(0.26, imputer.getUValue(C, London, Cob, none, 0d));

        none.put(WallInsulationType.Internal, 150d);
        Assert.assertEquals(0.20, imputer.getUValue(D, London, Cob, none, 0d));
    }

    @Test
    public void UnfilledCavityUValuesMustMatchThoseInExcelSheet() {
        final IWallUValueImputer imputer = imputationSchema.getWallPropertyTables().getWallUValueImputer();
        final Map<WallInsulationType, Double> none = new TreeMap<>();

        none.put(WallInsulationType.Internal, 0d);
        none.put(WallInsulationType.External, 0d);
        none.put(WallInsulationType.FilledCavity, 0d);

        //No insulation
        Assert.assertEquals(2.10, imputer.getUValue(A, London, Cavity, none, 0d));
        //Add 50mm of internal insulation to unfilled cavity
        none.put(WallInsulationType.Internal, 50d);
        Assert.assertEquals(0.35, imputer.getUValue(G, London, Cavity, none, 0d));
    }

    @Test
    public void FilledCavityUValuesMustMatchThoseInExcelSheet() {
        final IWallUValueImputer imputer = imputationSchema.getWallPropertyTables().getWallUValueImputer();
        final Map<WallInsulationType, Double> none = new TreeMap<>();

        none.put(WallInsulationType.Internal, 0d);
        none.put(WallInsulationType.External, 0d);
        none.put(WallInsulationType.FilledCavity, 50d);

        Assert.assertEquals(0.65, imputer.getUValue(A, London, Cavity, none, 0d));

        none.put(WallInsulationType.Internal, 50d);
        Assert.assertEquals(0.31, imputer.getUValue(B, London, Cavity, none, 0d));

        none.put(WallInsulationType.Internal, 100d);
        Assert.assertEquals(0.22, imputer.getUValue(C, London, Cavity, none, 0d));

        none.put(WallInsulationType.Internal, 150d);
        Assert.assertEquals(0.17, imputer.getUValue(D, London, Cavity, none, 0d));
    }

    @Test
    public void TimberFrameUValuesMustMatchThoseInExcelSheet() {
        final IWallUValueImputer imputer = imputationSchema.getWallPropertyTables().getWallUValueImputer();
        final Map<WallInsulationType, Double> none = new TreeMap<>();

        none.put(WallInsulationType.Internal, 0d);
        none.put(WallInsulationType.External, 0d);
        none.put(WallInsulationType.FilledCavity, 0d);

        Assert.assertEquals(2.5, imputer.getUValue(A, London, TimberFrame, none, 0d));

        none.put(WallInsulationType.Internal, 50d);
        Assert.assertEquals(0.30, imputer.getUValue(K, London, Cavity, none, 0d));
    }

    @Test
    public void SystemBuildUValuesMustMatchThoseInExcelSheet() {
        final IWallUValueImputer imputer = imputationSchema.getWallPropertyTables().getWallUValueImputer();
        final Map<WallInsulationType, Double> none = new TreeMap<>();

        none.put(WallInsulationType.Internal, 0d);
        none.put(WallInsulationType.External, 0d);
        none.put(WallInsulationType.FilledCavity, 0d);

        Assert.assertEquals(2.0, imputer.getUValue(A, London, SystemBuild, none, 0d));

        none.put(WallInsulationType.Internal, 50d);
        Assert.assertEquals(0.60, imputer.getUValue(B, London, SystemBuild, none, 0d));

        none.put(WallInsulationType.Internal, 100d);
        Assert.assertEquals(0.35, imputer.getUValue(C, London, SystemBuild, none, 0d));

        none.put(WallInsulationType.Internal, 150d);
        Assert.assertEquals(0.14, imputer.getUValue(K, London, SystemBuild, none, 0d));
    }

    @Test
    public void MetalFrameUValuesMustMatchThoseInExcelSheet() {
        final IWallUValueImputer imputer = imputationSchema.getWallPropertyTables().getWallUValueImputer();
        final Map<WallInsulationType, Double> none = new TreeMap<>();

        none.put(WallInsulationType.Internal, 0d);
        none.put(WallInsulationType.External, 0d);
        none.put(WallInsulationType.FilledCavity, 0d);

        Assert.assertEquals(2.2, imputer.getUValue(A, London, MetalFrame, none, 0d));

        none.put(WallInsulationType.Internal, 50d);
        Assert.assertEquals(2.2, imputer.getUValue(A, London, MetalFrame, none, 0d));

        Assert.assertEquals(0.86, imputer.getUValue(E, London, MetalFrame, none, 0d));
        Assert.assertEquals(0.30, imputer.getUValue(K, London, MetalFrame, none, 0d));
    }

    @Test
    public void EnsureWallKValueTablesAreReadFromExcelSheet() throws Exception {
        final IWallKValueImputer imputer = imputationSchema.getWallPropertyTables().getWallKValueImputer();
        assertThat("imputer", imputer, is(notNullValue()));
    }

    @Test
    public void KValueForAsBuiltWallsMustMatchThoseInExcelSheet() throws Exception {
        final IWallKValueImputer imputer = imputationSchema.getWallPropertyTables().getWallKValueImputer();

        Assert.assertEquals(202d, imputer.getKValue(GraniteOrWhinstone, ImmutableSet.<WallInsulationType>of()));
        Assert.assertEquals(156d, imputer.getKValue(Sandstone, ImmutableSet.<WallInsulationType>of()));
        Assert.assertEquals(135d, imputer.getKValue(SolidBrick, ImmutableSet.<WallInsulationType>of()));
        Assert.assertEquals(148d, imputer.getKValue(Cob, ImmutableSet.<WallInsulationType>of()));
        Assert.assertEquals(139d, imputer.getKValue(Cavity, ImmutableSet.<WallInsulationType>of()));
        Assert.assertEquals(9d, imputer.getKValue(TimberFrame, ImmutableSet.<WallInsulationType>of()));
        Assert.assertEquals(211d, imputer.getKValue(SystemBuild, ImmutableSet.<WallInsulationType>of()));
        Assert.assertEquals(14d, imputer.getKValue(MetalFrame, ImmutableSet.<WallInsulationType>of()));
    }

    @Test
    public void KValuesForInsulatedSolidBrickWallsMustMatchThoseInExcelSheet() throws Exception {
        final IWallKValueImputer imputer = imputationSchema.getWallPropertyTables().getWallKValueImputer();

        Assert.assertEquals(17d, imputer.getKValue(SolidBrick, ImmutableSet.of(Internal)));
        Assert.assertEquals(135d, imputer.getKValue(SolidBrick, ImmutableSet.of(External)));

        Assert.assertEquals(66d, imputer.getKValue(Cob, ImmutableSet.of(Internal)));
        Assert.assertEquals(148d, imputer.getKValue(Cob, ImmutableSet.of(External)));

        Assert.assertEquals(10d, imputer.getKValue(SystemBuild, ImmutableSet.of(Internal)));
        Assert.assertEquals(211d, imputer.getKValue(SystemBuild, ImmutableSet.of(External)));

    }
}
