package uk.org.cse.nhm.language.definition.reporting;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.validate.BatchForbidden;

import com.larkery.jasb.bind.Bind;


@Bind("report.measure-installations")
@BatchForbidden(element="report.measure-installations")
@Doc("Enables the dwelling/installationLog report, which records the installation of measures in dwellings")
public class XInstallationLogReport extends XReportingElement {

}
