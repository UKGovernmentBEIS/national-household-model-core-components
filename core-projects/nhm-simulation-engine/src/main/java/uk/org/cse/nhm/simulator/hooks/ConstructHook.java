package uk.org.cse.nhm.simulator.hooks;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.main.IDateRunnable;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.main.Initializable;
import uk.org.cse.nhm.simulator.main.Priority;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.IStateListener;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

public class ConstructHook extends AbstractHook implements IStateListener, IDateRunnable, Initializable, IStateChangeSource {
	private final DateTime from, until;

    private final Set<DateTime> scheduledDates = new HashSet<>();
    private final LinkedHashSet<IDwelling> affectedHouses = new LinkedHashSet<IDwelling>();
    
	private final ISimulator simulator;
	final ICanonicalState state;

	private Set<IStateChangeSource> causes = new LinkedHashSet<IStateChangeSource>();
	
	@AssistedInject
	protected ConstructHook(
			final ISimulator simulator,
			final ICanonicalState state,
			@Assisted("from") final DateTime from,
            @Assisted("until") final DateTime until,
			@Assisted final List<IHookRunnable> delegates) {
		super(delegates, simulator);
		this.simulator = simulator;
		this.state = state;
        this.from = from;
        this.until = until;
	}
	
	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.TRIGGER;
	}
	
	@Override
	public void initialize() throws NHMException {
		state.addStateListener(this);
	}
	
	@Override
	public void stateChanged(final ICanonicalState state, final IStateChangeNotification notification) {
        final DateTime now = state.getTrueDate();

        // too lazy to setup listener on the right date.
        if (now.isBefore(from)) return;

        if (now.isAfter(until)) {
            state.removeStateListener(this);
            return;
        }

		// I'm not sure what happens if you use on.construct to construct more houses
        // but it's probably a pretty mad thing to do
		if (notification.getCauses().contains(this)) {
            affectedHouses.clear();
			return;
		}
		
		getAffectedHouses(notification, affectedHouses);
		
		// finally consider scheduling an event
		if (!affectedHouses.isEmpty() && !scheduledDates.contains(notification.getDate())) {
			simulator.schedule(notification.getDate(), Priority.ofIdentifier(getIdentifier()), this);
			scheduledDates.add(notification.getDate());
		}
	}

	void getAffectedHouses(final IStateChangeNotification notification, final Set<IDwelling> affectedHouses) {
		// if anything is changed, record the fact
		if (!notification.getCreatedDwellings().isEmpty()) {
			affectedHouses.addAll(notification.getCreatedDwellings());
			causes.addAll(notification.getCauses());
		}
	}
	
	@Override
	public void run(final DateTime date) {
		causes.add(this);
		super.runDelegates(state, date, causes, affectedHouses);
		scheduledDates.remove(date);
		affectedHouses.clear();
		causes.clear();
	}
}
