package uk.org.cse.stockimport.hom.impl.steps.services;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.IBackBoiler;
import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IRoomHeater;
import uk.org.cse.nhm.hom.emf.technologies.IStorageHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersFactory;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.types.MorphologyType;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.services.ISpaceHeatingDTO;
import uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType;
import uk.org.cse.stockimport.hom.impl.steps.services.heating.IHeatSourceBuilder;
import uk.org.cse.stockimport.hom.impl.steps.services.heating.IRoomHeaterBuilder;
import uk.org.cse.stockimport.hom.impl.steps.services.heating.ISecondaryHeatingSystemBuilder;
import uk.org.cse.stockimport.hom.impl.steps.services.heating.IStorageHeaterBuilder;
import uk.org.cse.stockimport.hom.impl.steps.services.heating.IWarmAirSystemBuilder;
import uk.org.cse.stockimport.repository.HouseCaseSources;
import uk.org.cse.stockimport.repository.IHouseCaseSources;

public class SpaceHeatingBuildStepTest {
	/**
	 * @issue NHM-444
	 */
	@Test
	public void testInstallSecondaryHeaterEmpty() {
		final SpaceHeatingBuildStep f = new SpaceHeatingBuildStep();
		final IRoomHeater h = mock(IRoomHeater.class);
		final ITechnologyModel m = mock(ITechnologyModel.class);
		
		f.installSecondaryHeater("test", m, h);
		
		verify(m).setSecondarySpaceHeater(h);
	}
	
	/**
	 * @issue NHM-444
	 */
	@Test
	public void testInstallSecondaryHeaterNonEmpty() {
		final SpaceHeatingBuildStep f = new SpaceHeatingBuildStep();
		final IRoomHeater h = mock(IRoomHeater.class);
		final IRoomHeater ex = mock(IRoomHeater.class);
		final ITechnologyModel m = mock(ITechnologyModel.class);
		when(m.getSecondarySpaceHeater()).thenReturn(ex);
		
		when(h.getFuel()).thenReturn(FuelType.MAINS_GAS);
		when(ex.getFuel()).thenReturn(FuelType.ELECTRICITY);
		
		f.installSecondaryHeater("test", m, h);

		verify(m, never()).setSecondarySpaceHeater(any(IRoomHeater.class));
	}
	
	@Test
	public void testGetCentralHeatingSystemCreates() {
		final ITechnologyModel model = ITechnologiesFactory.eINSTANCE.createTechnologyModel();
		
		final ICentralHeatingSystem centralHeatingSystem = SpaceHeatingBuildStep.getCentralHeatingSystem(model);
		
		Assert.assertNotNull(centralHeatingSystem);
		
		Assert.assertSame(centralHeatingSystem, model.getPrimarySpaceHeater());
		
	}
	
	@Test
	public void testGetCentralHeatingSystemInserts() {
		final ITechnologyModel model = ITechnologiesFactory.eINSTANCE.createTechnologyModel();
		final IRoomHeater roomHeater = ITechnologiesFactory.eINSTANCE.createRoomHeater();
		
		model.setSecondarySpaceHeater(roomHeater);
		
		final ICentralHeatingSystem centralHeatingSystem = SpaceHeatingBuildStep.getCentralHeatingSystem(model);
		
		Assert.assertNotNull(centralHeatingSystem);
		
		Assert.assertSame(centralHeatingSystem, model.getPrimarySpaceHeater());
		Assert.assertSame(roomHeater, model.getSecondarySpaceHeater());
	}
	
	@Test
	public void testGetCentralHeatingSystemFinds() {
		final ITechnologyModel model = ITechnologiesFactory.eINSTANCE.createTechnologyModel();
		final ICentralHeatingSystem hs = ITechnologiesFactory.eINSTANCE.createCentralHeatingSystem();
		model.setPrimarySpaceHeater(hs);
		
		final ICentralHeatingSystem centralHeatingSystem = SpaceHeatingBuildStep.getCentralHeatingSystem(model);
		
		Assert.assertNotNull(centralHeatingSystem);
		
		Assert.assertSame(hs, centralHeatingSystem);
	}
	
