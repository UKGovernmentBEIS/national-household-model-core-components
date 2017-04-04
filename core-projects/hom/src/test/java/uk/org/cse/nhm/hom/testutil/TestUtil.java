package uk.org.cse.nhm.hom.testutil;

import static org.mockito.Matchers.doubleThat;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class TestUtil {
	/**
	 * Matches a double which is in the range value +/- margin.
	 * @param value
	 * @param margin
	 * @return
	 */
	public static double around(final double value, final double margin) {
		return doubleThat(new BaseMatcher<Double>() {
			@Override
			public boolean matches(final Object arg0) {
				if (arg0 instanceof Double) {
					return Math.abs(((Double) arg0).doubleValue() - value) < margin;
				}
				return false;
			}

			@Override
			public void describeTo(Description arg0) {
				arg0.appendText(String.format("%f +/- %f", value, margin));
			}
		});
	}
}
