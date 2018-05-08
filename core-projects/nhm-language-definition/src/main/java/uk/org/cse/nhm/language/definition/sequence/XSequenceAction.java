package uk.org.cse.nhm.language.definition.sequence;

import java.util.ArrayList;
import java.util.List;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.XDwellingAction;
import uk.org.cse.nhm.language.definition.action.XFlaggedDwellingAction;

@Doc({"Perform several actions in sequence for one house, making their side-effects available for modifying variables.",
    "",
    "When do is applied to a house, the first measure it contains will be applied with the house in its original condition,",
    "then the second measure will be applied to the house after the first, and so on.",
    "",
    "If the all: argument is not set to false, then the unsuitability of any measure will cause the unsuitability of the entire package.",
    "For example, if you install insulation and a boiler in a package, but the insulation is not suitable, the boiler will not be installed even if it is suitable.",
    "",
    "This means you can use do to undo things if they don't work out, using action.case and action.fail to test after-the-fact.",
    "The do command also has a special behaviour with regard to the set, increase and decrease actions:",
    "when using these actions within a do statement, special functions like capital-cost and net-cost refer to the",
    "capital cost and net cost of the do statement so-far."
})
@Bind("do")
@Category(CategoryType.ACTIONCOMBINATIONS)
public class XSequenceAction extends XFlaggedDwellingAction implements IScopingElement {

    public static final class P {

        public static final String actions = "actions";
        public static final String all = "all";
        public static final String hide = "hide";
    }

    private List<XNumberDeclaration> hide = new ArrayList<>();
    private boolean all = true;
    private List<XDwellingAction> actions = new ArrayList<>();

    @BindNamedArgument
    @Doc({"If all: is true, the measures will either all be installed if they are all suitable or none of them will be if at least one is not.",
        "If any of them do fail, the whole do will be considered to have failed and will be unsuitable as well.",
        "",
        "If all: is false, the measures will be attempted in order, and any that fail will be skipped over.",
        "However, in this case the do statement as a whole will always be considered suitable/successful."
    })
    public boolean isAll() {
        return all;
    }

    public void setAll(final boolean all) {
        this.all = all;
    }

    @BindNamedArgument
    @Doc("If true, changes to the given declarations will be hidden from the scenario above this do")
    public List<XNumberDeclaration> getHide() {
        return hide;
    }

    public void setHide(final List<XNumberDeclaration> hide) {
        this.hide = hide;
    }

    @Doc({"The actions to perform on the house, in order. The second action sees the effects of the first, the third the first two, and so on.",
        "",
        "Unless all: is false, the failure or unsuitability of any of these actions will prevent any others from being installed.",
        "",
        "Any set, increase, or decrease actions have their values computed with respect to the total effect of the actions already",
        "performed within this do command. This means that, for example, you can use set to store the capital-cost of the do statement in a variable."
    })
    @BindRemainingArguments
    public List<XDwellingAction> getActions() {
        return actions;
    }

    public void setActions(final List<XDwellingAction> actions) {
        this.actions = actions;
    }
}
