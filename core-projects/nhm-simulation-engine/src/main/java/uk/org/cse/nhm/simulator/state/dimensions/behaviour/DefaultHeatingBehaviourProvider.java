package uk.org.cse.nhm.simulator.state.dimensions.behaviour;

import javax.inject.Inject;
import javax.inject.Named;

import com.google.inject.Provider;

import uk.org.cse.nhm.simulator.SimulatorConfigurationConstants;

public class DefaultHeatingBehaviourProvider implements Provider<IHeatingBehaviour> {
	private final IHeatingBehaviour value;

	@Inject
	public DefaultHeatingBehaviourProvider(
			@Named(SimulatorConfigurationConstants.DEMAND_TEMPERATURE) final double demandTemperature) {
		this.value = HeatingBehaviour.DEFAULT_BEHAVIOUR.withLivingAreaDemandTemperature(demandTemperature);
	}
	
	@Override
	public IHeatingBehaviour get() {
		return this.value;
	}

}
