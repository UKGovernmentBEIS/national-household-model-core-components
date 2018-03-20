package uk.org.cse.stockimport.simple.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.types.RoofConstructionType;
import uk.org.cse.nhm.hom.components.fabric.types.CoveringType;
import uk.org.cse.nhm.hom.components.fabric.types.RoofStructureType;
import uk.org.cse.nhm.stockimport.simple.dto.MappableDTOReader;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.geometry.IRoofDTO;
import uk.org.cse.stockimport.domain.geometry.impl.RoofDTO;

public class RoofMapperTest  extends AbsMapperTest{
	
	private final double expectedRoofInsulationThickness = 50.9d;
	private final RoofConstructionType roofConstructionType = RoofConstructionType.PitchedSlateOrTiles;
	private final RoofStructureType roofStructureType = RoofStructureType.Pitched;
	private final CoveringType coveringType = CoveringType.Slates;
	
	@Before
	public void initiateTests(){
		fields()
		.add(IBasicDTO.AACODE, aacode)
		//Insulation
		.add(IRoofDTO.INSULATION_THICKNESS_FIELD, String.valueOf(expectedRoofInsulationThickness))
		//Roof Construction
		.add(IRoofDTO.CONSTRUCTION_TYPE_FIELD, roofConstructionType.toString())
		.add(IRoofDTO.COVERING_TYPE_FIELD, coveringType.toString())
		.add(IRoofDTO.STRUCTURE_TYPE, roofStructureType.toString());
	}
	
	@Test
	public void testMapFieldSet() throws Exception{
		final IRoofDTO builtDTO = new MappableDTOReader<>(RoofDTO.class).read(fieldSet);
		testBuildReferenceData(builtDTO, aacode);
		testRoofStructureData(builtDTO,roofConstructionType,roofStructureType,coveringType);
		testInsulationData(builtDTO, expectedRoofInsulationThickness);
	}

		
	public static final void testRoofStructureData(final IRoofDTO roofDTO, final RoofConstructionType constructionType, 
			final RoofStructureType roofStructureType, final CoveringType coveringType){
		assertEquals("construction type", constructionType ,roofDTO.getRoofType());
		assertEquals("structure type", roofStructureType, roofDTO.getStructureType());
		assertEquals("covering type", coveringType, roofDTO.getCoveringType());
	}
	
	public static final void testInsulationData(final IRoofDTO roofDTO, final double insulationThickness){
		assertEquals("Insulation Thickness", Optional.of(insulationThickness), roofDTO.getInsulationThickness());
	}
}
