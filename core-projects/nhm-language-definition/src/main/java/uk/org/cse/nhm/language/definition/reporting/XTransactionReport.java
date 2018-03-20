package uk.org.cse.nhm.language.definition.reporting;

import java.util.ArrayList;
import java.util.List;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.tags.Tag;
import uk.org.cse.nhm.language.definition.two.actions.XPayHookAction;
import uk.org.cse.nhm.language.definition.two.selectors.ISetOfHouses;
import uk.org.cse.nhm.language.definition.two.selectors.XAllTheHouses;
import uk.org.cse.nhm.language.validate.BatchForbidden;

@Doc({
		"A report which logs transactions that houses engage in.",
		"When used in a scenario, this will cause the generation of a report with the given name",
		"which records all of the transactions that happen to houses in its associated group.",
		"If a set of tags are specified, only transactions which have all the given tags will be logged.",
		"It is worth noting that the transaction log is often very large, so (for example) logging all of the transactions",
		"produced for all houses will slow the simulation somewhat and also produce a very large report package.",
		"The amounts written out are multiplied through by the weight.",
		"The transactions report will additionally display all of the transactions which occurred between global accounts."
})

@Bind("report.transactions")
@BatchForbidden(element = "report.transactions")
@SeeAlso(XPayHookAction.class)
public class XTransactionReport extends XCustomReportingElement {
	public static final class P {
		public static final String group = "group";
		public static final String tags = "tags";
	}
	
    private ISetOfHouses group = new XAllTheHouses();
	private List<Tag> tags = new ArrayList<>();
	
	public XTransactionReport() {
		setName("transactions");
	}
	
	@BindNamedArgument("group")
	@Prop(P.group)
	
	@Doc("A group of houses to restrict this report to - defaults to all houses.")
    public ISetOfHouses getGroup() {
		return group;
	}
    public void setGroup(final ISetOfHouses group) {
		this.group = group;
	}
	
	
@BindNamedArgument
	@Prop(P.tags)
	@Doc("A set of tags, all of which must be present on a transaction for it to be logged - defaults to empty.")
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(final List<Tag> tags) {
		this.tags = tags;
	}
}
