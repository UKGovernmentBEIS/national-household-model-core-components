package uk.org.cse.stockimport.ehcs2010.spss.builders;

import uk.org.cse.stockimport.domain.geometry.IRoofDTO;
import uk.org.cse.stockimport.domain.geometry.impl.RoofDTO;
import uk.org.cse.stockimport.ehcs2010.spss.AbstractAutoHomElementBuilder;

/**
 * RoofFromSpssElementBuilder.
 *
 * @author richardt
 * @version $Id: RoofFromSpssElementBuilder.java 94 2010-09-30 15:39:21Z
 * richardt
 * @since 0.0.1-SNAPSHOT
 */
public class RoofFromSpssElementBuilder extends AbstractAutoHomElementBuilder<IRoofDTO> {

    public RoofFromSpssElementBuilder() {
        super(RoofDTO.class);
    }
}
