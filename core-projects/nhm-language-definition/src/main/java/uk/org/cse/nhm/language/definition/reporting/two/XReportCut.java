package uk.org.cse.nhm.language.definition.reporting.two;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.validate.ISelfValidating;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindRemainingArguments;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;

@Bind("cut")
@Doc("Each cut adds an extra summary output to a report, in which each columns summaries' are computed for subsets of houses broken down by the values of the functions in the cut.")
public class XReportCut extends XReportPart implements ISelfValidating {
	private List<String> values = new ArrayList<>();

	@Doc("This is used to name the summary table that is produced; in a report called MY-REPORT, a cut named MY-CUT will produce a summary table called MY-CUT-OF-MY-REPORT.")
	@Override
	@BindNamedArgument
	public String getName() {
		return super.getName();
	}

	@Doc({"These should be the name:s of columns in the report; the summary outputs of the report will be broken down by their distinct combinations.",
		"You can also cut by the special columns 'selected' and 'sent-from', which determine whether the state of the house was chosen to become the truth, where in the scenario the house has been sent from",
		"and whether the house was sent to the report by an action before it succeeded, after it succeeded, or before it failed."
	})
	@BindRemainingArguments
	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}
	
	@Override
	public List<IError> validate(final Deque<XElement> context) {
		final Iterator<XElement> it = context.descendingIterator();
		while (it.hasNext()) {
			final XElement e = it.next();
			if (e instanceof XReportDefinition) {
				final XReportDefinition xrd = (XReportDefinition) e;
				
				final Set<String> knownColumns = new HashSet<>();
				knownColumns.add("selected");
				knownColumns.add("sent-from");
				knownColumns.add("suitable");
				
				for (final XReportPart xrp : xrd.getContents()) {
					if (xrp instanceof XReportColumn) {
						knownColumns.add(xrp.getIdentifier().getName());
					}
				}
				
				final SetView<String> difference = Sets.difference(ImmutableSet.copyOf(values), knownColumns);
				if (difference.isEmpty()) {
					break;
				} else {
					return Collections.singletonList(
							BasicError.at(getSourceNode(), 
									"This report does not contain the column(s) " + Joiner.on(", ").join(difference) +
									", so they cannot be used in a cut. The cut must use the name: values of columns in the same report."
									)
							);
				}
			}
		}
		return Collections.emptyList();
	}
}
