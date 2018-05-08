package uk.org.cse.nhm.simulator.integration.tests;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.pojomatic.Pojomatic;

import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.energycalculator.api.types.DoorType;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue;
import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.structure.Door;
import uk.org.cse.nhm.hom.structure.IWall;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Elevation;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.simulator.integration.tests.guice.IFunctionAssertion;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IHypotheticalComponentsScope;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.components.IFlags;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;

public class ResetTests extends SimulatorIntegrationTest {
	@Test
	public void reducingFloorUValueReducesEnergyUse() throws Exception{
		final IntegrationTestOutput output = super.runSimulation(
				dataService,
				loadScenario("resetting/resetFloorsDown.s"),
				true, Collections.<Class<?>>emptySet());

		for (final IDwelling d : output.state.getDwellings()) {
			final IFlags f = output.state.get(output.flags, d);

			final double ubefore = f.getRegister("u-before").get();
			final double uafter = f.getRegister("u-after").get();

			final double before = f.getRegister("energy-before").get();
			final double after = f.getRegister("energy-after").get();

			if (ubefore < uafter) {
				Assert.assertTrue("An increase in u-value should increase or not affect energy use", after >= before);
			} else if (ubefore > uafter) {
				Assert.assertTrue("A reduction in u-value should reduce or not affect energy use", before >= after);
			}
		}
	}

	private static final double MAX_DIFFERENCE = 0.1;

	private boolean different(final IPowerTable n, final IPowerTable t) {
		double a = 0;
		double b = 0;
		for (final FuelType ft : FuelType.values()) {
			a+= n.getPowerByFuel(ft);
			b+= t.getPowerByFuel(ft);
		}
		if (Math.abs(a-b)/a > 0.05) {
			return true;
		} else {
			return false;
		}
	}

	@Test
	@Ignore("This is for debugging")
	public void resetWithDiffs() throws Exception {
		super.runSimulation(
				dataService,
				loadScenario("resetting/resetDiff.s"),
				true,
				Collections.<Class<?>>emptySet(), ImmutableMap.<String, IFunctionAssertion>of(
						"diff",
						new IFunctionAssertion(){
							final Set<String> s = new HashSet<>();
							@Override
							public void evaluate(final String name, final IntegrationTestOutput output,
									final IComponentsScope scope, final ILets lets, final double value) {
								final StructureModel now = scope.get(output.structure);
								final StructureModel then = lets.get("before", IHypotheticalComponentsScope.class)
										.get().get(output.structure);

								final IPowerTable powerNow = scope.get(output.power);
								final IPowerTable powerThen = lets.get("before", IHypotheticalComponentsScope.class)
										.get().get(output.power);

								if (different(powerNow, powerThen)) {
									if (!s.contains(scope.get(output.basicAttributes).getAacode())) {
										s.add(scope.get(output.basicAttributes).getAacode());
										System.out.println(scope.get(output.basicAttributes));
										System.out.println(Pojomatic.diff(now, then));
										System.out.println();
									}
								}
							}
						}
						));
	}

	private void checkResetDoesNothing(final String scenario) throws Exception {
		final IntegrationTestOutput output = super.runSimulation(dataService, loadScenario(scenario), true, Collections.<Class<?>>emptySet());

		final Set<String> badCodes = new HashSet<>();
		double totalError = 0;
		for (final IDwelling d : output.state.getDwellings()) {
			final IFlags flags = output.state.get(output.flags, d);

			final Double before = flags.getRegister("new-energy").get();
			final Double after = flags.getRegister("old-energy").get();

			final double error = Math.abs(before - after) / before;
			totalError += error;

			if (error > MAX_DIFFERENCE) {
				String wct = "";
				final StructureModel sm = output.state.get(output.structure, d);
				for (final Storey st : sm.getStoreys()) {
					for (final IWall w : st.getImmutableWalls()) {
						if (w.isPartyWall()) continue;
						wct += " " + w.getWallConstructionType()+" " + w.getWallInsulationTypes() + " " + w.getUValue();
					}
				}
				badCodes.add(output.getAACode(d) + " " + SAPAgeBandValue.fromYear(
						output.state.get(output.basicAttributes, d).getBuildYear(),
						output.state.get(output.basicAttributes, d).getRegionType()
						) + wct);
			}
		}

		for (final String s : badCodes) {
			System.out.println(s);
		}

		System.out.println(badCodes.size());

		final double averageError = totalError / output.state.getDwellings().size();
		Assert.assertTrue(averageError + " is too big",
				averageError < 0.015);
		Assert.assertTrue(scenario, badCodes.isEmpty());

		System.out.println(String.format("Average error of %f%% for %s",
				averageError * 100, scenario));
	}

