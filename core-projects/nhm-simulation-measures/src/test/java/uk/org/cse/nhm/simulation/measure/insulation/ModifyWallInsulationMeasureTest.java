package uk.org.cse.nhm.simulation.measure.insulation;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;
import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl;
import uk.org.cse.nhm.hom.structure.IMutableWall;
import uk.org.cse.nhm.hom.structure.IWall;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Elevation;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.simulation.measure.util.Util;
import uk.org.cse.nhm.simulation.measure.util.Util.MockDimensions;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;

public class ModifyWallInsulationMeasureTest {
	
	private static final double thicknessBefore = 555d;
	private static final double thicknessAfter = 999d;
	private ModifyWallInsulationMeasure measure;
	private MockDimensions dims;

	@Before
	public void setup() {
		this.dims = Util.getMockDimensions();
		
		this.measure = new ModifyWallInsulationMeasure(
				dims.structure,
				ConstantComponentsFunction.<Double>of(Name.of("test"), thicknessAfter)
				);
	}
	
	@Test
	public void testSuitability() {
		final IMutableWall wall = mock(IMutableWall.class);
		Assert.assertTrue(measure.isSuitable(buildHouseIncludingWall(wall), ILets.EMPTY));
	}
	
	static class MockWallProps {
		Map<WallInsulationType, Double> thicknessByType = new HashMap<>();

		public MockWallProps() {
		}

		public double getInsulationThickness(final WallInsulationType t) {
			return thicknessByType.get(t);
		}

		public void setInsulationThickness(final WallInsulationType t, final double insulationThickness) {
			thicknessByType.put(t, insulationThickness);
		}

		public Set<WallInsulationType> getWallInsulationTypes() {
			return thicknessByType.keySet();
		}
	}

	@Test
	public void testApplicationOfMeasure() throws NHMException {
		final ModifyWallInsulationMeasure m = measure;

		final MockWallProps p = new MockWallProps();
		final IMutableWall wall = mock(IMutableWall.class);
		when(wall.getWallInsulationThickness(any(WallInsulationType.class))).then(new Answer<Double>() {
			@Override
			public Double answer(final InvocationOnMock invocation) throws Throwable {
				return p.getInsulationThickness((WallInsulationType) invocation.getArguments()[0]);
			}
		});
		Mockito.doAnswer(new Answer<Object>() {
			@Override
			public Object answer(final InvocationOnMock invocation) throws Throwable {
				final Object[] arguments = invocation.getArguments();
				p.setInsulationThickness((WallInsulationType) invocation.getArguments()[0], (Double) arguments[1]);
				return null;
			}
		}).when(wall).setWallInsulationThicknessAndAddOrRemoveInsulation(any(WallInsulationType.class), any(Double.class));
		when(wall.getWallInsulationTypes()).then(new Answer<Set<WallInsulationType>>() {
			@Override
			public Set<WallInsulationType> answer(final InvocationOnMock invocation)
					throws Throwable {
				return p.getWallInsulationTypes();
			}
		});
		
		wall.setWallInsulationThicknessAndAddOrRemoveInsulation(WallInsulationType.External, thicknessBefore);
		wall.setWallInsulationThicknessAndAddOrRemoveInsulation(WallInsulationType.FilledCavity, thicknessBefore);
		wall.setWallInsulationThicknessAndAddOrRemoveInsulation(WallInsulationType.Internal, thicknessBefore);
		
		final ISettableComponentsScope houseIncludingWall = buildHouseIncludingWall(wall);

		Assert.assertTrue(m.isSuitable(houseIncludingWall, ILets.EMPTY));
			
		final StructureModel modifiedStructure = Util.applyAndGetStructure(dims, m, houseIncludingWall);
			
		Assert.assertEquals("Should still be a single storey house", 1, modifiedStructure.getStoreys().size());
		for(final IWall w: modifiedStructure.getStoreys().get(0).getImmutableWalls()) {
			for(final WallInsulationType type : w.getWallInsulationTypes()) {
				Assert.assertEquals(String.format("Insulation thickness of walls should match the measure for insulation type %s",type.toString()), thicknessAfter,  w.getWallInsulationThickness(type), 0d);
			}
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
