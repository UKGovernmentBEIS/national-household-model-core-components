package uk.org.cse.nhm.language.definition.action.choices;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.bool.XBoolean;

@Bind("select.filter")
@Doc("Restricts another choice selector's input options to those matching the contained test.")
public class XFilterSelector extends XChoiceSelector {

    public static final class P {

        public static final String test = "test";
        public static final String selector = "selector";
    }

    private XBoolean test;
    private XChoiceSelector selector;

    @Prop(P.test)
    @Doc("The test to filter alternatives with; only alternatives which pass this test will be considered by the contained selector.")

    @BindNamedArgument
    @NotNull(message = "select.filter must contain a test function to filter the alternative by.")
    public XBoolean getTest() {
        return test;
    }

    public void setTest(final XBoolean test) {
        this.test = test;
    }

    @Prop(P.selector)
    @Doc("The selector to choose between matching alternatives with.")

    @BindNamedArgument
    @NotNull(message = "select.filter must contain another selector to choose between the filtered alternatives.")
    public XChoiceSelector getSelector() {
        return selector;
    }

    public void setSelector(final XChoiceSelector selector) {
        this.selector = selector;
    }
}
