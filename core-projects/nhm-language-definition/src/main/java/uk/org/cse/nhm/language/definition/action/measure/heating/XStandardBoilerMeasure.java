package uk.org.cse.nhm.language.definition.action.measure.heating;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;


@Doc("Installs a standard condensing boiler")
@Unsuitability({
	"fuel is biomass AND built form of dwelling is flat",
	"fuel is oil AND dwelling has existing community space heating",
	"fuel is oil AND dwelling has existing community hot water",
	"fuel is mains gas AND dwelling has existing community space heating",
	"fuel is mains gas, bottled gas or bulk LPG AND dwelling has existing community hot water",
	"fuel is mains gas, bottled gas or bulk LPG AND dwelling is off gas grid",
	"the internal floor area of the dwelling is smaller than the minimum floor area",
	"both the front and back plots of the dwelling are smaller than the minimum external space"
})
@Bind("measure.standard-boiler")
public class XStandardBoilerMeasure extends XBoilerMeasure {
	public static final class P {
		public static final String requiredFloorArea = "requiredFloorArea";
		public static final String requiredExteriorArea = "requiredExteriorArea";
		public static final String cylinderVolume = "cylinderVolume";
		public static final String cylinderInsulationThickness = "cylinderInsulationThickness";
		
	}
	
	private double requiredFloorArea = 0;
	private double requiredExteriorArea = 0;
	private XNumber cylinderVolume = XNumberConstant.create(110);
	private double cylinderInsulationThickness = 50;
	
	@BindNamedArgument("required-floor-area")
	@Prop(P.requiredFloorArea)
	@Doc("The boiler will only be suitable for houses with at least this floor area")
	public double getRequiredFloorArea() {
		return requiredFloorArea;
	}
    
	public void setRequiredFloorArea(final double requiredFloorArea) {
		this.requiredFloorArea = requiredFloorArea;
	}
	
	
	@BindNamedArgument("required-exterior-area")
	@Prop(P.requiredExteriorArea)
	@Doc("The boiler will only be suitable for houses with at least this exterior plot area")
	public double getRequiredExteriorArea() {
		return requiredExteriorArea;
	}
	public void setRequiredExteriorArea(final double requiredExteriorArea) {
		this.requiredExteriorArea = requiredExteriorArea;
	}
	
	
	@BindNamedArgument("cylinder-volume")
	@Prop(P.cylinderVolume)
	@Doc("The boiler will be installed along with a new cylinder of this volume (liters)")
	public XNumber getCylinderVolume() {
		return cylinderVolume;
	}
	public void setCylinderVolume(final XNumber cylinderVolume) {
		this.cylinderVolume = cylinderVolume;
	}
	
	@BindNamedArgument("cylinder-insulation-thickness")
	
	@Doc("The installed cylinder will have this insulation thickness (mm, factory jacket)")
	@Prop(P.cylinderInsulationThickness)
	public double getCylinderInsulationThickness() {
		return cylinderInsulationThickness;
	}
	public void setCylinderInsulationThickness(final double cylinderInsulationThickness) {
		this.cylinderInsulationThickness = cylinderInsulationThickness;
	}
}
