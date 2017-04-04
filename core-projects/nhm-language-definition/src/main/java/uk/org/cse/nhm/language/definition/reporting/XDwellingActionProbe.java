package uk.org.cse.nhm.language.definition.reporting;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.action.XDwellingAction;
import uk.org.cse.nhm.language.definition.action.XFlaggedDwellingAction;
import uk.org.cse.nhm.language.definition.function.XFunction;
import uk.org.cse.nhm.language.validate.BatchForbidden;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;

@Doc(
		value = {
				"Report on the the values of the capture: argument before and after applying the given action.",
				"The report produced is disaggregated, containing one row for each time the probe is applied to a dwelling.",
				"Every row will contain the simulation date, dwelling ID, dwelling weight, and the before-and-after values",
				"of each captured value. If the action being probed is unsuitable, the after values will all be 'n/a' (not applicable)",
				"and the 'Succeeded' column will be 'false'."
			}
	)
@Unsuitability("the action being probed is unsuitable")
@Bind("probe")
@BatchForbidden(element = "probe")
@Category(CategoryType.REPORTING)
public class XDwellingActionProbe extends XFlaggedDwellingAction {
	public static final class P {
		public static final String name = "name";
		public static final String delegate = "delegate";
		public static final String probedValues = "probedValues";
	}
	
	private String name;
	private XDwellingAction delegate;
	private final List<XFunction> probedValues = new ArrayList<>();
	
	@Override
	@BindNamedArgument
	@Doc("The name of the file to write the probed values into.")
	@NotNull(message = "action.probe must have a name.")
	public String getName() {
		return name;
	}
	@Override
	public void setName(final String name) {
		this.name = name;
	}
	
	@BindPositionalArgument(0)
	@Doc("The action which is to be probed - each row in the report will describe the condition of a house before and after this action is applied to it.")
	public XDwellingAction getDelegate() {
		return delegate;
	}
	public void setDelegate(final XDwellingAction delegate) {
		this.delegate = delegate;
	}
	
	@Doc({
		"Defines the values which should be captured by this measure probe. Each element this contains denotes values to be recorded before and after each use of the delegate action.",
		"Elements given a name (by specifying their name: argument) will produce columns with a corresponding column header; this is a good way to ensure a consistently formatted file is",
		"produced for easy consumption by other automated systems."
	})
	@Prop(P.probedValues)
	@BindNamedArgument("capture")
	public List<XFunction> getProbedValues() {
		return probedValues;
	}
	
	public void setProbedValues(final List<XFunction> funcs) {
		probedValues.clear();
		probedValues.addAll(funcs);
	}
}
