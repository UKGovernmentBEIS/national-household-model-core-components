package uk.org.cse.nhm.simulator.state.dimensions.fuel.cost;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.ImmutableSortedMultiset;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultiset;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.obligations.impl.TariffFuelAction;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;

public class Tariffs implements ITariffs {
	private final Map<FuelType, ITariff> tariffByFuel = new HashMap<>();
	private final Set<ITariff> tariffs = new LinkedHashSet<ITariff>();
	
	ExtraChargeOrderComparator order = new ExtraChargeOrderComparator();
	
	private final Set<IExtraCharge> extraCharges = new LinkedHashSet<>();
	private final Map<FuelType, SortedMultiset<IExtraCharge>> extraChargesByFuel = new LinkedHashMap<>();
	private final SortedMultiset<IExtraCharge> extraChargesWithoutFuel = TreeMultiset.create(order);
	
	private Tariffs() {
	}
	
	public static Tariffs free() {
		final Tariffs t = new Tariffs();
		
		for (final FuelType ft : FuelType.values()) {
			final FreeTariff of = FreeTariff.of(ft);
			t.tariffByFuel.put(ft, of);
			t.tariffs.add(of);
		}
		
		return t;
	}
	
	@Override
	public ITariffs copy() {
		final Tariffs copy = new Tariffs();
		copy.tariffByFuel.putAll(tariffByFuel);
		copy.tariffs.addAll(tariffs);
		
		copy.extraCharges.addAll(extraCharges);
		for (final Entry<FuelType, SortedMultiset<IExtraCharge>> entry : extraChargesByFuel.entrySet()) {
			final TreeMultiset<IExtraCharge> container = TreeMultiset.create(order);
			copy.extraChargesByFuel.put(entry.getKey(), container);
			container.addAll(entry.getValue());
		}
		
		copy.extraChargesWithoutFuel.addAll(extraChargesWithoutFuel);
		return copy;
	}

	@Override
	public Iterator<ITariff> iterator() {
		return tariffs.iterator();
	}

	@Override
	public ITariff getTariff(final FuelType fuelType) {
		return tariffByFuel.get(fuelType);
	}

	@Override
	public void adoptTariffs(final Set<ITariff> tariffs) throws IllegalArgumentException {
		destroyCachedActions();
		if (canAdoptTariffs(tariffs)) {
			for (final ITariff nt : tariffs) {
				for (final FuelType ft : nt.getFuelTypes()) {
					this.tariffs.remove(tariffByFuel.put(ft, nt));
					this.tariffs.add(nt);
				}
			}
		} else {
			throw new IllegalArgumentException("Adopting the supplied tariffs would result in "+
						"an inconsistent set of tariffs");
		}
	}

	@Override
	public boolean canAdoptTariffs(final Set<ITariff> tariffs) {
		final EnumSet<FuelType> handled = EnumSet.noneOf(FuelType.class);
		
		for (final ITariff t : tariffs) {
			if (Collections.disjoint(t.getFuelTypes(), handled)) {
				handled.addAll(t.getFuelTypes());
			} else {
				return false;
			}
		}
		
		for (final ITariff t : this.tariffs) {
			if (!Collections.disjoint(t.getFuelTypes(), handled)) {
				if (!handled.containsAll(t.getFuelTypes())) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	final List<TariffFuelAction> cachedActions = new LinkedList<>();
	
	@Override
	public void computeCharges(final ISettableComponentsScope scope, final ILets lets) {
		updateCachedActions();
		
		for (final TariffFuelAction tfa : cachedActions) {
			scope.apply(tfa, lets);
		}
		
		for (final IExtraCharge c : getNonFuelSpecificExtraCharges()) {
			c.apply(scope, lets);
		}
	}

	private void destroyCachedActions() {
		cachedActions.clear();
	}
	
	private void updateCachedActions() {
		if (cachedActions.isEmpty()) {
			for (final ITariff t : this) {
				for (final FuelType ft : t.getFuelTypes()) {
					cachedActions.add(new TariffFuelAction(ft, t, getExtraCharges(ft)));
				}
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((extraCharges == null) ? 0 : extraCharges.hashCode());
		result = prime * result + ((tariffs == null) ? 0 : tariffs.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Tariffs other = (Tariffs) obj;
		if (extraCharges == null) {
			if (other.extraCharges != null)
				return false;
		} else if (!extraCharges.equals(other.extraCharges))
			return false;
		if (tariffs == null) {
			if (other.tariffs != null)
				return false;
		} else if (!tariffs.equals(other.tariffs))
			return false;
		return true;
	}

	@Override
	public boolean addExtraCharge(final IExtraCharge charge) {
		if (extraCharges.contains(charge)) {
			return false;
		} else {
			destroyCachedActions();
			extraCharges.add(charge);
			if (charge.getFuel().isPresent()) {
				final FuelType fuel = charge.getFuel().get();
				if (!extraChargesByFuel.containsKey(fuel)) {
					extraChargesByFuel.put(fuel, TreeMultiset.create(order));
				}
				
				extraChargesByFuel.get(fuel).add(charge);
			} else {
				extraChargesWithoutFuel.add(charge);
			}
			return true;
		}
	}

	@Override
	public boolean removeExtraCharge(final IExtraCharge charge) {
		if (extraCharges.contains(charge)) {
			destroyCachedActions();
			extraCharges.remove(charge);
			
			if (charge.getFuel().isPresent()) {
				final FuelType fuel = charge.getFuel().get();
				if (extraChargesByFuel.containsKey(fuel)) {
					extraChargesByFuel.get(fuel).remove(charge);
				}
				
			} else {
				extraChargesWithoutFuel.remove(charge);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean hasExtraCharge(final IExtraCharge charge) {
		return extraCharges.contains(charge);
	}

	@Override
	public SortedMultiset<IExtraCharge> getExtraCharges(final FuelType fuelType) {
		if (extraChargesByFuel.containsKey(fuelType)) {
			return extraChargesByFuel.get(fuelType);
		} else {
			return ImmutableSortedMultiset.of();
		}
	}

	@Override
	public SortedMultiset<IExtraCharge> getNonFuelSpecificExtraCharges() {
		return extraChargesWithoutFuel;
	}
	
	@Override
	public boolean clearExtraCharges() {
		if (extraCharges.isEmpty()) return false;
		destroyCachedActions();
		extraCharges.clear();
		extraChargesByFuel.clear();
		extraChargesWithoutFuel.clear();
		return true;
	}
}
