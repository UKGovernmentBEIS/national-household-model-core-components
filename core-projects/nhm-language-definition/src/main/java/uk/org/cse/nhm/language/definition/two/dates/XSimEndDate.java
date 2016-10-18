package uk.org.cse.nhm.language.definition.two.dates;

import org.joda.time.DateTime;

import uk.org.cse.nhm.language.two.build.IBuilder;

import com.larkery.jasb.bind.Bind;

@Bind("scenario-end")
public class XSimEndDate extends XDate {
	@Override
	public DateTime asDate(IBuilder builder) {
		return builder.getEndDate();
	}
}
