package uk.org.cse.nhm.language.definition.two.hooks;

import java.util.ArrayList;
import java.util.List;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.two.dates.XDateSequence;

@Doc({      "Do some commands on a specific date or dates.",
            "",
            "This is one of the commands you will probably use most often.",
            "To make anything happen in a scenario, you need to say when it is going to happen.",
            "on.dates allows you to cause things to happen on particular simulation dates.",
            "",
            "If two on.dates elements schedule things to happen on the same date, the one",
            "which is written earlier in the scenario will happen first, so the second will 'see'",
            "the effects of the first.",
            "",
            "on.dates can trigger its commands on several dates, so if you want a similar thing to happen repeatedly you can supply a list of dates or 'regularly' as the first argument."
      })
@Bind("on.dates")
public class XDateHook extends XHook {
	public static final String SCHEDULING_OBSOLESCENCE = "We have implemented a new mechanism for scheduling events, which replaces policies/targets and some reporting elements.";
	
	public static final class P {
		public static final String dates = "dates";
	}
	private List<XDateSequence> dates = new ArrayList<>();
	
	@Doc("A list of the dates when the commands to run should be enacted; you can supply several literal dates as well as date-generating commands like 'regularly', 'scenario-start' and 'scenario-end' here in square brackets.")
	@Prop(P.dates)
	@BindPositionalArgument(0)
	public List<XDateSequence> getDates() {
		return dates;
	}

	public void setDates(final List<XDateSequence> dates) {
		this.dates = dates;
	}
}

