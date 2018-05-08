package uk.org.cse.nhm.language.definition.function.bool.house;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.XDwellingAction;
import uk.org.cse.nhm.language.definition.function.bool.XHouseBoolean;

@Bind("house.is-suitable-for")
@Doc("Tests whether a house is suitable for a particular measure or choice.")
public class XSuitableFor extends XHouseBoolean {

    public final static class P {

        public final static String FOR = "for";
    }

    private XDwellingAction action;

    @BindPositionalArgument(0)

    @Prop(P.FOR)
    @Doc("The ID of the measure or choice which will be used as the test.")
    @NotNull(message = "house.is-suitable must have 'for' specified (the action whose suitability is to be tested)")
    public XDwellingAction getFor() {
        return action;
    }

    public void setFor(final XDwellingAction action) {
        this.action = action;
    }
}
