package uk.org.cse.stockimport.domain.services;

import static uk.org.cse.stockimport.domain.services.BasicSpaceHeatingSystemType.BOILER;
import static uk.org.cse.stockimport.domain.services.BasicSpaceHeatingSystemType.COMMUNITY;
import static uk.org.cse.stockimport.domain.services.BasicSpaceHeatingSystemType.HEAT_PUMP;
import static uk.org.cse.stockimport.domain.services.BasicSpaceHeatingSystemType.WARM_AIR_SYSTEM;
import static uk.org.cse.stockimport.sedbuk.tables.BoilerType.INSTANT_COMBI;
import static uk.org.cse.stockimport.sedbuk.tables.BoilerType.REGULAR;
import static uk.org.cse.stockimport.sedbuk.tables.BoilerType.UNKNOWN;

import java.util.Arrays;
import java.util.Collection;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;

import uk.org.cse.stockimport.sedbuk.tables.BoilerType;

/**
 * A representation of Table 5 in the CHM conversion document, detailing the types of system
 * which the CHM understands.
 * 
 * This is a combination of {@link BasicSpaceHeatingSystemType} with some other attributes;
 * you can use {@link #getBasicType()} to get the associated {@link BasicSpaceHeatingSystemType}
 * 
 * @author hinton
 * @since 1.0
 */
public enum SpaceHeatingSystemType {
	STANDARD(BOILER, REGULAR),
	COMBI(BOILER, INSTANT_COMBI), 
	CPSU(BOILER, BoilerType.CPSU),
	STORAGE_COMBI(BOILER, BoilerType.STORAGE_COMBI),
	BACK_BOILER(BasicSpaceHeatingSystemType.BACK_BOILER, UNKNOWN),
	BACK_BOILER_NO_CENTRAL_HEATING(BasicSpaceHeatingSystemType.BACK_BOILER, UNKNOWN),
	
	STORAGE_HEATER(BasicSpaceHeatingSystemType.STORAGE_HEATER, UNKNOWN),
	ROOM_HEATER(BasicSpaceHeatingSystemType.ROOM_HEATER, UNKNOWN),
	WARM_AIR(WARM_AIR_SYSTEM, UNKNOWN),
	/**
	 * @assumption Community heating assumed to use mains gas as specified by CHM Converting English Housing Survey document.
	 */
	COMMUNITY_HEATING_WITHOUT_CHP(COMMUNITY, UNKNOWN),
	COMMUNITY_HEATING_WITH_CHP(COMMUNITY, UNKNOWN),
	/**
     * @assumption Biomass boiler assumed to use Biomass Pellets based on CHM spreadsheet worksheet:Building Physics Parameters, cell: I1012
	 */
	GROUND_SOURCE_HEAT_PUMP(HEAT_PUMP, UNKNOWN),
	AIR_SOURCE_HEAT_PUMP(HEAT_PUMP, UNKNOWN),
	
	MISSING(BasicSpaceHeatingSystemType.BOILER, UNKNOWN);
	
	private final BasicSpaceHeatingSystemType basicType;
	private final BoilerType boilerType;
	
	private SpaceHeatingSystemType(final BasicSpaceHeatingSystemType basicType, final BoilerType boilerType) {
		this.basicType = basicType;
		this.boilerType = boilerType;
	}
	
	/**
     * @since 1.0
     */
    public BasicSpaceHeatingSystemType getBasicType() {
		return basicType;
	}

    /**
     * @since 1.0
     */
    public BoilerType getBoilerType() {
		return boilerType;
	}
	
	/**
	 * Finds a SpaceHeatingSystemType which matches the given BoilerType and FuelType.
	 * Always returns Absent if the boiler type is unknown.
	 * There should never be multiple matches.
	 * 
	 * @param boilerType
	 * @throws Runtime exception if 0 or > 1 matches are found.
	 * @return
	 * @since 1.0
	 */
	public static Optional<SpaceHeatingSystemType> lookupHeatingTypeForBoiler(final BoilerType boilerType) {
		if(boilerType == UNKNOWN) {
			return Optional.absent();
		}
		
		Collection<SpaceHeatingSystemType> matching = Collections2.filter(Arrays.asList(SpaceHeatingSystemType.values()), new Predicate<SpaceHeatingSystemType>() {
			public boolean apply(SpaceHeatingSystemType applyTo) {
				return applyTo.boilerType == boilerType;
			}
		});
		if(matching.isEmpty()) {
			throw new RuntimeException("Could not find a boiler matching " + boilerType);
		}
		else if (matching.size() == 1) {
			return Optional.of(Iterables.get(matching, 0));
		}
		else {
			throw new RuntimeException("Found multiple possible boilers matching boiler " + boilerType);
		}
	}
}