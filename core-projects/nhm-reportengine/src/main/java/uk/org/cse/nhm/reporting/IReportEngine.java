package uk.org.cse.nhm.reporting;

import java.io.IOException;

import uk.org.cse.nhm.reporting.standard.IReportingSession;

public interface IReportEngine {
    public static final String BATCH_ENGINE = "uk.org.cse.nhm.reporting.IReportEngine.BATCH_ENGINE";
    public static final String STANDALONE_BATCH_ENGINE = "uk.org.cse.nhm.reporting.IReportEngine.STANDALONE_BATCH_ENGINE";
	public static final String STANDARD_ENGINE = "uk.org.cse.nhm.reporting.IReportEngine.STANDARD_ENGINE";

	public IReportingSession startReportingSession() throws IOException;
}
