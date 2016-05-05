package uk.org.cse.nhm.simulator.util;

import java.util.Arrays;
import java.util.TreeSet;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

public class TimeSeriesTest {
	@Test
	public void testValueBeforeAndAfterBound() {
		final TimeSeries<Object> ts = new TimeSeries<Object>();
		
		Object a = new Object();
		ts.addPoint(new DateTime(100), a);
		
		Assert.assertSame(a, ts.getValue(new DateTime(0)));
		Assert.assertSame(a, ts.getValue(new DateTime(100)));
		Assert.assertSame(a, ts.getValue(new DateTime(1000)));
	}
	
	@Test
	public void testValueBeforeAndAfterBoundWithDefault() {
		final Object d = new Object();
		final TimeSeries<Object> ts = new TimeSeries<Object>(d);
		
		Object a = new Object();
		ts.addPoint(new DateTime(100), a);
		
		Assert.assertSame(d, ts.getValue(new DateTime(0)));
		Assert.assertSame(a, ts.getValue(new DateTime(100)));
		Assert.assertSame(a, ts.getValue(new DateTime(1000)));
	}
	
	@Test
	public void testEmptyWithDefault() {
		final Object d = new Object();
		final TimeSeries<Object> ts = new TimeSeries<Object>(d);
		Assert.assertSame(d, ts.getValue(new DateTime(23345)));
	}
	
	@Test
	public void testValueStepsAndPoints() {
		final TimeSeries<Object> ts = new TimeSeries<Object>();
		
		Object a = new Object();
		Object b = new Object();
		ts.addPoint(new DateTime(100), a);
		ts.addPoint(new DateTime(200), b);
		
		Assert.assertSame(a, ts.getValue(new DateTime(0)));
		Assert.assertSame(a, ts.getValue(new DateTime(100)));
		Assert.assertSame(b, ts.getValue(new DateTime(200)));
		Assert.assertSame(b, ts.getValue(new DateTime(1000)));
		Assert.assertEquals(new TreeSet<DateTime>(Arrays.asList(new DateTime[] {new DateTime(100), new DateTime(200)})), ts.getDateTimes());
	}
}

