package uk.org.cse.nhm.simulator.util;

import java.util.EnumSet;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;

import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITime;

public class TimeUtil {
	public static ITime mockTime(final DateTime when) {
		return new ITime() {
			@Override
			public DateTime get(final Optional<XForesightLevel> foresight, final ILets lets) {
				return when;
			}
			
			@Override
			public DateTime get(final ILets lets) {
				return when;
			}
			
			@Override
			public DateTime get(final XForesightLevel key) {
				return when;
			}
			
			@Override
			public Set<XForesightLevel> predictableLevels() {
				return EnumSet.allOf(XForesightLevel.class);
			}
		};
	}
}
