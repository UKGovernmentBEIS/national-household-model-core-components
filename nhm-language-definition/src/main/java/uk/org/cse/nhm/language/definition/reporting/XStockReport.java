package uk.org.cse.nhm.language.definition.reporting;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.validate.BatchForbidden;

import com.larkery.jasb.bind.Bind;

@Bind("report.housing-stock")
@BatchForbidden(element = "report.housing-stock")
@Doc("Generates an output in json file format of the housing stock which was loaded by the scenario.")
public class XStockReport extends XReportingElement {
}
