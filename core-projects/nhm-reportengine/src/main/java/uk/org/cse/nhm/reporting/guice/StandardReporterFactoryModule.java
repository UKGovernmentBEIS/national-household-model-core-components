package uk.org.cse.nhm.reporting.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;

import uk.org.cse.nhm.reporting.errors.ErrorReporter;
import uk.org.cse.nhm.reporting.errors.WarningReporter;
import uk.org.cse.nhm.reporting.profiler.ProfilingReporter;
import uk.org.cse.nhm.reporting.standard.IReporterFactory;
import uk.org.cse.nhm.reporting.standard.LookupTablesReporter;
import uk.org.cse.nhm.reporting.standard.ScenarioInputReporter;
import uk.org.cse.nhm.reporting.standard.StockInputReporter;
import uk.org.cse.nhm.reporting.standard.TSVSequenceReporter;
import uk.org.cse.nhm.reporting.standard.dwellings.TSVDwellingsReporter;
import uk.org.cse.nhm.reporting.standard.explain.ExplainReporterFactory;
import uk.org.cse.nhm.reporting.standard.flat.*;
import uk.org.cse.nhm.reporting.standard.flat.accounts.TSVGlobalAccountsReport;
import uk.org.cse.nhm.reporting.standard.probes.ProbeReporter;
import uk.org.cse.nhm.reporting.standard.timeseries.TSVTimeSeriesReporter;
import uk.org.cse.nhm.reporting.standard.timeseries.TimeSeriesReporterFactory;
import uk.org.cse.nhm.reporting.standard.transactions.TSVDwellingTransactionReporter;

public class StandardReporterFactoryModule extends AbstractModule {

    @Override
    protected void configure() {
        final Multibinder<IReporterFactory> reporterFactories = Multibinder.newSetBinder(binder(), IReporterFactory.class);

        // these guys need to be in the DI context as they use velocity etc.
        reporterFactories.addBinding().to(ExplainReporterFactory.class).in(Scopes.SINGLETON);
        reporterFactories.addBinding().to(TimeSeriesReporterFactory.class).in(Scopes.SINGLETON);
        reporterFactories.addBinding().to(StockInputReporter.Factory.class).in(Scopes.SINGLETON);
        reporterFactories.addBinding().to(LookupTablesReporter.Factory.class).in(Scopes.SINGLETON);
        reporterFactories.addBinding().to(WarningReporter.Factory.class).in(Scopes.SINGLETON);
        reporterFactories.addBinding().toInstance(new SimpleReporterFactory(ProfilingReporter.class));

        // these guys can be actual singletons I new up now; they have no special stuff in them
        reporterFactories.addBinding().toInstance(new SimpleReporterFactory(TSVTimeSeriesReporter.class));

        reporterFactories.addBinding().toInstance(new SimpleReporterFactory(ScenarioInputReporter.class));

        reporterFactories.addBinding().toInstance(new SimpleReporterFactory(TSVAttributesReporter.class));
        reporterFactories.addBinding().toInstance(new SimpleReporterFactory(TSVStructureReporter.class));
        reporterFactories.addBinding().toInstance(new SimpleReporterFactory(TSVEmissionsReporter.class));
        reporterFactories.addBinding().toInstance(new SimpleReporterFactory(TSVEnergyReporter.class));

        reporterFactories.addBinding().toInstance(new SimpleReporterFactory(TSVDwellingTransactionReporter.class));
        reporterFactories.addBinding().toInstance(new SimpleReporterFactory(TSVFuelCostReporter.class));

        reporterFactories.addBinding().toInstance(new SimpleReporterFactory(TSVDemolitionReporter.class));
        reporterFactories.addBinding().toInstance(new SimpleReporterFactory(TSVGlobalAccountsReport.class));
        reporterFactories.addBinding().toInstance(new SimpleReporterFactory(TSVMeasureCostReporter.class));
        reporterFactories.addBinding().toInstance(new SimpleReporterFactory(TSVNationalPowerReporter.class));
        reporterFactories.addBinding().toInstance(new SimpleReporterFactory(TSVTechnologyCountReporter.class));
        reporterFactories.addBinding().toInstance(new SimpleReporterFactory(TSVTechnologyInstallationReporter.class));

        reporterFactories.addBinding().toInstance(new SimpleReporterFactory(TSVDwellingsReporter.class));
        reporterFactories.addBinding().toInstance(new SimpleReporterFactory(ProbeReporter.class));

        reporterFactories.addBinding().toInstance(new SimpleReporterFactory(ErrorReporter.class));

        reporterFactories.addBinding().toInstance(new SimpleReporterFactory(TSVSequenceReporter.class));
    }
}
