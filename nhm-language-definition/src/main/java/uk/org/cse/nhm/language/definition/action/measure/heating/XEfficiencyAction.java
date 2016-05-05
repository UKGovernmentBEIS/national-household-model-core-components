package uk.org.cse.nhm.language.definition.action.measure.heating;

import java.util.Deque;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.action.XFlaggedDwellingAction;
import uk.org.cse.nhm.language.definition.enums.XChangeDirection;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.validate.ISelfValidating;


@Bind("action.change-efficiency")
@Doc({
	"Changes the efficiency of a heating system.",
	"Has no effect on electric resistive heating systems, which are always 100% efficient.",
	})
public class XEfficiencyAction extends XFlaggedDwellingAction implements ISelfValidating {
	public static class P {
		public static final String DIRECTION = "direction";
	}
	
	private XChangeDirection direction = XChangeDirection.Set;
	private XNumber winterEfficiency = null;
	private XNumber summerEfficiency = null;

	@Prop(XBoilerMeasure.P.WINTER_EFFICIENCY)
	@BindNamedArgument("winter-efficiency")
	@Doc({
		"The desired new efficiency number as a proportion or percentage.",
		"For boilers this is their winter efficiency.",
		"For heat pumps this is their COP",
		"Other heating systems will take this as their main efficiency.",
		"If this number is not set, the winter efficiency will be unchanged by this action."
	})
	public XNumber getWinterEfficiency() {
		return winterEfficiency;
	}

	public void setWinterEfficiency(final XNumber winterEfficiency) {
		this.winterEfficiency = winterEfficiency;
	}
	
	@Prop(XBoilerMeasure.P.SUMMER_EFFICIENCY)
	@BindNamedArgument("summer-efficiency")
	@Doc({
		"The desired new summer efficiency number as a proportion or percentage.",
		"If this number is not set, the summer efficiency will be unchanged by this action.",
		"If this number is set to 0 or a negative number, then it will be added to the winter efficiency to calculate the new value.",
		"Summer Efficiency only affects boilers - other types of heating system will ignore this number."
	})
	public XNumber getSummerEfficiency() {
		return summerEfficiency;
	}
	
	public void setSummerEfficiency(final XNumber summerEfficiency) {
		this.summerEfficiency = summerEfficiency;
	}

	@Prop(P.DIRECTION)
	@BindNamedArgument
	@Doc("Determines whether we are improving bad heater, degrading good heaters, or setting the efficiency on all heaters.")
	public XChangeDirection getDirection() {
		return direction;
	}

	public void setDirection(final XChangeDirection direction) {
		this.direction = direction;
	}

	@Override
	public List<IError> validate(Deque<XElement> context) {
		if (summerEfficiency == null && winterEfficiency == null) {
			return ImmutableList.of(BasicError.warningAt(getLocation(), "This use of action.change-efficiency will not do anything, since neither winter-efficiency nor summer-efficiency were set."));
		} else {
			return ImmutableList.of();
		}
	}
}
