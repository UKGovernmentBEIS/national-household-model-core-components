package uk.org.cse.stockimport.imputation.apertures.windows;

import uk.org.cse.nhm.energycalculator.mode.WindowUValues;

public class WindowPropertyTables implements IWindowPropertyTables {

	private IWindowFrameFactor frameFactors = new FrameFactors();
	private WindowUValues uValues = new WindowUValues(false);
	private ITransmittanceFactors transmittanceFactors = new TransmittanceFactors(false);
	
	@Override
	public WindowUValues getUValues() {
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
