package uk.org.cse.nhm.simulation.measure.renewables;

public class SolarHotWaterMeasureTest {

    // ToDo: once we have roof area, make work again
    /*
	 * private SolarHotWaterMeasure measure; private final float minArea = 10f;
	 * private final float minOrientation = 170f; private final float
	 * maxOrientation = 190f; private final float systemArea = 8f; private final
	 * float cylinderVolume = 100f; private final float cylinderInsulation =
	 * 50f;
	 * 
	 * @Before public void createMeasure() { measure = new
	 * SolarHotWaterMeasure(); measure.setMinimumRoofArea(minArea);
	 * measure.setMinimumOrientation(minOrientation);
	 * measure.setMaximumOrientation(maxOrientation);
	 * measure.setInstalledArea(systemArea);
	 * measure.setCylinderVolume(cylinderVolume);
	 * measure.setCylinderInsulation(cylinderInsulation); }
	 * 
	 * @After public void clearMeasure() { measure = null; }
	 * 
	 * @Test public void testBasicSuitability() { StructureModel structure =
	 * mock(StructureModel.class); ITechnologyModel tech =
	 * mock(ITechnologyModel.class);
	 * 
	 * when(structure.getRoofConstructionType()).thenReturn(RoofConstructionType.
	 * PitchedSlateOrTiles.Pitched);
	 * when(hc.getRoofs()).thenReturn(Collections.singleton(roof));
	 * 
	 * when(roof.getArea()).thenReturn(minArea);
	 * 
	 * when(roof.getRoofType()).thenReturn(RoofType.ExternalHeatLoss);
	 * 
	 * when(roof.getStructure()).thenReturn(RoofStructureType.Pitched);
	 * 
	 * when(roof.getOrientation()).thenReturn((minOrientation + maxOrientation)
	 * / 2);
	 * 
	 * Assert.assertTrue("This case should be suitable",
	 * measure.isSuitable(Util.mockComponents(hc))); }
	 * 
	 * @Test public void testSuitabilityWithExistingSystemPresent() { final
	 * HouseCase hc = mock(HouseCase.class); final Roof roof = mock(Roof.class);
	 * final SolarSystem existingSystem = mock(SolarSystem.class);
	 * 
	 * when(existingSystem.getType()).thenReturn(SolarSystemType.PV);
	 * when(existingSystem.getArea()).thenReturn(minArea);
	 * 
	 * when(hc.getRoofs()).thenReturn(Collections.singleton(roof));
	 * when(hc.getSolarSystems
	 * ()).thenReturn(Collections.singleton(existingSystem));
	 * 
	 * when(roof.getArea()).thenReturn(2*minArea);
	 * 
	 * when(roof.getRoofType()).thenReturn(RoofType.ExternalHeatLoss);
	 * 
	 * when(roof.getStructure()).thenReturn(RoofStructureType.Pitched);
	 * 
	 * when(roof.getOrientation()).thenReturn((minOrientation + maxOrientation)
	 * / 2);
	 * 
	 * Assert.assertTrue("This case should be suitable",
	 * measure.isSuitable(Util.mockComponents(hc))); }
	 * 
	 * @Test public void testUnsuitabilityFromInsufficientArea() { final
	 * HouseCase hc = mock(HouseCase.class); final Roof roof = mock(Roof.class);
	 * 
	 * when(hc.getRoofs()).thenReturn(Collections.singleton(roof));
	 * 
	 * when(roof.getArea()).thenReturn(minArea - 1);
	 * 
	 * when(roof.getRoofType()).thenReturn(RoofType.ExternalHeatLoss);
	 * 
	 * when(roof.getStructure()).thenReturn(RoofStructureType.Pitched);
	 * 
	 * when(roof.getOrientation()).thenReturn((minOrientation + maxOrientation)
	 * / 2);
	 * 
	 * Assert.assertFalse("This case should not be suitable",
	 * measure.isSuitable(Util.mockComponents(hc))); }
	 * 
	 * @Test public void testUnsuitabilityFromOvercrowding() { final HouseCase
	 * hc = mock(HouseCase.class); final Roof roof = mock(Roof.class); final
	 * SolarSystem existingSystem = mock(SolarSystem.class);
	 * 
	 * when(existingSystem.getType()).thenReturn(SolarSystemType.PV);
	 * 
	 * when(existingSystem.getArea()).thenReturn(minArea+1); // so there is
	 * minArea -1 left over
	 * 
	 * when(hc.getRoofs()).thenReturn(Collections.singleton(roof));
	 * when(hc.getSolarSystems
	 * ()).thenReturn(Collections.singleton(existingSystem));
	 * 
	 * when(roof.getArea()).thenReturn(2*minArea);
	 * 
	 * when(roof.getRoofType()).thenReturn(RoofType.ExternalHeatLoss);
	 * 
	 * when(roof.getStructure()).thenReturn(RoofStructureType.Pitched);
	 * 
	 * when(roof.getOrientation()).thenReturn((minOrientation + maxOrientation)
	 * / 2);
	 * 
	 * Assert.assertFalse("This case should not be suitable",
	 * measure.isSuitable(Util.mockComponents(hc))); }
	 * 
	 * @Test public void testUnsuitabilityFromExistingSolarHW() { final
	 * HouseCase hc = mock(HouseCase.class); final Roof roof = mock(Roof.class);
	 * final SolarSystem existingSystem = mock(SolarSystem.class);
	 * 
	 * when(existingSystem.getType()).thenReturn(SolarSystemType.EvactuatedTube);
	 * 
	 * when(existingSystem.getArea()).thenReturn(minArea-1); // so there is
	 * minArea + 1 left over
	 * 
	 * when(hc.getRoofs()).thenReturn(Collections.singleton(roof));
	 * when(hc.getSolarSystems
	 * ()).thenReturn(Collections.singleton(existingSystem));
	 * 
	 * when(roof.getArea()).thenReturn(2*minArea);
	 * 
	 * when(roof.getRoofType()).thenReturn(RoofType.ExternalHeatLoss);
	 * 
	 * when(roof.getStructure()).thenReturn(RoofStructureType.Pitched);
	 * 
	 * when(roof.getOrientation()).thenReturn((minOrientation + maxOrientation)
	 * / 2);
	 * 
	 * Assert.assertFalse("This case should not be suitable",
	 * measure.isSuitable(Util.mockComponents(hc))); }
	 * 
	 * @Test public void testUnsuitabilityFromRoofOrientation() { final
	 * HouseCase hc = mock(HouseCase.class); final Roof roof = mock(Roof.class);
	 * 
	 * when(hc.getRoofs()).thenReturn(Collections.singleton(roof));
	 * 
	 * when(roof.getArea()).thenReturn(minArea);
	 * 
	 * when(roof.getRoofType()).thenReturn(RoofType.ExternalHeatLoss);
	 * 
	 * when(roof.getStructure()).thenReturn(RoofStructureType.Pitched);
	 * 
	 * when(roof.getOrientation()).thenReturn(minOrientation - 1);
	 * 
	 * Assert.assertFalse("This case should be un suitable",
	 * measure.isSuitable(Util.mockComponents(hc))); }
	 * 
	 * @Test public void testInstallationWithoutExisting() throws
	 * NHMException { final HouseCase hc = mock(HouseCase.class);
	 * final Roof roof = mock(Roof.class);
	 * 
	 * when(hc.getRoofs()).thenReturn(Collections.singleton(roof));
	 * 
	 * when(roof.getArea()).thenReturn(minArea);
	 * 
	 * when(roof.getRoofType()).thenReturn(RoofType.ExternalHeatLoss);
	 * 
	 * when(roof.getStructure()).thenReturn(RoofStructureType.Pitched);
	 * 
	 * when(roof.getOrientation()).thenReturn((minOrientation + maxOrientation)
	 * / 2);
	 * 
	 * final MeasureAppliedResult result =
	 * measure.apply(Util.mockComponents(hc)); final HouseCase output =
	 * Util.getHouseCase(result);
	 * 
	 * Assert.assertEquals("Should be 1 solar system", 1,
	 * output.getSolarSystems().size()); for (final SolarSystem ss :
	 * output.getSolarSystems()) {
	 * Assert.assertEquals("Should be an evacuated tube system",
	 * SolarSystemType.EvactuatedTube, ss.getType());
	 * Assert.assertEquals("Should have the specified area", systemArea ,
	 * ss.getArea()); }
	 * 
	 * Assert.assertEquals("Should be 1 heating system", 1,
	 * output.getWaterHeatingSystems().size()); for (final WaterHeatingSystem
	 * whs : output.getWaterHeatingSystems()) {
	 * Assert.assertEquals("Should be a solar WHS",
	 * WaterHeatingSystemType.Solar, whs.getType());
	 * Assert.assertEquals("Should have fuel type photons", FuelType.Photons,
	 * whs.getFuelType());
	 * 
	 * Assert.assertSame("Solar system should be connected to heating system",
	 * output.getSolarSystems().iterator().next(), ((SolarWaterHeatingSystem)
	 * whs).getSolarSystem());
	 * 
	 * // check cylinder Assert.assertNotNull("Cylinder should be present",
	 * whs.getCylinder());
	 * 
	 * Assert.assertEquals("Volume as specified", cylinderVolume,
	 * whs.getCylinder().getVolume());
	 * 
	 * Assert.assertEquals("Insulation as specified", cylinderInsulation,
	 * whs.getCylinder().getInsulationThickness());
	 * 
	 * Assert.assertTrue("Cylinder in heated space",
	 * whs.getCylinder().getIsInHeatedSpace());
	 * Assert.assertEquals("Cylinder insulation = factory",
	 * CylinderInsulationType.Factory, whs.getCylinder().getInsulationType()); }
	 * }
     */
}
