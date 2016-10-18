package uk.org.cse.nhm.language.definition.sequence;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindRemainingArguments;

@Doc({
	"The do function is analogous to the do action, except it cannot change the state of a house.",
	"Consequently the ForSimulation scope is not legal in any of the (set), (increase) or (decrease) statements within it",
	"Instead, it simply performs a sequence of calculations and snapshots before computing a final value which",
	"is taken as the overall value of the sequence."
})
@SeeAlso(XSequenceAction.class)
@Bind("do")
@Category(CategoryType.ARITHMETIC)
public class XSequenceFunction extends XNumber implements IScopingElement {
	public static final class P {
		public static final String delegate = "delegate";
		public static final String sequence = "sequence";
	}
	private XNumber delegate;
	private List<XBindingAction> sequence = new ArrayList<>();
	
	@BindRemainingArguments
	@Doc("A sequence of variable setting or snapshotting operations")
	public List<XBindingAction> getSequence() {
		return sequence;
	}

	public void setSequence(final List<XBindingAction> sequence) {
		this.sequence = sequence;
	}

	@BindNamedArgument("return")
	@NotNull(message="sequence function must have a value to return specified with the return: keyword")
	public XNumber getDelegate() {
		return delegate;
	}

	public void setDelegate(final XNumber delegate) {
		this.delegate = delegate;
	}
}
