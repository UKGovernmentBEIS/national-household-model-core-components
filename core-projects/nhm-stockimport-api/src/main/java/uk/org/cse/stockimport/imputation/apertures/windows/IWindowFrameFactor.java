package uk.org.cse.stockimport.imputation.apertures.windows;

import uk.org.cse.nhm.energycalculator.api.types.FrameType;

public interface IWindowFrameFactor {

	/**
	 * @since 1.0
	 */
	public abstract double getFrameFactor(FrameType frameType);

	public abstract void setFrameFactor(FrameType frameType, Double factor);

}