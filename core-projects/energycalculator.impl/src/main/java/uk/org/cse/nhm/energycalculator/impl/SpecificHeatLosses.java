package uk.org.cse.nhm.energycalculator.impl;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.Property;

import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.StepRecorder;
import uk.org.cse.nhm.energycalculator.api.types.steps.EnergyCalculationStep;

@AutoProperty
public class SpecificHeatLosses implements ISpecificHeatLosses {
    public final double fabricLoss;
	public final double interzoneHeatLoss;
	public final double floorArea;
    public final double thermalMassParameter;
	public final double ventilationLoss;
	public final double thermalBridgeEffect;
    public final double airChangeRate;
    private final double specificHeatLoss;
    private final double heatLossParameter;

    public SpecificHeatLosses(final double fabricLoss,
                              final double interzoneHeatLoss,
                              final double thermalMassParameter,
                              final double floorArea,
                              final double ventilationLoss,
                              final double thermalBridgeEffect,
                              final double airChangeRate) {
        this.fabricLoss = fabricLoss;
		this.interzoneHeatLoss = interzoneHeatLoss;
        this.thermalMassParameter = thermalMassParameter;
        this.floorArea = floorArea;
		this.ventilationLoss = ventilationLoss;
        this.thermalBridgeEffect = thermalBridgeEffect;
        this.airChangeRate = airChangeRate;

		StepRecorder.recordStep(EnergyCalculationStep.ThermalBridges, thermalBridgeEffect);
		StepRecorder.recordStep(EnergyCalculationStep.VentilationHeatLoss, ventilationLoss);

        /*
        BEISDOC
        NAME: Total fabric heat loss
        DESCRIPTION: Fabric heat loss added to thermal bridging
        TYPE: formula
        UNIT: W/℃
        SAP: (37)
        BREDEM: 3H
        DEPS: fabric-heat-loss,thermal-bridging-heat-loss
        ID: total-fabric-heat-loss
        CODSIEB
        */
        double totalFabricHeatLoss = fabricLoss + thermalBridgeEffect;
        StepRecorder.recordStep(EnergyCalculationStep.FabricLossTotal, totalFabricHeatLoss);

        /*
        BEISDOC
        NAME: Specific Heat Loss
        DESCRIPTION: The rate at which the dwelling loses heat per degree of temperature difference with the outside. The ventilation heat loss added to the total fabric heat loss.
        TYPE: Formula
        UNIT: W/℃
        SAP: (39)
        BREDEM: 3H
        DEPS: ventilation-heat-loss,total-fabric-heat-loss
        GET: house.heat-loss
        ID: specific-heat-loss
        CODSIEB
        */
        specificHeatLoss = totalFabricHeatLoss + ventilationLoss;
        StepRecorder.recordStep(EnergyCalculationStep.HeatTransferCoefficient, specificHeatLoss);

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
        heatLossParameter = getSpecificHeatLoss() / floorArea;
        StepRecorder.recordStep(EnergyCalculationStep.HeatLossParameter, heatLossParameter);
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
    public double getFabricLoss() {
        return fabricLoss;
    }

	@Override
	@Property
    public double getThermalMassParameter() {
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
