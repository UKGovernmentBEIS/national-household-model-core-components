/**
 * 
 */
package uk.org.cse.stockimport.domain;

import uk.org.cse.stockimport.domain.schema.DTO;
import uk.org.cse.stockimport.domain.schema.DTOField;


/**
 * @author glenns
 * @since 1.0
 */
@DTO(value = "lighting", required=false)
public interface ILowEnergyLightingDTO extends IBasicDTO {
	public static final String FRACTION_FIELD = "fraction";

    /**
     * @since 1.0
     */
	@DTOField(FRACTION_FIELD)
    double getLowEnergyLightsFraction();
    void setLowEnergyLightsFraction(double fraction);
}
