package uk.org.cse.stockimport.imputation.apertures.doors;

import uk.org.cse.nhm.hom.components.fabric.types.DoorType;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;

/**
 * Tells the dto -> object model builder about the properties of doors
 * 
 * @author hinton
 * @since 1.0
 */
public interface IDoorPropertyImputer {
    /**
     * @since 1.0
     */
    public double getArea(final DoorType doorType);
    /**
     * @since 1.0
     */
    public double getUValue(Band ageBand, final DoorType doorType);
}
