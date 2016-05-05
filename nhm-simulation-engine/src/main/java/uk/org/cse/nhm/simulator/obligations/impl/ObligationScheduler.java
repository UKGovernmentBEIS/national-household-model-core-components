package uk.org.cse.nhm.simulator.obligations.impl;

import java.util.Iterator;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.carrotsearch.hppc.ObjectIntMap;
import com.carrotsearch.hppc.ObjectIntOpenHashMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.main.Initializable;
import uk.org.cse.nhm.simulator.obligations.IObligation;
import uk.org.cse.nhm.simulator.obligations.IObligationHistory;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.IStateListener;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;

/**
 * This class watches for the addition of obligations to dwellings, 
 * and invokes those obligations {@link IObligation#handle(IDwelling)} methods.
 * 
 * I guess this could probably happen as a special behaviour in state instead.
 * 
 * @since 3.7.0
 *
 */
public class ObligationScheduler implements IStateListener, Initializable {
	private static final Logger log = LoggerFactory.getLogger(ObligationScheduler.class);
	final IDimension<IObligationHistory> obligationDimension;
	final ICanonicalState state;
	
	/**
	 * Tracks the number of obligations for each dwelling that we have processed; when a dwelling is changed,
	 * we will only consider scheduling obligations that are added after this point
	 */
	final ObjectIntMap<IDwelling> obligationCountByDwelling = new ObjectIntOpenHashMap<IDwelling>();

    final Multimap<IDwelling, IObligation> dwellingObligations = HashMultimap.create();
	
	@Inject
	public ObligationScheduler(
			ITimeDimension time,
			ISimulator simulator,
			IDimension<IObligationHistory> obligationDimension,
			ICanonicalState state) {
		this.obligationDimension = obligationDimension;
		this.state = state;
	}
	
	@Override
	public void initialize() throws NHMException {
		state.addStateListener(this);
		log.debug("listening for obligation-related changes");
	}

	@Override
	public void stateChanged(final ICanonicalState state, final IStateChangeNotification notification) {
		for (final IDwelling d : notification.getCreatedDwellings()) {
			updateDwelling(state, d);
		}
		
		for (final IDwelling d : notification.getChangedDwellings(obligationDimension)) {
			updateDwelling(state, d);
		}

        for (final IDwelling d : notification.getDestroyedDwellings()) {
            for (final IObligation ob : dwellingObligations.get(d)) {
                ob.forget(d);
            }
            dwellingObligations.removeAll(d);
        }
	}

	protected void updateDwelling(final ICanonicalState state, final IDwelling d) {
		// run backwards through obligations on the dwelling until we reach obligations we already know about.
		final IObligationHistory obligationsForDwelling = state.get(obligationDimension, d);
		final int currentObligationCount = obligationsForDwelling.size();
		final int previousObigationCount = obligationCountByDwelling.get(d);
		obligationCountByDwelling.put(d, currentObligationCount);
		
		int obligationsToConsider = currentObligationCount - previousObigationCount;
		
		final Iterator<IObligation> fromNewestToOldest = obligationsForDwelling.reverseIterator();
		
		while (fromNewestToOldest.hasNext() && obligationsToConsider > 0) {
			obligationsToConsider--;
			final IObligation obligation = fromNewestToOldest.next();
			obligation.handle(d);
            dwellingObligations.put(d, obligation);
		}
	}
}
