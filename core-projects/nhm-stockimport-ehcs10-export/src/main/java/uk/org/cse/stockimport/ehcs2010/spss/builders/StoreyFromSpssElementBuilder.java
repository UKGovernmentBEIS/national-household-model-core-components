package uk.org.cse.stockimport.ehcs2010.spss.builders;

import uk.org.cse.stockimport.domain.geometry.IStoreyDTO;
import uk.org.cse.stockimport.domain.geometry.impl.StoreyDTO;
import uk.org.cse.stockimport.ehcs2010.spss.AbstractAutoHomElementBuilder;

/**
 * StoreyFromSpssElementBuilder.
 * 
 * @author richardt
 * @version $Id: StoreyFromSpssElementBuilder.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public class StoreyFromSpssElementBuilder extends AbstractAutoHomElementBuilder<IStoreyDTO> {
	public StoreyFromSpssElementBuilder() {
		super(StoreyDTO.class);
	}
}
