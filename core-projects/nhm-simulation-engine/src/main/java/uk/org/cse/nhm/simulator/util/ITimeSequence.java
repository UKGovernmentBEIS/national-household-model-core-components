package uk.org.cse.nhm.simulator.util;

import java.util.SortedSet;

import org.joda.time.DateTime;

public interface ITimeSequence {

    public SortedSet<DateTime> getDateTimes();
}
