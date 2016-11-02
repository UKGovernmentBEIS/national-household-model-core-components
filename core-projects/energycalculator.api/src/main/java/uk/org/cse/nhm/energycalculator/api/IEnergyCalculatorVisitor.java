package uk.org.cse.nhm.energycalculator.api;

import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.types.AreaType;
import uk.org.cse.nhm.energycalculator.api.types.FloorConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.GlazingType;
import uk.org.cse.nhm.energycalculator.api.types.OvershadingType;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType;

/**
 * A visitor interface for a house to accept; the energy calculator algorithm can use this to collect
 * all the inputs it needs from a house however that house may be structured.
 * 
 * Where a given part of the house implements more than one of these interfaces, it should be
 * passed to each applicable method, so a window will invoke visitTransparentElement and 
 * visitFabricElement.
 * 
 * @author hinton
 *
 */
public interface IEnergyCalculatorVisitor {
	/**
	 * Present a heating system to the energy calculator algorithm. Heating
	 * systems determine the background temperature in a house,
	 * typically using a responsiveness parameter to interpolate
	 * between an ideal and worst-case background temperature.
	 * 
	 * The actual energy to heat conversion will be done by something
	 * presented to {#visitEnergyTransducer(IEnergyTransducer)}.
	 */
	void visitHeatingSystem(IHeatingSystem system, double proportion);

	/**
	 * Return the proportion of heat provided by the heat system 
	 * (put in when we visit it).
	 * 
	 * Throws an exception if that heating system was not seen.
	 */
	double heatSystemProportion(IHeatingSystem system);
	
	/**
	 * Present an energy transducer to the energy calculator algorithm. The body
	 * of the calculation is done by these - see the interface for
	 * more details.
	 */
    public void visitEnergyTransducer(final IEnergyTransducer transducer);
	
	/**
	 * Present a ventilation system to the algorithm; ventilation
	 * systems are used to compute the air change rate through the
	 * house. At the moment, most houses won't contain one of these,
	 * as full ventilation systems are quite rare.
	 */ 
    public void visitVentilationSystem(final IVentilationSystem ventilation);
    
	/**
	 * @param glazingType
	 * @param insulationType
	 * @param visibleLightTransmittivity the visible light transmittivity
	 * @param solarGainTransmissivity the solar gain transmissivity

	 * @param area
	 * 
	 * @param frameType
	 * @param frameFactor the proportion of the window which is glazed
	 * 
	 * @param horizontalOrientation the angle from the dot product of this plane's normal and the north vector.
	 * @param verticalOrientation the angle between the normal from this plane and the vertical
	 * @param overshading the degree of overshading for this transparent element.
	 */
	public void visitTransparentElement(
			final GlazingType glazingType,
			final WindowInsulationType insulationType,
			final double visibleLightTransmittivity,
			final double solarGainTransmissivity,
			final double area,
			final FrameType frameType,
			final double frameFactor,
			final double horizontalOrientation,
			final double verticalOrientation,
			final OvershadingType overshading
			);
	/**
	 * Add the infiltration contribution from a wall of the given area and ACH
	 * 
	 * @param wallArea
	 * @param airChangeRate air change rate (proportion of house volume / hr)
	 */
	public void addWallInfiltration(final double wallArea, final WallConstructionType wallType, final double airChangeRate);
	
	/**
	 * Add the infiltration contribution from passive vents
	 * 
	 * @param the number of vents 
	 */
	public void addVentInfiltration(final int vents);
	
	/**
	 * Add the infiltration contribution from a single open flue.
	 */
	public void addFlueInfiltration();
	
	/**
	 * Add the infiltration contribution from a single chimney.
	 */
	public void addChimneyInfiltration(); 
	
	/**
	 * Add the infiltration contribution from intermittent fans (something which is forced)
	 * 
	 * @param Number of fans
	 */
	public void addFanInfiltration(final int fans);
	
	/**
	 * Add the infiltration contribution from a floor of the given type and ACH
	 * @param airChangeRate
	 */
	public void addGroundFloorInfiltration(final FloorConstructionType floorType);
	
	/**
	 * Add the heat loss, thermal mass, and external erea from a wall.
	 */
	public void visitWall(
			final WallConstructionType constructionType,
			final double externalOrExternalInsulationThickness,
			final boolean hasCavityInsulation,
			final double area,
			final double uValue,
			final Optional<ThermalMassLevel> thermalMassLevel
		);
	
	/**
	 * Visit a fabric element with the given heat loss, thermal mass, and external area.
	 * @param name TODO
	 * @param specificHeatLoss
	 * @param thermalMass
	 * @param externalArea
	 */
	public void visitFabricElement(final AreaType type, final double area, final double uValue, final Optional<ThermalMassLevel> thermalMassLevel);
	
	public double getTotalThermalMass();
}
