package uk.org.cse.nhm.simulator.state;

/**
 * An IDimension is a handle used to describe an aspect of an {@link IDwelling}.
 * An {@link IDwelling} has various data associated with it in an
 * {@link IState}, which are grouped together under an {@link IDimension}.
 *
 * @author hinton
 *
 * @param <T> the type of data held under this dimension
 */
public interface IDimension<T> {

    String CONSTRUCT_COPY = "IDimension.construct-copy";

    /**
     * @return true if this dimension is in-principle something which can be
     * changed directly
     */
    public boolean isSettable();

    public int index();
}
