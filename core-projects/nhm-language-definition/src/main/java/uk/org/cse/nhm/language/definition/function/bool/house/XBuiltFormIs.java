package uk.org.cse.nhm.language.definition.function.bool.house;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.enums.XBuiltFormType;
import uk.org.cse.nhm.language.definition.function.bool.XHouseBoolean;


@Bind("house.built-form-is")
@Doc("A test which matches only houses with the specified built form")
public class XBuiltFormIs extends XHouseBoolean {
	public static final class P {
		public static final String EQUAL_TO = "equalTo";
	}
	
	private XBuiltFormType equalTo;
	
	public void setEqualTo(final XBuiltFormType equalTo) {
		this.equalTo = equalTo;
	}
	
	
	@Prop(P.EQUAL_TO)
	
	@BindPositionalArgument(0)
	@Doc("The built form that a house must have to pass")
	public XBuiltFormType getEqualTo() {
		return equalTo;
	}
}
