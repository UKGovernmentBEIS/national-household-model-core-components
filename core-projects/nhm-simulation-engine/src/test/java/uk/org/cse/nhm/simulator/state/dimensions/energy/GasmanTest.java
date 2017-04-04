package uk.org.cse.nhm.simulator.state.dimensions.energy;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;

public class GasmanTest {
	@SuppressWarnings("unchecked")
	@Test
	public void gasmanComethAndReadsMeters() {
		IDimension<IEnergyMeter> dimension = mock(IDimension.class);
		ICanonicalState state = mock(ICanonicalState.class);
		ISimulator sim = mock(ISimulator.class);
		
		final Gasman g = new Gasman(dimension, state, sim);
		
		verify(state).addStateListener(g);
		verify(sim).addSimulationStepListener(g);
		
		IStateChangeNotification notification = mock(IStateChangeNotification.class);

		final IDwelling d1 = mock(IDwelling.class), d2 = mock(IDwelling.class), d3 = mock(IDwelling.class);
		
		when(notification.getChangedDwellings(dimension)).thenReturn(ImmutableSet.of(d1));
		
		g.stateChanged(null, notification);
		
		when(notification.getCreatedDwellings()).thenReturn(ImmutableSet.of(d2, d3));
		when(notification.getDestroyedDwellings()).thenReturn(ImmutableSet.of(d3));
		
		g.stateChanged(null, notification);
		
		g.simulationStepped(null, null, false);
		
		verify(state).get(dimension, d1);

		verify(state).get(dimension, d2);
	}
}
