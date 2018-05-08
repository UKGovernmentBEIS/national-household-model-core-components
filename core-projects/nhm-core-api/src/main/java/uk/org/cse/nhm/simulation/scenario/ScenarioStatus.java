package uk.org.cse.nhm.simulation.scenario;

/**
 * ScenarioStatus.
 *
 * @author richardt
 * @version $Id: cse-eclipse-codetemplates.xml 94 2010-09-30 15:39:21Z richardt
 * $
 * @since 1.1.0-DUNDRY
 */
public enum ScenarioStatus {
    ERROR,
    PROCESSING,
    INQUEUE,
    RECEIVED,
    BUILDING_SCENARIO,
    SIMULATION_RUNNING,
    REPORT_REQUESTED,
    REPORT_REQUEST_RECEIVED,
    REPORTING,
    SIM_COMPLETE,
    REPORT_COMPLETE;
}
