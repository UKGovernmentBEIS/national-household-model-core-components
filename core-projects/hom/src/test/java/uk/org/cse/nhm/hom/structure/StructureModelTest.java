package uk.org.cse.nhm.hom.structure;

import java.awt.Polygon;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.hom.components.fabric.types.FloorLocationType;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.hom.types.BuiltFormType;

public class StructureModelTest {

	private Storey size4;
	private Storey size1;

	@Before
	public void setup() {
		size4 = new Storey();
		final Polygon size4Pol = new Polygon(
			new int[]{0, 2, 2, 0},
			new int[]{0, 0, 2, 2},
			4
		);
		size4.setPerimeter(size4Pol);

		size1 = new Storey();
		final Polygon size1Pol = new Polygon(
			new int[]{0, 1, 1, 0},
			new int[]{0, 0, 1, 1},
			4
		);
		size1.setPerimeter(size1Pol);
	}

	@Test
	public void externalRoofArea() {
		for (final BuiltFormType type : BuiltFormType.values()) {
			final StructureModel structure = new StructureModel(type);
			structure.addStorey(size4);

			Assert.assertEquals(
				"Single storey unit square " + type + " rooftype " + structure.getRoofConstructionType(),
				type.isFlat() ? 0d :
					structure.getRoofConstructionType().isPitched() ? 4.883d : 4d,
				structure.getExternalRoofArea(true),
				0.01
			);

			structure.addStorey(size1);

			Assert.assertEquals(
				"Two storey, upper storey 'contained' by lower " + type + " rooftype " + structure.getRoofConstructionType(),
				type.isFlat() ? 0d :
					structure.getRoofConstructionType().isPitched() ? 4.883d : 4d,
				structure.getExternalRoofArea(true),
				0.01
			);
		}
	}

	@Test
	public void hasExternalRoofOrFloor() {
		for (final BuiltFormType type : BuiltFormType.values()) {
			final StructureModel structure = new StructureModel(type);

			final Storey ground = new Storey();
			ground.setFloorLocationType(FloorLocationType.GROUND);
			structure.addStorey(ground);

			Assert.assertEquals(
					type + " ground floor has external roof?",
					!type.isFlat(),
					structure.hasExternalRoof()
			);

			Assert.assertTrue(
					type + " ground floor has external floor?",
					structure.hasExternalFloor()
			);

			final Storey top = new Storey();
			top.setFloorLocationType(FloorLocationType.TOP_FLOOR);
			structure.addStorey(top);

			Assert.assertTrue(
					type + " topfloor has external roof?",
					structure.hasExternalRoof()
			);

			ground.setFloorLocationType(FloorLocationType.FIRST_FLOOR);
			Assert.assertEquals(
					type + " topFloor floor has external floor?",
					!type.isFlat(),
					structure.hasExternalFloor()
			);
		}
	}
}
