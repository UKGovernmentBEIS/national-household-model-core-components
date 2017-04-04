package uk.org.cse.nhm.simulator.state.dimensions.fuel.cost;

import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.ICarbonFactors;

public class EmissionsTest {
	private final Random random = new Random();
	
	private class Fac implements ICarbonFactors {
		
		private final double[] factors = new double[FuelType.values().length];
		
		public Fac() {
			for (int i = 0; i<factors.length; i++) {
				// the Emissions class under test makes optimisations if values are zero
				// so we want to ensure some zeroes by only filling in half the squares
				if (random.nextBoolean()) {
					factors[i] = random.nextDouble() * 10;
				}
			}
		}
		
		@Override
		public double getCarbonFactor(final FuelType ft) {
			return factors[ft.ordinal()];
		}
		
	}
	
	private class Pow implements IPowerTable {
		
		private final float[][] en = new float[ServiceType.values().length][FuelType.values().length];
		
		public Pow() {
			for (final float[] vals : en) {
				if (random.nextBoolean()) {
					// the Emissions class under test makes optimisations if values are zero
					// so we want to ensure some zeroes again
					for (int i = 0; i<vals.length;i++) {
						vals[i] = (float)(random.nextDouble() * 100f);
					}
				}
			}
		}
		
		@Override
		public float getFuelUseByEnergyService(final ServiceType es, final FuelType ft) {
			return en[es.ordinal()][ft.ordinal()];
		}

		@Override
		public float getSpecificHeatLoss() {
			return 0;
        }

        @Override
        public float getFabricHeatLoss() {
            return 0;
        }

        @Override
        public float getVentilationHeatLoss() {
            return 0;
        }

        @Override
        public float getThermalBridgingHeatLoss() {
            return 0;
        }

        @Override
        public float getAirChangeRate() {
            return 0;
        }

        @Override
        public float getWeightedHeatLoad(double[] weights, boolean space,
        		boolean water) {
        	return 0;
        }

		@Override
		public float getPowerByFuel(final FuelType ft) {
			float acc = 0;
			for (final ServiceType st : ServiceType.values()) {
				acc += getFuelUseByEnergyService(st, ft);
			}
			return acc;
		}
		
		@Override
		public float getMeanInternalTemperature() {
			return 0;
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
		public float getPrimaryHeatDemand() {
			return 0;
		}

		@Override
		public float getSecondaryHeatDemand() {
			return 0;
		}

		@Override
		public float getHotWaterDemand() {
			return 0;
		}
	}
	
	@Test
	public void testEmissionsComputesProduct() {
		for (int i = 0; i<1000; i++) {
			final Fac fac = new Fac();
			final Pow pow = new Pow();
			final Emissions e = new Emissions(fac, pow);
			for (final FuelType ft : FuelType.values()) {
				for (final ServiceType st : ServiceType.values()) {
					Assert.assertEquals(
							fac.factors[ft.ordinal()] *
							pow.en[st.ordinal()][ft.ordinal()],
							e.getAnnualEmissions(ft, st),
							0.001
							);
				}
			}
		}
	}
}
