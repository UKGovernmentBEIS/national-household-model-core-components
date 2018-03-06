package uk.org.cse.stockimport.imputation.lookupbuilders.excel;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.WindowGlazingAirGap;
import uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType;
import uk.org.cse.nhm.energycalculator.impl.IWindowUValues;
import uk.org.cse.stockimport.imputation.apertures.windows.IWindowPropertyTables;
import uk.org.cse.stockimport.imputation.apertures.windows.TransmittanceFactors;
import uk.org.cse.stockimport.imputation.apertures.windows.WindowPropertyTables;
import uk.org.cse.stockimport.imputation.lookupbuilders.ILookUpTableBuilder;

public class WindowPropertyTablesBuilder implements ILookUpTableBuilder<IWindowPropertyTables, XSSFWorkbook> {
	
	/* Static values for reading in general house property tables */
	public final String generalSheetName = "Windows";
	
	public IWindowPropertyTables buildTables(XSSFWorkbook workbook){
		IWindowPropertyTables windowPropertyTables = new WindowPropertyTables();
		XSSFSheet sheet = workbook.getSheet(generalSheetName);
		
		buildFrameFactors(windowPropertyTables, sheet);
		buildUValues(windowPropertyTables, sheet);
		buildTransmittenceFactors(windowPropertyTables, sheet);
		
		return windowPropertyTables;
	}
	
	protected void buildFrameFactors(final IWindowPropertyTables windowPropertyTables, final XSSFSheet sheet){
		windowPropertyTables.getFrameFactors().setFrameFactor(FrameType.Wood,sheet.getRow(2).getCell(1).getNumericCellValue());
		windowPropertyTables.getFrameFactors().setFrameFactor(FrameType.Metal,sheet.getRow(3).getCell(1).getNumericCellValue());
		windowPropertyTables.getFrameFactors().setFrameFactor(FrameType.uPVC,sheet.getRow(4).getCell(1).getNumericCellValue());
	}
	
	protected void buildUValues(final IWindowPropertyTables windowPropertyTables, final XSSFSheet sheet){
		IWindowUValues uvalues = windowPropertyTables.getUValues();
		
		uvalues.setCurtainEffectFactor(sheet.getRow(42).getCell(1).getNumericCellValue());
		
		//SingleGlazing
		uvalues.addSingleGlazingUValue(FrameType.Wood, sheet.getRow(9).getCell(1).getNumericCellValue());
		uvalues.addSingleGlazingUValue(FrameType.Metal, sheet.getRow(10).getCell(1).getNumericCellValue());
		uvalues.addSingleGlazingUValue(FrameType.uPVC, sheet.getRow(11).getCell(1).getNumericCellValue());
	
		//SecondaryGlazing
		uvalues.addSecondaryGlazingUValue(FrameType.Wood, sheet.getRow(15).getCell(1).getNumericCellValue());
		uvalues.addSecondaryGlazingUValue(FrameType.uPVC, sheet.getRow(16).getCell(1).getNumericCellValue());
	
		//DoubleGlazing
		uvalues.addDoubleGlazing(FrameType.Wood, WindowInsulationType.Air, sheet.getRow(20).getCell(2).getNumericCellValue(), WindowGlazingAirGap.gapOf6mm);
		uvalues.addDoubleGlazing(FrameType.Metal, WindowInsulationType.Air, sheet.getRow(21).getCell(2).getNumericCellValue(), WindowGlazingAirGap.gapOf6mm);
		uvalues.addDoubleGlazing(FrameType.uPVC, WindowInsulationType.Air, sheet.getRow(22).getCell(2).getNumericCellValue(), WindowGlazingAirGap.gapOf6mm);
		
		uvalues.addDoubleGlazing(FrameType.Wood, WindowInsulationType.LowEHardCoat, sheet.getRow(23).getCell(2).getNumericCellValue(), WindowGlazingAirGap.gapOf6mm);
		uvalues.addDoubleGlazing(FrameType.Metal, WindowInsulationType.LowEHardCoat, sheet.getRow(24).getCell(2).getNumericCellValue(), WindowGlazingAirGap.gapOf6mm);
		uvalues.addDoubleGlazing(FrameType.uPVC, WindowInsulationType.LowEHardCoat, sheet.getRow(25).getCell(2).getNumericCellValue(), WindowGlazingAirGap.gapOf6mm);
		
		uvalues.addDoubleGlazing(FrameType.Wood, WindowInsulationType.LowESoftCoat, sheet.getRow(26).getCell(2).getNumericCellValue(), WindowGlazingAirGap.gapOf6mm);
		uvalues.addDoubleGlazing(FrameType.Metal, WindowInsulationType.LowESoftCoat, sheet.getRow(27).getCell(2).getNumericCellValue(), WindowGlazingAirGap.gapOf6mm);
		uvalues.addDoubleGlazing(FrameType.uPVC, WindowInsulationType.LowESoftCoat, sheet.getRow(28).getCell(2).getNumericCellValue(), WindowGlazingAirGap.gapOf6mm);
		
		//TripleGlazing
		uvalues.addTripleGlazing(FrameType.Wood, WindowInsulationType.Air, sheet.getRow(32).getCell(2).getNumericCellValue(), WindowGlazingAirGap.gapOf6mm);
		uvalues.addTripleGlazing(FrameType.Metal, WindowInsulationType.Air, sheet.getRow(33).getCell(2).getNumericCellValue(), WindowGlazingAirGap.gapOf6mm);
		uvalues.addTripleGlazing(FrameType.uPVC, WindowInsulationType.Air, sheet.getRow(34).getCell(2).getNumericCellValue(), WindowGlazingAirGap.gapOf6mm);
		
		uvalues.addTripleGlazing(FrameType.Wood, WindowInsulationType.LowEHardCoat, sheet.getRow(35).getCell(2).getNumericCellValue(), WindowGlazingAirGap.gapOf6mm);
		uvalues.addTripleGlazing(FrameType.Metal, WindowInsulationType.LowEHardCoat, sheet.getRow(36).getCell(2).getNumericCellValue(), WindowGlazingAirGap.gapOf6mm);
		uvalues.addTripleGlazing(FrameType.uPVC, WindowInsulationType.LowEHardCoat, sheet.getRow(37).getCell(2).getNumericCellValue(), WindowGlazingAirGap.gapOf6mm);
		
		uvalues.addTripleGlazing(FrameType.Wood, WindowInsulationType.LowESoftCoat, sheet.getRow(38).getCell(2).getNumericCellValue(), WindowGlazingAirGap.gapOf6mm);
		uvalues.addTripleGlazing(FrameType.Metal, WindowInsulationType.LowESoftCoat, sheet.getRow(39).getCell(2).getNumericCellValue(), WindowGlazingAirGap.gapOf6mm);
		uvalues.addTripleGlazing(FrameType.uPVC, WindowInsulationType.LowESoftCoat, sheet.getRow(40).getCell(2).getNumericCellValue(), WindowGlazingAirGap.gapOf6mm);
	}
	
