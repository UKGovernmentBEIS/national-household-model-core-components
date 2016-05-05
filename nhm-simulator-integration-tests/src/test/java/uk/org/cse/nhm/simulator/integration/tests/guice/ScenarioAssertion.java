package uk.org.cse.nhm.simulator.integration.tests.guice;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.ISimulationStepListener;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.main.Initializable;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.functions.IAggregationFunction;

/**
 * A device which tests an assertion about the direction and rate of change of a particular quantity within a simulation.
 * 
 * The test is evaluated at the end of each step, so changes that happen within the first step will not be tested.
 * 
 * @author hinton
 *
 */
public class ScenarioAssertion implements ISimulationStepListener, Initializable {
	private static final Logger log = LoggerFactory.getLogger(ScenarioAssertion.class);
	private final int direction;
	private final double bound;
	private final IDwellingGroup group;
	private final IAggregationFunction function;
	private final boolean continuous;
	private final IState state;
	private final String name;

	private Double lastResult;
	
	@Inject
	public ScenarioAssertion(
			final IState state,
			final ISimulator simulator,
			@Assisted final String name,
			@Assisted final int direction,
			@Assisted final double bound, 
			@Assisted final boolean continuous,
			@Assisted final IDwellingGroup group,
			@Assisted final IAggregationFunction function) {
		this.state = state;
		this.name = name;
		this.direction = direction;
		this.bound = bound;
		this.continuous = continuous;
		this.group = group;
		this.function = function;
		simulator.addSimulationStepListener(this);
	}

	@Override
	public void simulationStepped(final DateTime dateOfStep, final DateTime nextDate, final boolean isFinalStep) throws NHMException {
		final double value = evaluateAggregation();
		log.debug("Checking assertion {} at {}, val = {}", new Object[] {name, dateOfStep, value});
		if (lastResult != null && (continuous || isFinalStep)) {
			// compute the difference as a proportion of the previous value
			final double delta = lastResult == value ? 0 : ((value - lastResult) / Math.abs(lastResult));
			
			final int sign;
			if (Math.abs(delta) < bound) {
				sign = 0;
			} else if (delta < 0) {
				sign = -1; // value < lastResult
			} else {
				sign = 1;
			}
			
			if (sign != direction) {
				throw new AssertionError(
						String.format("Assertion %s failed: %s is false (at %s)", name, 
								(direction == 0) ? String.format("| %.2f | > %.2f", delta, bound) :
									(direction > 0) ? String.format("%.2f < %.2f", delta, bound) :
										String.format("%.2f < %.2f", delta, bound), dateOfStep));
			}
		}
		
		if (continuous || lastResult == null) {
			lastResult = value;
		}
	}

	private double evaluateAggregation() {
		return function.evaluate(state, ILets.EMPTY, group.getContents());
	}

	@Override
	public void initialize() throws NHMException {
		// TODO Auto-generated method stub
		
	}
}
