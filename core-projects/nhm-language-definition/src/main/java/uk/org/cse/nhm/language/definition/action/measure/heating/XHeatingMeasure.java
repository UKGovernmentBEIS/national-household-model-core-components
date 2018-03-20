package uk.org.cse.nhm.language.definition.action.measure.heating;

import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.ProducesTags;
import uk.org.cse.nhm.language.definition.ProducesTags.Tag;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.action.XMeasure;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XSizingFunction;
import uk.org.cse.nhm.language.definition.money.TransactionTags;

@Unsuitability("the measure could not be sized")
@ProducesTags(
		{@Tag(value = TransactionTags.Internal.capex, 
			detail="When the measure is installed, it will produce a transaction with this tag for the cost of purchase."),
		@Tag( value = TransactionTags.Internal.opex,
		detail = "Each year, for each house with this measure in, the house will incur an operational cost with this tag")
		})
public abstract class XHeatingMeasure extends XMeasure {
	public static final class P {
		public static final String SIZING = "sizing";
		public static final String OPEX = "opex";
		public static final String CAPEX = "capex";
	}
	
	private XSizingFunction sizing;
	private XNumber capex;
	private XNumber opex;
	
	@Prop(P.SIZING)
	
	@BindNamedArgument("size")
	@Doc("Contains the measure's sizing function")
	public XSizingFunction getSizing() {
		return sizing;
	}
	public void setSizing(final XSizingFunction sizing) {
		this.sizing = sizing;
	}
	
	@Prop(P.CAPEX)
	@BindNamedArgument
	@Doc("Contains the measure's capex function")
	public XNumber getCapex() {
		return capex;
	}
	public void setCapex(final XNumber capex) {
		this.capex = capex;
	}
	
	@Prop(P.OPEX)
	@BindNamedArgument
	@Doc("Contains the measure's opex function")
	public XNumber getOpex() {
		return opex;
	}
	public void setOpex(final XNumber opex) {
		this.opex = opex;
	}
}
