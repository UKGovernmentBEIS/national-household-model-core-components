package uk.org.cse.nhm.energycalculator.impl;

import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.IHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.impl.InsolationPlaneUtil;
import uk.org.cse.nhm.energycalculator.api.types.MonthType;
import uk.org.cse.nhm.energycalculator.api.types.Zone2ControlParameter;
import uk.org.cse.nhm.energycalculator.api.types.ZoneType;

public class BredemSeasonalParameters extends SeasonalParameters {
	private final double externalTemperature;
	private final double siteWindSpeed;
	private final double horizontalSolarFlux;
	private final double latitude;
	private final IHeatingSchedule zoneOneHeatingSchedule;
	private final Optional<IHeatingSchedule> zoneTwoSchedule;

	public BredemSeasonalParameters(final MonthType month, final double externalTemperature,
			final double siteWindSpeed, final double horizontalSolarFlux, final double latitude, final IHeatingSchedule heatingSchedule, final Optional<IHeatingSchedule> zoneTwoSchedule) {
		super(month);

		this.externalTemperature = externalTemperature;
		this.siteWindSpeed = siteWindSpeed;
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
	public double getSolarFlux(final double angleFromHorizontal, final double angleFromNorth) {
		/*
		BEISDOC
		NAME: Effective solar flux
		DESCRIPTION:
		TYPE: formula
		UNIT: W
		SAP: (U1)
		BREDEM: 2.4.1G
		DEPS: insolation,solar-flux-adjustment
		ID: effective-solar-flux
		CODSIEB
		*/
        return horizontalSolarFlux * InsolationPlaneUtil.getSolarFluxMultiplier(getSolarDeclination(), latitude, angleFromHorizontal, angleFromNorth);
	}

	@Override
	public IHeatingSchedule getHeatingSchedule(final ZoneType zone, final Optional<Zone2ControlParameter> zone2ControlParameter) {
		switch (zone) {
		case ZONE1:
			return zoneOneHeatingSchedule;
		case ZONE2:
			return zoneTwoSchedule.or(zoneOneHeatingSchedule);
		default:
			throw new RuntimeException("There is no heating schedule for zone " + zone);
		}
	}

	@Override
	public boolean isHeatingOn() {
		return zoneOneHeatingSchedule.isHeatingOn() ||
				(zoneTwoSchedule.isPresent() && zoneTwoSchedule.get().isHeatingOn());
	}

	@Override
	public boolean isCoolingOn() {
		throw new UnsupportedOperationException("Cooling not yet implemented in the NHM energy calculator.");
	}

	@Override
	public double getHeatingOnFactor(final IInternalParameters parameters, final ISpecificHeatLosses losses, final double revisedGains,
			final double[] demandTemperature) {
		return BREDEMHeatingOn.getHeatingOnFactor(parameters, losses, revisedGains, demandTemperature, getExternalTemperature());
	}
}
