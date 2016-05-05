package uk.org.cse.nhm.simulation.measure.boilers;

import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.EmitterType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.IHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.ISpaceHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.hom.emf.technologies.boilers.ICombiBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IStorageCombiBoiler;
import uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl;
import uk.org.cse.nhm.hom.emf.util.Efficiency;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulation.measure.WaterHeatingSystemTestUtil;
import uk.org.cse.nhm.simulation.measure.util.Util;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;

public class GasCondensingCombiBoilerMeasureTest extends AbstractGasBoilerMeasureTest {
	IComponentsFunction<Number> efficiency =
        ConstantComponentsFunction.<Number>of(Name.of("Eff"),0.5f);
	int storageVolume = 100;
	int cylinderVolume = 110;
	float insulationThickness = 50f;
	int installationYear = 1983;

	@Test
	public void testInstallStorageIntoEmptyHouse() throws NHMException {
		
		testResult(setupThings(true), efficiency, FuelType.MAINS_GAS, EmitterType.RADIATORS, true, installationYear, insulationThickness, storageVolume, true);
	}

	@Test
	public void testInstallInstantIntoEmptyHouse() throws NHMException {
		testResult(setupThings(false), efficiency, FuelType.MAINS_GAS, EmitterType.RADIATORS, true, installationYear, insulationThickness, cylinderVolume, false);
	}

	private ITechnologyModel setupThings(final boolean shouldBeStorage) throws NHMException {
		final StructureModel structure = new StructureModel() {
			{
				setOnGasGrid(true);
			}
		};
		final ITechnologyModel technologies = new TechnologyModelImpl() {
		};

		
		final IPowerTable ecr = mock(IPowerTable.class);
		
		final ISettableComponentsScope components = Util.mockComponents(dims, structure, technologies, ecr);
		
		
		final CombiBoilerMeasure m = new CombiBoilerMeasure(
				dims.time,
				Util.mockWetHeatingMeasureFactory(),
				dims.technology,
				dims.structure,
				operations,
				FuelType.MAINS_GAS,
				Util.mockSizingFunction(Optional.of(1000d)),
				Util.mockCapexFunction(1000d, 2000d),
				Util.mockOpexFunction(1000, 2000, 3000),
				ConstantComponentsFunction.<Number>of(Name.of("Test"), 0d),
				efficiency,
				efficiency,
				ConstantComponentsFunction.<Number>of(Name.of("storage vol."), shouldBeStorage ? storageVolume : 0));
		
		
		return Util.applyAndGetTech(dims, m, components);
	}
	
	private void testResult(final ITechnologyModel technologies, final IComponentsFunction<Number> efficiency, final FuelType fuel, final EmitterType emitters, final boolean condensing, final int installationYear,
			final float cylinderInsulationThickness, final float cylinderVolume, final boolean isStorage) {
		final IHeatSource heatSource = technologies.getIndividualHeatSource();
		Assert.assertTrue(heatSource instanceof ICombiBoiler);

		final ICombiBoiler boiler = (ICombiBoiler) heatSource;
		Assert.assertEquals(Efficiency.fromDouble(efficiency.compute(null, null).doubleValue()),
                            boiler.getSummerEfficiency());
		Assert.assertEquals(Efficiency.fromDouble(efficiency.compute(null, null).doubleValue()),
                            boiler.getWinterEfficiency());
		Assert.assertEquals(fuel, boiler.getFuel());
		Assert.assertEquals(condensing, boiler.isCondensing());
		Assert.assertEquals(installationYear, boiler.getInstallationYear());
		Assert.assertEquals(3000d, boiler.getAnnualOperationalCost(), 0d);
		
		final ISpaceHeater main = technologies.getPrimarySpaceHeater();
		Assert.assertTrue(main instanceof ICentralHeatingSystem);
		final ICentralHeatingSystem centralHeating = (ICentralHeatingSystem) main;

		Assert.assertTrue(centralHeating.getControls().contains(HeatingSystemControlType.PROGRAMMER));
		Assert.assertTrue(centralHeating.getControls().contains(HeatingSystemControlType.THERMOSTATIC_RADIATOR_VALVE));
		Assert.assertTrue(centralHeating.getControls().contains(HeatingSystemControlType.ROOM_THERMOSTAT));

		WaterHeatingSystemTestUtil.testCombinedWaterHeatingSystem(technologies);
		
		if (isStorage) {
			Assert.assertTrue(heatSource instanceof IStorageCombiBoiler);
			final IStorageCombiBoiler storageCombi = (IStorageCombiBoiler)boiler;
			final IWaterTank store = storageCombi.getStore();
			Assert.assertEquals(storageVolume, store.getVolume(), Util.ERROR_DELTA);

		}
		final ICentralWaterSystem centralHotWater = technologies.getCentralWaterSystem();
		Assert.assertNull("Cylinder found in water system connected to combi", centralHotWater.getStore());
	}