	@Test
	public void testCreateBoiler() {
		final SpaceHeatingBuildStep step = new SpaceHeatingBuildStep();
		final IHeatSourceBuilder builder = mock(IHeatSourceBuilder.class);
		
		final ISpaceHeatingDTO dto = mock(ISpaceHeatingDTO.class);
		when(dto.getSpaceHeatingSystemType()).thenReturn(SpaceHeatingSystemType.STANDARD);
		// EMF objects are a pain to mock, as they have interrelationship tracking stuff that needs
		// to work.
		final IBoiler boiler = IBoilersFactory.eINSTANCE.createBoiler();
		final ITechnologyModel tm = ITechnologiesFactory.eINSTANCE.createTechnologyModel();		
		step.setHeatSourceBuilder(builder);
		step.setSecondaryBuilder(mock(ISecondaryHeatingSystemBuilder.class));
		final IHouseCaseSources<IBasicDTO> p = 
				HouseCaseSources.withImmutableList("foo", 2010, Collections.singletonList((IBasicDTO) dto));
		
		when(dto.getInstallationYear()).thenReturn(Optional.of(1900));
		
		final SurveyCase model = mock(SurveyCase.class);
		when(model.getBuildYear()).thenReturn(1900);
		
		when(builder.createHeatSource(1900, dto)).thenReturn(boiler);
		
		when(model.getTechnologies()).thenReturn(tm);
		
		final BasicCaseAttributes battrs = mock(BasicCaseAttributes.class);
		when(battrs.getBuildYear()).thenReturn(1900);
		when(model.getBasicAttributes()).thenReturn(battrs );
		
		addDefaultStructure(model);

		step.build(model, p);
		
		verify(builder).createHeatSource(1900, dto);
		
		Assert.assertNotNull(tm.getPrimarySpaceHeater());
		Assert.assertSame(boiler, tm.getIndividualHeatSource());
		
		Assert.assertTrue(((ICentralHeatingSystem)tm.getPrimarySpaceHeater()).getHeatSource() == boiler);
	}

	private void addDefaultStructure(final SurveyCase model) {
		final StructureModel structure = mock(StructureModel.class);
		when(structure.isOnGasGrid()).thenReturn(true);
		when(model.getStructure()).thenReturn(structure);
	}
	
	@Test
	public void testCreateStorageHeater() {
		final SpaceHeatingBuildStep step = new SpaceHeatingBuildStep();
		final IStorageHeaterBuilder builder = mock(IStorageHeaterBuilder.class);
		
		final ISpaceHeatingDTO dto = mock(ISpaceHeatingDTO.class);
		when(dto.getSpaceHeatingSystemType()).thenReturn(SpaceHeatingSystemType.STORAGE_HEATER);
		// EMF objects are a pain to mock, as they have interrelationship tracking stuff that needs
		// to work.
		final IStorageHeater storageHeater = ITechnologiesFactory.eINSTANCE.createStorageHeater();
		final ITechnologyModel tm = ITechnologiesFactory.eINSTANCE.createTechnologyModel();		
		step.setStorageHeaterBuilder(builder);
		step.setSecondaryBuilder(mock(ISecondaryHeatingSystemBuilder.class));
		final IHouseCaseSources<IBasicDTO> p = 
				HouseCaseSources.withImmutableList("foo", 2010, Collections.singletonList((IBasicDTO) dto));
		
		final SurveyCase model = mock(SurveyCase.class);
		addDefaultStructure(model);
		
		when(builder.buildStorageHeater(dto)).thenReturn(storageHeater);
		
		when(model.getTechnologies()).thenReturn(tm);
		
		step.build(model, p);
		
		verify(builder).buildStorageHeater(dto);
			
		Assert.assertSame(storageHeater, tm.getPrimarySpaceHeater());
	}
	
