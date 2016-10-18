package uk.org.cse.nhm.simulation.measure.boilers;

import static org.mockito.Mockito.mock;

import org.junit.Assert;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.EmitterType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.ISpaceHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilerImpl;
import uk.org.cse.nhm.hom.emf.technologies.impl.CentralHeatingSystemImpl;
import uk.org.cse.nhm.hom.emf.technologies.impl.CentralWaterSystemImpl;
import uk.org.cse.nhm.hom.emf.technologies.impl.CommunityHeatSourceImpl;
import uk.org.cse.nhm.hom.emf.technologies.impl.MainWaterHeaterImpl;
import uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl;
import uk.org.cse.nhm.hom.emf.util.Efficiency;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.hom.emf.util.impl.TechnologyOperations;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulation.measure.WaterHeatingSystemTestUtil;
import uk.org.cse.nhm.simulation.measure.util.Util;
import uk.org.cse.nhm.simulation.measure.util.Util.MockDimensions;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;

/**
 * Tests the suitability constraints in the abstract gas boiler measure;
 * 
 * tests for subclasses might want to use the static methods, if they are still suitable tests.
 * 
 * @author hinton
 *
 */
public class AbstractGasBoilerMeasureTest {
	protected final ITechnologyOperations operations = new TechnologyOperations();
	protected final MockDimensions dims = Util.getMockDimensions();
	/**
	 * Creates a mock IComponents with real StructureModel and TechnologyModel. These will have nothing in them by default.
	 * 
	 * @return
	 */
	protected ISettableComponentsScope mockComponents() {
		StructureModel structure = new StructureModel();
		ITechnologyModel technologies = new TechnologyModelImpl() {
		};

		IPowerTable ecr = mock(IPowerTable.class);

		return Util.mockComponents(dims, structure, technologies, ecr);
	}
	
	protected ITechnologyModel applyAndGetResult(final IComponentsAction act) throws NHMException {
		return Util.applyAndGetTech(dims, act, mockComponents());
	}
	
	protected void assertBoilerBasics(ITechnologyModel technologies, double efficiency, FuelType fuel, EmitterType emitters, boolean condensing, int installationYear,
			double cylinderInsulationThickness, double cylinderVolume) {
		assertBoilerBasics(technologies, Efficiency.fromDouble(efficiency),
				fuel, emitters, condensing, installationYear, cylinderInsulationThickness, cylinderVolume);
	}
	protected void assertBoilerBasics(ITechnologyModel technologies, Efficiency efficiency, FuelType fuel, EmitterType emitters, boolean condensing, int installationYear,
			double cylinderInsulationThickness, double cylinderVolume) {
		IHeatSource heatSource = technologies.getIndividualHeatSource();
		Assert.assertTrue(heatSource instanceof IBoiler);
		IBoiler boiler = (IBoiler) heatSource;
		Assert.assertEquals(efficiency, boiler.getSummerEfficiency());
		Assert.assertEquals(efficiency, boiler.getWinterEfficiency());
		Assert.assertEquals(fuel, boiler.getFuel());
		Assert.assertEquals(condensing, boiler.isCondensing());
		Assert.assertEquals(installationYear, boiler.getInstallationYear());

		final ISpaceHeater main = technologies.getPrimarySpaceHeater();
		Assert.assertTrue(main instanceof ICentralHeatingSystem);
		ICentralHeatingSystem centralHeating = (ICentralHeatingSystem) main;

		Assert.assertTrue(centralHeating.getControls().contains(HeatingSystemControlType.PROGRAMMER));
		Assert.assertTrue(centralHeating.getControls().contains(HeatingSystemControlType.THERMOSTATIC_RADIATOR_VALVE));
		Assert.assertTrue(centralHeating.getControls().contains(HeatingSystemControlType.ROOM_THERMOSTAT));
		Assert.assertEquals(3000d, boiler.getAnnualOperationalCost(), 0d);
		
		WaterHeatingSystemTestUtil.testCombinedWaterHeatingSystem(technologies);
		
		WaterHeatingSystemTestUtil.testStandardHotWaterCylinderPresent(technologies, cylinderInsulationThickness, cylinderVolume);
	}

