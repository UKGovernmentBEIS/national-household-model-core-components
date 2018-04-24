package uk.org.cse.nhm.simulator.integration.tests;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.hom.money.FinancialAttributes;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.IPrimarySpaceHeater;
import uk.org.cse.nhm.hom.emf.technologies.IRoomHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.people.People;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.components.IFlags;
import uk.org.cse.nhm.types.MainHeatingSystemType;

public class HouseFunctionTest extends SimulatorIntegrationTest {
	@Test
	public void obligationAndAnnualCostWork() throws Exception {
		 super.runSimulation(dataService, loadScenario("predict/predict-an-obligation.nhm"), true, Collections.<Class<?>>emptySet());
	}
	
	@Test
	public void predictionFunctionWorks() throws Exception {
		 final IntegrationTestOutput output = super.runSimulation(dataService,
	                loadScenario("predict/predict-variant-time.nhm"), true, Collections.<Class<?>>emptySet());
		 
		 // so the expected outcome here is:
		 // all these assertions are checked in the scenario as well.
		 
		 for (final IDwelling dwelling : output.state.getDwellings()) {
			 final IFlags flags = output.state.get(output.flags, dwelling);
			 
			// with total foresight:
			 
			 // 1 * 1 + 1 * 2    for tariff
			 // 1 * 3 + 2 * 3	 for carbon
			 
			 // = 1 + 2 + 3 + 6 = 12
			 
			 final double billAll = flags.getRegister("bill-all").get();
			 Assert.assertEquals(12d, billAll, 0.1);
			 
			 // with no foresight:
			 // 1 * 1 + 1 * 1
			 // 1 * 3 + 1 * 3
			 
			 // = 8
			 
			 final double billNone = flags.getRegister("bill-none").get();
			 Assert.assertEquals(8d, billNone, 0.1);
			 
			 // with tariff foresight:
			 
			 final double billTariff = flags.getRegister("bill-tariffs").get();
			 Assert.assertEquals(9d, billTariff, 0.1);
			 
			 // with carbon foresight : 1 + 1 + 3 + 6
			 
			 final double billCarbon = flags.getRegister("bill-carbon").get();
			 Assert.assertEquals(11d, billCarbon, 0.1);
		 }
	}
	
    @Test
    public void wallPredicates() throws Exception {
        super.runSimulation(dataService, loadScenario("insulation/wall-predicates.s"), true, Collections.<Class<?>>emptySet());
    }

    @Test
    public void lookupTable() throws Exception {
        final IntegrationTestOutput output = super.runSimulation(dataService,
                loadScenario("functions/lookup/lookuptabletest.s"), true, Collections.<Class<?>>emptySet());
        final Set<IDwelling> dwellings = output.state.getDwellings();
        for (final IDwelling dwelling : dwellings) {
            final boolean isOnGas = output.state.get(output.structure, dwelling).isOnGasGrid();
            final RegionType region = output.state.get(output.basicAttributes, dwelling).getRegionType();
            final int buildYear = output.state.get(output.basicAttributes, dwelling).getBuildYear();

            final double theValue = output.state.get(output.flags, dwelling).getRegister("test-register").get();

            // [ONGAS REGION <1950 >=1950]
            // [true London 1 2 ]
            // [false London 3 4 ]
            // [* * 5 6 ]

            final double expectedValue;

            if (region == RegionType.London) {
                if (isOnGas) {
                    if (buildYear < 1950) {
                        expectedValue = 1;
                    } else {
                        expectedValue = 2;
                    }
                } else {
                    if (buildYear < 1950) {
                        expectedValue = 3;
                    } else {
                        expectedValue = 4;
                    }
                }
            } else {
                if (buildYear < 1950) {
                    expectedValue = 5;
                } else {
                    expectedValue = 6;
                }
            }

            Assert.assertEquals(expectedValue, theValue, 0d);
        }
    }

    @Test
    public void lookupFunction() throws Exception {
        final IntegrationTestOutput output = super.runSimulation(dataService,
                loadScenario("functions/lookup/lookuptest.s"), true, Collections.<Class<?>>emptySet());
        final Set<IDwelling> dwellings = output.state.getDwellings();
        for (final IDwelling dwelling : dwellings) {
            final boolean isOnGas = output.state.get(output.structure, dwelling).isOnGasGrid();
            final RegionType region = output.state.get(output.basicAttributes, dwelling).getRegionType();
            final int buildYear = output.state.get(output.basicAttributes, dwelling).getBuildYear();

            final double theValue = output.state.get(output.flags, dwelling).getRegister("test-register").get();

            if (isOnGas && (region == RegionType.London)) {
                Assert.assertEquals(10d, theValue, 0);
            } else if (!isOnGas && (buildYear < 1950)) {
                Assert.assertEquals(20d, theValue, 0);
            } else {
                Assert.assertEquals(30d, theValue, 0);
            }
        }
    }

