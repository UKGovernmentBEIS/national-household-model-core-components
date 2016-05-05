package uk.org.cse.stockimport.ehcs2010.spss.elementreader;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType;
import uk.org.cse.stockimport.sedbuk.tables.BoilerType;

public class BoilerMatchInterface {
	public static FuelType nhmFromBoilerMatch(final uk.org.cse.boilermatcher.types.FuelType a) {
        if (a == null) return null;
		switch (a) {
		case BULK_LPG:return FuelType.BULK_LPG;
		case MAINS_GAS:return FuelType.MAINS_GAS;
		case OIL: return FuelType.OIL;
		default: return FuelType.MAINS_GAS;
		}
	}
	
	public static FlueType nhmFromBoilerMatch(final uk.org.cse.boilermatcher.types.FlueType a) {
        if (a == null) return null;
		switch (a) {
		case BALANCED_FLUE: return FlueType.BALANCED_FLUE;
		case FAN_ASSISTED_BALANCED_FLUE: return FlueType.FAN_ASSISTED_BALANCED_FLUE;
		case OPEN_FLUE:return FlueType.OPEN_FLUE;
		default:return FlueType.FAN_ASSISTED_BALANCED_FLUE;		
		}
	}
	
	public static BoilerType nhmFromBoilerMatch(final uk.org.cse.boilermatcher.types.BoilerType a) {
        if (a == null) return null;
		switch (a) {
		case CPSU: return BoilerType.CPSU;
		case INSTANT_COMBI: return BoilerType.INSTANT_COMBI;
		case REGULAR: return BoilerType.REGULAR;
		case STORAGE_COMBI: return BoilerType.STORAGE_COMBI;
		case UNKNOWN: return BoilerType.UNKNOWN;
		default: return BoilerType.REGULAR;
		}
	}

	public static uk.org.cse.boilermatcher.types.FuelType nhmToBoilerMatch(final FuelType a) {
        if (a == null) return null;
		switch (a) {
		case BULK_LPG: return uk.org.cse.boilermatcher.types.FuelType.BULK_LPG;
		case MAINS_GAS: return uk.org.cse.boilermatcher.types.FuelType.MAINS_GAS;
		case OIL: return uk.org.cse.boilermatcher.types.FuelType.OIL;
		default: return null;
		}
	}
	
	public static uk.org.cse.boilermatcher.types.FlueType nhmToBoilerMatch(final FlueType a) {
        if (a == null) return null;
		switch (a) {
		case BALANCED_FLUE: return uk.org.cse.boilermatcher.types.FlueType.BALANCED_FLUE;
		case FAN_ASSISTED_BALANCED_FLUE: return uk.org.cse.boilermatcher.types.FlueType.FAN_ASSISTED_BALANCED_FLUE;
		case OPEN_FLUE: return uk.org.cse.boilermatcher.types.FlueType.OPEN_FLUE;
		default: return null;
		}
	}
	
	public static uk.org.cse.boilermatcher.types.BoilerType nhmToBoilerMatch(final BoilerType a) {
        if (a == null) return null;
		switch (a) {
		case CPSU: return uk.org.cse.boilermatcher.types.BoilerType.CPSU;
		case INSTANT_COMBI: return uk.org.cse.boilermatcher.types.BoilerType.INSTANT_COMBI;
		case REGULAR: return uk.org.cse.boilermatcher.types.BoilerType.REGULAR;
		case STORAGE_COMBI: return uk.org.cse.boilermatcher.types.BoilerType.STORAGE_COMBI;
		case UNKNOWN: return uk.org.cse.boilermatcher.types.BoilerType.UNKNOWN;
		default: return null;
		}
	}
}
