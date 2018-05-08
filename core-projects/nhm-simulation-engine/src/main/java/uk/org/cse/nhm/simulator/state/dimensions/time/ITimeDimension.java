package uk.org.cse.nhm.simulator.state.dimensions.time;

import org.joda.time.DateTime;

import uk.org.cse.nhm.simulator.state.impl.IInternalDimension;

public interface ITimeDimension extends IInternalDimension<ITime> {

    /**
     * Update the current time, returning true if it has changed.
     *
     * @param dateTime
     * @return
     */
    public boolean setCurrentTime(final DateTime dateTime);
}
