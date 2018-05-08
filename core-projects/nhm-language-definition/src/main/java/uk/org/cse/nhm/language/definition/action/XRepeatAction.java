package uk.org.cse.nhm.language.definition.action;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.bool.XBoolean;

@Doc("Repeat an action a certain number of times, whilst some conditions are true")
@Bind("action.repeat")
@Unsuitability(alwaysSuitable = true)
@Category(CategoryType.ACTIONCOMBINATIONS)
public class XRepeatAction extends XFlaggedDwellingAction {

    public static class P {

        public static final String times = "times";
        public static final String whileCondition = "whileCondition";
        public static final String untilCondition = "untilCondition";
        public static final String delegate = "delegate";
    }
    private int times = 10;
    private XBoolean whileCondition = null;
    private XBoolean untilCondition = null;
    private XDwellingAction delegate;

    @Prop(P.times)
    @BindNamedArgument
    @Doc("The maximum number of times to repeat the contained action")
    public int getTimes() {
        return times;
    }

    public void setTimes(final int times) {
        this.times = times;
    }

    @Doc({"Before the contained action is performed, this condition will be checked; if it is false, the repetition will stop.",
        "The condition is evaluated in the context of the repeat action, so for example (cost.sum) will be the sum of the cost of",
        "every repetition that has happened so far."})
    @BindNamedArgument("while")
    @Prop(P.whileCondition)
    public XBoolean getWhileCondition() {
        return whileCondition;
    }

    public void setWhileCondition(final XBoolean whileCondition) {
        this.whileCondition = whileCondition;
    }

    @Doc({"After the contained action is performed, this condition will be checked; if it is true, the repetition will stop.",
        "As with the while condition, this is evaluated in the context of the repeat action, so costs will accumulate."})
    @BindNamedArgument("until")
    @Prop(P.untilCondition)
    public XBoolean getUntilCondition() {
        return untilCondition;
    }

    public void setUntilCondition(final XBoolean untilCondition) {
        this.untilCondition = untilCondition;
    }

    @NotNull(message = "repeat requires an action to repeat")
    @Doc("This is the action which will be repeated; it will be attempted whether or not it is suitable")
    @BindPositionalArgument(0)
    @Prop(P.delegate)
    public XDwellingAction getDelegate() {
        return delegate;
    }

    public void setDelegate(final XDwellingAction delegate) {
        this.delegate = delegate;
    }
}
