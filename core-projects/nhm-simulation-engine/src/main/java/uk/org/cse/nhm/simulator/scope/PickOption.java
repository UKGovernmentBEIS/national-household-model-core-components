package uk.org.cse.nhm.simulator.scope;

import uk.org.cse.nhm.simulator.let.ILets;

public final class PickOption {
	public final IComponentsScope scope;
	public final ILets lets;
	
	public PickOption(final IComponentsScope scope, final ILets lets) {
		this.scope = scope;
		this.lets = lets;
	}
	
	public PickOption withBindings(final ILets lets) {
		return new PickOption(scope, lets);
	}
}
