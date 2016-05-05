package uk.org.cse.stockimport.ehcs2010.spss.builders;

import uk.org.cse.stockimport.domain.IVentilationDTO;
import uk.org.cse.stockimport.domain.impl.VentilationDTO;
import uk.org.cse.stockimport.ehcs2010.spss.AbstractAutoHomElementBuilder;

/**
 * @since 1.0
 */
public class VentilationFromSpssElementBuilder extends AbstractAutoHomElementBuilder<IVentilationDTO> {
    /**
     * @since 1.0
     */
    public VentilationFromSpssElementBuilder() {
		super(VentilationDTO.class);
	}
}
