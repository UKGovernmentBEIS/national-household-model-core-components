package uk.org.cse.stockimport.imputation.walls;



public interface IWallPropertyTables {
	IWallInfiltrationImputer getWallInfiltrationImputer();
	void setWallInfiltrationImputer(IWallInfiltrationImputer infiltrationImputer);
	
	IWallUValueImputer getWallUValueImputer();
	void setWallUValueImputer(IWallUValueImputer wallIWallUValueImputer);
	
	IWallThicknessImputer getWallThicknessImputer();
	void setWallThicknessImputer(IWallThicknessImputer imputer);
}
