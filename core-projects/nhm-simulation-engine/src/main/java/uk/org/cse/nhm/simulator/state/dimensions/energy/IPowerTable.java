package uk.org.cse.nhm.simulator.state.dimensions.energy;

import java.util.List;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculationSteps;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;

public interface IPowerTable extends IEnergyCalculationSteps {
	float getFuelUseByEnergyService(final ServiceType es, final FuelType ft);
	float getFuelUseByEnergyService(final List<ServiceType> es, final FuelType ft);

	/**
	 * The heat demand which was satisfied by the primary space heating system.
	 */
	float getPrimaryHeatDemand();
	/**
	 * The heat demand which was satistifed by the secondary space heating system.
	 */
	float getSecondaryHeatDemand();
	float getHotWaterDemand();

    float getSpecificHeatLoss();
    float getFabricHeatLoss();
    float getVentilationHeatLoss();
    float getThermalBridgingHeatLoss();

	float getPowerByFuel(final FuelType ft);

	float getMeanInternalTemperature();

    float getAirChangeRate();

    float getWeightedHeatLoad(final double[] weights, final boolean space, final boolean water);
}
