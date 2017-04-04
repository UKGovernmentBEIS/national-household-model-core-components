package uk.org.cse.nhm.simulator.state.dimensions.fuel.cost;

import java.util.Set;

import com.google.inject.Provider;

public interface ITariffRegistry extends Provider<ITariffs> {
	public Set<ITariff> getDefaultTariffs();
	public void addDefaultTariff(final ITariff tariff);
}
