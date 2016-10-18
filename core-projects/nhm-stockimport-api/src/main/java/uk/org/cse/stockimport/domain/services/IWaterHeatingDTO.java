package uk.org.cse.stockimport.domain.services;

import com.google.common.base.Optional;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.stockimport.domain.schema.DTO;
import uk.org.cse.stockimport.domain.schema.DTOField;

/**
 * Defines the DTO for domestic hot water (DHW) systems. In the overwhelming majority of cases, DHW is provided
 * by a connection to the main space heating system, which is indicated by the {@link #isWithCentralHeating()} method.
 * In such cases, most of the attributes of the DTO are not requried, as they are given by attributes of the main
 * heating system.
 * 
 * @author hinton
 * @since 1.0
 */
@DTO(value = "water-heating", required=true)
public interface IWaterHeatingDTO extends IHeatingDTO {
	public static final String WITHCENTRALHEATING_FIELD = "WithCentralHeating";
	public static final String SYSTEMTYPE_FIELD = "WaterHeatingSystemType";
	public static final String INSTALLATIONYEAR_FIELD = "InstallationYear";
	public static final String SOLARSTOREVOLUME_FIELD = "SolarStoreVolume";
	public static final String HASSOLARHOTWATER_FIELD = "SolarHotWaterPresent";
	public static final String ISSOLARSTOREINCYLINDER_FIELD = "SolarStoreInCylinder";
	public static final String HASCYLINDERTHERMOSTAT_FIELD = "CylinderThermostatPresent";
	public static final String CYLINDERTHICKNESS_FIELD = "CylinderInsulationThickness";
	public static final String CYLINDERVOLUME_FIELD = "CylinderVolume";
	public static final String IMMERSIONTYPE_FIELD = "ImmersionHeaterType";
	public static final String CYLINDERFACTORYINSULATED_FIELD = "CylinderFactoryInsulated";
	
	public static final String HEATINGFUEL_FIELD = "mainHeatingFuel";
	
	@DTOField(HEATINGFUEL_FIELD)
	public Optional<FuelType> getMainHeatingFuel() ;
	public void setMainHeatingFuel(Optional<FuelType> mainHeatingFuel) ;
	
	
	@DTOField(WITHCENTRALHEATING_FIELD)
	public boolean isWithCentralHeating() ;
	public void setWithCentralHeating(boolean withCentralHeating) ;
	
	@DTOField(value=SYSTEMTYPE_FIELD)
	public Optional<WaterHeatingSystemType> getWaterHeatingSystemType() ;
	public void setWaterHeatingSystemType(Optional<WaterHeatingSystemType> waterHeatingSystemType) ;
	
	@DTOField(value=CYLINDERVOLUME_FIELD)
	public Optional<Double> getCylinderVolume() ;
	public void setCylinderVolume(Optional<Double> cylinderVolume) ;
	
	@DTOField(value=CYLINDERTHICKNESS_FIELD)
	public Optional<Double> getCylinderInsulationThickness() ;
	public void setCylinderInsulationThickness(Optional<Double> cylinderInsulationThickness) ;
	
	@DTOField(value=HASCYLINDERTHERMOSTAT_FIELD)
	public Optional<Boolean> getCylinderThermostatPresent() ;
	public void setCylinderThermostatPresent(Optional<Boolean> cylinderThermostatPresent) ;
	
	@DTOField(value=CYLINDERFACTORYINSULATED_FIELD)
	public Optional<Boolean> getCylinderFactoryInsulated() ;
	public void setCylinderFactoryInsulated(Optional<Boolean> cylinderFactoryInsulated) ;
	
	@DTOField(value=IMMERSIONTYPE_FIELD)
	public Optional<ImmersionHeaterType> getImmersionHeaterType() ;
	public void setImmersionHeaterType(Optional<ImmersionHeaterType> immersionHeaterType) ;
	
	@DTOField(HASSOLARHOTWATER_FIELD)
	public boolean isSolarHotWaterPresent() ;
	public void setSolarHotWaterPresent(boolean solarHotWaterPresent) ;
	
	@DTOField(ISSOLARSTOREINCYLINDER_FIELD)
	public boolean isSolarStoreInCylinder() ;
	public void setSolarStoreInCylinder(boolean solarStoreInCylinder) ;
	
	@DTOField(SOLARSTOREVOLUME_FIELD)
	public double getSolarStoreVolume() ;
	public void setSolarStoreVolume(double solarStoreVolume) ;
	
	@DTOField(value=INSTALLATIONYEAR_FIELD)
	public Optional<Integer> getInstallationYear() ;
	public void setInstallationYear(Optional<Integer> installationYear) ;
}
