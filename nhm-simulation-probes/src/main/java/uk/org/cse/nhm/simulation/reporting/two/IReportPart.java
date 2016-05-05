package uk.org.cse.nhm.simulation.reporting.two;

import java.util.Set;

public interface IReportPart {
	public Set<Column> columns();
	public Set<Cut> cuts();
}
