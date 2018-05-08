package uk.org.cse.nhm.simulator.state.dimensions.fuel.cost;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.language.definition.fuel.XMethodOfPayment;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;

public class FreeTariff extends AbstractNamed implements ITariff {

    private final Set<FuelType> fuelTypes;
    private static final FreeTariff[] byFuelType = new FreeTariff[FuelType.values().length];

    public static final FreeTariff of(final FuelType fuelType) {
        if (byFuelType[fuelType.ordinal()] == null) {
            final FreeTariff ft = new FreeTariff(fuelType);
            byFuelType[fuelType.ordinal()] = ft;
        }
        return byFuelType[fuelType.ordinal()];
    }

    private FreeTariff(FuelType fuelType) {
        setIdentifier(Name.of("free " + fuelType));
        this.fuelTypes = ImmutableSet.of(fuelType);
    }

    @Override
    public Set<FuelType> getFuelTypes() {
        return fuelTypes;
    }

    @Override
    public void apply(FuelType fuelType, ISettableComponentsScope output) {

    }

    @Override
    public Name getIdentifierForFuel(FuelType ft) {
        return getIdentifier();
    }

    @Override
    public XMethodOfPayment getMethodOfPayment() {
        return XMethodOfPayment.Free;
    }
}
