package uk.org.cse.nhm.energycalculator.api.types;

import java.util.Collections;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

/**
 * Represents a kind of energy used in a heating system; this would be an enum,
 * were it not for the fact that some heating systems (for example boilers)
 * require an intermediate internal form of energy to work out their
 * efficiencies correctly.
 *
 * @since 1.0.0
 * @author hinton
 *
 */
public enum EnergyType {
    CommunityGAS("Com. Gas"),
    CommunityELECTRICITY("Com. Electricity"),
    CommunityOIL("Com. Oil"),
    CommunityBIOMASS("Com. Biomass"),
    FuelGAS("Gas"),
    FuelBULK_LPG("Bulk LPG"),
    FuelBOTTLED_LPG("Bottled LPG"),
    FuelPEAK_ELECTRICITY("Electricity"),
    FuelOFFPEAK_ELECTRICITY("Electricity (off-peak)"),
    FuelSOLID_FUEL("Solid Fuel"),
    FuelHEATING_OIL("Heating Oil"),
    FuelBIOMASS_WOODCHIP("Biomass (Woodchip)"),
    FuelBIOMASS_WOOD("Biomass (Wood)"),
    FuelBIOMASS_PELLETS("Biomass (Pellets)"),
    FuelPHOTONS("Photons"),
    FuelCOMMUNITY_HEAT("Community Heat"),
    HackMEAN_INTERNAL_TEMPERATURE("Mean Temp"),
    HackUNADJUSTED_TEMPERATURE("Unadj Temp"),
    DemandsHOT_WATER_VOLUME("Water (M3)"),
    DemandsHOT_WATER("Water (Heat)"),
    DemandsVISIBLE_LIGHT("Visible Light"),
    DemandsHEAT("Heat"),
    GainsAPPLIANCE_GAINS("Appliance Heat"),
    GainsMETABOLIC_GAINS("Metabolic Gains"),
    GainsLIGHTING_GAINS("Lighting Gains"),
    GainsCOOKING_GAINS("Cooking Gains"),
    GainsPUMP_AND_FAN_GAINS("Pump & Fan Gains"),
    GainsHOT_WATER_USAGE_GAINS("HW Gains (Usage)"),
    GainsHOT_WATER_SYSTEM_GAINS("HW Gains (System)"),
    GainsSOLAR_GAINS("Solar Gains"),
    GainsUSEFUL_GAINS("Useful Gains"),
    GenerationExportedElectricity("Exported Electricity"),
    FuelINTERNAL1("Internal 1"),
    FuelINTERNAL2("Internal 2"),
    FuelINTERNAL3("Internal 3");

    ;
	public static final Set<EnergyType> JUST_HEAT = Collections.singleton(DemandsHEAT);

    ;
	
	public static final EnergyType[] allFuels = new EnergyType[]{
        FuelGAS,
        FuelBULK_LPG,
        FuelPEAK_ELECTRICITY,
        FuelOFFPEAK_ELECTRICITY,
        FuelSOLID_FUEL,
        FuelBIOMASS_WOODCHIP,
        FuelPHOTONS,
        FuelCOMMUNITY_HEAT,
        FuelBOTTLED_LPG,
        FuelHEATING_OIL,
        FuelBIOMASS_PELLETS,
        FuelBIOMASS_WOOD,
        GenerationExportedElectricity
    };

    public static final EnergyType[] ELECTRICITY = new EnergyType[]{FuelPEAK_ELECTRICITY, FuelOFFPEAK_ELECTRICITY};
    public static final Set<EnergyType> ELECTRICITY_SET = ImmutableSet.of(FuelPEAK_ELECTRICITY, FuelOFFPEAK_ELECTRICITY);

    private final String name;
    public static final Set<EnergyType> NONE = Collections.emptySet();

    EnergyType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
