package uk.org.cse.nhm.simulation.measure.insulation;

import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl;
import uk.org.cse.nhm.hom.structure.IMutableWall;
import uk.org.cse.nhm.hom.structure.IWall;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Elevation;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.language.builder.function.MapWallTypes;
import uk.org.cse.nhm.language.definition.enums.XWallConstructionTypeRule;
import uk.org.cse.nhm.language.definition.enums.XWallInsulationRule;
import uk.org.cse.nhm.simulation.measure.util.Util;
import uk.org.cse.nhm.simulation.measure.util.Util.MockDimensions;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;

public class WallInsulationMeasureTest {
	
	private WallInsulationMeasure measure;
	private MockDimensions dims;

	@Before
	public void setup() {
		this.dims = Util.getMockDimensions();
		
		this.measure = new WallInsulationMeasure(
				dims.structure, 
				ConstantComponentsFunction.<Number>of(Name.of("test"), 0d), 
				15, 
				ConstantComponentsFunction.<Number>of(Name.of("test"), 10d), 
				Optional.<IComponentsFunction<Number>>absent(),
				MapWallTypes.getPredicateMatching(XWallConstructionTypeRule.Cavity, XWallInsulationRule.NoCavity),
				WallInsulationType.FilledCavity);
		
	}
	
	public boolean testSuitability(final WallConstructionType wallType) {
		final IMutableWall wall = mock(IMutableWall.class);
		when(wall.getWallConstructionType()).thenReturn(wallType);
		return measure.isSuitable(buildHouseIncludingWall(wall), ILets.EMPTY);
	}

	@Test
	public void testSuitability() {
		for (final WallConstructionType wt : WallConstructionType.values()) {
			Assert.assertEquals(wt == WallConstructionType.Cavity, testSuitability(wt));
		}
	}
	
	@Test
	public void testApplicationOfMeasure() throws NHMException {
		final WallInsulationMeasure m = measure;

		// repeat test, to check that copier works repeatedly and that measure
		// is not keeping state or anything.
		for (int i = 0; i<100; i++) {
			final IMutableWall wall = mock(IMutableWall.class);
			when(wall.getWallConstructionType()).thenReturn(WallConstructionType.Cavity);
			when(wall.getArea()).thenReturn(100d);

			final ISettableComponentsScope houseIncludingWall = buildHouseIncludingWall(wall);

			Assert.assertTrue(m.isSuitable(houseIncludingWall, ILets.EMPTY));
			
			final StructureModel modifiedStructure = Util.applyAndGetStructure(dims, m, houseIncludingWall);
			
			Assert.assertEquals(1, modifiedStructure.getStoreys().size());
			
			final Storey storey = modifiedStructure.getStoreys().get(0);
			final IMutableWall modifiedWall = storey.getWalls().iterator().next();
			
			verify(modifiedWall, times(1)).addInsulation(eq(WallInsulationType.FilledCavity), eq(15.0), anyDouble());
			Assert.assertEquals(WallConstructionType.Cavity, wall.getWallConstructionType());
		}
	}

	private ISettableComponentsScope buildHouseIncludingWall(final IMutableWall wall) {
		final Storey storey = mock(Storey.class);
		when(storey.getWalls()).thenReturn(Collections.singleton(wall));
		when(storey.getImmutableWalls()).thenReturn(Collections.<IWall> singleton(wall));
		when(storey.copy()).thenReturn(storey);
		
		final ITechnologyModel technologies = new TechnologyModelImpl() {
		};
		final Elevation dummy = mock(Elevation.class);
		final StructureModel structure = new StructureModel() {
			{
				addStorey(storey);
				setElevation(ElevationType.FRONT, dummy);
				setElevation(ElevationType.BACK, dummy);
				setElevation(ElevationType.LEFT, dummy);
				setElevation(ElevationType.RIGHT, dummy);
			}
		};

		return Util.mockComponents(dims, structure, technologies);
	}
}
