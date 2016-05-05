package uk.org.cse.stockimport.imputation.walls;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType;
import uk.org.cse.nhm.hom.components.fabric.types.WallInsulationType;

import com.google.common.collect.ImmutableMap;

/**
 * Lookup K values for walls; this is based on the data in the CHM spreadsheet, as RDSAP doesn't seem to say much about this.
 *
 * Because the CHM spreadsheet only handles some combinations of insulation, I have made the following assumptions:
 * @assumption For those wall types where only the uninsulated k value is specified, insulation does not change the k value (so the uninsulated value is used).
 * @assumption For those wall types where there are multiple insulation types present, the minimum k value from the table is chosen.
 * 
 * @author hinton
 * @since 1.0
 */
public class WallKValueImputer implements IWallKValueImputer {
	/**
	 * K values by construction type as-built
	 */
	private Map<WallConstructionType, Double> asBuilt = new TreeMap<WallConstructionType, Double>();
	
	/**
	 * K values by construction type with single insulation type applied.
	 */
	private Map<WallConstructionType, Map<WallInsulationType, Double>> insulated = new TreeMap<>();

	private double internalWallKValue;
	
	/* (non-Javadoc)
	 * @see uk.org.cse.stockimport.imputation.rdsap.walls.IWallKValueImputer#getKValue(uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType, java.util.Set)
	 */
    @Override
	public double getKValue(
			final WallConstructionType constructionType,
			final Set<WallInsulationType> insulationTypes) {
		if (insulationTypes.isEmpty() || !insulated.containsKey(constructionType)) {
			if (asBuilt.containsKey(constructionType)) {
				return asBuilt.get(constructionType);
			} else {
				return 0;
			}
		} else {
			double minimum = Double.MAX_VALUE;
			final Map<WallInsulationType, Double> map = insulated.get(constructionType);
			for (final WallInsulationType wit : insulationTypes) {
				if (map.containsKey(wit)) minimum = Math.min(minimum, map.get(wit));
			}

			if (minimum == Double.MAX_VALUE) {
				return asBuilt.get(constructionType);
			}

			return minimum;
		}
	}
    
    public WallKValueImputer(){
    	this(true);
    }
    
    public WallKValueImputer(boolean useRdSapDefaults){
    	if(useRdSapDefaults){
    		asBuilt.putAll(ImmutableMap.<WallConstructionType, Double>builder()
				.put(WallConstructionType.Party_DensePlasterBothSidesDenseBlocksCavity, 180d)
				.put(WallConstructionType.Party_SinglePlasterboardBothSidesDenseBlocksCavity, 70d)
				.put(WallConstructionType.Party_SinglePlasterboardBothSidesDenseAACBlocksCavity, 45d)
				.put(WallConstructionType.Party_DoublePlasterBothSidesTwinTimberFrame, 50d)
				.put(WallConstructionType.Party_MetalFrame, 20d)
				.put(WallConstructionType.GraniteOrWhinstone, 202d)
				.put(WallConstructionType.Sandstone, 156d)
				.put(WallConstructionType.SolidBrick, 135d)
				.put(WallConstructionType.Cob, 148d)
				.put(WallConstructionType.Cavity, 139d)
				.put(WallConstructionType.TimberFrame, 9d)
				.put(WallConstructionType.SystemBuild, 211d)
				.put(WallConstructionType.MetalFrame, 14d).build());
    		
    		insulated.putAll(ImmutableMap.<WallConstructionType, Map<WallInsulationType, Double>>builder()
				.put(WallConstructionType.SolidBrick, ImmutableMap.of(WallInsulationType.External, 135d, WallInsulationType.Internal, 17d))
				.put(WallConstructionType.Cob, ImmutableMap.of(WallInsulationType.External, 148d, WallInsulationType.Internal, 66d))
				.put(WallConstructionType.SystemBuild, ImmutableMap.of(WallInsulationType.External, 211d, WallInsulationType.Internal, 10d)).build());
    		
    		internalWallKValue = 100;
    	}
    }
    
    /**
     * @param constructionType
     * @param kValue
     * @since 3.0
     */
    public void addAsBuiltWallUValue(WallConstructionType constructionType, Double kValue){
    	asBuilt.put(constructionType, kValue);
    }
    
    public void addInsulatedWallType(WallConstructionType wallConstructionType, WallInsulationType insulationType, Double kValue){
    	if (insulated.containsKey(wallConstructionType) == false){
    		Map<WallInsulationType, Double> map = new TreeMap<>();
    		map.put(insulationType, kValue);
    		
    		insulated.put(wallConstructionType, map);
    	} else {
    		insulated.get(wallConstructionType).put(insulationType, kValue);
    	}
    }

    /* (non-Javadoc)
	 * @see uk.org.cse.stockimport.imputation.rdsap.walls.IWallKValueImputer#getInternalWallKValue()
	 */
    @Override
	public double getInternalWallKValue() {
		return internalWallKValue;
	}

}
