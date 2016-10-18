package uk.org.cse.stockimport.ehcs2010.spss.elementreader;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.ehcs10.physical.types.Enum1713;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1777;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1779;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.stockimport.ehcs2010.spss.elementreader.lookup.EHCSPrimaryHeatingCode;

public class SpssSpaceHeatingReaderFuelTest {

	final SedbukHelper reader = new SedbukHelper(null, null);

	@Test
	public void getFuelUsesMainSurveyFuelIfNoEHSCode() {
		Assert.assertEquals(FuelType.MAINS_GAS, reader.getMostLikelyFuelType(null, Enum1713.__MISSING, Enum1777.Gas_Mains, null));
	}
	
	@Test
	public void getFuelUsesBackBoilerFuelIfNoEHSCode() {
		Assert.assertEquals(FuelType.HOUSE_COAL, reader.getMostLikelyFuelType(null, Enum1713.BackBoiler, null, Enum1779.Anthracite));
	}

	@Test
	public void getFuelUsesEHSFuelIfSurveyFuelRejected() {
		Assert.assertEquals(FuelType.MAINS_GAS, reader.getMostLikelyFuelType(EHCSPrimaryHeatingCode.GAS_FAN_ASSISTED_ELECTRIC_IGNITION_HIGH_THERMAL_CAPACITY, null, Enum1777.Oil, null));
	}

	@Test
	public void getFuelUsesSurveyFuelIfAllowableOption() {
		Assert.assertEquals(FuelType.OIL, reader.getMostLikelyFuelType(EHCSPrimaryHeatingCode.WARM_AIR_GAS_OR_OIL_WITH_BALANCED_OR_OPEN_FLUE_CONDENSING, null, Enum1777.Oil, null));
	}

	@Test
	public void getFuelUsesSurveyFuelIfBackBoilerFuelNotPresent() {
		Assert.assertEquals(FuelType.HOUSE_COAL, reader.getMostLikelyFuelType(EHCSPrimaryHeatingCode.SOLID_BACK_BOILER_CLOSED_FIRE, null, Enum1777.SolidFuel_Coal, null));
	}
}
