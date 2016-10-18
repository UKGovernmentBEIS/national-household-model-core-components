package uk.org.cse.stockimport.imputation.lookupbuilders.excel;
import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.Cavity;
import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.Cob;
import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.GraniteOrWhinstone;
import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.MetalFrame;
import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.Party_DensePlasterBothSidesDenseBlocksCavity;
import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.Party_DoublePlasterBothSidesTwinTimberFrame;
import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.Party_MetalFrame;
import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.Party_SinglePlasterboardBothSidesDenseAACBlocksCavity;
import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.Party_SinglePlasterboardBothSidesDenseBlocksCavity;
import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.Sandstone;
import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.SolidBrick;
import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.SystemBuild;
import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.TimberFrame;
import static uk.org.cse.nhm.hom.types.SAPAgeBandValue.Band.A;
import static uk.org.cse.nhm.hom.types.SAPAgeBandValue.Band.B;
import static uk.org.cse.nhm.hom.types.SAPAgeBandValue.Band.C;
import static uk.org.cse.nhm.hom.types.SAPAgeBandValue.Band.D;
import static uk.org.cse.nhm.hom.types.SAPAgeBandValue.Band.E;
import static uk.org.cse.nhm.hom.types.SAPAgeBandValue.Band.F;
import static uk.org.cse.nhm.hom.types.SAPAgeBandValue.Band.G;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType;
import uk.org.cse.nhm.hom.components.fabric.types.WallInsulationType;
import uk.org.cse.nhm.hom.types.SAPAgeBandValue;
import uk.org.cse.stockimport.imputation.lookupbuilders.ILookUpTableBuilder;
import uk.org.cse.stockimport.imputation.walls.IWallPropertyTables;
import uk.org.cse.stockimport.imputation.walls.WallInfiltrationImputer;
import uk.org.cse.stockimport.imputation.walls.WallKValueImputer;
import uk.org.cse.stockimport.imputation.walls.WallPropertyTables;
import uk.org.cse.stockimport.imputation.walls.WallThicknessImputer;
import uk.org.cse.stockimport.imputation.walls.WallUValueImputer;

public class WallPropertyTablesBuilder implements ILookUpTableBuilder<IWallPropertyTables, XSSFWorkbook> {
	
	public static final String SHEETNAME = "Walls";

	@Override
	public IWallPropertyTables buildTables(final XSSFWorkbook workbook){
		final IWallPropertyTables wallPropertyTables = new WallPropertyTables();
		final XSSFSheet sheet = workbook.getSheet(SHEETNAME);
		
		buildInfiltrationTables(workbook, wallPropertyTables, sheet);
		buildWallThicknessTables(workbook, wallPropertyTables, sheet);
		buildWallUValueTables(workbook, wallPropertyTables, sheet);
		buildWallKValueTables(workbook, wallPropertyTables, sheet);
		
		return wallPropertyTables;
	}
	
	void buildWallKValueTables(final XSSFWorkbook workbook, final IWallPropertyTables wallPropertyTables, final XSSFSheet sheet){
		final WallKValueImputer wallKValueImputer = new WallKValueImputer(false);
		
		//AsBuilt
		wallKValueImputer.addAsBuiltWallUValue(Party_DensePlasterBothSidesDenseBlocksCavity, sheet.getRow(81).getCell(1).getNumericCellValue());
		wallKValueImputer.addAsBuiltWallUValue(Party_SinglePlasterboardBothSidesDenseBlocksCavity, sheet.getRow(82).getCell(1).getNumericCellValue());
		wallKValueImputer.addAsBuiltWallUValue(Party_SinglePlasterboardBothSidesDenseAACBlocksCavity, sheet.getRow(83).getCell(1).getNumericCellValue());
		wallKValueImputer.addAsBuiltWallUValue(Party_DoublePlasterBothSidesTwinTimberFrame, sheet.getRow(84).getCell(1).getNumericCellValue());
		wallKValueImputer.addAsBuiltWallUValue(Party_MetalFrame, sheet.getRow(85).getCell(1).getNumericCellValue());
		wallKValueImputer.addAsBuiltWallUValue(GraniteOrWhinstone, sheet.getRow(86).getCell(1).getNumericCellValue());
		wallKValueImputer.addAsBuiltWallUValue(Sandstone, sheet.getRow(87).getCell(1).getNumericCellValue());
		wallKValueImputer.addAsBuiltWallUValue(SolidBrick, sheet.getRow(88).getCell(1).getNumericCellValue());
		wallKValueImputer.addAsBuiltWallUValue(Cob, sheet.getRow(89).getCell(1).getNumericCellValue());
		wallKValueImputer.addAsBuiltWallUValue(Cavity, sheet.getRow(90).getCell(1).getNumericCellValue());
		wallKValueImputer.addAsBuiltWallUValue(TimberFrame, sheet.getRow(91).getCell(1).getNumericCellValue());
		wallKValueImputer.addAsBuiltWallUValue(SystemBuild, sheet.getRow(92).getCell(1).getNumericCellValue());
		wallKValueImputer.addAsBuiltWallUValue(MetalFrame, sheet.getRow(93).getCell(1).getNumericCellValue());

		//SolidBrick
		wallKValueImputer.addInsulatedWallType(SolidBrick, WallInsulationType.External, sheet.getRow(96).getCell(1).getNumericCellValue());
		wallKValueImputer.addInsulatedWallType(SolidBrick, WallInsulationType.Internal, sheet.getRow(97).getCell(1).getNumericCellValue());
		
		//Cob
		wallKValueImputer.addInsulatedWallType(Cob, WallInsulationType.External, sheet.getRow(100).getCell(1).getNumericCellValue());
		wallKValueImputer.addInsulatedWallType(Cob, WallInsulationType.Internal, sheet.getRow(101).getCell(1).getNumericCellValue());
		
		//SystemBuild
		wallKValueImputer.addInsulatedWallType(SystemBuild, WallInsulationType.External, sheet.getRow(104).getCell(1).getNumericCellValue());
		wallKValueImputer.addInsulatedWallType(SystemBuild, WallInsulationType.Internal, sheet.getRow(105).getCell(1).getNumericCellValue());
		
		wallPropertyTables.setWallKValueImputer(wallKValueImputer);
	}
	
