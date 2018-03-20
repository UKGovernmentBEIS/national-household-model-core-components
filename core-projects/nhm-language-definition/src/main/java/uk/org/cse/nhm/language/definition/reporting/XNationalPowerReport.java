package uk.org.cse.nhm.language.definition.reporting;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.Obsolete;
import uk.org.cse.nhm.language.validate.BatchForbidden;


@Bind("report.national-power")
@BatchForbidden(element="report.national-power")
@Doc("Enables the aggregates/national-power report, which displays the national energy consumption breakdown")
@Obsolete(reason="This report is rarely used, and can be recreated using the aggregate command", version="6.4.0")
public class XNationalPowerReport extends XReportingElement {

}
