package uk.org.cse.nhm.language.definition.function.bool.house;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.enums.XRegionType;
import uk.org.cse.nhm.language.definition.function.bool.XHouseBoolean;


@Bind("house.region-is")
@Doc("Tests whether a house is in a particular region")
public class XRegionIs extends XHouseBoolean {
	public static final class P {
		public static final String EQUAL_TO = "equalTo";
	}
	
	private XRegionType equalTo;
	
	public void setEqualTo(final XRegionType equalTo) {
		this.equalTo = equalTo;
	}
	
	@Prop(P.EQUAL_TO)
	@Doc("If the house is in this region, the house passes this test")
	
	@BindPositionalArgument(0)
	public XRegionType getEqualTo() {
		return equalTo;
	}
}
