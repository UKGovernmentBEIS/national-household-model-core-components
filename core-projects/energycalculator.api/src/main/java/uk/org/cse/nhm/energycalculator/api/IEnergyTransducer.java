package uk.org.cse.nhm.energycalculator.api;

import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.TransducerPhaseType;

/**
 * Represents something which can take energy of one sort and turn it into
 * energy of another sort.
 * 
 * A single device might have more than one transducer in it
 * (for example, a heating system which is also a hot water system would have a
 * heat transducer and a hot water transducer). 
 * 
 * @author hinton
 *
 */
public interface IEnergyTransducer {
	/**
	 * @return The {@link ServiceType} this transducer is designed to satisfy.
	 */
	public ServiceType getServiceType();
	
	/**
	 * Modify the state to increase / satisfy some energy demands
	 * 
	 * @param house  the house which is being evaluated (this is passed in so that house components needn't know what house they are in, allowing for easy mongo serialization)
	 * @param parameters the parameters (temp etc.) in use (heat pumps will need these)
	 * @param losses the computed specific heat losses for the house
	 * @param state the current energy demand / satisfaction matrix
	 */
	public void generate(final IEnergyCalculatorHouseCase house, 
			final IInternalParameters parameters, 
			final ISpecificHeatLosses losses, 
			final IEnergyState state);
	
	/**
	 * @return the priority of this system, amongst other systems which don't interact.
	 */
	public int getPriority();
	
	public TransducerPhaseType getPhase();
}
