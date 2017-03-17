package uk.org.cse.nhm.language.definition.function.health;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.function.bool.XAny;
import uk.org.cse.nhm.language.definition.function.bool.XBoolean;
import uk.org.cse.nhm.language.definition.function.house.XGetProportionOfDoubleGlazedWindows;
import uk.org.cse.nhm.language.definition.function.num.XHeatLoss;
import uk.org.cse.nhm.language.definition.function.num.XHouseNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;
import uk.org.cse.nhm.language.definition.function.num.XPrior;

@Category(CategoryType.HEALTH)
@Doc({
	"Computes the health impact of a change in e-value and permeability."
	})
@Bind("health-impact-of")
@SeeAlso({XSITFunction.class, XPermeabilityFunction.class})
public class XHealthImpactFunction extends XHouseNumber {
	public static final class P {
		public static final String fromTemperature = "fromTemperature";
		public static final String toTemperature = "toTemperature";
        public static final String fromH = "fromHeatLoss";
        public static final String toH = "toHeatLoss";
		public static final String fromPermeability = "fromPermeability";
		public static final String toPermeability = "toPermeability";
		public static final String toYear = "toYear";
		public static final String fromYear = "fromYear";
		public static final String diseases = "diseases";
		public static final String impact = "impact";
        public static final String hasTrickleVents = "hasTrickleVents";
        public static final String hasExtractFans = "hasExtractFans";
        public static final String fromG = "fromDoubleGlazing";
        public static final String toG = "toDoubleGlazing";
	}

	@Doc("A type of disease")
	public enum XDisease {
		@Doc("Cerebrovascular")
		CaV,
		@Doc("Cardiopulmonary")
	    CP,
	    @Doc("Lung cancers")
	    LC,
	    @Doc("Myocardial infarction")
	    MI,
	    @Doc("Winter cerebrovascular")
	    WCaV,
	    @Doc("Winter cardiovascular")
	    WCV,
	    @Doc("Winter myocardial infarction")
	    WMI,
	    @Doc("Common mental disorders")
	    CMD,
	    @Doc("Athsma")
	    Asthma
	}

	@Doc("One of the types of impact a disease may have")
	public enum XImpact {
		@Doc("Disease effect on life years (in QALYs)")
		Morbidity,
		@Doc("Life years lost due to excess deaths (in QALYs)")
		Mortality,
		@Doc("Cost in pounds from healthcare to the NHS")
		Cost
	}

	private XImpact impact = XImpact.Cost;
	private List<XDisease> diseases = new ArrayList<XDisease>(
			Arrays.asList(XDisease.values())
			);

	private XNumber fromTemperature;
	private XNumber toTemperature = XPrior.valueOf(new XSITFunction());

	private XNumber fromPermeability;
	private XNumber toPermeability = XPrior.valueOf(new XPermeabilityFunction());

	private XNumber fromYear = XNumberConstant.create(0);
	private XNumber toYear = XNumberConstant.create(10);

    private XBoolean hasTrickleVents = new XAny();
    private XBoolean hasExtractFans = new XAny();

    private XNumber fromHeatLoss = XPrior.valueOf(new XHeatLoss());
    private XNumber toHeatLoss = new XHeatLoss();

    private XNumber fromDoubleGlazing = XPrior.valueOf(new XGetProportionOfDoubleGlazedWindows());
    private XNumber toDoubleGlazing = new XGetProportionOfDoubleGlazedWindows();

    @Prop(P.fromG)
    @BindNamedArgument("double-glazed-before")
    @Doc({"A function or boolean to indicate whether the house was mostly double glazed before the intervention.",
                "",
                "The default is to test whether the proportion double glazed is at least 80%."
                })
    public XNumber getFromDoubleGlazing() {
        return fromDoubleGlazing;
    }

    public void setFromDoubleGlazing(final XNumber fromDoubleGlazing) {
        this.fromDoubleGlazing = fromDoubleGlazing;
    }

    @Prop(P.toG)
    @BindNamedArgument("double-glazed-after")
    @Doc({"A function or boolean to indicate whether the house is mostly double glazed after the intervention.",
                "",
                "The default is to test whether the proportion double glazed was at least 80%."
                })
    public XNumber getToDoubleGlazing() {
        return toDoubleGlazing;
    }

