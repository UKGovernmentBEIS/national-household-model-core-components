package uk.org.cse.stockimport.imputation.walls;

import java.util.Set;

import uk.org.cse.nhm.energycalculator.api.types.RegionType.Country;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;

/**
 * An implementation of Table S3 from SAP 2012.
 *
 * The values for walls including insulation thickness are missing from SAP
 * 2012, so they are taken from SAP 2009 instead.
 *
 * The metal frame thickness comes from the Cambridge Housing Model.
 *
 * @author hinton
 * @since 1.0
 */
public class WallThicknessImputer implements IWallThicknessImputer {
    // each of these tables gives the thickness for a certain construction type, with the first row in each
    // pair being the thickness without internal or external insulation, and the second being where internal or
    // external insulation is present

    /**
     * @assumption CHM does not have a row for insulated stone, but RDSAP does,
     * so we are using the RDSAP one.
     */
    private double[][] stone;
    private double[][] brick;

    private double[][] cavity;

    private double[][] timberFrame;

    private double[][] cob;

    private double[][] system;

    /**
     * @assumption Insulated metal frame walls get thicker with insulation like
     * timber ones do.
     */
    private double[][] metalFrame;

    public WallThicknessImputer() {
        this(true);
    }

    public WallThicknessImputer(final boolean useRdSapDefaults) {
        if (useRdSapDefaults) {
            //Initialise tables

            stone = new double[][]{
                {500, 500, 500, 500, 450, 420, 420, 420, 450, 450, 450, 450},
                {570, 570, 570, 570, 520, 490, 490, 490, 520, 520, 520, 520},};

            brick = new double[][]{
                {220, 220, 220, 220, 240, 250, 270, 270, 300, 300, 300, 300},
                {290, 290, 290, 290, 310, 320, 340, 340, 370, 370, 370, 370},};

            cavity = new double[][]{
                {250, 250, 250, 250, 250, 260, 270, 270, 300, 300, 300, 300},
                {300, 300, 300, 300, 300, 310, 320, 320, 330, 330, 330, 330}
            };

            timberFrame = new double[][]{
                {150, 150, 150, 250, 270, 270, 270, 270, 300, 300, 300, 300},
                {200, 200, 200, 290, 310, 270, 270, 270, 300, 300, 300, 300}
            };

            cob = new double[][]{
                {540, 540, 540, 540, 540, 540, 560, 560, 590, 590, 590, 590},
                {590, 590, 590, 590, 590, 590, 610, 610, 640, 640, 640, 640}
            };

            system = new double[][]{
                {250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300},
                {320, 320, 320, 320, 320, 370, 370, 370, 370, 370, 370, 370}
            };

            metalFrame = new double[][]{
                {100, 100, 100, 200, 220, 220, 220, 220, 250, 250, 250, 250},
                {150, 150, 150, 240, 260, 220, 220, 220, 250, 250, 250, 250}
            };
        } else {
            stone = new double[2][11];
            brick = new double[2][11];
            cavity = new double[2][11];
            timberFrame = new double[2][11];
            cob = new double[2][11];
            system = new double[2][11];
            metalFrame = new double[2][11];
        }
    }

    private static final SAPAgeBandValue.Band bigScotlandStoneAdjustment = Band.B;

    private double scotlandAdjustment(final double thicknessMM, final Country country, final WallConstructionType constructionType, final Band ageBand) {
        if (country == Country.Scotland) {
            switch (constructionType) {
                case GraniteOrWhinstone:
                case Sandstone:
                    // Footnote 1
                    if (ageBand.after(bigScotlandStoneAdjustment)) {
                        return thicknessMM + 100;
                    } else {
                        return thicknessMM + 200;
                    }

                case Cavity:
                    // Footnote 2
                    return thicknessMM + 50;
                default:
                    return thicknessMM;
            }
        } else {
            return thicknessMM;
        }
    }

    public void addWallThickness(final WallConstructionType constructionType, final boolean hasInsulation, final double thickness, final Band ageBandValue) {
        final int row = (hasInsulation ? 1 : 0);

        switch (constructionType) {
            case GraniteOrWhinstone:
            case Sandstone:
                stone[row][ageBandValue.ordinal()] = thickness;
                break;
            case Cavity:
                cavity[row][ageBandValue.ordinal()] = thickness;
                break;
            case Cob:
                cob[row][ageBandValue.ordinal()] = thickness;
                break;
            case SolidBrick:
                brick[row][ageBandValue.ordinal()] = thickness;
                break;
            case SystemBuild:
                system[row][ageBandValue.ordinal()] = thickness;
                break;
            case MetalFrame:
                metalFrame[row][ageBandValue.ordinal()] = thickness;
                break;
            case TimberFrame:
                timberFrame[row][ageBandValue.ordinal()] = thickness;
                break;
            default:
                break;
        }
    }

    /* (non-Javadoc)
	 * @see uk.org.cse.stockimport.imputation.tables.IWallThicknessImputer#getWallThickness(uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue, uk.org.cse.nhm.energycalculator.api.types.RegionType, uk.org.cse.nhm.energycalculator.api.types.WallConstructionType, java.util.Set)
     */
    @Override
    public double getWallThickness(final Band ageBand, final Country country, final WallConstructionType construction, final Set<WallInsulationType> insulations) {
        final double[][] lookup;

        switch (construction) {
            case Cavity:
                lookup = cavity;
                break;
            case Cob:
                lookup = cob;
                break;
            case GraniteOrWhinstone:
            case Sandstone:
                lookup = stone;
                break;
            case SolidBrick:
                lookup = brick;
                break;
            case SystemBuild:
                lookup = system;
                break;
            case MetalFrame:
                lookup = metalFrame;
                break;
            case TimberFrame:
                lookup = timberFrame;
                break;
            default:
//			log.error("RD SAP doesn't have a wall thickness lookup for wall type {}", construction);
                return 0;
        }

        final boolean hasNonCavityInsulation = insulations.contains(WallInsulationType.External) || insulations.contains(WallInsulationType.Internal);

        return scotlandAdjustment(
                lookup[hasNonCavityInsulation ? 1 : 0][ageBand.ordinal()],
                country,
                construction,
                ageBand);
    }
}
