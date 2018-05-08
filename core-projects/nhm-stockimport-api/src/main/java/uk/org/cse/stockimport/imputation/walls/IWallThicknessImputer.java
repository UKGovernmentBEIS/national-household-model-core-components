package uk.org.cse.stockimport.imputation.walls;

import java.util.Set;

import uk.org.cse.nhm.energycalculator.api.types.RegionType.Country;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;

public interface IWallThicknessImputer {

    /**
     * @since 1.0
     */
    public abstract double getWallThickness(Band ageBand,
            Country country, WallConstructionType construction,
            Set<WallInsulationType> insulations);

}
