package uk.org.cse.nhm.simulation.measure.heatpumps;

import java.util.List;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.EmitterType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatPumpSourceType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.IHeatPump;
import uk.org.cse.nhm.hom.emf.technologies.IHybridHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType;
import uk.org.cse.nhm.hom.emf.util.Efficiency;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.language.builder.action.measure.wetheating.IWetHeatingMeasureFactory;
import uk.org.cse.nhm.simulation.measure.AbstractHeatingMeasure;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.measure.sizing.ISizingFunction;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IComponents;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public abstract class AbstractHeatPumpMeasure extends AbstractHeatingMeasure {
	final Set<HeatingSystemControlType> heatingSystemControlTypes = Sets.newHashSet(new HeatingSystemControlType[] { HeatingSystemControlType.PROGRAMMER, HeatingSystemControlType.ROOM_THERMOSTAT });
	
	final IComponentsFunction<Number> efficiency;
	final double cylinderInsulationThickness;
	final IComponentsFunction<Number> cylinderVolume;

	final HeatPumpSourceType heatPumpSourceType;

	protected final ITechnologyOperations operations;
	protected final IDimension<ITechnologyModel> technologies;
	protected final Optional<Hybrid> hybrid;

	private final IDimension<StructureModel> structure;

	static public class Hybrid {
		public final IComponentsFunction<Number> efficiency;
		private final List<Double> splits;
		private final FuelType fuel;
		public Hybrid(
				final IComponentsFunction<Number> efficiency, 
				final List<Double> splits,
				final FuelType fuel) {
			super();
			this.efficiency = efficiency;
			this.splits = splits;
			this.fuel = fuel;
		}
	}
	
	protected AbstractHeatPumpMeasure(
			final ITimeDimension time, 
			final IWetHeatingMeasureFactory factory,
			final TechnologyType technology,
			final ISizingFunction sizingFunction,
			final IComponentsFunction<Number> capitalCostFunction,
			final IComponentsFunction<Number> operationalCostFunction,
			final IComponentsFunction<Number> wetHeatingCostFunction,
			final IComponentsFunction<Number> efficiency, 
			final double cylinderInsulationThickness,
			final IComponentsFunction<Number> cylinderVolume, 
			final HeatPumpSourceType heatPumpSourceType,
			final ITechnologyOperations operations,
			final IDimension<ITechnologyModel> technologies,
			final IDimension<StructureModel> structure,
			final FuelType mainFuel,
			final Optional<Hybrid> hybrid
			) {
		super(	time, 
				technologies,
				operations, 
				factory,
				technology, 
				sizingFunction, 
				capitalCostFunction, operationalCostFunction, wetHeatingCostFunction);
		this.efficiency = efficiency;
		this.cylinderInsulationThickness = cylinderInsulationThickness;
		this.cylinderVolume = cylinderVolume;
		this.heatPumpSourceType = heatPumpSourceType;
		this.operations = operations;
		this.technologies = technologies;
		this.structure = structure;
		this.hybrid = hybrid;
	}
	
	private class Modifier implements IModifier<ITechnologyModel> {
		private final double opex;
        private final double efficiency;
        private final double cylinderVolume;
		private final IHybridHeater hh;

		public Modifier(final double opex, final double efficiency, final double cylinderVolume, final IHybridHeater hh) {
			super();
			this.opex = opex;
            this.efficiency = efficiency;
            this.cylinderVolume = cylinderVolume;
            this.hh = hh;
		}

		@Override
		public boolean modify(final ITechnologyModel newCase) {
			
			final ITechnologiesFactory factory = ITechnologiesFactory.eINSTANCE;

			final IHeatPump heating = factory.createHeatPump();
			heating.setAuxiliaryPresent(false);
			heating.setCoefficientOfPerformance(Efficiency.fromDouble(efficiency));
			heating.setSourceType(heatPumpSourceType);
			heating.setFuel(FuelType.ELECTRICITY);
			heating.setFlueType(FlueType.NOT_APPLICABLE);
			heating.setAnnualOperationalCost(opex);
			heating.setHybrid(hh);
			
			if (operations.hasCommunityWaterHeating(newCase)) {
				operations.installHeatSource(newCase, heating, true, false, EmitterType.RADIATORS, getHeatingSystemControlTypes(), 0, 0);
			} else {
				operations.installHeatSource(newCase, heating, true, true, EmitterType.RADIATORS, getHeatingSystemControlTypes(), cylinderInsulationThickness, cylinderVolume);
			}
			
			return true;
		}
	}

	@Override
	protected boolean doApply(final ISettableComponentsScope components, final ILets lets, final double size, final double capex, final double opex) throws NHMException {
		final IHybridHeater hh;
		if (hybrid.isPresent()) {
			hh = ITechnologiesFactory.eINSTANCE.createHybridHeater();
			hh.setEfficiency(Efficiency.fromDouble(hybrid.get().efficiency.compute(components, lets).doubleValue()));
			hh.setFuel(hybrid.get().fuel);
			hh.getFraction().addAll(hybrid.get().splits);
		} else {
			hh = null;
		}
		
		components.modify(technologies, new Modifier(opex,
                                                     efficiency.compute(components, lets).doubleValue(),
                                                     cylinderVolume.compute(components, lets).doubleValue(),
                                                     hh));
		return true;
	}
	
	@Override
	protected final boolean doIsSuitable(final IComponents components) {
		if (hybrid.isPresent() && hybrid.get().fuel == FuelType.MAINS_GAS) {
			if (!components.get(structure).isOnGasGrid()) return false;
		}
		
		return doDoIsSuitable(components);
	}

	protected abstract  boolean doDoIsSuitable(IComponents components);

	@Override
	protected Set<HeatingSystemControlType> getHeatingSystemControlTypes() {
		return heatingSystemControlTypes;
	}
}
