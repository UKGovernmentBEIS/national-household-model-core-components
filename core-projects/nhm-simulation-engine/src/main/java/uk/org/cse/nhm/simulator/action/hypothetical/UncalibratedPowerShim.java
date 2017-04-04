package uk.org.cse.nhm.simulator.action.hypothetical;

import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IHypotheticalBranch;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.impl.IInternalDimension;

class UncalibratedPowerShim implements IInternalDimension<IPowerTable> {
	private final IHypotheticalBranch branch;
	private final IDimension<IPowerTable> uncalibrated;

	public UncalibratedPowerShim(
			final IHypotheticalBranch branch,
			final IDimension<IPowerTable> uncalibrated) {
				this.branch = branch;
				this.uncalibrated = uncalibrated;
	}
	
	@Override
	public int index() {
		return uncalibrated.index();
	}

	@Override
	public boolean isSettable() {
		return uncalibrated.isSettable();
	}

	@Override
	public IPowerTable get(final IDwelling instance) {
		return branch.get(uncalibrated, instance);
	}

	@Override
	public int getGeneration(final IDwelling instance) {
		return branch.getGeneration(uncalibrated, instance);
	}

	@Override
	public IPowerTable copy(final IDwelling instance) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean set(final IDwelling instance, final IPowerTable value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void merge(final IDwelling instance, final IInternalDimension<IPowerTable> branch) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IInternalDimension<IPowerTable> branch(final IBranch newBranch, final int capacity) {
		if (newBranch instanceof IHypotheticalBranch) {
			return new UncalibratedPowerShim(
					(IHypotheticalBranch) newBranch, 
					uncalibrated);
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public boolean isEqual(final IPowerTable a, final IPowerTable b) {
		return (a == null && b == null) || (a != null && a.equals(b));
	}
}
