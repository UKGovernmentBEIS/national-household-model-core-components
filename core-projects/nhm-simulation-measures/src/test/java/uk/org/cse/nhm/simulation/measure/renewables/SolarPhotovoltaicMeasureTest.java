package uk.org.cse.nhm.simulation.measure.renewables;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.energycalculator.api.types.RoofConstructionType;
import uk.org.cse.nhm.hom.components.fabric.types.FloorLocationType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.hom.types.BuiltFormType;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.sizing.impl.SizingFunction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;

public class SolarPhotovoltaicMeasureTest {

	private IDimension<ITechnologyModel> techDim;
	private IDimension<StructureModel> structureDim;
	private ILogEntryHandler log;
	private IComponentsScope scope;
	private ILets lets;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		techDim = mock(IDimension.class);
		structureDim = mock(IDimension.class);
		log = mock(ILogEntryHandler.class);

		scope = mock(IComponentsScope.class);
		lets = mock(ILets.class);
	}

	@Test
	public void suitability() {
		final SolarPhotovoltaicMeasure pv = new SolarPhotovoltaicMeasure(
				new SizingFunction(
						ConstantComponentsFunction.<Number>of(Name.of("Size kW"), 1d),
						Double.NEGATIVE_INFINITY,
						Double.POSITIVE_INFINITY
				),
				ConstantComponentsFunction.<Number>of(Name.of("Capex"), 1d),
				ConstantComponentsFunction.<Number>of(Name.of("Own Use Fraction"), 0.5d),
				techDim,
				structureDim,
				log
			);

		for (final BuiltFormType builtForm : BuiltFormType.values()) {
			for (final RoofConstructionType roofType : RoofConstructionType.values()) {
				for (final FloorLocationType floorType : FloorLocationType.values()) {
					setupStructure(builtForm, roofType,floorType);
					setupTech(false);

					final boolean expectedUnsuitable = (builtForm.isFlat() && floorType != FloorLocationType.TOP_FLOOR) || roofType == RoofConstructionType.Thatched;

					if (expectedUnsuitable) {
						Assert.assertEquals(builtForm + " " + roofType + " " + floorType + " no existing PV", false, pv.isSuitable(scope, lets));

					} else {
						Assert.assertEquals(builtForm + " " + roofType + " " + floorType + " no existing PV", true, pv.isSuitable(scope, lets));

						setupTech(true);
						Assert.assertEquals(builtForm + " " + roofType + " " + floorType + " existing PV", false, pv.isSuitable(scope, lets));
					}
				}
			}
		}
	}

	private void setupStructure(final BuiltFormType builtForm, final RoofConstructionType roofType, final FloorLocationType floorLocation) {
		final StructureModel structure = new StructureModel(builtForm);
		structure.setRoofConstructionType(roofType);

		final Storey storey = new Storey();
		storey.setFloorLocationType(floorLocation);
		structure.addStorey(storey);
		when(scope.get(structureDim)).thenReturn(structure);
	}

	private void setupTech(final boolean existingSolarPV) {
		final ITechnologyModel tech = ITechnologiesFactory.eINSTANCE.createTechnologyModel();

		if (existingSolarPV) {
			tech.setSolarPhotovoltaic(ITechnologiesFactory.eINSTANCE.createSolarPhotovoltaic());
		}

		when(scope.get(techDim)).thenReturn(tech);
	}
}
