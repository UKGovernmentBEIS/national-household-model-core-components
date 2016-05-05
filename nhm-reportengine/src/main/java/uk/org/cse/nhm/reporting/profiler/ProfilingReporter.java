package uk.org.cse.nhm.reporting.profiler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;
import uk.org.cse.nhm.logging.logentry.ProfiledItemLogEntry;
import uk.org.cse.nhm.logging.logentry.ProfilerLogEntry;
import uk.org.cse.nhm.reporting.report.IReportOutput;
import uk.org.cse.nhm.reporting.standard.GenericDescriptor;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReportDescriptor;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReportDescriptor.Type;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReporter;
import uk.org.cse.nhm.reporting.standard.resources.ResourceOutput;

public class ProfilingReporter implements IReporter {
    private OutputStream itemsOut;
    private OutputStream entriesOut;
    private final IOutputStreamFactory streamFactory;
    private final ObjectMapper mapper;
    private boolean active = false;
    
    @SuppressWarnings("serial")
	@Inject
    public ProfilingReporter(final IOutputStreamFactory streamFactory) {
        this.streamFactory = streamFactory;
        this.mapper = new ObjectMapper().setAnnotationIntrospector(new NopAnnotationIntrospector() {});
    }
    
    @Override
    public Set<Class<? extends ISimulationLogEntry>> getEntryClasses() {
        return ImmutableSet.<Class<? extends ISimulationLogEntry>>
            of(ProfiledItemLogEntry.class,
               ProfilerLogEntry.class);
    }
    	
    @Override
    public void handle(final ISimulationLogEntry entry) {
        if (entry instanceof ProfiledItemLogEntry) {
            writeItem((ProfiledItemLogEntry) entry);
        } else if (entry instanceof ProfilerLogEntry) {
            writeEntry((ProfilerLogEntry) entry);
        }
    }

    private void writeItem(final ProfiledItemLogEntry e) {
        if (itemsOut == null) {
            itemsOut = streamFactory.createReportFile(IReportOutput.PROFILE + "profiled-items.jsonp",
                                                      Optional.<IReportDescriptor>absent());
            write(itemsOut, "profiler.items([");
        }
        writeObject(itemsOut, e);
    }

    private void writeEntry(final ProfilerLogEntry e) {
        if (entriesOut == null) {
            entriesOut = streamFactory.createReportFile(IReportOutput.PROFILE + "profile.jsonp",
                                                        Optional.<IReportDescriptor>absent());
            write(entriesOut, "profiler.log([");
        }

        writeObject(entriesOut, e);
    }

    private void write(final OutputStream out, final String string) {
        try {
            out.write(string.getBytes(StandardCharsets.UTF_8));            
        } catch (final IOException e) {e.printStackTrace();}
    }

    private void writeObject(final OutputStream out, final Object e) {
        try {
            write(out, mapper.writeValueAsString(e));
        } catch (final IOException ex) {ex.printStackTrace();}

        write(out, ", \n");
    }

    @Override
    public void close() throws IOException {
        if (entriesOut != null) {
            entriesOut.flush();
            write(entriesOut, "]);");
            entriesOut.close();
            active = true;
        }
        if (itemsOut != null) {
            itemsOut.flush();
            write(itemsOut, "]);");
            itemsOut.close();
            active = true;
        }

        if (active) {
            try (final OutputStream pageOutput = streamFactory.createReportFile(IReportOutput.PROFILE + "index.html",
                                                                          Optional.<IReportDescriptor>of(GenericDescriptor.of(Type.Profiling)))) {
                final ResourceOutput res = new ResourceOutput(IReportOutput.PROFILE + "index.html");
                res.doWriteContent(pageOutput);
            }
            try (final OutputStream pageOutput = streamFactory.createReportFile(IReportOutput.PROFILE + "profiler.js",
                                                                          Optional.<IReportDescriptor>absent())) {
                final ResourceOutput res = new ResourceOutput(IReportOutput.PROFILE + "profiler.js");
                res.doWriteContent(pageOutput);
            }
        }
    }
}


