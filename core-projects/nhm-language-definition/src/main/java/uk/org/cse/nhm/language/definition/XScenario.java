package uk.org.cse.nhm.language.definition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.enums.XEnergyCalculatorType;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;
import uk.org.cse.nhm.language.definition.function.num.XHouseWeight;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindRemainingArguments;


@Bind("scenario")
@Doc(
		{
			"The scenario is the top element, which contains all the definitions for the model.",
			"",
                        "Scenarios accept some keyword arguments which set some simple parameters that affect the entire simulation,",
                        "like the start and end date of the run,",
                        "and a series of unnamed arguments which define more complex parameters like tariffs and carbon factors,",
                        "declare user-defined variables, and instruct the model what changes to make to the stock and what to report on."
                }
	)
@Category(CategoryType.MAIN)
public class XScenario extends XElement implements IHouseContext {
	public static final class P {
		public static final String GRANULARITY = "granularity";
		public static final String CONTENTS = "contents";
		public static final String END_DATE = "endDate";
		public static final String START_DATE = "startDate";
		public static final String SEED = "seed";
		public static final String DEMAND_TEMPERATURE = "demandTemperature";
		public static final String STOCKID = "stockID";
		public static final String DESCRIPTION = "description";
		public static final String WEIGHTING = "weighting";
		public static final String SURVEY_WEIGHTING = "surveyWeighting";
        public static final String PROFILE = "profile";
        public static final String CALCULATOR_TYPE = "calculator";
	}

	@Doc("Defines the different ways the quantum can be used to create weights for simulated dwellings")
	public enum Weighting {
		@Doc({"Round the survey weight of a case to the nearest whole number of dwellings using the quantum.",
			"",
			"For example, if the quantum is 100, a case with weight 49 would be omitted, cases with weight 50-149 would be represented as a single group of 100",
			"and a case with weight 150 would be represented as a single group of 200."
		})
		Round,
		@Doc({"For a survey weight of x and a quantum of q, create ceil(x/q) dwellings each having an equal share of the total weight x.",
			"",
			"For example, if the quantum is 100, a case with weight 50 would be represented as a single dwelling with weight 50, whereas a case with weight 150",
			"would be represented as two cases each with weight 75.",
			"Using this scheme the quantum is effectively the maximum weight any simulated dwelling can have.",
			"",
			"This will create dwellings with non-integral weights."
		})
		Uniform,
		@Doc({"For a survey weight of x and a quantum of q, create floor(x/q) dwellings each having weight q, and one additional dwelling which carries",
			"any left-over weight. ", "", "For example if the quantum is 100, a case with weight 250 would be represented as a single dwelling with weight 50, and two with weight 100.",
			"",
			"This will only create dwellings with non-integral weights if the stock data contains cases with non-integral weights."})
		Remainder;

		public Function<Double, List<Double>> getFunction(final int granularity) {
			switch (this) {
			case Remainder:return new RemainderImpl(granularity);
			case Round:return new RoundImpl(granularity);
			case Uniform:return new UniformImpl(granularity);
			}
			throw new UnsupportedOperationException("Unknown quantisation mode " + this);
		}
		
		static class RoundImpl implements Function<Double, List<Double>> {
			private final int granularity;

			public RoundImpl(final int granularity) {
				this.granularity = granularity;
			}

			@Override
			public List<Double> apply(final Double input) {
				// old-style quantisation
				final int count = (int) Math.round(input.doubleValue()/granularity);
				return Collections.nCopies(count, Double.valueOf(granularity));
			}
		}
		
		static class UniformImpl implements Function<Double, List<Double>> {
			private final int granularity;

			public UniformImpl(final int granularity) {
				this.granularity = granularity;
			}

			@Override
			public List<Double> apply(final Double input) {
				final int count = (int) Math.ceil(input.doubleValue()/granularity);
				return Collections.nCopies(count, input.doubleValue() / count);
			}
		}
		
		static class RemainderImpl implements Function<Double, List<Double>> {
			private final int granularity;

			public RemainderImpl(final int granularity) {
				this.granularity = granularity;
			}

			@Override
			public List<Double> apply(final Double input) {
				final int count = (int) Math.floor(input.doubleValue()/granularity);
				final double remainder = input.doubleValue() - (count * granularity);
				if (remainder > 0) {
					return ImmutableList.<Double>builder()
								.addAll(Collections.nCopies(count, Double.valueOf(granularity)))
								.add(remainder)
								.build();
				} else {
					return Collections.nCopies(count, Double.valueOf(granularity));
				}
			}
		}
	}
	
    private List<String> stockID = new ArrayList<>();
	
	private double demandTemperature = 19d;
	private long seed = 0;
	
	private DateTime startDate, endDate;
	private int granularity = 400;
	private Weighting weighting = Weighting.Uniform;
	private XNumber surveyWeighting = new XHouseWeight();
	
	private List<IScenarioElement<?>> contents = new ArrayList<IScenarioElement<?>>();
	
	private String scenarioDescription;

    private int profile = 0;
    
    private XEnergyCalculatorType energyCalculator = XEnergyCalculatorType.BREDEM2012;
    
