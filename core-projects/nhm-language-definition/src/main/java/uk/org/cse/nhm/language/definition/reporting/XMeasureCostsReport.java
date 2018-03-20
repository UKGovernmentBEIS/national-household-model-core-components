package uk.org.cse.nhm.language.definition.reporting;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.validate.BatchForbidden;


@Bind("report.aggregate-measure-costs")
@BatchForbidden(element="report.aggregate-measure-costs")
@Doc("Enables the aggregates/measureCosts report, which displays aggregate information about measures installed")
public class XMeasureCostsReport extends XReportingElement {

}
