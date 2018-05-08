package uk.org.cse.nhm.language.builder;

import java.util.Set;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.ipc.api.scenario.IStockService;
import uk.org.cse.nhm.language.definition.XScenario;
import uk.org.cse.nhm.simulator.main.ISimulator;

public interface ISimulatorBuilder {

    ISimulator build(XScenario scenario, String executionID,
            IStockService dataService,
            final SimulationParameter<?>... additionalParameters) throws NHMException;

    public Set<Class<?>> getAdaptableClasses();
}
