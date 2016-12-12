package uk.org.cse.nhm.energycalculator.api;

import uk.org.cse.nhm.energycalculator.api.types.MonthType;

public interface IWeather {
	double getExternalTemperature(final MonthType month);
	double getHorizontalSolarFlux(final MonthType month);
	double getWindSpeed(final MonthType month);

	@Override
	public int hashCode();
	@Override
	public boolean equals(Object obj);
}
