package uk.org.cse.nhm.language.definition.reporting;

import java.util.ArrayList;
import java.util.List;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.Obsolete;
import uk.org.cse.nhm.language.definition.group.XAllHousesGroup;
import uk.org.cse.nhm.language.definition.group.XGroup;
import uk.org.cse.nhm.language.definition.two.actions.XTransitionHookAction;
import uk.org.cse.nhm.language.validate.BatchForbidden;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindRemainingArguments;


@Bind("report.group-transitions")
@BatchForbidden(element = "report.group-transitions")
@Doc(
		value = {
				"Defines a report which tracks the transition of houses between a set of mutually exclusive conditions.",
				"The resulting report is a Sankey diagram, in which the passage of houses from one of the <sgmltag>when</sgmltag> clauses below to another",
				"is illustrated by an arrow, with which the time of the change and the target causing the change are associated.",
				"The intention of this is to allow you to easily see, for example, how policies are changing the technologies present in a house",
				"over the course of a simulation."
		}
	)
@Obsolete(reason = "Since we are moving away from groups in favour of housing sets, we have rewritten the group transitions report as a new element which uses this.", 
	version = "5.0.0", 
	inFavourOf = {XTransitionHookAction.class})
public class XGroupTransitionReport extends XCustomReportingElement {
	public static final class P {
		public static final String cases = "cases";
		public static final String group = "group";
	}
	
	private List<XGroupTransitionCase> cases = new ArrayList<XGroupTransitionCase>();

	private XGroup group = new XAllHousesGroup();

	@Doc("The group definition, which specifies the houses which this group transition report will see.")
	
	@Prop(P.group)
	@BindNamedArgument
	public XGroup getGroup() {
		return group;
	}

	public void setGroup(final XGroup group) {
		this.group = group;
	}

	@Prop(P.cases)
	@BindRemainingArguments
	
	@Doc("Each when case defines a subdivision of the source group - transitions between these subdivisions will be reported on.")
	public List<XGroupTransitionCase> getCases() {
		return cases;
	}

	public void setCases(final List<XGroupTransitionCase> cases) {
		this.cases = cases;
	}
}
