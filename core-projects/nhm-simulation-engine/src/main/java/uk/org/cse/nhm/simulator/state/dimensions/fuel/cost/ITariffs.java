package uk.org.cse.nhm.simulator.state.dimensions.fuel.cost;

import java.util.List;
import java.util.Set;

import uk.org.cse.nhm.hom.ICopyable;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;

/**
 * Represents the tariff structure associated with a house.
 *
 * This contains all the tariffs that the house has in it.
 *
 * @author hinton
 * @since 3.8.0
 */
public interface ITariffs extends ICopyable<ITariffs>, Iterable<ITariff> {

    /**
     * @param fuelType
     * @return the tariff for the given type of fuel
     */
    public ITariff getTariff(final FuelType fuelType);

    /**
     * go on the given tariffs if legal; if illegal (there would be more than
     * one tariff per fuel either in the input or in the result) there will be
     * an illegal argument exception.
     *
     * @param tariff
     */
    public void adoptTariffs(final Set<ITariff> tariff) throws IllegalArgumentException;

    /**
     * @param tariffs
     * @return true if {@link #adoptTariffs(Set)} would not throw an exception
     * for this set.
     */
    public boolean canAdoptTariffs(final Set<ITariff> tariffs);

    /**
     * Adds an extra charge. This will remain even if the tariff changes.
     *
     * @return true if the extra charge was not previously present
     */
    public boolean addExtraCharge(final IExtraCharge charge);

    /**
     * Removes a particular extra charge if it was previously added, otherwise
     * does nothing.
     *
     * @return true if the charge was present
     */
    public boolean removeExtraCharge(final IExtraCharge charge);

    /**
     * Tests is a particular extra charge is present.
     */
    public boolean hasExtraCharge(final IExtraCharge charge);

    /**
     * @return the extra charges which should be applied for a given fuel type.
     */
    public List<IExtraCharge> getExtraCharges(final FuelType fuelType);

    /**
     * @return the extra charges which are not specific to a particular fuel
     * type, but should be run after all the tariffs.
     */
    public List<IExtraCharge> getNonFuelSpecificExtraCharges();

    public void computeCharges(final ISettableComponentsScope scope, final ILets lets);

    /**
     * remove all extra charges
     *
     * @return true if there were any extra charges before
     */
    public boolean clearExtraCharges();
}
