package uk.org.cse.stockimport.imputation.loookupbuilders.excel;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.GlazingType;
import uk.org.cse.nhm.energycalculator.api.types.WindowGlazingAirGap;
import uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType;
import uk.org.cse.nhm.energycalculator.impl.IWindowUValues;
import uk.org.cse.stockimport.imputation.ImputationSchema;
import uk.org.cse.stockimport.imputation.apertures.windows.ITransmittanceFactors;
import uk.org.cse.stockimport.imputation.apertures.windows.IWindowFrameFactor;
import uk.org.cse.stockimport.imputation.lookupbuilders.excel.WindowPropertyTablesBuilder;

public class CreationOfWindowImputationDataFromExcelTest extends AbsImputationFromExcelTest {

	final ImputationSchema imputationSchema = new ImputationSchema("123123");
	final WindowPropertyTablesBuilder builder = new WindowPropertyTablesBuilder();
	
	@Before
	public void initialiseTests() throws Exception {
		imputationSchema.setWindowPropertyTables(builder.buildTables(getExcelWorkBook()));
	}

	@Test
	public void EnsureAWindowsPropertiesTableIsCreated() throws Exception {
		assertThat("property table", imputationSchema.getWindowPropertyTables(), is(notNullValue()));
	}
	
	@Test
	public void FrameFactorsAreReadFromExcelAndStoredInTable() throws Exception {
		IWindowFrameFactor frameFactors = imputationSchema.getWindowPropertyTables().getFrameFactors();
		assertThat("Framefactors table", frameFactors, is(notNullValue()));
		assertThat(frameFactors.getFrameFactor(FrameType.Wood), equalTo(2.0d));
	}
	
	@Test
	public void UValuesAreReadFromExcelAndStoredInTable() throws Exception {
		IWindowUValues uValues = imputationSchema.getWindowPropertyTables().getUValues();
		assertThat("uvalues table", uValues, is(notNullValue()));
		
		Assert.assertEquals(4.0, uValues.getUValue(FrameType.Wood, GlazingType.Single, null, WindowGlazingAirGap.gapOf6mm), 0.05);
		Assert.assertEquals(2.2, uValues.getUValue(FrameType.Wood, GlazingType.Secondary, null, WindowGlazingAirGap.gapOf6mm), 0.05);
		Assert.assertEquals(3.2, uValues.getUValue(FrameType.Metal, GlazingType.Double, WindowInsulationType.Air, WindowGlazingAirGap.gapOf6mm), 0.05);
		Assert.assertEquals(2.2, uValues.getUValue(FrameType.Wood, GlazingType.Triple, WindowInsulationType.Air, WindowGlazingAirGap.gapOf6mm), 0.05);
	}
	
	@Test
	public void TransmittenceFactorsAreReadFromExcelAndStoredInTable() throws Exception {
		ITransmittanceFactors transmittenceFactors = imputationSchema.getWindowPropertyTables().getTransmittanceFactors();
		assertThat("transmittenceFactors table", transmittenceFactors, is(notNullValue()));
	}
	
	@Test
	public void testLightTransmittance() {
		final ITransmittanceFactors factors = imputationSchema.getWindowPropertyTables().getTransmittanceFactors();
		Assert.assertEquals(0.9, factors.getLightTransmittance(GlazingType.Single, null), 0.01);
		Assert.assertEquals(0.8, factors.getLightTransmittance(GlazingType.Double, WindowInsulationType.Air), 0.01);
		Assert.assertEquals(0.8, factors.getLightTransmittance(GlazingType.Double, WindowInsulationType.LowEHardCoat), 0.01);
		Assert.assertEquals(0.8, factors.getLightTransmittance(GlazingType.Double, WindowInsulationType.LowESoftCoat), 0.01);
		Assert.assertEquals(0.8, factors.getLightTransmittance(GlazingType.Secondary, null), 0.01);
		Assert.assertEquals(0.7, factors.getLightTransmittance(GlazingType.Triple, WindowInsulationType.Air), 0.01);
		Assert.assertEquals(0.7, factors.getLightTransmittance(GlazingType.Triple, WindowInsulationType.LowEHardCoat), 0.01);
		Assert.assertEquals(0.7, factors.getLightTransmittance(GlazingType.Triple, WindowInsulationType.LowESoftCoat), 0.01);
	}
	
	@Test
	public void testGainsTransmittance() {
		final ITransmittanceFactors factors = imputationSchema.getWindowPropertyTables().getTransmittanceFactors();
		Assert.assertEquals(0.85, factors.getGainsTransmittance(GlazingType.Single, null), 0.01);
		Assert.assertEquals(0.76, factors.getGainsTransmittance(GlazingType.Double, WindowInsulationType.Air), 0.01);
		Assert.assertEquals(0.72, factors.getGainsTransmittance(GlazingType.Double, WindowInsulationType.LowEHardCoat), 0.01);
		Assert.assertEquals(0.63, factors.getGainsTransmittance(GlazingType.Double, WindowInsulationType.LowESoftCoat), 0.01);
		Assert.assertEquals(0.76, factors.getGainsTransmittance(GlazingType.Secondary, null), 0.01);
		Assert.assertEquals(0.68, factors.getGainsTransmittance(GlazingType.Triple, WindowInsulationType.Air), 0.01);
		Assert.assertEquals(0.64, factors.getGainsTransmittance(GlazingType.Triple, WindowInsulationType.LowEHardCoat), 0.01);
		Assert.assertEquals(0.57, factors.getGainsTransmittance(GlazingType.Triple, WindowInsulationType.LowESoftCoat), 0.01);
	}
}
