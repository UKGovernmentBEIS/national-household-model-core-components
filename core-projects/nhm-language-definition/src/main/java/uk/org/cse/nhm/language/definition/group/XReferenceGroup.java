package uk.org.cse.nhm.language.definition.group;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.Obsolete;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;


@Bind("group.reference")
@Doc("A group which just refers to another group, by using its ID. A reference to a group always has the same members as the referent.")
@Obsolete(reason = "This language elment is unnecessary since the introduction of cross-references and templates to the language.")
public class XReferenceGroup extends XGroup {
	public static final class P {
		public static final String REFERENCE = "reference";
	}
	private XGroup reference;

	@Prop(P.REFERENCE)
	
	@NotNull
	@Doc("The ID of the group being referred to.")
	
@BindNamedArgument("to")
	public XGroup getReference() {
		return reference;
	}

	public void setReference(final XGroup reference) {
		this.reference = reference;
	}
}
