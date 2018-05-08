package uk.org.cse.stockimport.ehcs2010.spss.builders;

import uk.org.cse.stockimport.domain.geometry.IElevationDTO;
import uk.org.cse.stockimport.domain.geometry.impl.ElevationDTO;
import uk.org.cse.stockimport.ehcs2010.spss.AbstractAutoHomElementBuilder;

/**
 * ElevationElementBuilder.
 *
 * @author richardt
 * @version $Id: ElevationElementBuilder.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public class ElevationElementBuilder extends AbstractAutoHomElementBuilder<IElevationDTO> {

    public ElevationElementBuilder() {
        super(ElevationDTO.class);
    }
}
