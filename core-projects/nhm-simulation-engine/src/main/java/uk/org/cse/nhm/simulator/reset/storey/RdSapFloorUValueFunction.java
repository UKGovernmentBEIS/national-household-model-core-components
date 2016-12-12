package uk.org.cse.nhm.simulator.reset.storey;

import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.energycalculator.api.types.EnergyCalculatorType;
import uk.org.cse.nhm.energycalculator.impl.GroundFloorUValues;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.behaviour.IHeatingBehaviour;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * Computes a floor u-value according to RDSAP rules.
 * @author hinton
 *
 */
public class RdSapFloorUValueFunction extends AbstractNamed implements IComponentsFunction<Double> {
	private final IDimension<StructureModel> structureDimension;
	private final IDimension<IHeatingBehaviour> behaviourDimension;
	private final GroundFloorUValues uValues;



	@AssistedInject
	RdSapFloorUValueFunction(
			final IDimension<StructureModel> structureDimension,
			final IDimension<IHeatingBehaviour> behaviourDimension,
			@Assisted("rsi") final double rsi,
			@Assisted("rse") final double rse,
			@Assisted("soilThermalConductivity") final double soilThermalConductivity,
			@Assisted("deckThermalResistance") final double deckThermalResistance,
			@Assisted("openingsPerMeterOfExposedPerimeter") final double openingsPerMeterOfExposedPerimeter,
			@Assisted("heightAboveGroundLevel") final double heightAboveGroundLevel,
			@Assisted("uValueOfWallsToUnderfloorSpace") final double uValueOfWallsToUnderfloorSpace,
			@Assisted("averageWindSpeedAt10m") final double averageWindSpeedAt10m,
			@Assisted("windShieldingFactor") final double windShieldingFactor,
			@Assisted("floorInsulationConductivity") final double floorInsulationConductivity) {
		this.structureDimension = structureDimension;
		this.behaviourDimension = behaviourDimension;

		this.uValues = new GroundFloorUValues()
				.setRsi(rsi)
				.setRse(rse)
				.setSoilThermalConductivity(soilThermalConductivity)
				.setDeckThermalResistance(deckThermalResistance)
				.setOpeningsPerMeterOfExposedPerimeter(openingsPerMeterOfExposedPerimeter)
				.setHeightAboveGroundLevel(heightAboveGroundLevel)
				.setuValueOfWallsToUnderfloorSpace(uValueOfWallsToUnderfloorSpace)
				.setAverageWindSpeedAt10m(averageWindSpeedAt10m)
				.setWindShieldingFactor(windShieldingFactor)
				.setFloorInsulationConductivity(floorInsulationConductivity);
	}

	@Override
	public Double compute(final IComponentsScope scope, final ILets lets) {
		final StructureModel structure = scope.get(structureDimension);
		final Optional<Storey> storey = lets.get(ResetFloorsAction.STOREY_SCOPE_KEY, Storey.class);
		final Optional<Double> areaBelow = lets.get(ResetFloorsAction.STOREY_GROUND_AREA_KEY, Double.class);

		final EnergyCalculatorType calculatorType = scope.get(behaviourDimension).getEnergyCalculatorType();

		if (storey.isPresent() && areaBelow.isPresent()) {
			return compute(structure, storey.get(), areaBelow.get(), calculatorType);
		} else {
			// error
			return 0d;
		}
	}

	private double compute(final StructureModel structure, final Storey storey,
			final double areaBelow, final EnergyCalculatorType calculatorType) {

		return uValues.getU(
				storey.getAverageWallThicknessWithInsulation(),
				Math.max(0, storey.getArea() - areaBelow),
				storey.getExposedPerimeter(),
				structure.getGroundFloorConstructionType(),
				structure.getFloorInsulationThickness()
			);
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return ImmutableSet.<IDimension<?>>of(structureDimension);
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.emptySet();
	}

}
