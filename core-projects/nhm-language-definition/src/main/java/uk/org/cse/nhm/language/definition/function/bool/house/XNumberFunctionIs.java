package uk.org.cse.nhm.language.definition.function.bool.house;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.Obsolete;
import uk.org.cse.nhm.language.definition.function.bool.XNumberSequence.XEqualNumbers;
import uk.org.cse.nhm.language.definition.function.bool.XNumberSequence.XGreater;
import uk.org.cse.nhm.language.definition.function.bool.XNumberSequence.XGreaterEq;
import uk.org.cse.nhm.language.definition.function.bool.XNumberSequence.XLess;
import uk.org.cse.nhm.language.definition.function.bool.XNumberSequence.XLessEq;
import uk.org.cse.nhm.language.definition.function.num.XNumber;


@Bind("house.value-is")
@Doc("This element converts a numeric attribute of a house into a boolean test.")
@Obsolete(inFavourOf={XEqualNumbers.class, XGreater.class, XGreaterEq.class, XLess.class, XLessEq.class})
public class XNumberFunctionIs extends XDoubleIs {
	public static final class P {
		public static final String number = "number";
	}
	private XNumber number;

	
	@Doc("The numeric function to use in this test.")
	@BindPositionalArgument(0)
	public XNumber getNumber() {
		return number;
	}

	public void setNumber(final XNumber number) {
		this.number = number;
	}
}