	/**
	 * Check that the on/off gas flag sets or breaks suitability OK
	 * 
	 * @param m
	 */
	public static void testSuitabilityOnGas(final MockDimensions dims, final AbstractBoilerMeasure m) {
		StructureModel structure = new StructureModel() {
			{
				setOnGasGrid(false);
			}
		};
		ITechnologyModel tech = new TechnologyModelImpl() {
		};


		final IComponentsScope c = Util.mockComponents(dims, structure, tech);

		Assert.assertFalse("Not suitable when not on gas grid", m.isSuitable(c, ILets.EMPTY));
		structure.setOnGasGrid(true);
		Assert.assertTrue("Suitable when on gas grid", m.isSuitable(c, ILets.EMPTY));
	}
	
	/**
	 * Check that community space heating makes gas boiler measure not suitable
	 * @param m
	 */
	public static void testSuitabilityFalseGivenExistingCommunitySpaceHeating(final MockDimensions dims, final AbstractBoilerMeasure m) {
		final ICommunityHeatSource community = new CommunityHeatSourceImpl() {
		};

		StructureModel structure = new StructureModel() {
			{
				setOnGasGrid(true);
			}
		};
		ITechnologyModel technologies = new TechnologyModelImpl() {
			{
				setCommunityHeatSource(community);
				setPrimarySpaceHeater(new CentralHeatingSystemImpl() {
					{
						setHeatSource(community);
					}
				});
			}
		};
		IComponentsScope components = Util.mockComponents(dims, structure, technologies);
		Assert.assertFalse(m.isSuitable(components, ILets.EMPTY));
	}

	/**
	 * Check that community water heating makes gas boiler measure unsuitable.
	 * 
	 * @param m
	 */
	public static void testSuitabilityFalseGivenExistingCommunityWaterHeating(final MockDimensions dims, final AbstractBoilerMeasure m) {
		final ICommunityHeatSource community = new CommunityHeatSourceImpl() {
		};

		StructureModel structure = new StructureModel() {
			{
				setOnGasGrid(true);
			}
		};
		ITechnologyModel technologies = new TechnologyModelImpl() {
			{
				setCommunityHeatSource(community);
				setCentralWaterSystem(new CentralWaterSystemImpl() {
					{
						setPrimaryWaterHeater(new MainWaterHeaterImpl() {
							{
								setHeatSource(community);
							}
						});
					}
				});
			}
		};

		IComponentsScope components = Util.mockComponents(dims,structure, technologies);
		Assert.assertFalse(m.isSuitable(components, ILets.EMPTY));
	}

	/**
	 * Check that an existing standard boiler does not make gas boiler measure
	 * unsuitable.
	 */
	public static void testSuitabilityWithStandardHeatingSystemInstalledPreviously(final MockDimensions dims, final AbstractBoilerMeasure m) {
		final IBoiler boiler = new BoilerImpl() {
		};

		StructureModel structure = new StructureModel() {
			{
				setOnGasGrid(true);
			}
		};
		ITechnologyModel technologies = new TechnologyModelImpl() {
			{
				setIndividualHeatSource(boiler);
				setPrimarySpaceHeater(new CentralHeatingSystemImpl() {
					{
						setHeatSource(boiler);
					}
				});
				setCentralWaterSystem(new CentralWaterSystemImpl() {
					{
						setPrimaryWaterHeater(new MainWaterHeaterImpl(){{
								setHeatSource(boiler);
						}});
					}
				});
			}
		};
		IComponentsScope components = Util.mockComponents(dims, structure, technologies);
		Assert.assertTrue(m.isSuitable(components, ILets.EMPTY));
	}
}
