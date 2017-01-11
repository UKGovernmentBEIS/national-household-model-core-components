package uk.org.cse.nhm.logging.logentry;

import org.apache.commons.math3.util.FastMath;
import org.joda.time.DateTime;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Measure cost report log
 *
 * @author tomw
 */
@AutoProperty
public class MeasureCostLogEntry extends AbstractDatedLogEntry {
	private final String technology;
	// Source (policy name)
	private final String source;
	private final Stats opex;
	private final Stats capex;
	private final Stats sizeInstalled;
	private final double count;
	private final String units;

	@AutoProperty
	public static class Stats {
		/** The sample mean */
		private final double mean;

		/** The sample variance */
		private final double variance;

		/** The maximum value */
		private final double max;

		/** The minimum value */
		private final double min;

		/** The sum of the sample values */
		private final double sum;

		/**
		 * Constructor
		 *
		 * @param mean
		 *            the sample mean
		 * @param variance
		 *            the sample variance
		 * @param n
		 *            the number of observations in the sample
		 * @param max
		 *            the maximum value
		 * @param min
		 *            the minimum value
		 * @param sum
		 *            the sum of the values
		 */
		@JsonCreator
		public Stats(@JsonProperty("mean") final double mean,
				@JsonProperty("variance") final double variance,
				@JsonProperty("max") final double max,
				@JsonProperty("min") final double min, @JsonProperty("sum") final double sum) {
			super();
			this.mean = mean;
			this.variance = variance;
			this.max = max;
			this.min = min;
			this.sum = sum;
		}

		/**
		 * @return Returns the max.
		 */
		public double getMax() {
			return max;
		}

		/**
		 * @return Returns the mean.
		 */
		public double getMean() {
			return mean;
		}

		/**
		 * @return Returns the min.
		 */
		public double getMin() {
			return min;
		}

		/**
		 * @return Returns the sum.
		 */
		public double getSum() {
			return sum;
		}

		/**
		 * @return Returns the standard deviation
		 */
		@JsonIgnore
		public double getStandardDeviation() {
			return FastMath.sqrt(variance);
		}

		/**
		 * @return Returns the variance.
		 */
		public double getVariance() {
			return variance;
		}

		@Override
		public String toString() {
			return Pojomatic.toString(this);
		}

		@Override
		public boolean equals(final Object obj) {
			return Pojomatic.equals(this, obj);
		}

		@Override
		public int hashCode() {
			return Pojomatic.hashCode(this);
		}
	}

	@JsonCreator
	public MeasureCostLogEntry(
			@JsonProperty("technology") final String technology,
			@JsonProperty("source") final String source,
			@JsonProperty("opex") final Stats opex,
			@JsonProperty("capex") final Stats capex,
			@JsonProperty("sizeInstalled") final Stats sizeInstalled,
			@JsonProperty("count") final double count,
			@JsonProperty("date") final DateTime date,
			@JsonProperty("units") final String units) {
		super(date);
		this.technology = technology;
		this.source = source;
		this.opex = opex;
		this.capex = capex;
		this.sizeInstalled = sizeInstalled;
		this.count = count;
		this.units = units;
	}

	public String getTechnology() {
		return technology;
	}

	public String getSource() {
		return source;
	}

	public String getUnits() {
		return units;
	}

	public double getCount() {
		return count;
	}

	public Stats getOpex() {
		return opex;
	}

	public Stats getCapex() {
		return capex;
	}

	public Stats getSizeInstalled() {
		return sizeInstalled;
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

	@Override
	public boolean equals(final Object obj) {
		return Pojomatic.equals(this, obj);
	}

	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}
}
