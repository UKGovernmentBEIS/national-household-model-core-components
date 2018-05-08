package uk.org.cse.nhm.simulator;

import uk.org.cse.commons.names.IIdentified;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;

public interface IProfilingStack {

    public void push(final IIdentified thing);

    public void pop(final IIdentified thing);

    public NHMException die(final String message, final IIdentified thing, final IComponentsScope scope);
}
