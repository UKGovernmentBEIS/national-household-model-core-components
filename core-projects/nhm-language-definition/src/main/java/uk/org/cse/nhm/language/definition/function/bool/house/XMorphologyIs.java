package uk.org.cse.nhm.language.definition.function.bool.house;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.enums.XMorphologyType;
import uk.org.cse.nhm.language.definition.function.bool.XHouseBoolean;

@Bind("house.morphology-is")
@Doc("A test which matches houses with a particular morphology (RUMORPH)")
public class XMorphologyIs extends XHouseBoolean {

    public static final class P {

        public static final String EQUAL_TO = "equalTo";
    }

    private XMorphologyType equalTo;

    public void setEqualTo(final XMorphologyType equalTo) {
        this.equalTo = equalTo;
    }

    @Prop(P.EQUAL_TO)

    @BindPositionalArgument(0)
    @Doc("The morphology which a house must have to pass this test")
    public XMorphologyType getEqualTo() {
        return equalTo;
    }
}
