package uk.org.cse.nhm.language.definition.two.dates;

import org.joda.time.DateTime;

import uk.org.cse.nhm.language.two.build.IBuilder;

import com.larkery.jasb.bind.Bind;

@Bind("scenario-start")
public class XSimStartDate extends XDate {
	@Override
	public DateTime asDate(IBuilder builder) {
		return builder.getStartDate();
	}
}
