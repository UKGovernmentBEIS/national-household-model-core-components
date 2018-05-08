package uk.org.cse.stockimport.imputation.loookupbuilders.excel;

import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;

import uk.org.cse.stockimport.util.Path;

public abstract class AbsImputationFromExcelTest {

    private final String defaultSchemaFile = Path.file("src", "test", "resources", "default_schema.xlsx");
    private FileInputStream excelFileStream;
    private XSSFWorkbook workBook;

    @Before
    public void openExcelWorkBook() throws Exception {
        excelFileStream = new FileInputStream(defaultSchemaFile);
        workBook = new XSSFWorkbook(excelFileStream);
    }

    @After
    public void closeOpenObjects() throws Exception {
        excelFileStream.close();
    }

    protected XSSFWorkbook getExcelWorkBook() {
        return workBook;
    }
}
