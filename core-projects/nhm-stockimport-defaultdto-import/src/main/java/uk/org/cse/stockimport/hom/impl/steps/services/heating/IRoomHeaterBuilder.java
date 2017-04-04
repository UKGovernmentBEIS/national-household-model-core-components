package uk.org.cse.stockimport.hom.impl.steps.services.heating;

import uk.org.cse.nhm.hom.emf.technologies.IRoomHeater;
import uk.org.cse.stockimport.domain.services.ISpaceHeatingDTO;

/**
 * IRoomHeaterBuilder.
 * @since 1.0
 */
public interface IRoomHeaterBuilder {

    /**
     * @since 1.0
     */
    IRoomHeater buildRoomHeater(final int constructionYear, final ISpaceHeatingDTO dto);

    IRoomHeater buildDefaultElectricRoomHeater();
}
