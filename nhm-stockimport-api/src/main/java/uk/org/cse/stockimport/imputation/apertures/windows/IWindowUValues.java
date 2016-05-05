package uk.org.cse.stockimport.imputation.apertures.windows;

import uk.org.cse.nhm.hom.components.fabric.types.FrameType;
import uk.org.cse.nhm.hom.components.fabric.types.GlazingType;
import uk.org.cse.nhm.hom.components.fabric.types.WindowInsulationType;

public interface IWindowUValues {

	public abstract void addSingleGlazingUValue(FrameType frameType,
			Double uValue);

	public abstract void addSecondaryGlazingUValue(FrameType frameType,
			Double uValue);

	public abstract void addDoubleGlazing(FrameType frameType,
			WindowInsulationType insulationType, Double uValue);

	public abstract void addTripleGlazing(FrameType frameType,
			WindowInsulationType insulationType, Double uValue);

	public abstract void setCurtainEffectFactor(double curtainEffectFactor);

	public abstract double getUValue(FrameType frameType,
			GlazingType glazingType, WindowInsulationType insulationType);

}