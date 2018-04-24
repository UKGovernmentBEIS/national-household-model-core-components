package uk.org.cse.stockimport.hom.impl.steps.imputation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Polygon;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.types.FloorConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.energycalculator.api.types.RoofConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;
import uk.org.cse.nhm.hom.structure.IMutableWall;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Elevation;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.hom.types.BuiltFormType;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.impl.HouseCaseDTO;
import uk.org.cse.stockimport.repository.IHouseCaseSources;

public class MainImputationStepTest {
	@Test
	public void testCorrectValuesImputed() {
		final MainImputationStep mis = new MainImputationStep();
		final HouseCaseDTO hdc = mock(HouseCaseDTO.class);
		when(hdc.getRegionType()).thenReturn(RegionType.SouthWest);

		@SuppressWarnings("unchecked")
		final IHouseCaseSources<IBasicDTO> dtos = mock(IHouseCaseSources.class);
		when(dtos.requireOne(HouseCaseDTO.class)).thenReturn(hdc);

		final SurveyCase sc = new SurveyCase();
		final StructureModel sm = new StructureModel(BuiltFormType.Detached);
		sc.setStructure(sm);

		final Polygon pg = new Polygon(
				new int[] {0, 1, 1, 0},
				new int[] {0, 0, 1, 1},
				4);

		final Storey s = new Storey();
		s.setPerimeter(pg);
		s.setHeight(1);
		sm.addStorey(s);
		for (final ElevationType et : ElevationType.values())
			sm.setElevation(et, new Elevation());
		for (final IMutableWall wall : s.getWalls()) {
			wall.setWallConstructionType(WallConstructionType.SolidBrick);
		}
		sm.setRoofConstructionType(RoofConstructionType.Flat);
		sm.setGroundFloorConstructionType(FloorConstructionType.Solid);

		mis.build(sc, dtos);

		for (final IMutableWall wall : s.getWalls()) {
			Assert.assertEquals(2.1, wall.getUValue(), 0.01);
			Assert.assertEquals(0.35, wall.getAirChangeRate(), 0.01);
		}
		Assert.assertEquals(1.2, s.getFloorUValue(), 0.01);
		Assert.assertEquals(2.3, s.getCeilingUValue(), 0.01);
	}
}
