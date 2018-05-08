package uk.org.cse.nhm.language.definition.exposure;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;

@Bind("schedule.repeat")
@Doc(
        {
            "This exposure periodically samples from the group using the supplied sampler,",
            "and then exposes all sampled houses to the action."
        }
)
public class XIntervalSchedule extends XSchedule {

    public static final class P {

        public static final String START_DATE = "startDate";
        public static final String END_DATE = "endDate";
        public static final String INTERVAL = "interval";
        public static final String SAMPLER = "sampler";
    }

    private DateTime startDate;
    private DateTime endDate;
    private Period interval = PeriodFormat.getDefault().parsePeriod("1 year");
    private XSampler sampler;

    @Prop(P.START_DATE)

    @BindNamedArgument("start-date")
    @Doc("The date on which to start exposing houses - if absent, the simulation start date is used.")
    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(final DateTime startDate) {
        this.startDate = startDate;
    }

    @Prop(P.END_DATE)

    @BindNamedArgument("end-date")
    @Doc("The latest date on which houses may be sampled - if absent, the simulation end date is used.")
    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(final DateTime endDate) {
        this.endDate = endDate;
    }

    @Prop(P.INTERVAL)
    @BindNamedArgument("interval")

    @Doc("The interval for houses (e.g. 1 year). The houses will be exposed at whole number multiples of this period from the start date, until that would pass the end date.")
    public Period getInterval() {
        return interval;
    }

    public void setInterval(final Period interval) {
        this.interval = interval;
    }

    @Prop(P.SAMPLER)

    @Doc("A sampler, which will be used when an exposure event is scheduled to happen.")
    @BindPositionalArgument(0)
    public XSampler getSampler() {
        return sampler;
    }

    public void setSampler(final XSampler sampler) {
        this.sampler = sampler;
    }
}
