package uk.org.cse.nhm.language.definition.action.measure.insulation;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.action.XFlaggedDwellingAction;
import uk.org.cse.nhm.language.definition.action.reset.XResetRoofs;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

@Doc(
	{"Adds or removes a loft from any buildings. If removing a loft, it will also remove any loft insulation that is present. It does not update the u-value afterwards."}
	)
@SeeAlso(XResetRoofs.class)
@Bind("action.set-has-loft")
@Unsuitability(alwaysSuitable = true)
@Category(CategoryType.RESETACTIONS)

public class XSetLoftAction extends XFlaggedDwellingAction {
	private boolean addLoft;

	@Doc("Whether the house is to have a loft after the action is applied.")
	@BindPositionalArgument(0)
	public boolean isAddLoft() {
		return addLoft;
	}

	public void setAddLoft(final boolean addLoft) {
		this.addLoft = addLoft;
	}
}
