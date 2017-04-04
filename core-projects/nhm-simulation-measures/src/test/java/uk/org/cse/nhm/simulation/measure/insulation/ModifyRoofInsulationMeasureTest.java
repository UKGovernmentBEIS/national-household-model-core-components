package uk.org.cse.nhm.simulation.measure.insulation;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Elevation;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.simulation.measure.util.Util;
import uk.org.cse.nhm.simulation.measure.util.Util.MockDimensions;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;


public class ModifyRoofInsulationMeasureTest {
	private static final double thicknessBefore = 555d;
	private static final double thicknessAfter = 999d;
	private ModifyRoofInsulationMeasure measure;
	
	private MockDimensions dims;

	@Before
	public void setup() {
		this.dims = Util.getMockDimensions();
		
		this.measure = new ModifyRoofInsulationMeasure(
				dims.structure,
				ConstantComponentsFunction.<Double>of(Name.of("test"), thicknessAfter),
				Optional.<IComponentsFunction<? extends Number>>absent()
				);
	}
	
	@Test
	public void testSuitability() {
		Assert.assertTrue(measure.isSuitable(makeComponents(new MockRoofProps(thicknessBefore, Optional.<Double>absent())), ILets.EMPTY));
	}
	
	private ISettableComponentsScope makeComponents(final MockRoofProps p) {
		final Elevation elevation = mock(Elevation.class);
		when(elevation.copy()).thenReturn(elevation);
		final Storey storey = mock(Storey.class);
		when(storey.copy()).thenReturn(storey);
		Mockito.doAnswer(new Answer<Double>() {
			@Override
			public Double answer(final InvocationOnMock invocation) throws Throwable {
				p.setUvalue(Optional.of((Double) invocation.getArguments()[0]));
				return null;
			};
		}).when(storey).setCeilingUValue(any(Double.class));
		when(storey.getCeilingUValue()).then(new Answer<Optional<Double>>() {
			@Override
			public Optional<Double> answer(final InvocationOnMock invocation)
					throws Throwable {
				return p.getUvalue();
			}
		});
		
		final ITechnologyModel technologies = new TechnologyModelImpl() {};
		final StructureModel structure = new StructureModel() {
			@Override
			public List<Storey> getStoreys() {
				return ImmutableList.of(storey);
			}

			@Override
			public double getRoofInsulationThickness() {
				return p.getThickness();
			}
			
			@Override
			public void setRoofInsulationThickness(
					final double CeilingInsulationThickness) {
				p.setThickness(CeilingInsulationThickness);
			}

				@Override
				public boolean getHasLoft() {
					return true;
				}
				
			@Override
			public Map<ElevationType, Elevation> getElevations() {
				final Builder<ElevationType, Elevation> b = ImmutableMap.<ElevationType, Elevation>builder();
				for(final ElevationType t: ElevationType.values()) {
					b.put(t, elevation);
				}
				return b.build();
			}
		};

		final ISettableComponentsScope mockComponents = Util.mockComponents(dims, structure, technologies);
		
		return mockComponents;
	}

	static class MockRoofProps {
		double thickness = 0d;
		Optional<Double> uvalue = Optional.absent();
		public double getThickness() {
			return thickness;
		}
		public void setThickness(final double thickness) {
			this.thickness = thickness;
		}
		public Optional<Double> getUvalue() {
			return uvalue;
		}
		public void setUvalue(final Optional<Double> uvalue) {
			this.uvalue = uvalue;
		}
		public MockRoofProps(final double thickness, final Optional<Double> uvalue) {
			super();
			this.thickness = thickness;
			this.uvalue = uvalue;
		}
	}

	@Test
	public void testApplicationOfMeasure() throws NHMException {
		final ModifyRoofInsulationMeasure m = measure;
		final MockRoofProps p = new MockRoofProps(thicknessBefore,Optional.<Double>absent());
		
		final ISettableComponentsScope house = makeComponents(p);
		
		Assert.assertTrue(m.isSuitable(house, ILets.EMPTY));
			
		final StructureModel modifiedStructure = Util.applyAndGetStructure(dims, m, house);
			
		Assert.assertEquals("Should still be a single storey house", 1, modifiedStructure.getStoreys().size());
		Assert.assertEquals("Roof insulation thickness should match that of the measure", modifiedStructure.getRoofInsulationThickness(), thicknessAfter, 0d);
	}
	
}

