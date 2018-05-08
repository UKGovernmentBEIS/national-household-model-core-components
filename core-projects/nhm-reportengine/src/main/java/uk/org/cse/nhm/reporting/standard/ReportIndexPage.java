package uk.org.cse.nhm.reporting.standard;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormat;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;

import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReportDescriptor;
import uk.org.cse.nhm.reporting.standard.TemporaryFileStreamFactory.CompletedOutput;
import uk.org.cse.nhm.reporting.standard.resources.TemplatedResourceOutput;

public class ReportIndexPage extends TemplatedResourceOutput {

    public ReportIndexPage(final VelocityEngine velocityEngine,
            final List<CompletedOutput> outputs,
            final DateTime startDate,
            final DateTime endDate) {
        super("index.vm", "index.html", velocityEngine, buildParameters(outputs, startDate, endDate));
    }

    @Override
    public String getPath() {
        return "index.html";
    }

    private static Map<String, Object> buildParameters(final List<CompletedOutput> outputs, final DateTime startDate, final DateTime endDate) {
        final Multimap<IReportDescriptor.Type, CompletedOutput> grouped = TreeMultimap.create(
                new Comparator<IReportDescriptor.Type>() {
            @Override
            public int compare(final IReportDescriptor.Type o1, final IReportDescriptor.Type o2) {
                return o1.compareTo(o2);
            }
        },
                new Comparator<CompletedOutput>() {

            @Override
            public int compare(final CompletedOutput o1, final CompletedOutput o2) {
                return o1.name.compareTo(o2.name);
            }
        }
        );

        for (final CompletedOutput output : outputs) {
            if (output.descriptor != null) {
                grouped.put(output.descriptor.getType(), output);
            }
        }

        final Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("entries", grouped);

        parameters.put("error", grouped.keySet().contains(IReportDescriptor.Type.Problems));

        final DateTimeFormatter formatter = DateTimeFormat.mediumDateTime();

        parameters.put("started", formatter.print(startDate));
        parameters.put("finished", formatter.print(endDate));

        parameters.put("runtime",
                PeriodFormat.getDefault().print(new Period(startDate, endDate)));

        return parameters;
    }
}
