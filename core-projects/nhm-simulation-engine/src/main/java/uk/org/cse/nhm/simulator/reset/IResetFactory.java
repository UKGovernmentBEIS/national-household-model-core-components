package uk.org.cse.nhm.simulator.reset;

import java.util.Set;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.hom.components.fabric.types.WallInsulationType;
import uk.org.cse.nhm.simulator.reset.doors.DoorTypeFunction;
import uk.org.cse.nhm.simulator.reset.doors.ResetDoorsAction;
import uk.org.cse.nhm.simulator.reset.glazing.GlazingFrameTypeFunction;
import uk.org.cse.nhm.simulator.reset.glazing.GlazingInsulationTypeFunction;
import uk.org.cse.nhm.simulator.reset.glazing.GlazingTypeFunction;
import uk.org.cse.nhm.simulator.reset.glazing.ResetGlazingsAction;
import uk.org.cse.nhm.simulator.reset.opex.ResetOpexAction;
import uk.org.cse.nhm.simulator.reset.opex.TechnologyFuelFunction;
import uk.org.cse.nhm.simulator.reset.opex.TechnologyTypeFunction;
import uk.org.cse.nhm.simulator.reset.storey.FloorIsNotExposedFunction;
import uk.org.cse.nhm.simulator.reset.storey.RdSapFloorUValueFunction;
import uk.org.cse.nhm.simulator.reset.storey.ResetFloorsAction;
import uk.org.cse.nhm.simulator.reset.storey.ResetRoofsAction;
import uk.org.cse.nhm.simulator.reset.walls.ResetWallsAction;
import uk.org.cse.nhm.simulator.reset.walls.WallConstructionFunction;
import uk.org.cse.nhm.simulator.reset.walls.WallInfiltrationFunction;
import uk.org.cse.nhm.simulator.reset.walls.WallInsulationFunction;
import uk.org.cse.nhm.simulator.reset.walls.WallUValueFunction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * Guice constructed automatic factory for creating things related to resetting bits of houses
 * to a controlled state, i.e. wall, door, glazing related functions and actions.
 */
public interface IResetFactory {
	/* Functions pertinent to walls */
	
	public ResetWallsAction
		createResetWallsAction(@Assisted("uvalues") final Optional<IComponentsFunction<Number>> uLookup,
							   @Assisted("infiltration") final Optional<IComponentsFunction<Number>> infiltration,
							   @Assisted("thickness") final Optional<IComponentsFunction<Number>> thickness);

	public WallConstructionFunction createWallConstructionFunction();
	public WallInfiltrationFunction createWallInfiltrationFunction();
	public WallInsulationFunction createWallInsulationThicknessFunction(final Set<WallInsulationType> matching);
	public WallUValueFunction createWallUValueFunction();

	/* Functions pertinent to glazing */
	
	public ResetGlazingsAction
		createResetGlazingsAction(@Assisted("frameFactor") final Optional<IComponentsFunction<Number>> frameFactor,
								  @Assisted("gainsTransmittance") final Optional<IComponentsFunction<Number>> gains,
								  @Assisted("lightTransmittance") final Optional<IComponentsFunction<Number>> light,
								  @Assisted("uValue") final Optional<IComponentsFunction<Number>> uValue);

	public GlazingFrameTypeFunction createGlazingFrameTypeFunction();
	public GlazingInsulationTypeFunction createGlazingInsulationTypeFunction();
	public GlazingTypeFunction createGlazingTypeFunction();

	public ResetDoorsAction createResetDoorsAction(
			@Assisted boolean rescale, 
			@Assisted("area") Optional<IComponentsFunction<Number>> area,
			@Assisted("uvalue") Optional<IComponentsFunction<Number>> uValue);

	public DoorTypeFunction createDoorTypeFunction();

	public ResetFloorsAction createResetFloorsAction(
			@Assisted("uValue") Optional<IComponentsFunction<Number>> uValue,
			@Assisted("infiltration") Optional<IComponentsFunction<Number>> infiltration);

	public RdSapFloorUValueFunction createComputeFloorUValueFunction(
			@Assisted("rsi") final double rsi, 
			@Assisted("rse") final double rse, 
			@Assisted("soilThermalConductivity") final double soilThermalConductivity,
			@Assisted("deckThermalResistance") final double deckThermalResistance,
			@Assisted("openingsPerMeterOfExposedPerimeter") final double openingsPerMeterOfExposedPerimeter,
			@Assisted("heightAboveGroundLevel") final double heightAboveGroundLevel,
			@Assisted("uValueOfWallsToUnderfloorSpace") final double uValueOfWallsToUnderfloorSpace,
			@Assisted("averageWindSpeedAt10m") final double averageWindSpeedAt10m, 
			@Assisted("windShieldingFactor") final double windShieldingFactor,
			@Assisted("floorInsulationConductivity") final double floorInsulationConductivity
			);

	public FloorIsNotExposedFunction createFloorIsGroundFloorFunction();

	public ResetRoofsAction createResetRoofsAction(			
			@Assisted("uValue") final Optional<IComponentsFunction<Number>> uValue
			);
	
	public ResetOpexAction createResetOpexAction(
			final IComponentsFunction<? extends Number> value
			);
	
	public TechnologyTypeFunction createTechTypeFunction();
	
	public TechnologyFuelFunction createTechFuelFunction();
}
