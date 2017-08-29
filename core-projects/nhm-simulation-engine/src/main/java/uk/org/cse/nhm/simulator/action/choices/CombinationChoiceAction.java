package uk.org.cse.nhm.simulator.action.choices;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope.IPicker;
import uk.org.cse.nhm.simulator.sequence.SequenceAction;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

/**
 * CombinationChoiceAction.
 *
 * @author trickyBytes
 */
public class CombinationChoiceAction extends AbstractNamed implements IComponentsAction {
    private final IPicker selector;
    private final Set<IComponentsAction> packages;
    
    @AssistedInject
    public CombinationChoiceAction(
            @Assisted final IPicker selector,
            @Assisted final List<Set<IComponentsAction>> groups) {
        
        final ImmutableSet.Builder<IComponentsAction> packageBuilder = ImmutableSet.builder();
        for (final List<IComponentsAction> packageList : Sets.cartesianProduct(groups)) {
            packageBuilder.add(new SequenceAction(packageList, true, Sets.newHashSet()));
        }

        this.packages = packageBuilder.build();
        this.selector = selector;
    }    
    
    /**
     * @return
     * @see uk.org.cse.nhm.simulator.state.IStateChangeSource#getSourceType()
     */
    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }

    /**
     * @param scope
     * @param lets
     * @return
     * @throws NHMException
     * @see uk.org.cse.nhm.simulator.scope.IComponentsAction#apply(uk.org.cse.nhm.simulator.scope.ISettableComponentsScope, uk.org.cse.nhm.simulator.let.ILets)
     */
    @Override
    public boolean apply(ISettableComponentsScope scope, ILets lets) throws NHMException {
        return scope.apply(this.packages, lets, this.selector);
    }

    /**
     * @param scope
     * @param lets
     * @return
     * @see uk.org.cse.nhm.simulator.scope.IComponentsAction#isSuitable(uk.org.cse.nhm.simulator.scope.IComponentsScope, uk.org.cse.nhm.simulator.let.ILets)
     */
    @Override
    public boolean isSuitable(IComponentsScope scope, ILets lets) {
        for (final IComponentsAction p : this.packages) {
            if (p.isSuitable(scope, lets)) {
               return true;
            }
        }
        return false; // no packages are suitable
    }

    /**
     * @return
     * @see uk.org.cse.nhm.simulator.scope.IComponentsAction#isAlwaysSuitable()
     */
    @Override
    public boolean isAlwaysSuitable() {
        return false;
    }

}
