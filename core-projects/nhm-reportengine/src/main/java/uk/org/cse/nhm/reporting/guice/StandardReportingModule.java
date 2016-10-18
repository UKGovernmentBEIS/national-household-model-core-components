package uk.org.cse.nhm.reporting.guice;

import uk.org.cse.nhm.reporting.IReportEngine;

public class StandardReportingModule extends ReportingModule {
	public StandardReportingModule(boolean webbish) {
		super(IReportEngine.STANDARD_ENGINE, webbish);
	}

	@Override
	protected void configure() {
		super.configure();
		// install all the reporter factories
		// they are bound into a set in this other module
		install(new StandardReporterFactoryModule());
	}
}
