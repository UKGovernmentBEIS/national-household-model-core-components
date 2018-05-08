package uk.org.cse.nhm.language.visit.impl;

import java.util.Set;

import uk.org.cse.nhm.language.adapt.IAdapter;
import uk.org.cse.nhm.language.adapt.IAdapterDelegator;
import uk.org.cse.nhm.language.visit.IVisitable;
import uk.org.cse.nhm.language.visit.IVisitor;

public class AdapterInstaller<T extends IVisitable<T>> implements IVisitor<T> {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory
            .getLogger(AdapterInstaller.class);

    private final Set<IAdapter> adapters;

    public AdapterInstaller(final Set<IAdapter> adapters) {
        this.adapters = adapters;
    }

    @Override
    public void visit(final T v) {
        if (v instanceof IAdapterDelegator) {
            for (final IAdapter a : adapters) {
                ((IAdapterDelegator) v).addAdapter(a);
            }
        } else {
            log.debug("skipping {} (not adapter delegator)", v);
        }
    }

    @Override
    public void leave(final T v) {

    }

    @Override
    public boolean enter(final T v) {
        return true;
    }

    @Override
    public String toString() {
        return "Adapter Adding Visitor";
    }
}
