package uk.org.cse.stockimport.simple.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType;
import uk.org.cse.nhm.stockimport.simple.dto.MappableDTOReader;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.services.IWaterHeatingDTO;
import uk.org.cse.stockimport.domain.services.ImmersionHeaterType;
import uk.org.cse.stockimport.domain.services.WaterHeatingSystemType;
import uk.org.cse.stockimport.domain.services.impl.WaterHeatingDTO;

public class WaterHeatingMapperTest extends AbsMapperTest {
	IWaterHeatingDTO heatingDTO;
	
	final ElectricityTariffType electricityTariffType = ElectricityTariffType.FLAT_RATE;
	final boolean expectedComminityChargedUsage = true;
	final double expectedCHPFraction = .75d;
	
	final double expectedSummerEfficiency = .5d;
	final double expectedWinterEfficiency = .65d;
	final double expectedBasicEfficiency = .45;
	
	final boolean expectedWithCentralHeating = true;
	final WaterHeatingSystemType expectedWaterHeatingSystemType = WaterHeatingSystemType.BACK_BOILER;
	final ImmersionHeaterType expectedImmersionHeaterType = ImmersionHeaterType.DUAL_COIL;
	final FuelType expectedMainHeatingFuel = FuelType.BIOMASS_PELLETS;
	final FlueType expectedFlueType = FlueType.BALANCED_FLUE;
	final int expectedInstallationYear = 1991;
	
	final double expectedCylinderVolume = 67.4;
	final Boolean expectedCylinderThermostatPresent = Boolean.TRUE;
	final Boolean expectedCylinderFactoryInsulated = Boolean.TRUE;
	final double expectedCylinderInsulationThickness = 38.0;
	final Boolean isFromPCDBMatch = Boolean.TRUE;
	
	
	final Boolean expectedIsSolarHotWaterPresent = Boolean.TRUE;
	final Boolean expectedIsSolarStoreInCylinder = Boolean.TRUE;
	final Double expectedSolarVolumeOutsideCylinder = 12.45d;
	
	@Before
	public void initiateTests(){
		
		fields()
		//Assertions
		.add(IBasicDTO.AACODE, aacode)
		
		//Charging
		.add(IWaterHeatingDTO.TARIFF_FIELD, electricityTariffType.toString())
		.add(IWaterHeatingDTO.COMMUNUTYBASEDCHARGE_FIELD, Boolean.toString(expectedComminityChargedUsage))
		.add(IWaterHeatingDTO.CHPFRACTION_FIELD, String.valueOf(expectedCHPFraction))
		
		//Efficiency
		.add(IWaterHeatingDTO.SUMMEREFFICIENCY_FIELD, String.valueOf(expectedSummerEfficiency))
		.add(IWaterHeatingDTO.WINNTEREFFICIENCY_FIELD, String.valueOf(expectedWinterEfficiency))
		.add(IWaterHeatingDTO.BASICEFFICIENCY_FIELD, String.valueOf(expectedBasicEfficiency))
	
		//System Data
		.add(IWaterHeatingDTO.WITHCENTRALHEATING_FIELD, Boolean.toString(expectedWithCentralHeating))
		.add(IWaterHeatingDTO.SYSTEMTYPE_FIELD, expectedWaterHeatingSystemType.toString())
		.add(IWaterHeatingDTO.IMMERSIONTYPE_FIELD, expectedImmersionHeaterType.toString())
		.add(IWaterHeatingDTO.HEATINGFUEL_FIELD, expectedMainHeatingFuel.toString())
		.add(IWaterHeatingDTO.FLUETYPE_FIELD, expectedFlueType.toString())
		.add(IWaterHeatingDTO.INSTALLATIONYEAR_FIELD, String.valueOf(expectedInstallationYear))
		.add(IWaterHeatingDTO.INSTALLATIONYEAR_FIELD, Integer.toString(expectedInstallationYear))
		.add(IWaterHeatingDTO.IS_PCDB_MATCH_FIELD, Boolean.toString(isFromPCDBMatch))
		.add(IWaterHeatingDTO.HAS_ELECTRIC_SHOWER_FIELD, "")
		
		//Cylinder Data
		.add(IWaterHeatingDTO.CYLINDERVOLUME_FIELD, String.valueOf(expectedCylinderVolume))
		.add(IWaterHeatingDTO.HASCYLINDERTHERMOSTAT_FIELD, expectedCylinderThermostatPresent.toString())
		.add(IWaterHeatingDTO.CYLINDERFACTORYINSULATED_FIELD, expectedCylinderFactoryInsulated.toString())
		.add(IWaterHeatingDTO.CYLINDERTHICKNESS_FIELD, String.valueOf(expectedCylinderInsulationThickness))
	
		//Solar Data
		.add(IWaterHeatingDTO.HASSOLARHOTWATER_FIELD, expectedIsSolarHotWaterPresent.toString())
		.add(IWaterHeatingDTO.ISSOLARSTOREINCYLINDER_FIELD, expectedIsSolarStoreInCylinder.toString())
		.add(IWaterHeatingDTO.SOLARSTOREVOLUME_FIELD, String.valueOf(expectedSolarVolumeOutsideCylinder));		
	}

