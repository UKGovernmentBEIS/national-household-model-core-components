package uk.org.cse.nhm.language.definition.function.bool.house;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.language.definition.function.bool.XHouseBoolean;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;


@Bind("house.main-heating-fuel-is")
@Doc("A test which matches houses with the specified main heating fuel type.")
public class XMainHeatingFuelIs extends XHouseBoolean {
	public static final class P {
		public static final String EQUAL_TO = "equalTo";
	}
	
	private XFuelType equalTo;
	
	public void setEqualTo(final XFuelType equalTo) {
		this.equalTo = equalTo;
	}
	
	@Prop(P.EQUAL_TO)
	@Doc("The type of main heating fuel which a house must have to pass the test.")
	
	@BindPositionalArgument(0)
	public XFuelType getEqualTo() {
		return equalTo;
	}
}
