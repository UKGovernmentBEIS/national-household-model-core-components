package uk.org.cse.nhm.language.definition.two.dates;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;

import com.google.common.collect.ImmutableList;
import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.two.build.IBuilder;

@Doc({
    "Represents a series of regularly spaced dates, produced by taking the start date, and adding the interval to it until the end date is reached.",
    "",
    "Just writing 'regularly' produces annual dates from the scenario's start date through to its end date.",
    "",
    "Date sequences can also be written more briefly as two dates separated by two dots, like 01/01/2014..01/01/2020.",
    "This would produce annual dates on the first of each year from 2014 to 2020.",
    "",
    "You can even write 2014..2020 as a shorthand for the first of January.",
    "If you leave out one of these two dates, the range implicitly runs from the scenario start or end date.",
    "For example ..2020 means 'from the start of the run, until 2020', and '2020..' means 'from 2020, until the end of the run'."
})
@Bind("regularly")
public class XIntervalSequence extends XDateSequence {

    private Period every = PeriodFormat.getDefault().parsePeriod("1 year");
    private XDate from = new XSimStartDate();
    private XDate until = new XSimEndDate();

    @Doc("The interval to add to produce the dates in the period")
    @BindNamedArgument
    public Period getEvery() {
        return every;
    }

    public void setEvery(final Period period) {
        this.every = period;
    }

    @Doc("The start date - this is always included in the result")
    @BindNamedArgument
    public XDate getFrom() {
        return from;
    }

    public void setFrom(final XDate from) {
        this.from = from;
    }

    @Doc("The end date - this is only included in the result if it is a whole number of intervals from the start date")
    @BindNamedArgument
    public XDate getUntil() {
        return until;
    }

    public void setUntil(final XDate to) {
        this.until = to;
    }

    @Override
    public List<DateTime> asDates(final IBuilder builder) {
        final ImmutableList.Builder<DateTime> b = ImmutableList.builder();
        DateTime current = from.asDate(builder);
        final DateTime end = until.asDate(builder);

        while (current.isBefore(end)) {
            b.add(current);
            current = current.plus(every);
        }

        if (current.equals(end)) {
            b.add(current);
        }

        return b.build();
    }
}
