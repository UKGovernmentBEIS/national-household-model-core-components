package uk.org.cse.stockimport.imputation.walls;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType;
import uk.org.cse.nhm.hom.components.fabric.types.WallInsulationType;
import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;

/**
 * An RD-Sap wall property imputer. Currently only works for england.
 *
 * @author hinton
 * @since 1.0
 */
public class WallUValueImputer implements IWallUValueImputer {

    private static final Logger log = LoggerFactory.getLogger(WallUValueImputer.class);

    // the following tables are the rows from SAP table S6, in roughly the same order.
    /**
     * This is the "tail-end" of the first row of table S6, where the values are
     * constants rather than equations, starting at SAP age band E
     */
    private double[] uninsulatedGranite;

    /**
     * @param constructionType
     * @param ageBandValue
     * @param insulationThickness - for unfilled cavity assume greater than zero
     * is insulated
     * @param uValue
     * @param filledCavity - applicable only if it's a cavity
     * @since 3.0
     */
    public void addWallUValue(final WallConstructionType constructionType, final Band ageBandValue,
            final double insulationThickness, final double uValue, final boolean filledCavity) {
        switch (constructionType) {
            case Sandstone:
                uninsulatedSandstone[ageBandValue.ordinal()] = uValue;
                break;
            case GraniteOrWhinstone:
                uninsulatedGranite[ageBandValue.ordinal()] = uValue;
                break;
            case SolidBrick:
                solidBrickInsulated[insulationRow(insulationThickness)][ageBandValue.ordinal()] = uValue;
                break;
            case Cob:
                cobInsulated[insulationRow(insulationThickness)][ageBandValue.ordinal()] = uValue;
                break;
            case Cavity:
                if (filledCavity) {
                    filledCavityInsulated[insulationRow(insulationThickness)][ageBandValue.ordinal()] = uValue;
                } else {
                    //assume that if insulation thickness is great than zero then it's insulated
                    final int insulatedRow = (insulationThickness > 0 ? 1 : 0);
                    unfilledCavityInsulated[insulatedRow][ageBandValue.ordinal()] = uValue;
                }
                break;
            case TimberFrame:
                final int insulatedRow = (insulationThickness > 0 ? 1 : 0);
                timberFrameInsulated[insulatedRow][ageBandValue.ordinal()] = uValue;
                break;
            case SystemBuild:
                systemBuildInsulated[insulationRow(insulationThickness)][ageBandValue.ordinal()] = uValue;
                break;
            case MetalFrame:
                metalFrameInsulated[0][ageBandValue.ordinal()] = uValue;
                break;
            default:
                break;
        }
    }

    protected int insulationRow(final double insulationThickness) {
        int row = 0;

        if (insulationThickness > 49) {
            row = 1;
            if (insulationThickness > 99) {
                row = 2;
                if (insulationThickness > 149) {
                    row = 3;
                }
            }
        }

        return row;
    }

    /**
     * This is the "tail-end" of the second row of table S6, where the values
     * are constants rather than equations, starting at SAP age band E
     */
    private double[] uninsulatedSandstone;

    /**
     * U values for solid brick walls with 0, 50, 100, and 150mm of insulation
     */
    private double[][] solidBrickInsulated;

    /**
     * U values for cob with 0, 50, 100, and 150mm of insulation.
     */
    private double[][] cobInsulated;

    /**
     * Values for insulated or uninsulated filled cavity wall Because RDSAP
     * specifies (in the annotations to table S6) that unfilled wall with
     * internal or external insulation is to be treated as filled cavity wall
     * for the purposes of the SAP calculation, the second row of this is the
     * same as the first row of {@link #filledCavityInsulated}.
     *
     * @assumption Using 0.65 for U-values of early insulated + unfilled
     * cavities, as per CHM. CHM note is that this is at DECC's request.
     */
    private double[][] unfilledCavityInsulated;

    /**
     * U values for filled cavity walls with 0, 50, 100 or 150 mm of insulation
     *
     * @assumption Using 0.65 for U-values of early insulated + filled cavities,
     * as per CHM. CHM note is that this is at DECC's request.
     */
    private double[][] filledCavityInsulated;

