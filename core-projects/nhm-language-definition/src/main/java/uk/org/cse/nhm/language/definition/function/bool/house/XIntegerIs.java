package uk.org.cse.nhm.language.definition.function.bool.house;

import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.bool.XHouseBoolean;

public abstract class XIntegerIs extends XHouseBoolean {
	public static final class P {
		public static final String ABOVE = "above";
		public static final String EXACTLY = "exactly";
		public static final String BELOW = "below";
	}
	
	private Integer above = null;
	private Integer exactly = null;
	private Integer below = null;
	
	
@BindNamedArgument
	@Doc("The lower bound for the numeric test - if this is specified, the value must be strictly greater than this bound.")
	public Integer getAbove() {
		return above;
	}
	public void setAbove(final Integer above) {
		this.above = above;
	}
	
	
@BindNamedArgument
	@Doc("If this is specified, the tested value must be exactly equal to this value.")
	public Integer getExactly() {
		return exactly;
	}
	public void setExactly(final Integer exactly) {
		this.exactly = exactly;
	}
	
	
@BindNamedArgument
	@Doc("The upper bound for the numeric test - if this is specified, the value must be strictly less than this bound.")
	public Integer getBelow() {
		return below;
	}
	public void setBelow(final Integer below) {
		this.below = below;
	}
}
