package uk.org.cse.nhm.language.definition.reporting.modes;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Obsolete;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.two.hooks.XDateHook;

@Category(CategoryType.OBSOLETE)
@Obsolete(
        reason = XDateHook.SCHEDULING_OBSOLESCENCE,
        inFavourOf = {XDateHook.class}
)
abstract public class XReportMode extends XElement {
}
