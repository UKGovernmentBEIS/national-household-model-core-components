package uk.org.cse.stockimport.domain.services.impl;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.google.common.base.Optional;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType;
import uk.org.cse.stockimport.domain.services.IWaterHeatingDTO;
import uk.org.cse.stockimport.domain.services.ImmersionHeaterType;
import uk.org.cse.stockimport.domain.services.WaterHeatingSystemType;

/**
 * @since 1.0
 */
@AutoProperty
public class WaterHeatingDTO extends AbsHeatingSystemDTO implements IWaterHeatingDTO {
    private Optional<FuelType> mainHeatingFuel = Optional.absent();
    private Optional<FlueType> flueType = Optional.absent();
    private boolean withCentralHeating = true;
    private Optional<WaterHeatingSystemType> waterHeatingSystemType = Optional.absent();
    private Optional<Double> cylinderVolume = Optional.absent();
    private Optional<Double> cylinderInsulationThickness = Optional.absent();
    private Optional<Boolean> cylinderThermostatPresent = Optional.absent();
    private Optional<Boolean> cylinderFactoryInsulated = Optional.absent();
    private Optional<ImmersionHeaterType> immersionHeaterType = Optional.absent();
    private boolean solarHotWaterPresent;
    private boolean solarStoreInCylinder;
    private double solarStoreVolume;

    private Optional<Integer> installationYear = Optional.absent();

    /**
     * @since 2.0
     */
    public WaterHeatingDTO(){
    	super();
    }
    
	@Override
	public Optional<FuelType> getMainHeatingFuel() {
		return mainHeatingFuel;
	}

	@Override
	public void setMainHeatingFuel(final Optional<FuelType> mainHeatingFuel) {
		this.mainHeatingFuel = mainHeatingFuel;
	}

	@Override
	public Optional<FlueType> getFlueType() {
		return flueType;
	}

	@Override
	public void setFlueType(final Optional<FlueType> flueType) {
		this.flueType = flueType;
	}

	@Override
	public boolean isWithCentralHeating() {
		return withCentralHeating;
	}

	@Override
	public void setWithCentralHeating(final boolean withCentralHeating) {
		this.withCentralHeating = withCentralHeating;
	}

	@Override
	public Optional<WaterHeatingSystemType> getWaterHeatingSystemType() {
		return waterHeatingSystemType;
	}

	@Override
	public void setWaterHeatingSystemType(final Optional<WaterHeatingSystemType> waterHeatingSystemType) {
		this.waterHeatingSystemType = waterHeatingSystemType;
	}

	@Override
	public Optional<Double> getCylinderVolume() {
		return cylinderVolume;
	}

	@Override
	public void setCylinderVolume(final Optional<Double> cylinderVolume) {
		this.cylinderVolume = cylinderVolume;
	}

	@Override
	public Optional<Double> getCylinderInsulationThickness() {
		return cylinderInsulationThickness;
	}

	@Override
	public void setCylinderInsulationThickness(final Optional<Double> cylinderInsulationThickness) {
		this.cylinderInsulationThickness = cylinderInsulationThickness;
	}

	@Override
	public Optional<Boolean> getCylinderThermostatPresent() {
		return cylinderThermostatPresent;
	}

	@Override
	public void setCylinderThermostatPresent(final Optional<Boolean> cylinderThermostatPresent) {
		this.cylinderThermostatPresent = cylinderThermostatPresent;
	}

	@Override
	public Optional<Boolean> getCylinderFactoryInsulated() {
		return cylinderFactoryInsulated;
	}

	@Override
	public void setCylinderFactoryInsulated(final Optional<Boolean> cylinderFactoryInsulated) {
		this.cylinderFactoryInsulated = cylinderFactoryInsulated;
	}

	@Override
	public Optional<ImmersionHeaterType> getImmersionHeaterType() {
		return immersionHeaterType;
	}

	@Override
	public void setImmersionHeaterType(final Optional<ImmersionHeaterType> immersionHeaterType) {
		this.immersionHeaterType = immersionHeaterType;
	}

	@Override
	public boolean isSolarHotWaterPresent() {
		return solarHotWaterPresent;
	}

	@Override
	public void setSolarHotWaterPresent(final boolean solarHotWaterPresent) {
		this.solarHotWaterPresent = solarHotWaterPresent;
	}

	@Override
	public boolean isSolarStoreInCylinder() {
		return solarStoreInCylinder;
	}

	@Override
	public void setSolarStoreInCylinder(final boolean solarStoreInCylinder) {
		this.solarStoreInCylinder = solarStoreInCylinder;
	}

	@Override
	public double getSolarStoreVolume() {
		return solarStoreVolume;
	}

	@Override
	public void setSolarStoreVolume(final double solarStoreVolume) {
		this.solarStoreVolume = solarStoreVolume;
	}

	@Override
	public Optional<Integer> getInstallationYear() {
		return installationYear;
	}

	@Override
	public void setInstallationYear(final Optional<Integer> installationYear) {
		this.installationYear = installationYear;
	}

	@Override
	public boolean equals(final Object obj) {
		return Pojomatic.equals(this, obj);
	}
}
