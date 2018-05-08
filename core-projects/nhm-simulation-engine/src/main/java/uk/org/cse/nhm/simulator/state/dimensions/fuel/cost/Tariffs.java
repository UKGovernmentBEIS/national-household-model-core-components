package uk.org.cse.nhm.simulator.state.dimensions.fuel.cost;

import java.util.*;
import java.util.Map.Entry;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.obligations.impl.TariffFuelAction;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;

public class Tariffs implements ITariffs {

    private final Map<FuelType, ITariff> tariffByFuel = new HashMap<>();
    private final Set<ITariff> tariffs = new LinkedHashSet<ITariff>();

    private final Set<IExtraCharge> extraCharges = new LinkedHashSet<>();
    private final Map<FuelType, List<IExtraCharge>> extraChargesByFuel = new LinkedHashMap<>();
    private List<IExtraCharge> extraChargesWithoutFuel = new ArrayList<>();

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
        for (final Entry<FuelType, List<IExtraCharge>> entry : extraChargesByFuel.entrySet()) {
            final List<IExtraCharge> container = new ArrayList<>();
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
            throw new IllegalArgumentException("Adopting the supplied tariffs would result in "
                    + "an inconsistent set of tariffs");
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
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tariffs other = (Tariffs) obj;
        if (extraCharges == null) {
            if (other.extraCharges != null) {
                return false;
            }
        } else if (!extraCharges.equals(other.extraCharges)) {
            return false;
        }
        if (tariffs == null) {
            if (other.tariffs != null) {
                return false;
            }
        } else if (!tariffs.equals(other.tariffs)) {
            return false;
        }
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
                    extraChargesByFuel.put(fuel, new ArrayList<>());
                }

                List<IExtraCharge> current = extraChargesByFuel.get(fuel);
                current.add(charge);
                extraChargesByFuel.put(fuel, inDependencyOrder(current));

            } else {
                extraChargesWithoutFuel.add(charge);
                extraChargesWithoutFuel = inDependencyOrder(extraChargesWithoutFuel);
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
    public List<IExtraCharge> getExtraCharges(final FuelType fuelType) {
        if (extraChargesByFuel.containsKey(fuelType)) {
            return extraChargesByFuel.get(fuelType);
        } else {
            return ImmutableList.of();
        }
    }

    @Override
    public List<IExtraCharge> getNonFuelSpecificExtraCharges() {
        return extraChargesWithoutFuel;
    }

    @Override
    public boolean clearExtraCharges() {
        if (extraCharges.isEmpty()) {
            return false;
        }
        destroyCachedActions();
        extraCharges.clear();
        extraChargesByFuel.clear();
        extraChargesWithoutFuel.clear();
        return true;
    }

    /*
	This is a naive algorithm for sorting a list of extra charges into dependency order.
	It's probably not very fast, but we shouldn't have many extra charges, so it should be OK.
     */
    List<IExtraCharge> inDependencyOrder(List<IExtraCharge> existing) {
        List<IExtraCharge> result = new ArrayList<>();
        Queue<IExtraCharge> toSort = new LinkedList<>();
        toSort.addAll(existing);

        while (!toSort.isEmpty()) {
            IExtraCharge current = toSort.poll();

            if (!result.contains(current)) {
                Set<IExtraCharge> depsPresent = new HashSet<>(current.getDependencies());
                depsPresent.retainAll(extraCharges);

                if (depsPresent.isEmpty() || result.containsAll(depsPresent)) {
                    result.add(current);
                } else {
                    // Try adding it again later.
                    toSort.add(current);
                }
            }
        }

        return result;
    }
}
