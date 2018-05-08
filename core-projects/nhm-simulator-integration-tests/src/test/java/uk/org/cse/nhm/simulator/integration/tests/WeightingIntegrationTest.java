package uk.org.cse.nhm.simulator.integration.tests;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;
import uk.org.cse.nhm.simulator.state.IDwelling;

public class WeightingIntegrationTest extends SimulatorIntegrationTest {

    private static final Map<String, Double> getTotalWeights(final IntegrationTestOutput result) {
        final Map<String, Double> sumByCode = new HashMap<>();

        for (final IDwelling d : result.state.getDwellings()) {
            final BasicCaseAttributes attributes = result.state.get(result.basicAttributes, d);

            if (sumByCode.containsKey(attributes.getAacode())) {
                sumByCode.put(attributes.getAacode(), sumByCode.get(attributes.getAacode()) + d.getWeight());
            } else {
                sumByCode.put(attributes.getAacode(), (double) d.getWeight());
            }
        }

        return sumByCode;
    }

    @Test
    public void rounding() throws Exception {
        final IntegrationTestOutput result = runSimulation(dataService, loadScenario("weights/rounding.s"), true, Collections.<Class<?>>emptySet());

        final Map<String, Double> sumByCode = getTotalWeights(result);

        for (final IDwelling d : result.state.getDwellings()) {
            Assert.assertEquals("All the dwellings that are created should have weight 1000", 1000d, d.getWeight(), 0.01);
        }

        double expectedTotal = 0;

        for (final SurveyCase sc : dataService.getSurveyCases("asdf", Collections.<String>emptySet())) {
            final double expectedCount = Math.round(sc.getBasicAttributes().getDwellingCaseWeight() / 1000d);
            expectedTotal += expectedCount * 1000d;
            if (expectedCount == 0) {
                Assert.assertFalse(sumByCode.containsKey(sc.getBasicAttributes().getAacode()));
            } else {
                Assert.assertEquals(sumByCode.get(sc.getBasicAttributes().getAacode()), expectedCount * 1000d, 0.1);
            }
        }

        Assert.assertEquals(result.state.getGlobals().getVariable("count", Number.class).get().doubleValue(), expectedTotal, 0.1);
    }

    @Test
    public void remainder() throws Exception {
        final IntegrationTestOutput result = runSimulation(dataService, loadScenario("weights/remainder.s"), true, Collections.<Class<?>>emptySet());

        verifyExactCounts(result);
    }

    @Test
    public void uniform() throws Exception {
        final IntegrationTestOutput result = runSimulation(dataService, loadScenario("weights/remainder.s"), true, Collections.<Class<?>>emptySet());

        verifyExactCounts(result);
    }

    private void verifyExactCounts(final IntegrationTestOutput result) {
        final Map<String, Double> sumByCode = getTotalWeights(result);

        double expectedTotal = 0;

        for (final SurveyCase sc : dataService.getSurveyCases("asdf", Collections.<String>emptySet())) {
            Assert.assertEquals(sumByCode.get(sc.getBasicAttributes().getAacode()), sc.getBasicAttributes().getDwellingCaseWeight(), 0.1);
            expectedTotal += sc.getBasicAttributes().getDwellingCaseWeight();
        }

        Assert.assertEquals(result.state.getGlobals().getVariable("count", Number.class).get().doubleValue(), expectedTotal, 0.1);
    }
}
