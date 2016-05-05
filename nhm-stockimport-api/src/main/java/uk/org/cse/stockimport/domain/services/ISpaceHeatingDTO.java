package uk.org.cse.stockimport.domain.services;

import java.util.List;

import com.google.common.base.Optional;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterControlType;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType;
import uk.org.cse.stockimport.domain.schema.DTO;
import uk.org.cse.stockimport.domain.schema.DTOField;
import uk.org.cse.stockimport.domain.services.impl.BoilerMatch;

/**
 * The DTO for a space-heating system
 * 
 * @author hinton
 * @since 1.0
 */
@DTO(
		value = "space-heating", 
		required=true
	)
public interface ISpaceHeatingDTO extends IHeatingDTO {
	
	public static final String CYLINDERTHICKNESS_FIELD = "storageCombiCylinderInsulationThickness";
	public static final String CYLINDERVOLUME_FIELD = "storageCombiCylinderVolume";
	public static final String CYLINDERFACTORYINSULATED_FIELD = "isStorageCombiCylinderFactoryInsulated";
	public static final String HASCYLINDERTHERMOSTAT_FIELD = "isStorageCombiCylinderThemostatPresent";
	public static final String SOLARSTOREVOLUME_FIELD = "storageCombiSolarVolume";
	
	public static final String SECONDARYHEATINGSYSTEMTYPE_FIELD = "secondaryHeatingSystemType";
	
	public static final String STORAGECONTROL_FIELD = "storageHeaterControlType";
	public static final String STORAGEHEATERTYPE_FIELD = "storageHeaterType";
	
	public static final String SPACEHEATINGTYPE_FIELD = "spaceHeatingSystemType";
	public static final String HEATINGCONTROLTYPES_FIELD = "heatingSystemControlTypes";
	public static final String CONDENSING_FIELD = "isCondensing";
	
	public static final String INSTALLATION_FIED = "installationYear";
	
	public static final String HEATINGFUEL_FIELD = "mainHeatingFuel";
	
	@DTOField(HEATINGFUEL_FIELD)
	public FuelType getMainHeatingFuel() ;
	public void setMainHeatingFuel(final FuelType mainHeatingFuel) ;
	
	
	@DTOField(SPACEHEATINGTYPE_FIELD)
	public SpaceHeatingSystemType getSpaceHeatingSystemType() ;
	public void setSpaceHeatingSystemType(final SpaceHeatingSystemType spaceHeatingSystemType) ;
		
	@DTOField(value=CONDENSING_FIELD)
	public Optional<Boolean> getCondensing() ;
	public void setCondensing(final Optional<Boolean> condensing) ;
	
	@DTOField(value=STORAGEHEATERTYPE_FIELD)
	public Optional<StorageHeaterType> getStorageHeaterType() ;
	public void setStorageHeaterType(final Optional<StorageHeaterType> storageHeaterType) ;
	
	@DTOField(value=STORAGECONTROL_FIELD)
	public Optional<StorageHeaterControlType> getStorageHeaterControlType() ;
	public void setStorageHeaterControlType(final Optional<StorageHeaterControlType> storageHeaterControlType) ;
	
	@DTOField(SECONDARYHEATINGSYSTEMTYPE_FIELD)
	public SecondaryHeatingSystemType getSecondaryHeatingSystemType() ;
	public void setSecondaryHeatingSystemType(final SecondaryHeatingSystemType secondaryHeatingSystemType) ;
	
	@DTOField(value=CYLINDERVOLUME_FIELD)
	public Optional<Double> getStorageCombiCylinderVolume() ;
	public void setStorageCombiCylinderVolume(final Optional<Double> storageCombiCylinderVolume) ;
	
	@DTOField(value=CYLINDERTHICKNESS_FIELD)
	public Optional<Double> getStorageCombiCylinderInsulationThickness() ;
	public void setStorageCombiCylinderInsulationThickness(final Optional<Double> storageCombiCylinderInsulationThickness) ;
	
	@DTOField(value=HASCYLINDERTHERMOSTAT_FIELD)
	public Optional<Boolean> getStorageCombiCylinderThemostatPresent() ;
	public void setStorageCombiCylinderThemostatPresent(final Optional<Boolean> storageCombiCylinderThemostatPresent) ;
	
	@DTOField(value=CYLINDERFACTORYINSULATED_FIELD)
	public Optional<Boolean> getStorageCombiCylinderFactoryInsulated() ;
	public void setStorageCombiCylinderFactoryInsulated(final Optional<Boolean> storageCombiCylinderFactoryInsulated) ;
	
	@DTOField(value=SOLARSTOREVOLUME_FIELD)
	public Optional<Double> getStorageCombiSolarVolume() ;
	public void setStorageCombiSolarVolume(final Optional<Double> storageCombiSolarVolume) ;
		
	@DTOField(HEATINGCONTROLTYPES_FIELD)
	public List<HeatingSystemControlType> getHeatingSystemControlTypes() ;
	public void setHeatingSystemControlTypes(final List<HeatingSystemControlType> types);
	
	@DTOField(value=INSTALLATION_FIED)
	public Optional<Integer> getInstallationYear() ;
	public void setInstallationYear(final Optional<Integer> year);
	
	public BoilerMatch getBoilerMatch();
}
