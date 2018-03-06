package uk.org.cse.nhm.simulation.measure.insulation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;
import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.GlazingType;
import uk.org.cse.nhm.energycalculator.api.types.WindowGlazingAirGap;
import uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType;
import uk.org.cse.nhm.hom.structure.Glazing;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Elevation;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;

public class GlazingMeasureTest {
	private IDimension<StructureModel> structureDimension;
	
	@SuppressWarnings("unchecked")
	@Before
	public void createMocks() {
		structureDimension = mock(IDimension.class);
	}

	public GlazingMeasure createGlazingMeasure() {
		return new GlazingMeasure(structureDimension, 
				ConstantComponentsFunction.<Number>of(Name.of("test"), 0d), 
				0d, 
				0d, 
				0d, 
				0d, 
				FrameType.Metal, 
				GlazingType.Double,
				WindowInsulationType.LowESoftCoat,
				WindowGlazingAirGap.gapOf12mm);
	}
	
	private IComponentsScope components(final StructureModel sm) {
		final IComponentsScope c = mock(IComponentsScope.class);
		when(c.get(structureDimension)).thenReturn(sm);
		return c;
	}
	
	@Test
	public void applyGlazingTest() {
		final GlazingMeasure m = createGlazingMeasure();
		final StructureModel sm = mock(StructureModel.class);
		final Elevation e = new Elevation();
		e.setGlazings(ImmutableList.<Glazing>of());
		final Map<ElevationType, Elevation> es = ImmutableMap.<ElevationType, Elevation>builder()
				.put(ElevationType.FRONT, e)
				.put(ElevationType.BACK, e.copy())
				.put(ElevationType.LEFT, e.copy())
				.put(ElevationType.RIGHT, e.copy())
				.build();
		when(sm.getElevations()).thenReturn(es);
		Assert.assertTrue("Glazing should successfully modify a given structure model.", m.modify(sm));
		for(final Elevation elevation: es.values()) {
			final List<Glazing> glazings = elevation.getGlazings();
			Assert.assertEquals("Should be one glazing installed", glazings.size(), 1);
			Assert.assertEquals("Should be the same glazing installed as in the measure", glazings.get(0), m.getGlazing());
			Assert.assertEquals("Should be the same glazing type installed as in the measure", glazings.get(0).getGlazingType(), m.getGlazing().getGlazingType());
			Assert.assertEquals("Should be 100% of the glazed proportion", glazings.get(0).getGlazedProportion(), 1d, 0d);
			Assert.assertEquals("Should be correct insulation type", WindowInsulationType.LowESoftCoat, glazings.get(0).getInsulationType());
			Assert.assertEquals("Should be correct glazing air gap", WindowGlazingAirGap.gapOf12mm, glazings.get(0).getWindowGlazingAirGap());
		}
	}
	
	@Test
	public void isSuitableForGlazingTest() {
		final GlazingMeasure m = createGlazingMeasure();
		final StructureModel sm = mock(StructureModel.class);
		Assert.assertTrue("Any house should be suitable for glazing", m.isSuitable(components(sm), ILets.EMPTY));
	}
}
