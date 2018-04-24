package uk.org.cse.nhm.language.definition.reporting;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.validate.BatchForbidden;


@Bind("report.global-accounts")
@BatchForbidden(element = "report.global-accounts")
@Doc({
	"Produces a report on global account balances over time.",
	"Global accounts are any accounts not attached to a dwelling."
})
public class XGlobalAccountReport extends XReportingElement {
}
