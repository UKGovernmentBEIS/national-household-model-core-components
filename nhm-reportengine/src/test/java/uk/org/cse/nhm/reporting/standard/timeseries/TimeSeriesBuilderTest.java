package uk.org.cse.nhm.reporting.standard.timeseries;

import static java.math.BigDecimal.ONE;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterables;

import uk.org.cse.nhm.logging.logentry.AggregateLogEntry;
import uk.org.cse.nhm.reporting.standard.timeseries.model.Annotation;
import uk.org.cse.nhm.reporting.standard.timeseries.model.Point;
import uk.org.cse.nhm.reporting.standard.timeseries.model.Series;

public class TimeSeriesBuilderTest {
	private static final ImmutableMap<String, String> SERIES = ImmutableMap.of("series", "a group");
	private static final ImmutableMap<String, String> SERIES_2 = ImmutableMap.of("series", "a different group");
	private static final DateTime NOW = new DateTime(0);
	private Set<String> empty = Collections.emptySet();
	private ImmutableMap<String, Double> emptyMap = ImmutableMap.of();
	private List<AggregateLogEntry> entries;

	@Before
	public void setup() {
		entries = new ArrayList<AggregateLogEntry>();
	}
	
	@Test
	public void testNoEntriesBuildsNothing() { 
		final TimeSeriesBuilder builder = new TimeSeriesBuilder("name", entries);
		Assert.assertEquals(Collections.emptyList(), builder.buildSeries(entries));
	}
	
	@Test
	public void testBuildsCorrectNumberOfSeries() {
		entries.add(new AggregateLogEntry("report", empty, SERIES, NOW, ImmutableMap.of("value", 0d)));
		entries.add(new AggregateLogEntry("report", empty, SERIES, NOW, ImmutableMap.of("value", 0d)));
		entries.add(new AggregateLogEntry("report", empty, SERIES_2, NOW, ImmutableMap.of("value", 0d)));
		final TimeSeriesBuilder builder = new TimeSeriesBuilder("name", entries);
		List<Series> result = builder.buildSeries(entries);
		Assert.assertEquals("Should make 2 series (one for each different series name in the data).", 2, result.size());
	}
	
	@Test
	public void testSeriesIncludesPointsInDateOrder() {
		DateTime first = new DateTime(0);
		entries.add(new AggregateLogEntry("report", empty, SERIES, first, ImmutableMap.of("value", 0d)));
		DateTime second = new DateTime(1);
		entries.add(new AggregateLogEntry("report", empty, SERIES, second, ImmutableMap.of("value", 1d)));
		final TimeSeriesBuilder builder = new TimeSeriesBuilder("name", entries);
		List<Series> result = builder.buildSeries(entries);
		Assert.assertEquals("Should make 1 series (one for each different series name in the data).", 1, result.size());
		Series series = result.get(0);
		
		Assert.assertEquals("Series should take name from group and variable name.", "value for a group", series.getName());
		Assert.assertEquals("Data should be translated into Points in order.", ImmutableSortedSet.of(new Point(first, 0d), new Point(second, 1d)), series.getData());
	}
	
	@Test
	public void testNoAnnotationIfNoCauses() {
		entries.add(new AggregateLogEntry("report", empty, SERIES, null, emptyMap));
		final TimeSeriesBuilder builder = new TimeSeriesBuilder("name", entries);
		Assert.assertTrue("No annotations should be added if there are no causes of change.", builder.buildAnnotations(entries).isEmpty());
	}
	
	@Test
	public void testCreatesAnnotations() {
		entries.add(new AggregateLogEntry("report", ImmutableSet.of("cause"), SERIES, NOW, emptyMap));
		final TimeSeriesBuilder builder = new TimeSeriesBuilder("name", entries);
		SortedSet<Annotation> annotations = builder.buildAnnotations(entries);
		Assert.assertEquals("Single entry should create one annotation.", 1, annotations.size());
		Annotation a = Iterables.get(annotations, 0);
		Assert.assertEquals("Annotation date should come from entry date.", BigDecimal.ZERO, a.getDate());
		Assert.assertEquals("Annotation text should come from entry causes and dates", "1970 Jan 01<br>cause", a.getText());
	}
	
