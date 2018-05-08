package uk.org.cse.nhm.language.definition.sequence;

import java.util.ArrayList;
import java.util.List;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;

@Doc({"Decreases the values stored in some variables by the given amount or amounts.",
    "If you give multiple variables and a single value, all variables will be decreased by the same amount.",
    "Otherwise, each variable will be decreased by the corresponding value."
})
@Bind("decrease")
public class XDecreaseAction extends XVarSetAction {

    @Override
    protected List<XNumber> getDefaultValue() {
        final ArrayList<XNumber> result = new ArrayList<>();
        result.add(XNumberConstant.create(1));
        return result;
    }
}
