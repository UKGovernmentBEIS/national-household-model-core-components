package uk.org.cse.nhm.language.definition.context;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

@Bind("weather")
@Doc({
	"The monthly weather from January to December, described in terms of insolation, wind speed and temperature."
})
public class XWeatherConstant extends XWeather {
	public static class P {
		public static final String windspeed = "windspeed";
		public static final String temperature = "temperature";
		public static final String insolation = "insolation";
	}

	private List<Double> windspeed = new ArrayList<>();
	private List<Double> temperature = new ArrayList<>();
	private List<Double> insolation = new ArrayList<>();
	
	@BindNamedArgument
	@Prop(P.windspeed)
	@Size(max = 12, min = 12, message = "weather must contain a list of 12 numbers for windspeed in each month from January through to December.")
	@Doc("The windspeed in m/s for each month from January through to December.")
	public List<Double> getWindspeed() {
		return windspeed;
	}

	public void setWindspeed(final List<Double> windspeed) {
		this.windspeed = windspeed;
	}

	@BindNamedArgument
	@Prop(P.temperature)
	@Size(min = 12, max = 12, message = "weather must contain a list of 12 numbers for temperature in each month from January through to December.")
	@Doc("The temperature in celcius for each month from January through to December.")
	public List<Double> getTemperature() {
		return temperature;
	}
	
	public void setTemperature(final List<Double> temperature) {
		this.temperature = temperature;
	}
	
	@BindNamedArgument
	@Prop(P.insolation)
	@Size(min = 12, max = 12, message = "weather must contain a list of 12 numbers for insolation in each month from January through to December.")
	@Doc("The solar radiation incident on the plane, in watts per square meter, for each month from January through to December.")
	public List<Double> getInsolation() {
		return insolation;
	}
	
	public void setInsolation(final List<Double> insolation) {
		this.insolation = insolation;
	}
}
