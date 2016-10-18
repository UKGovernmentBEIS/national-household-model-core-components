package uk.org.cse.stockimport.ehcs2010.spss.elementreader;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.ServicesEntry;
import uk.org.cse.nhm.ehcs10.physical.impl.ServicesEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1282;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1727;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1742;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1766;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1771;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1779;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType;
import uk.org.cse.stockimport.domain.services.ISpaceHeatingDTO;
import uk.org.cse.stockimport.domain.services.IWaterHeatingDTO;
import uk.org.cse.stockimport.domain.services.ImmersionHeaterType;
import uk.org.cse.stockimport.domain.services.WaterHeatingSystemType;
import uk.org.cse.stockimport.domain.services.impl.WaterHeatingDTO;
import uk.org.cse.stockimport.repository.IHouseCaseSources;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRepositoryFactory;
import uk.org.cse.stockimport.util.DefaultLookup;
import uk.org.cse.stockimport.util.ILookup;

/**
 * Converts Survey Data into {@link IWaterHeatingDTO}s for the builder to process.
 * 
 * @author hinton
 * @since 1.0
 */
public class SpssHotWaterReader extends AbsSpssReader<IWaterHeatingDTO> {
	private static final int OTHER_FUEL_SOLAR_CODE = 15;
	private static final Logger log = LoggerFactory.getLogger(SpssHotWaterReader.class);
	private static final double DEFAULT_CHP_FRACTION = 0.35d;
	private static final boolean DEFAULT_COMMUNITY_USAGE_BASED_CHARGING = true;
	private static final double SOLAR_VOLUME_OUTSIDE_CYLINDER = 75d;
	
	private static class ServicesUtils {
		private static final Double DEFAULT_CYLINDER_VOLUME = 110d;
		
		private static final Map<Enum1727, Double> VOLUME_BY_VOLUME_CODE = 
				ImmutableMap.<Enum1727, Double>builder()
					.put(Enum1727._450X900Mm_110L_, 110d)
					.put(Enum1727._450X1050Mm_140L_, 140d)
					.put(Enum1727._450X1500Mm_210L_, 210d)
					.put(Enum1727._450X1650Mm_245L_, 245d)
				.build();
		
		private static final Map<Enum1771, Double> INSULATION_THICKNESS_BY_CODE = 
				ImmutableMap.<Enum1771, Double>builder()
					.put(Enum1771._0, 0d)
					.put(Enum1771._12_5Mm, 12.5d)
					.put(Enum1771._25Mm, 25d)
					.put(Enum1771._38Mm, 38d)
					.put(Enum1771._50Mm, 50d)
					.put(Enum1771._80Mm, 80d)
					.put(Enum1771._100Mm, 100d)
					.put(Enum1771._150Mm, 150d)
				.build();
		
		public static double getCylinderVolume(final ServicesEntry services) {
			final Enum1727 e = services.getCylinder_Size_Volume();
			if (e == null || e == Enum1727.__MISSING) {
				final Enum1282 cylinder_Present = services.getCylinder_Present();
				if (cylinder_Present == null || cylinder_Present != Enum1282.Yes) {
					return 0;
				} else {
					return DEFAULT_CYLINDER_VOLUME;
				}
			} else {
				return VOLUME_BY_VOLUME_CODE.get(e);
			}
		}
		
		public static Double getCylinderInsulationThickness(final ServicesEntry services) {
			final Enum1771 thicknessCode = services.getHotWaterCylinder_Thickness_Mms_();
			
			if (INSULATION_THICKNESS_BY_CODE.containsKey(thicknessCode)) {
				return INSULATION_THICKNESS_BY_CODE.get(thicknessCode);
			} else {
				return null;
			}
		}
		
		public static boolean isTrue(final Enum10 e) {
			return e == Enum10.Yes;
		}
		
		public static boolean isOnGasGrid(final ServicesEntry services) {
			return isTrue(services.getGasSystem_MainsSupply());
		}

		public static boolean isCylinderFactoryInsulated(final ServicesEntry services) {
			return (services.getHotWaterCylinder_Insulation() == Enum1766.Foam);
		}

		public static FuelType getPointOfUseFuelType(final ServicesEntry entry, final Enum1742 fromSurvey) {
			switch (fromSurvey) {
			case BottledGas:
				return FuelType.BOTTLED_LPG;
			case BulkLPG:
				return FuelType.BULK_LPG;
			case Oil:
				return FuelType.OIL;
			case Standard:
				return FuelType.ELECTRICITY;
			case MainsGas:
				return FuelType.MAINS_GAS;
			case __MISSING:
			default:
				if (!isOnGasGrid(entry)) {
					return FuelType.ELECTRICITY;
				} else {
					return FuelType.MAINS_GAS;
				}
			}
		}

