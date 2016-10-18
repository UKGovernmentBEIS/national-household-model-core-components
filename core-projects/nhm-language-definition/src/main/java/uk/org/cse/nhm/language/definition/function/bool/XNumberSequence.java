package uk.org.cse.nhm.language.definition.function.bool;

import java.util.ArrayList;
import java.util.List;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindRemainingArguments;

public abstract class XNumberSequence extends XBoolean {
	public static final String VALUES = "values";
	private List<XNumber> values = new ArrayList<>();

	@BindRemainingArguments
	@Doc("The VALUES to compare with each other.")
	public List<XNumber> getValues() {
		return values;
	}

	public void setValues(final List<XNumber> values) {
		this.values = values;
	}
	
	@Bind(">")
	@Doc("True if for the VALUES given, the first value exceeds the second, the second value exceeds the third, and so on.")
	public static class XGreater extends XNumberSequence {

	}

	@Bind(">=")
	@Doc("True if for the VALUES given, the first value exceeds or equals the second, the second value exceeds or equals the third, and so on.")
	public static class XGreaterEq extends XNumberSequence {

	}
	
	@Bind("<")
	@Doc("True if for the VALUES given, the first value is less than the second, the second value is less than the third, and so on.")
	public static class XLess extends XNumberSequence {

	}

	@Bind("<=")
	@Doc("True if for the VALUES given, the first value is less than or equals the second, the second value is less than or equal to the third, and so on.")
	public static class XLessEq extends XNumberSequence {

	}
	
	@Bind("=")
	@Doc("True if every value for the VALUES given is within 1 x 10^-6 of the next")
	public static class XEqualNumbers extends XNumberSequence {

	}
}