	@Test
	public void testMapFieldSet() throws Exception {
		final IWaterHeatingDTO waterHeatingDTO = new MappableDTOReader<>(WaterHeatingDTO.class).read(fieldSet);
		
		testBuildReferenceData(waterHeatingDTO, aacode);
		testSolarSystemData(waterHeatingDTO, expectedIsSolarHotWaterPresent, expectedIsSolarStoreInCylinder, expectedSolarVolumeOutsideCylinder);
		testChargingData(waterHeatingDTO, electricityTariffType, expectedComminityChargedUsage, expectedCHPFraction);
		testSystemEfficiencyData(waterHeatingDTO, expectedSummerEfficiency, expectedWinterEfficiency, expectedBasicEfficiency);
		testWaterCylinderData(waterHeatingDTO, expectedCylinderVolume, expectedCylinderThermostatPresent, expectedCylinderFactoryInsulated, expectedCylinderInsulationThickness);
		testSystemData(waterHeatingDTO, expectedWithCentralHeating, expectedWaterHeatingSystemType, expectedImmersionHeaterType, expectedMainHeatingFuel, expectedFlueType, expectedInstallationYear);
	}

	
	public static final void testSolarSystemData(final IWaterHeatingDTO heatingDTO, final boolean expectedIsSolarHotWaterPresent, 
			final boolean expectedIsSolarStoreInCylinder,final double expectedSolarVolumeOutsideCylinder){
		assertEquals("expectedIsSolarHotWaterPresent", expectedIsSolarHotWaterPresent, heatingDTO.isSolarHotWaterPresent());
		assertEquals("expectedIsSolarStoreInCylinder", expectedIsSolarStoreInCylinder, heatingDTO.isSolarStoreInCylinder());
		assertEquals("expectedSolarVolumeOutsideCylinder", expectedSolarVolumeOutsideCylinder, heatingDTO.getSolarStoreVolume(), 0d);
	}
	
	public static final void testWaterCylinderData(final IWaterHeatingDTO heatingDTO, final Double expectedCylinderVolume, 
			final boolean expectedCylinderThermostatPresent, final boolean expectedCylinderFactoryInsulated, final Double expectedCylinderInsulationThickness){
		assertEquals("expectedCylinderVolume", expectedCylinderVolume, heatingDTO.getCylinderVolume().get());
		assertEquals("expectedCylinderThermostatPresent", expectedCylinderThermostatPresent, heatingDTO.getCylinderThermostatPresent().get());
		assertEquals("expectedCylinderFactoryInsulated", expectedCylinderFactoryInsulated, heatingDTO.getCylinderFactoryInsulated().get());
		assertEquals("cylinderInsulationThickness", expectedCylinderInsulationThickness, heatingDTO.getCylinderInsulationThickness().get());
	}
	
	public static final void testSystemData(final IWaterHeatingDTO heatingDTO, final boolean expectedWithCentralHeating,
			final WaterHeatingSystemType expectedWaterHeatingSystemType,final ImmersionHeaterType expectedImmersionHeaterType, 
			final FuelType expectedMainHeatingFuel, final FlueType expectedFlueType, final Integer expectedInstallationYear){
		assertEquals("isWithCentralHeating", expectedWithCentralHeating, heatingDTO.isWithCentralHeating());
		assertEquals("WaterHeatingSystem", expectedWaterHeatingSystemType, heatingDTO.getWaterHeatingSystemType().get());
		assertEquals("ImmersionHeaterType", expectedImmersionHeaterType, heatingDTO.getImmersionHeaterType().get());
		assertEquals("FuelType", Optional.of(expectedMainHeatingFuel), heatingDTO.getMainHeatingFuel());
		assertEquals("FlueType", expectedFlueType, heatingDTO.getFlueType().get());
		assertEquals("installation year", expectedInstallationYear, heatingDTO.getInstallationYear().get());
	}
	
	public static final void testSystemEfficiencyData(final IWaterHeatingDTO heatingDTO, final double expectedSummerEfficiency, 
			final double expectedWinterEfficiency,final double expectedBasicEfficiency){
		assertEquals("SummerEfficiency", expectedSummerEfficiency, heatingDTO.getSummerEfficiency().get(),0d);
		assertEquals("WinterEfficiency", expectedWinterEfficiency, heatingDTO.getWinterEfficiency().get(),0d);
		assertEquals("BasicEfficiency", expectedBasicEfficiency, heatingDTO.getBasicEfficiency(),0d);
	}

	public static final void testChargingData(final IWaterHeatingDTO heatingDTO, final ElectricityTariffType expectedTariff, 
			final boolean expectedComminityChargedUsage, final Double expectedCHPFraction){
		assertEquals("electrictyTarrifType",expectedTariff, heatingDTO.getElectricTariff().get());
		assertEquals("expectedComminityChargedUsage",expectedComminityChargedUsage, heatingDTO.getCommunityChargingUsageBased().get());
		assertEquals("expectedCHPFraction",expectedCHPFraction, heatingDTO.getChpFraction().get());
	}
}
