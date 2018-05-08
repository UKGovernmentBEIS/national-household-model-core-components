package uk.org.cse.stockimport.ehcs2010.spss.elementreader;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.org.cse.nhm.ehcs10.physical.types.Enum1282.No;
import static uk.org.cse.nhm.ehcs10.physical.types.Enum1282.Unknown;
import static uk.org.cse.nhm.ehcs10.physical.types.Enum1282.Yes;
import static uk.org.cse.nhm.ehcs10.physical.types.Enum1282.__MISSING;
import static uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType.ECONOMY_10;
import static uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType.ECONOMY_7;
import static uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType.FLAT_RATE;
import static uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType.TWENTYFOUR_HOUR_HEATING;
import static uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType.DELAYED_START_THERMOSTAT;
import static uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType.PROGRAMMER;
import static uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType.ROOM_THERMOSTAT;
import static uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType.THERMOSTATIC_RADIATOR_VALVE;
import static uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType.TIME_TEMPERATURE_ZONE_CONTROL;
import static uk.org.cse.nhm.hom.emf.technologies.StorageHeaterControlType.AUTOMATIC_CHARGE_CONTROL;
import static uk.org.cse.nhm.hom.emf.technologies.StorageHeaterControlType.CELECT_CHARGE_CONTROL;
import static uk.org.cse.nhm.hom.emf.technologies.StorageHeaterControlType.MANUAL_CHARGE_CONTROL;
import static uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType.CONVECTOR;
import static uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType.FAN;
import static uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType.OLD_LARGE_VOLUME;
import static uk.org.cse.stockimport.ehcs2010.spss.elementreader.lookup.EHCSPrimaryHeatingCode.ELECTRIC_STORAGE_MODERN_SLIMLINE_CONVECTOR;
import static uk.org.cse.stockimport.ehcs2010.spss.elementreader.lookup.EHCSPrimaryHeatingCode.ELECTRIC_STORAGE_MODERN_SLIMLINE_WITH_FAN;
import static uk.org.cse.stockimport.ehcs2010.spss.elementreader.lookup.EHCSPrimaryHeatingCode.ELECTRIC_STORAGE_OLD_LARGE_VOLUME;
import static uk.org.cse.stockimport.ehcs2010.spss.elementreader.lookup.EHCSPrimaryHeatingCode.GAS_UNKNOWN_FLUE_TYPE;
import static uk.org.cse.stockimport.ehcs2010.spss.elementreader.lookup.EHCSPrimaryHeatingCode.OIL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;

import uk.org.cse.boilermatcher.lucene.IBoilerTableEntry;
import uk.org.cse.boilermatcher.lucene.ISedbukIndex;
import uk.org.cse.boilermatcher.lucene.SedbukFix;
import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.ServicesEntry;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1282;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1713;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1776;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1777;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterControlType;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType;
import uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType;
import uk.org.cse.stockimport.domain.services.SecondaryHeatingSystemType;
import uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType;
import uk.org.cse.stockimport.domain.services.impl.BoilerMatch;
import uk.org.cse.stockimport.domain.services.impl.SpaceHeatingDTO;
import uk.org.cse.stockimport.ehcs2010.spss.elementreader.lookup.EHCSPrimaryHeatingCode;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRepositoryFactory;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRespository;
import uk.org.cse.stockimport.sedbuk.tables.BoilerType;

@RunWith(MockitoJUnitRunner.class)
public class SpssSpaceHeatingReaderTest {

    SpssSpaceHeatingReader reader;

    @Mock
    IHouseCaseSourcesRepositoryFactory itrFactory;

