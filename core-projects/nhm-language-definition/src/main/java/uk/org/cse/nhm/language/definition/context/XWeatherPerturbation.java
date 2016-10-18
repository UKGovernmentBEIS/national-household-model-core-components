package uk.org.cse.nhm.language.definition.context;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;

@Bind("weather.perturb")
@Doc({
	"Determines the weather by taking another weather element and modifying the values which come out of it.",
	"Each aspect of the weather may be scaled or offset. If it is both scaled and offset then the scaling will be calculated and applied first.",
	"These changes will affect every month independently.",
	"This element is useful if we want to apply a change across a large section of weather without having to change all the numbers by hand.",
	"For example, we could use it in a batch to analyse sensitivity of a scenario to the weather."
})
public class XWeatherPerturbation extends XWeather {
	public static class P {
		public static final String weather = "weather";
		public static final String offsetWindspeed = "offsetWindspeed";
		public static final String offsetTemperature = "offsetTemperature";
		public static final String offsetInsolation = "offsetInsolation";
		public static final String scaleWindspeed = "scaleWindspeed";
		public static final String scaleTemperature = "scaleTemperature";
		public static final String scaleInsolation = "scaleInsolation";
	}
	
	private XWeather weather;
	private double offsetWindspeed;
	private double offsetTemperature;
	private double offsetInsolation;
	private double scaleWindspeed;
	private double scaleTemperature;
	private double scaleInsolation;
	
	@BindPositionalArgument(0)
	@Prop(P.weather)
	@Doc("The weather which will be perturbed.")
	@NotNull(message = "weather.perturb must contain another weather element which it will nodify.")
	public XWeather getWeather() {
		return weather;
	}
	
	public void setWeather(final XWeather weather) {
		this.weather = weather;
	}
	
	@BindNamedArgument("offset-windspeed")
	@Prop(P.offsetWindspeed)
	@Doc("The amount to add to the wind speed in m/s.")
	public double getOffsetWindspeed() {
		return offsetWindspeed;
	}
	
	public void setOffsetWindspeed(final double offsetWindspeed) {
		this.offsetWindspeed = offsetWindspeed;
	}
	
	@BindNamedArgument("offset-temperature")
	@Prop(P.offsetTemperature)
	@Doc("The amount to add to the temperature in celcius.")
	public double getOffsetTemperature() {
		return offsetTemperature;
	}
	
	public void setOffsetTemperature(final double offsetTemperature) {
		this.offsetTemperature = offsetTemperature;
	}

	@BindNamedArgument("offset-insolation")
	@Prop(P.offsetInsolation)
	@Doc("The amount to add to the solar radiation incident on the plane, in watts per square meter.")
	public double getOffsetInsolation() {
		return offsetInsolation;
	}
	
	public void setOffsetInsolation(final double offsetInsolation) {
		this.offsetInsolation = offsetInsolation;
	}
	
	@BindNamedArgument("scale-windspeed")
	@Prop(P.scaleWindspeed)
	@Doc("The proprortion to scale the wind speed by. A value of 0 implies no change.")
	public double getScaleWindspeed() {
		return scaleWindspeed;
	}
	
	public void setScaleWindspeed(final double scaleWindspeed) {
		this.scaleWindspeed = scaleWindspeed;
	}
	
	@BindNamedArgument("scale-temperature")
	@Prop(P.scaleTemperature)
	@Doc("The proportion to scale the temperature by. A value of 0 implies no change.")
	public double getScaleTemperature() {
		return scaleTemperature;
	}
	
	public void setScaleTemperature(final double scaleTemperature) {
		this.scaleTemperature = scaleTemperature;
	}
	
	@BindNamedArgument("scale-insolation")
	@Prop(P.scaleInsolation)
	@Doc("The proportion to scale the insolation by. A value of 0 implies no change.")
	public double getScaleInsolation() {
		return scaleInsolation;
	}
	
	public void setScaleInsolation(final double scaleInsolation) {
		this.scaleInsolation = scaleInsolation;
	}
}
