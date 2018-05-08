package uk.org.cse.nhm.language.definition.exposure;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.joda.time.DateTime;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.XElement;

@Bind("schedule.time-series")
@Doc(
        {
            "An exposure function which defines a sequence of dates and sampling rules.",
            "On each date, the associated sampling rule will be used to choose houses for exposure."
        }
)
public class XTimeSeriesSchedule extends XSchedule {

    public static final class P {

        public static final String ENTRY_ON = "on";
        public static final String ENTRY_SAMPLER = "sampler";
        public static final String ENTRIES = "entries";
    }

    private List<Entry> entries = new ArrayList<Entry>();

    @Bind("expose")

    public static class Entry extends XElement {

        private DateTime on;
        private XSampler sampler;

        @BindNamedArgument
        @Prop(P.ENTRY_ON)
        @Doc("The date on which to take expose a sample of the source group.")
        @NotNull(message = "entry must define a date for 'on'.")
        public DateTime getOn() {
            return on;
        }

        public void setOn(final DateTime on) {
            this.on = on;
        }

        @BindPositionalArgument(0)
        @Prop(P.ENTRY_SAMPLER)
        @Doc("A sampling rule, determining how the exposed houses should be selected.")
        @NotNull(message = "entry must contain a sampling function.")
        public XSampler getSampler() {
            return sampler;
        }

        public void setSampler(final XSampler sampler) {
            this.sampler = sampler;
        }
    }

    @BindRemainingArguments
    @Doc("The sequence of sampling rules and dates for this exposure.")
    @Prop(P.ENTRIES)
    @Size(min = 1, message = "schedule.timeseries must contain at least one entry")
    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(final List<Entry> entries) {
        this.entries = entries;
    }
}
