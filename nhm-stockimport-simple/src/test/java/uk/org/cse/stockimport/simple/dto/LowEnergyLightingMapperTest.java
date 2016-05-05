package uk.org.cse.stockimport.simple.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.stockimport.simple.dto.MappableDTOReader;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.ILowEnergyLightingDTO;
import uk.org.cse.stockimport.domain.impl.LowEnergyLightingDTO;

public class LowEnergyLightingMapperTest extends AbsMapperTest {

	
	private final double expectedLightFraction = 23.45;
	
	@Before
	public void initiateTests(){
		fields()
			.add(IBasicDTO.AACODE, aacode)
			.add(ILowEnergyLightingDTO.FRACTION_FIELD, expectedLightFraction);
	}
	
	@Test
	public void mapMapFieldSet() throws Exception {
		final ILowEnergyLightingDTO dto = new MappableDTOReader<>(LowEnergyLightingDTO.class).read(fieldSet); 
		testBuildReferenceData(dto, aacode);
		assertEquals("Light Fraction", expectedLightFraction,dto.getLowEnergyLightsFraction(), 0d);
	}
}
