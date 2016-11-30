package uk.org.cse.nhm.energycalculator.api;

/**
 * Describes all the heat loss information produced during an energy calculation
 * 
 * @author hinton
 *
 */
public interface ISpecificHeatLosses {
	/**
	 * The specific heat loss from the house to the outside, in watts/degree
	 * @return
	 */
	public double getSpecificHeatLoss();
	
	/**
	 * @return the specific heat loss for the flow of heat between zones
	 */
	public double getInterzoneHeatLoss();
	
	/**
	 * @return the heat loss parameter, which is heat loss normalized by floor area
	 */
	public double getHeatLossParameter();
	
	/**
	 * @return the total estimated thermal mass for the house
	 */
	public double getThermalMass();
	
	/**
	 * @return the TMP, which is thermal mass normalized by floor area
	 */
	public double getThermalMassParameter();

	/**
	 * @return the heat loss attributable to ventilation
	 */
	public double getVentilationLoss();

	/**
	 * @return the heat loss attributable to thermal bridging.
	 */
    public double getThermalBridgeEffect();

    public double getFabricLoss();

    /**
     * @return the air change rate, including the effect of ventilation systems
     */
    public double getAirChangeRate();
}
