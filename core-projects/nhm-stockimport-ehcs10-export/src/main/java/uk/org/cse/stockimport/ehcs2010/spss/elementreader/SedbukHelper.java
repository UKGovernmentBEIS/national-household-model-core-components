package uk.org.cse.stockimport.ehcs2010.spss.elementreader;

import static uk.org.cse.nhm.ehcs10.physical.types.Enum1713.Combination;
import static uk.org.cse.nhm.ehcs10.physical.types.Enum1713.CombinedPrimaryStorageUnit;
import static uk.org.cse.nhm.ehcs10.physical.types.Enum1713.Condensing;
import static uk.org.cse.nhm.ehcs10.physical.types.Enum1713.CondensingCombi;
import static uk.org.cse.nhm.ehcs10.physical.types.Enum1713.Standard;
import static uk.org.cse.nhm.ehcs10.physical.types.Enum1777.*;
import static uk.org.cse.nhm.ehcs10.physical.types.Enum1779.Anthracite;
import static uk.org.cse.nhm.ehcs10.physical.types.Enum1779.BottledGas;
import static uk.org.cse.nhm.ehcs10.physical.types.Enum1779.BulkLPG;
import static uk.org.cse.nhm.ehcs10.physical.types.Enum1779.Coal;
import static uk.org.cse.nhm.ehcs10.physical.types.Enum1779.MainsGas;
import static uk.org.cse.nhm.ehcs10.physical.types.Enum1779.Oil;
import static uk.org.cse.nhm.ehcs10.physical.types.Enum1779.Smokeless;
import static uk.org.cse.nhm.ehcs10.physical.types.Enum1779.Wood;
import static uk.org.cse.nhm.hom.emf.technologies.FuelType.BIOMASS_WOOD;
import static uk.org.cse.nhm.hom.emf.technologies.FuelType.BOTTLED_LPG;
import static uk.org.cse.nhm.hom.emf.technologies.FuelType.BULK_LPG;
import static uk.org.cse.nhm.hom.emf.technologies.FuelType.ELECTRICITY;
import static uk.org.cse.nhm.hom.emf.technologies.FuelType.HOUSE_COAL;
import static uk.org.cse.nhm.hom.emf.technologies.FuelType.MAINS_GAS;
import static uk.org.cse.nhm.hom.emf.technologies.FuelType.OIL;
import static uk.org.cse.stockimport.sedbuk.tables.BoilerType.CPSU;
import static uk.org.cse.stockimport.sedbuk.tables.BoilerType.INSTANT_COMBI;
import static uk.org.cse.stockimport.sedbuk.tables.BoilerType.REGULAR;

import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

import uk.org.cse.boilermatcher.lucene.IBoilerTableEntry;
import uk.org.cse.boilermatcher.lucene.ISedbukIndex;
import uk.org.cse.boilermatcher.lucene.SedbukFix;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1713;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1777;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1779;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.stockimport.domain.services.BasicSpaceHeatingSystemType;
import uk.org.cse.stockimport.ehcs2010.spss.elementreader.lookup.EHCSPrimaryHeatingCode;
import uk.org.cse.stockimport.sedbuk.tables.BoilerType;

/**
 * A class containing the stuff required to do sedbuk.
 */
public class SedbukHelper {

    private final ISedbukIndex index;
    private final SedbukFix fixTable;

    private final Map<Enum1713, BoilerType> boilerGroupLookup = ImmutableMap.<Enum1713, BoilerType>builder()
            .put(Combination, INSTANT_COMBI).put(Standard, REGULAR)
            .put(CombinedPrimaryStorageUnit, CPSU).put(Condensing, BoilerType.REGULAR)
            .put(CondensingCombi, INSTANT_COMBI).build();

    public SedbukHelper(final SedbukFix fixTable, final ISedbukIndex index) {
        this.fixTable = fixTable;
        this.index = index;
    }

    /**
     * Maps boiler group to boiler type for use in Sedbuk lookup. MISSING or
     * unknown boiler groups returns null, so that they will not be considered
     * in the Sedbuk lookup. Backboiler group returns Unknown, so that its
     * Sedbuk lookup will fail.
     *
     * @param boilerGroup
     * @param cylinderVolume
     * @return
     * @since 1.0
     */
    public BoilerType getBoilerTypeFromBoilerGroup(final Enum1713 boilerGroup) {
        if (!boilerGroupLookup.containsKey(boilerGroup)) {
            return null;
        }
        return boilerGroupLookup.get(boilerGroup);
    }

    public Optional<IBoilerTableEntry> lookup(final Integer boilerCode, //FINCHBCD
            final Enum1713 boilerGroup, //FINMHBOI
            final Enum1777 mainHeatingFuel, //FINMHFUE services.getMainHeatingFuel()
            final Enum1779 backBoilerFuel, //FINWHXTY services.getBackBoiler_Type_Fuel()
            final String manufacturer,
            final String model) {
        final EHCSPrimaryHeatingCode ehsCode = boilerCode == null ? null : EHCSPrimaryHeatingCode.lookupByEHSCode(boilerCode);

        final FuelType fuelType = getMostLikelyFuelType(ehsCode, boilerGroup, mainHeatingFuel, backBoilerFuel);

        IBoilerTableEntry sedbukMatch = null;
        if (boilerGroup != Enum1713.BackBoiler || isBackBoilerKnownToBeInSedbuk(model)) {
            sedbukMatch = index.find(manufacturer, model,
                    BoilerMatchInterface.nhmToBoilerMatch(fuelType),
                    null,
                    BoilerMatchInterface.nhmToBoilerMatch(getBoilerTypeFromBoilerGroup(boilerGroup)));
            if (sedbukMatch == null) {
                sedbukMatch = fixTable.find(manufacturer, model);
            }
        }
        return Optional.fromNullable(sedbukMatch);
    }

