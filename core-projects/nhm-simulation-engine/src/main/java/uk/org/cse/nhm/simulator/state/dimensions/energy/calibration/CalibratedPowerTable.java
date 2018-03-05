package uk.org.cse.nhm.simulator.state.dimensions.energy.calibration;

import java.util.List;

import uk.org.cse.nhm.energycalculator.api.types.steps.EnergyCalculationStep;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;

public class CalibratedPowerTable implements IPowerTable {
	private final float[] cal;
	private final IPowerTable uncal;

	public CalibratedPowerTable(final float[] cal, final IPowerTable uncal) {
		this.cal = cal;
		this.uncal = uncal;
	}

	@Override
	public float getFuelUseByEnergyService(final ServiceType es, final FuelType ft) {
		final float calibratedPowerByFuel = getPowerByFuel(ft);
		final float uncalibratedPowerByFuel = uncal.getPowerByFuel(ft);

		if (calibratedPowerByFuel == 0) {
			return 0f;
		} else if (uncalibratedPowerByFuel == 0) {
			return calibratedPowerByFuel / ServiceType.values().length;
		} else {
			return uncal.getFuelUseByEnergyService(es, ft)
					* (calibratedPowerByFuel / uncalibratedPowerByFuel);
		}
 	}


	@Override
	public float getFuelUseByEnergyService(List<ServiceType> es, FuelType ft) {
		float accum = 0;
		for (ServiceType serviceType : es) {
			accum += getFuelUseByEnergyService(serviceType, ft);
		}
		return accum;
	}

	@Override
	public float getSpecificHeatLoss() {
		return uncal.getSpecificHeatLoss();
    }

    @Override
    public float getFabricHeatLoss() {
        return uncal.getFabricHeatLoss();
    }

    @Override
    public float getVentilationHeatLoss() {
        return uncal.getVentilationHeatLoss();
    }

    @Override
    public float getThermalBridgingHeatLoss() {
        return uncal.getThermalBridgingHeatLoss();
    }

	@Override
	public float getMeanInternalTemperature() {
		return uncal.getMeanInternalTemperature();
	}

	@Override
	public float getAirChangeRate() {
		return uncal.getMeanInternalTemperature();
	}

    @Override
    public float getWeightedHeatLoad(double[] weights, boolean space, boolean water) {
    	return uncal.getWeightedHeatLoad(weights, space, water);
    }

	@Override
	public float getPowerByFuel(final FuelType ft) {
		return cal[ft.ordinal()];
	}

	@Override
	public float getPrimaryHeatDemand() {
		return uncal.getPrimaryHeatDemand();
	}

	@Override
	public float getSecondaryHeatDemand() {
		return uncal.getSecondaryHeatDemand();
	}

	@Override
	public float getHotWaterDemand() {
		return uncal.getHotWaterDemand();
	}

	@Override
	public float getAirChangeRateWithoutDeliberate() {
		return uncal.getMeanInternalTemperature();
	}

    @Override
    public double readStepAnnual(EnergyCalculationStep step) {
        return uncal.readStepAnnual(step);
    }

    @Override
    public double readStepMonthly(EnergyCalculationStep step, int month) {
        return uncal.readStepMonthly(step, month);
    }
}
