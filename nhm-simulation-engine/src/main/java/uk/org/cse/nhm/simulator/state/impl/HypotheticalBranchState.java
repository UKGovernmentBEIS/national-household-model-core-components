package uk.org.cse.nhm.simulator.state.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IHypotheticalBranch;

public class HypotheticalBranchState extends BranchState implements IHypotheticalBranch {
	private final Set<IDimension<?>> replacedDimensions = new HashSet<>();
	
	HypotheticalBranchState(final BranchState branchState) {
		super(branchState, 1);
	}
	
	HypotheticalBranchState(final CanonicalState canonicalState) {
		super(canonicalState, 1);
	}

	/**
	 * Replace a dimension in this hypothesis with an entirely new function.
	 * Typically the new function will be a shim which merely provides a value,
	 * or delegates to another dimension.
	 * Any dimension you pass in here must be able to be emplaced back into
	 * another branch by use of its branch() method; that is to say, no replacement
	 * dimension should keep any ancestry when it is branched.
	 */
	@Override
	public <T> void replaceDimension(final IDimension<T> dimension, final IInternalDimension<T> replacement) {
		branchedDimensions[dimension.index()] = replacement;
		replacedDimensions.add(dimension);
	}
	
	@Override
	public boolean isHypothetical() {
		return true;
	}

	/*
	 * All branches in a hypothesis are themselves hypotheses
	 */
	@Override
	public IBranch branch(final int capacity) {
		return hypotheticalBranch();
	}

	/*
	 * Only hypotheses may be merged into a hypothesis
	 */
	@Override
	protected void dieIfCannotMergeWith(final IBranch child) {
		if (!child.isHypothetical()) throw new IllegalArgumentException("Only hypothetical branches should ever be merged here");
	}

	private boolean isDimensionReplaced(final IDimension<?> dimension) {
		return replacedDimensions.contains(dimension);
	}
	
	/*
	 * This needs overriding in case the other branch has had one of its dimensions replaced.
	 */
	@Override
	protected <T> void copyState(final IInternalDimension<T> dimension, final IBranch state, final Collection<? extends IDwelling> affected) {
		final HypotheticalBranchState other = (HypotheticalBranchState) state;

		if (other.isDimensionReplaced(dimension)) {
			// we are going to directly replace the dimension in this branch as well
			final IInternalDimension<T> replacementInOther = other.getInternalDimension(dimension);
			replaceDimension(dimension, replacementInOther.branch(this, 1));
		} else {
			super.copyState(dimension, state, affected);
		}		
	}
}
