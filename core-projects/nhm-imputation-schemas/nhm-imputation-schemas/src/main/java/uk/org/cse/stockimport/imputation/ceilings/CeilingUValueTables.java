package uk.org.cse.stockimport.imputation.ceilings;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import uk.org.cse.nhm.energycalculator.api.types.RoofConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;

public class CeilingUValueTables implements ICeilingUValueTables {
	
	final TreeMap<Integer, Double> insulatedPitchedUValues = new TreeMap<Integer, Double>();
	final TreeMap<Integer, Double> insulatedThatchedUValues = new TreeMap<Integer, Double>();
	final List<TreeMap<Integer,Double>>  unknownValuesByRoofTypeAndAgeBand = new ArrayList<>();
	final List<TreeMap<Integer,Double>>  unknownValuesByRoofTypeAndAgeBandWithRoomInRoof = new ArrayList<>();
	
	private double partyCeilingKValue;
	private double roofKValue;
	
	public CeilingUValueTables(){
		for(final RoofConstructionType roofConstructionType : RoofConstructionType.values()){
			unknownValuesByRoofTypeAndAgeBand.add(new TreeMap<Integer, Double>());
		}
		//Slight fudge for room in roof as CHM has no values for these
		unknownValuesByRoofTypeAndAgeBandWithRoomInRoof.add(new TreeMap<Integer, Double>());
		unknownValuesByRoofTypeAndAgeBandWithRoomInRoof.add(new TreeMap<Integer, Double>());
	}
	
	@Override
	public TreeMap<Integer, Double> getInsulatedPitchedUValues() {
		return insulatedPitchedUValues;
	}

	@Override
	public TreeMap<Integer, Double> getInsulatedThatchedUValues() {
		return insulatedThatchedUValues;
	}

	@Override
	public double[][] getUnknownValuesByRoofTypeAndAgeBand() {
		final double[][] unkownValues = new double[unknownValuesByRoofTypeAndAgeBand.size()][11];
		
		int row =0;
		for (final TreeMap<Integer,Double> map : unknownValuesByRoofTypeAndAgeBand){
			for(final Integer ageBand : map.keySet()){
				unkownValues[row][ageBand] = map.get(ageBand);
			}
			row++;
		}
		
		return unkownValues;
	}

	@Override
	public double[][] getUnknownValuesByRoofTypeAndAgeBandWithRoomInRoof() {
		final double[][] unkownValues = new double[unknownValuesByRoofTypeAndAgeBandWithRoomInRoof.size()][11];
		
		int row =0;
		for (final TreeMap<Integer,Double> map : unknownValuesByRoofTypeAndAgeBandWithRoomInRoof){
			for(final Integer ageBand : map.keySet()){
				unkownValues[row][ageBand] = map.get(ageBand);
			}
			row++;
		}
		
		return unkownValues;
	}

	@Override
	public double getPartyCeilingKValue() {
		return partyCeilingKValue;
	}

	@Override
	public double getRoofKValue() {
		return roofKValue;
	}
	
	public void addInsulatedPitchedUValue(final Double insulationThickness, final Double uValue){
		insulatedPitchedUValues.put((int)Math.round(insulationThickness), uValue);
	}
	
	public void addInsulatedThatchedUValue(final Double insulationThickness, final Double uValue){
		insulatedThatchedUValues.put((int)Math.round(insulationThickness), uValue);
	}
	
	public void addUValuesForUnkownInsulationThickness(final RoofConstructionType roofType, final Band ageBandValue, final Double value){
		final TreeMap<Integer,Double> ageBandValues = unknownValuesByRoofTypeAndAgeBand.get(roofType.ordinal());
		ageBandValues.put(ageBandValue.ordinal(), value);
	}
	
	public void addUnknownValuesByRoofTypeAndAgeBandWithRoomInRoof(final RoofConstructionType roofType, final Band ageBandValue, final Double value){
		final TreeMap<Integer,Double> ageBandValues = unknownValuesByRoofTypeAndAgeBandWithRoomInRoof.get(roofType.ordinal());
		ageBandValues.put(ageBandValue.ordinal(), value);
	}
	
	public void setPartyCeilingKValue(final double partyCeilingKValue) {
		this.partyCeilingKValue = partyCeilingKValue;
	}

	public void setRoofKValue(final double roofKValue) {
		this.roofKValue = roofKValue;
	}
}
