package uk.org.cse.stockimport.imputation.apertures.windows;

import uk.org.cse.nhm.hom.components.fabric.types.FrameType;

public interface IWindowFrameFactor {

	/**
	 * @since 1.0
	 */
	public abstract double getFrameFactor(FrameType frameType);

	public abstract void setFrameFactor(FrameType frameType, Double factor);

}