	void buildWallUValueTables(final XSSFWorkbook workbook, final IWallPropertyTables wallPropertyTables, final XSSFSheet sheet){
		final WallUValueImputer wallUValueImputer = new WallUValueImputer(false);
		
		addWallUValueByAgeBand(sheet.getRow(32),wallUValueImputer,GraniteOrWhinstone,0d,false, 
				new SAPAgeBandValue.Band[] {A, B, C, D, E, F, G});
		addWallUValueByAgeBand(sheet.getRow(33),wallUValueImputer,Sandstone,0d,false, 
				new SAPAgeBandValue.Band[] {A, B, C, D, E, F, G});
		
		//BrickInsulated
		addWallUValueByAgeBand(sheet.getRow(36),wallUValueImputer,SolidBrick,0d,false);
		addWallUValueByAgeBand(sheet.getRow(37),wallUValueImputer,SolidBrick,50d,false);
		addWallUValueByAgeBand(sheet.getRow(38),wallUValueImputer,SolidBrick,100d,false);
		addWallUValueByAgeBand(sheet.getRow(39),wallUValueImputer,SolidBrick,150d,false);
		
		//CobInsulated
		addWallUValueByAgeBand(sheet.getRow(42),wallUValueImputer,Cob,0d,false);
		addWallUValueByAgeBand(sheet.getRow(43),wallUValueImputer,Cob,50d,false);
		addWallUValueByAgeBand(sheet.getRow(44),wallUValueImputer,Cob,100d,false);
		addWallUValueByAgeBand(sheet.getRow(45),wallUValueImputer,Cob,150d,false);
		
		//Unfilled Cavity
		addWallUValueByAgeBand(sheet.getRow(48),wallUValueImputer,Cavity,0,false);
		addWallUValueByAgeBand(sheet.getRow(49),wallUValueImputer,Cavity,100,false);
		
		//Filled Cavity
		addWallUValueByAgeBand(sheet.getRow(52),wallUValueImputer,Cavity,0d,true);
		addWallUValueByAgeBand(sheet.getRow(53),wallUValueImputer,Cavity,50d,true);
		addWallUValueByAgeBand(sheet.getRow(54),wallUValueImputer,Cavity,100d,true);
		addWallUValueByAgeBand(sheet.getRow(55),wallUValueImputer,Cavity,150d,true);
		
		//Timber frame
		addWallUValueByAgeBand(sheet.getRow(58),wallUValueImputer,TimberFrame,0,false);
		addWallUValueByAgeBand(sheet.getRow(59),wallUValueImputer,TimberFrame,100,false);
		
		//Metal Frame
		addWallUValueByAgeBand(sheet.getRow(67),wallUValueImputer,MetalFrame,0,false);
		
		//SystemBuild
		//Filled Cavity
		addWallUValueByAgeBand(sheet.getRow(62),wallUValueImputer,SystemBuild,0d,true);
		addWallUValueByAgeBand(sheet.getRow(63),wallUValueImputer,SystemBuild,50d,true);
		addWallUValueByAgeBand(sheet.getRow(64),wallUValueImputer,SystemBuild,100d,true);
		addWallUValueByAgeBand(sheet.getRow(65),wallUValueImputer,SystemBuild,150d,true);
		
		
		//coefficients
		wallUValueImputer.setSandstoneCoefficient(sheet.getRow(69).getCell(1).getNumericCellValue());
		wallUValueImputer.setSandstoneConstant(sheet.getRow(70).getCell(1).getNumericCellValue());
		wallUValueImputer.setGraniteConstant(sheet.getRow(71).getCell(1).getNumericCellValue());
		wallUValueImputer.setGraniteCoefficient(sheet.getRow(72).getCell(1).getNumericCellValue());
		
		wallPropertyTables.setWallUValueImputer(wallUValueImputer);
	}
	
