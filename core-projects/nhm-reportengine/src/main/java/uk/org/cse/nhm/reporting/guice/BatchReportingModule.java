package uk.org.cse.nhm.reporting.guice;

import com.google.inject.multibindings.Multibinder;

import uk.org.cse.nhm.reporting.IReportEngine;
import uk.org.cse.nhm.reporting.batch.BatchOutputReporter;
import uk.org.cse.nhm.reporting.errors.ErrorReporter;
import uk.org.cse.nhm.reporting.standard.IReporterFactory;
import uk.org.cse.nhm.reporting.standard.ScenarioInputReporter;
import uk.org.cse.nhm.reporting.standard.flat.SimpleReporterFactory;

public class BatchReportingModule extends ReportingModule {

    public BatchReportingModule(boolean webbish) {
        super(IReportEngine.BATCH_ENGINE, webbish);
    }

    @Override
    protected void configure() {
        super.configure();

        // these are the outputs enabled in batch mode;
        final Multibinder<IReporterFactory> reporterFactories = Multibinder.newSetBinder(binder(), IReporterFactory.class);
        reporterFactories.addBinding().toInstance(new SimpleReporterFactory(BatchOutputReporter.class));
        reporterFactories.addBinding().toInstance(new SimpleReporterFactory(ScenarioInputReporter.class));
        reporterFactories.addBinding().toInstance(new SimpleReporterFactory(ErrorReporter.class));
    }
}
