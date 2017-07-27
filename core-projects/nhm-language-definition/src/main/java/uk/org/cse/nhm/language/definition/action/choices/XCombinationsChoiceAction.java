package uk.org.cse.nhm.language.definition.action.choices;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.action.XDwellingAction;
import uk.org.cse.nhm.language.definition.action.XFlaggedDwellingAction;
import uk.org.cse.nhm.language.definition.action.choices.XChoiceAction.P;
import uk.org.cse.nhm.language.definition.sequence.IScopingElement;

/**
 * XCombinationsChoiceAction.
 *
 * @author trickyBytes
 */
@Unsuitability("none of the choices are suitable")
@Category(CategoryType.ACTIONCOMBINATIONS)
@Bind("combination.choice")
@Doc("An action tries combinations of one action from each group of actions and choices whichever matches the select rule")
public class XCombinationsChoiceAction extends XFlaggedDwellingAction implements IScopingElement {
    public static final String CATEGORY = "Choices and packages";

    public static final class P {
        public static final String SELECTOR = "selector";
        public static final String DELEGATES = "delegates";
    }
    
    private XChoiceSelector selector;
    private List<List<XDwellingAction>> delegates = new ArrayList<List<XDwellingAction>>();

    @BindRemainingArguments

    @Prop(P.DELEGATES)
    @Doc("A list of other actions, which this action will choose between, apply, etc.")
    @Size(min = 1, message = "all-of, any-of or choice action was empty: it must contain other actions which it will apply of chose between.")
    public List<List<XDwellingAction>> getDelegates() {
        return delegates;
    }

    public void setDelegates(final List<List<XDwellingAction>> delegates) {
        this.delegates = delegates;
    }
    
    @NotNull(message = "choice element must always contain a selector.")
    @BindNamedArgument("select")
    @Prop(P.SELECTOR)
    @Doc("The rule for selecting the winning action from this choice.")
    public XChoiceSelector getSelector() {
        return selector;
    }
    
    public void setSelector(final XChoiceSelector selector) {
        this.selector = selector;
    }
}
