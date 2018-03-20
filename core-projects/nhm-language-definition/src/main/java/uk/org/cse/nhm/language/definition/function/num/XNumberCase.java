package uk.org.cse.nhm.language.definition.function.num;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.function.bool.XBoolean;



@Bind("function.case")
@Doc(
		{
			"A function which determines its value by evaluating a series of boolean tests.",
			"The value associated with the first test (in order of writing) which is passed is the value produced by the function."
		}
	)
@Category(CategoryType.ARITHMETIC)

public class XNumberCase extends XNumber {
	
	@Bind("when")
	@Doc("One of the mutually exclusive conditions in a case function.")
	public static class XNumberCaseWhen extends XElement {
		public static final class P {
			public static final String test = "test";
			public static final String value = "value";
		}
		private XBoolean test;
		private XNumber value;
		
		@NotNull(message = "when element must contain a test")
		@Doc("The test for this case.")
		@BindPositionalArgument(0)
		public XBoolean getTest() {
			return test;
		}
		public void setTest(final XBoolean test) {
			this.test = test;
		}
		
		@NotNull(message = "when element must contain a number to return if the test passes")
		@Doc("The function to be used for the value, if the associated test is the first to pass")
		@BindPositionalArgument(1)
		public XNumber getValue() {
			return value;
		}
		public void setValue(final XNumber value) {
			this.value = value;
		}
	}
	
	public static final class P {
		public static final String cases = "cases";
		public static final String otherwise = "otherwise";
	}
	
	private List<XNumberCaseWhen> cases = new ArrayList<XNumberCaseWhen>();
	private XNumber otherwise;
	
	
	@BindRemainingArguments
	@Doc("A sequence of tests and values - the first test which passes determines the value of this function.")
	public List<XNumberCaseWhen> getCases() {
		return cases;
	}
	public void setCases(final List<XNumberCaseWhen> cases) {
		this.cases = cases;
	}
	
	
	@BindNamedArgument("default")
	@NotNull(message="function.case element must contain an otherwise element to specify the default value if all the tests fail")
	@Doc("The fall-back value of the case - if a house passes none of the case tests, this is the resulting value.")
	public XNumber getOtherwise() {
		return otherwise;
	}
	public void setOtherwise(final XNumber otherwise) {
		this.otherwise = otherwise;
	}
}