    @Mock
    IHouseCaseSourcesRespository<Object> iteratorProvider;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        when(itrFactory.build((Iterable<Class<?>>) any(), (String) any())).thenReturn(iteratorProvider);
        reader = new SpssSpaceHeatingReader("", itrFactory, mock(SedbukFix.class), mock(ISedbukIndex.class));
    }

    @Test
    public void testCHPFractionIsSetForCHP() {
        assertEquals("CHP fraction is assumed to always be 0.35 for CHP systems", Optional.of(0.35), reader.getCHPFraction(SpaceHeatingSystemType.COMMUNITY_HEATING_WITH_CHP));
    }

    @Test
    public void testCHPFractionIsUnsetForOtherHeating() {
        for (final SpaceHeatingSystemType type : SpaceHeatingSystemType.values()) {
            if (type != SpaceHeatingSystemType.COMMUNITY_HEATING_WITH_CHP) {
                assertEquals("CHP fraction should be unset for heating system type " + type, Optional.<Double>absent(), reader.getCHPFraction(type));
            }
        }
    }

    @Test
    public void testUsageBasedChargingIsTrueForCHPAndCommunityHeating() {
        assertEquals("Usage based charging is assumed to always be true for CHP systems", Optional.of(true), reader.getIsCommunityChargingUsageBased(SpaceHeatingSystemType.COMMUNITY_HEATING_WITH_CHP));
        assertEquals("Usage based charging is assumed to always be true for Community systems", Optional.of(true), reader.getIsCommunityChargingUsageBased(SpaceHeatingSystemType.COMMUNITY_HEATING_WITHOUT_CHP));
    }

    @Test
    public void testUsageBasedChargingIsUnsetForOtherHeating() {
        for (final SpaceHeatingSystemType type : SpaceHeatingSystemType.values()) {
            if (type != SpaceHeatingSystemType.COMMUNITY_HEATING_WITH_CHP && type != SpaceHeatingSystemType.COMMUNITY_HEATING_WITHOUT_CHP) {
                assertEquals("Usage based charging should be unset for heating system type " + type, Optional.<Boolean>absent(), reader.getIsCommunityChargingUsageBased(type));
            }
        }
    }

    @Test
    public void testEHCSHeatingLookupUsesBoilerGroupForGas() {
        assertEquals("Gas standard boiler with standard boiler group should still be a standard boiler.", SpaceHeatingSystemType.STANDARD, reader.getMainHeatingSystemType(GAS_UNKNOWN_FLUE_TYPE, Enum1713.Standard));
        assertEquals("Gas standard boiler with back boiler group should return a back boiler.", SpaceHeatingSystemType.BACK_BOILER, reader.getMainHeatingSystemType(GAS_UNKNOWN_FLUE_TYPE, Enum1713.BackBoiler));
        assertEquals("Gas standard boiler with combi boiler group should return a combi boiler.", SpaceHeatingSystemType.COMBI, reader.getMainHeatingSystemType(GAS_UNKNOWN_FLUE_TYPE, Enum1713.Combination));
        assertEquals("Gas standard boiler with condensing combi boiler group should return a combi boiler.", SpaceHeatingSystemType.COMBI, reader.getMainHeatingSystemType(GAS_UNKNOWN_FLUE_TYPE, Enum1713.CondensingCombi));
    }

    @Test
    public void testEHCSHeatingLookupUsesBoilerGroupIfNotGas() {
        assertEquals("Oil standard boiler with standard boiler group should still be a standard boiler.", SpaceHeatingSystemType.STANDARD, reader.getMainHeatingSystemType(OIL, Enum1713.Standard));
        assertEquals("Oil standard boiler with back boiler group should still become a back boiler.", SpaceHeatingSystemType.BACK_BOILER,
                reader.getMainHeatingSystemType(OIL, Enum1713.BackBoiler));
        assertEquals("Oil standard boiler with combi boiler group should still become a combi boiler.", SpaceHeatingSystemType.COMBI,
                reader.getMainHeatingSystemType(OIL, Enum1713.Combination));
        assertEquals("Oil standard boiler with condensing combi boiler group should still become a combi boiler.", SpaceHeatingSystemType.COMBI,
                reader.getMainHeatingSystemType(OIL, Enum1713.CondensingCombi));
    }

    @Test
    public void testStorageHeaterTypeIsCorrectFromEHSCodes() {
        assertEquals("Should return a convector storage heater.", CONVECTOR, reader.getStorageHeaterType(ELECTRIC_STORAGE_MODERN_SLIMLINE_CONVECTOR).get());
        assertEquals("Should return a convector storage heater.", FAN, reader.getStorageHeaterType(ELECTRIC_STORAGE_MODERN_SLIMLINE_WITH_FAN).get());
        assertEquals("Should return a convector storage heater.", OLD_LARGE_VOLUME, reader.getStorageHeaterType(ELECTRIC_STORAGE_OLD_LARGE_VOLUME).get());
        assertEquals("Only storage heaters should return storage heater types.", Optional.<StorageHeaterType>absent(), reader.getStorageHeaterType(OIL));
    }

    @Test
    public void testStorageHeaterControlTypeIsCorrect() {
        final ServicesEntry services = mock(ServicesEntry.class);
        when(services.getPrimaryHeatingControls_CelectTypeC()).thenReturn(Enum1282.Yes);
        assertEquals("Storage heater with neither advanced control type should return manual charge control", CELECT_CHARGE_CONTROL, reader.getStorageHeaterControlType(SpaceHeatingSystemType.STORAGE_HEATER, services).get());

        when(services.getPrimaryHeatingControls_CelectTypeC()).thenReturn(Enum1282.No);
        when(services.getPrimaryHeatingControls_AutomaticCha()).thenReturn(Enum1282.Yes);
        assertEquals("Storage heater with neither advanced control type should return manual charge control", AUTOMATIC_CHARGE_CONTROL, reader.getStorageHeaterControlType(SpaceHeatingSystemType.STORAGE_HEATER, services).get());

        when(services.getPrimaryHeatingControls_AutomaticCha()).thenReturn(Enum1282.No);
        assertEquals("Storage heater with neither advanced control type should return manual charge control", MANUAL_CHARGE_CONTROL, reader.getStorageHeaterControlType(SpaceHeatingSystemType.STORAGE_HEATER, services).get());

        assertEquals("Only storage heaters should return storage heater control types", Optional.<StorageHeaterControlType>absent(), reader.getStorageHeaterControlType(SpaceHeatingSystemType.COMBI, services));
    }

    @Test
    public void testElectricTariffAbsentForNonElectricHeating() {
        for (final FuelType fuel : FuelType.values()) {
            if (fuel != FuelType.ELECTRICITY) {
                assertEquals("No electric tariff should be returned for fuel type " + fuel, Optional.<ElectricityTariffType>absent(), reader.getElectricTariff(fuel, Enum1777.Electricity_10HrTariff));
            }
        }
    }

    @Test
    public void testElectricTariffCorrectForElectricHeating() {
        assertEquals("Electric fuel type should return economy 7 tariff for electricy_7hr main heating fuel type.", ECONOMY_7, reader.getElectricTariff(FuelType.ELECTRICITY, Enum1777.Electricity_7HrTariff).get());
        assertEquals("Electric fuel type should return economy 10 tariff for electricy_10hr main heating fuel type.", ECONOMY_10, reader.getElectricTariff(FuelType.ELECTRICITY, Enum1777.Electricity_10HrTariff).get());
        assertEquals("Electric fuel type should return twenty four hour tariff for electricy_24hr main heating fuel type.", TWENTYFOUR_HOUR_HEATING,
                reader.getElectricTariff(FuelType.ELECTRICITY, Enum1777.Electricity_24HrTariff).get());
        assertEquals("Electric fuel type should return flat rate tariff for electricy_standard main heating fuel type.", FLAT_RATE, reader.getElectricTariff(FuelType.ELECTRICITY, Enum1777.Electricity_Standard).get());
        assertEquals("Electric fuel type should return absent tariff for other main heating fuel type.", Optional.<ElectricityTariffType>absent(), reader.getElectricTariff(FuelType.ELECTRICITY, Enum1777.Oil));
    }

    @Test
    public void testHeatingSystemControlTypeLookup() {
        final ServicesEntry services = mock(ServicesEntry.class);
        when(services.getPrimaryHeatingControls_CentralTimer()).thenReturn(Yes);
        when(services.getPrimaryHeatingControls_RoomThermost()).thenReturn(Yes);
        when(services.getPrimaryHeatingControls_Thermostatic()).thenReturn(Yes);
        when(services.getPrimaryHeatingControls_TimeAndTemp()).thenReturn(Yes);
        when(services.getPrimaryHeatingControls_DelayedTime()).thenReturn(Yes);

        assertEquals("Should include all the primary heating control types", Sets.newHashSet(new HeatingSystemControlType[]{
            PROGRAMMER,
            ROOM_THERMOSTAT,
            THERMOSTATIC_RADIATOR_VALVE,
            TIME_TEMPERATURE_ZONE_CONTROL,
            DELAYED_START_THERMOSTAT
        }), reader.getHeatingSystemControlTypes(services));
    }

    @Test
    public void testNegativeHeatingSystemControlTypeLookup() {
        final ServicesEntry services = mock(ServicesEntry.class);
        when(services.getPrimaryHeatingControls_CentralTimer()).thenReturn(No);
        when(services.getPrimaryHeatingControls_RoomThermost()).thenReturn(__MISSING);
        when(services.getPrimaryHeatingControls_Thermostatic()).thenReturn(Unknown);
        when(services.getPrimaryHeatingControls_TimeAndTemp()).thenReturn(No);
        when(services.getPrimaryHeatingControls_DelayedTime()).thenReturn(No);

        assertEquals("Should include none of the primary heating control types", Sets.newHashSet(new HeatingSystemControlType[]{}), reader.getHeatingSystemControlTypes(services));
    }

    @Test
    public void testSecondaryHeatingSystemTypeNotPresent() {
        final ServicesEntry services = mock(ServicesEntry.class);
        when(services.getOtherHeating_Present()).thenReturn(Enum10.No);
        assertEquals("Should return No secondary system.", SecondaryHeatingSystemType.NO_SECONDARY_SYSTEM, reader.getSecondaryHeatingSystemType(services));
    }

    @Test
    public void testSecondaryHeatingSystemTypePresent() {
        final ServicesEntry services = mock(ServicesEntry.class);
        when(services.getOtherHeating_Present()).thenReturn(Enum10.Yes);
        when(services.getOtherHeating_TypeOfSystem()).thenReturn(Enum1776.LPG_FixedHeaters);
        assertEquals("Should return gas fire.", SecondaryHeatingSystemType.GAS_FIRE, reader.getSecondaryHeatingSystemType(services));
    }

    @Test
    public void sedbukMatchEfficiencyIsAFractionOf1() {
        final IBoilerTableEntry boilerEntry = mock(IBoilerTableEntry.class);
        when(boilerEntry.getAnnualEfficiency()).thenReturn(66.0);
        when(boilerEntry.getBoilerType()).thenReturn(BoilerMatchInterface.nhmToBoilerMatch(BoilerType.REGULAR));
        when(boilerEntry.getFlueType()).thenReturn(BoilerMatchInterface.nhmToBoilerMatch(FlueType.OPEN_FLUE));
        when(boilerEntry.getBrand()).thenReturn("Vailant");
        when(boilerEntry.getModel()).thenReturn("EHS2010");
        when(boilerEntry.getQualifier()).thenReturn("sx");
        when(boilerEntry.getRow()).thenReturn(10);

        final SpaceHeatingDTO dto = mock(SpaceHeatingDTO.class);
        final BoilerMatch boilerMatch = mock(BoilerMatch.class);
        when(dto.getBoilerMatch()).thenReturn(boilerMatch);

        reader.setSebukDataOnDTO(dto, boilerEntry);

        verify(dto).setSummerEfficiency(Optional.of(0d));
        verify(dto).setWinterEfficiency(Optional.of(0d));
        verify(dto).setBasicEfficiency(0.66);
        verify(boilerMatch).setSedbukBrand("Vailant");
        verify(boilerMatch).setSedbukModel("EHS2010");
        verify(boilerMatch).setSedbukQualifier("sx");
        verify(boilerMatch).setSedbukRow(10);
    }

    @Test
    public void EHCSMatchEfficiencyIsAFractionOf1() {
        final SpaceHeatingDTO dto = mock(SpaceHeatingDTO.class);
        reader.setEHCSPrimaryHeatingCodeDataOnDTO(dto, EHCSPrimaryHeatingCode.GAS_WALL_MOUNTED_OPEN_OR_BALANCED_FLUE, FuelType.MAINS_GAS, Enum1713.Standard);

        verify(dto).setSummerEfficiency(Optional.<Double>absent());
        verify(dto).setWinterEfficiency(Optional.<Double>absent());
        verify(dto).setBasicEfficiency(0.66);

    }

    @Test
    public void backboilerEfficiencyIsAFractionOf1() {
        final SpaceHeatingDTO dto = mock(SpaceHeatingDTO.class);
        final ServicesEntry services = mock(ServicesEntry.class);
        when(services.getBackBoiler_Present()).thenReturn(Enum10.Yes);

        reader.fallBackToBackBoilerHeatingData(dto, services);

        verify(dto).setSummerEfficiency(Optional.<Double>absent());
        verify(dto).setWinterEfficiency(Optional.<Double>absent());
        verify(dto).setBasicEfficiency(EHCSPrimaryHeatingCode.SOLID_BACK_BOILER_CLOSED_FIRE.getEfficiency());
    }
}
