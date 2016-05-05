package uk.org.cse.nhm.simulator.action;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.commons.Glob;
import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.action.IUnifiedReport.IRecord;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.components.IFlags;

public class FlaggedStateAction implements IStateAction, IModifier<IFlags> {
	private final IDimension<IFlags> flags;
	private final IStateAction delegate;

    private final List<Glob> preconditions;
    private final List<Glob> postactions;
    
	private final List<IUnifiedReport> reports;
	
	@AssistedInject
	public FlaggedStateAction(
			IDimension<IFlags> flags, 
			@Assisted IStateAction delegate,
			@Assisted("test") final List<Glob> testFlags, 
			@Assisted("update") final List<Glob> updateFlags,
			@Assisted final List<IUnifiedReport> reports) {
		this.flags = flags;
		this.delegate = delegate;
		this.preconditions = testFlags;
        this.postactions = updateFlags;
		this.reports = reports;
	}

	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}

	@Override
	public Set<IDwelling> apply(IStateScope scope, Set<IDwelling> dwellings, ILets lets) throws NHMException {
		final Multimap<IDwelling, IUnifiedReport.IRecord> records = ArrayListMultimap.create();

        scope.apply(new IComponentsAction() {
                @Override
                public Name getIdentifier() {
                    return FlaggedStateAction.this.getIdentifier();
                }
			
                @Override
                public StateChangeSourceType getSourceType() {
                    return StateChangeSourceType.ACTION;
                }
			
                @Override
                public boolean isSuitable(IComponentsScope scope, ILets lets) {
                    return true;
                }
			
                @Override
                public boolean isAlwaysSuitable() {
                    return true;
                }
			
                @Override
                public boolean apply(ISettableComponentsScope scope, ILets lets) throws NHMException {
                    for (final IUnifiedReport report : reports) {
                        records.put(scope.getDwelling(), report.before(getIdentifier().getName(), scope, lets));
                    }
                    return true;
                }

                @Override
                public String toString() {
                    return String.format("%s [to reports %s]", delegate, reports);
                }
            }, dwellings, lets);
		
        
		final Set<IDwelling> affected = delegate.apply(scope, suitable(scope.getState(), dwellings), lets);

        changeFlags(scope.getState(), affected);
		
		if (!records.isEmpty()) {
			for (final IDwelling d : affected) {
                final Optional<IComponentsScope> componentsScope_ = scope.getComponentsScope(d);
                final IComponentsScope componentsScope = componentsScope_.isPresent() ? 
                    componentsScope_.get():
                    scope.getState().detachedScope(d);
                    
                for (final IRecord record : records.get(d)) record.after(componentsScope, lets);
			}
		}
						
		return affected;
	}

	private void changeFlags(IBranch state, Set<IDwelling> affected) {
		if (postactions.isEmpty()) return;
		
		for (final IDwelling d : affected) {
			state.modify(flags, d, this);
		}
	}

	private Set<IDwelling> suitable(IBranch branch, Set<IDwelling> dwellings) {
		if (preconditions.isEmpty()) return dwellings;
		
		final LinkedHashSet<IDwelling> subset = new LinkedHashSet<IDwelling>();
		for (final IDwelling d : dwellings) {
			if (testFlags(branch, d)) {
				subset.add(d);
			}
		}
		return subset;
	}

	private boolean testFlags(IBranch branch, IDwelling d) {
        return branch.get(flags, d).flagsMatch(preconditions);
	}

	@Override
	public Set<IDwelling> getSuitable(IStateScope scope, Set<IDwelling> dwellings, ILets lets) {
		return suitable(scope.getState(), delegate.getSuitable(scope, dwellings, lets));
	}
	
	@Override
	public Name getIdentifier() {
		return delegate.getIdentifier();
	}
	
	@Override
	public boolean modify(final IFlags modifiable) {
        return modifiable.modifyFlagsWith(postactions);
	}

    public String toString() {
        return delegate.toString();
    }
}
