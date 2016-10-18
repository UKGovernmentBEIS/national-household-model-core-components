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

public class ChangeHook extends AbstractHook implements IStateListener, IDateRunnable, Initializable, IStateChangeSource {
	/**
	 * Dates on which we are scheduled to go off
	 */
	private final Set<DateTime> scheduledDates = new HashSet<>();
	/**
	 * Houses which have been affected since we last went off
	 */
	private final LinkedHashSet<IDwelling> affectedHouses = new LinkedHashSet<>();
	/**
	 * Causes of us going off
	 */
	private final LinkedHashSet<IStateChangeSource> causes = new LinkedHashSet<>();

	/**
	 * If false, don't go off for stock creator
	 */
	private final boolean includeStockCreator;
	
	private final ISimulator simulator;
	final ICanonicalState state;
	
	@AssistedInject
	protected ChangeHook(
			final ISimulator simulator,
			final ICanonicalState state,
			@Assisted final boolean includeStockCreator,
			@Assisted final List<IHookRunnable> delegates) {
		super(delegates, simulator);
		this.simulator = simulator;
		this.state = state;
		this.includeStockCreator = includeStockCreator;
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
		if (!includeStockCreator) {
			for (final IStateChangeSource s : notification.getCauses()) {
				if (s.getSourceType() == StateChangeSourceType.CREATION) return;
			}
		}
		// if one of the accumulated causes of this notification is this hook
		// just bail as we would be recursive otherwise
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
		if (!notification.getAllChangedDwellings().isEmpty() ||
				!notification.getCreatedDwellings().isEmpty()) {
			affectedHouses.addAll(notification.getAllChangedDwellings());
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
