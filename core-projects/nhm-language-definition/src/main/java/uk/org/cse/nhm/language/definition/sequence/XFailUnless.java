package uk.org.cse.nhm.language.definition.sequence;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.bool.XBoolean;

@Doc({"A shorthand for using action.case and action.fail to trigger the failure of an action in some condition."})
@Bind("fail-unless")
@Category(CategoryType.ACTIONCOMBINATIONS)
public class XFailUnless extends XBindingAction {
	public static final class P {
        public static final String condition = "condition";
    }
	
    private XBoolean condition = null;

    @Prop(P.condition)
    @Doc({"The test condition to use. If this does not work out to be true, this action will fail.",
                "If this action is used inside a do command, this will cause the do to fail as well."})
    @NotNull(message="fail-unless must have a logical test for its first argument")
	@BindPositionalArgument(0)
    public XBoolean getCondition() {
        return condition;
	}
    public void setCondition(XBoolean condition) {
        this.condition = condition;
	}
}
