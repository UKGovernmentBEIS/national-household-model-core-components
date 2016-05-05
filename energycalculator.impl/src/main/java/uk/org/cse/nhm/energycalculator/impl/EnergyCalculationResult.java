package uk.org.cse.nhm.energycalculator.impl;

import java.util.EnumMap;
import java.util.Map;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculationResult;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.types.AreaType;

@AutoProperty
public class EnergyCalculationResult implements IEnergyCalculationResult {
	final IEnergyState energyState;
	final ISpecificHeatLosses heatLosses;
	final Map<AreaType, Double> heatLossAreas = new EnumMap<AreaType, Double>(AreaType.class);
	final Map<AreaType, Double> heatLossByArea = new EnumMap<AreaType, Double>(AreaType.class);
	
	public EnergyCalculationResult(IEnergyState energyState, ISpecificHeatLosses heatLosses, final double[] areas, final double[] byArea) {
		this.energyState = energyState;
		this.heatLosses = heatLosses;
		for (final AreaType at : AreaType.values()) {
			heatLossAreas.put(at, areas[at.ordinal()]);
			heatLossByArea.put(at, byArea[at.ordinal()]);
		}
	}

	@Override
	public IEnergyState getEnergyState() {
		return energyState;
	}

	@Override
	public ISpecificHeatLosses getHeatLosses() {
		return heatLosses;
	}
	
	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

	public Map<AreaType, Double> getHeatLossAreas() {
		return heatLossAreas;
	}

	public Map<AreaType, Double> getHeatLossByArea() {
		return heatLossByArea;
	}
}
