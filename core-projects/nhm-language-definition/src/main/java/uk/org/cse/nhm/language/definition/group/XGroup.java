package uk.org.cse.nhm.language.definition.group;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.Obsolete;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.two.selectors.XAllTheHouses;
import uk.org.cse.nhm.language.definition.two.selectors.XFilteredSet;

import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.Identity;

@Category(CategoryType.OBSOLETE)
@Obsolete(
	reason = "Groups are being removed in favour of sets of houses.",
	inFavourOf = {XAllTheHouses.class, XFilteredSet.class}
		)
public abstract class XGroup extends XElement {
	@Override
	@Doc("A unique name for this group")
	
	@BindNamedArgument
	
	@Identity
	public String getName() {
		return super.getName();
	}

	@Override
	public void setName(final String name) {
		super.setName(name);
	}
}
