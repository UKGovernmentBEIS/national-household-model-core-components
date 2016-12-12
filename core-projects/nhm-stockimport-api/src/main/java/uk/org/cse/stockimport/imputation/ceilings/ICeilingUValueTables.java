package uk.org.cse.stockimport.imputation.ceilings;

import java.util.TreeMap;

public interface ICeilingUValueTables {
	TreeMap<Integer, Double> getInsulatedPitchedUValues();
	TreeMap<Integer, Double> getInsulatedThatchedUValues();
	double[][] getUnknownValuesByRoofTypeAndAgeBand();
	double[][] getUnknownValuesByRoofTypeAndAgeBandWithRoomInRoof();
}
