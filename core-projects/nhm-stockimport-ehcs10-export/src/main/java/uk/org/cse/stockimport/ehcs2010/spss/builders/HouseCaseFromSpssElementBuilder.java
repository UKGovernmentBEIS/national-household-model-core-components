package uk.org.cse.stockimport.ehcs2010.spss.builders;

import uk.org.cse.stockimport.domain.IHouseCaseDTO;
import uk.org.cse.stockimport.domain.impl.HouseCaseDTO;
import uk.org.cse.stockimport.ehcs2010.spss.AbstractAutoHomElementBuilder;

/**
 * HouseCaseFromSpssElementBuilder.
 *
 * @author richardt
 * @version $Id: HouseCaseFromSpssElementBuilder.java 94 2010-09-30 15:39:21Z
 * richardt
 * @since 0.0.1-SNAPSHOT
 */
public class HouseCaseFromSpssElementBuilder extends AbstractAutoHomElementBuilder<IHouseCaseDTO> {

    public HouseCaseFromSpssElementBuilder() {
        super(HouseCaseDTO.class);
    }
}
