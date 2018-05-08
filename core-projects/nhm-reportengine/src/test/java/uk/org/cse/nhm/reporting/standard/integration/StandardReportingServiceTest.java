package uk.org.cse.nhm.reporting.standard.integration;

import java.io.IOException;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

import uk.org.cse.nhm.logging.logentry.AggregateLogEntry;
import uk.org.cse.nhm.reporting.IReportEngine;
import uk.org.cse.nhm.reporting.guice.StandardReportingModule;
import uk.org.cse.nhm.reporting.standard.IReportingSession;

public class StandardReportingServiceTest {

    private IReportEngine service;

    @Before
    public void setup() {
        final Injector i = Guice.createInjector(new StandardReportingModule(true));
        this.service = i.getInstance(Key.get(IReportEngine.class, Names.named(IReportEngine.STANDARD_ENGINE)));
    }

    @Test
    public void createsZipFile() throws IOException {
        final IReportingSession session = service.startReportingSession();

        session.close();

        Assert.assertTrue("Resulting zip file exists", Files.exists(session.getResultPath()));
    }

    @Test
    public void createsCustomReport() throws IOException {
        final IReportingSession session = service.startReportingSession();

        session.acceptLogEntry(new AggregateLogEntry("my-lovely-report",
                ImmutableSet.of("Things"),
                ImmutableMap.of("group", "A group"), new DateTime(0), ImmutableMap.of("Hello", 99d)));

        session.close();

        Assert.assertTrue("Resulting zip file exists", Files.exists(session.getResultPath()));

        final ZipInputStream zis = new ZipInputStream(Files.newInputStream(session.getResultPath()));

        ZipEntry ze;

        boolean hasReport = false;
        while ((ze = zis.getNextEntry()) != null) {
            if (ze.getName().equals("aggregate/my-lovely-report.tab")) {
                hasReport = true;
            }
        }

        Assert.assertTrue("Resulting zip file contains my lovely report", hasReport);
    }
}
