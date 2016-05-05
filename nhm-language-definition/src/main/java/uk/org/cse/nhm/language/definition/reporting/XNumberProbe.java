package uk.org.cse.nhm.language.definition.reporting;

import java.util.ArrayList;
import java.util.List;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.XFunction;
import uk.org.cse.nhm.language.definition.function.num.XHouseNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.validate.BatchForbidden;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;


@Bind("function.probe")
@BatchForbidden(element="function.probe")
@Doc(
		value = {
			"Applies a probe to a numerical scenario element. The probe should have no effect on the scenario's behaviour;",
			"instead it provides a view onto the intermediate values produced by the simulator which would otherwise be discarded.",
			"The probe works by delegating the calculation it will do to the element being probed, but also capturing",
			"the output and additional specified details about the house being evaluated.",
			"Some other language elements can provide extra information to probes - for more information see the section on Probeable Values."
		}
	)
@Category(CategoryType.REPORTING)
public class XNumberProbe extends XHouseNumber {
	public static final class P {
		public static final String delegate = "delegate";
		public static final String probedValues = "probedValues";
		public static final String name = "name";
	}
	private XNumber delegate;

	private final List<XFunction> probedValues = new ArrayList<>();
	
	private String name;
	
	@Doc(
			{
				"The element which is under probe - this will be used to determine the value of this probe, as far as its",
				"containing element is concerned; however, after it has been evaluated, the additional probed values within the capture",
				"element will be recorded."
			}
		)
	@BindPositionalArgument(0)
	
	@Prop(P.delegate)
	public XNumber getDelegate() {
		return delegate;
	}
	public void setDelegate(final XNumber delegate) {
		this.delegate = delegate;
	}
	

	@Doc({"Additional values to capture in the probe report, apart from the output of the delegate.",
		"Elements given a name (by specifying their name: argument) will produce columns with a corresponding column header; this is a good way to ensure a consistently formatted file is",
		"produced for easy consumption by other automated systems."
	})
	@BindNamedArgument("capture")
	@Prop(P.probedValues)
	public List<XFunction> getProbedValues() {
		return probedValues;
	}
	
	public void setProbedValues(final List<XFunction> probedValues) {
		this.probedValues.clear();
		this.probedValues.addAll(probedValues);
	}
	
	@Override
	
@BindNamedArgument
	@Doc("The name of this probe - this will be used to name the resulting report.")
	@Prop(P.name)
	public String getName() {
		return name;
	}
	@Override
	public void setName(final String name) {
		this.name = name;
	}
	
	
}
