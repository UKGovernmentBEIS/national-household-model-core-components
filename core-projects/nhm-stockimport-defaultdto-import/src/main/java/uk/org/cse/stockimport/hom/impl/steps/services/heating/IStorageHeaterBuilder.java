package uk.org.cse.stockimport.hom.impl.steps.services.heating;

import uk.org.cse.nhm.hom.emf.technologies.IStorageHeater;
import uk.org.cse.stockimport.domain.services.ISpaceHeatingDTO;

/**
 * @since 1.0
 */
public interface IStorageHeaterBuilder {

    /**
     * @since 1.0
     */
    IStorageHeater buildStorageHeater(ISpaceHeatingDTO dto);

}
