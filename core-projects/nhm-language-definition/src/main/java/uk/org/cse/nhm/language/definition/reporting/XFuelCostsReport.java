package uk.org.cse.nhm.language.definition.reporting;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.validate.BatchForbidden;

@Bind("report.fuel-costs")
@BatchForbidden(element = "report.fuel-costs")
@Doc({"Generates a report on changes in fuel costs for each dwelling."})
public class XFuelCostsReport extends XReportingElement {
}
