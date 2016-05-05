package uk.org.cse.nhm.simulator.scope;

import java.util.Set;

import com.google.common.base.Optional;

import uk.org.cse.commons.scopes.IScope;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;

/**
 * A scope for performing operations on simulation state.
 * 
 * @since 3.7.0
 * @param <T> The type of the tag on the scope.
 */
public interface IStateScope extends IScope<IStateChangeSource> {
    /**
     * Modify the state, using the given action on the set of houses provided
     * @return the affected dwellings
     */
    public Set<IDwelling> apply(final IStateAction action,
                                final Set<IDwelling> dwellings,
                                final ILets lets) throws NHMException;

    /**
     * Modify the state, using the given action on each dwelling in the given set of dwellings
     * @return the affected dwellings
     */
	public Set<IDwelling> apply(final IComponentsAction action,
                                final Set<IDwelling> dwellings,
                                final ILets lets) throws NHMException;

    /**
     * Directly access the underlying state information in this scope
     */
	public IBranch getState();

    public IStateScope branch(final IStateChangeSource why);

    public void merge(final IStateScope branched);
    
    /**
     * Get a components scope for the given dwelling, if one has been created
     * by applying an action.
     */
	public Optional<IComponentsScope> getComponentsScope(final IDwelling dwelling);
	
	/**
     * Creates a free-floating hypothetical state about this house, which can never become
     * the truth but can be used to work stuff out
     */
    IHypotheticalComponentsScope createHypothesis(final IDwelling dwelling);

    /**
     * Find the 'prior scope' for this scope
     * This is the scope for the parent branch.
     */
    public IComponentsScope getPriorScope(final IDwelling dwelling);
}
