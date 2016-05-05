package uk.org.cse.stockimport.imputation.house;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class RdSAPHousePropertyTables implements IHousePropertyTables {
	
	private final Map<Integer, Double> livingAreaFactions = ImmutableMap.<Integer, Double> builder()
            .put(1, 0.75)
            .put(2, 0.50)
            .put(3, 0.30)
            .put(4, 0.25)
            .put(5, 0.21)
            .put(6, 0.18)
            .put(7, 0.16)
            .put(8, 0.14)
            .put(9, 0.13)
            .put(10, 0.12)
            .put(11, 0.11)
            .put(12, 0.10)
            .put(13, 0.10)
            .put(14, 0.09)
            .build();

    private final double defaultFactor = 0.09;

	@Override
	public Map<Integer, Double> getlivingAreaFactions() {
		// TODO Auto-generated method stub
		return livingAreaFactions;
	}

	@Override
	public double getDefaultFraction() {
		return defaultFactor;
	}
}
