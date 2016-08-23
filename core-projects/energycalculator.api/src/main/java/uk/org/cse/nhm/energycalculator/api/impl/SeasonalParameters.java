package uk.org.cse.nhm.energycalculator.api.impl;

import uk.org.cse.nhm.energycalculator.api.IHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.ISeasonalParameters;
import uk.org.cse.nhm.energycalculator.api.types.ZoneType;

import com.google.common.base.Optional;

public class SeasonalParameters implements ISeasonalParameters {	
	private final double externalTemperature;
	private final double siteWindSpeed;
	private final double solarDeclination;
	private final int monthOfYear;
	private final double horizontalSolarFlux;
	private final double latitude;
	private final IHeatingSchedule zoneOneHeatingSchedule;
	private Optional<IHeatingSchedule> zoneTwoSchedule;
	
	public SeasonalParameters(int monthOfYear, double solarDeclination, double externalTemperature,
			double siteWindSpeed, double horizontalSolarFlux, double latitude, IHeatingSchedule heatingSchedule, Optional<IHeatingSchedule> zoneTwoSchedule) {
		this.externalTemperature = externalTemperature;
		this.siteWindSpeed = siteWindSpeed;
		this.solarDeclination = solarDeclination;
		this.monthOfYear = monthOfYear;
		this.horizontalSolarFlux = horizontalSolarFlux;
		this.latitude = latitude;
		this.zoneOneHeatingSchedule = heatingSchedule;
		this.zoneTwoSchedule = zoneTwoSchedule;
	}

	@Override
	public double getExternalTemperature() {
		return externalTemperature;
	}

	@Override
	public double getSiteWindSpeed() {
		return siteWindSpeed;
	}

	@Override
	public double getSolarDeclination() {
		return solarDeclination;
	}

	@Override
	public double getSolarFlux(final double angleFromHorizontal, final double angleFromNorth) {
		/*
		BEISDOC
		NAME: Effective solar flux
		DESCRIPTION: 
		TYPE: formula
		UNIT: W
		SAP: (U1)
		BREDEM: 2.4.1G
		DEPS: weather
		ID: effective-solar-flux
		CODSIEB
		*/
        return horizontalSolarFlux * InsolationPlaneUtil.getSolarFluxMultiplier(getSolarDeclination(), latitude, angleFromHorizontal, angleFromNorth);
	}

	@Override
	public int getMonthOfYear() {
		return monthOfYear;
	}

	@Override
	public IHeatingSchedule getHeatingSchedule(ZoneType zone) {
		switch (zone) {
		case ZONE1:
			return zoneOneHeatingSchedule;
		case ZONE2:
			return zoneTwoSchedule.or(zoneOneHeatingSchedule);
		}
		throw new RuntimeException("There is no heating schedule for zone " + zone);
	}
	
	@Override
	public boolean isHeatingOn() {
		return zoneOneHeatingSchedule.isHeatingOn() ||
				(zoneTwoSchedule.isPresent() && zoneTwoSchedule.get().isHeatingOn());
	}
}
