package uk.org.cse.stockimport.hom.impl.steps.services.heating;

import uk.org.cse.nhm.hom.emf.technologies.IRoomHeater;
import uk.org.cse.stockimport.domain.services.SecondaryHeatingSystemType;
import uk.org.cse.stockimport.hom.impl.steps.services.SpaceHeatingBuildStep;

/**
 * Helper for {@link SpaceHeatingBuildStep} which sets up the secondary heaters
 * in a house.
 *
 * @author hinton
 * @since 1.0
 */
public interface ISecondaryHeatingSystemBuilder {

    /**
     * @since 1.0
     */
    IRoomHeater createSecondaryHeatingSystem(SecondaryHeatingSystemType type);
}
