package uk.org.cse.nhm.language.definition.action;

import javax.validation.constraints.NotNull;

import org.joda.time.Period;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;

@Bind("action.delayed")
@Doc({
	"Schedules the action: argument to happen on a dwelling after a delay.",
	"Keeps copies of any values saved in vars (but not snapshots) by a let or choice. Makes these temporarily available again while action: is running.",
	"The action will not be triggered if the house has been destroyed in the interim."
})
@Category(CategoryType.ACTIONCOMBINATIONS)
public class XDelayedAction extends XFlaggedDwellingAction {
	public static class P {
		public static final String action = "action";
		public static final String delay = "delay";
	}
	
	private XDwellingAction action;
	private Period delay;
	
	@BindNamedArgument
	@Prop(P.action)
	@Doc("The action which will be performed once the delay has ellapsed.")
	@NotNull(message = "action.delayed must contain an action to be performed in the future.")
	public XDwellingAction getAction() {
		return action;
	}
	
	public void setAction(final XDwellingAction action) {
		this.action = action;
	}
	
	@BindNamedArgument
	@Prop(P.delay)
	@Doc("The delay after which the action will be performed.")
	@NotNull(message = "ation.delayed must contain a delay.")
	public Period getDelay() {
		return delay;
	}
	
	public void setDelay(final Period delay) {
		this.delay = delay;
	}
}
