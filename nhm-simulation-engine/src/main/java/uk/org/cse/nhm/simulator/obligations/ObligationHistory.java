package uk.org.cse.nhm.simulator.obligations;

import java.util.Iterator;
import java.util.List;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import uk.org.cse.commons.collections.branchinglist.BranchingList;
import uk.org.cse.nhm.hom.ICopyable;

public class ObligationHistory extends BranchingList<IObligation> implements ICopyable<IObligationHistory>,  IObligationHistory {
	public ObligationHistory() {
		super(null);
	}
	
	protected ObligationHistory(ObligationHistory parent) {
		super(parent);
	}
	
	@Override
	public IObligationHistory branch() {
		return new ObligationHistory(this);
	}
	
	@Override
	public IObligationHistory copy() {
		return branch();
	}
	
	@Override
	public <T extends IObligation> Optional<T> getObligation(Class<T> type) {
		List<T> obligations = getObligations(type);
		if(obligations.size() == 1) {
			return Optional.of(obligations.get(0));
		} else {
			return Optional.absent();
		}
	
	}

	@Override
	public <T extends IObligation> List<T> getObligations(Class<T> type) {
		Iterator<IObligation> iterator = this.iterator();
		Builder<T> items = ImmutableList.builder();
		while(iterator.hasNext()) {
			IObligation item = iterator.next();
			if(type.isInstance(item)) {
				items.add(type.cast(item));
			}
		}
		return items.build();
	}
}
