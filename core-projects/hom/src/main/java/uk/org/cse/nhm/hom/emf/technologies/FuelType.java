/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

import uk.org.cse.nhm.energycalculator.api.types.EnergyType;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Fuel Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getFuelType()
 * @model
 * @generated
 */
public enum FuelType implements Enumerator {
	/**
	 * The '<em><b>Mains Gas</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MAINS_GAS_VALUE
	 * @generated
	 * @ordered
	 */
	MAINS_GAS(0, "MainsGas", "MAINS_GAS"),

	/**
	 * The '<em><b>Bulk LPG</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BULK_LPG_VALUE
	 * @generated
	 * @ordered
	 */
	BULK_LPG(1, "BulkLPG", "BULK_LPG"),

	/**
	 * The '<em><b>Bottled LPG</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BOTTLED_LPG_VALUE
	 * @generated
	 * @ordered
	 */
	BOTTLED_LPG(2, "BottledLPG", "BOTTLED_LPG"),

	/**
	 * The '<em><b>Electricity</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ELECTRICITY_VALUE
	 * @generated
	 * @ordered
	 */
	ELECTRICITY(3, "Electricity", "ELECTRICITY"),

	/**
	 * The '<em><b>Oil</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OIL_VALUE
	 * @generated
	 * @ordered
	 */
	OIL(4, "Oil", "OIL"),

	/**
	 * The '<em><b>Biomass Pellets</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BIOMASS_PELLETS_VALUE
	 * @generated
	 * @ordered
	 */
	BIOMASS_PELLETS(5, "BiomassPellets", "BIOMASS_PELLETS"),

	/**
	 * The '<em><b>Biomass Woodchip</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BIOMASS_WOODCHIP_VALUE
	 * @generated
	 * @ordered
	 */
	BIOMASS_WOODCHIP(6, "BiomassWoodchip", "BIOMASS_WOODCHIP"),

	/**
	 * The '<em><b>Biomass Wood</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BIOMASS_WOOD_VALUE
	 * @generated
	 * @ordered
	 */
	BIOMASS_WOOD(7, "BiomassWood", "BIOMASS_WOOD"),

	/**
	 * The '<em><b>Photons</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PHOTONS_VALUE
	 * @generated
	 * @ordered
	 */
	PHOTONS(8, "Photons", "PHOTONS"), /**
	 * The '<em><b>House Coal</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #HOUSE_COAL_VALUE
	 * @generated
	 * @ordered
	 */
	HOUSE_COAL(9, "HouseCoal", "HOUSE_COAL"), /**
	 * The '<em><b>Community Heat</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #COMMUNITY_HEAT_VALUE
	 * @generated
	 * @ordered
	 */
	COMMUNITY_HEAT(10, "CommunityHeat", "COMMUNITY_HEAT"), /**
	 * The '<em><b>Peak Electricity</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * 	You should <b>not</b> directly set this as the fueltype for any system that consumes electricity.
	 * This is only here for accounting purposes. To specify a device as electrical, use {@link #ELECTRICITY} instead.
	 * <!-- end-user-doc -->
	 * @see #PEAK_ELECTRICITY_VALUE
	 * @generated
	 * @ordered
	 */
	PEAK_ELECTRICITY(11, "PeakElectricity", "PEAK_ELECTRICITY"), /**
	 * The '<em><b>Off Peak Electricity</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * 	You should <b>not</b> directly set this as the fueltype for any system that consumes electricity.
	 * This is only here for accounting purposes. To specify a device as electrical, use {@link #ELECTRICITY} instead.
	 * <!-- end-user-doc -->
	 * @see #OFF_PEAK_ELECTRICITY_VALUE
	 * @generated
	 * @ordered
	 */
	OFF_PEAK_ELECTRICITY(12, "OffPeakElectricity", "OFF_PEAK_ELECTRICITY"), /**
	 * The '<em><b>Exported Electricity</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXPORTED_ELECTRICITY_VALUE
	 * @generated
	 * @ordered
	 */
	EXPORTED_ELECTRICITY(13, "ExportedElectricity", "EXPORTED_ELECTRICITY");

	/**
	 * The '<em><b>Mains Gas</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Mains Gas</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MAINS_GAS
	 * @model name="MainsGas" literal="MAINS_GAS"
	 * @generated
	 * @ordered
	 */
	public static final int MAINS_GAS_VALUE = 0;

	/**
	 * The '<em><b>Bulk LPG</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Bulk LPG</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BULK_LPG
	 * @model name="BulkLPG" literal="BULK_LPG"
	 * @generated
	 * @ordered
	 */
	public static final int BULK_LPG_VALUE = 1;