	@Test
	public void testCreatesOneAnnotationPerTimeStepIncludingAllText() {
		entries.add(new AggregateLogEntry("report", ImmutableSet.of("cause"), SERIES, NOW, emptyMap));
		entries.add(new AggregateLogEntry("report", ImmutableSet.of("cause 2"), SERIES_2, NOW, emptyMap));
		final TimeSeriesBuilder builder = new TimeSeriesBuilder("name", entries);
		SortedSet<Annotation> annotations = builder.buildAnnotations(entries);
		Assert.assertEquals("Two entries on the same date should create one annotation.", 1, annotations.size());
		Annotation a = Iterables.get(annotations, 0);
		Assert.assertEquals("Annotation date should come from entry date.", BigDecimal.ZERO, a.getDate());
		Assert.assertEquals("Annotation text should come from causes merged across all entries.", "1970 Jan 01<br>cause<br>cause 2", a.getText());
	}
	
	@Test
	public void testJSONOutput() throws IOException {
		entries.add(new AggregateLogEntry("report", empty, SERIES, NOW, ImmutableMap.of("value", 0d)));
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final TimeSeriesBuilder builder = new TimeSeriesBuilder("name", entries);
		builder.doWriteContent(outputStream);
		Assert.assertEquals("JSON should have been written by the report builder.", 
				"nhmChartDataLoaded(\"aggregate/name.jsonp\", {\"series\":[{\"data\":[{\"y\":0.0,\"x\":0}],\"name\":\"value for a group\"}],\"annotations\":[]});", 
				new String(outputStream.toByteArray()));
	}
	
	@Test
	public void testCreatesNoExtraPointsIfAllDatesArePresent() {
		final SortedSet<Point> points = ImmutableSortedSet.<Point>copyOf(
				ImmutableSet.<Point>builder()
				.add(new Point(NOW, 1d))
				.add(new Point(new DateTime(1000), 2d))
				.build()
				);
		
		final SortedSet<DateTime> dates = ImmutableSortedSet.<DateTime>copyOf(
				ImmutableSet.<DateTime>builder()
				.add(NOW)
				.add(new DateTime(1000))
				.build()
				);
		
		Assert.assertEquals("" + points, 2, points.size());
		Assert.assertEquals(2, dates.size());
		
		final Set<Point> extraPoints = TimeSeriesBuilder.createMissingPointsForAllDates(dates, points);
		
		Assert.assertTrue("No extras (points are comprehensive) " + extraPoints, extraPoints.isEmpty());
	}
	
	@Test
	public void testCreatesPointsAtEnd() {
		final SortedSet<Point> points = ImmutableSortedSet.<Point>naturalOrder()
				.add(new Point(NOW, 1d))
				.add(new Point(new DateTime(1000), 2d))
				.build();
		
		final SortedSet<DateTime> dates = ImmutableSortedSet.<DateTime>naturalOrder()
				.add(NOW)
				.add(new DateTime(1000))
				.add(new DateTime(2000))
				.build();
		
		Assert.assertEquals(2, points.size());
		Assert.assertEquals(3, dates.size());
		
		final Set<Point> extraPoints = TimeSeriesBuilder.createMissingPointsForAllDates(dates, points);
		
		Assert.assertEquals("One extra : " + extraPoints, extraPoints.size(), 1);
		
		Assert.assertEquals("Date is correct", extraPoints.iterator().next().getX(), BigDecimal.valueOf(2));

		Assert.assertEquals("Value is correct", extraPoints.iterator().next().getY(), 2d, 0.1);
	}
	
	@Test
	public void testCreatesPointsInMiddle() {
		final SortedSet<Point> points = ImmutableSortedSet.<Point>naturalOrder()
				.add(new Point(NOW, 1d))
				.add(new Point(new DateTime(2000), 2d))
				.build();
		
		
		SortedSet<DateTime> dates = ImmutableSortedSet.<DateTime>naturalOrder()
			.add(NOW)
			.add(new DateTime(1000))
			.add(new DateTime(2000))
			.build();
		
		Assert.assertEquals(2, points.size());
		Assert.assertEquals(3, dates.size());
		
		final Set<Point> extraPoints = TimeSeriesBuilder.createMissingPointsForAllDates(dates, points);
		
		Assert.assertEquals("One extra : " + extraPoints, extraPoints.size(), 1);
		             
		Assert.assertEquals("Date is correct", extraPoints.iterator().next().getX(), ONE);
		             
		Assert.assertEquals("Value is correct", extraPoints.iterator().next().getY(), 1d, 0.1);
	}
}
