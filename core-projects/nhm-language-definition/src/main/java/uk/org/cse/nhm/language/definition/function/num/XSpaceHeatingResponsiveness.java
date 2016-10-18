package uk.org.cse.nhm.language.definition.function.num;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.action.scaling.heating.XHeatingResponsivenessScaling;
import uk.org.cse.nhm.language.definition.action.scaling.heating.XSpaceHeatingSystem;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

@Bind("house.heating-responsiveness")
@Doc({
	"Returns the responsiveness of a dwelling's space-heating.",
	"If the system is absent, 0 will be returned.",
	"Responsiveness is a quantity defined by SAP which determines how quickly a heating system adapts to changing temperatures. It ranges between 0 and 1.",
})
@SeeAlso(XHeatingResponsivenessScaling.class)
public class XSpaceHeatingResponsiveness extends XHouseNumber {
	public static class P {
		public static final String of = "of";
	}
	
	private XSpaceHeatingSystem of;

	@NotNull(message = "house.heating-responsiveness must include an 'of' attribute.")
	@Prop(P.of)
	@Doc("Determines which space heating system to get the efficiency for.")
	@BindNamedArgument
	public XSpaceHeatingSystem getOf() {
		return of;
	}

	public void setOf(final XSpaceHeatingSystem of) {
		this.of = of;
	}
}
