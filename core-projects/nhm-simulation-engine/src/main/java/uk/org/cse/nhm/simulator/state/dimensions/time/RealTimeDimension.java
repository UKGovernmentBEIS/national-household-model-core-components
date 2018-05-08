package uk.org.cse.nhm.simulator.state.dimensions.time;

import java.util.EnumSet;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.base.Optional;

import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.dimensions.DimensionCounter;
import uk.org.cse.nhm.simulator.state.impl.IInternalDimension;

/**
 * This time dimension implementation keeps track of the real time, which is the
 * same whoever's asking. It is the variation that is going to be in almost all
 * places in the simulation, and by and large there is only ever one instance of
 * it.
 *
 * In some hypoethetical circumstances, the {@link SurrealTimeDimension} will be
 * emplaced on top of this, allowing time to be rewritten.
 */
public class RealTimeDimension implements ITimeDimension, ITime {

    DateTime now;
    private final int index;
    private int generation;

    @Inject
    public RealTimeDimension(final DimensionCounter dc) {
        now = new DateTime(0);
        index = dc.next();
    }

    @Override
    public ITime get(final IDwelling instance) {
        return this;
    }

    @Override
    public int getGeneration(final IDwelling instance) {
        return generation;
    }

    @Override
    public ITime copy(final IDwelling instance) {
        return this;
    }

    @Override
    public boolean set(final IDwelling instance, final ITime value) {
        return false;
    }

    @Override
    public void merge(final IDwelling instance, final IInternalDimension<ITime> branch) {
    }

    @Override
    public IInternalDimension<ITime> branch(final IBranch forkingState, final int capacity) {
        return this;
    }

    @Override
    public boolean isEqual(final ITime a, final ITime b) {
        return a == b;
    }

    @Override
    public boolean isSettable() {
        return false;
    }

    @Override
    public int index() {
        return this.index;
    }

    @Override
    public boolean setCurrentTime(final DateTime dateTime) {
        if (now.equals(dateTime)) {
            return false;
        } else {
            now = dateTime;
            generation++;
            return true;
        }
    }

    @Override
    public DateTime get(final XForesightLevel key) {
        return now;
    }

    @Override
    public DateTime get(final ILets lets) {
        return now;
    }

    @Override
    public DateTime get(final Optional<XForesightLevel> foresight, final ILets lets) {
        return now;
    }

    @Override
    public Set<XForesightLevel> predictableLevels() {
        return EnumSet.allOf(XForesightLevel.class);
    }
}
