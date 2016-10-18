package uk.org.cse.nhm.language.definition.context;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

@Bind("context.weather")
@Doc({
	"A context parameter which defines the weather conditions for the simulation.",
})
@Category(CategoryType.WEATHER)
public class XWeatherContext extends XContextParameter {
	public static final class P {
		public static final String weather = "weather";
	}
	
	private XWeather weather;

	@BindPositionalArgument(0)
	@Prop(P.weather)
	@Doc("The weather for the simulation.")
	@NotNull(message = "weather.context must contain some weather.")
	public XWeather getWeather() {
		return weather;
	}

	public void setWeather(final XWeather weather) {
		this.weather = weather;
	}
}
