package uk.org.cse.nhm.language.definition.two.dates;

import org.joda.time.DateTime;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.two.build.IBuilder;

@Doc({
    "Contains a single date; these can also be written in the form dd/mm/yyyy."
})

@Bind("just")
public class XSingleDate extends XDate {

    protected DateTime date;

    @BindPositionalArgument(0)
    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    @Override
    public DateTime asDate(IBuilder builder) {
        return date;
    }
}
