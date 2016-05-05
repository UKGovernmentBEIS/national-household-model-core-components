package uk.org.cse.nhm.energycalculator.api.impl;

import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;

/**
 * A base class for {@link IEnergyTransducer} implementations which just provides the simple getters and setters.
 * @author hinton
 * @since 1.0.0
 */
public abstract class EnergyTransducer implements IEnergyTransducer {
	private ServiceType serviceType;
	private int priority;
	

	public EnergyTransducer(ServiceType serviceType, int priority) {
		super();
		this.serviceType = serviceType;
		this.priority = priority;
	}

	@Override
	public ServiceType getServiceType() {
		return serviceType;
	}

	@Override
	public int getPriority() {
		return priority;
	}
}