		public static FuelType getHotWaterFuel(final Enum1779 e) {
			if (e == null) return FuelType.MAINS_GAS;
			switch (e) {
			case Coal:
			case Anthracite:
				return FuelType.HOUSE_COAL;
			case BottledGas:
				return(FuelType.BOTTLED_LPG);
			case BulkLPG:
				return(FuelType.BULK_LPG);
			case Oil:
				return(FuelType.OIL);
			case Smokeless:
				return(FuelType.HOUSE_COAL);
			case Wood:
				return(FuelType.BIOMASS_WOOD);
			case __MISSING:
			case MainsGas:
			default:
				return(FuelType.MAINS_GAS);
			}
		}
	}
	
	private final ILookup<FuelType, Double> hotWaterBoilerEfficiencyByFuel = 
			DefaultLookup.of(
					ImmutableMap.<FuelType, Double> builder()
						.put(FuelType.MAINS_GAS, 0.65d)
						.put(FuelType.BULK_LPG, 0.65d)
						.put(FuelType.OIL, 0.70d)
						.put(FuelType.HOUSE_COAL, 0.55d)
				.build(),
				/**
				 * Basic efficiency for hot-water only boilers not specified in the map {@link #hotWaterBoilerEfficiency}
				 */
				0.65d);
	
	private final double backBoilerEfficiency = 0.65;

	private final double gasSinglepointEfficiency = 0.7;
	private final double gasMultipointEfficiency = 0.65;
	private final double communityDHWEfficiency = 0.8;
	private final double communityCHPEfficiency = 0.75;
	
	/** @since 1.0 */
    public SpssHotWaterReader(final String executionId, final IHouseCaseSourcesRepositoryFactory providerFactory) {
		super(executionId, providerFactory);
	}
	
    /**
     * @assumption All hot water boilers that aren't mains gas, bulk LPG, oil or coal are 65% efficient.
     */
	private double getHotWaterBoilerEfficiency(final FuelType fuelType) {
		return hotWaterBoilerEfficiencyByFuel.get(fuelType);
	}
	
	private void addCylinderProperties(final WaterHeatingDTO dto, final ServicesEntry services) {
		final double cylinderVolume = ServicesUtils.getCylinderVolume(services);
		if (cylinderVolume > 0) {
			dto.setCylinderVolume(Optional.of(cylinderVolume));
			final Double cylinderInsulationThickness = ServicesUtils.getCylinderInsulationThickness(services);
			dto.setCylinderInsulationThickness(Optional.fromNullable(cylinderInsulationThickness));
			dto.setCylinderFactoryInsulated(Optional.fromNullable(ServicesUtils.isCylinderFactoryInsulated(services)));
			switch (services.getWaterHeatingControls_CylinderThermos()) {
			case Yes:
				dto.setCylinderThermostatPresent(Optional.of(true));
				break;
			case __MISSING:
			case Unknown:
			default:
			case No:
				dto.setCylinderThermostatPresent(Optional.of(false));
				break;
			}
		} else {
			dto.setCylinderVolume(Optional.<Double>absent());
		}
	}
	
	private void addImmersionHeaterProperties(final WaterHeatingDTO dto, final ServicesEntry services) {
		final ImmersionHeaterType type;
		if (ServicesUtils.isTrue(services.getDualImmersionHeater_Present())) {
			type = ImmersionHeaterType.DUAL_COIL;
		} else {
			if (ServicesUtils.isTrue(services.getSingleImmersionHeater_Present())) {
				type = ImmersionHeaterType.SINGLE_COIL;
			} else {
				type = null;
			}
		}
		dto.setImmersionHeaterType(Optional.fromNullable(type));
		if (type != null) {
			if (!dto.getCylinderVolume().isPresent()) {
				log.warn("{}: {} immersion heater, but no cylinder; adding default cylinder", dto.getAacode(), type);
				dto.setCylinderVolume(Optional.of(ServicesUtils.DEFAULT_CYLINDER_VOLUME));
				dto.setCylinderFactoryInsulated(Optional.of(true));
				dto.setCylinderThermostatPresent(Optional.of(true));
			}
		}		
	}
	