	void buildWallThicknessTables(final XSSFWorkbook workbook, final IWallPropertyTables wallPropertyTables, final XSSFSheet sheet){
		final WallThicknessImputer wallThicknessImputer = new WallThicknessImputer(false);
		//TODO: Could make this a little smaller by looping through allowed wall types...
		//Solid
		addWallThicknessByAgeBand(WallConstructionType.Sandstone, false, sheet.getRow(3), wallThicknessImputer);
		addWallThicknessByAgeBand(WallConstructionType.Sandstone, true, sheet.getRow(4), wallThicknessImputer);
		//Brick
		addWallThicknessByAgeBand(WallConstructionType.SolidBrick, false, sheet.getRow(7), wallThicknessImputer);
		addWallThicknessByAgeBand(WallConstructionType.SolidBrick, true, sheet.getRow(8), wallThicknessImputer);
		//Cavity
		addWallThicknessByAgeBand(WallConstructionType.Cavity, false, sheet.getRow(11), wallThicknessImputer);
		addWallThicknessByAgeBand(WallConstructionType.Cavity, true, sheet.getRow(12), wallThicknessImputer);
		//Timber Frame
		addWallThicknessByAgeBand(WallConstructionType.TimberFrame, false, sheet.getRow(15), wallThicknessImputer);
		addWallThicknessByAgeBand(WallConstructionType.TimberFrame, true, sheet.getRow(16), wallThicknessImputer);
		//Cob
		addWallThicknessByAgeBand(WallConstructionType.Cob, false, sheet.getRow(19), wallThicknessImputer);
		addWallThicknessByAgeBand(WallConstructionType.Cob, true, sheet.getRow(20), wallThicknessImputer);
		//System
		addWallThicknessByAgeBand(WallConstructionType.SystemBuild, false, sheet.getRow(23), wallThicknessImputer);
		addWallThicknessByAgeBand(WallConstructionType.SystemBuild, true, sheet.getRow(24), wallThicknessImputer);
		//Metal Frame
		addWallThicknessByAgeBand(WallConstructionType.MetalFrame, false, sheet.getRow(27), wallThicknessImputer);
		addWallThicknessByAgeBand(WallConstructionType.MetalFrame, true, sheet.getRow(28), wallThicknessImputer);
		
		wallPropertyTables.setWallThicknessImputer(wallThicknessImputer);
	}
	
	private void addWallUValueByAgeBand(final XSSFRow row, final WallUValueImputer wallUValueImputer,
			final WallConstructionType wallType, final double insulationThickness, final boolean filledCavity, final SAPAgeBandValue.Band[] sapAgeBands){
		
		double uValue;
		for(final SAPAgeBandValue.Band ageBandValue : sapAgeBands){
            if (ageBandValue == SAPAgeBandValue.Band.L) continue;
			uValue = row.getCell(ageBandValue.ordinal() + 1).getNumericCellValue();
			wallUValueImputer.addWallUValue(wallType, ageBandValue, insulationThickness, uValue, filledCavity);
		}
	}
	
	private void addWallUValueByAgeBand(final XSSFRow row, final WallUValueImputer wallUValueImputer,
			final WallConstructionType wallType, final double insulationThickness, final boolean filledCavity){
		addWallUValueByAgeBand(row, wallUValueImputer, wallType, insulationThickness, filledCavity, SAPAgeBandValue.Band.values());
	}
	
	private void addWallThicknessByAgeBand(final WallConstructionType wallType, final boolean insulatated, final XSSFRow row, final WallThicknessImputer wallThicknessImputer){
		double thickness;
		for(final SAPAgeBandValue.Band ageBandValue : SAPAgeBandValue.Band.values()){
            if (ageBandValue == SAPAgeBandValue.Band.L) continue;
			thickness = row.getCell(ageBandValue.ordinal() + 1).getNumericCellValue();
			wallThicknessImputer.addWallThickness(wallType, insulatated, thickness, ageBandValue);
		}
	}
		
	/**
	 * Uses sheet: Walls</br>
	 * Rows: 75 and 76</br>
	 * Cells: 1</br>
	 * 
	 * @param workbook
	 * @param wallPropertyTables
	 */
	protected void buildInfiltrationTables(final XSSFWorkbook workbook, final IWallPropertyTables wallPropertyTables, final XSSFSheet sheet){
		final WallInfiltrationImputer infiltrationImputer = new WallInfiltrationImputer(false);
		
		infiltrationImputer.setSteelOrTimberFrameInfiltration(sheet.getRow(75).getCell(1).getNumericCellValue());
		infiltrationImputer.setOtherWallInfiltration(sheet.getRow(76).getCell(1).getNumericCellValue());
		
		wallPropertyTables.setWallInfiltrationImputer(infiltrationImputer);
	}
}
