package uk.org.cse.nhm.language.definition.money.obligations;

import javax.validation.constraints.NotNull;

import org.joda.time.Period;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.bool.XBoolean;

@Bind("periodic-payment")
@Doc({
    "The obligation created will make payments at fixed intervals. It will cease when either the lifetime has passed or the while condition is no longer true.",
    "If neither a lifetime nor a while condition has been specified, the obligation will continue until the scenario ends.",
    "Keeps copies of any values saved in vars (but not snapshots) by a let or choice. Makes these temporarily available again for calculating the while condition."
})

public class XPeriodicPayment extends XPaymentSchedule {

    public static class P {

        public static final String interval = "interval";
        public static final String lifetime = "lifetime";
        public static final String whileCondition = "whileCondition";
    }

    private Period interval;
    private Period lifetime;
    private XBoolean whileCondition;

    @Prop(P.interval)
    @Doc("The period of time between payments.")
    @NotNull(message = "finance.with-obligation must specify the interval between payments.")
    @BindNamedArgument
    public Period getInterval() {
        return interval;
    }

    public void setInterval(final Period interval) {
        this.interval = interval;
    }

    @Prop(P.lifetime)
    @Doc("A period of time after which the obligation will cease to generate payments.")
    @BindNamedArgument
    public Period getLifetime() {
        return lifetime;
    }

    public void setLifetime(final Period lifetime) {
        this.lifetime = lifetime;
    }

    @Prop(P.whileCondition)
    @Doc("A test which will be evaluated before each payment. If it is true, the payment will succeed and the obligation will continue. Otherwise, the payment will not be made and the obligation will be terminated.")
    @BindNamedArgument("while")
    public XBoolean getWhileCondition() {
        return whileCondition;
    }

    public void setWhileCondition(final XBoolean whileCondition) {
        this.whileCondition = whileCondition;
    }
}
