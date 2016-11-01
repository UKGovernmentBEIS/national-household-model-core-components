package uk.org.cse.nhm.simulator.integration.tests;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;
import uk.org.cse.nhm.energycalculator.api.types.WallType;
import uk.org.cse.nhm.hom.structure.IWall;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.hom.types.RegionType;
import uk.org.cse.nhm.language.builder.function.MapWallTypes;
import uk.org.cse.nhm.language.definition.enums.XWallConstructionTypeRule;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.components.IFlags;

public class WallInsulationMeasureIntegrationTests extends SimulatorIntegrationTest {
	@Test
	public void rValueFunction() throws NHMException, InterruptedException {
		final IntegrationTestOutput output = super.runSimulation(dataService, loadScenario("insulation/rvaluebyfunction.s"), true, Collections.<Class<?>>emptySet());
		
		int affectedCount = 0;
		for (final IDwelling dwelling : output.state.getDwellings()) {
			final IFlags flags = output.state.get(output.flags, dwelling);
			if (flags.testFlag("affected")) {
				affectedCount++;
				boolean hasGoodWall = false;
				for (final Storey s : output.state.get(output.structure, dwelling).getStoreys()) {
					for (final IWall wall : s.getWalls()) {
						if (wall.getWallConstructionType().getWallType() == WallType.External &&
								!(wall.getWallInsulationTypes().contains(WallInsulationType.External) || 
										wall.getWallInsulationTypes().contains(WallInsulationType.Internal))
								) {
							final double r = output.state.get(output.basicAttributes, dwelling).getRegionType() == RegionType.London ?
									0.1 : 0.2;
							
							final double r2 = r * 33d;
							
							final double olduvalue = 1/(1 / wall.getUValue() - r2);
							
							if (olduvalue > 1.395 || olduvalue < 1.605) {
								hasGoodWall = true;
							}
						}
					}
				}
				Assert.assertTrue(output.state.get(output.basicAttributes, dwelling).getAacode() + " has at least one correctly insulated wall", hasGoodWall);
			}
		}
		
		Assert.assertTrue("Some people were affected", affectedCount>0);
	}
	
	@Test
	public void uValueFunction() throws NHMException, InterruptedException {
		final IntegrationTestOutput output = super.runSimulation(dataService, loadScenario("insulation/uvaluebyfunction.s"), true, Collections.<Class<?>>emptySet());
		
		int affectedCount = 0;
		for (final IDwelling dwelling : output.state.getDwellings()) {
			final IFlags flags = output.state.get(output.flags, dwelling);
			if (flags.testFlag("affected")) {
				affectedCount++;
				for (final Storey s : output.state.get(output.structure, dwelling).getStoreys()) {
					for (final IWall wall : s.getWalls()) {
						if (wall.getWallConstructionType().getWallType() == WallType.External) {
							// u-value was between 20 and 25 in the scenario, then we did 50mm of r=0.1 in london and 0.2 elsewhere
							final double u = output.state.get(output.basicAttributes, dwelling).getRegionType() == RegionType.London ?
									4 : 8;
							
							Assert.assertEquals("U-value as specified", u, wall.getUValue(), 0.01);
							
							Assert.assertEquals("thickness updated", 33d, wall.getWallInsulationThickness(WallInsulationType.FilledCavity), 0.01);
						}
					}
				}
			}
		}
		
		Assert.assertTrue("Some people were affected", affectedCount>0);
	}

    @Ignore // this test is nondeterministic and has stopped working; have not fixed yet.
	@Test
	public void suitabilityOverrides() throws NHMException, InterruptedException {
		final IntegrationTestOutput output = super.runSimulation(dataService, loadScenario("insulation/suitability.s"), true, Collections.<Class<?>>emptySet());
		
		boolean atLeastOneDefault = false;
		boolean atLeastOneDouble = false;
		boolean atLeastOneSandstone = false;
		
		for (final IDwelling dwelling : output.state.getDwellings()) {
			final IFlags flags = output.state.get(output.flags, dwelling);
			final StructureModel structure = output.state.get(output.structure, dwelling);
			
			final boolean gotDouble = flags.testFlag("double-insulation");
			final boolean gotCavityOnSandstone = flags.testFlag("sandstone-cwi");
			final boolean gotDefaultCavity = flags.testFlag("default-cwi");
			final boolean wasFullyInsulatedCavity = flags.testFlag("fully-preinsulated-cavity");
			
			final Set<WallConstructionType> wct = new HashSet<>();
			final Set<WallInsulationType> wit = new HashSet<>();
			for (final Storey s : structure.getStoreys()) {
				for (final IWall wall : s.getWalls()) {
					wct.add(wall.getWallConstructionType());
					wit.addAll(wall.getWallInsulationTypes());
				}
			}
			
			if (gotDouble) {
				atLeastOneDouble = true;
				boolean atLeastOneWallHasAtLeast100mmCavity = false;
				for (final Storey s : structure.getStoreys()) {
					for (final IWall wall : s.getWalls()) {
						if (MapWallTypes.getPredicateMatching(XWallConstructionTypeRule.AnyCavity).apply(wall.getWallConstructionType())) {
							if (wall.getWallInsulationThickness(WallInsulationType.FilledCavity) >= 100d) {
								atLeastOneWallHasAtLeast100mmCavity = true;
								break;
							}
						}
					}
					Assert.assertTrue(
							"Insulation got added to a wall", atLeastOneWallHasAtLeast100mmCavity);
				}
			} else if (gotCavityOnSandstone) {
				atLeastOneSandstone = true;
				boolean hadSandstone = false;
				for (final Storey s : structure.getStoreys()) {
					for (final IWall wall : s.getWalls()) {
						if (wall.getWallConstructionType().equals(WallConstructionType.Sandstone)) {
							hadSandstone = true;
							Assert.assertTrue("Sandstone wall marked as having filled cavity insulation", 
									wall.getWallInsulationTypes().contains(WallInsulationType.FilledCavity));
						}
					}
				}
				Assert.assertTrue("At least one sandstone wall", hadSandstone);
			} else if (gotDefaultCavity) {
				atLeastOneDefault = true;
				boolean insulatedACavity = false;
				boolean otherInsulation = false;
				for (final Storey s : structure.getStoreys()) {
					for (final IWall wall : s.getWalls()) {
						if (EnumSet.of(
								WallConstructionType.Cavity, 
								WallConstructionType.MetalFrame,
								WallConstructionType.TimberFrame,
								WallConstructionType.SystemBuild)
									.contains(wall.getWallConstructionType())) {
							Assert.assertTrue("Cavities should have been insulated", wall.getWallInsulationTypes().contains(WallInsulationType.FilledCavity));
							insulatedACavity = true;
						}
						otherInsulation = otherInsulation || wall.getWallInsulationTypes().contains(WallInsulationType.Internal) ||
								wall.getWallInsulationTypes().contains(WallInsulationType.External);
					}
				}
				
				Assert.assertTrue("Insulated at least one cavity wall", insulatedACavity);
				Assert.assertFalse("Should not have been insulated beforehand unless there's some non-cavity insulation " + output.state.get(output.flags, dwelling), 
						wasFullyInsulatedCavity && !otherInsulation);
			}
		}
		
		Assert.assertTrue("Default behaviour occurred at least once", atLeastOneDefault);
		Assert.assertTrue("Double-insulating occurred at least once", atLeastOneDouble);
		Assert.assertTrue("Cavity insulating sandstone occurred at least once", atLeastOneSandstone);
	}
}