    public void setToDoubleGlazing(final XNumber toDoubleGlazing) {
        this.toDoubleGlazing = toDoubleGlazing;
    }

    @Prop(P.toH)
    @BindNamedArgument("heat-loss-after")
    @Doc({"The house's specific heat loss after the intervention.",
                "",
                "This affects the health impact associated with overheating."})
    public XNumber getToHeatLoss() {
        return toHeatLoss;
    }

    public void setToHeatLoss(final XNumber toHeatLoss) {
        this.toHeatLoss = toHeatLoss;
    }

    @Prop(P.fromH)
    @BindNamedArgument("heat-loss-before")
    @Doc({"The house's specific heat loss before the intervention.",
                "",
                "This affects the health impact associated with overheating."})
    public XNumber getFromHeatLoss() {
        return fromHeatLoss;
    }

    public void setFromHeatLoss(final XNumber fromHeatLoss) {
        this.fromHeatLoss = fromHeatLoss;
    }

    @Doc("A function to test whether the house has extractor fans")
    @BindNamedArgument("has-extract-fans")
    public XBoolean getHasExtractFans() {
        return hasExtractFans;
    }

    public void setHasExtractFans(final XBoolean hasExtractFans) {
        this.hasExtractFans = hasExtractFans;
    }

    @Doc("A function to test whether the house has trickle vents")
    @BindNamedArgument("has-trickle-vents")
    public XBoolean getHasTrickleVents() {
        return hasTrickleVents;
    }

    public void setHasTrickleVents(final XBoolean hasTrickleVents) {
        this.hasTrickleVents = hasTrickleVents;
    }

	@Doc("The internal temperature of the house before the change; you probably want to record this in a variable.")
	@BindNamedArgument("temperature-before")
	@NotNull(message = "health-impact-of requires the temperature-before argument")
	@Prop(P.fromTemperature)
	public XNumber getFromTemperature() {
		return fromTemperature;
	}
	public void setFromTemperature(final XNumber fromEValue) {
		this.fromTemperature = fromEValue;
	}

	@Doc("The internal temperature of the house after the change.")
	@BindNamedArgument("temperature-after")
	@Prop(P.toTemperature)
	public XNumber getToTemperature() {
		return toTemperature;
	}
	public void setToTemperature(final XNumber toEValue) {
		this.toTemperature = toEValue;
	}

	@Doc("The permeability of the house before the change; you probably want to record this in a variable.")
	@BindNamedArgument("permeability-before")
	@NotNull(message = "health-impact-of requires the permeability-before argument")
	@Prop(P.fromPermeability)
	public XNumber getFromPermeability() {
		return fromPermeability;
	}
	public void setFromPermeability(final XNumber fromPermeability) {
		this.fromPermeability = fromPermeability;
	}

	@Doc("The permeability of the house after the change.")
	@BindNamedArgument("permeability-after")
	@Prop(P.toPermeability)
	public XNumber getToPermeability() {
		return toPermeability;
	}
	public void setToPermeability(final XNumber toPermeability) {
		this.toPermeability = toPermeability;
	}

	@Doc("The first year from now to include in the health impact (0 will include every year from now until now+horizon).")
	@BindNamedArgument("offset")
	@Prop(P.fromYear)
	public XNumber getFromYear() {
		return fromYear;
	}
	public void setFromYear(final XNumber fromYear) {
		this.fromYear = fromYear;
	}

	@Doc("The number of years over which to sum the health impact.")
	@BindNamedArgument("horizon")
	@Prop(P.toYear)
	public XNumber getToYear() {
		return toYear;
	}
	public void setToYear(final XNumber toYear) {
		this.toYear = toYear;
	}

	@Doc("The type of impact to compute.")
	@BindNamedArgument("on")
	@Prop(P.impact)
	public XImpact getImpact() {
		return impact;
	}
	public void setImpact(final XImpact impact) {
		this.impact = impact;
	}

	@BindNamedArgument("due-to")
	@Doc("The kinds of disease to consider.")
	@Prop(P.diseases)
	public List<XDisease> getDiseases() {
		return diseases;
	}
	public void setDiseases(final List<XDisease> diseases) {
		this.diseases = diseases;
	}

}
