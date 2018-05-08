package uk.org.cse.nhm.simulator.state.functions.impl.house;

import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;

import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.state.IComponents;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public abstract class StructureFunction<T> extends AbstractNamed implements IComponentsFunction<T> {

    private final IDimension<StructureModel> structure;

    protected StructureFunction(IDimension<StructureModel> structure) {
        this.structure = structure;
    }

    protected StructureModel getStructure(final IComponents c) {
        return c.get(structure);
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return Collections.<IDimension<?>>singleton(structure);
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return Collections.emptySet();
    }

}
