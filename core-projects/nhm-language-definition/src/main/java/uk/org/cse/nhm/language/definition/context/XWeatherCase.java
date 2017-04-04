package uk.org.cse.nhm.language.definition.context;


import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.function.bool.XBoolean;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;
import com.larkery.jasb.bind.BindRemainingArguments;

@Bind("weather.case")
@Doc({
	"Determines the weather for a given house by looking through a list of tests. The weather will be determined by the first test which passes.",
	"If none of the tests pass, the weather will be determined by the default value."
})
public class XWeatherCase extends XWeather implements IHouseContext {
	public static class P {
		public static final String def = "default";
		public static final String cases = "cases";
	}
	
	private XWeather defaultWeather;
	private List<XWeatherWhen> cases = new ArrayList<>();
	
	@BindNamedArgument("default")
	@Doc("The weather which will be used if none of the case statements match.")
	@Prop(P.def)
	@NotNull(message = "weather.case must always specify a default weather to be used if none of its case statements match.")
	public XWeather getDefault() {
		return defaultWeather;
	}

	public void setDefault(final XWeather defaultWeather) {
		this.defaultWeather = defaultWeather;
	}

	@BindRemainingArguments
	@Doc("The cases which we will test our house against, in the order in which they should be run. The first to pass (if any) will determine the weather.")
	@Prop(P.cases)
	@Size(min = 1, message = "weather.case must contain at least one when statement.")
	public List<XWeatherWhen> getCases() {
		return cases;
	}

	public void setCases(final List<XWeatherWhen> cases) {
		this.cases = cases;
	}

	@Bind("when")
	@Doc("A case which will determine the weather if its condition is met.")
	public static class XWeatherWhen extends XElement {
		public static class P {
			public static final String condition = "condition";
			public static final String weather = "weather";
		}
		
		private XBoolean condition;
		private XWeather weather;

		@BindPositionalArgument(0)
		@Prop(P.condition)
		@NotNull(message = "when must contain a test on a house as its first argument.")
		@Doc("The test which determines whether this when clause will be used.")
		public XBoolean getCondition() {
			return condition;
		}
		
		public void setCondition(final XBoolean condition) {
			this.condition = condition;
		}
		
		@BindPositionalArgument(1)
		@Prop(P.weather)
		@NotNull(message = "when must contain one of the kinds of weather element as its second argument.")
		@Doc("The weather which will be applied if the test passes.")
		public XWeather getWeather() {
			return weather;
		}
		
		public void setWeather(final XWeather weather) {
			this.weather = weather;
		}
	}
}