    /**
     * U values for timber frame with 0 or > 0 mm of insulation
     */
    private double[][] timberFrameInsulated;

    /**
     * U values for system build walls with 0, 50mm, 100mm, and 150mm of
     * insulation
     */
    private double[][] systemBuildInsulated;

    /**
     * The constant 0.002 in RDSAP equation S5.1.1 U = 3.0 - 0.002 * wall
     * thickness in mm
     */
    private double sandstoneCoefficient;

    /**
     * The constant 3 in RDSAP equation S5.1.1 U = 3.0 - 0.002 * wall thickness
     * in mm
     */
    private double sandstoneConstant;

    /**
     * The constant 3.3 in RDSAP equation S5.1.1 U = 3.3 - 0.002 * wall
     * thickness in mm
     */
    private double graniteConstant;

    /**
     * The constant 0.002 in RDSAP equation S5.1.1 U = 3.3 - 0.002 * wall
     * thickness in mm
     */
    private double graniteCoefficient;

    /**
     * @assumption U values for metal frame insulated walls are taken directly
     * from the CHM spreadsheet, because RDSAP doesn't have any data for them.
     */
    private final double[][] metalFrameInsulated = new double[][]{
        {2.20, 2.20, 2.20, 0.86, 0.86, 0.53, 0.53, 0.53, 0.45, 0.35, 0.30}
    };

    public WallUValueImputer() {
        this(true);
    }

    public WallUValueImputer(final boolean useRdSapDefaults) {
        if (useRdSapDefaults) {
            uninsulatedGranite = new double[]{1.70, 1.00, 0.60, 0.60, 0.45, 0.35, 0.30};

            uninsulatedSandstone = new double[]{1.70, 1.00, 0.60, 0.60, 0.45, 0.35, 0.30};

            solidBrickInsulated = new double[][]{
                {2.10, 2.10, 2.10, 2.10, 1.70, 1.00, 0.60, 0.60, 0.45, 0.35, 0.30},
                {0.60, 0.60, 0.60, 0.60, 0.55, 0.45, 0.35, 0.35, 0.30, 0.25, 0.21},
                {0.35, 0.35, 0.35, 0.35, 0.35, 0.32, 0.24, 0.24, 0.21, 0.19, 0.17},
                {0.25, 0.25, 0.25, 0.25, 0.25, 0.21, 0.18, 0.18, 0.17, 0.15, 0.14}
            };

            cobInsulated = new double[][]{
                {0.80, 0.80, 0.80, 0.80, 0.80, 0.80, 0.60, 0.60, 0.45, 0.35, 0.30},
                {0.40, 0.40, 0.40, 0.40, 0.40, 0.40, 0.35, 0.35, 0.30, 0.25, 0.21},
                {0.26, 0.26, 0.26, 0.26, 0.26, 0.26, 0.26, 0.26, 0.26, 0.25, 0.21},
                {0.20, 0.20, 0.20, 0.20, 0.20, 0.20, 0.20, 0.20, 0.20, 0.20, 0.20},};

            unfilledCavityInsulated = new double[][]{
                {2.10, 1.60, 1.60, 1.60, 1.60, 1.00, 0.60, 0.60, 0.45, 0.35, 0.30},
                {0.65, 0.65, 0.65, 0.65, 0.65, 0.65, 0.35, 0.35, 0.45, 0.35, 0.30}
            };

            filledCavityInsulated = new double[][]{
                {0.65, 0.65, 0.65, 0.65, 0.65, 0.65, 0.35, 0.35, 0.45, 0.35, 0.30},
                {0.31, 0.31, 0.31, 0.31, 0.31, 0.27, 0.25, 0.25, 0.25, 0.25, 0.25},
                {0.22, 0.22, 0.22, 0.22, 0.22, 0.20, 0.19, 0.19, 0.19, 0.19, 0.19},
                {0.17, 0.17, 0.17, 0.17, 0.17, 0.16, 0.15, 0.15, 0.15, 0.15, 0.15}
            };

            timberFrameInsulated = new double[][]{
                {2.50, 1.90, 1.90, 1.00, 0.80, 0.45, 0.40, 0.40, 0.40, 0.35, 0.30},
                {0.60, 0.55, 0.55, 0.40, 0.40, 0.40, 0.40, 0.40, 0.40, 0.35, 0.30}
            };

            systemBuildInsulated = new double[][]{
                {2.00, 2.00, 2.00, 2.00, 1.70, 1.00, 0.60, 0.60, 0.45, 0.35, 0.30},
                {0.60, 0.60, 0.60, 0.60, 0.55, 0.45, 0.35, 0.35, 0.30, 0.25, 0.21},
                {0.35, 0.35, 0.35, 0.35, 0.35, 0.32, 0.24, 0.24, 0.21, 0.19, 0.17},
                {0.25, 0.25, 0.25, 0.25, 0.25, 0.21, 0.18, 0.18, 0.17, 0.15, 0.14}
            };

            sandstoneCoefficient = 0.002;
            sandstoneConstant = 3.0;
            graniteConstant = 3.3;
            graniteCoefficient = 0.002;
        } else {
            uninsulatedGranite = new double[7];
            uninsulatedSandstone = new double[7];
            solidBrickInsulated = new double[4][11];
            cobInsulated = new double[4][11];
            unfilledCavityInsulated = new double[2][11];
            filledCavityInsulated = new double[4][11];
            timberFrameInsulated = new double[4][11];
            systemBuildInsulated = new double[4][11];
        }
    }

