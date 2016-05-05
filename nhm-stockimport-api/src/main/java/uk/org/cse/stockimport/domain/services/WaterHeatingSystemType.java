package uk.org.cse.stockimport.domain.services;

/**
 * This describes all the water heating system types which the {@link IWaterHeatingDTO} represents.
 * 
 * @author hinton
 * @since 1.0
 */
public enum WaterHeatingSystemType {
	STANDARD_BOILER,
	AIR_SOURCE_HEAT_PUMP,
	GROUND_SOURCE_HEAT_PUMP,
	SINGLEPOINT,
	MULTIPOINT,
	WARM_AIR,
	COMMUNITY_CHP,
	COMMUNITY, 
	BACK_BOILER
}
