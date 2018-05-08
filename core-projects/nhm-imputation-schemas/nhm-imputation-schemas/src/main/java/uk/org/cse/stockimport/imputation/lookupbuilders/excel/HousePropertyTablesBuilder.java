package uk.org.cse.stockimport.imputation.lookupbuilders.excel;

import org.apache.commons.lang3.Range;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import uk.org.cse.stockimport.imputation.house.HousePropertyTables;
import uk.org.cse.stockimport.imputation.house.IHousePropertyTables;
import uk.org.cse.stockimport.imputation.lookupbuilders.ILookUpTableBuilder;

public class HousePropertyTablesBuilder implements ILookUpTableBuilder<IHousePropertyTables, XSSFWorkbook> {

    /* Static values for reading in general house property tables */
    public final String generalSheetName = "General";
    public final Range<Integer> livingAreaRowRange = Range.between(2, 15);
    public final int defaultFractionRow = 17;

    public IHousePropertyTables buildTables(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheet(generalSheetName);
        HousePropertyTables housePropertyTables = new HousePropertyTables();

        buildLivingAreaFractionTable(housePropertyTables, sheet);
        buildDefaultFraction(housePropertyTables, sheet);

        return housePropertyTables;
    }

    protected void buildLivingAreaFractionTable(
            final HousePropertyTables housePropertyTables, XSSFSheet sheet) {
        XSSFRow row;
        for (int ct = livingAreaRowRange.getMinimum(); ct <= livingAreaRowRange
                .getMaximum(); ct++) {
            row = sheet.getRow(ct);
            ((HousePropertyTables) housePropertyTables).addLivingAreaFraction(
                    Integer.valueOf(row.getCell(0).getRawValue()),
                    Double.valueOf(row.getCell(1).getRawValue()));
        }
    }

    protected void buildDefaultFraction(
            final HousePropertyTables housePropertyTables, XSSFSheet sheet) {
        XSSFRow row;
        row = sheet.getRow(defaultFractionRow);
        ((HousePropertyTables) housePropertyTables).setDefaultFraction(Double
                .parseDouble(row.getCell(1).getRawValue()));
    }
}
