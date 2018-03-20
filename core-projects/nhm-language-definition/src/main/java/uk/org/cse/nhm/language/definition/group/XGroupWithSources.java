package uk.org.cse.nhm.language.definition.group;

import java.util.ArrayList;
import java.util.List;

import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;

public abstract class XGroupWithSources extends XGroup {
	public static final class P {
		public static final String SOURCES = "sources";
	}
	
	private List<XGroup> sources = new ArrayList<XGroup>();
	
	
	@BindRemainingArguments
	@Prop(P.SOURCES)
	@Doc("The groups which will be used to determine the contents of this group.")
	public List<XGroup> getSources() {
		return sources;
	}

	public void setSources(final List<XGroup> sources) {
		this.sources = sources;
	}
}