    /* (non-Javadoc)
	 * @see uk.org.cse.stockimport.imputation.rdsap.walls.IWallUValueImputer#getUValue(uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue, uk.org.cse.nhm.energycalculator.api.types.RegionType, uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType, java.util.Map, double)
     */
    @Override
    public double getUValue(
            final SAPAgeBandValue.Band ageBand,
            final RegionType region,
            final WallConstructionType constructionType,
            final Map<WallInsulationType, Double> insulationThicknesses,
            final double wallThickness) {
        log.debug("getUValue({}, {}, {}, {}, {})", new Object[]{ageBand, region, constructionType, insulationThicknesses, wallThickness});
        final double result;

        if (constructionType == null) {
            log.error("U value requested with null wall construction type");
            result = 0;
        } else {
            switch (constructionType.getWallType()) {
                case External:
                    // get sap age bands, and do interpolation
                    result = getExternalWallUValue(ageBand, region, constructionType, insulationThicknesses, wallThickness);
                    break;
                case Internal:
                    log.warn("U value requested for internal wall, which could be a mistake");
                case Party:
                    result = 0;
                    break;
                default:
                    result = 0;
                    log.error("U value requested for wall construction type {}, which has unknown wall type {}", constructionType, constructionType.getWallType());
                    break;
            }
        }
        return result;
    }

    /**
     * This implements the top two rows of Table S6, which have equations in
     * them.
     *
     * @param construction
     * @param age
     * @param thickness
     * @return
     */
    private double calculateStoneWallUValue(final WallConstructionType construction, final SAPAgeBandValue.Band age, final double thickness) {
        if (age.ordinal() < SAPAgeBandValue.Band.E.ordinal()) {
            if (construction == WallConstructionType.GraniteOrWhinstone) {
                return graniteConstant - graniteCoefficient * thickness;
            } else if (construction == WallConstructionType.Sandstone) {
                return sandstoneConstant - sandstoneCoefficient * thickness;
            } else {
                log.error("stone wall u-value requested for wall with construction type {} - this should never happen", construction);
                return 0;
            }
        } else if (age.ordinal() == SAPAgeBandValue.Band.E.ordinal()) {
            if (construction == WallConstructionType.GraniteOrWhinstone) {
                return Math.min(graniteConstant - graniteCoefficient * thickness,
                        uninsulatedGranite[age.ordinal() - SAPAgeBandValue.Band.E.ordinal()]);
            } else if (construction == WallConstructionType.Sandstone) {
                return Math.min(sandstoneConstant - sandstoneCoefficient * thickness,
                        uninsulatedSandstone[age.ordinal() - SAPAgeBandValue.Band.E.ordinal()]);
            } else {
                log.error("stone wall u-value requested for wall with construction type {} - this should never happen", construction);
                return 0;
            }
        } else {
            if (construction == WallConstructionType.GraniteOrWhinstone) {
                return uninsulatedGranite[age.ordinal() - SAPAgeBandValue.Band.E.ordinal()];
            } else if (construction == WallConstructionType.Sandstone) {
                return uninsulatedSandstone[age.ordinal() - SAPAgeBandValue.Band.E.ordinal()];
            } else {
                log.error("stone wall u-value requested for wall with construction type {} - this should never happen", construction);
                return 0;
            }
        }

    }

