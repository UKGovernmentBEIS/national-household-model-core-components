package uk.org.cse.stockimport.imputation.apertures.windows;

import uk.org.cse.nhm.energycalculator.impl.IWindowUValues;
import uk.org.cse.nhm.energycalculator.impl.WindowUValues;

public class WindowPropertyTables implements IWindowPropertyTables {

	private IWindowFrameFactor frameFactors = new FrameFactors();
	private IWindowUValues uValues = new WindowUValues(false);
	private ITransmittanceFactors transmittanceFactors = new TransmittanceFactors(false);
	
	@Override
	public IWindowUValues getUValues() {
		return uValues;
	}

	@Override
	public IWindowFrameFactor getFrameFactors() {
		return frameFactors;
	}

	@Override
	public ITransmittanceFactors getTransmittanceFactors() {
		return transmittanceFactors;
	}
}
