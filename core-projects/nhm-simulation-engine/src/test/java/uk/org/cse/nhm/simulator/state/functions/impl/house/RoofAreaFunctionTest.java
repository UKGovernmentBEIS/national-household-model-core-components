package uk.org.cse.nhm.simulator.state.functions.impl.house;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.org.cse.nhm.energycalculator.api.types.RoofConstructionType;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RoofAreaFunctionTest {

	private StructureModel structure;
	private IDimension<StructureModel> structureDim;
	private ILets lets;
	private IComponentsScope scope;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		structure = mock(StructureModel.class);
		structureDim = mock(IDimension.class);
		lets = mock(ILets.class);
		scope = mock(IComponentsScope.class);

		when(scope.get(structureDim)).thenReturn(structure);
		when(structure.getExternalRoofArea()).thenReturn(1d);
	}

	@Test
	public void returnsExternalRoofArea() {
		Assert.assertEquals(
				1d,
				new RoofAreaFunction(structureDim, false).compute(scope, lets),
				0d
			);
	}

	@Test
	public void pitchCorrection() {
		when(structure.getRoofConstructionType()).thenReturn(RoofConstructionType.Flat);

		Assert.assertEquals(
				"Flat",
				1d,
				new RoofAreaFunction(structureDim, true).compute(scope, lets),
				0d
			);

		when(structure.getRoofConstructionType()).thenReturn(RoofConstructionType.Thatched);

		Assert.assertEquals(
				"Thatched",
				1d,
				new RoofAreaFunction(structureDim, true).compute(scope, lets),
				0d
			);

		when(structure.getRoofConstructionType()).thenReturn(RoofConstructionType.PitchedSlateOrTiles);

		Assert.assertEquals(
				"Pitched",
				1.22,
				new RoofAreaFunction(structureDim, true).compute(scope, lets),
				0.1d
			);
	}
}
