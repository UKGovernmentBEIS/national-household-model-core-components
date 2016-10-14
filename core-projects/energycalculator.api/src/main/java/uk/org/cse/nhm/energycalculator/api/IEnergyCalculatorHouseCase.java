package uk.org.cse.nhm.energycalculator.api;

import uk.org.cse.nhm.energycalculator.api.types.SiteExposureType;

/**
 * An interface which describes a house in sufficient detail for a energy calculator to operate on it.
 * @author hinton
 *
 */
public interface IEnergyCalculatorHouseCase {
	/**
	 * Accept the given {@link IEnergyCalculatorVisitor}, presenting it with all of the things in this house that it is interested in.
	 * 
	 * See the visitor interface itself for more detail on what these are.
	 * @param constants TODO
	 * @param visitor
	 */
	public void accept(IConstants constants, final IEnergyCalculatorParameters parameters, final IEnergyCalculatorVisitor visitor);

	/**
	 * @param zone
	 * @return the floor area in M2 of the given zone
	 */
	public double getFloorArea();

	/**
	 * The proportion of the floor area in the living area (zone 1)
	 * @return
	 */
	public double getLivingAreaProportionOfFloorArea();
	
	/**
	 * @return the specific heat loss between zones 1 and 2, in watts/m2
	 */
	public double getInterzoneSpecificHeatLoss();

	/**
	 */
	public double getHouseVolume();

	/**
	 * @return the number of storeys in this house (at least one)
	 */
	public int getNumberOfStoreys();

	/**
	 * A draught lobby is an unheated enclosed space outside the front door. For example, some houses have a secondary door enclosing an
	 * unheated porch. Similarly most flats are connected to enclosed corridors.
	 * 
	 * @return whether the house has a draught lobby
	 */
	public boolean hasDraughtLobby();

	/**
	 * @return number of sheltered sides for this property
	 */
	public int getNumberOfShelteredSides();

	/**
	 * @return the proportion (0-1) of zone two which is heated.
	 */
	public double getZoneTwoHeatedProportion();

	/**
	 * @return The year when the house was constructed (e.g. 1950)
	 */
	public int getBuildYear();

	/**
	 * @return the proportion of windows & doors that have been draught stripped
	 */
	public double getDraughtStrippedProportion();

	/**
	 * @since 6.4.1
	 * @return
	 */
	public SiteExposureType getSiteExposure();
}
