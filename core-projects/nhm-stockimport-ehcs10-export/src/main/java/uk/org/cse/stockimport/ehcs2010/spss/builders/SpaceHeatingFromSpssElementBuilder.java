package uk.org.cse.stockimport.ehcs2010.spss.builders;

import uk.org.cse.stockimport.domain.services.ISpaceHeatingDTO;
import uk.org.cse.stockimport.domain.services.impl.SpaceHeatingDTO;
import uk.org.cse.stockimport.ehcs2010.spss.AbstractAutoHomElementBuilder;

/**
 * @since 1.0
 */
public class SpaceHeatingFromSpssElementBuilder extends AbstractAutoHomElementBuilder<ISpaceHeatingDTO> {

    public SpaceHeatingFromSpssElementBuilder() {
        super(SpaceHeatingDTO.class);
    }
}