	@Test
	public void resettingWithSapDoesVeryLittle() throws Exception {
		checkResetDoesNothing("resetting/doResetDoors.s");
		checkResetDoesNothing("resetting/doResetFloors.s");
		checkResetDoesNothing("resetting/doResetRoofs.s");
		checkResetDoesNothing("resetting/doResetWalls.s");
		checkResetDoesNothing("resetting/doResetWindows.s");
		checkResetDoesNothing("resetting/doResetToSap.s");
	}

	@Test
	public void resettingDoorPropertiesWorks() throws Exception {
		final IntegrationTestOutput output = super.runSimulation(dataService, loadScenario("resetting/resetDoors.s"), true, Collections.<Class<?>>emptySet());
		for (final IDwelling d : output.state.getDwellings()) {
			final StructureModel sm = output.state.get(output.structure, d);
			for (final Elevation e : sm.getElevations().values()) {
				for (final Door door : e.getDoors()) {
					if (door.getDoorType() == DoorType.Glazed) {
						Assert.assertEquals(0.9, door.getuValue(), 0d);
						Assert.assertEquals(10, door.getArea(), 0d);
					} else {
						Assert.assertEquals(1.9, door.getuValue(), 0d);
						Assert.assertEquals(11, door.getArea(), 0d);
					}
				}
			}
		}
	}

	@Test
	public void resettingWallUValuesWorks() throws Exception {
		final IntegrationTestOutput output = super.runSimulation(dataService, loadScenario("resetting/resetWalls.s"), true, Collections.<Class<?>>emptySet());
		for (final IDwelling d : output.state.getDwellings()) {
			final StructureModel sm = output.state.get(output.structure, d);
			for (final Storey s : sm.getStoreys()) {
				for (final IWall wall : s.getImmutableWalls()) {
					final double expecteduValue;
					if (!wall.isPartyWall()) {
						switch (wall.getWallConstructionType()) {
						case Sandstone:
							if (wall.getWallInsulationThickness(WallInsulationType.External) <= 50) {
								expecteduValue = 1;
							} else {
								expecteduValue = 2;
							}
							break;
						case SolidBrick:
							if (wall.getWallInsulationThickness(WallInsulationType.External) <= 50) {
								expecteduValue = 9;
							} else {
								expecteduValue = 10;
							}
							break;
						case GraniteOrWhinstone:
						case MetalFrame:
						case Cob:
						case SystemBuild:
						case Cavity:
						case TimberFrame:
							if (wall.getWallInsulationThickness(WallInsulationType.External) <= 50) {
								expecteduValue = 11;
							} else {
								expecteduValue = 12;
							}
							break;
						default:
							Assert.fail("Model has changed without updating test - you've added a new kind of external wall");
							expecteduValue = 0;
							break;
						}
						Assert.assertEquals(expecteduValue, wall.getUValue(), 0d);
					}
				}
			}
		}
	}

	@Test
	public void canSetInterzoneSpecificHeatTransfer() throws NHMException, InterruptedException {
		super.runSimulation(
				dataService,
				loadScenario("resetting/doSetInterzoneSpecificHeatTransfer.s"),
				true,
				Collections.<Class<?>>emptySet()
			);
	}


	@Test
	public void canSetSiteExposure() throws NHMException, InterruptedException {
		super.runSimulation(
				dataService,
				loadScenario("resetting/doSetSiteExposure.s"),
				true,
				Collections.<Class<?>>emptySet()
			);
	}
}
