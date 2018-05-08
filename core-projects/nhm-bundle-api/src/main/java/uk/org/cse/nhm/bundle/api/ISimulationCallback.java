package uk.org.cse.nhm.bundle.api;

import java.nio.file.Path;

/**
 * Used to tell a client how a simulation is getting on
 */
public interface ISimulationCallback<T> {

    public void log(final String source, final String message);

    public void progress(final double proportion);

    public void completed(final Path pathToResult);

    public void invalid(final IValidationResult<T> validation);

    public void failed();

    public void cancelled();

    /**
     * Used by the simulation to determine whether to stop running. return true
     * if you want to cancel the run and throw away the result.
     */
    public boolean shouldCancel();
}
