package uk.org.cse.nhm.language.definition.function.num;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.XElement;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;


@Doc("Contains a number to be used for sizing, and allows a maximum and minimum size to be specified")
@Bind("size")
public class XSizingFunction extends XElement {
	public static final class P {
		public static final String VALUE = "value";
		public static final String MAXIMUM_SIZE = "maximumSize";
		public static final String MINIMUM_SIZE = "minimumSize";
	}
	
	private XNumber value;
	private double maximumSize = Double.POSITIVE_INFINITY;
	private double minimumSize = 0;

	@Prop(P.VALUE)
	
	@BindPositionalArgument(0)
	@Doc("Defines the value of this quantity")
	@NotNull(message = "sizing function must always contain a value to calculate the size.")
	public XNumber getValue() {
		return value;
	}

	public void setValue(final XNumber value) {
		this.value = value;
	}

	@Prop(P.MAXIMUM_SIZE)
	
	@BindNamedArgument("max")
	@Doc("Defines the largest size which will be considered suitable")
	public double getMaximumSize() {
		return maximumSize;
	}

	public void setMaximumSize(final double maximumSize) {
		this.maximumSize = maximumSize;
	}

	@Prop(P.MINIMUM_SIZE)
	
	@BindNamedArgument("min")
	@Doc("Defines the smallest size which will be considered suitable")
	public double getMinimumSize() {
		return minimumSize;
	}

	public void setMinimumSize(final double minimumSize) {
		this.minimumSize = minimumSize;
	}
}
