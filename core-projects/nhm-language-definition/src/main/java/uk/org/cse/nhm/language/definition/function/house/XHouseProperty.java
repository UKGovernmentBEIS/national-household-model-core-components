package uk.org.cse.nhm.language.definition.function.house;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.XCategoryFunction;

@Bind("house.static-property")
@Doc("Returns the values of a house property as a categorical variable.")
public class XHouseProperty extends XCategoryFunction {

    public static final class P {

        public static final String name = "name";
    }

    private String name;

    @BindPositionalArgument(0)
    @NotNull(message = "house.static-property must always include the name of the property to look up.")
    @Doc("The name of the property to look up from the housing stock.")
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
