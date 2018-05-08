package uk.org.cse.nhm.language.definition.exposure;

import org.joda.time.Period;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;

@Bind("schedule.on-group-entry")
@Doc({
    "When some houses enter the sampled group, this exposure function will",
    "select all of the newly entered houses and apply the related action to them,",
    "possibly with the specified delay."
})
public class XExposeOnEntry extends XExposure {

    public static final class P {

        public static final String delay = "delay";
    }
    private Period delay = Period.ZERO;

    @BindNamedArgument
    @Prop(P.delay)
    @Doc("The delay between houses entering the group, and being exposed to the action.")
    public Period getDelay() {
        return delay;
    }

    public void setDelay(Period delay) {
        this.delay = delay;
    }
}
