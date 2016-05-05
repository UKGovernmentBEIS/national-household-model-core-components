package uk.org.cse.nhm.language.definition.function.num;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.action.XDwellingAction;
import uk.org.cse.nhm.language.definition.sequence.XSequenceAction;
import uk.org.cse.nhm.language.definition.sequence.XSetAction;
import uk.org.cse.nhm.language.definition.sequence.XSnapshotAction;
import uk.org.cse.nhm.language.definition.sequence.XSnapshotDeclaration;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindRemainingArguments;


@Bind("under")
@Doc({ "The under function evaluates some other function using a temporary hypothetical state of a house.",
		"While it does not actually make any changes to a house, it appears to have done so from the point of view of a function inside it.",
        "Note that the under function does not efficiently avoid recomputing values from one use of the function to another,",
        "so if you want to evaluate multiple values in a counterfactual state it is better to use a snapshot or the equivalent functionality",
        "which is available in the set action (which can calculate multiple values at once)."
        })
@SeeAlso({ XSetAction.class, XSequenceAction.class, XSnapshotAction.class })
@Category(CategoryType.COUNTERFACTUALS)
public class XUnderFunction extends XHouseNumber implements IHypotheticalContext {
	public static class P {
		public static final String DELEGATE = "delegate";
		public static final String SNAPSHOT = "snapshot";
		public static final String HYPOTHESES = "hypotheses";
	}

	private XNumber delegate;
	private XSnapshotDeclaration snapshot;
	private List<XDwellingAction> hypotheses = new ArrayList<>();

	@Prop(P.DELEGATE)
	@BindNamedArgument("evaluate")
	@NotNull(message = "under element must always contain another function which it will evaluate.")
	@Doc("The function to be evaluated under the specified conditions. From the point of view of this function, the house will be in a modified state, even though the changes are actually temporary.")
	public XNumber getDelegate() {
		return delegate;
	}

	public void setDelegate(final XNumber delegate) {
		this.delegate = delegate;
	}

	@Prop(P.SNAPSHOT)
    @BindNamedArgument
	@Doc({ 
		"The name of the snapshot to use for the purposes of computing the evaluate: argument (optional).", 
		"If this is specified, the house snapshot of the given name will be loaded before applying any hypotheses or computing the child function.",
		"Otherwise, the current state of the house will be used." })
	public XSnapshotDeclaration getSnapshot() {
		return snapshot;
	}

	public void setSnapshot(final XSnapshotDeclaration snapshot) {
		this.snapshot = snapshot;
	}

	@Prop(P.HYPOTHESES)
	
	@Doc({
		"The actions to apply to the house to create our temporary hypothetical state.",
		"Once these have been applied, the evaluate: argument will be computed. Finally, these changes will be discarded."
	})
	@BindRemainingArguments
	public List<XDwellingAction> getHypotheses() {
		return hypotheses;
	}

	public void setHypotheses(final List<XDwellingAction> hypotheses) {
		this.hypotheses = hypotheses;
	}
}
