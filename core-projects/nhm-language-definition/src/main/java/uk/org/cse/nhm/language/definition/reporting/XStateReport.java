package uk.org.cse.nhm.language.definition.reporting;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.Obsolete;
import uk.org.cse.nhm.language.validate.BatchForbidden;


@Bind("report.state")
@BatchForbidden(element="report.state")
@Doc("Enables the dwelling state reports, which give energy, carbon emissions, and basic attributes for the housing stock over time")
@Obsolete(reason="This report is rarely used, and can be recreated using the probe command", version="6.4.0")
public class XStateReport extends XReportingElement {

}
