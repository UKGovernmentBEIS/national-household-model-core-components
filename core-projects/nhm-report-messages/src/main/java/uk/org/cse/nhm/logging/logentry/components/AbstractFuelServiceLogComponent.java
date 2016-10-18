package uk.org.cse.nhm.logging.logentry.components;

import java.util.HashMap;
import java.util.Map;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;

@AutoProperty
public abstract class AbstractFuelServiceLogComponent {
	private final Map<FuelType, Map<ServiceType, Double>> values;
	
	public static class MapBuilder {
		private final Map<FuelType, ImmutableMap.Builder<ServiceType, Double>>
			acc = new HashMap<>();
			
		public MapBuilder put(final FuelType ft, final ServiceType st, final double value) {
			if (value == 0) return this;
			
			if (!acc.containsKey(ft)) {
				acc.put(ft, ImmutableMap.<ServiceType, Double>builder());
			}
			
			acc.get(ft).put(st, value);
			
			return this;
		}
		
		public static MapBuilder builder() {
			return new MapBuilder();
		}

		public Map<FuelType, Map<ServiceType, Double>> build() {
			final ImmutableMap.Builder<FuelType, Map<ServiceType, Double>> b = ImmutableMap.builder();
			
			for (final FuelType ft : acc.keySet()) {
				b.put(ft, acc.get(ft).build());
			}
			
			return b.build();
		}
	}
	
	public AbstractFuelServiceLogComponent(
			Map<FuelType, Map<ServiceType, Double>> values) {
		super();
		this.values = values;
	}

	protected double getTotalValue() {
		double acc = 0;
		
		for (final FuelType ft : FuelType.values()) {
			for (final ServiceType st : ServiceType.values()) {
				acc += getValue(st, ft);
			}
		}
		
		return acc;
	}

	protected double getValue(final ServiceType service) {
		double acc = 0;

		for (final FuelType ft : FuelType.values()) {
			acc += getValue(service, ft);
		}

		return acc;
	}
	
	protected double getValue(final ServiceType service, final FuelType fuel) {
		if (values.containsKey(fuel)) {
			final Double d = values.get(fuel).get(service);
			if (d == null) return 0;
			return d;
		}
		return 0;
	}
	
	protected double getValue(final FuelType fuel) {
		double acc = 0;

		for (final ServiceType st : ServiceType.values()) {
			acc += getValue(st, fuel);
		}

		return acc;
	}
	
	public Map<FuelType, Map<ServiceType, Double>> getValues() {
		return values;
	}
	
	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

	@Override
	public boolean equals(Object obj) {
		return Pojomatic.equals(this, obj);
	}

	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}
}
