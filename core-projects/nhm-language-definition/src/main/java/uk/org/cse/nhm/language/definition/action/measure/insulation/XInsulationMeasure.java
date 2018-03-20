package uk.org.cse.nhm.language.definition.action.measure.insulation;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.ProducesTags;
import uk.org.cse.nhm.language.definition.ProducesTags.Tag;
import uk.org.cse.nhm.language.definition.action.XMeasure;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;
import uk.org.cse.nhm.language.definition.money.TransactionTags;

@ProducesTags(
		@Tag(value = TransactionTags.Internal.capex, 
			detail="When the insulation is installed, it will produce a transaction with this tag for the cost of purchase.")
		)
public abstract class XInsulationMeasure extends XMeasure {
	public static final class P {
		public static final String thickness = "thickness";		
		public static final String capex = "capex";
		public static final String resistanceFunction = "resistanceFunction";
		public static final String uValueFunction = "uValueFunction";
	}
	
    private Double thickness = null;
	private XNumber capex;
	private XNumber resistanceFunction = new XNumberConstant();
	private XNumber uvalueFunction;
	
	@BindNamedArgument
	@Prop(P.thickness)
	@Doc({"The thickness (mm) of the installed insulation. The thickness of insulation on a wall, roof, etc. is what the model uses to determine whether there is any present, so this must be positive.",
		"If the resistance: argument is used to specify the thermal property of the insulation material, then the thickness is added to any existing insulation, if topping up insulation is allowed.",
		"If the u-value: argument is used to specify the thermal property, then the thickness associated with this kind of insulation is just set to the given value."
	})
	@NotNull(message = "insulation measure must have a defined 'thickness' of insulation to be installed.")
    public Double getThickness() {
		return thickness;
	}
    public void setThickness(final Double thickness) {
		this.thickness = thickness;
	}
	
	
	@BindNamedArgument
	@Prop(P.capex)
	@Doc("The capital cost function for the installed insulation. When the insulation is installed, this will be evaluated to work out the cost. During the calculation, the (size.m2) element will evaluate to the square meterage of insulation that was installed.")
	public XNumber getCapex() {
		return capex;
	}
	public void setCapex(final XNumber capex) {
		this.capex = capex;
	}
	
	
	@BindNamedArgument("resistance")
	@Prop(P.resistanceFunction)
	@Doc({"A function used to compute the r-value for this insulation, per mm of thickness. This is overridden by the u-value argument, if that is provided.",
		"The r-value is used to determine the u-value of the wall after insulation is applied, using the standard formula u' = 1/(r + 1/u)), so the final u-value in this case is",
		"1 / ((thickness * result of this function) + 1 / (original u-value))"
	})
	public XNumber getResistanceFunction() {
		return resistanceFunction;
	}
	public void setResistanceFunction(final XNumber resistanceFunction) {
		this.resistanceFunction = resistanceFunction;
	}
	
	
	@BindNamedArgument("u-value")
	@Prop(P.uValueFunction)
	@Doc({
		"A function used to compute the u-value for the wall after insulation is applied. This overrides the resistance attribute and resistance element.",
		"Note that directly setting the u-value of the wall is fraught with difficulty. If you have multiple policies applying",
		"several kinds of insulation in sequence, this insulation measure <emphasis>reset</emphasis> the u-value to a new value and so you must define a <emphasis>comprehensive</emphasis>",
		"u-value function here which can accommodate all possible prior insulation states applied to the house."
	})
	public XNumber getUvalueFunction() {
		return uvalueFunction;
	}
	public void setUvalueFunction(final XNumber uvalueFunction) {
		this.uvalueFunction = uvalueFunction;
	}
}
