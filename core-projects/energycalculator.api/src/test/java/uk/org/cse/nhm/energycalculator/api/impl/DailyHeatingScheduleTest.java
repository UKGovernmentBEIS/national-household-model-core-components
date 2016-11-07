package uk.org.cse.nhm.energycalculator.api.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.impl.DailyHeatingSchedule;

/**
 * Tests the behaviour of the {@link DailyHeatingSchedule}, using a heating profile that looks like this:
 *
 * <pre>
 *      		       +---------\
 *      		       |	     |\
 *      		       |	     | \
 *      		       |	     |  \
 *      		       |	     |   \
 *      		       |         |    \
 *      		       |         |     \
 *      		       |         |      \
 *    -----------------+---------+-------\-----------------------------------
 *    00		      05       	10	12				   24
 * </pre>
 * @author hinton
 *
 */
public class DailyHeatingScheduleTest {
	private DailyHeatingSchedule on5to10;
	private DailyHeatingSchedule off;

	@Before
	public void setup() {
		this.on5to10 = DailyHeatingSchedule.fromHours(5, 10);
		this.off = new DailyHeatingSchedule();
	}

	@Test
	public void testGetMeanTemperature() {
		final double meanTemperature = on5to10.getMeanTemperature(10, 5, 2*60);

		// average is 5h at 10 degrees, remainder at 5 degrees, with 2 hours of triangle in between.

		final double auc =
				5*5 +
				5*10 +
				(2*5 + 2*5/2d) +
				12*5;

		final double mean = auc / 24d;

		Assert.assertEquals(mean, meanTemperature, 0);

		Assert.assertEquals(13, off.getMeanTemperature(100, 13, 343), 0);
	}

	@Test
	public void testIsHeatingOn() {
		Assert.assertTrue(on5to10.isHeatingOn());
		Assert.assertFalse(off.isHeatingOn());
	}
}
