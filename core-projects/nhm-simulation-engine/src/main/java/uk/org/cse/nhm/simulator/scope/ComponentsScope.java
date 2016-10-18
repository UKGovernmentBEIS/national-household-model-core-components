package uk.org.cse.nhm.simulator.scope;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;

import uk.org.cse.commons.scopes.IScope;
import uk.org.cse.commons.scopes.impl.Scope;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.obligations.IObligation;
import uk.org.cse.nhm.simulator.obligations.IObligationHistory;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.impl.GlobalTransactionHistory;
import uk.org.cse.nhm.simulator.transactions.DwellingTransactionHistory;
import uk.org.cse.nhm.simulator.transactions.IPayment;
import uk.org.cse.nhm.simulator.transactions.ITransaction;
import uk.org.cse.nhm.simulator.transactions.Transaction;

public class ComponentsScope extends Scope<IStateChangeSource> implements ISettableComponentsScope {
    protected final IScope<?> parentScope;

    protected final IBranch branch;
	protected final IDwelling dwelling;
	protected final IDimension<DwellingTransactionHistory> transactionDimension;
	protected final IDimension<IObligationHistory> obligationDimension;
	
	protected final ITimeDimension timeDimension;
	protected final ScopeFactory scopeFactory;
	
	/**
	 * This is a shared, mutable datastructure held in common between all the scopes in a branch; it is probably the worst bit of this.
	 * 
	 * It is managed by callers to {@link #createChild(IStateChangeSource, IBranch, Map)} below, who are expected to pass in a map which
	 * should be worked on within the resulting scope, and are also expected to handle merging the changes back into any other maps should
	 * the child scope be added to this scope in the end.
	 * 
	 * At the moment, said methods are {@link #applyInSequence(List, ILets, boolean)}, {@link #apply(Set, ILets, uk.org.cse.nhm.simulator.scope.ISettableComponentsScope.IPicker)},
	 * and {@link #createHypothesis()}
	 */
	protected final Map<String, Double> yieldedValues;

    ComponentsScope(final IScope<?> parentScope,
                    final IDimension<DwellingTransactionHistory> transactionDimension,
                    final IDimension<IObligationHistory> obligationDimension,
                    final ITimeDimension time,
                    final ScopeFactory scopeFactory,
                    final IStateChangeSource action,
                    final IBranch branch,
                    final IDwelling dwelling,
                    final Map<String, Double> yieldedValues) {
        super(action);
        this.parentScope = parentScope;
        this.obligationDimension = obligationDimension;
        this.transactionDimension = transactionDimension;
        this.timeDimension = time;
        this.scopeFactory = scopeFactory;
        this.branch = branch;
        this.dwelling = dwelling;
        this.yieldedValues = yieldedValues;
    }

    @Override
    public IComponentsScope getPriorScope() {
        if (parentScope instanceof IComponentsScope) {
            return (IComponentsScope) parentScope;
        } else if (parentScope instanceof IStateScope) {
            final IStateScope containingStateScope = (IStateScope) parentScope;

            // this is the _containing_ state scope, not the _prior_ state scope.
            // we ask it for the _prior_ components scope for the given dwelling
            // this is different because:
            // when we apply an action, we go:
            // 1. make a branch of the canonical state
            // 2. create a state scope for that branch
            //    so if in that branch we want to know (delta X)
            //    it ought to be delta to the canonical state.
            //    which is not accessible except through said state scope
            return containingStateScope.getPriorScope(dwelling);
        } else {
            throw new UnsupportedOperationException("Cannot get prior scope from this");
        }
    }
	
    protected ComponentsScope createChild(final IStateChangeSource action, final IBranch branch, final Map<String, Double> yieldedValues) {
        if (branch == this.branch) {
            return scopeFactory.createComponentsScope(parentScope, action, branch, dwelling, yieldedValues);
        } else {
            return scopeFactory.createComponentsScope(this, action, branch, dwelling, yieldedValues);
        }
	}
	
	@Override
	public boolean apply(final IComponentsAction action, final ILets lets)
			throws NHMException {
		final ComponentsScope child = createChild(action, branch, yieldedValues);
		final boolean apply = action.apply(child, lets);
		if (apply) {
			addChild(child);
			child.close();
		}
		return apply;
	}

	@Override
	public boolean apply(final Set<IComponentsAction> actions, final ILets lets, final IPicker picker)
			throws NHMException {
	
		final ImmutableSet.Builder<PickOption> builder = ImmutableSet.builder();
		
		for (final IComponentsAction a : actions) {
			final ComponentsScope hypothesis = createChild(a, branch.branch(1), new HashMap<>(yieldedValues));

			if (a.apply(hypothesis, lets)) {
				builder.add(new PickOption(hypothesis, lets));
			}
		}

		final Set<PickOption> successfulActions = builder.build();

		if (successfulActions.isEmpty()) {
			return false;
		} else {
            // we need to ensure that if the picker uses randomness, the end result also uses randomness
            final long stateBefore = branch.getRandom().getSeed();
			final PickOption option = picker.pick(branch.getRandom(), successfulActions);
            final boolean usedRandomnessToPick = stateBefore != branch.getRandom().getSeed();

            if (option == null) {
				return false;
			}
			
			final ComponentsScope picked = (ComponentsScope) option.scope;
			picked.close();
			addChildAndMerge(picked);
			
			// merge all yielded values in
			yieldedValues.putAll(picked.yieldedValues);

            // this is untidy; if we used any randomness to select the branch, we will
            // have lost that perturbation after we did the merge.  to make sure that we
            // do end up using some randomness, we use a bit more here.
            if (usedRandomnessToPick) {
                branch.getRandom().nextDouble();
            }
            
			return true;
		}
	}
	
