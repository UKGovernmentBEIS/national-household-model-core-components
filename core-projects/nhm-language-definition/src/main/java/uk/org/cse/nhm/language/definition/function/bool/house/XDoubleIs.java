package uk.org.cse.nhm.language.definition.function.bool.house;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.bool.XBoolean;

import com.larkery.jasb.bind.BindNamedArgument;

public abstract class XDoubleIs extends XBoolean {
	public static final class P {
		public static final String ABOVE = "above";
		public static final String EXACTLY = "exactly";
		public static final String BELOW = "below";
	}
	
	private Double above = null;
	private Double exactly = null;
	private Double below = null;
	
	
@BindNamedArgument
	@Doc("The lower bound for the numeric test - if this is specified, the value must be strictly greater than this bound.")
	public Double getAbove() {
		return above;
	}
	public void setAbove(final Double above) {
		this.above = above;
	}
	
	
@BindNamedArgument
	@Doc("If this is specified, the tested value must be exactly equal to this value.")
	public Double getExactly() {
		return exactly;
	}
	public void setExactly(final Double exactly) {
		this.exactly = exactly;
	}
	
	
@BindNamedArgument
	@Doc("The upper bound for the numeric test - if this is specified, the value must be strictly less than this bound.")
	public Double getBelow() {
		return below;
	}
	public void setBelow(final Double below) {
		this.below = below;
	}
}
