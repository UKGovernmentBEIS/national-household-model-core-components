package uk.org.cse.nhm.language.definition.reporting;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.Identity;

import uk.org.cse.nhm.language.definition.Doc;

/**
 * A reporting element which has some user-defined parts.
 */
public abstract class XCustomReportingElement extends XReportingElement {
	@Override
	@Doc("A human readable name for this report.")
	
	@BindNamedArgument
	@NotNull(message = "report must always have a unique name.")
	
	@Identity
	public final String getName() {
		return super.getName();
	}

	@Override
	public final void setName(final String name) {
		super.setName(name);
	}
}
