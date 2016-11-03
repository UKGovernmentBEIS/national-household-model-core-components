package uk.org.cse.stockimport.imputation.apertures.windows;

import uk.org.cse.nhm.energycalculator.impl.IWindowUValues;

public interface IWindowPropertyTables {
	IWindowUValues getUValues();
	IWindowFrameFactor getFrameFactors();
	ITransmittanceFactors getTransmittanceFactors();
}

