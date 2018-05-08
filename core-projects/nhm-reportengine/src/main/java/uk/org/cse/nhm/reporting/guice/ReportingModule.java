package uk.org.cse.nhm.reporting.guice;

import com.google.inject.PrivateModule;
import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;

import uk.org.cse.nhm.reporting.IReportEngine;
import uk.org.cse.nhm.reporting.standard.IReporterFactory;
import uk.org.cse.nhm.reporting.standard.IZippingFileStreamFactory;
import uk.org.cse.nhm.reporting.standard.StandardReportEngine;
import uk.org.cse.nhm.reporting.standard.ZippingIndexingFileStreamFactory;
import uk.org.cse.nhm.reporting.standard.ZippingStandaloneFileStreamFactory;

class ReportingModule extends PrivateModule {

    private final String engineName;
    private boolean webbish;

    ReportingModule(final String engineName, final boolean webbish) {
        super();
        this.engineName = engineName;
        this.webbish = webbish;
    }

    @Override
    protected void configure() {
        Multibinder.newSetBinder(binder(), IReporterFactory.class);

        install(new VelocityModule());

        if (webbish) {
            bind(IZippingFileStreamFactory.class).to(ZippingIndexingFileStreamFactory.class);
        } else {
            bind(IZippingFileStreamFactory.class).to(ZippingStandaloneFileStreamFactory.class);
        }

        bind(StandardReportEngine.class).in(Scopes.SINGLETON);
        bind(IReportEngine.class).annotatedWith(Names.named(engineName)).to(StandardReportEngine.class);

        expose(IReportEngine.class).annotatedWith(Names.named(engineName));
    }
}
