package uk.org.cse.nhm.bundle.api;

import java.util.Set;

/**
 * Presentation of an NHM version as an OSGi service.
 */
public interface INationalHouseholdModel {
    /**
     * @return the version number of this NHM
     */
    public String version();

    /**
     * Validate the scenario starting at top
     */
    public <P> IValidationResult<P> validate(final IFS<P> fs, final P top);

    /**
     * Convert the given scenario into an include graph.
     * This is also included in a validation result for free.
     */
    public <P> IIncludeGraph<P> getIncludeGraph(final IFS<P> fs, final P top);

    /**
     * Extract all the definitions from the given scenario.
     * This is also included in a validation result for free.
     */
    public <P> Set<IDefinition<P>> getDefinitions(final IFS<P> fs, final P top);

    /**
     * Run a simulation talking to the given callback.
     * This method is synchronous, so you probably want to spawn a thread to do it in.
     */
    public <P> void simulate(final IFS<P> fs, final P top, final ISimulationCallback<P> callback);

    /**
     * @return an auxiliary object which helps with the language.
     */
    public ILanguage language();

    /**
     * Take all of a scenario at the given path and crunch it down into
     * the bare essentials needed to run the model.
     */
    public <P> IRunInformation<P> getRunInformation(final IFS<P> fs,
                                                    final P top);

    /**
     * Simulate something based on the run information above.
     */ 
    public <P> void simulate(final IFS<P> fs,
                             final IRunInformation<P> totality,
                             final ISimulationCallback<String> callback);

    public <P> void importStock(final IFS<P> fs,
                                final P stockZipFile,
                                final IStockImportCallback callback);
}
