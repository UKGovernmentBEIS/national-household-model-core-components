package uk.org.cse.nhm.language.definition.fuel;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XHouseMeterReading;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

@Doc({
	"A function which charges different per-unit prices for different levels of consumption. For example: standing charge + 2p each for first thousand units + 1p each for all subsequent units."
})
@Bind("function.stepped-pricing")
@Category(CategoryType.TARIFFS)
public class XSteppedPricing extends XNumber {
	public static class P {
		public static final String standingCharge = "standingCharge";
		public static final String alwaysApply = "alwaysApply";
		public static final String unitsFunction = "unitsFunction";
		public static final String ranges = "ranges";
	}
	
	private XNumber standingCharge;
	private boolean alwaysApply = true;
	private List<XRangeCharge> ranges = new ArrayList<XRangeCharge>();
	private XNumber unitsFunction = new XHouseMeterReading();
	
	
	@BindNamedArgument("standing-charge")
	@Prop(P.standingCharge)
	@Doc("The base charge which is independent of the number of units consumed.")
	public XNumber getStandingCharge() {
		return standingCharge;
	}
	
	public void setStandingCharge(final XNumber standingCharge) {
		this.standingCharge = standingCharge;
	}
	
	@BindNamedArgument("always-apply")
	
	@Prop(P.alwaysApply)
	@Doc("Determines whether the standing charge still applies if 0 units are consumed.")
	public boolean getAlwaysApply() {
		return alwaysApply;
	}

	public void setAlwaysApply(final boolean alwaysApply) {
		this.alwaysApply = alwaysApply;
	}

	
	@BindRemainingArguments
	@Prop(P.ranges)
	@Size(min = 1, message = "function.incremental-pricing must contain at least one range.")
	@Doc("Ranges which specify a charge for a particular portion of the units consumed..")
	public List<XRangeCharge> getRanges() {
		return ranges;
	}
	
	public void setRanges(final List<XRangeCharge> ranges) {
		this.ranges = ranges;
	}
	
	@BindNamedArgument("units")
	
	@Prop(P.unitsFunction)
	@NotNull(message = "function.incrememental-pricing must always contain an element to act as its units function, which will return the number of units which were consumed by a house.")
	@Doc("A function which returns the number of units which were consumed by a house. Defaults to the meter reading for the fuel for the containing charge.")
	public XNumber getUnitsFunction() {
		return unitsFunction;
	}
	
	public void setUnitsFunction(final XNumber unitsFunction) {
		this.unitsFunction = unitsFunction;
	}
}
