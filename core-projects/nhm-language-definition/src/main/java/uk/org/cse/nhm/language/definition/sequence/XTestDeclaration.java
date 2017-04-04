package uk.org.cse.nhm.language.definition.sequence;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.IScenarioElement;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.function.bool.XBoolean;
import uk.org.cse.nhm.language.validate.contents.Declaration;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;
import com.larkery.jasb.bind.Identity;

@Declaration
@Bind("def-test")
@Category(CategoryType.DECLARATIONS)
public class XTestDeclaration extends XBoolean implements IScenarioElement<XElement> {
	public static final String VALUE = "value";
	private XBoolean value;
	
	@Doc("The name of the function to declare")
	@NotNull(message = "all declarations require a name, as their first unnamed argument")
	@Identity
	@BindPositionalArgument(0)
	@Override
	public String getName() {
		return super.getName();
	}

	@Override
	public void setName(final String name) {
		super.setName(name);
	}

	@BindPositionalArgument(1)
	@NotNull(message = "def-test requires a test as its second argument")
	@Doc("The test itself")
	@Prop(VALUE)
	public XBoolean getValue() {
		return value;
	}

	public void setValue(final XBoolean value) {
		this.value = value;
	}
}
