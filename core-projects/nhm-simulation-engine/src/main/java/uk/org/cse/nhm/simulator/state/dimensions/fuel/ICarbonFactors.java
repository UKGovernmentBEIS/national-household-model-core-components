package uk.org.cse.nhm.simulator.state.dimensions.fuel;

import java.util.EnumMap;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;

public interface ICarbonFactors {
	/**
	 * Carbon factor lookup table as per SAP 2009, page 199
	 * @author hinton
	 *
	 */
	static class SapFactors implements ICarbonFactors {
		public static final EnumMap<FuelType, Double> factors = new EnumMap<>(FuelType.class);
		static {
			for (final FuelType ft : FuelType.values()) {
				factors.put(ft, 0d);
			}
			factors.put(FuelType.MAINS_GAS, 			0.216);
			factors.put(FuelType.BULK_LPG,  			0.241);
			factors.put(FuelType.BOTTLED_LPG, 			0.241);
			factors.put(FuelType.ELECTRICITY,			0.519);
			factors.put(FuelType.PEAK_ELECTRICITY, 		0.519);
			factors.put(FuelType.OFF_PEAK_ELECTRICITY, 	0.519);
			factors.put(FuelType.OIL, 					0.298);
			factors.put(FuelType.HOUSE_COAL, 			0.394);
			factors.put(FuelType.BIOMASS_WOOD, 			0.019);
			factors.put(FuelType.BIOMASS_PELLETS, 		0.039);
			factors.put(FuelType.BIOMASS_WOODCHIP, 		0.016);
			factors.put(FuelType.COMMUNITY_HEAT, 		0.216); 			// Assuming mains gas community heat source.
			factors.put(FuelType.PHOTONS, 				0d);
		}
		
		public SapFactors() {
			
		}
		
		@Override
		public double getCarbonFactor(FuelType ft) {
			return factors.get(ft);
		}
	}
	
	public ICarbonFactors SAP09 = new SapFactors();

	double getCarbonFactor(FuelType ft);
}
