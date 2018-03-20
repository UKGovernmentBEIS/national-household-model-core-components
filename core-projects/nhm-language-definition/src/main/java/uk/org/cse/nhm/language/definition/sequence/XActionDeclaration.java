package uk.org.cse.nhm.language.definition.sequence;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;
import com.larkery.jasb.bind.Identity;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.IScenarioElement;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.action.XDwellingAction;
import uk.org.cse.nhm.language.validate.contents.Declaration;

@Declaration
@Bind("def-action")
@Category(CategoryType.DECLARATIONS)
public class XActionDeclaration extends XDwellingAction implements IScenarioElement<XElement> {
	public static final String VALUE = "value";
	private XDwellingAction value;
	
	@Doc("The name of the action to declare")
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
	@NotNull(message = "def-action requires an action as its second argument")
	@Doc("The action itself")
	@Prop(VALUE)
	public XDwellingAction getValue() {
		return value;
	}

	public void setValue(final XDwellingAction value) {
		this.value = value;
	}
}
