package uk.org.cse.nhm.language.definition.two.selectors;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.bool.XBoolean;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;

@Bind("filter")
@Doc({
	"Produces a set of houses in SOURCE which pass the logical TEST given in the first argument.",
	"You can use this to select all the houses which match particular criteria."
})
public class XFilteredSet extends XSetOfHouses implements IHouseContext {
	public static final class P {
		public static final String source = "source";
		public static final String test = "test";
	}
	private XSetOfHouses source = new XAllTheHouses();
	private XBoolean test;
	
	@Doc("This set of houses, the SOURCE, is the set of houses to consider including in the resulting filtered set. The output of the filter will be a subset of this set.")
	@BindPositionalArgument(1)
	public XSetOfHouses getSource() {
		return source;
	}
	public void setSource(final XSetOfHouses source) {
		this.source = source;
	}
	
	@Doc("This TEST will be used on each house in SOURCE to decide if it should be included in the result.")
	@BindPositionalArgument(0)
	@NotNull(message="filter must have a test")
	public XBoolean getTest() {
		return test;
	}
	public void setTest(final XBoolean test) {
		this.test = test;
	}
}
