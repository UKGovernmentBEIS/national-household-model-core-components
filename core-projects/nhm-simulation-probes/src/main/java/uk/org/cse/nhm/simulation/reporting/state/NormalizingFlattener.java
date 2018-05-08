package uk.org.cse.nhm.simulation.reporting.state;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import uk.org.cse.nhm.logging.logentry.components.BasicCaseAttributesLogComponent;
import uk.org.cse.nhm.simulator.state.IComponents;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;

/**
 * An {@link IComponentFlattener} which serializes out the structure state into
 * mongodb.
 *
 * This does deduplication to reduce mongo traffic
 *
 * @author hinton
 *
 */
public abstract class NormalizingFlattener<T> implements IComponentFlattener {

    /**
     * This stores generated IDs for cases (within-run)
     */
    private final HashMap<T, Integer> genIDs = new HashMap<T, Integer>();
    /**
     * Used to generate unique IDs for cases
     */
    private final AtomicInteger counter = new AtomicInteger();

    protected abstract T getValue(final IComponents components);

    /**
     * Convert a dwelling's structure into an ID, and if necessary store the ID
     * <-> house case mapping into mongo.
     */
    @Override
    public Object flatten(IState state, final IDwelling dwelling) {
        final T hc = getValue(state.detachedScope(dwelling));
        final int ID = getID(hc);
        return new BasicCaseAttributesLogComponent(ID);
    }

    protected abstract void valueNormalized(final T value, final int id);

    /**
     * Yield a new ID for the house case, or an existing ID if the same house
     * case has already passed through
     *
     * this is not synchronized, so it is dangerous. Synchronizing it makes it
     * extraordinarily slow.
     *
     * @param hc
     * @return an ID
     */
    private int getID(final T hc) {
        Integer s = genIDs.get(hc);
        if (s == null) {
            s = generateID();
            genIDs.put(hc, s);
            valueNormalized(hc, s);
        }
        return s;
    }

    private Integer generateID() {
        return counter.getAndIncrement();
    }
}
