package uk.org.cse.nhm.language.definition.reporting.aggregate;

import java.util.ArrayList;
import java.util.List;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.XFunction;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;
import uk.org.cse.nhm.language.definition.group.XGroup;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindRemainingArguments;


@Bind("division.by-combination")
@Doc(
		value = {
			"This division mechanism separates cases according to the cross product of some sets of values.",
			"The values are defined by child elements"
		})
public class XCrossDivision extends XDivision implements IHouseContext{
	public static final class P {
		public static final String source = "source";
		public static final String categories = "categories";
	}
	private XGroup source;
	private List<XFunction> categories = new ArrayList<>();
	
	
	@BindNamedArgument
	@Doc("The subdivisions will be drawn from the houses in this group.")
	public XGroup getSource() {
		return source;
	}
	public void setSource(final XGroup source) {
		this.source = source;
	}
	
	
	@BindRemainingArguments
	@Doc("Each of these functions will be used as a dimension on which to cut the source group.")
	public List<XFunction> getCategories() {
		return categories;
	}
	public void setCategories(final List<XFunction> categories) {
		this.categories = categories;
	}
}
