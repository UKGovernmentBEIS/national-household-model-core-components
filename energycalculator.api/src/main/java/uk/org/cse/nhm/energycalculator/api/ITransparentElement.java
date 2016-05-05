package uk.org.cse.nhm.energycalculator.api;

import uk.org.cse.nhm.energycalculator.api.types.OvershadingType;

public interface ITransparentElement {
	/**
	 * @return the visible light transmittivity; this is analogous to {@link #getSolarGainTransmissivity()},
	 * in that the area is included
	 */
	public double getVisibleLightTransmittivity();
	
	/**
	 * @return the solar gain transmissivity in M2 - this includes the area and the dimensionless quantity
	 * describing how much solar energy is absorbed, so a 1M2 panel which absorbs half of the power it receives from the sun
	 * will have a gain transmissivity of 0.5 M2; multiply this by W/M2 to get the solar gain for this element at a given power.
	 */
	double getSolarGainTransmissivity();
	
	/**
	 * @return the angle from the dot product of this plane's normal and the north vector.
	 */
	double getHorizontalOrientation();
	
	/**
	 * @return the angle between the normal from this plane and the vertical
	 */
	double getVerticalOrientation();
	
	/**
	 * @return the degree of overshading for this transparent element.
	 */
	OvershadingType getOvershading();
}
