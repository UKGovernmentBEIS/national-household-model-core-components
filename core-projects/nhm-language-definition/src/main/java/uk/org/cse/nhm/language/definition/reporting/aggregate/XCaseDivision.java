package uk.org.cse.nhm.language.definition.reporting.aggregate;

import java.util.ArrayList;
import java.util.List;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.bool.XBoolean;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;
import uk.org.cse.nhm.language.definition.group.XGroup;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindRemainingArguments;

@Doc("A division rule which uses a sequence of tests to split the population into mutually exclusive subsets.")

@Bind("division.by-case")
public class XCaseDivision extends XDivision implements IHouseContext {
	public static final class P {
		public static final String source = "source";
		public static final String cases = "cases";
	}
	
	private XGroup source;
	private List<XBoolean> cases = new ArrayList<XBoolean>();
	
	
	@Doc("If specified, the houses considered in this division will only be those which are also in this source group.")
	@BindNamedArgument("source")
	public XGroup getSource() {
		return source;
	}

	public void setSource(final XGroup source) {
		this.source = source;
	}

	
	@BindRemainingArguments
	@Doc("Each of these conditions creates a group which will contain any houses not matched by a previous condition that pass the condition.")
	public List<XBoolean> getCases() {
		return cases;
	}
	
	public void setCases(final List<XBoolean> cases) {
		this.cases = cases;
	}
}
