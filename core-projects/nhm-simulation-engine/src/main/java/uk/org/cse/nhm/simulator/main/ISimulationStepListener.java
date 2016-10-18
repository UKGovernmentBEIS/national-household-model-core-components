package uk.org.cse.nhm.simulator.main;

import org.joda.time.DateTime;

import uk.org.cse.nhm.NHMException;

/**
 * An event listener interface for simulation steps;
 * 
 *  See {@link ISimulator#addSimulationStepListener(SimulationStepListener)}
 *  
 * @author hinton
 *
 */
public interface ISimulationStepListener {
	public void simulationStepped(final DateTime dateOfStep, final DateTime nextDate, boolean isFinalStep) throws NHMException;
}
