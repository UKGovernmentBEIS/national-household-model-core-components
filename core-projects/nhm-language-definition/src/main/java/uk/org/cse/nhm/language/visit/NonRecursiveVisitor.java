package uk.org.cse.nhm.language.visit;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

/**
 * A visitor which will not visit identical elements if it is already inside
 * them; it is different from {@link SinglyVisitingVisitor} because it will
 * visit something twice if it occurs in two places, just not if it is used
 * within its own definition.
 *
 * @author hinton
 *
 * @param <V>
 */
public class NonRecursiveVisitor<V extends IVisitable<V>> implements IVisitor<V> {

    private final Set<V> elements = Collections.newSetFromMap(new IdentityHashMap<V, Boolean>());

    @Override
    public final boolean enter(final V v) {
        if (elements.contains(v)) {
            wouldRecur(v);
            return false;
        } else {
            elements.add(v);
            return doEnter(v);
        }
    }

    protected boolean doEnter(final V v) {
        return true;
    }

    protected void wouldRecur(final V v) {

    }

    @Override
    public void visit(final V v) {

    }

    @Override
    public final void leave(final V v) {
        elements.remove(v);
        doLeave(v);
    }

    protected void doLeave(final V v) {

    }

}
