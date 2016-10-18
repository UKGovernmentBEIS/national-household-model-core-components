package uk.org.cse.stockimport.hom.impl.steps.services.heating;

import uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem;
import uk.org.cse.stockimport.domain.services.ISpaceHeatingDTO;

/**
 * @since 1.0
 */
public interface IWarmAirSystemBuilder {

    /**
     * @since 1.0
     */
    IWarmAirSystem buildWarmAirSystem(ISpaceHeatingDTO dto);

}
