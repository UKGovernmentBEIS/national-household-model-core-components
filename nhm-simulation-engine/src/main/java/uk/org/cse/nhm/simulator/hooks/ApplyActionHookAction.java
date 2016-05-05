package uk.org.cse.nhm.simulator.hooks;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableList;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

public class ApplyActionHookAction extends AbstractNamed implements IHookRunnable, IStateChangeSource {
	private final IDwellingSet source;
	private final List<IStateAction> actions;

	@AssistedInject
	public ApplyActionHookAction(
			@Assisted final IDwellingSet source,
			@Assisted final List<IStateAction> actions) {
		super();
		this.source = source;
		this.actions = ImmutableList.copyOf(actions);
	}

	@Override
	public void run(final IStateScope state,
                    final DateTime date, 
                    final Set<IStateChangeSource> causes, 
                    final ILets lets) {
		final Set<IDwelling> targets = source.get(state.getState(), lets);
		
		final List<IDwelling> shuffled = new ArrayList<>(targets);
		
		state.getState().getRandom().shuffle(shuffled);

        final LinkedHashSet<IDwelling> set = new LinkedHashSet<>(shuffled);
        
        for (final IStateAction action : actions) {
            state.apply(action, set, lets);
        }
	}
	
	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.TRIGGER;
	}
}
