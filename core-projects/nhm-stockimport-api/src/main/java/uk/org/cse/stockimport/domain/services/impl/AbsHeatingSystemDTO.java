package uk.org.cse.stockimport.domain.services.impl;

import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.stockimport.domain.geometry.impl.AbsDTO;
import uk.org.cse.stockimport.domain.services.IHeatingDTO;

public abstract class AbsHeatingSystemDTO extends AbsDTO implements IHeatingDTO {
	
	//Charging/Tariff
	private Optional<Double> chpFraction = Optional.absent();
	private Optional<ElectricityTariffType> electricTariff = Optional.absent();
	private Optional<Boolean> communityChargingUsageBased = Optional.absent();

	//Efficiencies
	private Optional<Double> summerEfficiency = Optional.absent();
	private Optional<Double> winterEfficiency = Optional.absent();
	private double basicEfficiency;
	@Override
	public Optional<Double> getChpFraction() {
		return chpFraction;
	}
	@Override
	public void setChpFraction(final Optional<Double> chpFraction) {
		this.chpFraction = chpFraction;
	}
	@Override
	public Optional<ElectricityTariffType> getElectricTariff() {
		return electricTariff;
	}
	@Override
	public void setElectricTariff(final Optional<ElectricityTariffType> electricTariff) {
		this.electricTariff = electricTariff;
	}
	@Override
	public Optional<Boolean> getCommunityChargingUsageBased() {
		return communityChargingUsageBased;
	}
	@Override
	public void setCommunityChargingUsageBased(
			final Optional<Boolean> communityChargingUsageBased) {
		this.communityChargingUsageBased = communityChargingUsageBased;
	}
	@Override
	public Optional<Double> getSummerEfficiency() {
		return summerEfficiency;
	}
	@Override
	public void setSummerEfficiency(final Optional<Double> summerEfficiency) {
		this.summerEfficiency = summerEfficiency;
	}
	@Override
	public Optional<Double> getWinterEfficiency() {
		return winterEfficiency;
	}
	@Override
	public void setWinterEfficiency(final Optional<Double> winterEfficiency) {
		this.winterEfficiency = winterEfficiency;
	}
	@Override
	public double getBasicEfficiency() {
		return basicEfficiency;
	}
	@Override
	public void setBasicEfficiency(final double basicEfficiency) {
		this.basicEfficiency = basicEfficiency;
	}
}
