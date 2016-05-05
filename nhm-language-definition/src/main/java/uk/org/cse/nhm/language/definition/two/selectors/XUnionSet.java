package uk.org.cse.nhm.language.definition.two.selectors;

import java.util.ArrayList;
import java.util.List;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindRemainingArguments;

@Bind("union")
@Doc("A set of houses which is computed by taking the union of some other sets.")
public class XUnionSet extends XSetOfHouses {
	private List<XSetOfHouses> contents = new ArrayList<>();

	public static class P {
		public static final String contents = "contents";
	}
	
	@Prop(P.contents)
	@Doc("The sets to take the union of; when this set is used, each of these sets will be sampled in order and combined")
	@BindRemainingArguments
	public List<XSetOfHouses> getContents() {
		return contents;
	}

	public void setContents(final List<XSetOfHouses> contents) {
		this.contents = contents;
	}
}
