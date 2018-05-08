package uk.org.cse.nhm.simulation.reporting.probes;

import java.util.Map;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * Marker interface for probing functions.
 *
 * @author hinton
 *
 */
public interface IProbingFunction extends IComponentsFunction<Map<String, Object>> {

    ImmutableSet<String> getHeaders();
}
