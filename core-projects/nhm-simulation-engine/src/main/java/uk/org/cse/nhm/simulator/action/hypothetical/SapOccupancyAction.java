package uk.org.cse.nhm.simulator.action.hypothetical;

import javax.inject.Inject;

import uk.org.cse.nhm.hom.people.People;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IHypotheticalComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

/**
 * Applies the SAP occupancy calculation, defined on page 155 of SAP2009
 *
 */
public class SapOccupancyAction extends HypotheticalAction {

    private static final double FLOOR_AREA_THRESHOLD = 13.9;
    private static final double EXPONENTIAL_COEFFICIENT = 1.76;
    private static final double EXPONENTIAL_BIAS = -0.000349;
    private static final double LINEAR_COEFFICIENT = 0.0013;

    final IDimension<People> people;
    final IDimension<StructureModel> structure;

    @Inject
    public SapOccupancyAction(
            final IDimension<People> people,
            final IDimension<StructureModel> structure) {
        this.people = people;
        this.structure = structure;
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }

    /**
     * This is the equation defined on p155, which tells you number of people by
     * fitting on floor area
     *
     * @param floorArea area in m2
     * @return SAP occupancy for given area (num people)
     */
    protected static double getSapOccupancy(final double floorArea) {
        if (floorArea > FLOOR_AREA_THRESHOLD) {
            final double delta = floorArea - FLOOR_AREA_THRESHOLD;
            return 1
                    + EXPONENTIAL_COEFFICIENT * (1 - Math.exp(EXPONENTIAL_BIAS * delta * delta))
                    + LINEAR_COEFFICIENT * delta;
        } else {
            return 1d;
        }
    }

    @Override
    protected boolean doApply(IHypotheticalComponentsScope scope, ILets lets) {
        final double newOccupancy = getSapOccupancy(scope.get(structure).getFloorArea());
        scope.modify(people, new SetOccupancy(newOccupancy));
        return true;
    }

    static class SetOccupancy implements IModifier<People> {

        final double newOccupancy;

        public SetOccupancy(final double newOccupancy) {
            this.newOccupancy = newOccupancy;
        }

        @Override
        public boolean modify(final People modifiable) {
            if (modifiable.getOccupancy() == newOccupancy) {
                return false;
            } else {
                modifiable.setOccupancy(newOccupancy);
                return true;
            }
        }
    }
}
