package uk.org.cse.stockimport.imputation.walls;


public class WallPropertyTables implements IWallPropertyTables {
	
	IWallInfiltrationImputer infiltrationImputer;
	IWallThicknessImputer wallThicknessImputer;
	IWallUValueImputer wallUValueImputer;
	IWallKValueImputer wallKValueImputer;

	@Override
	public IWallInfiltrationImputer getWallInfiltrationImputer() {
		return infiltrationImputer;
	}

	@Override
	public IWallKValueImputer getWallKValueImputer() {
		return wallKValueImputer;
	}

	@Override
	public IWallUValueImputer getWallUValueImputer() {
		return wallUValueImputer;
	}

	@Override
	public IWallThicknessImputer getWallThicknessImputer() {
		return wallThicknessImputer;
	}

	@Override
	public void setWallInfiltrationImputer(
			IWallInfiltrationImputer infiltrationImputer) {
		this.infiltrationImputer = infiltrationImputer;
	}

	@Override
	public void setWallThicknessImputer(IWallThicknessImputer imputer) {
		wallThicknessImputer = imputer;
	}

	@Override
	public void setWallUValueImputer(IWallUValueImputer wallIWallUValueImputer) {
		this.wallUValueImputer = wallIWallUValueImputer;
	}

	@Override
	public void setWallKValueImputer(IWallKValueImputer wallKValueImputer) {
		this.wallKValueImputer = wallKValueImputer;
	}
}