	/**
	 * The '<em><b>Bottled LPG</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Bottled LPG</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BOTTLED_LPG
	 * @model name="BottledLPG" literal="BOTTLED_LPG"
	 * @generated
	 * @ordered
	 */
	public static final int BOTTLED_LPG_VALUE = 2;

	/**
	 * The '<em><b>Electricity</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Electricity</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ELECTRICITY
	 * @model name="Electricity" literal="ELECTRICITY"
	 * @generated
	 * @ordered
	 */
	public static final int ELECTRICITY_VALUE = 3;

	/**
	 * The '<em><b>Oil</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Oil</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #OIL
	 * @model name="Oil" literal="OIL"
	 * @generated
	 * @ordered
	 */
	public static final int OIL_VALUE = 4;

	/**
	 * The '<em><b>Biomass Pellets</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Biomass Pellets</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BIOMASS_PELLETS
	 * @model name="BiomassPellets" literal="BIOMASS_PELLETS"
	 * @generated
	 * @ordered
	 */
	public static final int BIOMASS_PELLETS_VALUE = 5;

	/**
	 * The '<em><b>Biomass Woodchip</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Biomass Woodchip</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BIOMASS_WOODCHIP
	 * @model name="BiomassWoodchip" literal="BIOMASS_WOODCHIP"
	 * @generated
	 * @ordered
	 */
	public static final int BIOMASS_WOODCHIP_VALUE = 6;

	/**
	 * The '<em><b>Biomass Wood</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Biomass Wood</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BIOMASS_WOOD
	 * @model name="BiomassWood" literal="BIOMASS_WOOD"
	 * @generated
	 * @ordered
	 */
	public static final int BIOMASS_WOOD_VALUE = 7;

	/**
	 * The '<em><b>Photons</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Photons</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PHOTONS
	 * @model name="Photons" literal="PHOTONS"
	 * @generated
	 * @ordered
	 */
	public static final int PHOTONS_VALUE = 8;

	/**
	 * The '<em><b>House Coal</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>House Coal</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #HOUSE_COAL
	 * @model name="HouseCoal" literal="HOUSE_COAL"
	 * @generated
	 * @ordered
	 */
	public static final int HOUSE_COAL_VALUE = 9;

	/**
	 * The '<em><b>Community Heat</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Community Heat</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #COMMUNITY_HEAT
	 * @model name="CommunityHeat" literal="COMMUNITY_HEAT"
	 * @generated
	 * @ordered
	 */
	public static final int COMMUNITY_HEAT_VALUE = 10;

	/**
	 * The '<em><b>Peak Electricity</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Peak Electricity</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PEAK_ELECTRICITY
	 * @model name="PeakElectricity" literal="PEAK_ELECTRICITY"
	 * @generated
	 * @ordered
	 */
	public static final int PEAK_ELECTRICITY_VALUE = 11;

	/**
	 * The '<em><b>Off Peak Electricity</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Off Peak Electricity</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #OFF_PEAK_ELECTRICITY
	 * @model name="OffPeakElectricity" literal="OFF_PEAK_ELECTRICITY"
	 * @generated
	 * @ordered
	 */
	public static final int OFF_PEAK_ELECTRICITY_VALUE = 12;

	/**
	 * The '<em><b>Exported Electricity</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Exported Electricity</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #EXPORTED_ELECTRICITY
	 * @model name="ExportedElectricity" literal="EXPORTED_ELECTRICITY"
	 * @generated
	 * @ordered
	 */
	public static final int EXPORTED_ELECTRICITY_VALUE = 13;

