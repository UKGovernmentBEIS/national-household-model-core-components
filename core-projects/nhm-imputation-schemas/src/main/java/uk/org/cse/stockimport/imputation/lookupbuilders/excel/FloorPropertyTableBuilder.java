package uk.org.cse.stockimport.imputation.lookupbuilders.excel;

import org.apache.commons.lang3.Range;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import uk.org.cse.nhm.hom.types.SAPAgeBandValue;
import uk.org.cse.stockimport.imputation.floors.FloorPropertyTables;
import uk.org.cse.stockimport.imputation.floors.IFloorPropertyTables;

public class FloorPropertyTableBuilder {

	/* Static values for reading in floor property tables */
	public final String floorSheetName = "Floors";
	public final Range<Integer> ageBandInsValueRowRange = Range.between(2, 12);
	public final int timberAgeBandRow = 15;
	
	/**
	 * @param workbook
	 * @return
	 * @since 3.0
	 */
	public IFloorPropertyTables buildTables(final XSSFWorkbook workbook){
		final IFloorPropertyTables floorPropertyTables = new FloorPropertyTables();
		
		//Ground k values
		final XSSFSheet sheet = workbook.getSheet(floorSheetName);
		XSSFRow row;
		
		//Insulation thickness by age band
		final double[] ageBand = new double[SAPAgeBandValue.Band.values().length];
		int ageBandCt = 0;
		for (int ct = ageBandInsValueRowRange.getMinimum(); ct <= ageBandInsValueRowRange.getMaximum(); ct++) {
			row = sheet.getRow(ct);
			ageBand[ageBandCt] = row.getCell(1).getNumericCellValue();
			ageBandCt++;
		}
		((FloorPropertyTables)floorPropertyTables).setEnglandInsulationBySapAgeBand(ageBand);
		
		//Suspended timber age-band
		row = sheet.getRow(timberAgeBandRow);
		((FloorPropertyTables)floorPropertyTables).setLastAgeBandForSuspendedTimber(SAPAgeBandValue.Band.valueOf(row.getCell(1).getStringCellValue()));
		
		/* U-values */
		((FloorPropertyTables)floorPropertyTables).setRsi(sheet.getRow(28).getCell(1).getNumericCellValue());
		((FloorPropertyTables)floorPropertyTables).setRse(sheet.getRow(29).getCell(1).getNumericCellValue());
		((FloorPropertyTables)floorPropertyTables).setSoilThermalConductivity(sheet.getRow(30).getCell(1).getNumericCellValue());
		((FloorPropertyTables)floorPropertyTables).setDeckThermalResistance(sheet.getRow(31).getCell(1).getNumericCellValue());
		((FloorPropertyTables)floorPropertyTables).setOpeningsPerMeterOfExposedPerimeter(sheet.getRow(32).getCell(1).getNumericCellValue());
		((FloorPropertyTables)floorPropertyTables).setHeightAboveGroundLevel(sheet.getRow(33).getCell(1).getNumericCellValue());
		((FloorPropertyTables)floorPropertyTables).setuValueOfWallsToUnderfloorSpace(sheet.getRow(34).getCell(1).getNumericCellValue());
		((FloorPropertyTables)floorPropertyTables).setAverageWindSpeedAt10m(sheet.getRow(35).getCell(1).getNumericCellValue());
		((FloorPropertyTables)floorPropertyTables).setWindShieldingFactor(sheet.getRow(36).getCell(1).getNumericCellValue());
		((FloorPropertyTables)floorPropertyTables).setFloorInsulationConductivity(sheet.getRow(37).getCell(1).getNumericCellValue());
		
		buildExposedFloorValuesBySapAgeBand(sheet, floorPropertyTables);
		
		return floorPropertyTables;
	}
	
	protected void buildExposedFloorValuesBySapAgeBand(final XSSFSheet sheet, final IFloorPropertyTables floorPropertyTables){
		
		XSSFRow row;
		boolean isInsulated = false;
		for (int ct = 41; ct <= 42; ct++) {
			row = sheet.getRow(ct);
			
			for (final SAPAgeBandValue.Band ageBandValue : SAPAgeBandValue.Band.values()) {
                if (ageBandValue == SAPAgeBandValue.Band.L) continue;
				((FloorPropertyTables)floorPropertyTables).addUValueForExposedFloor(
						isInsulated, ageBandValue, row.getCell(ageBandValue.ordinal()+1).getNumericCellValue());
			}
			isInsulated = true;
		}
	}
}
