package uk.org.cse.nhm.simulation.cli;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Guice;
import com.google.inject.Injector;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.ipc.api.scenario.IStockService;
import uk.org.cse.nhm.simulation.cli.guice.CliSimulationExecutorModule;

public class StockCreationFromJsonInputTest {

    private IStockService stockService;

    @Test
    public void EnsureThaStockCanBeCreatedFromJsonFile() throws Exception {
        Injector injector = Guice.createInjector(new CliSimulationExecutorModule("src/test/resources/singleHouseCase.json"));
        stockService = injector.getInstance(IStockService.class);

        List<SurveyCase> houseCases = stockService.getSurveyCases(
                "n/a", ImmutableSet.<String>builder().build());

        assertThat("No house cases returned", houseCases, notNullValue());
        assertThat("Incorrect number of houseCases returned", houseCases.size(), equalTo(1));

        IEnergyCalculatorHouseCase houseCase = houseCases.get(0);
        assertThat("No house case returned", houseCase, notNullValue());
        assertThat("AACODE not set", ((SurveyCase) houseCase).getBasicAttributes().getAacode(), equalTo("H0011103"));
    }
}
