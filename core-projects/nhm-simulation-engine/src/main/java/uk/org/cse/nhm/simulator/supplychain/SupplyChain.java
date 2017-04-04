package uk.org.cse.nhm.simulator.supplychain;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;
import org.joda.time.Period;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.SimulatorConfigurationConstants;
import uk.org.cse.nhm.simulator.main.IDateRunnable;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.main.Initializable;
import uk.org.cse.nhm.simulator.main.Priority;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IGlobals;

public class SupplyChain extends AbstractNamed implements Initializable {
	@Inject
	public SupplyChain(
			final ISimulator simulator,
			final ICanonicalState state,
			final @Named(SimulatorConfigurationConstants.GRANULARITY) int quantum,
			final @Named(SimulatorConfigurationConstants.END_DATE) DateTime endDate,
			@Assisted final Name identifier,
			@Assisted final String chainName, 
			@Assisted List<Supply> supplies) {
		
		final Priority priority = Priority.ofIdentifier(identifier);
		for (final Supply s : supplies) {
			final IDateRunnable operation = new IDateRunnable() {
				@Override
				public void run(DateTime date) {
					final IGlobals globals = state.getGlobals();
					
					final double setValue;
					
					if (s.getSet().isPresent()) {
						setValue = s.getSet().get();
					} else {
						setValue = globals.getVariable(chainName, Double.class).or(0d) * quantum;
					}
					
					final double incrementedValue;
					
					if (s.getIncrement().isPresent()) {
						incrementedValue = setValue + s.getIncrement().get();
					} else {
						incrementedValue = setValue;
					}
					
					globals.setVariable(chainName, incrementedValue / quantum);
				}
				
				@Override
				public String toString() {
					return String.format("%s %s", chainName, s.description());
				}
			};
			
			simulator.schedule(s.getOn(), priority, operation);
			
			if (s.getInterval().isPresent()) {
				final Period p = s.getInterval().get();
				DateTime now = s.getOn().plus(p);
				while (!now.isAfter(endDate)) {
					simulator.schedule(now, priority, operation);
					now = now.plus(p);
				}
			}
		}
	}
	
	
	@Override
	public void initialize() throws NHMException {
		
	}
}
