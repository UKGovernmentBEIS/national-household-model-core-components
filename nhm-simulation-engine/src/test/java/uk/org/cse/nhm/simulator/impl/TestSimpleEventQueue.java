package uk.org.cse.nhm.simulator.impl;

import java.util.Arrays;
import java.util.NoSuchElementException;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.main.IDateRunnable;
import uk.org.cse.nhm.simulator.main.Priority;

public class TestSimpleEventQueue {
	@Test(expected=NoSuchElementException.class)
	public void testEmptyQueue() throws NHMException {
		final SimpleEventQueue queue = new SimpleEventQueue();
		Assert.assertTrue("Newly constructed queue is empty", queue.isEmpty());
		queue.despatch(); 
	}
	
	@Test
	public void testNonEmptyQueue() throws NHMException {
		final SimpleEventQueue queue = new SimpleEventQueue();
		
		queue.schedule(new DateTime(), Priority.ofStockCreator(), null);
		
		Assert.assertTrue("Queue with elements is not empty", queue.isEmpty() == false);
		
		queue.despatch();
		
		Assert.assertTrue("Queue is empty after element removed", queue.isEmpty());
	}
	
	@Test
	public void testQueueSequencing() throws NHMException {
		final DateTime d1 = new DateTime(0);
		final DateTime d2 = new DateTime(1);
		final DateTime d3 = new DateTime(2);
		
		final int[] values = new int[4];
		final int[] valueIndex = new int[1];
		
		final IDateRunnable e1 = new IDateRunnable() {
			@Override
			public void run(DateTime date) {
				Assert.assertEquals(d1, date);
				values[valueIndex[0]++] = 0;
			}
		};
		
		final IDateRunnable e2 = new IDateRunnable() {
			@Override
			public void run(DateTime date) {
				Assert.assertEquals(d2, date);
				values[valueIndex[0]++] = 1;
			}
		};
		
		final IDateRunnable e3 = new IDateRunnable() {
			@Override
			public void run(DateTime date) {
				Assert.assertEquals(d3, date);
				values[valueIndex[0]++] = 2;
			}
		};
		
		final IDateRunnable e4 = new IDateRunnable() {
			@Override
			public void run(DateTime date) {
				Assert.assertEquals(d3, date);
				values[valueIndex[0]++] = 3;
			}
		};
		
		final SimpleEventQueue queue = new SimpleEventQueue();
		
		queue.schedule(d3, Priority.ofObligation(0), e3);
		queue.schedule(d3, Priority.ofCheckpoints(), e4);// should get run before the previous
		queue.schedule(d1, Priority.ofStockCreator(), e1);
		queue.schedule(d2, Priority.ofStockCreator(), e2);
		
		queue.drain();
		Assert.assertTrue("Array in right order (" + Arrays.toString(values) + ")", Arrays.equals(new int[] {0,1,3,2}, values));
	}
}
