package uk.org.cse.nhm.simulation.measure.boilers;

import java.util.Set;

import javax.inject.Inject;

import com.google.common.collect.Sets;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.EmitterType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
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

public class DistrictHeatingMeasure extends AbstractHeatingMeasure {
	final double cylinderInsulation;
    final IComponentsFunction<Number> cylinderVolume;
	final IComponentsFunction<Number> efficiency;

	final IDimension<ITechnologyModel> technologies;
	final IDimension<StructureModel> structure;
	
	final Set<HeatingSystemControlType> heatingSystemControlTypes = 
			Sets.newHashSet(new HeatingSystemControlType[] { 
					HeatingSystemControlType.PROGRAMMER,
			HeatingSystemControlType.THERMOSTATIC_RADIATOR_VALVE });
	private final ITechnologyOperations operations;
	private final boolean chargingUsageBased;
	
	@Inject
	public DistrictHeatingMeasure(
			final ITimeDimension time,
			final IWetHeatingMeasureFactory factory,
			
			final IDimension<ITechnologyModel> technologies,
			final IDimension<StructureModel> structure,

			final ITechnologyOperations operations,
			
			@Assisted() final ISizingFunction sizingFunction,
			@Assisted("capex") final IComponentsFunction<Number> capitalCostFunction,
			@Assisted("opex") final IComponentsFunction<Number> operationalCostFunction,
			@Assisted("wetHeating") final IComponentsFunction<Number> wetHeatingCostFunction,
			@Assisted("volume") final IComponentsFunction<Number> cylinderVolume,
			@Assisted("insulation") final double cylinderInsulation, 
			@Assisted("efficiency") final IComponentsFunction<Number> efficiency,
			@Assisted("chargingUsageBased") final boolean chargingUsageBased) {
		super(	time, 
				technologies,
				operations, 
				factory,
				TechnologyType.districtHeating(), 
				sizingFunction, 
				capitalCostFunction, operationalCostFunction, wetHeatingCostFunction);
		this.operations = operations;
		this.cylinderInsulation = cylinderInsulation;
		this.cylinderVolume = cylinderVolume;
		this.efficiency = efficiency;
		this.technologies = technologies;
		this.structure = structure;
		this.chargingUsageBased = chargingUsageBased;
	}

	private class Modifier implements IModifier<ITechnologyModel> {
		private final double opex;
        private final double efficiency;
        private final double cylinderVolume;
		
		public Modifier(final double opex,
                        final double efficiency, final double cylinderVolume) {
			this.opex = opex;
            this.efficiency = efficiency;
            this.cylinderVolume = cylinderVolume;
		}

		@Override
		public boolean modify(final ITechnologyModel newCase) {
			final ITechnologiesFactory factory = ITechnologiesFactory.eINSTANCE;

			final ICommunityHeatSource heating = factory.createCommunityHeatSource();
			heating.setFuel(FuelType.MAINS_GAS);
			heating.setHeatEfficiency(Efficiency.fromDouble(efficiency));
			heating.setAnnualOperationalCost(opex);
			heating.setChargingUsageBased(chargingUsageBased);

			operations.installHeatSource(newCase,
                                         heating,
                                         true,
                                         true,
                                         EmitterType.RADIATORS,
                                         getHeatingSystemControlTypes(),
                                         cylinderInsulation,
                                         cylinderVolume);
			
			return true;
		}
	}
	
	@Override
	protected boolean doApply(final ISettableComponentsScope components, final ILets lets, final double size, final double cap, final double op) throws NHMException {
        final double _eff = efficiency.compute(components, lets).doubleValue();
        final double _vol = cylinderVolume.compute(components, lets).doubleValue();
		components.modify(technologies, new Modifier(op, _eff, _vol));
		return true;
	}

	@Override
	protected boolean doIsSuitable(final IComponents components) {
		final StructureModel structureModel = components.get(structure);
		return structureModel.isOnGasGrid();
	}

	@Override
	protected Set<HeatingSystemControlType> getHeatingSystemControlTypes() {
		return heatingSystemControlTypes;
	}
}
