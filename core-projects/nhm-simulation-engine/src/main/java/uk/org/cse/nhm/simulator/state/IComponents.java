package uk.org.cse.nhm.simulator.state;

/**
 * This is typically a capture for a dwelling in a particular branch. It curries
 * the dwelling into the set methods on {@link IBranch}, although in principle
 * it could represent several dwellings whose state is entirely identical.
 *
 * @author hinton
 *
 */
public interface IComponents {

    /**
     * @param dimension
     * @return the dimension value for the captured dwelling/dwellings
     */
    public <T> T get(final IDimension<T> dimension);

    /**
     * Return the ID of the captured dwelling
     *
     * @return
     */
    public int getDwellingID();

    /**
     * Returns the captured dwelling. Please be careful when using this method:
     * generally prefer to pass the scope around. It has been added to allow the
     * delayed action to work, because we need to keep track of a house across a
     * time period.
     */
    public IDwelling getDwelling();

    /**
     * Returns the captured state; again, prefer to work on the state through
     * the components if you can.
     */
    public IState getState();
}
