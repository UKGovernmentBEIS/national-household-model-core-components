package uk.org.cse.nhm.simulation.measure.heatpumps;

import javax.inject.Inject;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatPumpSourceType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.language.builder.action.measure.wetheating.IWetHeatingMeasureFactory;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.measure.sizing.ISizingFunction;
import uk.org.cse.nhm.simulator.state.IComponents;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class AirSourceHeatPumpMeasure extends AbstractHeatPumpMeasure {
	@Inject
	public AirSourceHeatPumpMeasure(
			final ITimeDimension time,
			final IWetHeatingMeasureFactory factory,
			final IDimension<ITechnologyModel> technologies,
			final IDimension<StructureModel> structure,
			final ITechnologyOperations operations,
			@Assisted final ISizingFunction sizingFunction,
			@Assisted("capex") final IComponentsFunction<Number> capitalCostFunction,
			@Assisted("opex") final IComponentsFunction<Number> operationalCostFunction,
			@Assisted("wetHeating") final IComponentsFunction<Number> wetHeatingCostFunction,
			@Assisted("efficiency") final IComponentsFunction<Number> efficiency, 
			@Assisted("insulation") final double cylinderInsulationThickness,
			@Assisted("volume") final IComponentsFunction<Number> cylinderVolume,
			@Assisted("fuel") final FuelType mainFuel,
			@Assisted final Optional<Hybrid> hybrid
			) {
		super(	time, 
				factory,
				TechnologyType.airSourceHeatPump(), 
				sizingFunction,
				capitalCostFunction, 
				operationalCostFunction,
				wetHeatingCostFunction,
				efficiency,
				cylinderInsulationThickness, 
				cylinderVolume, 
				HeatPumpSourceType.AIR,
				operations, 
				technologies,
				structure,
				mainFuel,
				hybrid);
	}

	@Override
	protected boolean doDoIsSuitable(final IComponents components) {
		// not community heating is the only requirement
		final ITechnologyModel technologyModel = components.get(technologies);
		if (operations.hasCommunitySpaceHeating(technologyModel))
			return false;
		return true;
	}
	
	@Override
	public boolean isAlwaysSuitable() {
		return false;
	}
}
