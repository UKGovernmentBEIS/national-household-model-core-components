package uk.org.cse.nhm.language.definition.action.measure.lighting;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.action.XMeasure;
import uk.org.cse.nhm.language.definition.enums.XLightType;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;

@Bind("measure.replace-lighting")
@Doc({
	"Replace lighting in a dwelling with another type of lighting."
})
@Unsuitability("The house has no lights of the type that are to be replaced.")
@SeeAlso(XLightingProportionsMeasure.class)
public class XLightingMeasure extends XMeasure {
	public static final class P {
		public static final String from = "from";
		public static final String to = "to";
		public static final String capex = "capex";
	}
	
	XNumber capex = XNumberConstant.create(0);

	@BindNamedArgument
	@Prop(P.capex)
	@Doc({
		"The capital cost of installing the low energy lighting.", 
		"This may be a function of the quantity of lights installed, which is measured as the part of the floor area serviced by those lights (size.m2)."
	})
	public XNumber getCapex() {
		return capex;
	}

	public void setCapex(final XNumber capex) {
		this.capex = capex;
	}
	
	private List<XLightType> from = incandescent();
	private XLightType to = XLightType.CFL;

	@Prop(P.from)
	@Doc("All lights in this list will be replaced with the type given by the with: argument.")
	@BindNamedArgument("replace")
	@Size(min = 1, message = "At least one type of light has to be specified to replace")
	public List<XLightType> getFrom() {
		return from;
	}

	private List<XLightType> incandescent() {
		final List<XLightType> out = new ArrayList<>();
		out.add(XLightType.Incandescent);
		return out;
	}

	public void setFrom(List<XLightType> from) {
		this.from = from;
	}

	@Prop(P.to)
	@Doc("The type of light to replace the existing lights with.")
	@BindNamedArgument("with")
	public XLightType getTo() {
		return to;
	}

	public void setTo(XLightType to) {
		this.to = to;
	}
}
