package uk.org.cse.nhm.language.definition.action.scaling;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.XFlaggedDwellingAction;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

import com.larkery.jasb.bind.BindPositionalArgument;

@Category(CategoryType.RESETACTIONS)
abstract public class XScalingMeasure extends XFlaggedDwellingAction {
	public static class P {
		public static final String scaling = "scaling";
		public static final String of = "of";
	}
	
	private XNumber scaling;
	
	@NotNull(message = "scaling actions must always contain a number function which will determine the level of scaling to apply.")
	@Prop(P.scaling)
	@Doc("Determines the resulting value to set. New value = old value * (1 + scaling).")
	@BindPositionalArgument(0)
	public final XNumber getScaling() {
		return scaling;
	}
	
	public final void setScaling(final XNumber scaling) {
		this.scaling = scaling;
	}
}
