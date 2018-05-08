package uk.org.cse.nhm.language.definition.two.actions;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.XFunction;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;
import uk.org.cse.nhm.language.definition.two.selectors.ISetOfHouses;
import uk.org.cse.nhm.language.definition.two.selectors.XAllTheHouses;
import uk.org.cse.nhm.language.validate.BatchForbidden;

@Bind("transitions")
@BatchForbidden(element = "transitions")
@Doc(
        value = {
            "Defines a report which tracks the transition of houses between a set of mutually exclusive conditions.",
            "The resulting report is a Sankey diagram, in which the passage of houses from one of the <sgmltag>when</sgmltag> clauses below to another",
            "is illustrated by an arrow, with which the time of the change and the target causing the change are associated.",
            "The intention of this is to allow you to easily see, for example, how policies are changing the technologies present in a house",
            "over the course of a simulation."
        }
)
@Category(CategoryType.REPORTING)
public class XTransitionHookAction extends XHookAction implements IHouseContext {

    public static final class P {

        public static final String source = "Source";
        public static final String divideBy = "divideBy";
    }

    private ISetOfHouses source = new XAllTheHouses();
    private List<XFunction> divideBy = new ArrayList<>();

    @BindNamedArgument("source")
    @NotNull(message = "transitions must always have a source")
    @Doc("The set of houses which we are investigating dwellings moving into or out of.")
    public ISetOfHouses getSource() {
        return source;
    }

    public void setSource(final ISetOfHouses source) {
        this.source = source;
    }

    @BindNamedArgument("divide-by")
    @Doc("The source group will be sub-divided by these functions.")
    public List<XFunction> getDivideBy() {
        return divideBy;
    }

    public void setDivideBy(final List<XFunction> divideBy) {
        this.divideBy = divideBy;
    }
}
