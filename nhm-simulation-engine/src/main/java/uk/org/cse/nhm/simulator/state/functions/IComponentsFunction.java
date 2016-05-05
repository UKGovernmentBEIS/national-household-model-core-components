package uk.org.cse.nhm.simulator.state.functions;

import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;

import uk.org.cse.commons.names.IIdentified;
import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

/**
 * A function which takes some components to type parameter T.
 * 
 * @author hinton
 *
 * @param <T>
 */
public interface IComponentsFunction<T> extends IIdentified {	
    public <Q extends T> Q compute(IComponentsScope scope, ILets lets);
	public Set<IDimension<?>> getDependencies();
	public Set<DateTime> getChangeDates();
	
	IComponentsFunction<Boolean> TRUE = new IComponentsFunction<Boolean>() {
		
		@Override
		public Name getIdentifier() {
			return Name.of("TRUE");
		}
		
		@Override
		public Set<IDimension<?>> getDependencies() {
			return Collections.emptySet();
		}
		
		@Override
		public Boolean compute(final IComponentsScope ignore, final ILets lets) {
			return true;
		}
		
		@Override
		public String toString() {
			return "TRUE";
		}

		@Override
		public Set<DateTime> getChangeDates() {
			return Collections.emptySet();
		}
	};
}
