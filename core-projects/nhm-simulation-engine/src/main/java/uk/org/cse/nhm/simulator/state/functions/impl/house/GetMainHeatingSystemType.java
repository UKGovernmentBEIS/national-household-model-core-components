package uk.org.cse.nhm.simulator.state.functions.impl.house;

import javax.inject.Inject;

import uk.org.cse.nhm.hom.emf.technologies.IBackBoiler;
import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IHeatPump;
import uk.org.cse.nhm.hom.emf.technologies.IHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IPrimarySpaceHeater;
import uk.org.cse.nhm.hom.emf.technologies.IStorageHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.ICombiBoiler;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.types.MainHeatingSystemType;

/**
 * GetBoilerType.
 *
 * @author richardTiffin
 * @version $Id$
 */
public class GetMainHeatingSystemType extends TechnologyFunction<MainHeatingSystemType> {

    @Inject
    public GetMainHeatingSystemType(IDimension<ITechnologyModel> technologies) {
        super(technologies);
    }

    /**
     * @param scope
     * @param lets
     * @return
     * @see
     * uk.org.cse.nhm.simulator.state.functions.IComponentsFunction#compute(uk.org.cse.nhm.simulator.scope.IComponentsScope,
     * uk.org.cse.nhm.simulator.let.ILets)
     */
    @SuppressWarnings("unchecked")
    @Override
    public MainHeatingSystemType compute(IComponentsScope scope, ILets lets) {
        return getTypeOfMainHeatingSystem(super.getTechnologies(scope));
    }

    protected final boolean isCondensing(ITechnologyModel technologies) {
        IPrimarySpaceHeater primarySpaceHeater = technologies.getPrimarySpaceHeater();

        if (primarySpaceHeater instanceof ICentralHeatingSystem) {
            final IHeatSource heatSource = ((ICentralHeatingSystem) primarySpaceHeater).getHeatSource();
            if ((heatSource instanceof IBoiler) || (heatSource instanceof ICombiBoiler)) {
                return ((IBoiler) heatSource).isCondensing();
            }
        }

        return false;
    }

    protected final MainHeatingSystemType getTypeOfMainHeatingSystem(ITechnologyModel technologies) {
        IPrimarySpaceHeater primarySpaceHeater = technologies.getPrimarySpaceHeater();
        if (technologies.isUsingAssumedElectricSpaceHeater()) {
            return MainHeatingSystemType.AssumedElectricHeater;
        } else if (primarySpaceHeater instanceof ICentralHeatingSystem) {
            final IHeatSource heatSource = ((ICentralHeatingSystem) primarySpaceHeater).getHeatSource();

            if ((heatSource instanceof ICombiBoiler) && isCondensing(technologies)) {
                return MainHeatingSystemType.CondensingCombiBoiler;
            } else if (isCondensing(technologies)) {
                return MainHeatingSystemType.Condensing;
            } else if (heatSource instanceof IBackBoiler) {
                return MainHeatingSystemType.BackBoiler;
            } else if (heatSource instanceof ICombiBoiler) {
                return MainHeatingSystemType.CombiBoiler;
            } else if (heatSource instanceof IBoiler) {
                return MainHeatingSystemType.StandardBoiler;
            } else if (heatSource instanceof ICommunityHeatSource) {
                return MainHeatingSystemType.Community;
            } else if (heatSource instanceof IHeatPump) {
                return MainHeatingSystemType.HeatPump;
            } else {
                return MainHeatingSystemType.StandardBoiler;
            }
        } else if (primarySpaceHeater instanceof IStorageHeater) {
            return MainHeatingSystemType.StorageHeater;
        } else if (primarySpaceHeater instanceof IWarmAirSystem) {
            return MainHeatingSystemType.WarmAirSystem;
        } else {
            return MainHeatingSystemType.RoomHeater;
        }
    }
}