	protected void buildTransmittenceFactors(final IWindowPropertyTables windowPropertyTables, final XSSFSheet sheet){
		TransmittanceFactors transmittanceFactors = (TransmittanceFactors) windowPropertyTables.getTransmittanceFactors();
		
		transmittanceFactors.setSingleGlazingGainsTransmittance(sheet.getRow(1).getCell(5).getNumericCellValue());
		transmittanceFactors.setSingleGlazingLightTransmittance(sheet.getRow(2).getCell(5).getNumericCellValue());
		transmittanceFactors.setSecondaryGlazingGainsTransmittance(sheet.getRow(3).getCell(5).getNumericCellValue());
		transmittanceFactors.setSecondaryGlazingLightTransmittance(sheet.getRow(4).getCell(5).getNumericCellValue());
		
		int row = 8;
		//Double glazing gains
		transmittanceFactors.addDoubleGlazingGainsTransimittance(WindowInsulationType.Air, sheet.getRow(row++).getCell(5).getNumericCellValue());
		transmittanceFactors.addDoubleGlazingGainsTransimittance(WindowInsulationType.LowESoftCoat, sheet.getRow(row++).getCell(5).getNumericCellValue());
		transmittanceFactors.addDoubleGlazingGainsTransimittance(WindowInsulationType.LowEHardCoat, sheet.getRow(row++).getCell(5).getNumericCellValue());
		
		//Triple glazing gains
		row = 14;
		transmittanceFactors.addTripleGlazingGainsTransimittance(WindowInsulationType.Air, sheet.getRow(row++).getCell(5).getNumericCellValue());
		transmittanceFactors.addTripleGlazingGainsTransimittance(WindowInsulationType.LowESoftCoat, sheet.getRow(row++).getCell(5).getNumericCellValue());
		transmittanceFactors.addTripleGlazingGainsTransimittance(WindowInsulationType.LowEHardCoat, sheet.getRow(row++).getCell(5).getNumericCellValue());
		
		//Double glazing light
		row = 20;
		transmittanceFactors.addDoubleGlazingLightTransimittance(WindowInsulationType.Air, sheet.getRow(row).getCell(5).getNumericCellValue());
		transmittanceFactors.addDoubleGlazingLightTransimittance(WindowInsulationType.LowESoftCoat, sheet.getRow(row).getCell(5).getNumericCellValue());
		transmittanceFactors.addDoubleGlazingLightTransimittance(WindowInsulationType.LowEHardCoat, sheet.getRow(row).getCell(5).getNumericCellValue());
		
		//Triple glazing light
		row = 26;
		transmittanceFactors.addTripleGlazingLightTransimittance(WindowInsulationType.Air, sheet.getRow(row++).getCell(5).getNumericCellValue());
		transmittanceFactors.addTripleGlazingLightTransimittance(WindowInsulationType.LowESoftCoat, sheet.getRow(row++).getCell(5).getNumericCellValue());
		transmittanceFactors.addTripleGlazingLightTransimittance(WindowInsulationType.LowEHardCoat, sheet.getRow(row++).getCell(5).getNumericCellValue());
	}
}