	@Override
	public List<IWaterHeatingDTO> read(final IHouseCaseSources<Object> provider) {
		final ServicesEntry services = provider.requireOne(ServicesEntry.class);

		if (services == null) {
			log.warn("{}: no services entry", provider.getAacode());
			return Collections.emptyList();
		}
		
		final WaterHeatingDTO dto = new WaterHeatingDTO();
		dto.setAacode(provider.getAacode());
		
		if (shouldBeWithCentralHeating(services, provider.requireOne(ISpaceHeatingDTO.class))) {
			// TODO validate the correctness of this field - if it cannot be true (ie. main heating type is not suitable)
			// we should disregard it
			dto.setWithCentralHeating(true);
		} else {
			addWaterHeatingSystem(provider.getSurveyYear(), dto, services);
		}
		
		// if this hasn't worked, get main heating system
		// check dhw system type somehow?
		
		// cylinder size & insulation informations
		addCylinderProperties(dto, services);

		// immersion heaters, if present
		addImmersionHeaterProperties(dto, services);
		
		// solar dhw system
		final Integer otherCode = services.getHotWaterSystem_OtherFuelCode();
		if (otherCode != null && otherCode.intValue() == OTHER_FUEL_SOLAR_CODE) {
			// there is solar present
			dto.setSolarHotWaterPresent(true);
			if (dto.getCylinderVolume().or(0d) > 0d) {
				dto.setSolarStoreInCylinder(true);
				dto.setSolarStoreVolume(dto.getCylinderVolume().or(0d)/2d);
			} else {
				dto.setSolarStoreInCylinder(false);
				dto.setSolarStoreVolume(SOLAR_VOLUME_OUTSIDE_CYLINDER);
			}
		} 
		
		
		// tarrif related fields :
//		Enum1719 singleFuel = services.getSingleImmersionHeater_Type_Fuel();
//		Enum1719 dualFuel = services.getDualImmersionHeater_Type_Fuel();
		
		return Collections.singletonList((IWaterHeatingDTO) dto);
	}

	@SuppressWarnings("incomplete-switch")
	private boolean shouldBeWithCentralHeating(final ServicesEntry services, final ISpaceHeatingDTO space) {
		if (ServicesUtils.isTrue(services.getBoilerWithCentralHeating_Present())) {
			switch (space.getSpaceHeatingSystemType()) {
			case STORAGE_HEATER:
			case ROOM_HEATER:
				log.warn("{} has main heating type {} and DHW from main heating, which is impossible. Presuming DHW not with main heating", services.getAacode());
				return false;
			}
			return true;
		} else {
			switch (space.getSpaceHeatingSystemType()) {
			case BACK_BOILER:
				if (ServicesUtils.isTrue(services.getBackBoiler_Present())) {
					log.warn("{} has back-boiler main heating and DHW, so overriding DHW not with central heating", services.getAacode());
					return true;
				}
				break;
			
			case COMMUNITY_HEATING_WITHOUT_CHP:
			case COMMUNITY_HEATING_WITH_CHP:
				if (ServicesUtils.isTrue(services.getCommunal_Present_())) {
					log.warn("{} has communal main heating and DHW, so overriding DHW not with central heating", services.getAacode());
					return true;
				}
				break;
				
			case CPSU:
			case COMBI:
			case STANDARD:
			case STORAGE_COMBI:
				if (ServicesUtils.isTrue(services.getBoiler_WaterHeatingOnly__Present())) {
					log.warn("{} has a boiler main heating and DHW, so overriding DHW not with central heating", services.getAacode());
					return true;
				}
				break;
			}
		}
		return false;
	}

	private void addWaterHeatingSystem(final int surveyYear,final WaterHeatingDTO dto, final ServicesEntry services) {
		// apparently we are not with main system, so we need to extract the properties for the system
		log.debug("{} not with main - adding separate DHW", dto.getAacode());
		if (ServicesUtils.isTrue(services.getBoiler_WaterHeatingOnly__Present())) {
			addHotWaterOnlyStandardBoiler(surveyYear, dto, services);
		} else if (ServicesUtils.isTrue(services.getCommunal_Present_())) {
			addCommunalHotWater(dto, services);
		} else if (ServicesUtils.isTrue(services.getBackBoiler_Present())) {
			addHotWaterBackBoiler(dto, services);
		} else if (ServicesUtils.isTrue(services.getSeparateInstantaneousHeaterSinglePoi_FINWSPPR())) {
			addPointOfUse(dto, services, false);
		} else if (ServicesUtils.isTrue(services.getSeparateInstantaneousHeaterMultiPoin_FINWMPPR())) {
			addPointOfUse(dto, services, true);
		} else {
			// default to gas if main heating is gas, otherwise do electric immersion?
			addFallbackWaterHeating(surveyYear, dto, services);
		}
	}

