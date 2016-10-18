package uk.org.cse.stockimport.hom.impl.steps.services.heating;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static uk.org.cse.nhm.hom.emf.technologies.FuelType.BIOMASS_PELLETS;
import static uk.org.cse.nhm.hom.emf.technologies.FuelType.ELECTRICITY;
import static uk.org.cse.nhm.hom.emf.technologies.FuelType.HOUSE_COAL;
import static uk.org.cse.nhm.hom.emf.technologies.FuelType.MAINS_GAS;
import static uk.org.cse.nhm.hom.emf.technologies.FuelType.OIL;
import static uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType.AIR_SOURCE_HEAT_PUMP;
import static uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType.BACK_BOILER;
import static uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType.COMBI;
import static uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType.COMMUNITY_HEATING_WITHOUT_CHP;
import static uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType.COMMUNITY_HEATING_WITH_CHP;
import static uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType.GROUND_SOURCE_HEAT_PUMP;
import static uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType.ROOM_HEATER;
import static uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType.STANDARD;
import static uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType.STORAGE_COMBI;
import static uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType.WARM_AIR;

import org.junit.Test;

import com.google.common.base.Optional;

import junit.framework.Assert;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatPumpSourceType;
import uk.org.cse.nhm.hom.emf.technologies.ICommunityCHP;
import uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IHeatPump;
import uk.org.cse.nhm.hom.emf.technologies.IHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilerImpl;
import uk.org.cse.nhm.hom.emf.technologies.boilers.impl.InstantaneousCombiBoilerImpl;
import uk.org.cse.nhm.hom.emf.technologies.boilers.impl.StorageCombiBoilerImpl;
import uk.org.cse.nhm.hom.emf.util.Efficiency;
import uk.org.cse.stockimport.domain.services.ISpaceHeatingDTO;
import uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType;

/**
 * Check that the boiler builder builds the right kind of boilers.
 * 
 * This test is overspecific in a lot of places, expecting specific boiler interface implementations
 * but that is OK. Better to have an overspecific test than a test that doesn't fail when it should
 * 
 * @author hinton
 *
 */
public class HeatSourceBuilderTest {
	@Test
	public void testGetResponsiveness() {
		final HeatSourceBuilder bb = new HeatSourceBuilder();

		
		assertEquals(1.00, bb.getResponsiveness(STANDARD, MAINS_GAS));
		assertEquals(1.00, bb.getResponsiveness(COMBI, MAINS_GAS));
		assertEquals(1.00, bb.getResponsiveness(STORAGE_COMBI, MAINS_GAS));
		assertEquals(1.00, bb.getResponsiveness(BACK_BOILER, MAINS_GAS));
		assertEquals(1.00, bb.getResponsiveness(STANDARD, OIL));
		assertEquals(0.75, bb.getResponsiveness(STANDARD, HOUSE_COAL));
		assertEquals(0.75, bb.getResponsiveness(STANDARD, ELECTRICITY));
		// ignore storage heater, because it's problematic.
		assertEquals(1.00, bb.getResponsiveness(ROOM_HEATER, ELECTRICITY));
		assertEquals(1.00, bb.getResponsiveness(WARM_AIR, MAINS_GAS));
		assertEquals(0.75, bb.getResponsiveness(WARM_AIR, ELECTRICITY));
		assertEquals(1.00, bb.getResponsiveness(COMMUNITY_HEATING_WITHOUT_CHP, MAINS_GAS));
		assertEquals(1.00, bb.getResponsiveness(COMMUNITY_HEATING_WITH_CHP, MAINS_GAS));
		assertEquals(0.75, bb.getResponsiveness(STANDARD, BIOMASS_PELLETS));
		assertEquals(1.00, bb.getResponsiveness(GROUND_SOURCE_HEAT_PUMP, ELECTRICITY));
		assertEquals(1.00, bb.getResponsiveness(AIR_SOURCE_HEAT_PUMP, ELECTRICITY));
	}
	
	@Test
	public void testBuildCommunityCHP() {
		final HeatSourceBuilder builder = new HeatSourceBuilder();
		
		final ISpaceHeatingDTO dto = mock(ISpaceHeatingDTO.class);
		when(dto.getSpaceHeatingSystemType()).thenReturn(SpaceHeatingSystemType.COMMUNITY_HEATING_WITH_CHP);
		when(dto.getMainHeatingFuel()).thenReturn(FuelType.MAINS_GAS);
		when(dto.getChpFraction()).thenReturn(Optional.<Double>absent());
		when(dto.getCommunityChargingUsageBased()).thenReturn(Optional.of(true));
		when(dto.getBasicEfficiency()).thenReturn(0.45);
		when(dto.getInstallationYear()).thenReturn(Optional.<Integer>absent());
		final ICommunityHeatSource hs = (ICommunityHeatSource) builder.createHeatSource(1900, dto);
		Assert.assertTrue(hs instanceof ICommunityCHP);
		Assert.assertEquals(FuelType.MAINS_GAS, hs.getFuel());
		Assert.assertEquals(Efficiency.fromDouble(0.45), hs.getHeatEfficiency());
		Assert.assertEquals(Efficiency.fromDouble(0.35), ((ICommunityCHP) hs).getElectricalEfficiency());
	}
	