    @Test
    public void testOnGasFunction() throws NHMException, InterruptedException {
        final IntegrationTestOutput output = super.runSimulation(dataService, loadScenario("functions/ongastest.s"),
                true, Collections.<Class<?>>emptySet());
        final Set<IDwelling> dwellings = output.state.getDwellings();
        for (final IDwelling dwelling : dwellings) {
            final StructureModel structureModel = output.state.get(output.structure, dwelling);
            final boolean onGasGrid = structureModel.isOnGasGrid();
            final IFlags iFlags = output.state.get(output.flags, dwelling);
            final boolean testFlag = iFlags.testFlag("is-on-gas-grid");
            Assert.assertEquals("on-gas-grid-flag should be set to same value as onGasGrid", onGasGrid, testFlag);
        }
    }

    @Test
    public void testHasLoftFunction() throws NHMException, InterruptedException {
        final IntegrationTestOutput output = super.runSimulation(dataService, loadScenario("functions/haslofttest.s"),
                true, Collections.<Class<?>>emptySet());
        final Set<IDwelling> dwellings = output.state.getDwellings();
        for (final IDwelling d : dwellings) {
            final StructureModel structureModel = output.state.get(output.structure, d);
            final boolean hasLoft = structureModel.getHasLoft();
            final IFlags iFlags = output.state.get(output.flags, d);
            final boolean testFlag = iFlags.testFlag("has-loft-boom");
            Assert.assertEquals("has-loft-boom should be set to the same value as hasLoft field on the house", hasLoft,
                    testFlag);
        }
    }

    @Test
    public void testHasWorkingCentralHeatingFunction() throws NHMException, InterruptedException {
        final IntegrationTestOutput output2 = super.runSimulation(dataService,
                loadScenario("functions/hascentralheatingthatworkstest.s"), true, Collections.<Class<?>>emptySet());
        final Set<IDwelling> dwellings2 = output2.state.getDwellings();
        for (final IDwelling dwelling : dwellings2) {
            final ITechnologyModel techModel = output2.state.get(output2.technology, dwelling);
            final IPrimarySpaceHeater primarySpaceHeater = techModel.getPrimarySpaceHeater();
            final boolean hasCentralHeatingThatWorks = (primarySpaceHeater instanceof ICentralHeatingSystem)
                    && !((ICentralHeatingSystem) primarySpaceHeater).isBroken();
            final IFlags iFlags = output2.state.get(output2.flags, dwelling);
            final boolean testFlag = iFlags.testFlag("does-have-working-central-heating");
            Assert.assertEquals(
                    "does-have-working-central-heating flag should be set to same value as hasCentralHeatingThatWorks",
                    hasCentralHeatingThatWorks, testFlag);
        }
    }

    @Test
    public void testHasCentralHeatingFunction() throws NHMException, InterruptedException {
        final IntegrationTestOutput output = super.runSimulation(dataService,
                loadScenario("functions/hascentralheatingtest.s"), true, Collections.<Class<?>>emptySet());
        final Set<IDwelling> dwellings = output.state.getDwellings();
        for (final IDwelling dwelling : dwellings) {
            final ITechnologyModel techModel = output.state.get(output.technology, dwelling);
            final IPrimarySpaceHeater primarySpaceHeater = techModel.getPrimarySpaceHeater();
            final boolean hasCentralHeating = primarySpaceHeater instanceof ICentralHeatingSystem;
            final IFlags iFlags = output.state.get(output.flags, dwelling);
            final boolean testFlag = iFlags.testFlag("does-have-central-heating");
            Assert.assertEquals("does-have-central-heating flag should be set to same value as hasCentralHeating",
                    hasCentralHeating, testFlag);
        }
    }

