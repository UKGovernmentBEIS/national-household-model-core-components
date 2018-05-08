package uk.org.cse.nhm.language.definition.sequence;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.BindPositionalArgument;
import com.larkery.jasb.bind.Identity;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.XScenarioElement;

@Category(CategoryType.DECLARATIONS)
public abstract class XDeclaration extends XScenarioElement {

    public static final String CATEGORY = "Declarations and Variables";

    @Doc("The name of the variable to declare")
    @NotNull(message = "all declarations require a name, as their first unnamed argument")
    @Identity
    @BindPositionalArgument(0)
    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(final String name) {
        super.setName(name);
    }
}