	@Test
	public void testBuildCommunityHeatOnly() {
		final HeatSourceBuilder builder = new HeatSourceBuilder();
		
		final ISpaceHeatingDTO dto = mock(ISpaceHeatingDTO.class);
		when(dto.getSpaceHeatingSystemType()).thenReturn(SpaceHeatingSystemType.COMMUNITY_HEATING_WITHOUT_CHP);
		when(dto.getMainHeatingFuel()).thenReturn(FuelType.MAINS_GAS);
		when(dto.getChpFraction()).thenReturn(Optional.<Double>absent());
		when(dto.getCommunityChargingUsageBased()).thenReturn(Optional.of(true));
		when(dto.getInstallationYear()).thenReturn(Optional.<Integer>absent());
		when(dto.getBasicEfficiency()).thenReturn(0.45);
		final ICommunityHeatSource hs = (ICommunityHeatSource) builder.createHeatSource(1900, dto);
		Assert.assertFalse(hs instanceof ICommunityCHP);
		Assert.assertEquals(FuelType.MAINS_GAS, hs.getFuel());
		Assert.assertEquals(Efficiency.fromDouble(0.45), hs.getHeatEfficiency());
	}
	
	@Test
	public void testBuildHeatPump() {
		final HeatSourceBuilder builder = new HeatSourceBuilder();
	
		final ISpaceHeatingDTO dto = mock(ISpaceHeatingDTO.class);
		when(dto.getSpaceHeatingSystemType()).thenReturn(SpaceHeatingSystemType.GROUND_SOURCE_HEAT_PUMP);
		when(dto.getBasicEfficiency()).thenReturn(3d);
		when(dto.getMainHeatingFuel()).thenReturn(FuelType.ELECTRICITY);
		when(dto.getInstallationYear()).thenReturn(Optional.<Integer>absent());
		when(dto.getFlueType()).thenReturn(Optional.<FlueType>absent());
		
		final IHeatPump pump = (IHeatPump) builder.createHeatSource(1900, dto);
		
		Assert.assertEquals(HeatPumpSourceType.GROUND, pump.getSourceType());
		Assert.assertEquals(FlueType.NOT_APPLICABLE, pump.getFlueType());
		Assert.assertEquals(Efficiency.fromDouble(3d), pump.getCoefficientOfPerformance());
	}
	
	@Test
	public void testBuildASHeatPump() {
		final HeatSourceBuilder builder = new HeatSourceBuilder();
		
		final ISpaceHeatingDTO dto = mock(ISpaceHeatingDTO.class);
		when(dto.getSpaceHeatingSystemType()).thenReturn(SpaceHeatingSystemType.AIR_SOURCE_HEAT_PUMP);
		when(dto.getBasicEfficiency()).thenReturn(3d);
		when(dto.getMainHeatingFuel()).thenReturn(FuelType.MAINS_GAS);
		when(dto.getInstallationYear()).thenReturn(Optional.<Integer>absent());
		when(dto.getFlueType()).thenReturn(Optional.<FlueType>absent());
		
		final IHeatPump pump = (IHeatPump) builder.createHeatSource(1900, dto);
		
		Assert.assertEquals(HeatPumpSourceType.AIR, pump.getSourceType());
		Assert.assertEquals(FlueType.BALANCED_FLUE, pump.getFlueType());
		Assert.assertEquals(Efficiency.fromDouble(3d), pump.getCoefficientOfPerformance());
	}
	
