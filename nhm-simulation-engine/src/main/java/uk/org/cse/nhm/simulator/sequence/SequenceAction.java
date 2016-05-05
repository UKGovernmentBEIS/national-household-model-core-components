package uk.org.cse.nhm.simulator.sequence;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

/**
 * So, this is a special action which sometimes does odd things regarding its values
 */
public class SequenceAction extends AbstractNamed implements IComponentsAction {
	private final List<IComponentsAction> delegates;
	private final boolean requireSuccess;
	private final ImmutableSet<String> hideYields;
	private final boolean onlyFirstCanFail;
	private final boolean alwaysSuitable;
	
	@AssistedInject
	public SequenceAction(
			@Assisted final List<IComponentsAction> delegates,
			@Assisted("all") final boolean requireSuccess,
			@Assisted("hide") final Set<String> hideYields) {
		super();
		this.delegates = delegates;
		this.requireSuccess = requireSuccess;
		this.hideYields = ImmutableSet.copyOf(hideYields);
		
		this.onlyFirstCanFail = onlyFirstCanFail(delegates);
		this.alwaysSuitable = computeIsAlwaysSuitable(requireSuccess, delegates);
	}

	private class RollingApply implements IComponentsAction {
		final Iterator<IComponentsAction> cursor;
		
		public RollingApply(final Iterator<IComponentsAction> cursor) {
			super();
			this.cursor = cursor;
		}

		@Override
		public StateChangeSourceType getSourceType() {
			return SequenceAction.this.getSourceType();
		}

		@Override
		public Name getIdentifier() {
			return SequenceAction.this.getIdentifier();
		}

		@Override
		public boolean apply(final ISettableComponentsScope scope, ILets lets) throws NHMException {
			boolean needsIsolation = false;

			while (cursor.hasNext()) {
				final IComponentsAction action = cursor.next();
				if (action instanceof ISequenceSpecialAction) {
					final ISequenceSpecialAction special = (ISequenceSpecialAction) action;
					lets = special.reallyApply(scope, lets);
					if (special.needsIsolation()) {
						needsIsolation = true;
					}
				} else if (needsIsolation) {
					return scope.applyInSequence(ImmutableList.<IComponentsAction>of(action, this), lets, true);
				} else if (action instanceof ISequenceScopeAction) {
					final boolean b = action.apply(scope, lets);
					if (SequenceAction.this.requireSuccess) {
						if (!b) return false;
					}
				} else {
					final boolean b = scope.apply(action, lets);
					if (SequenceAction.this.requireSuccess) {
						if (!b) return false;
					}
				}
			}
			
			return true;
		}

		@Override
		public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
			return false;
		}
		
		@Override
		public boolean isAlwaysSuitable() {
			return false;
		}
		
		@Override
		public String toString() {
			return getIdentifier().toString();
		}
	}
	
	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}

	@Override
	public boolean apply(final ISettableComponentsScope scope, final ILets lets) throws NHMException {
		final RollingApply block = new RollingApply(delegates.iterator());
		final boolean result;
		
		Map<String, Double> hidden = null;
		
		if (!hideYields.isEmpty()) {
			hidden = new HashMap<>();
			for (final String s : hideYields) {
				hidden.put(s, scope.getYielded(s).orNull());
			}
		}
		
        // if (isAlwaysSuitable() || onlyFirstCanFail) {
        // 	result = block.apply(scope, lets);
        // } else {
        result = scope.applyInSequence(ImmutableList.<IComponentsAction>of(block), lets, true);
        // }
		
		if (hidden != null) {
			for (final Map.Entry<String, Double> e : hidden.entrySet()) {
				if (e.getValue() == null) {
					scope.unYield(e.getKey());
				} else {
					scope.yield(e.getKey(), e.getValue());
				}
			}
		}
		
		return result;
	}
	
	/**
	 * If we have several actions and only the first one can fail,
	 * we don't need an isolating branch
	 * @param delegates2 
	 */
	private static boolean onlyFirstCanFail(final List<IComponentsAction> delegates) {
		boolean first = true;
		for (final IComponentsAction a : delegates) {
			if (first) {
				first = false;
				if (a.isAlwaysSuitable()) return false;
			} else {
				if (!a.isAlwaysSuitable()) return false;
			}
		}
		return true;
	}

	@Override
	public boolean isAlwaysSuitable() {
		return alwaysSuitable;
	}
	
	/**
	 * We are always suitable if
	 * (a) we do not require success
	 * (b) we do require success, but all our delegates are always suitable
	 *  
	 * @param requireSuccess
	 * @param delegates
	 * @return
	 */
	private static boolean computeIsAlwaysSuitable(final boolean requireSuccess, final List<IComponentsAction> delegates) {
		if (requireSuccess) {
			for (final IComponentsAction a : delegates) {
				if (!a.isAlwaysSuitable()) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
		if (requireSuccess) {
			for (final IComponentsAction a : delegates) {
				if (!a.isSuitable(scope, lets)) return false;
			}
		}
		return true;
	}
}
