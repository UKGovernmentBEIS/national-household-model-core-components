package uk.org.cse.nhm.simulator.state.dimensions.time;

import java.util.EnumSet;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;

import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.impl.IInternalDimension;

/**
 * This is the counterpart to the {@link RealTimeDimension}, which supports
 * variation in the current date depending on the foresight levels of the person
 * asking. The way this works is:
 *
 * - the date is changed (typically advanced) or not for the different foresight
 * levels by something else, like a function which predicts the future in some
 * way. - when a function is being computed, it will ask the surreal time
 * dimension for the date at the forecast level that is required to be able to
 * predict that function.
 *
 * So, if the context cannot forecast tariffs, the value in {@link #times} under
 * {@link XForesightLevel#Tariffs} will not advance. When a time function is
 * computed in a tariff, it will probably experience that foresight level as the
 * value stored in the {@link ILets}. That value will be passed to
 * {@link #get(ILets)} through to {@link #get(XForesightLevel)} below, and so
 * the unchanging date is what will always be returned.
 */
public class SurrealTimeDimension implements ITimeDimension, IInternalDimension<ITime>, ITime {

    private final Set<XForesightLevel> predictableLevels;
    private final DateTime trueDate;
    private DateTime fakeDate;

    private final int index;
    private int modificationCount;

    public SurrealTimeDimension(final int index, final DateTime startDate, final Set<XForesightLevel> predictableLevels) {
        this.predictableLevels = EnumSet.copyOf(predictableLevels);
        this.predictableLevels.add(XForesightLevel.Always);
        this.predictableLevels.remove(XForesightLevel.Never);
        this.index = index;
        this.modificationCount = 0;
        this.trueDate = startDate;
        this.fakeDate = startDate;
    }

    @Override
    public ITime get(final IDwelling instance) {
        return this;
    }

    @Override
    public int getGeneration(final IDwelling instance) {
        return modificationCount;
    }

    @Override
    public ITime copy(final IDwelling instance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean set(final IDwelling instance, final ITime value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void merge(final IDwelling instance, final IInternalDimension<ITime> branch) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IInternalDimension<ITime> branch(final IBranch forkingState, final int capacity) {
        return this;
    }

    @Override
    public boolean isEqual(final ITime a, final ITime b) {
        return false;
    }

    @Override
    public boolean isSettable() {
        return false;
    }

    @Override
    public int index() {
        return index;
    }

    @Override
    public boolean setCurrentTime(final DateTime dateTime) {
        fakeDate = dateTime;
        modificationCount++;
        return true;
    }

    @Override
    public DateTime get(final XForesightLevel key) {
        if (predictableLevels.contains(key)) {
            return fakeDate;
        } else {
            return trueDate;
        }
    }

    @Override
    public DateTime get(final ILets lets) {
        // shortcut for empty
        if (lets == ILets.EMPTY) {
            return get(XForesightLevel.Default);
        }
        return get(lets.get(ITime.TIME_KEY, XForesightLevel.class).or(XForesightLevel.Default));
    }

    @Override
    public DateTime get(final Optional<XForesightLevel> foresight, final ILets lets) {
        if (foresight.isPresent()) {
            return get(foresight.get());
        } else {
            return get(lets);
        }
    }

    @Override
    public Set<XForesightLevel> predictableLevels() {
        return predictableLevels;
    }
}
