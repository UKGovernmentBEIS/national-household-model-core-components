package uk.org.cse.nhm.language.definition.sequence;

import java.util.ArrayList;
import java.util.List;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

import com.larkery.jasb.bind.Bind;

@Doc({"Sets the value of one or more variables to new values.",
        "If you give multiple variables and a single value, all variables will be set to the same amount.",
        "Otherwise, each variable will be set to the corresponding value."
        })
@Bind("set")
public class XSetAction extends XVarSetAction {
	@Override
	protected List<XNumber> getDefaultValue() {
		return new ArrayList<>();
	}
}