	@Override
	public boolean applyInSequence(final List<IComponentsAction> actions,
			final ILets lets, final boolean requireSuccess) throws NHMException {
		final Builder<ComponentsScope> children = ImmutableSet.builder();

        final IBranch b = branch.branch(1);

        final Map<String, Double> yieldForBranch = new HashMap<>(yieldedValues);
		
		for (final IComponentsAction a : actions) {
			final ComponentsScope child = createChild(a, b, yieldForBranch);
			if (a.apply(child, lets)) {
				children.add(child);
			} else if (requireSuccess) {
				return false;
			}
			child.close();
		}

		for (final ComponentsScope child : children.build()) {
			addChild(child);
		}

        branch.merge(b);
        yieldedValues.putAll(yieldForBranch);

		return true;
	}

	protected void addChildAndMerge(final ComponentsScope child) {
		super.addChild(child);
		branch.merge(child.branch);
	}
	
	@Override
	public <T> T get(final IDimension<T> dimension) {
		return branch.get(dimension, dwelling);
	}

	@Override
	public int getDwellingID() {
		return dwelling.getID();
	}

	@Override
	public <T> void modify(final IDimension<T> dimension, final IModifier<T> mod) {
		branch.modify(dimension, dwelling, mod);
	}
	
	@Override
	public void addNote(final Object note) {
		if (note instanceof ITransaction) {
			throw new IllegalArgumentException("Please add your transactions with addTransaction(), not addNote()");
		} else if (note instanceof IObligation) {
			throw new IllegalArgumentException("Please add your obligations with addObligation(), not addNote()");
		} else {
			super.addNote(note);
		}
	}	
	
	private void addTransaction(final ITransaction transaction) {
		super.addNote(transaction);

		/* We have to have separate modifiers for the global and per-dwelling accounts here, because the type system isn't good enough. */
		new IModifier<GlobalTransactionHistory>(){
			@Override
			public boolean modify(final GlobalTransactionHistory modifiable) {
				modifiable.receive(transaction);
				return true;
			}
		}.modify(branch.getGlobals().getGlobalAccount(transaction.getPayee()));
		
		modify(transactionDimension, new IModifier<DwellingTransactionHistory>() {
			@Override
			public boolean modify(final DwellingTransactionHistory modifiable) {	
				modifiable.pay(transaction);
				return true;
			}
		});
	}
	
	@Override
	public void addObligation(final IObligation obligation) {
		super.addNote(obligation);
		
		final IModifier<IObligationHistory> mod = new IModifier<IObligationHistory>() {
			@Override
			public boolean modify(final IObligationHistory modifiable) {	
				modifiable.add(obligation);
				return true;
			}
		};
		
		modify(obligationDimension, mod);
	}
	
	private final ITransaction createAndAddTransaction(final String counterparty, final double amount,final Set<String> tags) {
		final ITransaction of = Transaction.dwelling(dwelling, counterparty, amount, get(timeDimension).get(ILets.EMPTY), tags);
		addTransaction(of);
		return of;
	}

	@Override
	public void addTransaction(final IPayment payment) {
		if (payment.getAmount() != 0) {
			createAndAddTransaction(payment.getPayee(), payment.getAmount(), payment.getTags());
		}
	}
	
	@Override
	public IHypotheticalComponentsScope createHypothesis() {
        return scopeFactory.createHypotheticalScope(this, getTag(), branch.hypotheticalBranch(), dwelling, new HashMap<>(yieldedValues));
	}

	@Override
	public <T> Optional<T> getGlobalVariable(final String string, final Class<T> clazz) {
		return branch.getGlobals().getVariable(string, clazz);
	}
	
	@Override
	public <T> void setGlobalVariable(final String string, final T value) {
		branch.getGlobals().setVariable(string, value);
	}

	@Override
	public IDwelling getDwelling() {
		return dwelling;
	}
	
	@Override
	public GlobalTransactionHistory getGlobalAccount(final String accountName) {
		return branch.getGlobals().getGlobalAccount(accountName);
	}
	
	@Override
	public void yield(final String key, final double value) {
		yieldedValues.put(key, value);
	}
	
	@Override
	public void unYield(final String key) {
		yieldedValues.remove(key);
	}
	
	@Override
	public Optional<Double> getYielded(final String key) {
		return Optional.fromNullable(yieldedValues.get(key));
	}
	
	@Override
	public Map<String, Double> getYieldedValues() {
		return Collections.unmodifiableMap(yieldedValues);
	}
	
	@Override
	public void yieldAll(final Map<String, Double> values) {
		yieldedValues.putAll(values);
	}
	
	@Override
	public String toString() {
		return String.format("%s [%s] %s", getTag(), branch, yieldedValues);
	}

    @Override
    public IBranch getState() {
        return branch;
    }
}