	@Test
	public void testBuildStandardBoiler() {
		final HeatSourceBuilder builder = new HeatSourceBuilder();
		
		final ISpaceHeatingDTO dto = mock(ISpaceHeatingDTO.class);
		when(dto.getSpaceHeatingSystemType()).thenReturn(SpaceHeatingSystemType.STANDARD);
		when(dto.getMainHeatingFuel()).thenReturn(FuelType.OIL);
		when(dto.getSummerEfficiency()).thenReturn(Optional.of(1d));
		when(dto.getWinterEfficiency()).thenReturn(Optional.of(1d));
		when(dto.getFlueType()).thenReturn(Optional.of(FlueType.BALANCED_FLUE));
		when(dto.getInstallationYear()).thenReturn(Optional.<Integer>absent());
        when(dto.getCondensing()).thenReturn(Optional.<Boolean> absent());
		
		when(dto.getStorageCombiCylinderVolume()).thenReturn(Optional.<Double>absent());
		when(dto.getStorageCombiCylinderInsulationThickness()).thenReturn(Optional.<Double>absent());
		when(dto.getStorageCombiSolarVolume()).thenReturn(Optional.<Double>absent());
		
		final IBoiler b = (IBoiler) builder.createHeatSource(1900, dto);
		// this test is a bit evil, but necessary; don't know how else to say it shouldn't
		// implement any of the other boiler interfaces.
		Assert.assertTrue(b.getClass().equals(BoilerImpl.class));
		
		Assert.assertEquals(FuelType.OIL, b.getFuel());
		Assert.assertEquals(1d, b.getBasicResponsiveness());
		Assert.assertEquals(FlueType.BALANCED_FLUE, b.getFlueType());
		Assert.assertEquals(Efficiency.fromDouble(1d), b.getSummerEfficiency());
		Assert.assertEquals(Efficiency.fromDouble(1d), b.getWinterEfficiency());
	}
	
	@Test
	public void testBuildStorageCombiBoiler() {
		final HeatSourceBuilder builder = new HeatSourceBuilder();
		
		final ISpaceHeatingDTO dto = mock(ISpaceHeatingDTO.class);
		when(dto.getSpaceHeatingSystemType()).thenReturn(SpaceHeatingSystemType.STORAGE_COMBI);
		when(dto.getMainHeatingFuel()).thenReturn(FuelType.MAINS_GAS);
		when(dto.getSummerEfficiency()).thenReturn(Optional.of(1d));
		when(dto.getWinterEfficiency()).thenReturn(Optional.of(1d));
		when(dto.getFlueType()).thenReturn(Optional.of(FlueType.BALANCED_FLUE));
		when(dto.getInstallationYear()).thenReturn(Optional.<Integer>absent());
		
		when(dto.getStorageCombiCylinderVolume()).thenReturn(Optional.<Double>absent());
		when(dto.getStorageCombiCylinderInsulationThickness()).thenReturn(Optional.<Double>absent());
		when(dto.getStorageCombiSolarVolume()).thenReturn(Optional.<Double>absent());
        when(dto.getCondensing()).thenReturn(Optional.<Boolean> absent());
		
		final IBoiler b = (IBoiler) builder.createHeatSource(1900, dto);
		
		Assert.assertTrue(b.getClass().equals(StorageCombiBoilerImpl.class));
		
		Assert.assertEquals(FuelType.MAINS_GAS, b.getFuel());
		Assert.assertEquals(1d, b.getBasicResponsiveness());
		Assert.assertEquals(FlueType.BALANCED_FLUE, b.getFlueType());
		Assert.assertEquals(Efficiency.fromDouble(1d), b.getSummerEfficiency());
		Assert.assertEquals(Efficiency.fromDouble(1d), b.getWinterEfficiency());
	}
	
	@Test
	public void testBuildInstantCombiBoiler() {
		final HeatSourceBuilder builder = new HeatSourceBuilder();
		
		final ISpaceHeatingDTO dto = mock(ISpaceHeatingDTO.class);
		when(dto.getSpaceHeatingSystemType()).thenReturn(SpaceHeatingSystemType.COMBI);
		when(dto.getInstallationYear()).thenReturn(Optional.<Integer>absent());
		when(dto.getMainHeatingFuel()).thenReturn(FuelType.MAINS_GAS);
		when(dto.getSummerEfficiency()).thenReturn(Optional.of(1d));
		when(dto.getWinterEfficiency()).thenReturn(Optional.of(1d));
		when(dto.getFlueType()).thenReturn(Optional.of(FlueType.BALANCED_FLUE));
		
		when(dto.getStorageCombiCylinderVolume()).thenReturn(Optional.<Double>absent());
		when(dto.getStorageCombiCylinderInsulationThickness()).thenReturn(Optional.<Double>absent());
		when(dto.getStorageCombiSolarVolume()).thenReturn(Optional.<Double>absent());
        when(dto.getCondensing()).thenReturn(Optional.<Boolean> absent());
		
		final IHeatSource hs = builder.createHeatSource(1900, dto);
		final IBoiler b = (IBoiler) hs;
		
		Assert.assertTrue(b.getClass().equals(InstantaneousCombiBoilerImpl.class));
		Assert.assertEquals(FuelType.MAINS_GAS, b.getFuel());
		Assert.assertEquals(1d, b.getBasicResponsiveness());
		Assert.assertEquals(FlueType.BALANCED_FLUE, b.getFlueType());
		Assert.assertEquals(Efficiency.fromDouble(1d), b.getSummerEfficiency());
		Assert.assertEquals(Efficiency.fromDouble(1d), b.getWinterEfficiency());
	}
	
	@Test
	public void testCreateHeatSourceForWaterHeating() {
		
	}
}
