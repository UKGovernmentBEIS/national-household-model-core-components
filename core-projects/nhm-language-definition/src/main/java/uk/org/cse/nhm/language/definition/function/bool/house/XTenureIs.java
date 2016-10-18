package uk.org.cse.nhm.language.definition.function.bool.house;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.enums.XTenureType;
import uk.org.cse.nhm.language.definition.function.bool.XHouseBoolean;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;


@Bind("house.tenure-is")
@Doc("Tests whether the household has a particular tenure type.")
public class XTenureIs extends XHouseBoolean {
	public static final class P {
		public static final String EQUAL_TO = "equalTo";
	}
	
	private XTenureType equalTo;
	
	public void setEqualTo(final XTenureType equalTo) {
		this.equalTo = equalTo;
	}
	
	@Prop(P.EQUAL_TO)
	@Doc("The tenure type which the house must have to pass")
	
	@BindPositionalArgument(0)
	public XTenureType getEqualTo() {
		return equalTo;
	}
}
