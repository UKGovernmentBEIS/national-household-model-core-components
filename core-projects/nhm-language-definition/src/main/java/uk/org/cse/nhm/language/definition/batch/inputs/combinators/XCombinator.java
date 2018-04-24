package uk.org.cse.nhm.language.definition.batch.inputs.combinators;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.batch.inputs.XInputs;

public abstract class XCombinator extends XInputs {
	public static final class P {
		public static final String DELEGATES = "delegates";
	}
	
	private List<XInputs> delegates = new ArrayList<>();

	
	@BindRemainingArguments
	@Prop(P.DELEGATES)
	@Size(min = 1, message = "combinator elements must always contain at least one delegate.")
	@Doc("The delegates which this combinator element will combine.")
	public List<XInputs> getDelegates() {
		return delegates;
	}

	public void setDelegates(final List<XInputs> delegates) {
		this.delegates = delegates;
	}
}
