package uk.org.cse.nhm.language.definition.exposure;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Obsolete;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.two.hooks.XDateHook;
import uk.org.cse.nhm.language.definition.two.selectors.XSampledSet;

@Category(CategoryType.OBSOLETE)
@Obsolete(
        reason = XDateHook.SCHEDULING_OBSOLESCENCE,
        inFavourOf = {XDateHook.class, XSampledSet.class}
)
public abstract class XSampler extends XElement {

}
