package uk.org.cse.nhm.language.definition.reporting;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.Obsolete;
import uk.org.cse.nhm.language.validate.BatchForbidden;


@Bind("report.houses-by-region")
@BatchForbidden(element="report.houses-by-region")
@Doc("Enables the aggregates/demolition report, which displays the number of houses in each region over time")
@Obsolete(reason="This report is rarely used, and can be recreated using the aggregate command", version="6.4.0")
public class XHouseCountReport extends XReportingElement {

}
