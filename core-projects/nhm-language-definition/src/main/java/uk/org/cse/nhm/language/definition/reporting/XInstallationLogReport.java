package uk.org.cse.nhm.language.definition.reporting;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.validate.BatchForbidden;

@Bind("report.measure-installations")
@BatchForbidden(element = "report.measure-installations")
@Doc("Enables the dwelling/installationLog report, which records the installation of measures in dwellings")
public class XInstallationLogReport extends XReportingElement {

}
