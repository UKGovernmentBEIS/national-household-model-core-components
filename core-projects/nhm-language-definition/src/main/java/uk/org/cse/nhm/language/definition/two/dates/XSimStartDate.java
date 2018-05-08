package uk.org.cse.nhm.language.definition.two.dates;

import org.joda.time.DateTime;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.two.build.IBuilder;

@Bind("scenario-start")
public class XSimStartDate extends XDate {

    @Override
    public DateTime asDate(IBuilder builder) {
        return builder.getStartDate();
    }
}
