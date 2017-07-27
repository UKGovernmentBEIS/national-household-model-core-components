package uk.ac.ucl.hideem;

import java.util.List;

import com.google.common.base.Supplier;

import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.hom.types.BuiltFormType;

public interface IHealthModule {
    /**
     * Compute the health impact of a change to a dwelling.
     * The result is put into an instance of T supplied by the given factory.
     * @param factory a factory to construct the result
     * @param t1 the temperature in the dwelling before the intervention
     * @param t2 the temperature in the dwelling after the intervention
     * @param p1 the permeability of the dwelling before the intervention
     * @param p2 the permeability of the dwelling after the intervention
     * @param h1 the specific heat loss (ventilation + fabric) before intervention
     * @param h2 the specific heat loss (ventilation + fabric) after intervention
     * @param form the built form of the dwelling
     * @param floorArea the total floor area of the dwelling (summed over storeys)
     * @param region the region the dwelling is in
     * @param mainFloorLevel Not sure
     * @param hasWorkingExtractorFans self-explanatory?
     * @param hasTrickleVents self-explanatory?
     * @param hadDoubleGlazing whether the dwelling had more than 80%
     *        double or triple glazing before the intervention
     * @param hasDoubleGlazing whether the dwelling has more than 80%
     *        double or triple glazing after the intervention
     * @param people a list of the occupants in the house
     * @return An instanceof of T produced by factory, which has been
     *         told the relevant information about health impacts.
     */
    public <T extends HealthOutcome> T effectOf(
        Supplier<T> factory,

        double t1, double t2,

        double p1, double p2,

        double h1, double h2,

        // case number constituents
        BuiltFormType form,
        double floorArea,
        RegionType region,
        int mainFloorLevel, // fdfmainn (for flats)
        // finkxtwk and finbxtwk
        final boolean hadWorkingExtractorFans, // per finwhatever
        final boolean hadTrickleVents,         // this is cooked up elsewhere
        final boolean hasWorkingExtractorFans, // per finwhatever
        final boolean hasTrickleVents,         // this is cooked up elsewhere

        boolean hadDoubleGlazing,
        boolean hasDoubleGlazing,
        // who
        List<Person> people);

    public double getInternalTemperature(double specificHeat,
                                         double efficiency);

    public double getRebateDeltaTemperature(double baseTemperature, double rebate);
}
