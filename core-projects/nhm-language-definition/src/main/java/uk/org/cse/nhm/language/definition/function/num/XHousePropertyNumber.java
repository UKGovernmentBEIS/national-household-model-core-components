package uk.org.cse.nhm.language.definition.function.num;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.function.bool.house.XHousePropertyIs;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;


@Bind("house.static-number")
@Doc({
	"Access a static property which was imported as part of the stock, interpreting it as a number.",
	"If a value accessed through this is not a number, return 0 instead. and log a warning."
})
@SeeAlso(XHousePropertyIs.class)
public class XHousePropertyNumber extends XHouseNumber {
	public static final class P {
		public static final String name = "name";
	}
	
	private String name;

	@Override
	@BindPositionalArgument(0)
	@Prop(P.name)
	@NotNull(message = "get-static must include the name of the static property to access.")
	@Doc("The name of the property to access.")
	public String getName() {
		return name;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}
}