	@Test
	public void testCreateWarmAirSystem() {
		final SpaceHeatingBuildStep step = new SpaceHeatingBuildStep();
		final IWarmAirSystemBuilder builder = mock(IWarmAirSystemBuilder.class);
		
		final ISpaceHeatingDTO dto = mock(ISpaceHeatingDTO.class);
		when(dto.getSpaceHeatingSystemType()).thenReturn(SpaceHeatingSystemType.WARM_AIR);
		// EMF objects are a pain to mock, as they have interrelationship tracking stuff that needs
		// to work.
		final IWarmAirSystem warmAirSystem = ITechnologiesFactory.eINSTANCE.createWarmAirSystem();
		final ITechnologyModel tm = ITechnologiesFactory.eINSTANCE.createTechnologyModel();		
		step.setWarmAirBuilder(builder);
		step.setSecondaryBuilder(mock(ISecondaryHeatingSystemBuilder.class));
		final IHouseCaseSources<IBasicDTO> p = 
				HouseCaseSources.withImmutableList("foo", 2010, Collections.singletonList((IBasicDTO) dto));
		
		final SurveyCase model = mock(SurveyCase.class);
		addDefaultStructure(model);
		
		when(builder.buildWarmAirSystem(dto)).thenReturn(warmAirSystem);
		
		when(model.getTechnologies()).thenReturn(tm);
		
		step.build(model, p);
		
		verify(builder).buildWarmAirSystem(dto);
		
		Assert.assertSame(warmAirSystem, tm.getPrimarySpaceHeater());
	}
	
	@Test
	public void testCreateRoomHeater() {
		final SpaceHeatingBuildStep step = new SpaceHeatingBuildStep();
		final IRoomHeaterBuilder builder = mock(IRoomHeaterBuilder.class);
		
		final ISpaceHeatingDTO dto = mock(ISpaceHeatingDTO.class);
		when(dto.getSpaceHeatingSystemType()).thenReturn(SpaceHeatingSystemType.ROOM_HEATER);
		when(dto.getMainHeatingFuel()).thenReturn(FuelType.ELECTRICITY);
		
		final IRoomHeater roomHeater = ITechnologiesFactory.eINSTANCE.createRoomHeater();
		final ITechnologyModel tm = ITechnologiesFactory.eINSTANCE.createTechnologyModel();		
		step.setRoomHeaterBuilder(builder);
		step.setSecondaryBuilder(mock(ISecondaryHeatingSystemBuilder.class));
		
		final IHouseCaseSources<IBasicDTO> p = 
				HouseCaseSources.withImmutableList("foo", 2010, Collections.singletonList((IBasicDTO) dto));
		
		final SurveyCase model = mock(SurveyCase.class);
		addDefaultStructure(model);
		when(model.getBuildYear()).thenReturn(1);
		when(builder.buildRoomHeater(1, dto)).thenReturn(roomHeater);
		
		when(model.getTechnologies()).thenReturn(tm);
		
		step.build(model, p);
		
		verify(builder).buildRoomHeater(1, dto);
		
		Assert.assertSame(roomHeater, tm.getSecondarySpaceHeater());
	}
	
	
	@Test
	public void testCreateBackBoiler() {
		final SpaceHeatingBuildStep step = new SpaceHeatingBuildStep();
		final IRoomHeaterBuilder builder = mock(IRoomHeaterBuilder.class);
		final ISpaceHeatingDTO dto = mock(ISpaceHeatingDTO.class);
		when(dto.getSpaceHeatingSystemType()).thenReturn(SpaceHeatingSystemType.BACK_BOILER);
		when(dto.getMainHeatingFuel()).thenReturn(FuelType.MAINS_GAS);

		final IBackBoiler backBoiler = ITechnologiesFactory.eINSTANCE.createBackBoiler();
		final ITechnologyModel tm = ITechnologiesFactory.eINSTANCE.createTechnologyModel();		
		step.setRoomHeaterBuilder(builder);
		step.setSecondaryBuilder(mock(ISecondaryHeatingSystemBuilder.class));
		
		final IHouseCaseSources<IBasicDTO> p = 
				HouseCaseSources.withImmutableList("foo", 2010, Collections.singletonList((IBasicDTO) dto));
		
		final SurveyCase model = mock(SurveyCase.class);
		addDefaultStructure(model);
		when(model.getBuildYear()).thenReturn(1234);
		
		when(builder.buildRoomHeater(1234, dto)).thenReturn(backBoiler);
		
		when(model.getTechnologies()).thenReturn(tm);
		
		step.build(model, p);
		
		verify(builder).buildRoomHeater(1234, dto);

		//central heating should have been added
		Assert.assertNotNull(tm.getPrimarySpaceHeater());
		Assert.assertSame(backBoiler, tm.getSecondarySpaceHeater());
		Assert.assertSame(tm.getPrimarySpaceHeater(), backBoiler.getSpaceHeater());
	}

