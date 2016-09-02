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
		
		/*
		BEISDOC
		NAME: Heat loss parameter
		DESCRIPTION: The heat loss per degree of the dwelling divided by the floor area
		TYPE: formula
		UNIT: W/m^2/℃
		SAP: (40)
		BREDEM: 3I
		DEPS: specific-heat-loss,dwelling-floor-area
		GET: 
		SET: 
		ID: heat-loss-parameter
		CODSIEB
		*/
		this.heatLossParameter = this.specificHeatLoss / this.floorArea;
		
		/*
		BEISDOC
		NAME: Thermal mass parameter
		DESCRIPTION: Thermal mass divided by area of the dwelling
		TYPE: formula
		UNIT: kJ/℃/m^2
		SAP: (35), Table 1f
		BREDEM: 4A
		DEPS: thermal-mass,dwelling-floor-area
		GET:
		SET: 
		ID: thermal-mass-parameter
		CODSIEB
		*/
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
