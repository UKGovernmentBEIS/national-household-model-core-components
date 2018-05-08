package uk.org.cse.nhm.language.definition.action;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

@Bind("action.sometimes")
@Doc({
    "An action which delegates to another action, but introduces an extra chance of success or failure.",
    "This extra chance is independent of anything which would ordinarily cause its delegate action to fail."
})
@Category(CategoryType.ACTIONCOMBINATIONS)
public class XSometimesAction extends XFlaggedDwellingAction {

    public static class P {

        public static final String delegate = "delegate";
        public static final String chance = "chance";
    }

    private XNumber chance;
    private XDwellingAction delegate;

    @Prop(P.chance)
    @BindNamedArgument
    @Doc("The chance that the action will succeed.")
    @NotNull(message = "action.sometimes must always define a chance, which is the probability that the action succeeds.")
    public XNumber getChance() {
        return chance;
    }

    public void setChance(final XNumber chance) {
        this.chance = chance;
    }

    @Prop(P.delegate)
    @BindPositionalArgument(0)
    @Doc("The action which may or may not be run depending on chance.")
    @NotNull(message = "action.sometimes must always include another action, which may or may not be run depending on chance.")
    public XDwellingAction getDelegate() {
        return delegate;
    }

    public void setDelegate(final XDwellingAction delegate) {
        this.delegate = delegate;
    }
}
