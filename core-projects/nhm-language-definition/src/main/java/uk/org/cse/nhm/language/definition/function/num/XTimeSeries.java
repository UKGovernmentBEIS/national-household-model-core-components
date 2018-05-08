package uk.org.cse.nhm.language.definition.function.num;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.language.validate.timeseries.UniqueDates;

@Bind("function.time-series")
@Doc(value = {
    "Defines a number which changes as time progresses."
})
@UniqueDates
@Category(CategoryType.ARITHMETIC)
public class XTimeSeries extends XNumber {

    public static final class P {

        public static final String INITIAL = "initial";
        public static final String ON = "on";
    }

    private Double initial;
    private List<XOn> on = new ArrayList<>();

    @NotNull(message = "function.time-series must always contain an initial value.")
    @Doc("The initial value of the time series function, before the time has progressed past any 'on' elements.")
    @Prop(P.INITIAL)

    @BindNamedArgument
    public Double getInitial() {
        return initial;
    }

    public void setInitial(final Double initial) {
        this.initial = initial;
    }

    @Doc("A value of the time series function. Each value comes into effect as soon as the simulation reaches the specified date, replacing whichever value was previously in effect.")
    @Prop(P.ON)
    @BindRemainingArguments

    public List<XOn> getOn() {
        return on;
    }

    public void setOn(final List<XOn> on) {
        this.on = on;
    }

    @Doc("Defines the value of a timeseries on a given date")
    @Bind("on")
    public static class XOn extends XElement {

        public static final class P {

            public static final String DATE = "date";
            public static final String VALUE = "value";
        }

        private DateTime date;
        private double value;

        @NotNull(message = "on elements must always have a date.")
        @Doc("The date from which this value will take effect.")
        @Prop(P.DATE)

        @BindPositionalArgument(0)
        public DateTime getDate() {
            return date;
        }

        public void setDate(final DateTime date) {
            this.date = date;
        }

        @NotNull(message = "initial and on elements must always have a value.")
        @Doc("The number to be used.")
        @Prop(P.VALUE)

        @BindPositionalArgument(1)
        public double getValue() {
            return value;
        }

        public void setValue(final double value) {
            this.value = value;
        }
    }

    private XForesightLevel foresight = null;

    @BindNamedArgument
    @Doc({"The foresight level required to predict the change to this time series in a prediction function like predict-sum.",
        "If unset, this is determined from the foresight level where the function is being used; for example, within",
        "the tariff definition this will have foresight level Tariffs."})
    public XForesightLevel getForesight() {
        return foresight;
    }

    public void setForesight(final XForesightLevel foresight) {
        this.foresight = foresight;
    }
}
