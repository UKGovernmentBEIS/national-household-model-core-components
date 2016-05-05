package uk.org.cse.nhm.simulator.state.functions.impl.house;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.commons.Glob;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.components.IFlags;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class GetFlags extends AbstractNamed implements IComponentsFunction<String> {
    private final IDimension<IFlags> flags;
    private final Glob glob;
    
    @AssistedInject
	public GetFlags(final IDimension<IFlags> flags, @Assisted final Glob glob) {
        this.flags = flags;
        this.glob = glob;
    }

    @Override
	public String compute(final IComponentsScope scope, final ILets lets) {
        final StringBuffer result = new StringBuffer();
        final List<String> theFlags = new ArrayList<>(scope.get(flags).getFlags());
        Collections.sort(theFlags);
        for (final String f : theFlags) {
            if (glob.matches(f)) {
                if (result.length() > 0) {
                    result.append(",");
                }
                result.append(f);
            }
        }
		return result.toString();
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return Collections.<IDimension<?>>singleton(flags);
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.<DateTime>emptySet();
	}
}
