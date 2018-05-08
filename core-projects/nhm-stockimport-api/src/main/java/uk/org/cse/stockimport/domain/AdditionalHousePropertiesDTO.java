package uk.org.cse.stockimport.domain;

import java.util.HashMap;
import java.util.Map;

import uk.org.cse.stockimport.domain.geometry.impl.AbsDTO;

/**
 * Stores house properties which are not part of the model, and are not altered
 * by the simulation. These can only ever be put in by the stock import/creation
 * process.
 *
 * @since 2.0.0
 */
public class AdditionalHousePropertiesDTO extends AbsDTO {

    private Map<String, String> valuesByProperty = new HashMap<String, String>();

    /**
     * @since 2.0.0
     */
    public Map<String, String> getValuesByProperty() {
        return valuesByProperty;
    }

    /**
     * @since 2.0.0
     */
    public void setValuesByProperty(Map<String, String> valuesByProperty) {
        this.valuesByProperty = valuesByProperty;
    }
}
