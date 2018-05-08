package uk.org.cse.stockimport.imputation.apertures.windows;

import uk.org.cse.nhm.energycalculator.mode.WindowUValues;

public interface IWindowPropertyTables {

    WindowUValues getUValues();

    IWindowFrameFactor getFrameFactors();

    ITransmittanceFactors getTransmittanceFactors();
}
