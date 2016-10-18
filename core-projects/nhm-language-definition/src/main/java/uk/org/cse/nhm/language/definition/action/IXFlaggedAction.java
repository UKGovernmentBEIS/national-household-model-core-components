package uk.org.cse.nhm.language.definition.action;

import java.util.List;

import uk.org.cse.commons.Glob;

import uk.org.cse.nhm.language.definition.reporting.two.XReportDefinition;

public interface IXFlaggedAction {

	List<Glob> getTestFlags();

	List<Glob> getUpdateFlags();

	List<XReportDefinition> getReport();

}
