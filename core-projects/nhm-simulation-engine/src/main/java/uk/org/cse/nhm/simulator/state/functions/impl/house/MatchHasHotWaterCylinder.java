package uk.org.cse.nhm.simulator.state.functions.impl.house;

import com.google.inject.Inject;

import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

/**
 * MatchHasHotWaterCylinder.
 *
 * @author richardTiffin
 */
public class MatchHasHotWaterCylinder extends TechnologyFunction<Boolean> {

    /**
     * @param bad
     */
    @Inject
    public MatchHasHotWaterCylinder(IDimension<ITechnologyModel> bad) {
        super(bad);
    }

    /**
     * @param scope
     * @param lets
     * @return
     * @see
     * uk.org.cse.nhm.simulator.state.functions.IComponentsFunction#compute(uk.org.cse.nhm.simulator.scope.IComponentsScope,
     * uk.org.cse.nhm.simulator.let.ILets)
     */
    @Override
    public Boolean compute(IComponentsScope scope, ILets lets) {
        return hasHotWaterCylinder(scope.get(bad));
    }

    protected Boolean hasHotWaterCylinder(ITechnologyModel tech) {
        if ((tech.getCentralWaterSystem() != null) && (tech.getCentralWaterSystem() instanceof ICentralWaterSystem)) {
            ICentralWaterSystem cws = tech.getCentralWaterSystem();
            if (cws.getStore() != null) {
                return true;
            }
        }

        return false;
    }
}