	@Test
	public void fixGasGridShouldNotChangeOnGasGridDwellings() {
		testFixGasGrid(true, null, FuelType.MAINS_GAS, null, null);
	}

	@Test
	public void fixGasGridShouldNotChangeNonMainsGasHeatingTypes() {
		testFixGasGrid(false, null, FuelType.BIOMASS_PELLETS, null, null);
	}

	@Test
	public void fixGasGridShouldSetUrbanDwellingsToBeOnGasGrid() {
		testFixGasGrid(false, true, FuelType.MAINS_GAS, null, MorphologyType.Urban);
		testFixGasGrid(false, true, FuelType.MAINS_GAS, null, MorphologyType.TownAndFringe);
	}

	@Test
	public void fixGasGridShouldSetRuralDwellingsToUseBiomass() {
		testFixGasGrid(false, null, FuelType.MAINS_GAS, FuelType.BIOMASS_WOOD, MorphologyType.Village);
		testFixGasGrid(false, null, FuelType.MAINS_GAS, FuelType.BIOMASS_WOOD, MorphologyType.HamletsAndIsolatedDwellings);
	}

	private void testFixGasGrid(final boolean onGasGrid, final Boolean newOnGasGrid, final FuelType fuelType, final FuelType newFuelType, final MorphologyType morphology) {
		final SurveyCase model = mock(SurveyCase.class);
		final StructureModel structure = mock(StructureModel.class);
		final BasicCaseAttributes basic = mock(BasicCaseAttributes.class);
		when(model.getStructure()).thenReturn(structure);
		when(model.getBasicAttributes()).thenReturn(basic);

		when(structure.isOnGasGrid()).thenReturn(onGasGrid);
		when(basic.getMorphologyType()).thenReturn(morphology);

		final ITechnologiesFactory factory = ITechnologiesFactory.eINSTANCE;

		final ICommunityHeatSource heatSource = factory.createCommunityHeatSource();
		heatSource.setFuel(fuelType);

		new SpaceHeatingBuildStep().fixGasGridIncompatibility(model, heatSource);

		if (newFuelType != null) {
			Assert.assertEquals(newFuelType, heatSource.getFuel());
		} else {
			Assert.assertEquals(fuelType, heatSource.getFuel());
		}

		if (newOnGasGrid != null) {
			verify(structure, times(1)).setOnGasGrid(newOnGasGrid);
		} else {
			verify(structure, never()).setOnGasGrid(false);
		}
	}

	@Test
	public void fixSecondaryHeatingOffGasGridShouldHaveNoEffectOnGasGrid() {
		testFixSecondaryHeating(FuelType.MAINS_GAS, FuelType.MAINS_GAS, true);
	}

	@Test
	public void fixSecondaryHeatingOffGasGridShouldNotAffectNonMainsGas() {
		testFixSecondaryHeating(FuelType.BIOMASS_PELLETS, FuelType.BIOMASS_PELLETS, false);
	}

	@Test
	public void fixSecondaryHeatingOffGasGridShouldChangeFuelTypeToBulkLPG() {
		testFixSecondaryHeating(FuelType.MAINS_GAS, FuelType.BULK_LPG, false);
	}

	@Test
	public void fixSecondaryHeatingOffGasGridShouldDealWithNullSecondaryHeaters() {
		new SpaceHeatingBuildStep().fixSecondaryHeatingOffGasGrid(null, false);
		new SpaceHeatingBuildStep().fixSecondaryHeatingOffGasGrid(null, true);
	}

	private void testFixSecondaryHeating(final FuelType oldFuel, final FuelType expectedFuel, final boolean onGasGrid) {
		final ITechnologiesFactory factory = ITechnologiesFactory.eINSTANCE;
		final IRoomHeater secondaryHeater = factory.createRoomHeater();
		secondaryHeater.setFuel(oldFuel);
		new SpaceHeatingBuildStep().fixSecondaryHeatingOffGasGrid(secondaryHeater, onGasGrid);

		Assert.assertEquals(expectedFuel, secondaryHeater.getFuel());
	}
}
