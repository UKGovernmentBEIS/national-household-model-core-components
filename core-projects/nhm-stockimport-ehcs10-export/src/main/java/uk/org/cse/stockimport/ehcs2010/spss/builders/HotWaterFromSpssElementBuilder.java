package uk.org.cse.stockimport.ehcs2010.spss.builders;

import uk.org.cse.stockimport.domain.services.IWaterHeatingDTO;

/**
 * @since 1.0
 */
public class HotWaterFromSpssElementBuilder extends ReflectiveHomElementBuilder<IWaterHeatingDTO> {

    /**
     * @since 1.0
     */
    public HotWaterFromSpssElementBuilder() {
        super(IWaterHeatingDTO.class);
    }
}
