package uk.org.cse.nhm.language.definition.function.bool;

import java.util.ArrayList;
import java.util.List;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;

import com.larkery.jasb.bind.BindRemainingArguments;

@Category(CategoryType.LOGICCOMB)
public abstract class XBooleanWithInputs extends XBoolean {
	public static final class P {
		public static final String INPUTS = "inputs";
	}
	
	private List<XBoolean> inputs = new ArrayList<XBoolean>();
	
	@BindRemainingArguments
	
	@Prop(P.INPUTS)
	@Doc("The inputs to this boolean function")
	public List<XBoolean> getInputs() {
		return inputs;
	}
	
	public void setInputs(final List<XBoolean> inputs) {
		this.inputs = inputs;
	}
}
