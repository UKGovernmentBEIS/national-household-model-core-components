package uk.org.cse.stockimport.imputation.loookupbuilders.excel;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;
import uk.org.cse.stockimport.imputation.ImputationSchema;
import uk.org.cse.stockimport.imputation.floors.IFloorPropertyTables;
import uk.org.cse.stockimport.imputation.lookupbuilders.excel.FloorPropertyTableBuilder;

public class CreationOFloorImputationDataFromExcelTest extends AbsImputationFromExcelTest {
	
	final ImputationSchema imputationSchema = new ImputationSchema("123123");
	FloorPropertyTableBuilder builder = new FloorPropertyTableBuilder();
	
	@Before
	public void initialiseTests() throws Exception {
		imputationSchema.setFloorPropertyTables(builder.buildTables(getExcelWorkBook()));
	}

	@Test
	public void FloorPropertyTableCreated() throws Exception {
		final IFloorPropertyTables floorPropertyTables = imputationSchema.getFloorPropertyTables();
		assertThat("Floor property table", floorPropertyTables, is(notNullValue()));
	}
	
	@Test
	public void InsulationThicknessValuesByAgeBandFromFloorPropertyTable() throws Exception {
		final double[] ageBand = imputationSchema.getFloorPropertyTables().getEnglandInsulationBySapAgeBand();
		assertThat("no age band array returned", ageBand, is(notNullValue()));
		assertThat("size of age band array", ageBand.length, equalTo(12));
		assertThat("age band(K) thickness", ageBand[10], equalTo(100d));
		assertThat("age band(I) thickness", ageBand[8], equalTo(25d));
	}
	
	@Test
	public void SapAgeBandForSuspendedTimberSetInFloorPropertyTable(){
		final Band ageBandValue = imputationSchema.getFloorPropertyTables().getLastAgeBandForSuspendedTimber();
		assertThat("SAPAgeBand for last suspended timber", ageBandValue, is(notNullValue()));
		assertThat("Suspended timber age band", ageBandValue, equalTo(SAPAgeBandValue.Band.B));
	}
	
	@Test
	public void RsiSetInFloorPropertyTable() throws Exception {
		final double expectedValue = imputationSchema.getFloorPropertyTables().getRsi();
		assertThat("Rsi", expectedValue, equalTo(0.17d));
	}
	
	@Test
	public void RseSetInFloorPropertyTable() throws Exception {
		final double expectedValue = imputationSchema.getFloorPropertyTables().getRse();
		assertThat("Rss", expectedValue, equalTo(0.04d));
	}
	
	@Test
	public void soilThermalConductivitySetInFloorPropertyTable() throws Exception {
		final double expectedValue = imputationSchema.getFloorPropertyTables().getSoilThermalConductivity();
		assertThat("soilThermalConductivity", expectedValue, equalTo(1.5d));
	}
	
	@Test
	public void openingsPerMeterOfExposedPerimeterSetInFloorPropertyTable() throws Exception {
		final double expectedValue = imputationSchema.getFloorPropertyTables().getOpeningsPerMeterOfExposedPerimeter();
		assertThat("openingsPerMeterOfExposedPerimeter", expectedValue, equalTo(0.003d));
	}
	
	@Test
	public void deckThermalResistanceSetInFloorPropertyTable() throws Exception {
		final double expectedValue = imputationSchema.getFloorPropertyTables().getDeckThermalResistance();
		assertThat("deckThermalResistance", expectedValue, equalTo(0.2d));
	}
	
	@Test
	public void heightAboveGroundLevelSetInFloorPropertyTable() throws Exception {
		final double expectedValue = imputationSchema.getFloorPropertyTables().getHeightAboveGroundLevel();
		assertThat("heightAboveGroundLevel", expectedValue, equalTo(0.3d));
	}
	
	@Test
	public void uValueOfWallsToUnderfloorSpaceSetInFloorPropertyTable() throws Exception {
		final double expectedValue = imputationSchema.getFloorPropertyTables().getUValueOfWallsToUnderfloorSpace();
		assertThat("heightAboveGroundLevel", expectedValue, equalTo(1.5d));
	}
	
	@Test
	public void averageWindSpeedAt10mSetInFloorPropertyTable() throws Exception {
		final double expectedValue = imputationSchema.getFloorPropertyTables().getAverageWindSpeedAt10m();
		assertThat("averageWindSpeedAt10m", expectedValue, equalTo(5d));
	}
	
	@Test
	public void windShieldingFactorSetInFloorPropertyTable() throws Exception {
		final double expectedValue = imputationSchema.getFloorPropertyTables().getWindShieldingFactor();
		assertThat("windShieldingFactor", expectedValue, equalTo(0.05d));
	}
	
	@Test
	public void floorInsulationConductivitySetInFloorPropertyTable() throws Exception {
		final double expectedValue = imputationSchema.getFloorPropertyTables().getFloorInsulationConductivity();
		assertThat("floorInsulationConductivity", expectedValue, equalTo(0.035d));
	}

	@Test
	public void FloorUValuesSetInFoorPropertyTable() throws Exception {
		final double[][] exposedFloorUValueBySapAgeBand = imputationSchema.getFloorPropertyTables().getExposedFloorUValueBySapAgeBand();
		assertThat("floor property table", exposedFloorUValueBySapAgeBand, is(notNullValue()));
		assertThat("number of floor types", exposedFloorUValueBySapAgeBand.length, equalTo(2));
		assertThat(exposedFloorUValueBySapAgeBand[0][0], equalTo(1.2d));
		assertThat(exposedFloorUValueBySapAgeBand[1][3], equalTo(0.5d));
		assertThat(exposedFloorUValueBySapAgeBand[1][10], equalTo(0.22d));
	}
}
