package uk.org.cse.nhm.language.definition.two.dates;

import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;

import uk.org.cse.nhm.language.two.build.IBuilder;

public abstract class XDate extends XDateSequence {
	@Override
	public List<DateTime> asDates(IBuilder builder) {
		return Collections.<DateTime>singletonList(asDate(builder));
	}

	public abstract DateTime asDate(IBuilder builder);
}
