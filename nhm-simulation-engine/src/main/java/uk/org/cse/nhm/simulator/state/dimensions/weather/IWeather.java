package uk.org.cse.nhm.simulator.state.dimensions.weather;

import uk.org.cse.nhm.types.MonthType;

public interface IWeather {
	double getExternalTemperature(final MonthType month);
	double getHorizontalSolarFlux(final MonthType month);
	double getWindSpeed(final MonthType month);
}
