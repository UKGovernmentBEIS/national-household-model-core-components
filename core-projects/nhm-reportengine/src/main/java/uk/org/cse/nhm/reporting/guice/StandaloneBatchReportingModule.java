package uk.org.cse.nhm.reporting.guice;

import com.google.inject.PrivateModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;

import uk.org.cse.nhm.reporting.IReportEngine;
import uk.org.cse.nhm.reporting.batch.StandaloneBatchReportEngine;

public class StandaloneBatchReportingModule extends PrivateModule {

    @Override
    protected void configure() {
        bind(StandaloneBatchReportEngine.class).in(Scopes.SINGLETON);
        bind(IReportEngine.class).annotatedWith(Names.named(IReportEngine.STANDALONE_BATCH_ENGINE))
                .to(StandaloneBatchReportEngine.class);
        expose(IReportEngine.class).annotatedWith(Names.named(IReportEngine.STANDALONE_BATCH_ENGINE));
    }
}