	/**
	 * These tests for the base class ought still to work, because the suitability constraints are unchanged.
	 */
	@Test
	public void testBasicSuitability() {
		AbstractGasBoilerMeasureTest.testSuitabilityFalseGivenExistingCommunitySpaceHeating(dims, 
				new CombiBoilerMeasure(
						dims.time,
						Util.mockWetHeatingMeasureFactory(),
						dims.technology, 
						dims.structure, 
						operations, 
						
						FuelType.MAINS_GAS,
						Util.mockSizingFunction(Optional.of(10d)),
						Util.mockCapexFunction(10, 20),
						Util.mockOpexFunction(10, 20, 30),
						ConstantComponentsFunction.<Number>of(Name.of("Test"), 0d),
						efficiency,
						efficiency,
                        ConstantComponentsFunction.<Number>of(Name.of("stor vol"), 100d)
						));
		AbstractGasBoilerMeasureTest.testSuitabilityFalseGivenExistingCommunityWaterHeating(dims, 
				new CombiBoilerMeasure(
				dims.time,
				Util.mockWetHeatingMeasureFactory(),
				dims.technology, 
				dims.structure, 
				operations, 
				
				FuelType.MAINS_GAS,
				Util.mockSizingFunction(Optional.of(10d)),
				Util.mockCapexFunction(10, 20),
				Util.mockOpexFunction(10, 20, 30),
				ConstantComponentsFunction.<Number>of(Name.of("Test"), 0d),
				efficiency,
				efficiency,
                ConstantComponentsFunction.<Number>of(Name.of("stor vol"), 100d)
				));
		AbstractGasBoilerMeasureTest.testSuitabilityOnGas(dims, new CombiBoilerMeasure(
				dims.time,
				Util.mockWetHeatingMeasureFactory(),
				dims.technology, 
				dims.structure, 
				operations, 
				
				FuelType.MAINS_GAS,
				Util.mockSizingFunction(Optional.of(10d)),
				Util.mockCapexFunction(10, 20),
				Util.mockOpexFunction(10, 20, 30),
				ConstantComponentsFunction.<Number>of(Name.of("Test"), 0d),
				efficiency,
				efficiency,
                ConstantComponentsFunction.<Number>of(Name.of("stor vol"), 100d)
				));
		AbstractGasBoilerMeasureTest.testSuitabilityWithStandardHeatingSystemInstalledPreviously(dims, new CombiBoilerMeasure(
				dims.time,
				Util.mockWetHeatingMeasureFactory(),
				dims.technology, 
				dims.structure, 
				operations, 
				
				FuelType.MAINS_GAS,
				Util.mockSizingFunction(Optional.of(10d)),
				Util.mockCapexFunction(10, 20),
				Util.mockOpexFunction(10, 20, 30),
				ConstantComponentsFunction.<Number>of(Name.of("Test"), 0d),
				efficiency,
				efficiency,
                ConstantComponentsFunction.<Number>of(Name.of("stor vol"), 100d)
				));
	}
}
