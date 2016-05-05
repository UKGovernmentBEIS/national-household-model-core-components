package uk.org.cse.nhm.simulator.hooks;

import java.util.Set;

import org.joda.time.DateTime;

import uk.org.cse.commons.names.IIdentified;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;

public interface IHookRunnable extends IIdentified {
	public void run(final IStateScope scope,
                    final DateTime date, 
                    final Set<IStateChangeSource> causes, 
                    final ILets lets);
}