    @Test
    public void testDependsOnSecondaryHeatingFunction() throws NHMException, InterruptedException {
        final IntegrationTestOutput output2 = super.runSimulation(dataService,
                loadScenario("functions/dependsonsecondaryheatingtest.s"), true, Collections.<Class<?>>emptySet());
        final Set<IDwelling> dwellings2 = output2.state.getDwellings();
        for (final IDwelling dwelling : dwellings2) {
            final ITechnologyModel techModel = output2.state.get(output2.technology, dwelling);
            final IPrimarySpaceHeater primarySpaceHeater = techModel.getPrimarySpaceHeater();
            final IRoomHeater secondarySpaceHeater = techModel.getSecondarySpaceHeater();
            final boolean dependsOnSecondary =
                    ((primarySpaceHeater == null) || primarySpaceHeater.isBroken())
                            && (secondarySpaceHeater != null);
            final IFlags iFlags = output2.state.get(output2.flags, dwelling);
            final boolean testFlag = iFlags.testFlag("secondary-dependent");
            Assert.assertEquals("secondary-dependent flag should be set to same value as dependsOnSecondary",
                    dependsOnSecondary, testFlag);
        }
    }

    @Test
    public void inflator() throws NHMException, InterruptedException {
        super.runSimulation(
                dataService, loadScenario("functions/inflatortest.s"), true, Collections.<Class<?>>emptySet());
    }

    /**
     * Test based on there being only one housecase with 13 occupants, H0913215
     * 
     * @throws NHMException
     * @throws InterruptedException
     */
    @Test
    public void testNumberOfOccupantsFunction() throws NHMException, InterruptedException {
        final IntegrationTestOutput output = super.runSimulation(dataService,
                loadScenario("functions/numofoccupants.s"), true, Collections.<Class<?>>emptySet());
        final Set<IDwelling> dwellings = output.state.getDwellings();

        for (final IDwelling d : dwellings) {
            final People people = output.state.get(output.people, d);
            final IFlags iFlags = output.state.get(output.flags, d);
            final boolean testFlag = iFlags.testFlag("has-13-occupants");

            if (testFlag) {
                Assert.assertEquals("Number of people expected", 13, people.getNumberOfPeople());
            }
        }

        checkFlagCount(output, "has-13-occupants", 1192);
    }

    /**
     * Test based on their only being one house casewith an income of 300, I0592310.
     * 
     * @throws NHMException
     * @throws InterruptedException
     */
    @Test
    public void testHouseholdIncomeFunction() throws NHMException, InterruptedException {
        final IntegrationTestOutput output = super.runSimulation(dataService,
                loadScenario("functions/householdincome.s"), true, Collections.<Class<?>>emptySet());

        final Set<IDwelling> dwellings = output.state.getDwellings();
        for (final IDwelling d : dwellings) {
            final FinancialAttributes financialAttributes = output.state.get(output.finacialAttributes, d);
            final Double income = financialAttributes.getHouseHoldIncomeBeforeTax();
            final IFlags iFlags = output.state.get(output.flags, d);
            final boolean testFlag = iFlags.testFlag("income-matched");

            if (testFlag) {
                Assert.assertTrue("Income should equal 300", income.equals(300d));
            }
        }

        checkFlagCount(output, "income-matched", 1325);
    }

    /**
     * Test based on their only being one house case with an income of 300, I1182405.
     * 
     * @throws NHMException
     * @throws InterruptedException
     */
    @Test
    public void testNumberOfBedroomsFunction() throws NHMException, InterruptedException {
        final IntegrationTestOutput output = super.runSimulation(dataService,
                loadScenario("functions/numberofbedrooms.s"), true, Collections.<Class<?>>emptySet());

        checkFlagCount(output, "bedrooms-matched", 930);
    }

    /**
     * Test based on their only being one house case with an income of 300, I1182405.
     * 
     * @throws NHMException
     * @throws InterruptedException
     */
    @Test
    public void testTotalFloorAreaFunction() throws NHMException, InterruptedException {
        final IntegrationTestOutput output = super.runSimulation(dataService, loadScenario("functions/floorarea.s"),
                true, Collections.<Class<?>>emptySet());

        checkFlagCount(output, "area-match", 2479 + 733);
    }

    /**
     * Test based on their only being two house case with a space heating installation date of 1840 H1472309 and
     * WH1472309.
     * 
     * @throws NHMException
     * @throws InterruptedException
     */
    @Test
    public void testAgeOfSpaceHeatingSystem() throws NHMException, InterruptedException {
        final IntegrationTestOutput output = super.runSimulation(restrictHouseCases(dataService, "I0014310"),
                loadScenario("functions/spaceheatingage.s"), true, Collections.<Class<?>>emptySet());

        checkFlagCount(output, "heating-age-match", 545);
    }

