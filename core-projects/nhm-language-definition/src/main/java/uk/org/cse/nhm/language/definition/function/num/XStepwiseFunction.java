package uk.org.cse.nhm.language.definition.function.num;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;


@Bind("function.steps")
@Doc("A function which rounds a value to the nearest from a list of constants")
@Category(CategoryType.ARITHMETIC)

public class XStepwiseFunction extends XNumber {
	public static final class P {
		public static final String mode = "mode";
		public static final String value = "value";
		public static final String steps = "steps";
	}
	
	
	public enum XRoundingType {
		Up, Down, Closest
	}
	
	private XRoundingType mode = XRoundingType.Up;
	private XNumber value;
	private List<Double> steps = new ArrayList<Double>();
	
	
@BindNamedArgument("round")
	@Doc("The direction in which to round the value; say the value is 6.5, and the nearest steps are 4 and 8, down will give 4, and up and closest will give 8. For nearest, ties are broken by rounding up.")
	public XRoundingType getMode() {
		return mode;
	}
	public void setMode(final XRoundingType mode) {
		this.mode = mode;
	}
	
	
	@BindNamedArgument
	@Doc("The value which will be rounded")
	public XNumber getValue() {
		return value;
	}
	public void setValue(final XNumber value) {
		this.value = value;
	}
	
	@Doc("The allowed values after rounding")
	
	@BindNamedArgument
	@Size(min=1, message="function.steps needs to have some steps defined")
	public List<Double> getSteps() {
		return steps;
	}
	public void setSteps(final List<Double> steps) {
		this.steps = steps;
	}
}
