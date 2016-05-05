package uk.org.cse.nhm.language.definition.action.measure.heating;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;


@Bind("measure.district-heating")
@Doc("Connects a house to a district heating system (or 'heat network').")
@Unsuitability("dwelling is off gas grid")
public class XDistrictHeatingMeasure extends XWetHeatingMeasure {
	public static final class P {
		public static final String cylinderVolume = "cylinderVolume";
		public static final String cylinderInsulationThickness = "cylinderInsulationThickness";
		public static final String efficiency = "efficiency";
	}
	
	private XNumber cylinderVolume = XNumberConstant.create(110);
	private double cylinderInsulationThickness = 50;
	private XNumber efficiency = XNumberConstant.create(0.8);
	
	
    @BindNamedArgument("cylinder-volume")
	@Prop(P.cylinderVolume)
	@Doc("The volume of the cylyinder which will be installed (liters).")
	public XNumber getCylinderVolume() {
		return cylinderVolume;
	}
	public void setCylinderVolume(final XNumber cylinderVolume) {
		this.cylinderVolume = cylinderVolume;
	}
	
	
    @BindNamedArgument("cylinder-insulation-thickness")
	@Prop(P.cylinderInsulationThickness)
	@Doc("The insulation thickness of the hot water cylinder which will be installed (mm).")
	public double getCylinderInsulationThickness() {
		return cylinderInsulationThickness;
	}
	public void setCylinderInsulationThickness(final double cylinderInsulationThickness) {
		this.cylinderInsulationThickness = cylinderInsulationThickness;
	}
	
	
    @BindNamedArgument
	@Prop(P.efficiency)
	@Doc("The efficiency with which the heat exchanger supplies heat into the house. This is not the efficiency for primary energy.")
	public XNumber getEfficiency() {
		return efficiency;
	}
	public void setEfficiency(final XNumber efficiency) {
		this.efficiency = efficiency;
	}
}
