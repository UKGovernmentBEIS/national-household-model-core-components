package uk.org.cse.nhm.energycalculator.impl;

import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.GlazingType;
import uk.org.cse.nhm.energycalculator.api.types.WindowGlazingAirGap;
import uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType;

public interface IWindowUValues {

	public abstract void addSingleGlazingUValue(FrameType frameType,
			Double uValue);

	public abstract void addSecondaryGlazingUValue(FrameType frameType,
			Double uValue);

	public abstract void addDoubleGlazing(FrameType frameType,
			WindowInsulationType insulationType, Double uValue, WindowGlazingAirGap airGap);

	public abstract void addTripleGlazing(FrameType frameType,
			WindowInsulationType insulationType, Double uValue, WindowGlazingAirGap airGap);

	public abstract void setCurtainEffectFactor(double curtainEffectFactor);

	public abstract double getUValue(FrameType frameType,
			GlazingType glazingType, WindowInsulationType insulationType, WindowGlazingAirGap airGap);

}