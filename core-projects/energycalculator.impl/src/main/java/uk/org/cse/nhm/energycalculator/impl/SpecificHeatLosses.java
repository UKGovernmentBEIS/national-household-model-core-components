package uk.org.cse.nhm.energycalculator.impl;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.Property;

import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;

@AutoProperty
public class SpecificHeatLosses implements ISpecificHeatLosses {
	public final double specificHeatLoss;
	public final double interzoneHeatLoss;
	public final double floorArea;
	public final double thermalMass;
	public final double ventilationLoss;
	public final double thermalBridgeEffect;
	public final double heatLossParameter;
	public final double thermalMassParameter;
    public final double airChangeRate;

	public SpecificHeatLosses(final double specificHeatLoss, final double interzoneHeatLoss, final double thermalMass, final double floorArea, final double ventilationLoss, final double thermalBridgeEffect, final double airChangeRate) {
		this.specificHeatLoss = specificHeatLoss;
		this.interzoneHeatLoss = interzoneHeatLoss;
		this.floorArea = floorArea;
		this.thermalMass = thermalMass;
		this.ventilationLoss = ventilationLoss;
		this.thermalBridgeEffect = thermalBridgeEffect;
		this.heatLossParameter = this.specificHeatLoss / this.floorArea;
		this.thermalMassParameter = this.thermalMass / this.floorArea;
        this.airChangeRate = airChangeRate;
	}

	@Override
	public double getSpecificHeatLoss() {
		return specificHeatLoss;
	}

	@Override
	public double getInterzoneHeatLoss() {
		return interzoneHeatLoss;
	}

	@Override
	@Property
	public double getHeatLossParameter() {
		return heatLossParameter;
	}

	@Override
	public double getThermalMass() {
		return thermalMass;
	}

	@Override
	@Property
	public double getThermalMassParameter() {
		return thermalMassParameter;
	}
	
	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
	
	@Override
	public double getVentilationLoss() {
		return ventilationLoss;
	}
	
	@Override
	public double getThermalBridgeEffect() {
		return thermalBridgeEffect;
	}

    @Override
    public double getAirChangeRate() {
        return this.airChangeRate;
    }
}