	@BindNamedArgument("stock-id")
	@Prop(P.STOCKID)
	@Doc(
			{
                "An ID or IDs for a stock import, which will define the state of the housing stock at the start of the simulation.",
                "If you supply several stock IDs (in square brackets), the simulation will run on the union of these stocks.",
				"A list of available housing stocks and their unique IDs is available by visiting the 'Browse Stock' link in the application"
			}
		)
    @Size(min = 1, message = "scenario requires one or more stock IDs to be supplied as the stock-id: argument.")
    public List<String> getStockID() {
		return stockID;
	}

    public void setStockID(final List<String> stockID) {
        this.stockID.clear();
        this.stockID.addAll(stockID);
	}

    @BindNamedArgument("demand-temperature")
	@Doc({"The default demand temperature - this is the thermostat setting used in the energy calculation.",
		"",
		"The demand temperature can also be changed on a house-by-house basis using action.set-heating-temperatures."
	})
	@Prop(P.DEMAND_TEMPERATURE)
	public double getDemandTemperature() {
		return demandTemperature;
	}

	public void setDemandTemperature(final double demandTemperature) {
		this.demandTemperature = demandTemperature;
	}

	@BindNamedArgument("seed")
	@Prop(P.SEED)
	@Doc("A seed for the random number generator.")
	public long getSeed() {
		return seed;
	}


	public void setSeed(final long seed) {
		this.seed = seed;
	}

	@BindNamedArgument("start-date")
	@Prop(P.START_DATE)
	@Doc("The date on which the simulation should start running.")
	@NotNull(message = "scenario requires the 'start-date' attribute to be set.")
	public DateTime getStartDate() {
		return startDate;
	}


	public void setStartDate(final DateTime startDate) {
		this.startDate = startDate;
	}

    
    @Prop(P.PROFILE)
    @Doc("This provides information about what parts of the scenario take the most time to execute, for elements up to this 'depth' into the scenario. Note that profiling adversely affects the running time of the scenario, if enabled.")
    @BindNamedArgument("profile-depth")
    public int getProfile() {
        return profile;
    }
    
    public void setProfile(final int profile) {
        this.profile = profile;
    }

    
	@BindNamedArgument("end-date")	
	@Prop(P.END_DATE)
	@Doc({"The date at which the simulation should finish.",
	"",
	"Nothing which is scheduled after this date will be simulated."
	})
	@NotNull(message = "scenario requires the 'end-date' attribute to be set.")
	public DateTime getEndDate() {
		return endDate;
	}


	public void setEndDate(final DateTime endDate) {
		this.endDate = endDate;
	}

	@BindNamedArgument("quantum")
	@Prop(P.GRANULARITY)
	@Min(value = 10, message = "Quantum must be at least 10. Lower values than that would run extremely slowly.")
	@Doc(
			{
				"Because the survey represents all of the houses in the country using a weighted sample,",
				"the NHM expands a survey case with a given weight into multiple simulated houses during a run.",
				"The manner in which the weight and quantum are used to create simulated houses is determined by",
				"the weighting: argument; for all kinds of weighting, the quantum determines the maximum weight",
				"any simulated house will have."
			}
		)
	public int getGranularity() {
		return granularity;
	}

	public void setGranularity(final int granularity) {
		this.granularity = granularity;
	}

	@Prop(P.CONTENTS)
        @Doc("The remaining arguments declare variables, define complex parameters like tariffs, and specify what the model should simulate and when (c.f. on.dates).")
	@BindRemainingArguments
	public List<IScenarioElement<?>> getContents() {
		return contents;
	}

	public void setContents(final List<IScenarioElement<?>> contents) {
		this.contents = contents;
	}

	
	@BindNamedArgument
	@Prop(P.DESCRIPTION)
	@Doc("Describes the policy in some way.")
	public String getScenarioDescription() {
		return scenarioDescription;
	}


	public void setScenarioDescription(final String scenarioDescription) {
		this.scenarioDescription = scenarioDescription;
	}


	@BindNamedArgument
	@Prop(P.WEIGHTING)
	@Doc({"Specifies the weighting scheme used when quantising the stock.",
		"",
		"Along with the quantum this determines how many simulated houses are produced for a given weight in the stock."
	})
	public Weighting getWeighting() {
		return weighting;
	}


	public void setWeighting(final Weighting weighting) {
		this.weighting = weighting;
	}
	
	@BindNamedArgument("weight-by")
	@Prop(P.SURVEY_WEIGHTING)
	@Doc({
		"Specifies a function to calculate the survey weight of a dwelling.",
		"If the number produced here is greater than the quantum, then that survey case may be split into multiple dwellings with smaller weights.",
		"",
		"If house.weight is used here, it will return the dwelling weight from the stock."
	})
	public XNumber getWeightBy() {
		return surveyWeighting;
	}
	
	public void setWeightBy(XNumber surveyWeighting) {
		this.surveyWeighting = surveyWeighting;
	}
	
	@BindNamedArgument("energy-calculator")
	@Prop(P.CALCULATOR_TYPE)
	@Doc({
		"The energy calculator which will be used for this scenario."
	})
	public XEnergyCalculatorType getEnergyCalculator() {
		return energyCalculator;
	}
	
	public void setEnergyCalculator(XEnergyCalculatorType type) {
		this.energyCalculator = type;
	}
}
