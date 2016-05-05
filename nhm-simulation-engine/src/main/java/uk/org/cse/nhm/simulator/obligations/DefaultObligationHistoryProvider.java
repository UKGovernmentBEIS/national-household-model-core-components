package uk.org.cse.nhm.simulator.obligations;

import javax.inject.Inject;

import com.google.inject.Provider;

import uk.org.cse.nhm.simulator.obligations.impl.AnnualMaintenanceObligation;
import uk.org.cse.nhm.simulator.state.dimensions.energy.AnnualFuelObligation;

public class DefaultObligationHistoryProvider implements Provider<IObligationHistory> {
	private IObligationHistory history;
	
	@Inject
	public DefaultObligationHistoryProvider(
			AnnualMaintenanceObligation annualMaintenance,
			AnnualFuelObligation annualFuel) {

		history = new ObligationHistory();
		history.add(annualFuel);
		history.add(annualMaintenance);
	}

	@Override
	public IObligationHistory get() {
		return history;
	}
}
