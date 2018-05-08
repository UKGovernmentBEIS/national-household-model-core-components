package uk.org.cse.nhm.simulator.state.functions.impl.house;

import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.state.IComponents;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;

public abstract class AgeFunction<T> extends BasicAttributesFunction<T> {

    private final ITimeDimension time;

    protected AgeFunction(IDimension<BasicCaseAttributes> basic, ITimeDimension time) {
        super(basic);
        this.time = time;
    }

    protected int getAge(final IComponents components, final ILets lets) {
        final DateTime dt = components.get(time).get(lets);
        final int buildYear = getAttributes(components).getBuildYear();
        final int nowYear = dt.getYear();

        return nowYear - buildYear;
    }

    @Override
    public Set<DateTime> getChangeDates() {
        throw new RuntimeException("This is not implemented");
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return ImmutableSet.<IDimension<?>>builder()
                .addAll(super.getDependencies())
                .add(time)
                .build();
    }
}
