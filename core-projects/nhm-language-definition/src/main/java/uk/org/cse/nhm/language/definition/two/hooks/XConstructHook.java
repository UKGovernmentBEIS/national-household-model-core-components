package uk.org.cse.nhm.language.definition.two.hooks;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.action.XConstructAction;
import uk.org.cse.nhm.language.definition.two.dates.XDate;
import uk.org.cse.nhm.language.definition.two.dates.XSimEndDate;
import uk.org.cse.nhm.language.definition.two.dates.XSimStartDate;
import uk.org.cse.nhm.language.definition.two.selectors.XAffectedHouses;
import uk.org.cse.nhm.language.validate.contents.ForbidChild;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

@Doc({      "Run some commands when new houses are constructed.",
            "",
            "The simulator constructs new dwellings at the start of the run for every",
            "case in the stock whose build year is less than or equal to the starting year, and",
            "during the run if the stock contains any cases whose build year occurs between",
            "the scenario's start and end dates.",
            "",
            "This element will run the commands it contains whenever any houses are constructed.",
            "In addition, within this element the 'affected-houses' set contains the newly built dwellings",
            "so you can apply changes to new builds specifically by targetting that population.",
            "",
            "You cannot construct more houses within on.construction, in the interests of keeping things simple."
            })
@Bind("on.construction")
@SeeAlso(XAffectedHouses.class)
@ForbidChild(XConstructAction.class)
public class XConstructHook extends XHook {
	public static final class P {
		public static final String from = "from";
        public static final String until = "until";
	}

    private XDate from = new XSimStartDate();

    @Doc("This command will only be triggered for dwellings constructed on or after this date.")
    @Prop(P.from)
    @BindNamedArgument
    public XDate getFrom() {
        return from;
    }
    
    public void setFrom(XDate from) {
        this.from = from;
    }

    private XDate until = new XSimEndDate();

    @Doc("This command will only be triggered for dwellings constructed on or before this date.")
    @Prop(P.until)
    @BindNamedArgument
    public XDate getUntil() {
        return until;
    }
    
    public void setUntil(XDate until) {
        this.until = until;
    }
}

