package uk.org.cse.nhm.simulator.state.dimensions.fuel.cost;

import java.util.Set;

import uk.org.cse.commons.names.IIdentified;
import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.language.definition.fuel.XMethodOfPayment;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;

/**
 * Represents a tariff; this is a device which will work out the price for
 * multiple fuels for you.
 */
public interface ITariff extends IIdentified {

    /**
     * @return the fuel types this tariff accounts for
     */
    public Set<FuelType> getFuelTypes();

    /**
     * Compute the cost of the given fuel, and write payments into the given
     * scope.
     *
     * @param fuelType
     * @param output
     */
    public void apply(final FuelType fuelType, final ISettableComponentsScope output);

    public XMethodOfPayment getMethodOfPayment();

    public Name getIdentifierForFuel(final FuelType ft);
}
