package uk.org.cse.stockimport.imputation.loookupbuilders.excel;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.types.DoorType;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue;
import uk.org.cse.stockimport.imputation.ImputationSchema;
import uk.org.cse.stockimport.imputation.apertures.doors.IDoorPropertyImputer;
import uk.org.cse.stockimport.imputation.lookupbuilders.excel.DoorPropertyTablesBuilder;

public class CreationOfDoorImputationDataFromExcelTest extends AbsImputationFromExcelTest {

	final ImputationSchema imputationSchema = new ImputationSchema("123123");
	DoorPropertyTablesBuilder builder = new DoorPropertyTablesBuilder();
	
	@Before
	public void initialiseTests() throws Exception {
		imputationSchema.setDoorPropertyImputer(builder.buildTables(getExcelWorkBook()));
	}
	
	@Test
	public void EnsureDoorPropertyImputerIsCreated() throws Exception {
		assertThat("property table", imputationSchema.getDoorPropertyImputer(), is(notNullValue()));
	}

	@Test
	public void DoorAreaShouldMatchThoseInExcelSheet() throws Exception {
		final IDoorPropertyImputer doorPropertyImputer = imputationSchema.getDoorPropertyImputer();
		
		assertThat("glazed door area", doorPropertyImputer.getArea(DoorType.Glazed), equalTo(1.85d));
		assertThat("glazed door area", doorPropertyImputer.getArea(DoorType.Solid), equalTo(1.85d));
	}
	
	@Test
	public void DoorUValuesByAgeBandShouldMAtchThoseInExcelSheet(){
		final IDoorPropertyImputer doorPropertyImputer = imputationSchema.getDoorPropertyImputer();
		
		assertThat("glazed u value age band A", doorPropertyImputer.getUValue(SAPAgeBandValue.Band.A, DoorType.Glazed), equalTo(3.00d));
		assertThat("glazed u value age band K", doorPropertyImputer.getUValue(SAPAgeBandValue.Band.K, DoorType.Glazed), equalTo(2.00d));
		
		assertThat("solid u value age band A", doorPropertyImputer.getUValue(SAPAgeBandValue.Band.A, DoorType.Solid), equalTo(3.00d));
		assertThat("solid u value age band K", doorPropertyImputer.getUValue(SAPAgeBandValue.Band.K, DoorType.Solid), equalTo(2.00d));
	}
}