	private void addFallbackWaterHeating(final int surveyYear, final WaterHeatingDTO dto, final ServicesEntry services) {
		if (ServicesUtils.isOnGasGrid(services)) {
			switch (services.getMainHeatingFuel()) {
			case Gas_Bulk_LPG:
				addHotWaterOnlyStandardBoiler(surveyYear, dto, services, FuelType.BULK_LPG);
				return;
			case Gas_Mains:
				addHotWaterOnlyStandardBoiler(surveyYear, dto, services, FuelType.MAINS_GAS);
				return;
			default:
				break;
			}
		}
		
		dto.setImmersionHeaterType(Optional.of(ImmersionHeaterType.SINGLE_COIL));
	}

	private void addPointOfUse(final WaterHeatingDTO dto, final ServicesEntry services, final boolean isMultiPoint) {
		log.debug("{} add multipoint", dto.getAacode());
		
		dto.setWaterHeatingSystemType(Optional.of(isMultiPoint ? WaterHeatingSystemType.MULTIPOINT : WaterHeatingSystemType.SINGLEPOINT));

		final FuelType fuelType = 
				isMultiPoint ?
						ServicesUtils.getPointOfUseFuelType(services, services.getSeparateInstantaneousHeaterMultiPoin_FINWMPTY()) :
						ServicesUtils.getPointOfUseFuelType(services, services.getSeparateInstantaneousHeaterSinglePoi_FINWSPTY());
		
						
		dto.setMainHeatingFuel(Optional.of(fuelType));
		
		if (fuelType == FuelType.ELECTRICITY) {
			dto.setBasicEfficiency(1d);
		} else {
			dto.setBasicEfficiency(isMultiPoint ? gasMultipointEfficiency : gasSinglepointEfficiency);
		}
	}

	private void addHotWaterBackBoiler(final WaterHeatingDTO dto, final ServicesEntry services) {
		log.debug("{} add backboiler", dto.getAacode());
		
		dto.setWaterHeatingSystemType(Optional.of(WaterHeatingSystemType.BACK_BOILER));
		
		dto.setMainHeatingFuel(Optional.of(ServicesUtils.getHotWaterFuel(services.getBackBoiler_Type_Fuel())));
		
		dto.setBasicEfficiency(backBoilerEfficiency);
	}

	private void addCommunalHotWater(final WaterHeatingDTO dto, final ServicesEntry services) {
		log.debug("{} add communal DHW", dto.getAacode());
		
		switch (services.getCommunal_Type_Fuel()) {
		case CHP_Waste:
			dto.setWaterHeatingSystemType(Optional.of(WaterHeatingSystemType.COMMUNITY_CHP));
			dto.setChpFraction(Optional.of(DEFAULT_CHP_FRACTION));
			dto.setBasicEfficiency(communityCHPEfficiency);
			break;
		case FromBoiler:
		case __MISSING:
			dto.setWaterHeatingSystemType(Optional.of(WaterHeatingSystemType.COMMUNITY));
			dto.setBasicEfficiency(communityDHWEfficiency);
			break;
		}
		
		dto.setCommunityChargingUsageBased(Optional.of(DEFAULT_COMMUNITY_USAGE_BASED_CHARGING));
		dto.setMainHeatingFuel(Optional.of(FuelType.MAINS_GAS));
	}

	private void addHotWaterOnlyStandardBoiler(final int surveyYear,final WaterHeatingDTO dto, final ServicesEntry services) {
		addHotWaterOnlyStandardBoiler(surveyYear, dto, services, ServicesUtils.getHotWaterFuel(services.getBoiler_WaterHeatingOnly__Type_Fuel()));
	}

	private void addHotWaterOnlyStandardBoiler(final int surveyYear, final WaterHeatingDTO dto, final ServicesEntry services, final FuelType fuel) {
		log.debug("{} add boiler", dto.getAacode());

		dto.setMainHeatingFuel(Optional.of(fuel));
		
		dto.setWaterHeatingSystemType(Optional.of(WaterHeatingSystemType.STANDARD_BOILER));
		
		dto.setBasicEfficiency(getHotWaterBoilerEfficiency(fuel));
		
		final Integer age = services.getBoiler_WaterHeatingOnly__Age();
		
		if (age != null) {
			final int installationYear = surveyYear - age;
			dto.setInstallationYear(Optional.of(installationYear));
		}
		
		dto.setFlueType(Optional.of(FlueType.BALANCED_FLUE));
	}
	
	@Override
	protected Set<Class<?>> getSurveyEntryClasses() {
		return ImmutableSet.<Class<?>>of(ServicesEntryImpl.class, ISpaceHeatingDTO.class);
	}

	@Override
	protected Class<?> readClass() {
		return IWaterHeatingDTO.class;
	}
}
