package uk.org.cse.nhm.language.definition.sequence;

import java.util.Collections;
import java.util.Set;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.IScenarioElement;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.validate.contents.Declaration;
import uk.org.cse.nhm.language.validate.contents.ISpecialContentsForValidation;

import com.google.common.collect.ImmutableSet;
import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;
import com.larkery.jasb.bind.Identity;

@Declaration
@Category(CategoryType.DECLARATIONS)
@Doc("Declares a variable; can also be used to get its value")
@Bind("def")
public class XNumberDeclaration extends XNumber implements IScenarioElement<XElement>, ISpecialContentsForValidation {
	private XScope on = XScope.Event;
	private Double defaultValue;
	
	@Doc("The name of the variable to declare")
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

	@Doc({"The scope in which the variable should be declared"})
	@BindNamedArgument
	public XScope getOn() {
		return on;
	}

	public void setOn(final XScope scope) {
		this.on = scope;
	}

	// TODO this could be an error-number 
	@Doc("The value to use if the variable is accessed when it has never been set.")
	@BindNamedArgument("default")
	public Double getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(final Double defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	@Override
	public Set<Class<?>> getAdditionalRequirements() {
		if (on == XScope.House) {
			return ImmutableSet.<Class<?>>of(IHouseContext.class);
		} else {
			return Collections.emptySet();
		}
	}

    @Override
    public Set<Class<?>> getAdditionalProvisions() {
        return Collections.emptySet();
    }
}
