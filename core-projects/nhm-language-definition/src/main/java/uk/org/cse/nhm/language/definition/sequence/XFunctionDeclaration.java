package uk.org.cse.nhm.language.definition.sequence;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;
import com.larkery.jasb.bind.Identity;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.IScenarioElement;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.validate.contents.Declaration;

@Declaration
@Bind("def-function")
@Category(CategoryType.DECLARATIONS)
public class XFunctionDeclaration extends XNumber implements IScenarioElement<XElement> {

    public static final String VALUE = "value";
    private XNumber value;

    @Doc("The name of the function to declare")
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

    @BindPositionalArgument(1)
    @NotNull(message = "def-function requires a function as its second argument")
    @Doc("The function itself")
    @Prop(VALUE)
    public XNumber getValue() {
        return value;
    }

    public void setValue(final XNumber value) {
        this.value = value;
    }
}
