package uk.org.cse.nhm.simulation.measure.dh;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.emf.technologies.EmitterType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IMainWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.ISpaceHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl;
import uk.org.cse.nhm.hom.emf.util.Efficiency;
import uk.org.cse.nhm.hom.emf.util.impl.TechnologyOperations;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.types.BuiltFormType;
import uk.org.cse.nhm.hom.types.MorphologyType;
import uk.org.cse.nhm.simulation.measure.boilers.DistrictHeatingMeasure;
import uk.org.cse.nhm.simulation.measure.util.Util;
import uk.org.cse.nhm.simulation.measure.util.Util.MockDimensions;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;

public class DistrictHeatingMeasureTest {
	final float volume = 110;
	final float insulation = 50;
	final IComponentsFunction<Number> efficiency = new ConstantComponentsFunction<Number>(null, 0.85f);
	
	private DistrictHeatingMeasure measure;
	private MockDimensions dims;
	
	@Before
	public void createMeasure() {
		dims = Util.getMockDimensions();
		measure = new DistrictHeatingMeasure(
				dims.time, 
				Util.mockWetHeatingMeasureFactory(),
				dims.technology, 
				dims.structure, 
				new TechnologyOperations(),
				
				Util.mockSizingFunction(Optional.of(1d)),
				Util.mockCapexFunction(1d, 2d), 
				Util.mockOpexFunction(1d, 2d, 3d),
				ConstantComponentsFunction.<Number>of(Name.of("Test"), 0d),
                ConstantComponentsFunction.<Number>of(Name.of("Tank Size"), 110d),
				insulation, 
				efficiency,
				false);
	}
	
	@After
	public void clear() {
		measure = null;
	}
	
	@Test
	public void testSuitability() {

		final StructureModel badStructure = new StructureModel(BuiltFormType.Detached) {
			{
				setOnGasGrid(false);
			}
		};
		final StructureModel goodStructure = new StructureModel(BuiltFormType.PurposeBuiltHighRiseFlat) {
			{
				setOnGasGrid(true);
			}
		};
		final BasicCaseAttributes badCase = new BasicCaseAttributes() {
			{
				setMorphologyType(MorphologyType.HamletsAndIsolatedDwellings);
			}
		};
		final BasicCaseAttributes goodCase = new BasicCaseAttributes() {
			{
				setMorphologyType(MorphologyType.Urban);
			}
		};
		
		final IComponentsScope badComponents = mock(IComponentsScope.class);
		when(badComponents.get(dims.structure)).thenReturn(badStructure);
		when(badComponents.get(dims.basic)).thenReturn(badCase);
		
		final IComponentsScope goodComponents = mock(IComponentsScope.class);
		when(goodComponents.get(dims.structure)).thenReturn(goodStructure);
		when(goodComponents.get(dims.basic)).thenReturn(goodCase);

		Assert.assertFalse(measure.isSuitable(badComponents, ILets.EMPTY));
		Assert.assertTrue(measure.isSuitable(goodComponents, ILets.EMPTY));
	}
	
	@Test
	public void testBasicInstall() throws NHMException {
		final StructureModel structure = new StructureModel() {
			{
				setOnGasGrid(true);
			}
		};
		final ITechnologyModel technologies = new TechnologyModelImpl() {
		};
		
		final ISettableComponentsScope components = Util.mockComponents(dims, structure, technologies, mock(IPowerTable.class));
		
		final ITechnologyModel output = Util.applyAndGetTech(dims, measure, components);
	
		final ISpaceHeater mainHeating = output.getPrimarySpaceHeater();
		
		Assert.assertTrue(mainHeating instanceof ICentralHeatingSystem);
		final ICentralHeatingSystem centralHeating = (ICentralHeatingSystem) mainHeating;
		final IHeatSource heatSource = centralHeating.getHeatSource();
		Assert.assertTrue(heatSource instanceof ICommunityHeatSource);
		final ICommunityHeatSource communityHeating = (ICommunityHeatSource) heatSource;
		
		Assert.assertEquals(1983, communityHeating.getInstallationYear());
		
		Assert.assertTrue(centralHeating.getControls().contains(HeatingSystemControlType.PROGRAMMER));
		Assert.assertTrue(centralHeating.getControls().contains(HeatingSystemControlType.THERMOSTATIC_RADIATOR_VALVE));
		
		final IWaterHeater waterHeater = output.getCentralWaterSystem();
		Assert.assertTrue(waterHeater instanceof ICentralWaterSystem);
		final ICentralWaterSystem centralHotWater = (ICentralWaterSystem) waterHeater;
		final ICentralWaterHeater waterHeatSource = centralHotWater.getPrimaryWaterHeater();

		Assert.assertTrue(waterHeatSource instanceof IMainWaterHeater);
		final IMainWaterHeater mainWaterHeater = (IMainWaterHeater) waterHeatSource;
		Assert.assertEquals(communityHeating, mainWaterHeater.getHeatSource());

		Assert.assertEquals(FuelType.MAINS_GAS, communityHeating.getFuel());
		Assert.assertEquals(EmitterType.RADIATORS, centralHeating.getEmitterType());
		
		Assert.assertTrue(centralHotWater.getStore() instanceof IWaterTank);
		final IWaterTank cylinder = centralHotWater.getStore();

		Assert.assertTrue(centralHotWater.isSeparatelyTimeControlled());
		Assert.assertTrue(centralHotWater.isPrimaryPipeworkInsulated());
		Assert.assertTrue(cylinder.isThermostatFitted());
		Assert.assertTrue(cylinder.isFactoryInsulation());
		Assert.assertEquals(3d, communityHeating.getAnnualOperationalCost(), 0d);
		Assert.assertEquals(Efficiency.fromDouble(0.85), communityHeating.getHeatEfficiency());
	}
}
