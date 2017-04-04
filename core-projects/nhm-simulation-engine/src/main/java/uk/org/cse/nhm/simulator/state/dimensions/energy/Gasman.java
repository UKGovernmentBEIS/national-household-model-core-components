package uk.org.cse.nhm.simulator.state.dimensions.energy;

import java.util.LinkedHashSet;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.main.ISimulationStepListener;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.IStateListener;

/**
 * The gasman cometh to read your meter whenever your energy usage changes.
 * 
 * Because of the nature of the energy meter dimension, this is required to ensure that it
 * has the right value in it when time has passed
 * 
 * @author hinton
 *
 */
public class Gasman implements ISimulationStepListener, IStateListener {
	private static final Logger log = LoggerFactory.getLogger(Gasman.class);
	private final LinkedHashSet<IDwelling> needMeterReadings = new LinkedHashSet<>();
	private final IDimension<IEnergyMeter> meters;
	private final ICanonicalState state;

	@Inject
	public Gasman(IDimension<IEnergyMeter> meters, final ICanonicalState state, final ISimulator simulator) {
		this.meters = meters;
		this.state = state;
		state.addStateListener(this);
		simulator.addSimulationStepListener(this);
	}
	
	@Override
	public void simulationStepped(DateTime dateOfStep, DateTime nextDate, boolean isFinalStep) throws NHMException {
		log.debug("reading meters for {} dwellings", needMeterReadings.size());
		for (final IDwelling d : needMeterReadings) {
			state.get(meters, d);
		}
		needMeterReadings.clear();
	}

	@Override
	public void stateChanged(ICanonicalState state, IStateChangeNotification notification) {
		needMeterReadings.addAll(notification.getCreatedDwellings());
		needMeterReadings.addAll(notification.getChangedDwellings(meters));
		needMeterReadings.removeAll(notification.getDestroyedDwellings());
	}
}
