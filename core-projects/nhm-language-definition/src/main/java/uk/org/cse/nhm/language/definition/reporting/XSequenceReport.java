package uk.org.cse.nhm.language.definition.reporting;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.validate.BatchForbidden;

@Bind("report.sequence")
@Doc({
	"A report on the events which occurred during the scenario and how many dwellings they affected.",
	"The numbers of dwellings created, destroyed and modified are multiplied through by the scenario's quantum."
})
@BatchForbidden(element="report.sequence")
public class XSequenceReport extends XReportingElement {
}
