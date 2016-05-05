package uk.org.cse.nhm.reporting.standard.timeseries.model;

import java.math.BigDecimal;

import org.joda.time.DateTime;

class SecondsUtil {
	private static final BigDecimal MILLI = new BigDecimal(1000);
	
	public static BigDecimal secondsSinceUnixEra(DateTime date) {
		return new BigDecimal(date.getMillis()).divide(MILLI);
	}
}
