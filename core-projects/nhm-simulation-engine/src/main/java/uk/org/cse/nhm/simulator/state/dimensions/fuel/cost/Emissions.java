package uk.org.cse.nhm.simulator.state.dimensions.fuel.cost;

import java.util.List;

import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.state.dimensions.FuelServiceTable;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.ICarbonFactors;

class Emissions implements IEmissions {

    private final FuelServiceTable table;

    public Emissions(final ICarbonFactors pricing, final IPowerTable result) {
        final FuelServiceTable.Builder builder = FuelServiceTable.builder();

        for (final FuelType ft : FuelType.values()) {
            if (result.getPowerByFuel(ft) == 0) {
                continue;
            }

            final double factor = pricing.getCarbonFactor(ft);
            if (factor == 0) {
                continue;
            }

            for (final ServiceType es : ServiceType.values()) {
                final double f = result.getFuelUseByEnergyService(es, ft);
                builder.add(ft, es, f * factor);
            }
        }

        this.table = builder.build();
    }

    @Override
    public float getAnnualEmissions(FuelType ft, ServiceType es) {
        return table.get(ft, es);
    }

    public float getAnnualEmissions(FuelType ft, List<ServiceType> es) {
        float accum = 0f;

        for (ServiceType service : es) {
            accum += getAnnualEmissions(ft, service);
        }
        return accum;
    }
}
