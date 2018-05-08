package uk.org.cse.nhm.language.definition.two.dates;

import java.util.List;

import org.joda.time.DateTime;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.TopLevel;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.two.build.IBuilder;

@Category(CategoryType.SCHEDULING)
@TopLevel
public abstract class XDateSequence extends XElement {

    public abstract List<DateTime> asDates(final IBuilder builder);
}
