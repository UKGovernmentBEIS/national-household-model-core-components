package uk.org.cse.nhm.language.definition.function.bool.house;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.bool.XHouseBoolean;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;


@Bind("house.static-property-is")
@Doc({"An extra value from the stock import.",
	"This value varies by aacode, but is otherwise constant throughout the scenario.",
	"It is usually better to use a variable from inside the house object model instead if one exists."})
public class XHousePropertyIs extends XHouseBoolean {
	public static final class P {
		public static final String name = "name";
		public static final String equalTo = "equalTo";
		public static final String above = "above";
		public static final String below = "below";
	}
	
	private String name;
	private String equalTo = null;
	private Double above = null;
	private Double below = null;

	public void setName(final String name) {
		this.name = name;
	}

	
@BindNamedArgument
	@Doc({"The name of the property to look up.",
		"e.g. an English Housing Survey property could be named 'DWTYPENX'."})
	public String getName() {
		return name;
	}

	
@BindNamedArgument("equal-to")
	@Doc("If specified, this test will only pass if the property is equal to this value - for numeric properties, this will be compared as a number; for text properties, it will be compared as a string.")
	public String getEqualTo() {
		return equalTo;
	}

	public void setEqualTo(final String equalTo) {
		this.equalTo = equalTo;
	}

	
@BindNamedArgument("above")
	@Doc("If the house property being tested is numeric, this test will only pass if the numeric value is greater than this value (if specified).")
	public Double getAbove() {
		return above;
	}

	public void setAbove(final Double above) {
		this.above = above;
	}

	
@BindNamedArgument("below")
	@Doc("If the house property being tested is numeric, this test will only pass if the numeric value is less than this value (if specified).")
	public Double getBelow() {
		return below;
	}

	public void setBelow(final Double below) {
		this.below = below;
	}
}
