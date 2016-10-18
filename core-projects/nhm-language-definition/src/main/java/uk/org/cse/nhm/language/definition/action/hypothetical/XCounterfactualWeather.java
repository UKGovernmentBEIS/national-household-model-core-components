package uk.org.cse.nhm.language.definition.action.hypothetical;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.context.XWeatherConstant;

import com.google.common.collect.ImmutableList;
import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

@Doc("Sets counterfactual weather on a house; if no weather is specified, SAP 2009 weather is used.")
@Bind("counterfactual.weather")
@Category(CategoryType.WEATHER)
public class XCounterfactualWeather extends XCounterfactualAction {
	public static class P {
		public static final String weather = "weather";
	}
	
	private XWeatherConstant weather = getSAP2009Weather(); 
	
	private static XWeatherConstant getSAP2009Weather() {
		final XWeatherConstant sapWeather = new XWeatherConstant();
		sapWeather.setTemperature(ImmutableList.of(4.50, 5.00, 6.80, 8.700, 11.70, 14.60, 16.90, 16.90, 14.30, 10.80, 7.00, 4.90));
		sapWeather.setWindspeed(ImmutableList.of(5.40, 5.10, 5.10, 4.500, 4.100, 3.900, 3.700, 3.700, 4.200, 4.500, 4.80, 5.10));
		sapWeather.setInsolation(ImmutableList.of(26.0, 54.0, 94.0, 150.0, 190.0, 201.0, 194.0, 164.0, 116.0, 68.0, 33.0, 21.0));
		
		return sapWeather;
	}

	@BindPositionalArgument(0)
	@Prop(P.weather)
	@Doc("The weather which will be used while this counterfactual is in effect.")
	public XWeatherConstant getWeather() {
		return weather;
	}

	public void setWeather(final XWeatherConstant weather) {
		this.weather = weather;
	}
}
