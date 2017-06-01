package uk.org.cse.nhm.simulation.measure.lighting;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.hom.emf.technologies.ILight;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;

public class LowEnergyLightingMeasureTest {
	private ISettableComponentsScope scope;
	private IDimension<ITechnologyModel> techDim;
	private IDimension<StructureModel> structureDim;
	private ITechnologyModel tech;
	private ILets lets;
	private StructureModel structure;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		scope = mock(ISettableComponentsScope.class);
		techDim = mock(IDimension.class);
		structureDim = mock(IDimension.class);

		tech = ITechnologiesFactory.eINSTANCE.createTechnologyModel();
		when(scope.get(techDim)).thenReturn(tech);

		structure = mock(StructureModel.class);
		when(structure.getFloorArea()).thenReturn(1d);
		when(scope.get(structureDim)).thenReturn(structure);

		lets = mock(ILets.class);
	}

	@Test
	public void suitability() {
		final LowEnergyLightingMeasure measure = build(0.5, 1.0);

		Assert.assertFalse("Unsuitable if no lights.", measure.isSuitable(scope, lets));

		tech.getLights().add(light(ILight.CFL_EFFICIENCY));
		Assert.assertFalse("Unsuitable if not enough inefficient lights.", measure.isSuitable(scope, lets));

		tech.getLights().add(light(ILight.INCANDESCENT_EFFICIENCY));
		tech.getLights().add(light(ILight.INCANDESCENT_EFFICIENCY));
		Assert.assertTrue("Unsuitable if enough inefficient lights.", measure.isSuitable(scope, lets));
	}

	private ILight light(final double eff) {
		final ILight l = ITechnologiesFactory.eINSTANCE.createLight();
		l.setProportion(1.0);
		l.setEfficiency(eff);
		return l;
	}

	private LowEnergyLightingMeasure build(final double threshold, final double proportion) {


		return new LowEnergyLightingMeasure(threshold, proportion,
				ConstantComponentsFunction.<Number>of(Name.of("capex"), 10),
				techDim,
				structureDim);
	}

	@Test
	public void installLowEnergyLights() {
		final ILight badLights = ITechnologiesFactory.eINSTANCE.createLight();
		badLights.setEfficiency(ILight.INCANDESCENT_EFFICIENCY);
		badLights.setProportion(1);

		tech.getLights().add(badLights);

		final LowEnergyLightingMeasure measure = build(0, 1);
		measure.createModifier(scope, lets, 1d).modify(tech);

		Assert.assertEquals("The rubbish lights should have been removed, and new lights installed.", 1, tech.getLights().size());

		final ILight goodLights = tech.getLights().get(0);
		Assert.assertEquals("The remaining lights should have better efficiency.", ILight.CFL_EFFICIENCY, goodLights.getEfficiency(), 0.0);
		Assert.assertTrue("The remaining lights should have a proportion greater than 0.", goodLights.getProportion() > 0);
	}
}
