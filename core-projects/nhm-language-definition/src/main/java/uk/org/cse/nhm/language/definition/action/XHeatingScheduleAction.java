package uk.org.cse.nhm.language.definition.action;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.XElement;

@Bind("action.set-heating-schedule")
@Doc(value = {
    "An action which sets the heating schedule for the houses it is used against.",
    "This has no effect in SAP 2012 mode."
})
public class XHeatingScheduleAction extends XFlaggedDwellingAction {

    public static final class P {

        public static final String schedule = "schedule";
    }

    @Doc("Defines the hours on a set of days when the heating is on")
    @Bind("schedule")
    public static class XHeatingDays extends XElement {

        public static final class P {

            public static final String on = "on";
            public static final String heating = "heating";
        }

        @Doc("Defines a heating day or days")
        public enum XDayType {
            Monday,
            Tuesday,
            Wednesday,
            Thursday,
            Friday,
            Saturday,
            Sunday,
            @Doc("Mon-Fri")
            Weekdays,
            @Doc("Sat and Sun.")
            Weekends,
            @Doc("All week.")
            AllDays
        }

        @Doc({
            "A heating interval - defines a period of time when the heating will be on.",
            "The interval will be inclusive of the first hour and exclusive of the last hour, so heating between",
            "10 and 11 is from 10:00 to 11:00. Consequently heating between 10 and 10, for example, is no heating at all."
        })
        @Bind("heating")
        public static class HeatingInterval extends XElement {

            private int between;
            private int and;

            @BindNamedArgument
            @Doc("One end of the range in which the heating will be on. An hour of the day (00-24).")
            @Min(0)
            @Max(24)
            public int getBetween() {
                return between;
            }

            public void setBetween(final int from) {
                this.between = from;
            }

            @Doc("The other end of the range in which the heating will be on. An hour of the day (00-24).")

            @BindNamedArgument
            @Min(0)
            @Max(24)
            public int getAnd() {
                return and;
            }

            public void setAnd(final int to) {
                this.and = to;
            }
        }

        private XDayType on;
        private List<HeatingInterval> heating = new ArrayList<HeatingInterval>();

        @BindNamedArgument("on")
        @Doc("Defines the day or days of the week when this schedule pertains.")
        public XDayType getOn() {
            return on;
        }

        public void setOn(final XDayType day) {
            this.on = day;
        }

        @BindRemainingArguments
        @Doc({"Each of these defines a period of the day (or days) for this part of the heating schedule in which the heating will be on.",
            "The heating is on in the union of all the periods so specified, and off the rest of the time."})
        public List<HeatingInterval> getHeating() {
            return heating;
        }

        public void setHeating(final List<HeatingInterval> intervals) {
            this.heating = intervals;
        }
    }

    private List<XHeatingDays> schedule = new ArrayList<XHeatingDays>();

    @Doc({
        "A sequence of daily heating schedules; each such schedule specifies hours of heating for some days.",
        "The total heating schedule is the union of all of these, and each one is the union of the times it specifies.",
        "For example, you can set the heating to be on every day at 10-11 with an AllDays schedule, and then set it to be on 13-14",
        "at the weekends with a Weekends schedule."
    })

    @BindRemainingArguments
    public List<XHeatingDays> getSchedule() {
        return schedule;
    }

    public void setSchedule(final List<XHeatingDays> days) {
        this.schedule = days;
    }
}
