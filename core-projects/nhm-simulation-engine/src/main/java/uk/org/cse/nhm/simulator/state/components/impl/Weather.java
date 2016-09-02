package uk.org.cse.nhm.simulator.state.components.impl;

import java.util.Arrays;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import uk.org.cse.nhm.simulator.state.dimensions.weather.IWeather;
import uk.org.cse.nhm.types.MonthType;

@AutoProperty
public class Weather implements IWeather {
	/**
	 * The single, standard weather defined in SAP 2009
	 */
	public static final IWeather SAP09 = new Weather(
			/*
			BEISDOC
			NAME: External Temperature
			DESCRIPTION: The average monthly temperatures for UK regions.
			TYPE: table
			UNIT: ℃
			SAP: (22), Table U1 (UK average)
			BREDEM: Table A2 (UK Average)
			SET: context.weather,counterfactual.weather
			ID: external-temperature
			CODSIEB
			*/
			new double[] {4.30, 4.90, 6.50, 8.900, 11.70, 14.60, 16.60, 16.40, 14.10, 10.60, 7.10, 4.20},
			/*
			BEISDOC
			NAME: Insolation
			DESCRIPTION: The average monthly insolation for UK regions.
			TYPE: table
			UNIT: W/m^2
			SAP: (22), Table U3 (UK average)
			BREDEM: Table A1 (UK Average)
			SET: context.weather,counterfactual.weather
			ID: insolation
			CODSIEB
			*/
			new double[] {26.0, 54.0, 96.0, 150.0, 192.0, 200.0, 189.0, 157.0, 115.0, 66.00, 33.0, 21.0},
			/*
			BEISDOC
			NAME: Wind Speed
			DESCRIPTION: The average monthly wind speed for UK regions.
			TYPE: table
			UNIT: m/s
			SAP: (22), Table U2 (UK average)
			BREDEM: Table A3 (UK Average)
			SET: context.weather,counterfactual.weather
			ID: wind-speed
			CODSIEB
			*/
			new double[] {5.10, 5.00, 4.90, 4.400, 4.300, 3.800, 3.800, 3.700, 4.000, 4.300, 4.50, 4.70});
	
	public static final IWeather DEFAULT_WEATHER = SAP09;
	
	private final double[] externalTemperature = new double[MonthType.values().length];
	private final double[] horizontalSolarFlux = new double[MonthType.values().length];
	private final double[] windspeed = new double[MonthType.values().length];

	public Weather(final double[] externalTemperature, final double[] horizontalSolarFlux, final double[] windspeed) {
		if (externalTemperature == null || horizontalSolarFlux == null || windspeed == null) {
			throw new NullPointerException("Weather aspect is null!");
		}
		if (externalTemperature.length != this.externalTemperature.length || horizontalSolarFlux.length != this.horizontalSolarFlux.length
				|| windspeed.length != this.windspeed.length) {
			throw new RuntimeException("The year typically has 12 months");
		}

		for (final MonthType month : MonthType.values()) {
			this.externalTemperature[month.ordinal()] = externalTemperature[month.ordinal()];
			this.horizontalSolarFlux[month.ordinal()] = horizontalSolarFlux[month.ordinal()];
			this.windspeed[month.ordinal()] = windspeed[month.ordinal()];
		}
	}

	@Override
	public double getExternalTemperature(final MonthType month) {
		return externalTemperature[month.ordinal()];
	}

	@Override
	public double getHorizontalSolarFlux(final MonthType month) {
		return horizontalSolarFlux[month.ordinal()];
	}

	@Override
	public double getWindSpeed(final MonthType month) {
		return windspeed[month.ordinal()];
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(externalTemperature);
		result = prime * result + Arrays.hashCode(horizontalSolarFlux);
		result = prime * result + Arrays.hashCode(windspeed);
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Weather other = (Weather) obj;
		if (!Arrays.equals(externalTemperature, other.externalTemperature))
			return false;
		if (!Arrays.equals(horizontalSolarFlux, other.horizontalSolarFlux))
			return false;
		if (!Arrays.equals(windspeed, other.windspeed))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
}
