package uk.org.cse.stockimport.hom.impl.steps.services.heating;

import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem;
import uk.org.cse.nhm.hom.emf.util.Efficiency;
import uk.org.cse.stockimport.domain.services.ISpaceHeatingDTO;

/**
 * @since 1.0
 */
public class WarmAirSystemBuilder implements IWarmAirSystemBuilder {

    private static final ITechnologiesFactory T = ITechnologiesFactory.eINSTANCE;

    @Override
    public IWarmAirSystem buildWarmAirSystem(ISpaceHeatingDTO dto) {
        final IWarmAirSystem was = T.createWarmAirSystem();
        was.setFuelType(dto.getMainHeatingFuel());
        was.setEfficiency(Efficiency.fromDouble(dto.getBasicEfficiency()));
        return was;
    }
}
