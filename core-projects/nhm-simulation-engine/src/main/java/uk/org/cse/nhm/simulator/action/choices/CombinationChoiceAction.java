package uk.org.cse.nhm.simulator.action.choices;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
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
    private final Set<List<IComponentsAction>> alternatives;
    
    @AssistedInject
    public CombinationChoiceAction(
            @Assisted final IPicker selector,
            @Assisted final List<List<IComponentsAction>> alternatives) {
        
        this.selector = selector;
        
        final Builder<Set<IComponentsAction>> builder = ImmutableList.<Set<IComponentsAction>>builder();
        alternatives.forEach(pkg -> {
            builder.add(ImmutableSet.copyOf(pkg));
        });
        
        this.alternatives = Sets.cartesianProduct(builder.build());
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
        //This isn't quite right yet...we've got list of things in each of the alternatives
        //But now need to try them in turn to apply it to something....
        final ImmutableSet.Builder<IComponentsAction>  builder = ImmutableSet.<IComponentsAction>builder();
        
        alternatives.forEach(pkg -> {
            //TODO: What's the yield bit for plus the other bit
            builder.add(new SequenceAction(pkg, true, null));
        });
        
        return scope.apply(builder.build(), lets, selector);
    }

    /**
     * @param scope
     * @param lets
     * @return
     * @see uk.org.cse.nhm.simulator.scope.IComponentsAction#isSuitable(uk.org.cse.nhm.simulator.scope.IComponentsScope, uk.org.cse.nhm.simulator.let.ILets)
     */
    @Override
    public boolean isSuitable(IComponentsScope scope, ILets lets) {
        //TODO: Not sure what this should be yet - maybe if picker was selected?
        return true;
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
