package uk.org.cse.nhm.language.definition.sequence;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.Obsolete;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.action.XDwellingAction;
import uk.org.cse.nhm.language.definition.function.num.IHypotheticalContext;

@Doc("Takes a snapshot of the current house, which will be available for the rest of the parent (sequence).")
@Bind("take-snapshot")
@SeeAlso(XSetAction.class)
@Obsolete(
	inFavourOf=XSetAction.class, 
	version="5.1.4", 
	reason="As of 5.1.3, the (set) action can modify several variables in sequence using a hypothetical state. This makes most uses of snapshots unnecessary.")
public class XSnapshotAction extends XBindingAction implements IHypotheticalContext {
	public static final class P {
		public static final String name="snapshotName";
		public static final String modifications = "modifications";
	}
	private XSnapshotDeclaration name;

	private List<XDwellingAction> modifications = new ArrayList<>();
	
	@BindRemainingArguments
	@Doc("Actions to take on the snapshot, which change its state without having any side-effects")
	public List<XDwellingAction> getModifications() {
		return modifications;
	}

	public void setModifications(final List<XDwellingAction> modifications) {
		this.modifications = modifications;
	}

	@Doc("The name of the snapshot - use #name to refer to a declaration elsewhere")
	@BindPositionalArgument(0)
	@NotNull(message="take-snapshot requires a snapshot variable declaration")
	public XSnapshotDeclaration getSnapshotName() {
		return name;
	}

	public void setSnapshotName(final XSnapshotDeclaration name) {
		this.name = name;
	}
}
