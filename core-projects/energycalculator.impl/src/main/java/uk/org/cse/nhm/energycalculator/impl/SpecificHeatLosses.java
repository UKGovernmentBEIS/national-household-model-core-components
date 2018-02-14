package uk.org.cse.nhm.energycalculator.impl;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.Property;

import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;

@AutoProperty
public class SpecificHeatLosses implements ISpecificHeatLosses {
    public final double fabricLoss;
	public final double interzoneHeatLoss;
	public final double floorArea;
    public final double thermalMassParameter;
	public final double ventilationLoss;
	public final double thermalBridgeEffect;
    public final double airChangeRate;
    public final double airChangeExcludingDeliberate;

    public SpecificHeatLosses(final double fabricLoss,
                              final double interzoneHeatLoss,
                              final double thermalMassParameter,
                              final double floorArea,
                              final double ventilationLoss,
                              final double thermalBridgeEffect,
                              final double airChangeRate, 
                              final double airChangeExcludingDeliberate) {
        this.fabricLoss = fabricLoss;
		this.interzoneHeatLoss = interzoneHeatLoss;
        this.thermalMassParameter = thermalMassParameter;
        this.floorArea = floorArea;
		this.ventilationLoss = ventilationLoss;
        this.thermalBridgeEffect = thermalBridgeEffect;
        this.airChangeRate = airChangeRate;
        this.airChangeExcludingDeliberate = airChangeExcludingDeliberate;
	}

	@Override
    public double getSpecificHeatLoss() {
        /*
        BEISDOC
        NAME: Total fabric heat loss
        DESCRIPTION: Fabric heat loss added to thermal bridging
        TYPE: formula
        UNIT: W/℃
        SAP: (37)
        SAP_COMPLIANT: Yes
        BREDEM: 3H
        BREDEM_COMPLIANT: Yes
        DEPS: fabric-heat-loss,thermal-bridging-heat-loss
        ID: total-fabric-heat-loss
        CODSIEB
        */

        /*
        BEISDOC
        NAME: Specific Heat Loss
        DESCRIPTION: The rate at which the dwelling loses heat per degree of temperature difference with the outside. The ventilation heat loss added to the total fabric heat loss.
        TYPE: Formula
        UNIT: W/℃
        SAP: (39)
        SAP_COMPLIANT: Yes
        BREDEM: 3H
        BREDEM_COMPLIANT: Yes
        DEPS: ventilation-heat-loss,total-fabric-heat-loss
        GET: house.heat-loss
        ID: specific-heat-loss
        CODSIEB
        */

        return fabricLoss + ventilationLoss + thermalBridgeEffect;
	}

	@Override
	public double getInterzoneHeatLoss() {
		return interzoneHeatLoss;
	}

	@Override
	@Property
    public double getHeatLossParameter() {
        /*
        BEISDOC
        NAME: Heat loss parameter
        DESCRIPTION: The heat loss per degree of the dwelling divided by the floor area
        TYPE: formula
        UNIT: W/m^2/℃
        SAP: (40)
        SAP_COMPLIANT: Yes
        BREDEM: 3I
        BREDEM_COMPLIANT: Yes
        DEPS: specific-heat-loss,dwelling-floor-area
        GET:
        SET:
        ID: heat-loss-parameter
        CODSIEB
        */
        return getSpecificHeatLoss() / floorArea;
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
        SAP_COMPLIANT: Yes
        BREDEM: 4A
        BREDEM_COMPLIANT: Yes
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

	@Override
	public double getAirChangeExcludingDeliberate() {
		return this.airChangeExcludingDeliberate;
	}
}
