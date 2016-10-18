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
			factors.put(FuelType.MAINS_GAS, 			0.198);
			factors.put(FuelType.BULK_LPG,  			0.245);
			factors.put(FuelType.BOTTLED_LPG, 			0.245);
			factors.put(FuelType.ELECTRICITY,			0.517);
			factors.put(FuelType.PEAK_ELECTRICITY, 		0.517);
			factors.put(FuelType.OFF_PEAK_ELECTRICITY, 	0.517);
			factors.put(FuelType.OIL, 					0.274);
			factors.put(FuelType.HOUSE_COAL, 			0.301);
			factors.put(FuelType.BIOMASS_WOOD, 			0.028);
			factors.put(FuelType.BIOMASS_PELLETS, 		0.028);
			factors.put(FuelType.BIOMASS_WOODCHIP, 		0.009);
			factors.put(FuelType.COMMUNITY_HEAT, 		0.198);
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
