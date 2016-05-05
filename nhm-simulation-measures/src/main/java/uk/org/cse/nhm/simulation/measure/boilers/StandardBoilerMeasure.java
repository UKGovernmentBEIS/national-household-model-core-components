package uk.org.cse.nhm.simulation.measure.boilers;

import java.util.Set;

import javax.inject.Inject;

import com.google.common.collect.Sets;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.EmitterType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersFactory;
import uk.org.cse.nhm.hom.emf.util.Efficiency;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.language.builder.action.measure.wetheating.IWetHeatingMeasureFactory;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.measure.sizing.ISizingFunction;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IComponents;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;

/**
 * Measure which installs a standard boiler
 * 
 * @author hinton
 *
 */
public class StandardBoilerMeasure extends AbstractCylinderBoilerMeasure {
	final double minimumFloorArea;
	final double minimumExternalSpace;
	
	final ITechnologyOperations operations;
	final IDimension<ITechnologyModel> technologyDimension;
	final IDimension<StructureModel> structureDimension;
	
	final Set<HeatingSystemControlType> heatingSystemControlTypes = Sets.newHashSet(new HeatingSystemControlType[] { 
			HeatingSystemControlType.PROGRAMMER,
			HeatingSystemControlType.ROOM_THERMOSTAT, HeatingSystemControlType.THERMOSTATIC_RADIATOR_VALVE });
	
	@Inject
	public StandardBoilerMeasure(
			final ITimeDimension time, 
			final IWetHeatingMeasureFactory factory,
			
			final IDimension<ITechnologyModel> technologyDimension,
			final IDimension<StructureModel> structureDimension,
			final ITechnologyOperations operations,
			
			@Assisted final FuelType fuelType, 
			@Assisted final ISizingFunction sizingFunction,
			@Assisted("capex") final IComponentsFunction<Number> capitalCostFunction,
			@Assisted("opex") final IComponentsFunction<Number> operationalCostFunction,
			@Assisted("wetHeating") final IComponentsFunction<Number> wetHeatingCostFunction,
			@Assisted("winterEfficiency") final IComponentsFunction<Number> winterEfficiency,
			@Assisted("summerEfficiency") final IComponentsFunction<Number> summerEfficiency,
			@Assisted("cylinderVolume") final IComponentsFunction<Number> cylinderVolume,
			@Assisted("cylinderInsulation") final double cylinderInsulationThickness, 
			@Assisted("floorArea") final double minimumFloorArea,
			@Assisted("externalSpace") final double minimumExternalSpace
			) {
		super(	time, 
				technologyDimension,
				operations,
				factory,
				TechnologyType.standardBoiler(fuelType), 
				sizingFunction,
				capitalCostFunction, 
				operationalCostFunction, 
				wetHeatingCostFunction,
				fuelType == FuelType.ELECTRICITY ?
                  ConstantComponentsFunction.<Number>of(Name.of("100%"), 1d):
                  winterEfficiency,
              fuelType == FuelType.ELECTRICITY ?
                  ConstantComponentsFunction.<Number>of(Name.of("100%"), 1d):
                  summerEfficiency,
				fuelType, 
				cylinderVolume, 
				cylinderInsulationThickness);
		this.minimumFloorArea = minimumFloorArea;
		this.minimumExternalSpace = minimumExternalSpace;
		this.operations = operations;
		this.technologyDimension = technologyDimension;
		this.structureDimension = structureDimension;
	}

	private class Modifier implements IModifier<ITechnologyModel> {
		private final double opex;
        private final double winterEfficiency;
        private final double summerEfficiency;
        private final double cylinderVolume;

		public Modifier(final double opex, 
						final double winterEfficiency,
						final double summerEfficiency,
                        final double cylinderVolume) {
			this.opex = opex;
			this.winterEfficiency = winterEfficiency;
			this.summerEfficiency = summerEfficiency;
            this.cylinderVolume = cylinderVolume;
		}

		@Override
		public boolean modify(final ITechnologyModel newCase) {
			// TODO Auto-generated method stub
			final IBoiler heating = IBoilersFactory.eINSTANCE.createBoiler();
			heating.setWinterEfficiency(Efficiency.fromDouble(winterEfficiency));
			heating.setSummerEfficiency(Efficiency.fromDouble(summerEfficiency));
			heating.setFuel(getFuelType());
			heating.setPumpInHeatedSpace(true);
			heating.setFlueType(getFlueType());
			heating.setCondensing(isCondensing());
			heating.setAnnualOperationalCost(opex);
			
			operations.installHeatSource(newCase,
                                         heating,
                                         true,
                                         true,
                                         EmitterType.RADIATORS,
                                         getHeatingSystemControlTypes(),
                                         cylinderInsulationThickness,
                                         cylinderVolume);
			return true;
		}
	}
	
	@Override
	protected boolean doApply(final ISettableComponentsScope components, final ILets lets, final double size, final double capex, final double opex) throws NHMException {
		components.modify(technologyDimension, new Modifier(opex,
															getWinterEfficiency(components, lets),
															getSummerEfficiency(components, lets),
                                                            getCylinderVolume(components, lets)));
		
		return true;
	}

	@Override
	protected boolean doIsSuitable(final IComponents components) {
		final StructureModel structure = components.get(structureDimension);
		
		final FuelType ft = getFuelType();
		
		if (ft == FuelType.BIOMASS_PELLETS || ft == FuelType.BIOMASS_WOOD || ft == FuelType.BIOMASS_WOODCHIP) {
			if (structure.getBuiltFormType().isFlat()) return false;
		}
		
		if (minimumExternalSpace > 0) {		
			if (structure.getBackPlotArea() < minimumExternalSpace && structure.getFrontPlotArea() < minimumExternalSpace)
				return false;
		}

		if (minimumFloorArea > 0) {
			if (structure.getFloorArea() < minimumFloorArea)
				return false;
		}
		
		final ITechnologyModel technologies = components.get(technologyDimension);
		
		if (getFuelType().isGas()) {
			return GasBoilerSuitability.isSuitableIfGas(getFuelType(), operations, structure, technologies);
		} else if (getFuelType() == FuelType.OIL) {
			if (operations.hasCommunitySpaceHeating(technologies) ||
					operations.hasCommunityWaterHeating(technologies))
				return false;
		}
		
		return true;
	}
	
	@Override
	protected Set<HeatingSystemControlType> getHeatingSystemControlTypes() {
		return heatingSystemControlTypes;
	}
}
