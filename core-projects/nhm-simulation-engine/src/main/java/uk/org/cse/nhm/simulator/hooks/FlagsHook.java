package uk.org.cse.nhm.simulator.hooks;

import java.util.BitSet;
import java.util.List;
import java.util.Set;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.commons.Glob;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.components.IFlags;

public class FlagsHook extends ChangeHook {

    private final BitSet history = new BitSet();

    private final IDimension<IFlags> flagsDimension;
    private final List<Glob> conditions;
    private final boolean affectedIn, affectedOut;

    @AssistedInject
    public FlagsHook(
            final ISimulator simulator,
            final ICanonicalState state,
            final IDimension<IFlags> flagsDimension,
            @Assisted final List<IHookRunnable> delegates,
            @Assisted final List<Glob> conditions,
            @Assisted("in") final boolean affectedIn,
            @Assisted("out") final boolean affectedOut) {
        super(simulator, state, true, delegates);
        this.flagsDimension = flagsDimension;
        this.conditions = conditions;
        this.affectedIn = affectedIn;
        this.affectedOut = affectedOut;
    }

    @Override
    void getAffectedHouses(final IStateChangeNotification notification, final Set<IDwelling> affectedHouses) {
        for (final IDwelling d : notification.getChangedDwellings(flagsDimension)) {
            final IFlags flags = state.get(flagsDimension, d);
            final boolean matchesNow = flags.flagsMatch(conditions);
            final boolean matchedThen = history.get(d.getID());
            history.set(d.getID(), matchesNow);
            if (matchesNow != matchedThen) {
                if ((affectedIn && matchesNow) || (affectedOut && matchedThen)) {
                    affectedHouses.add(d);
                }
            }
        }
    }
}
