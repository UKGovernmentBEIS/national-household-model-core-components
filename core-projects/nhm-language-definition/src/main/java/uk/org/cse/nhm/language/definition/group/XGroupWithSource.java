package uk.org.cse.nhm.language.definition.group;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;

import com.larkery.jasb.bind.BindNamedArgument;

public abstract class XGroupWithSource extends XGroup {
	public static final class P {
		public static final String SOURCE = "source";
	}
	
	private XGroup source = XAllHousesGroup.create();

	
	@Prop(P.SOURCE)
	@BindNamedArgument("source")
	@Doc("The source for this group - only houses in the source can be present in this group. Defaults to all houses.")
	public XGroup getSource() {
		return source;
	}

	public void setSource(final XGroup source) {
		this.source = source;
	}
}
