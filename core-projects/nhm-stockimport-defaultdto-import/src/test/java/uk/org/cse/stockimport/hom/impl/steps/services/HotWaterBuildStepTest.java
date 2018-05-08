package uk.org.cse.stockimport.hom.impl.steps.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.nhm.hom.emf.technologies.IBackBoiler;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.IMainWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirCirculator;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersFactory;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IStorageCombiBoiler;
import uk.org.cse.stockimport.domain.IHouseCaseDTO;
import uk.org.cse.stockimport.domain.services.IWaterHeatingDTO;
import uk.org.cse.stockimport.domain.services.WaterHeatingSystemType;

public class HotWaterBuildStepTest {

    @Test
    public void testInstallSystemWaterTank() {
        final IWaterHeatingDTO dto = mock(IWaterHeatingDTO.class);
        final ICentralWaterSystem system = ITechnologiesFactory.eINSTANCE.createCentralWaterSystem();

        final ITechnologyModel tech = ITechnologiesFactory.eINSTANCE.createTechnologyModel();
        final IBoiler combi = IBoilersFactory.eINSTANCE.createBoiler();
        tech.setIndividualHeatSource(combi);
        tech.setCentralWaterSystem(system);

        ITechnologiesFactory.eINSTANCE.createMainWaterHeater();

        when(dto.getWaterHeatingSystemType()).thenReturn(Optional.of(WaterHeatingSystemType.STANDARD_BOILER));
        when(dto.getCylinderVolume()).thenReturn(Optional.of(110d));

        HotWaterBuildStep.installWaterTank(dto, tech);
        Assert.assertNotNull("Tank not installed", system.getStore());
    }

    @Test
    public void testSetWaterTankDetailsFromDTO() throws Exception {
        final IWaterHeatingDTO dto = mock(IWaterHeatingDTO.class);
        final IWaterTank swt = mock(IWaterTank.class);

        when(dto.getCylinderVolume()).thenReturn(Optional.of(110d));
        when(dto.getCylinderInsulationThickness()).thenReturn(Optional.of(150d));
        when(dto.getCylinderFactoryInsulated()).thenReturn(Optional.of(false));
        when(dto.getCylinderThermostatPresent()).thenReturn(Optional.of(true));

        HotWaterBuildStep.setWaterTankDetailsFromDTO(swt, dto);

        verify(swt).setVolume(110d);
        verify(swt).setInsulation(150d);
        verify(swt).setThermostatFitted(true);
        verify(swt).setFactoryInsulation(false);
    }

    @Test
    public void testCreateHotWaterSystem() {
        final ITechnologyModel tech = ITechnologiesFactory.eINSTANCE.createTechnologyModel();
        final ICentralWaterSystem centralWaterSystem = HotWaterBuildStep.getCentralWaterSystemOrCreate(tech);
        Assert.assertSame(centralWaterSystem, tech.getCentralWaterSystem());
        Assert.assertNotNull(centralWaterSystem);
        final ICentralWaterSystem centralWaterSystem2 = HotWaterBuildStep.getCentralWaterSystemOrCreate(tech);
        Assert.assertSame(centralWaterSystem, centralWaterSystem2);
    }

    @Test
    public void testAttachWaterHeater() {
        final ITechnologyModel tech = ITechnologiesFactory.eINSTANCE.createTechnologyModel();
        final ICentralWaterHeater heater = ITechnologiesFactory.eINSTANCE.createMainWaterHeater();
        HotWaterBuildStep.attachCentralWaterHeater(tech, heater);

        Assert.assertSame(heater, tech.getCentralWaterSystem().getPrimaryWaterHeater());
    }

    @Test
    public void testConnectToMainHeatSource() {
        final ITechnologyModel tech = ITechnologiesFactory.eINSTANCE.createTechnologyModel();
        final IBoiler boiler = IBoilersFactory.eINSTANCE.createBoiler();
        tech.setIndividualHeatSource(boiler);

        final ICentralWaterHeater connection = HotWaterBuildStep.connectToMainHeatSource(tech, IBoiler.class);

        Assert.assertTrue(connection instanceof IMainWaterHeater);
        Assert.assertSame(((IMainWaterHeater) connection).getHeatSource(), boiler);
    }

