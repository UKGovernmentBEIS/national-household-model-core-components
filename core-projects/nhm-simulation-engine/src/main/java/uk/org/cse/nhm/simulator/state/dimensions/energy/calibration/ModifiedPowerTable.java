package uk.org.cse.nhm.simulator.state.dimensions.energy.calibration;

import java.util.List;
import java.util.Set;

import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;

public class ModifiedPowerTable implements IPowerTable {
	private final IPowerTable delegate;
	private final Set<ServiceType> services;
	
	ModifiedPowerTable(final IPowerTable delegate, final Set<ServiceType> services) {
		super();
		this.delegate = delegate;
		this.services = services;
	}
	
	public static IPowerTable excludingEnergyServices(final IPowerTable delegate, final Set<ServiceType> services) {
		return new ModifiedPowerTable(delegate, services);
	}

	@Override
	public float getFuelUseByEnergyService(final ServiceType es, final FuelType ft) {
		if (services.contains(es)) {
			return 0;
		} else {
			return delegate.getFuelUseByEnergyService(es, ft);
		}
	}
	

	@Override
	public float getFuelUseByEnergyService(List<ServiceType> es, FuelType ft) {
		float accum = 0f;
		for (ServiceType serviceType : es) {
			accum += getFuelUseByEnergyService(serviceType, ft);
		}
		return accum;
	}

	@Override
	public float getSpecificHeatLoss() {
		return delegate.getSpecificHeatLoss();
	}

    @Override
    public float getFabricHeatLoss() {
        return delegate.getFabricHeatLoss();
    }

    @Override
    public float getVentilationHeatLoss() {
        return delegate.getVentilationHeatLoss();
    }

    @Override
    public float getThermalBridgingHeatLoss() {
        return delegate.getThermalBridgingHeatLoss();
    }

	@Override
	public float getMeanInternalTemperature() {
		return delegate.getMeanInternalTemperature();
	}


    @Override
    public float getAirChangeRate() {
        return delegate.getAirChangeRate();
    }
    
    @Override
    public float getWeightedHeatLoad(double[] weights, boolean space, boolean water) {
    	return delegate.getWeightedHeatLoad(weights, space, water);
    }
    
	@Override
	public float getPowerByFuel(final FuelType ft) {
		float remove = 0;
		for (final ServiceType st : services) {
			remove += delegate.getFuelUseByEnergyService(st, ft);
		}
		
		return delegate.getPowerByFuel(ft) - remove;
	}

	@Override
	public float getPrimaryHeatDemand() {
		return delegate.getPrimaryHeatDemand();
	}

	@Override
	public float getSecondaryHeatDemand() {
		return delegate.getSecondaryHeatDemand();
	}

	@Override
	public float getHotWaterDemand() {
		return delegate.getHotWaterDemand();
	}
}
