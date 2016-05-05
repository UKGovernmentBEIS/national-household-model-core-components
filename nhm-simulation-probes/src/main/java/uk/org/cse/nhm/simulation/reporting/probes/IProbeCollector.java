package uk.org.cse.nhm.simulation.reporting.probes;

import java.util.Map;

import org.joda.time.DateTime;

/**
 * The definition of a thing which collects values from probes.
 * 
 * This is what generates the sequence number for probe log entries,
 * so users of this should really call {@link #collectProbe(String, DateTime, int, Map)}
 * as soon as they have interacted with the thing they are probing;
 * that is to say, if you were to hold onto a probe value for a while,
 * you might end up with a confusing sequence number
 * 
 * @author hinton
 *
 */
public interface IProbeCollector {
	/**
	 * Send the given probe away into this simulation's probe log.
	 * @param probeName
	 * @param simulationTime
	 * @param houseID
	 * @param probedValues
	 */
	public void collectProbe(
			final String probeName, 
			final DateTime simulationTime,
			final int houseID,
			final float weight,
			final Map<String, Object> probedValues);
}
