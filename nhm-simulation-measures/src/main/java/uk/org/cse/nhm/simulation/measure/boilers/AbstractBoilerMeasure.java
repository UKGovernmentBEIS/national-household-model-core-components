package uk.org.cse.nhm.simulation.measure.boilers;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.language.builder.action.measure.wetheating.IWetHeatingMeasureFactory;
import uk.org.cse.nhm.simulation.measure.AbstractHeatingMeasure;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.measure.sizing.ISizingFunction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public abstract class AbstractBoilerMeasure extends AbstractHeatingMeasure {
    private final IComponentsFunction<Number> winterEfficiency;
    private final IComponentsFunction<Number> summerEfficiency;
	private final FuelType fuelType;

	public AbstractBoilerMeasure(
			final ITimeDimension time, 
			final IDimension<ITechnologyModel> technologies, 
			final ITechnologyOperations operations,
			final IWetHeatingMeasureFactory factory,
			final TechnologyType technology,
			final ISizingFunction sizingFunction,
			final IComponentsFunction<Number> capitalCostFunction,
			final IComponentsFunction<Number> operationalCostFunction,
			final IComponentsFunction<Number> whCapex,
			final IComponentsFunction<Number> winterEfficiency,
			final IComponentsFunction<Number> summerEfficiency,
			final FuelType fuelType) {
		super(	time, 
				technologies, 
				operations, 
				factory,
				technology, 
				sizingFunction, 
				capitalCostFunction, operationalCostFunction, whCapex);
		this.winterEfficiency = winterEfficiency;
		this.summerEfficiency = summerEfficiency;
		this.fuelType = fuelType;
	}

	public double getWinterEfficiency(final IComponentsScope house, final ILets lets) {
		return winterEfficiency.compute(house, lets).doubleValue();
	}

	public double getSummerEfficiency(final IComponentsScope house, final ILets lets) {
		double eff = summerEfficiency.compute(house, lets).doubleValue();
		if (eff > 0) {
			return eff;
			
		} else {
			return getWinterEfficiency(house, lets) + eff;
		}
	}
	
	protected FlueType getFlueType() {
		switch (fuelType) {
		case BIOMASS_PELLETS:
		case BIOMASS_WOOD:
		case BIOMASS_WOODCHIP:
		case OIL:
		case MAINS_GAS:
			return FlueType.FAN_ASSISTED_BALANCED_FLUE; 			
		case ELECTRICITY:
			return FlueType.NOT_APPLICABLE;
		default:
			return FlueType.CHIMNEY;
		}
	}
	
	protected boolean isCondensing() {
		return fuelType != FuelType.ELECTRICITY &&
				fuelType != FuelType.BIOMASS_PELLETS &&
				fuelType != FuelType.BIOMASS_WOODCHIP &&
				fuelType != FuelType.BIOMASS_WOOD;				
	}

	public FuelType getFuelType() {
		return fuelType;
	}
}
