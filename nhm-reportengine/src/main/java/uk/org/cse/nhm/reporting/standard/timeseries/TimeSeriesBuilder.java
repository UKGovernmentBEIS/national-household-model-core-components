package uk.org.cse.nhm.reporting.standard.timeseries;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.base.Joiner;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;

import uk.org.cse.nhm.logging.logentry.AggregateLogEntry;
import uk.org.cse.nhm.reporting.report.IReportOutput;
import uk.org.cse.nhm.reporting.report.SizeRecordingReportOutput;
import uk.org.cse.nhm.reporting.standard.jsonp.JSONPOutputUtility;
import uk.org.cse.nhm.reporting.standard.timeseries.model.Annotation;
import uk.org.cse.nhm.reporting.standard.timeseries.model.GroupAndVariable;
import uk.org.cse.nhm.reporting.standard.timeseries.model.Point;
import uk.org.cse.nhm.reporting.standard.timeseries.model.Series;
import uk.org.cse.nhm.reporting.standard.timeseries.model.SeriesWithAnnotations;

public class TimeSeriesBuilder extends SizeRecordingReportOutput {
	private static DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy MMM dd");
	private SeriesWithAnnotations series;
	private String path;

	public TimeSeriesBuilder(String reportName, Collection<AggregateLogEntry> entries) {
		this.path = String.format("%s.jsonp", reportName);

		this.series = new SeriesWithAnnotations(buildSeries(entries), buildAnnotations(entries));
	}

	@Override
	public String getPath() {
		return IReportOutput.DATA + path;
	}

	@Override
	public String getTemplate() {
		return IReportOutput.GENERIC_TEMPLATE;
	}

	@Override
	public Type getType() {
		return Type.Data;
	}

	@Override
	public void doWriteContent(OutputStream outputStream) throws IOException {
		JSONPOutputUtility.writeAsJSONP(this, series, outputStream);
	}

	public SeriesWithAnnotations buildSeriesWithAnnotations() {
		return series;
	}

	public List<Series> buildSeries(Collection<AggregateLogEntry> entries) {
		final Multimap<GroupAndVariable, AggregateLogEntry> entriesBySeries = HashMultimap.create();
		final Builder<Series> builder = ImmutableList.<Series> builder();
		final SortedSet<DateTime> allDates = new TreeSet<DateTime>();

		for (AggregateLogEntry entry : entries) {
			for (String variableName : entry.getColumns().keySet()) {
				entriesBySeries.put(new GroupAndVariable(entry.getReducedRowKey(), variableName), entry);
				allDates.add(entry.getDate());
			}
		}

		for (GroupAndVariable series : entriesBySeries.keySet()) {
			builder.add(new Series(series, dataForSeries(entriesBySeries.get(series), series.getVariable(), allDates)));
		}
		return builder.build();
	}

	private SortedSet<Point> dataForSeries(Collection<AggregateLogEntry> collection, String variableName, SortedSet<DateTime> allDates) {
		SortedSet<Point> result = new TreeSet<Point>();
		for (AggregateLogEntry entry : collection) {
			result.add(new Point(entry.getDate(), entry.getColumns().get(variableName)));
		}

		if (result.isEmpty() || result.size() == allDates.size()) {
			return result;
		}

		// now we are going to make sure that the result contains a point for
		// every date,
		// even if the input collection does not have entries for dates where
		// the value has not changed.
		// this is to help rickshaw out.

		result.addAll(createMissingPointsForAllDates(allDates, result));

		return result;
	}

	protected static Set<Point> createMissingPointsForAllDates(SortedSet<DateTime> allDates, SortedSet<Point> allPoints) {
		final Iterator<Point> points = allPoints.iterator();
		final Iterator<DateTime> dates = allDates.iterator();

		Point point = points.next();

		double lastY = 0;

		final SortedSet<Point> extraPoints = new TreeSet<Point>();

		while (dates.hasNext()) {
			final DateTime date = dates.next();
			// advance both iterators if they are in the same place
			if (point.isAtDate(date)) {
				lastY = point.getY();
				if (points.hasNext()) {
					point = points.next();
				} else {
					break;
				}
			} else if (point.isAfterDate(date)) {
				// insert a new point at date
				extraPoints.add(new Point(date, lastY));
			} else { // if point is before date - this is a case which should
						// never occur.
				throw new RuntimeException("A point exists for a date which is not in the set of all dates, which should never happen.");
			}
		}

		// if we have not exhausted all the dates, we need to pop them on the
		// end
		while (dates.hasNext()) {
			extraPoints.add(new Point(dates.next(), lastY));
		}

		return extraPoints;
	}

	public SortedSet<Annotation> buildAnnotations(Collection<AggregateLogEntry> entries) {
		TreeMultimap<DateTime, String> causesByDate = TreeMultimap.create();
		for (AggregateLogEntry entry : entries) {
			if (!entry.getCauses().isEmpty()) {
				causesByDate.putAll(entry.getDate(), entry.getCauses());
			}
		}

		SortedSet<Annotation> result = new TreeSet<Annotation>();
		for (DateTime date : causesByDate.keySet()) {
			result.add(new Annotation(date, String.format("%s<br>%s", dateFormat.print(date), Joiner.on("<br>").join(causesByDate.get(date)))));
		}

		return result;
	}


}
