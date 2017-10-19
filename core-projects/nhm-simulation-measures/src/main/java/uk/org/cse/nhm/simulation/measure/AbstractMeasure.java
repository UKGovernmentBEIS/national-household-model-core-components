package uk.org.cse.nhm.simulation.measure;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;

public abstract class AbstractMeasure extends AbstractNamed implements IComponentsAction {
    /**
     * @param scope
     * @param lets
     * @return
     * @throws NHMException
     * @see uk.org.cse.nhm.simulator.scope.IComponentsAction#apply(uk.org.cse.nhm.simulator.scope.ISettableComponentsScope, uk.org.cse.nhm.simulator.let.ILets)
     */
    @Override
    public final boolean apply(ISettableComponentsScope scope, ILets lets) throws NHMException {
        if(isSuitable(scope, lets)){
            return doApply(scope,lets);
        }
        return false;
    }
    
    abstract public boolean doApply(ISettableComponentsScope scope, ILets lets) throws NHMException;    
}