    @Test
    public void testConnectToWarmAir() {
        final ITechnologyModel tech = ITechnologiesFactory.eINSTANCE.createTechnologyModel();
        final IWarmAirSystem system = ITechnologiesFactory.eINSTANCE.createWarmAirSystem();
        tech.setPrimarySpaceHeater(system);
        final ICentralWaterHeater cwh = HotWaterBuildStep.connectToWarmAir(tech);
        Assert.assertTrue(cwh instanceof IWarmAirCirculator);
        Assert.assertSame(((IWarmAirCirculator) cwh).getWarmAirSystem(), system);
    }

    @Test
    public void testConnectToBackBoiler() {
        final ITechnologyModel tech = ITechnologiesFactory.eINSTANCE.createTechnologyModel();
        final IBackBoiler rh = ITechnologiesFactory.eINSTANCE.createBackBoiler();
        tech.setSecondarySpaceHeater(rh);
        final ICentralWaterHeater chw = HotWaterBuildStep.connectToBackBoiler(tech);
        Assert.assertTrue(chw instanceof IMainWaterHeater);
        Assert.assertSame(((IMainWaterHeater) chw).getHeatSource(), rh);
    }

    @Test
    public void testShouldAttachToEmptyMainHeating() {
        final ITechnologyModel tech = ITechnologiesFactory.eINSTANCE.createTechnologyModel();

        for (final WaterHeatingSystemType type : WaterHeatingSystemType.values()) {
            Assert.assertEquals(type + "",
                    type == WaterHeatingSystemType.BACK_BOILER,
                    HotWaterBuildStep.shouldAttachToMainHeating(type, tech));
        }
    }

    @Test
    public void testShouldAttachToBoiler() {
        final ITechnologyModel tech = ITechnologiesFactory.eINSTANCE.createTechnologyModel();
        final IBoiler boiler = IBoilersFactory.eINSTANCE.createBoiler();
        tech.setIndividualHeatSource(boiler);

        for (final WaterHeatingSystemType type : WaterHeatingSystemType.values()) {
            switch (type) {
                case BACK_BOILER:
                case STANDARD_BOILER:
                    Assert.assertTrue(type + "", HotWaterBuildStep.shouldAttachToMainHeating(type, tech));
                    break;
                case WARM_AIR:
                case AIR_SOURCE_HEAT_PUMP:
                case COMMUNITY:
                case COMMUNITY_CHP:
                case GROUND_SOURCE_HEAT_PUMP:
                case MULTIPOINT:
                case SINGLEPOINT:
                    Assert.assertFalse(HotWaterBuildStep.shouldAttachToMainHeating(type, tech));
                    break;
                default:
                    Assert.fail();
            }
        }
    }

    @Test
    public void testPrimaryPipeWorkInsulation() {
        final IHouseCaseDTO house = mock(IHouseCaseDTO.class);
        final HotWaterBuildStep buildStep = new HotWaterBuildStep();
        when(house.getBuildYear()).thenReturn(1995);
        Assert.assertFalse(buildStep.getPipeWorkInsulation(house));
        when(house.getBuildYear()).thenReturn(1996);
        Assert.assertTrue(buildStep.getPipeWorkInsulation(house));
    }

    @Test
    public void testMustHaveTank() {
        final IMainWaterHeater primary = mock(IMainWaterHeater.class);
        final IBoiler boiler = mock(IBoiler.class);
        when(primary.getHeatSource()).thenReturn(boiler);

        Assert.assertTrue(HotWaterBuildStep.mustHaveTank(primary));

        final IStorageCombiBoiler storage = mock(IStorageCombiBoiler.class);
        when(primary.getHeatSource()).thenReturn(storage);
        Assert.assertFalse(HotWaterBuildStep.mustHaveTank(primary));

        final IWarmAirCirculator invalidPrimary = mock(IWarmAirCirculator.class);
        Assert.assertTrue(HotWaterBuildStep.mustHaveTank(invalidPrimary));
    }

    @Test
    public void testInstallNewWaterHeater() {

    }
}
