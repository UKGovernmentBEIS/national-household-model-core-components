package uk.org.cse.stockimport.imputation.apertures.windows;

import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.GlazingType;
import uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue;

/**
 * The RD SAP window property imputer.
 * 
 * @author hinton
 * @since 1.0
 */
public class WindowPropertyImputer implements IWindowPropertyImputer {	
	/**
	 * The sublookup table for u values
	 */
	private final IWindowUValues uValues;
	/**
	 * Sublookup for frame fractors
	 */
	private final IWindowFrameFactor frameFactors;
	/**
	 * Sublookup for transmittance factors
	 */
	private final ITransmittanceFactors transmittanceFactors;
	
	public WindowPropertyImputer(){
		uValues = new UValues();
		frameFactors = new FrameFactors();
		transmittanceFactors = new TransmittanceFactors();
	}
	
	public WindowPropertyImputer(IWindowPropertyTables windowPropertyTables){
		uValues = windowPropertyTables.getUValues();
		frameFactors = windowPropertyTables.getFrameFactors();
		transmittanceFactors = windowPropertyTables.getTransmittanceFactors();
	}
	
	@Override
	public double getUValue(SAPAgeBandValue ageBand, FrameType frameType, GlazingType glazingType, WindowInsulationType insulationType) {
		return uValues.getUValue(frameType, glazingType, insulationType);
	}

	@Override
	public double getLightTransmittance(SAPAgeBandValue ageBand, FrameType frameType,
			GlazingType type, WindowInsulationType insulationType) {
		return transmittanceFactors.getLightTransmittance(type, insulationType);
	}

	@Override
	public double getGainsTransmittance(SAPAgeBandValue ageBand, FrameType frameType,
			GlazingType type, WindowInsulationType insulationType) {
		return transmittanceFactors.getGainsTransmittance(type, insulationType);
	}

	@Override
	public double getFrameFactor(SAPAgeBandValue ageBand, FrameType frameType,
			GlazingType type, WindowInsulationType insulationType) {
		return frameFactors.getFrameFactor(frameType);
	}
}
