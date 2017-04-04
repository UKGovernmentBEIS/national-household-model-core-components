package uk.org.cse.nhm.language.definition.function.num;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;


@Bind("house.u-value")
@Doc({"Yields the average u-value of all the external surfaces of a particular type in the house.",
	"This average is weighted by the areas of each surface, so if multiplied by the external area of the given type",
	"you will get the fabric heat loss associated with those elements.",
	"The value reported will be appropriate to the energy calculator mode currently in effect on the dwelling."
	})
public class XAverageUValue extends XHouseNumber {
	public static final class P {
		public static final String of = "of";
	}
	
	
	
	public enum XSurfaceType {
		AllSurfaces,
		Walls,
		Roofs,
		Floors,
		Windows,
		Doors
	}
	
	private XSurfaceType of = XSurfaceType.AllSurfaces;

	
@BindNamedArgument
	@Prop(P.of)
	@Doc("The type of surfaces to include in the calculation")
	public XSurfaceType getOf() {
		return of;
	}

	public void setOf(final XSurfaceType type) {
		this.of = type;
	}
}