    /**
     * Sedbuk table 104 is not supposed to contain back boilers, however we have
     * observed that this is not always true. It contains, for example: Glow
     * worm BBU 45/4, Miami 4. If the boiler contains BBU in the name, it is
     * unlikely to falsely match a standard boiler.
     *
     * @assumption We can match some back boilers with the string "bbu" in their
     * model using Sedbuk (which does not usually contain back boilers).
     * @param manufacturer
     * @param model
     * @return
     */
    private boolean isBackBoilerKnownToBeInSedbuk(final String model) {
        if (model != null) {
            return (model.toLowerCase().contains("bbu"));
        }
        return false;
    }

    /**
     * Gets the fuel type using a combination of EHS code and survey data. If an
     * EHS code was found and it is a back boiler, attempts to use the survey
     * back boiler fuel. If an EHS code was found but it was not a back boiler,
     * or it was a back boiler but no back boiler fuel was specified, attempts
     * to use the main survey fuel. If the survey fuel is not listed in the
     * allowable options for a particular EHS code, uses the EHS code's default
     * fuel type. If no EHS code was found, defaults to using the main survey
     * fuel.
     *
     * @param ehsCode
     * @param boilerGroup
     * @param services
     * @return
     */
    public FuelType getMostLikelyFuelType(final EHCSPrimaryHeatingCode ehsCode,
            final Enum1713 boilerGroup,
            final Enum1777 mainHeatingFuel,
            final Enum1779 backBoilerFuel) {
        final FuelType mainSurveyFuel = getFuelFromSurveyFuel(mainHeatingFuel);
        final FuelType backBoilerSurveyFuel = getMainHeatingFuelFromBackBoilerFuel(backBoilerFuel);

        if (ehsCode != null) {
            if (ehsCode.getSystem().getBasicType() == BasicSpaceHeatingSystemType.BACK_BOILER && backBoilerFuel != null) {
                return ehsCode.tryUseAlternativeFuel(backBoilerSurveyFuel);
            } else {
                return ehsCode.tryUseAlternativeFuel(mainSurveyFuel);
            }
        } else {
            if (boilerGroup == Enum1713.BackBoiler) {
                return backBoilerSurveyFuel;
            } else {
                return mainSurveyFuel;
            }
        }
    }

    ImmutableMap<Enum1777, FuelType> ehsFuelLookup = ImmutableMap.<Enum1777, FuelType>builder()
            .put(Electricity_24HrTariff, ELECTRICITY)
            .put(SolidFuel_SmokelessFuel, HOUSE_COAL)
            .put(Gas_Bottled, BOTTLED_LPG)
            .put(Electricity_10HrTariff, ELECTRICITY)
            .put(Electricity_7HrTariff, ELECTRICITY)
            .put(Communal_CHP_WasteHeat, MAINS_GAS)
            .put(Gas_Mains, MAINS_GAS)
            .put(Enum1777.Oil, OIL)
            .put(Communal_FromBoiler, MAINS_GAS)
            .put(SolidFuel_Coal, HOUSE_COAL)
            .put(Gas_Bulk_LPG, BULK_LPG)
            .put(SolidFuel_Anthracite, HOUSE_COAL)
            .put(SolidFuel_Wood, FuelType.BIOMASS_WOOD)
            .put(Electricity_Standard, ELECTRICITY).build();

    /**
     * <p>
     * Maps EHS main heating fuels to our FuelType. Returns null for _MISSING.
     * </p>
     *
     * @assumption Communal heating is assumed to use mains gas, as specified in
     * the conversion document.
     * @param mainHeatingFuel
     * @return
     * @since 1.0
     */
    public FuelType getFuelFromSurveyFuel(final Enum1777 mainHeatingFuel) {
        if (mainHeatingFuel == Enum1777.__MISSING) {
            return null;
        }
        return ehsFuelLookup.get(mainHeatingFuel);
    }

    private final Map<Enum1779, FuelType> backBoilerFuelTypeLookup = ImmutableMap.<Enum1779, FuelType>builder()
            .put(Enum1779.__MISSING, FuelType.MAINS_GAS).put(Oil, FuelType.OIL)
            .put(BottledGas, BOTTLED_LPG).put(Smokeless, HOUSE_COAL).put(Anthracite, HOUSE_COAL)
            .put(Wood, BIOMASS_WOOD).put(Coal, HOUSE_COAL).put(MainsGas, MAINS_GAS)
            .put(BulkLPG, BULK_LPG).build();

    /**
     * @param backBoilerFuel
     * @return A FuelType which matches the EHS back boiler fuel type.
     * @since 1.0
     */
    public FuelType getMainHeatingFuelFromBackBoilerFuel(final Enum1779 backBoilerFuel) {
        return backBoilerFuelTypeLookup.get(backBoilerFuel);
    }
}
