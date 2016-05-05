package uk.org.cse.nhm.simulator.state.dimensions.fuel.cost;

import java.util.Comparator;

public class ExtraChargeOrderComparator implements Comparator<IExtraCharge> {

	@Override
	public int compare(final IExtraCharge a, final IExtraCharge b) {
		return Integer.compare(a.getOrder(), b.getOrder());
	}
}
