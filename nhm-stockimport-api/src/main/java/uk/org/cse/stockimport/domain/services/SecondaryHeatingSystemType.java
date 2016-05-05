package uk.org.cse.stockimport.domain.services;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType;

/**
 * Based on the Secondary Heating System lookup table from the 2010 Cambridge Housing Model spreadsheet, Phyiscs Parameters worksheet.
 * 
 * To this we have added:
 * 	LPG heater since we have a separate fuel type for this.
 * 	Open flue gas fire as we think it is better to use this data rather than to throw it away. 
 * 
 * @author glenns
 * @since 1.0
 */
public enum SecondaryHeatingSystemType {
    NO_SECONDARY_SYSTEM,
    NOT_KNOWN,
    LPG_HEATER(FuelType.BOTTLED_LPG),
    GAS_FIRE(FuelType.MAINS_GAS, FlueType.BALANCED_FLUE),
    GAS_FIRE_FLUELESS(FuelType.MAINS_GAS, FlueType.NOT_APPLICABLE),
    GAS_FIRE_OPEN_FLUE(FuelType.MAINS_GAS, FlueType.OPEN_FLUE),
    GAS_COAL_EFFECT_FIRE(FuelType.MAINS_GAS, FlueType.BALANCED_FLUE),
    ELECTRIC_ROOM_HEATERS(FuelType.ELECTRICITY),
    OPEN_FIRE(FuelType.MAINS_GAS, FlueType.CHIMNEY);
    
    private SecondaryHeatingSystemType() {
    	this(null, null);
    }
    
    private SecondaryHeatingSystemType(final FuelType fuelType) {
    	this(fuelType, FlueType.NOT_APPLICABLE);
    }
    
    private SecondaryHeatingSystemType(FuelType fuelType, FlueType flueType) {
    	this.fuelType = fuelType;
    	this.flueType = flueType;
    }
    
    private final FuelType fuelType;
    private final FlueType flueType;
    /**
     * @since 1.0
     */
    public FuelType getFuelType() {
		return fuelType;
	}

    /**
     * @since 1.0
     */
    public FlueType getFlueType() {
		return flueType;
	}
	
}
