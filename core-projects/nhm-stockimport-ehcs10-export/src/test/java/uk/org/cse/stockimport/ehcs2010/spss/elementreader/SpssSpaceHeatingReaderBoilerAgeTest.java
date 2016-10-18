package uk.org.cse.stockimport.ehcs2010.spss.elementreader;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

import junit.framework.Assert;
import uk.org.cse.nhm.ehcs10.physical.ServicesEntry;
import uk.org.cse.stockimport.domain.impl.HouseCaseDTO;
import uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType;
import uk.org.cse.stockimport.domain.services.impl.SpaceHeatingDTO;

public class SpssSpaceHeatingReaderBoilerAgeTest extends SpssSpaceHeatingReaderTest {

	private SpaceHeatingDTO heating;
	private HouseCaseDTO houseCase;
	private ServicesEntry services;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		heating = mock(SpaceHeatingDTO.class);
		houseCase = mock(HouseCaseDTO.class);
		services = mock(ServicesEntry.class);
	}

	@Test
	public void ageShouldBeAbsentIfHeatSystemMissing() {
		when(heating.getSpaceHeatingSystemType()).thenReturn(SpaceHeatingSystemType.MISSING);

		Assert.assertEquals(Optional.<Integer> absent(), reader.getBoilerAge(heating, 0, services, houseCase));
	}

	@Test
	public void ageShouldCalculateFromBackBoilerAgeIfBackBoiler() {
		when(heating.getSpaceHeatingSystemType()).thenReturn(SpaceHeatingSystemType.BACK_BOILER);

		when(services.getBackBoiler_Age()).thenReturn(10);
		Assert.assertEquals(Optional.of(10), reader.getBoilerAge(heating, 20, services, houseCase));

		when(heating.getSpaceHeatingSystemType()).thenReturn(SpaceHeatingSystemType.BACK_BOILER_NO_CENTRAL_HEATING);
		Assert.assertEquals(Optional.of(10), reader.getBoilerAge(heating, 20, services, houseCase));
	}

	@Test
	public void ageShouldFallbackToCalculatingFromBoilerAgeIfBackBoilerWithNoBackBoilerAge() {
		when(heating.getSpaceHeatingSystemType()).thenReturn(SpaceHeatingSystemType.BACK_BOILER);

		when(services.getBackBoiler_Age()).thenReturn(null);
		when(services.getBoiler_Age()).thenReturn(10);
		Assert.assertEquals(Optional.of(10), reader.getBoilerAge(heating, 20, services, houseCase));
	}

	@Test
	public void ageShouldCalculateFromBoilerAgeIfNotBackBoiler() {
		when(heating.getSpaceHeatingSystemType()).thenReturn(SpaceHeatingSystemType.STANDARD);

		when(services.getBoiler_Age()).thenReturn(10);
		Assert.assertEquals(Optional.of(10), reader.getBoilerAge(heating, 20, services, houseCase));
	}

	@Test
	public void ageShouldByAbsentIfNoDataAvailable() {
		when(heating.getSpaceHeatingSystemType()).thenReturn(SpaceHeatingSystemType.STANDARD);
		when(services.getBoiler_Age()).thenReturn(null);

		Assert.assertEquals(Optional.<Integer> absent(), reader.getBoilerAge(heating, 0, services, houseCase));
	}

	@Test
	public void ageShouldUseHouseBuildYearIfSpecialValue88() {
		when(heating.getSpaceHeatingSystemType()).thenReturn(SpaceHeatingSystemType.STANDARD);

		when(services.getBoiler_Age()).thenReturn(88);
		when(houseCase.getBuildYear()).thenReturn(2000);
		Assert.assertEquals(Optional.of(2000), reader.getBoilerAge(heating, 0, services, houseCase));
	}
}
