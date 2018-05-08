package uk.org.cse.nhm.language.definition.sequence;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.XDwellingAction;
import uk.org.cse.nhm.language.definition.function.num.IHypotheticalContext;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.validate.contents.ActsAsParent;

public abstract class XVarSetAction extends XBindingAction {

    public static class P {

        public static final String variable = "variable";
        public static final String value = "value";
        public static final String under = "under";
    }

    private List<XNumberDeclaration> variable = new ArrayList<>();
    ;
	private List<XNumber> value = getDefaultValue();

    @Doc("The declarations of the variables to change - use #variable to refer to a declaration from elsewhere")
    @Prop(P.variable)
    @Size(message = "set, increase, and decrease need at least one variable to set", min = 1)
    @BindPositionalArgument(0)
    public List<XNumberDeclaration> getVariable() {
        return variable;
    }

    protected abstract List<XNumber> getDefaultValue();

    public void setVariable(final List<XNumberDeclaration> variable) {
        this.variable = variable;
    }

    @Doc({"The functions to use to compute the new value or adjustment",
        "If a single value is given here, but multiple variables are specified, all of the variables",
        "will be affected in the same way.",
        "Otherwise, the first value will be used to modify the first variable, the second the second, and so on.",
        "These functions and the effect of using them are computed in the given order, and so the computed value of one variable",
        "is available for use when computing the new values of subsequent variables."
    })
    @Prop(P.value)
    @BindPositionalArgument(1)
    @Size(message = "set, increase, and decrease need at least one value to use", min = 1)
    public List<XNumber> getValue() {
        return value;
    }

    public void setValue(final List<XNumber> value) {
        this.value = value;
    }

    private List<XDwellingAction> under = new ArrayList<>();

    @Doc({"If any actions are provided here, the values used will be computed in a hypothetical state",
        "in which these actions had been applied to the house.",
        "For example, this could be used to compute and store several values",
        "under a different set of u-value assumptions by using action.reset-walls to change the u-values.",
        "Note that it is the values which are computed in the hypothetical state; the variables will be changed."})
    @BindNamedArgument
    @Prop(P.under)
    @ActsAsParent(IHypotheticalContext.class)
    public List<XDwellingAction> getUnder() {
        return under;
    }

    public void setUnder(List<XDwellingAction> under) {
        this.under = under;
    }
}
