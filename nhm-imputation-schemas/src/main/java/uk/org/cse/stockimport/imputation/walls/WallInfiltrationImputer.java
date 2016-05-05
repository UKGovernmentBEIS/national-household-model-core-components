package uk.org.cse.stockimport.imputation.walls;

import uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType;

/**
 * Lookup table for wall infiltration rates.
 * 
 * @author hinton
 * @since 1.0
 */
public class WallInfiltrationImputer implements IWallInfiltrationImputer {
	//0.25 for steel or timber frame or 0.35 for masonry construction 

	private double steelOrTimberFrameInfiltration;
	private double otherWallInfiltration;
	
	public WallInfiltrationImputer(){
		this(true);
	}
	
	public WallInfiltrationImputer(boolean useRdSapDefaults){
		if(useRdSapDefaults){
			steelOrTimberFrameInfiltration = 0.25;
			otherWallInfiltration = 0.35;
		}
	}
	
	
	
	/* (non-Javadoc)
	 * @see uk.org.cse.stockimport.imputation.rdsap.walls.IWallInfiltrationImputer#getAirChangeRate(uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType)
	 */
    @Override
	public double getAirChangeRate(final WallConstructionType constructionType) {
		if (constructionType == WallConstructionType.TimberFrame || constructionType == WallConstructionType.MetalFrame) {
			return steelOrTimberFrameInfiltration;
		} else {
			return otherWallInfiltration;
		}
	}

	public void setSteelOrTimberFrameInfiltration(
			double steelOrTimberFrameInfiltration) {
		this.steelOrTimberFrameInfiltration = steelOrTimberFrameInfiltration;
	}

	public void setOtherWallInfiltration(double otherWallInfiltration) {
		this.otherWallInfiltration = otherWallInfiltration;
	}
}
