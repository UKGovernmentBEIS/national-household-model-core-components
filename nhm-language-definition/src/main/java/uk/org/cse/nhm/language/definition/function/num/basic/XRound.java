package uk.org.cse.nhm.language.definition.function.num.basic;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;

@Bind("round")
@Doc("Round one value to the closest, greatest lesser or least greater multiple of another.")
public class XRound extends XNumber {
	public static final class P {
		public static final String value = "value";
		public static final String precision = "precision";
		public static final String direction = "direction";
		
	}
	private XNumber value;
	private XNumber precision = XNumberConstant.create(1);
	private XRoundDirection direction = XRoundDirection.Nearest;
	
	public enum XRoundDirection {
		Nearest, 
		Lower, 
		Upper
	}
	
	@Prop(P.value)
	@Doc("The value which will be rounded.")
	@BindPositionalArgument(0)
	@NotNull(message = "round should have a numeric argument which is the function it will round")
	public XNumber getValue() {
		return value;
	}
	public void setValue(XNumber value) {
		this.value = value;
	}
	
	@Prop(P.precision)
	@Doc("The precision to round to; the result will be whatever whole number multiple of this precision is nearest, above or below the value being rounded.")
	@BindPositionalArgument(1)
	public XNumber getPrecision() {
		return precision;
	}
	public void setPrecision(XNumber precision) {
		this.precision = precision;
	}
	
	@Prop(P.direction)
	@BindNamedArgument("to")
	public XRoundDirection getDirection() {
		return direction;
	}
	public void setDirection(XRoundDirection direction) {
		this.direction = direction;
	}
	
}
