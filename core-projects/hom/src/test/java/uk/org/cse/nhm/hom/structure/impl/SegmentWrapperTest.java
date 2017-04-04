package uk.org.cse.nhm.hom.structure.impl;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;
import uk.org.cse.nhm.hom.structure.IMutableWall;

public class SegmentWrapperTest {
	double ERROR_DELTA = 0.001;

	IMutableWall wall;

	@Before
	public void setUp() throws Exception {
		wall = new SegmentWrapper(0.0, 0.0, 0.0, new SegmentData(0.0, 0.0), null);
	}

	@Test
	public void testRValuesSumWhenAddingInsulation() {
		wall.setUValue(2);
		wall.addInsulation(WallInsulationType.External, 1, 0.5);

		Assert.assertEquals(1.0, wall.getUValue(), ERROR_DELTA);
	}

	@Test
	public void testThicknessesSetWhenAddingInsulation() {
		Assert.assertEquals(0.0, wall.getWallInsulationThickness(WallInsulationType.External), ERROR_DELTA);
		wall.addInsulation(WallInsulationType.External, 1.0, 0.5);
		Assert.assertEquals(1.0, wall.getWallInsulationThickness(WallInsulationType.External), ERROR_DELTA);
	}

	@Test
	public void testExistingInsulationIsAddedToNewInsulation() {
		wall.addInsulation(WallInsulationType.External, 1.0, 0.5);
		Assert.assertEquals(1.0, wall.getWallInsulationThickness(WallInsulationType.External), ERROR_DELTA);
		wall.addInsulation(WallInsulationType.External, 2.0, 0.5);
		Assert.assertEquals(3.0, wall.getWallInsulationThickness(WallInsulationType.External), ERROR_DELTA);
	}

	@Test
	public void testInsulationAddedWhenThicknessGreaterThanZero() {
		wall.addInsulation(WallInsulationType.External, 1.0, 0.5);
		Assert.assertTrue(wall.getWallInsulationTypes().contains(WallInsulationType.External));
	}

	@Test
	public void testInsulationRemainsWhenAddedWithThicknessZero() {
		wall.addInsulation(WallInsulationType.External, 1.0, 0.5);
		wall.addInsulation(WallInsulationType.External, 0.0, 0.5);
		Assert.assertTrue(wall.getWallInsulationTypes().contains(WallInsulationType.External));
	}
}
