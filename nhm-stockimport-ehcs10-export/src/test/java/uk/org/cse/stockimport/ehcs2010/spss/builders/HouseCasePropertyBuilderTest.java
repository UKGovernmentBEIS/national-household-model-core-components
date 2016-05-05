package uk.org.cse.stockimport.ehcs2010.spss.builders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import uk.org.cse.stockimport.domain.AdditionalHousePropertiesDTO;

public class HouseCasePropertyBuilderTest {
	private HouseCasePropertyBuilder builder;
	
	String aacode = "TEST_AA_CODE";
	ImmutableMap<String, String> properties = ImmutableMap.of(
			"one", "one_value", 
			"two", "two_value");

	private AdditionalHousePropertiesDTO dto;

	@Before
	public void setUp() {
		this.builder = new HouseCasePropertyBuilder();
		dto = mock(AdditionalHousePropertiesDTO.class);
		when(dto.getAacode()).thenReturn(aacode);
		when(dto.getValuesByProperty()).thenReturn(properties);
	}

	@Test
	public void shouldBuildHeaderRow() {
		dto.setAacode(aacode);
		dto.setValuesByProperty(properties);
		
		Assert.assertArrayEquals(new String[] { "aacode", "one", "two" }, builder.buildHeader(dto));
	}

	@Test
	public void valuesShouldLineUpWithHeaders() {
		builder.buildHeader(dto); /* There is some internal state here, which means you can only build the rows once you have already built the headers. */
		final String[] result = builder.buildRow(dto);
		Assert.assertArrayEquals(new String[]{aacode, "one_value", "two_value"}, result);
	}
}
