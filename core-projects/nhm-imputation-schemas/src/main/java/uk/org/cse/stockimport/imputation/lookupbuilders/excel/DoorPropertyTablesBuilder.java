package uk.org.cse.stockimport.imputation.lookupbuilders.excel;

import static uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band.*;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import uk.org.cse.nhm.energycalculator.api.types.DoorType;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue;
import uk.org.cse.stockimport.imputation.apertures.doors.DoorPropertyImputer;
import uk.org.cse.stockimport.imputation.apertures.doors.IDoorPropertyImputer;
import uk.org.cse.stockimport.imputation.lookupbuilders.ILookUpTableBuilder;

public class DoorPropertyTablesBuilder implements ILookUpTableBuilder<IDoorPropertyImputer, XSSFWorkbook> {

    public static final String SHEETNAME = "Doors";

    @Override
    public IDoorPropertyImputer buildTables(final XSSFWorkbook workbook) {
        final IDoorPropertyImputer doorPropertyImputer = new DoorPropertyImputer();
        final XSSFSheet sheet = workbook.getSheet(SHEETNAME);

        buildWallDoorArea(workbook, doorPropertyImputer, sheet);
        buildWallUValues(workbook, doorPropertyImputer, sheet);

        return doorPropertyImputer;
    }

    protected void buildWallDoorArea(final XSSFWorkbook workbook, final IDoorPropertyImputer doorPropertyImputer, final XSSFSheet sheet) {
        final XSSFRow row = sheet.getRow(2);
        ((DoorPropertyImputer) doorPropertyImputer).addDoorArea(DoorType.Glazed, row.getCell(1).getNumericCellValue());
        ((DoorPropertyImputer) doorPropertyImputer).addDoorArea(DoorType.Solid, row.getCell(2).getNumericCellValue());
    }

    protected void buildWallUValues(final XSSFWorkbook workbook, final IDoorPropertyImputer doorPropertyImputer, final XSSFSheet sheet) {
        int rowNum = 6;
        XSSFRow row;
        for (final DoorType doorType : DoorType.values()) {
            row = sheet.getRow(rowNum);

            int cellNum = 1;
            for (final SAPAgeBandValue.Band ageBandValue
                    : new SAPAgeBandValue.Band[]{A, B, C, D, E, F, G, H, I, J}) {
                ((DoorPropertyImputer) doorPropertyImputer).addDoorUValue(doorType, ageBandValue, row.getCell(cellNum).getNumericCellValue());
                cellNum++;
            }
            ((DoorPropertyImputer) doorPropertyImputer).addDoorUValue(doorType, SAPAgeBandValue.Band.K, row.getCell(cellNum).getNumericCellValue());

            rowNum++;
        }
    }
}
