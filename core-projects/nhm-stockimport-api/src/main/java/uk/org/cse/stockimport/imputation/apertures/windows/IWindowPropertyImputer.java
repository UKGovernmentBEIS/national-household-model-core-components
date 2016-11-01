package uk.org.cse.stockimport.imputation.apertures.windows;

import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.GlazingType;
import uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType;
import uk.org.cse.nhm.hom.types.SAPAgeBandValue;

/**
 * Thing for determining properties of windows, according to SAP and RDSAP rules.
 * 
 * @author hinton
 * @since 1.0
 */
public interface IWindowPropertyImputer {
	/**
	 * Get the U value for a window of the given glazing type, for a property constructed in the given interval
	 * @param glazingType
	 * @param WindowInsulationType TODO
	 * @param ageBand
	 * @return
	 * @since 1.0
	 */
	public double getUValue(final SAPAgeBandValue ageBand, final FrameType frameType, final GlazingType glazingType, WindowInsulationType insulationType);
	/**
     * @since 1.0
     */
    public double getLightTransmittance(final SAPAgeBandValue ageBand, final FrameType frameType, final GlazingType type, WindowInsulationType insulationType);
    /**
     * @since 1.0
     */
    public double getGainsTransmittance(final SAPAgeBandValue ageBand, final FrameType frameType, final GlazingType type, WindowInsulationType insulationType);
    /**
     * @since 1.0
     */
    public double getFrameFactor(final SAPAgeBandValue ageBand, final FrameType frameType, final GlazingType type, WindowInsulationType insulationType);
}
