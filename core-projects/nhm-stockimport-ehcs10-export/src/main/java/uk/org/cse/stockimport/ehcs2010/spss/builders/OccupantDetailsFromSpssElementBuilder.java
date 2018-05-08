package uk.org.cse.stockimport.ehcs2010.spss.builders;

import uk.org.cse.stockimport.domain.IOccupantDetailsDTO;
import uk.org.cse.stockimport.domain.impl.OccupantDetailsDTO;
import uk.org.cse.stockimport.ehcs2010.spss.AbstractAutoHomElementBuilder;

/**
 * OccupantDetailsFromSpssElementBuilder.
 *
 * @author richardt
 * @version $Id: OccupantDetailsFromSpssElementBuilder.java 94 2010-09-30
 * 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public class OccupantDetailsFromSpssElementBuilder extends AbstractAutoHomElementBuilder<IOccupantDetailsDTO> {

    public OccupantDetailsFromSpssElementBuilder() {
        super(OccupantDetailsDTO.class);
    }
}
