package uk.org.cse.stockimport.imputation.loookupbuilders.excel;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import uk.org.cse.stockimport.imputation.ImputationSchema;
import uk.org.cse.stockimport.imputation.lookupbuilders.excel.HousePropertyTablesBuilder;

public class CreateHousePropertyImputationDataFromExcelTest extends AbsImputationFromExcelTest {
	final ImputationSchema imputationSchema = new ImputationSchema("123123");
	final HousePropertyTablesBuilder builder = new HousePropertyTablesBuilder();
	
	@Before
	public void initialiseTests() throws Exception {
		imputationSchema.setHousePropertyTables(builder.buildTables(getExcelWorkBook()));
	}
	
	@Test
	public void LivingAreaFractionReadCorrectlyFromExcelSheet() throws Exception {
		Map<Integer,Double> livingAreaFractions = imputationSchema.getHousePropertyTables().getlivingAreaFactions();
		assertNotNull("Should import living area fractions", livingAreaFractions);
		assertThat("Living area fraction map size", livingAreaFractions.size(), equalTo(14));
		assertThat("Living fraction for 1 room", livingAreaFractions.get(1), equalTo(0.75d));
		assertThat("Living fraction for 10 rooms", livingAreaFractions.get(10), equalTo(0.12d));
		assertThat("Living fraction for 14 rooms", livingAreaFractions.get(14), equalTo(0.09d));
		
		double defaultFraction = imputationSchema.getHousePropertyTables().getDefaultFraction();
		assertThat("Default factor", defaultFraction, equalTo(0.09d));
	}
}
