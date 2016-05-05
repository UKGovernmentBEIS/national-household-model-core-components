package uk.org.cse.nhm.language.definition.sequence;

import java.util.ArrayList;
import java.util.List;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;

import com.larkery.jasb.bind.Bind;

@Doc({"Increases the values stored in some variables by the given amount or amounts.",
        "If you give multiple variables and a single value, all variables will be increased by the same amount.",
        "Otherwise, each variable will be increased by the corresponding value."
        })
@Bind("increase")
public class XIncreaseAction extends XVarSetAction {

	@Override
	protected List<XNumber> getDefaultValue() {
        final ArrayList<XNumber> result = new ArrayList<>();
		result.add(XNumberConstant.create(1));
        return result;
	}
}
