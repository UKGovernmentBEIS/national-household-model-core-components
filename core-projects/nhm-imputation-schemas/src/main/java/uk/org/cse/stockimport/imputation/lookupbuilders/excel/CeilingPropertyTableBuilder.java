package uk.org.cse.stockimport.imputation.lookupbuilders.excel;

import org.apache.commons.lang3.Range;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import uk.org.cse.nhm.energycalculator.api.types.RoofConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;
import uk.org.cse.stockimport.imputation.ceilings.CeilingUValueTables;
import uk.org.cse.stockimport.imputation.ceilings.ICeilingUValueTables;
import uk.org.cse.stockimport.imputation.lookupbuilders.ILookUpTableBuilder;

public class CeilingPropertyTableBuilder implements ILookUpTableBuilder<ICeilingUValueTables, XSSFWorkbook> {

    /* Static values for reading in roofs */
    public final String roofSheetName = "Roofs";
    public final Range<Integer> pitchRowRange = Range.between(2, 11);
    public final Range<Integer> thatchRowRange = Range.between(15, 24);
    public final Range<Integer> unknowRowRange = Range.between(27, 29);
    public final Range<Integer> unknowRowRoomInRoofRange = Range.between(32, 33);
    public final int partyCeilingKValue = 35;
    public final int roofKValue = 36;

    /**
     * @param workbook
     * @return
     * @since 3.0
     */
    @Override
    public ICeilingUValueTables buildTables(final XSSFWorkbook workbook) {
        final XSSFSheet sheet = workbook.getSheet(roofSheetName);

        final ICeilingUValueTables ceilingUValueTables = new CeilingUValueTables();

        // Build insulatedPitchedUValues (rows 3 to 12)
        XSSFRow row;
        for (int ct = pitchRowRange.getMinimum(); ct <= pitchRowRange.getMaximum(); ct++) {
            row = sheet.getRow(ct);
            ((CeilingUValueTables) ceilingUValueTables)
                    .addInsulatedPitchedUValue(
                            Double.valueOf(row.getCell(0).getRawValue()),
                            Double.valueOf(row.getCell(1).getRawValue()));
        }

        // Build insulatedThatchUValues (rows 3 to 12)
        for (int ct = thatchRowRange.getMinimum(); ct <= thatchRowRange.getMaximum(); ct++) {
            row = sheet.getRow(ct);
            ((CeilingUValueTables) ceilingUValueTables)
                    .addInsulatedThatchedUValue(
                            Double.valueOf(row.getCell(0).getRawValue()),
                            Double.valueOf(row.getCell(1).getRawValue()));
        }

        // Build unkownValuesWithoutRoomInRoof
        RoofConstructionType roofConstructionType;
        int roofType = 0;
        for (int ct = unknowRowRange.getMinimum(); ct <= unknowRowRange.getMaximum(); ct++) {
            row = sheet.getRow(ct);
            roofConstructionType = RoofConstructionType.values()[roofType];

            for (final Band ageBandValue : SAPAgeBandValue.Band.values()) {
                if (ageBandValue == SAPAgeBandValue.Band.L) {
                    continue;
                }
                ((CeilingUValueTables) ceilingUValueTables)
                        .addUValuesForUnkownInsulationThickness(
                                roofConstructionType, ageBandValue, Double
                                        .valueOf(row.getCell(
                                                1 + ageBandValue.ordinal())
                                                .getRawValue()));
            }
            roofType++;
        }

        //Build unkownValuesWithoutRoomInRoof
        roofType = 0;
        for (int ct = unknowRowRoomInRoofRange.getMinimum(); ct <= unknowRowRoomInRoofRange.getMaximum(); ct++) {
            row = sheet.getRow(ct);
            roofConstructionType = RoofConstructionType.values()[roofType];

            for (final Band ageBandValue : SAPAgeBandValue.Band.values()) {
                if (ageBandValue == SAPAgeBandValue.Band.L) {
                    continue;
                }
                ((CeilingUValueTables) ceilingUValueTables)
                        .addUnknownValuesByRoofTypeAndAgeBandWithRoomInRoof(
                                roofConstructionType, ageBandValue, Double
                                        .valueOf(row.getCell(
                                                1 + ageBandValue.ordinal())
                                                .getRawValue()));
            }
            roofType++;
        }

        return ceilingUValueTables;
    }

}
