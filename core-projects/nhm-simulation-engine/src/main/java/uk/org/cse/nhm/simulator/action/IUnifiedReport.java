package uk.org.cse.nhm.simulator.action;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;

public interface IUnifiedReport {    
	public interface IRecord {
        public void after(final IComponentsScope scope, final ILets lets);
	}

    /**
     * Called before something happens
     */
	IRecord before(String key, IComponentsScope scope, ILets lets);
}
