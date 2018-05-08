package uk.org.cse.nhm.simulation.reporting.state;

import java.util.Set;

import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;

/**
 * Interface for devices which turn a dwelling component into something loggable
 * (a POJO).
 *
 * @author hinton
 *
 */
public interface IComponentFlattener {

    public Object flatten(IState state, final IDwelling dwelling);

    public Set<IDimension<?>> getComponents();
}