    /**
     * Get a u-value for an external wall.
     *
     * @param earliestBuild
     * @param latestBuild
     * @param region
     * @param constructionType
     * @param insulationThickness
     * @param wallThickness
     * @return
     */
    private double getExternalWallUValue(final SAPAgeBandValue.Band sapAgeBand, final RegionType region,
            final WallConstructionType constructionType,
            final Map<WallInsulationType, Double> insulationThicknesses,
            final double wallThickness) {
        log.debug("getExternalWallUValue({}, {}, {}, {}, {})",
                new Object[]{sapAgeBand, region, constructionType, insulationThicknesses, wallThickness});
        final double result;

        final double nonCavityThickness = insulationThicknesses.get(WallInsulationType.External)
                + insulationThicknesses.get(WallInsulationType.Internal);

        final double[][] lookup;

        if (constructionType != WallConstructionType.Cavity && insulationThicknesses.get(WallInsulationType.FilledCavity) > 0) {
            log.warn("Wall insulation and construction types are not compatible: {} vs {}", constructionType, insulationThicknesses);
        }

        switch (constructionType) {
            case GraniteOrWhinstone:
            case Sandstone:
                if (nonCavityThickness == 0) {
                    // uninsulated granite or whitstone has a special behaviour which is not a lookup at all.
                    return calculateStoneWallUValue(constructionType, sapAgeBand, wallThickness);
                } else {
                    // the values for insulated stone are the same as for insulated solid brick.
                    lookup = solidBrickInsulated;
                }
                break;
            case SolidBrick:
                lookup = solidBrickInsulated;
                break;
            case Cob:
                lookup = cobInsulated;
                break;
            case Cavity:
                // cavity requires special treatment
                if (insulationThicknesses.get(WallInsulationType.FilledCavity) == 0) {
                    lookup = unfilledCavityInsulated;
                } else {
                    // there is an RD SAP rule for insulated filled cavities.
                    lookup = filledCavityInsulated;
                }
                break;
            case MetalFrame:
                lookup = metalFrameInsulated;
                break;
            case TimberFrame:
                lookup = timberFrameInsulated;
                break;
            case SystemBuild:
                lookup = systemBuildInsulated;
                break;
            default:
                log.error("Tried to calculate external wall u value for non-external wall construction type {}", constructionType);
                return 0;
        }

        if (nonCavityThickness == 0) {
            result = lookup[0][sapAgeBand.ordinal()];
        } else {
            final int rounded = Math.min((int) Math.round(nonCavityThickness / 50d),
                    lookup.length - 1);
            if (rounded == 0 && lookup.length > 1) {
                result = lookup[1][sapAgeBand.ordinal()];
            } else {
                result = lookup[rounded][sapAgeBand.ordinal()];
            }
        }

        return result;
    }

    public void setSandstoneCoefficient(final double sandstoneCoefficient) {
        this.sandstoneCoefficient = sandstoneCoefficient;
    }

    public void setSandstoneConstant(final double sandstoneConstant) {
        this.sandstoneConstant = sandstoneConstant;
    }

    public void setGraniteConstant(final double graniteConstant) {
        this.graniteConstant = graniteConstant;
    }

    public void setGraniteCoefficient(final double graniteCoefficient) {
        this.graniteCoefficient = graniteCoefficient;
    }

}
