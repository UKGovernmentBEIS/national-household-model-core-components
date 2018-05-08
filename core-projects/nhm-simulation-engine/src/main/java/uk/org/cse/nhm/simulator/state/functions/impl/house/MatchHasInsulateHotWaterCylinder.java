package uk.org.cse.nhm.simulator.state.functions.impl.house;

import com.google.inject.Inject;

import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

/**
 * MatchHasInsulateHotWateCylinder.
 *
 * @author richardTiffin
 */
public class MatchHasInsulateHotWaterCylinder extends MatchHasHotWaterCylinder {

    /**
     * @param bad
     */
    @Inject
    public MatchHasInsulateHotWaterCylinder(IDimension<ITechnologyModel> bad) {
        super(bad);
    }

    /**
     * @param scope
     * @param lets
     * @return
     * @see
     * uk.org.cse.nhm.simulator.state.functions.impl.house.MatchHasHotWaterCylinder#compute(uk.org.cse.nhm.simulator.scope.IComponentsScope,
     * uk.org.cse.nhm.simulator.let.ILets)
     */
    @Override
    public Boolean compute(IComponentsScope scope, ILets lets) {
        return isCylinderInsulated(scope.get(bad));
    }

    /**
     * Uses
     * {@link MatchHasHotWaterCylinder#hasHotWaterCylinder(ITechnologyModel)} to
     * determine if cylinder is present then checks insulation amount to
     * determine if it is greater than zero
     *
     * @param tech
     * @return {@link Boolean#TRUE} {@link IWaterTank#getInsulation()} > 0
     */
    protected Boolean isCylinderInsulated(ITechnologyModel tech) {
        if (super.hasHotWaterCylinder(tech)) {
            ICentralWaterSystem cse = tech.getCentralWaterSystem();
            IWaterTank cylinder = cse.getStore();

            return (cylinder.getInsulation() > 0 ? true : false);
        }
        return false;
    }
}
