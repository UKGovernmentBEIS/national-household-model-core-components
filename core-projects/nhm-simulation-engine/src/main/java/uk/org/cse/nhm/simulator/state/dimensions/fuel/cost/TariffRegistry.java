package uk.org.cse.nhm.simulator.state.dimensions.fuel.cost;

import java.util.LinkedHashSet;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;

public class TariffRegistry implements ITariffRegistry {
	private final Set<ITariff> defaultTariffs = new LinkedHashSet<>();
	private final Set<FuelType> fuelsSeen = new LinkedHashSet<>();
	private ITariffs defaults;

	@Override
	public Set<ITariff> getDefaultTariffs() {
		return defaultTariffs;
	}

	@Override
	public void addDefaultTariff(final ITariff tariff) {
		Set<FuelType> overlap = Sets.intersection(fuelsSeen, tariff.getFuelTypes());
		if (overlap.isEmpty()) {
			defaultTariffs.add(tariff);
			defaults = null;
		} else {
			throw new IllegalArgumentException(String.format("Default tariff %s contained fuels which other default tariffs already define: %s", tariff.toString(), Joiner.on(", ").join(overlap)));
		}
	}

	@Override
	public ITariffs get() {
		if (defaults == null) {
			defaults = Tariffs.free();
			defaults.adoptTariffs(defaultTariffs);
		}
		return defaults;
	}
}
