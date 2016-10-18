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
			new double[] {4.50, 5.00, 6.80, 8.700, 11.70, 14.60, 16.90, 16.90, 14.30, 10.80, 7.00, 4.90},
			new double[] {26.0, 54.0, 94.0, 150.0, 190.0, 201.0, 194.0, 164.0, 116.0, 68.00, 33.0, 21.0},
			new double[] {5.40, 5.10, 5.10, 4.500, 4.100, 3.900, 3.700, 3.700, 4.200, 4.500, 4.80, 5.10});
	
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