	/**
	 * An array of all the '<em><b>Fuel Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final FuelType[] VALUES_ARRAY =
		new FuelType[] {
			MAINS_GAS,
			BULK_LPG,
			BOTTLED_LPG,
			ELECTRICITY,
			OIL,
			BIOMASS_PELLETS,
			BIOMASS_WOODCHIP,
			BIOMASS_WOOD,
			PHOTONS,
			HOUSE_COAL,
			COMMUNITY_HEAT,
			PEAK_ELECTRICITY,
			OFF_PEAK_ELECTRICITY,
			EXPORTED_ELECTRICITY,
		};

	/**
	 * A public read-only list of all the '<em><b>Fuel Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<FuelType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Fuel Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static FuelType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			FuelType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Fuel Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static FuelType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			FuelType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Fuel Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static FuelType get(int value) {
		switch (value) {
			case MAINS_GAS_VALUE: return MAINS_GAS;
			case BULK_LPG_VALUE: return BULK_LPG;
			case BOTTLED_LPG_VALUE: return BOTTLED_LPG;
			case ELECTRICITY_VALUE: return ELECTRICITY;
			case OIL_VALUE: return OIL;
			case BIOMASS_PELLETS_VALUE: return BIOMASS_PELLETS;
			case BIOMASS_WOODCHIP_VALUE: return BIOMASS_WOODCHIP;
			case BIOMASS_WOOD_VALUE: return BIOMASS_WOOD;
			case PHOTONS_VALUE: return PHOTONS;
			case HOUSE_COAL_VALUE: return HOUSE_COAL;
			case COMMUNITY_HEAT_VALUE: return COMMUNITY_HEAT;
			case PEAK_ELECTRICITY_VALUE: return PEAK_ELECTRICITY;
			case OFF_PEAK_ELECTRICITY_VALUE: return OFF_PEAK_ELECTRICITY;
			case EXPORTED_ELECTRICITY_VALUE: return EXPORTED_ELECTRICITY;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private FuelType(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

	/**
	 * @return the {@link EnergyType} corresponding to this fuel type in a community system.
	 * The distinction is required so that we can account for CO2 emissions of community systems
	 * but allow the household to pay for the heat they are supplied directly.
	 */
	public EnergyType getCommunityEnergyType() {
		switch (this) {
		case BIOMASS_PELLETS:
		case BIOMASS_WOOD:
		case BIOMASS_WOODCHIP:
			return EnergyType.CommunityBIOMASS;
		case BOTTLED_LPG:
		case BULK_LPG:
		case MAINS_GAS:
			return EnergyType.CommunityGAS;
		case ELECTRICITY:
			return EnergyType.CommunityELECTRICITY;
		case OIL:
			return EnergyType.CommunityOIL;
		default:
			throw new RuntimeException("Cannot get community energy type for fuel type " + this);
		}
	}
	
	/**
	 * @return the {@link EnergyType} corresponding to this {@link FuelType}, for use in the energy calculation.
	 */
	public EnergyType getEnergyType() {
		switch (this) {
		case MAINS_GAS:
			return EnergyType.FuelGAS;
		case BIOMASS_PELLETS:
			return EnergyType.FuelBIOMASS_PELLETS;
		case BIOMASS_WOOD:
			return EnergyType.FuelBIOMASS_WOOD;
		case BIOMASS_WOODCHIP:
			return EnergyType.FuelBIOMASS_WOODCHIP;
		case BOTTLED_LPG:
			return EnergyType.FuelBOTTLED_LPG;
		case BULK_LPG:
			return EnergyType.FuelBULK_LPG;
		case ELECTRICITY:
			break;
		case OFF_PEAK_ELECTRICITY:
			return EnergyType.FuelOFFPEAK_ELECTRICITY;
		case PEAK_ELECTRICITY:
			return EnergyType.FuelPEAK_ELECTRICITY;
		case OIL:
			return EnergyType.FuelHEATING_OIL;
		case PHOTONS:
			break;
		case HOUSE_COAL:
			return EnergyType.FuelSOLID_FUEL;
		case COMMUNITY_HEAT:
			return EnergyType.FuelCOMMUNITY_HEAT;
		case EXPORTED_ELECTRICITY:
			return EnergyType.GenerationExportedElectricity;
		default:
			break;
		}
		throw new RuntimeException("Cannot get energy type for fuel type " + this);
	}

	public boolean isGas() {
		return this == MAINS_GAS || this == BOTTLED_LPG || this == BULK_LPG;
	}

	public static FuelType of(final EnergyType et) {
		if (et == EnergyType.FuelGAS) {
			return MAINS_GAS;
		} else if (et == EnergyType.FuelBIOMASS_PELLETS) {
			return BIOMASS_PELLETS;
		} else if (et == EnergyType.FuelBIOMASS_WOOD) {
			return BIOMASS_WOOD;
		} else if (et == EnergyType.FuelBIOMASS_WOODCHIP) {
			return BIOMASS_WOODCHIP;
		} else if (et == EnergyType.FuelBOTTLED_LPG) {
			return BOTTLED_LPG;
		} else if (et == EnergyType.FuelBULK_LPG) {
			return BULK_LPG;
		} else if (et == EnergyType.FuelCOMMUNITY_HEAT) {
			return COMMUNITY_HEAT;
		} else if (et == EnergyType.FuelHEATING_OIL) {
			return OIL;
		} else if (et == EnergyType.FuelSOLID_FUEL) {
			return HOUSE_COAL;
		} else if (et == EnergyType.FuelPEAK_ELECTRICITY) {
			return PEAK_ELECTRICITY;
		} else if (et == EnergyType.FuelOFFPEAK_ELECTRICITY) {
			return OFF_PEAK_ELECTRICITY;
		} else if (et == EnergyType.FuelPHOTONS) {
			return FuelType.PHOTONS;
		} else if (et == EnergyType.GenerationExportedElectricity) {
			return FuelType.EXPORTED_ELECTRICITY;
		} else {
			throw new RuntimeException("Cannot get fuel type for energy type " + et);
		}
	}	
} //FuelType
