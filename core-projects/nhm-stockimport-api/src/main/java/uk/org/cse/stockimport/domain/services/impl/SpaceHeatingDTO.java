package uk.org.cse.stockimport.domain.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

import com.google.common.base.Optional;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterControlType;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType;
import uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType;
import uk.org.cse.stockimport.domain.services.IHeatingDTO;
import uk.org.cse.stockimport.domain.services.ISpaceHeatingDTO;
import uk.org.cse.stockimport.domain.services.SecondaryHeatingSystemType;
import uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType;

/**
 * @since 1.0
 */
@AutoProperty
public class SpaceHeatingDTO extends AbsHeatingSystemDTO implements ISpaceHeatingDTO, IHeatingDTO {
	private SpaceHeatingSystemType spaceHeatingSystemType;
	private FuelType mainHeatingFuel;
	private Optional<FlueType> flueType = Optional.absent();
	private List<HeatingSystemControlType> heatingSystemControlTypes = new ArrayList<>();
	private Optional<Boolean> condensing;
	private Optional<StorageHeaterType> storageHeaterType;
	private Optional<StorageHeaterControlType> storageHeaterControlType;
	private SecondaryHeatingSystemType secondaryHeatingSystemType;
	private Optional<Double> storageCombiCylinderVolume;
	private Optional<Double> storageCombiCylinderInsulationThickness;
	private Optional<Boolean> storageCombiCylinderThemostatPresent;
	private Optional<Boolean> storageCombiCylinderFactoryInsulated;
	private Optional<Double> storageCombiSolarVolume;
	private Optional<Integer> installationYear = Optional.absent();

	@Property(policy=PojomaticPolicy.NONE)
	private BoilerMatch boilerMatch;
	
	public SpaceHeatingDTO(){
		super();
	}
	
	@Override
	public SpaceHeatingSystemType getSpaceHeatingSystemType() {
		return spaceHeatingSystemType;
	}

	@Override
	public void setSpaceHeatingSystemType(final SpaceHeatingSystemType spaceHeatingSystemType) {
		this.spaceHeatingSystemType = spaceHeatingSystemType;
	}

	@Override
	public FuelType getMainHeatingFuel() {
		return mainHeatingFuel;
	}

	@Override
	public void setMainHeatingFuel(final FuelType mainHeatingFuel) {
		this.mainHeatingFuel = mainHeatingFuel;
	}

	@Override
	public Optional<Boolean> getCondensing() {
		return condensing;
	}

	@Override
	public void setCondensing(final Optional<Boolean> condensing) {
		this.condensing = condensing;
	}

	@Override
	public Optional<StorageHeaterType> getStorageHeaterType() {
		return storageHeaterType;
	}

	@Override
	public void setStorageHeaterType(final Optional<StorageHeaterType> storageHeaterType) {
		this.storageHeaterType = storageHeaterType;
	}

	@Override
	public Optional<StorageHeaterControlType> getStorageHeaterControlType() {
		return storageHeaterControlType;
	}

	@Override
	public void setStorageHeaterControlType(final Optional<StorageHeaterControlType> storageHeaterControlType) {
		this.storageHeaterControlType = storageHeaterControlType;
	}

	@Override
	public SecondaryHeatingSystemType getSecondaryHeatingSystemType() {
		return secondaryHeatingSystemType;
	}

	@Override
	public void setSecondaryHeatingSystemType(final SecondaryHeatingSystemType secondaryHeatingSystemType) {
		this.secondaryHeatingSystemType = secondaryHeatingSystemType;
	}

	@Override
	public Optional<Double> getStorageCombiCylinderVolume() {
		return storageCombiCylinderVolume;
	}

	@Override
	public void setStorageCombiCylinderVolume(final Optional<Double> storageCombiCylinderVolume) {
		this.storageCombiCylinderVolume = storageCombiCylinderVolume;
	}

	@Override
	public Optional<Double> getStorageCombiCylinderInsulationThickness() {
		return storageCombiCylinderInsulationThickness;
	}

	@Override
	public void setStorageCombiCylinderInsulationThickness(final Optional<Double> storageCombiCylinderInsulationThickness) {
		this.storageCombiCylinderInsulationThickness = storageCombiCylinderInsulationThickness;
	}

	@Override
	public Optional<Boolean> getStorageCombiCylinderThemostatPresent() {
		return storageCombiCylinderThemostatPresent;
	}

	@Override
	public void setStorageCombiCylinderThemostatPresent(final Optional<Boolean> storageCombiCylinderThemostatPresent) {
		this.storageCombiCylinderThemostatPresent = storageCombiCylinderThemostatPresent;
	}

	@Override
	public Optional<Boolean> getStorageCombiCylinderFactoryInsulated() {
		return storageCombiCylinderFactoryInsulated;
	}

	@Override
	public void setStorageCombiCylinderFactoryInsulated(final Optional<Boolean> storageCombiCylinderFactoryInsulated) {
		this.storageCombiCylinderFactoryInsulated = storageCombiCylinderFactoryInsulated;
	}

	@Override
	public Optional<Double> getStorageCombiSolarVolume() {
		return storageCombiSolarVolume;
	}

	@Override
	public void setStorageCombiSolarVolume(final Optional<Double> storageCombiSolarVolume) {
		this.storageCombiSolarVolume = storageCombiSolarVolume;
	}

	@Override
	public Optional<FlueType> getFlueType() {
		return flueType;
	}

	@Override
	public List<HeatingSystemControlType> getHeatingSystemControlTypes() {
		return heatingSystemControlTypes;
	}

	@Override
	public Optional<Integer> getInstallationYear() {
		return installationYear;
	}

	@Override
	public void setInstallationYear(final Optional<Integer> year) {
		this.installationYear = year;
	}
	
	@Override
	public void setFlueType(final Optional<FlueType> flueType) {
		this.flueType = flueType;
	}

	@Override
	public void setHeatingSystemControlTypes(final List<HeatingSystemControlType> heatingSystemControlTypes) {
		this.heatingSystemControlTypes = heatingSystemControlTypes;
	}

	/**
     * @since 1.0
     */
    @Override
	public BoilerMatch getBoilerMatch() {
		return boilerMatch;
	}

    /**
     * @since 1.0
     */
    public void setBoilerMatch(final BoilerMatch boilerMatch) {
		this.boilerMatch = boilerMatch;
    }
    
	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}

	@Override
	public boolean equals(final Object other) {
		return Pojomatic.equals(this, other);
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
}
