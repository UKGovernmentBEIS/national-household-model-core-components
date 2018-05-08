package uk.org.cse.nhm.simulator.util;

import org.joda.time.DateTime;

public interface ITimeSeries<T> extends ITimeSequence {

    public T getValue(final DateTime dateTime);
}