    @Test
    public void testMainHeatingSystemType() throws NHMException, InterruptedException {
        final IntegrationTestOutput output = super.runSimulation(
                restrictHouseCases(dataService, "I0014310", "10097873"),
                loadScenario("functions/typeOfMainHeatingSystemTests.s"), true, Collections.<Class<?>>emptySet());

        final Map<Double, MainHeatingSystemType> typeMap = new HashMap<>();
        typeMap.put(Double.valueOf(1), MainHeatingSystemType.StandardBoiler);
        typeMap.put(Double.valueOf(2), MainHeatingSystemType.CombiBoiler);
        typeMap.put(Double.valueOf(3), MainHeatingSystemType.HeatPump);
        typeMap.put(Double.valueOf(4), MainHeatingSystemType.StorageHeater);
        typeMap.put(Double.valueOf(5), MainHeatingSystemType.WarmAirSystem);
        typeMap.put(Double.valueOf(6), MainHeatingSystemType.RoomHeater);
        typeMap.put(Double.valueOf(7), MainHeatingSystemType.BackBoiler);
        typeMap.put(Double.valueOf(8), MainHeatingSystemType.Community);
        typeMap.put(Double.valueOf(9), MainHeatingSystemType.Condensing);
        typeMap.put(Double.valueOf(10), MainHeatingSystemType.CondensingCombiBoiler);

        final Set<IDwelling> dwellings = output.state.getDwellings();
        for (final IDwelling d : dwellings) {
            final BasicCaseAttributes attr = output.state.get(output.basicAttributes, d);
            final IFlags iFlags = output.state.get(output.flags, d);
            final Optional<Double> flagValue = iFlags.getRegister("heatingSystemType");

            if (attr.getAacode().equals("I0014310")) {
                Assert.assertEquals("I0014310 found boiler type", MainHeatingSystemType.StandardBoiler,
                        typeMap.get(flagValue.or(8d)));
            } else if (attr.getAacode().equals("10097873")) {
                Assert.assertEquals("10097873 found boiler type", MainHeatingSystemType.Condensing,
                        typeMap.get(flagValue.or(8d)));
            }
        }
    }

    @Test
    public void testHasHotWateCylinder() throws Exception {
        final IntegrationTestOutput output = super.runSimulation(
                restrictHouseCases(dataService, "I0014310"),
                loadScenario("functions/hasHotWaterCylinder.s"), true, Collections.<Class<?>>emptySet());

        checkFlagCount(output, "has-hot-water-cylinder", 545);
    }

    @Test
    public void testHasInsulatedHotWateCylinder() throws Exception {
        final IntegrationTestOutput output = super.runSimulation(
                restrictHouseCases(dataService, "I0014310"),
                loadScenario("functions/hasInsulatedHotWaterCylinder.s"), true, Collections.<Class<?>>emptySet());

        checkFlagCount(output, "has-insulated-hot-water-cylinder", 545);
    }

    @Test
    public void testGlazingProportionIsCorrectWhenUsingGetProportionOfDoubleGlazings() throws Exception {
        final IntegrationTestOutput output = super.runSimulation(
                restrictHouseCases(dataService, "I0014310", "10076410", "H0011104", "H0011103"),
                loadScenario("functions/glazingproportiontest.s"), true, Collections.<Class<?>>emptySet());

        final Set<IDwelling> dwellings = output.state.getDwellings();
        for (final IDwelling d : dwellings) {
            final BasicCaseAttributes attr = output.state.get(output.basicAttributes, d);
            final IFlags iFlags = output.state.get(output.flags, d);
            final Optional<Double> flagValue = iFlags.getRegister("proportionDoubleGlazed");

            if (attr.getAacode().equals("I0014310")) {
                Assert.assertEquals(Double.valueOf(1), flagValue.or(0d));
            } else if (attr.getAacode().equals("10076410")) {
                Assert.assertEquals(Double.valueOf(0), flagValue.or(0d));
            } else if (attr.getAacode().equals("H0011104")) {
                Assert.assertEquals(Double.valueOf(0.25), flagValue.or(0d));
            } else if (attr.getAacode().equals("H0011103")) {
                Assert.assertEquals(Double.valueOf(0.75), flagValue.or(0d));
            }
        }
    }
}
