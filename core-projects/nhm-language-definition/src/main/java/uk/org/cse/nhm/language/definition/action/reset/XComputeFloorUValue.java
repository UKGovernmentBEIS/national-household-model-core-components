package uk.org.cse.nhm.language.definition.action.reset;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.function.num.XHouseNumber;
import uk.org.cse.nhm.language.validate.contents.RequireParent;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

@RequireParent(XResetFloors.class)
@Bind("floor.sap-ground-floor-u-value")
@Doc("Computes a floor's u-value according to RDSAP rules")
@SeeAlso(XFloorIsGroundFloor.class)
public class XComputeFloorUValue extends XHouseNumber {
	private double rsi = 0.17; // square meter kelvin per watt
	private double rse = 0.04; // square meter kelvin per watt
	private double soilThermalConductivity = 1.5;// watts / meter kelvin
	private double deckThermalResistance = 0.2; // square meter kelvin per
												// watt
	private double openingsPerMeterOfExposedPerimeter = 0.003; // meters
	private double heightAboveGroundLevel = 0.3; // meters
	private double uValueOfWallsToUnderfloorSpace = 1.5; // wats per meter
															// square kelvin
	private double averageWindSpeedAt10m = 5;// meters per second
	private double windShieldingFactor = 0.05; // dimensionless
	private double floorInsulationConductivity = 0.035; // watts per meter
	@BindNamedArgument
	public double getRsi() {
		return rsi;
	}
	public void setRsi(final double rsi) {
		this.rsi = rsi;
	}
	@BindNamedArgument
	public double getRse() {
		return rse;
	}
	public void setRse(final double rse) {
		this.rse = rse;
	}
	@BindNamedArgument("soil-thermal-conductivity")
	public double getSoilThermalConductivity() {
		return soilThermalConductivity;
	}
	public void setSoilThermalConductivity(final double soilThermalConductivity) {
		this.soilThermalConductivity = soilThermalConductivity;
	}
	@BindNamedArgument("deck-thermal-resistance")
	public double getDeckThermalResistance() {
		return deckThermalResistance;
	}
	public void setDeckThermalResistance(final double deckThermalResistance) {
		this.deckThermalResistance = deckThermalResistance;
	}
	@BindNamedArgument("openings-per-meter")
	public double getOpeningsPerMeterOfExposedPerimeter() {
		return openingsPerMeterOfExposedPerimeter;
	}
	public void setOpeningsPerMeterOfExposedPerimeter(
			final double openingsPerMeterOfExposedPerimeter) {
		this.openingsPerMeterOfExposedPerimeter = openingsPerMeterOfExposedPerimeter;
	}
	@BindNamedArgument("height-above-ground")
	public double getHeightAboveGroundLevel() {
		return heightAboveGroundLevel;
	}
	public void setHeightAboveGroundLevel(final double heightAboveGroundLevel) {
		this.heightAboveGroundLevel = heightAboveGroundLevel;
	}
	@BindNamedArgument("u-value-of-walls-to-underfloor-space")
	public double getuValueOfWallsToUnderfloorSpace() {
		return uValueOfWallsToUnderfloorSpace;
	}
	public void setuValueOfWallsToUnderfloorSpace(
			final double uValueOfWallsToUnderfloorSpace) {
		this.uValueOfWallsToUnderfloorSpace = uValueOfWallsToUnderfloorSpace;
	}
	@BindNamedArgument("average-windspeed-at-10m")
	public double getAverageWindSpeedAt10m() {
		return averageWindSpeedAt10m;
	}
	public void setAverageWindSpeedAt10m(final double averageWindSpeedAt10m) {
		this.averageWindSpeedAt10m = averageWindSpeedAt10m;
	}
	@BindNamedArgument("wind-shielding-factor")
	public double getWindShieldingFactor() {
		return windShieldingFactor;
	}
	public void setWindShieldingFactor(final double windShieldingFactor) {
		this.windShieldingFactor = windShieldingFactor;
	}
	@BindNamedArgument("floor-insulation-conductivity")
	public double getFloorInsulationConductivity() {
		return floorInsulationConductivity;
	}
	public void setFloorInsulationConductivity(final double floorInsulationConductivity) {
		this.floorInsulationConductivity = floorInsulationConductivity;
	}
}
