package uk.org.cse.stockimport.ehcs2010.spss.builders;

import uk.org.cse.stockimport.domain.ILowEnergyLightingDTO;
import uk.org.cse.stockimport.domain.impl.LowEnergyLightingDTO;
import uk.org.cse.stockimport.ehcs2010.spss.AbstractAutoHomElementBuilder;

/**
 * Builds a csv file from LogEnergyLightingDTO elements.
 * 
 * @author glenns
 * @since 1.0
 */
public class LowEnergyLightingFromSpssElementBuilder extends AbstractAutoHomElementBuilder<ILowEnergyLightingDTO> {
	public LowEnergyLightingFromSpssElementBuilder() {
		super(LowEnergyLightingDTO.class);
	}
}
