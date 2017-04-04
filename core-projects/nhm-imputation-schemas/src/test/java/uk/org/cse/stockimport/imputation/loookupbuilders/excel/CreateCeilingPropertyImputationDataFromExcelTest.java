package uk.org.cse.stockimport.imputation.loookupbuilders.excel;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

import uk.org.cse.stockimport.imputation.ImputationSchema;
import uk.org.cse.stockimport.imputation.lookupbuilders.excel.CeilingPropertyTableBuilder;

public class CreateCeilingPropertyImputationDataFromExcelTest extends AbsImputationFromExcelTest {
	final ImputationSchema imputationSchema = new ImputationSchema("123123");
	final CeilingPropertyTableBuilder builder = new CeilingPropertyTableBuilder();

	@Before
	public void initialiseTests() throws Exception {
		imputationSchema.setCeilingUValueTables(builder.buildTables(getExcelWorkBook()));
	}

	@Test
	public void UValuesForCeilingsAreAreReadCorrectlyFromExcelSheet() throws FileNotFoundException, IOException{
		final TreeMap<Integer, Double> pitchedValues = imputationSchema.getCeilingUValueTables().getInsulatedPitchedUValues();
		assertNotNull("Should import pitched roof u value table", pitchedValues);
		assertThat("Should import pitched roof u value table", pitchedValues.size(), equalTo(10));
		assertThat("U-value for pitched insulation depth of 0.00", pitchedValues.get(0), equalTo(2.3d));
		assertThat("U-value for pitched insulation depth of 125.00", pitchedValues.get(125), equalTo(0.3d));
		assertThat("U-value for pitched insulation depth of 300.00", pitchedValues.get(300), equalTo(0.1d));

		final TreeMap<Integer, Double> thatchedValues = imputationSchema.getCeilingUValueTables().getInsulatedThatchedUValues();
		assertNotNull("Should import pitched roof u value table", pitchedValues);
		assertThat("Should import thatched  roof u value table", thatchedValues.size(), equalTo(10));
		assertThat("U-value for thatched insulation depth of 0.00", thatchedValues.get(0), equalTo(0.4d));
		assertThat("U-value for thatched insulation depth of 125.00", thatchedValues.get(125), equalTo(0.2d));
		assertThat("U-value for thatched insulation depth of 300.00", thatchedValues.get(50), equalTo(0.3d));

		final double[][] unkownValues = imputationSchema.getCeilingUValueTables().getUnknownValuesByRoofTypeAndAgeBand();
		assertNotNull("Should unkownValues u value table", unkownValues);
		assertThat("unkownValues table length", unkownValues.length, equalTo(3));
		assertThat("unkownValues u value [0][0]", unkownValues[0][0], equalTo(2.3));
		assertThat("unkownValues u value [0][5]", unkownValues[0][5], equalTo(0.68));
		assertThat("unkownValues u value [0][10]", unkownValues[0][10], equalTo(0.16));

		assertThat("unkownValues u value [2][0]", unkownValues[2][0], equalTo(2.3));
		assertThat("unkownValues u value [2][6]", unkownValues[2][6], equalTo(0.4));
		assertThat("unkownValues u value [2][10]", unkownValues[2][10], equalTo(0.25));

		final double[][] unkownValuesRoomInRoof = imputationSchema.getCeilingUValueTables().getUnknownValuesByRoofTypeAndAgeBandWithRoomInRoof();
		assertNotNull("Should unkownValuesRoomInRoof u value table", unkownValues);
		assertThat("unkownValuesRoomInRoof table length", unkownValuesRoomInRoof.length, equalTo(2));

		assertThat("unkownValuesRoomInRoof u value [0][0]", unkownValuesRoomInRoof[0][0], equalTo(2.3));
		assertThat("unkownValuesRoomInRoof u value [0][5]", unkownValuesRoomInRoof[0][5], equalTo(0.8));
		assertThat("unkownValuesRoomInRoof u value [0][10]", unkownValuesRoomInRoof[0][10], equalTo(0.25));

		assertThat("unkownValuesRoomInRoof u value [1][0]", unkownValuesRoomInRoof[1][0], equalTo(0.25));
		assertThat("unkownValuesRoomInRoof u value [1][6]", unkownValuesRoomInRoof[1][6], equalTo(0.25));
		assertThat("unkownValuesRoomInRoof u value [1][10]", unkownValuesRoomInRoof[1][10], equalTo(0.25));
	}
}
