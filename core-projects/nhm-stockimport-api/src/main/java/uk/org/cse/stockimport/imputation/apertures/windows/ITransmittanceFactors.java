package uk.org.cse.stockimport.imputation.apertures.windows;

import uk.org.cse.nhm.energycalculator.api.types.GlazingType;
import uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType;

public interface ITransmittanceFactors {

    /**
     * @since 1.0
     */
    public abstract double getGainsTransmittance(GlazingType glazingType,
            WindowInsulationType insulation);

    /**
     * @since 1.0
     */
    public abstract double getLightTransmittance(GlazingType glazingType,
            WindowInsulationType insulation);

}
