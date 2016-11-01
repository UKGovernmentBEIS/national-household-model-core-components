package uk.org.cse.nhm.hom.structure;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.GlazingType;
import uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType;

/**
 * Describes glazing as a proportion of an elevation that is glazed in a certain way. 
 * @author hinton
 *
 */
@AutoProperty
public class Glazing {
	/**
	 * The u-value of this glazing
	 */
	private double uValue;
	/**
	 * The light transmission factor, excluding area
	 */
	private double lightTransmissionFactor;
	/**
	 * The gains transmission factor, excluding area
	 */
	private double gainsTransmissionFactor;
	
	/*
	BEISDOC
	NAME: Elevation glazed proportion
	DESCRIPTION: The proportion of the elevation which is glazed with this kind of glazing 
	TYPE: value
	UNIT: Unitless proportion
	ID: elevation-glazed-proportion
	CODSIEB
	*/
	/**
	 * The proportion of the elevation which is glazed
	 */
	private double glazedProportion;
	
	/**
	 * The proportion of the glazed area which is the windows' frames
	 */
	private double frameFactor;

	/*
	BEISDOC
	NAME: Glazing type
	DESCRIPTION: Whether the glazing is single, double, triple or secondary
	TYPE: value
	UNIT: Category
	SAP: Input to Table 6b
	BREDEM: Input to Table 1, Table 24  
	SET: measure.install-glazing
	GET: house.double-glazed-proportion
	STOCK: elevations.csv (percentagedoubleglazed)
	NOTES: The stock importer produces a mixture of double glazed and some single glazed dwellings.
	ID: glazing-type
	CODSIEB
	*/
	/**
	 * The type of this glazing.
	 */
	private GlazingType glazingType;
	
	
	/*
	BEISDOC
	NAME: Frame type
	DESCRIPTION: The type of frame for a glazed element.
	TYPE: value
	UNIT: Category
	SAP: Input to Table 6c
	BREDEM: Input to Table 2
	STOCK: elevations.csv (singleglazedwindowframe, doubleglazedwindowframe)
	ID: frame-type
	CODSIEB
	*/
	private FrameType frameType;
	
	private WindowInsulationType insulationType;
	
    public Glazing(){
        super();
	}

	public Glazing copy() {
		final Glazing other = new Glazing();
		
		other.setUValue(getuValue());
		other.setFrameFactor(getFrameFactor());
		other.setLightTransmissionFactor(getLightTransmissionFactor());
		other.setGainsTransmissionFactor(getGainsTransmissionFactor());
		other.setGlazedProportion(getGlazedProportion());
		other.setFrameFactor(getFrameFactor());
		other.setInsulationType(getInsulationType());
		other.setGlazingType(getGlazingType());
		other.setFrameType(getFrameType());
		
		return other;
	}
    
    /**
     * Default Constructor, sets {@link WindowInsulationType} to {@link WindowInsulationType#Air}
     */
    public Glazing(final double glazedProportion, final GlazingType glazingType, final FrameType frameType) {
        this.glazedProportion = glazedProportion;
        this.glazingType = glazingType;
        this.frameType = frameType;
        this.insulationType = WindowInsulationType.Air;
    }

	public double getuValue() {
		return uValue;
	}

	public void setUValue(final double uValue) {
		this.uValue = uValue;
	}

	public double getLightTransmissionFactor() {
		return lightTransmissionFactor;
	}

	public void setLightTransmissionFactor(final double lightTransmissionFactor) {
		this.lightTransmissionFactor = lightTransmissionFactor;
	}

	public double getGainsTransmissionFactor() {
		return gainsTransmissionFactor;
	}

	public void setGainsTransmissionFactor(final double gainsTransmissionFactor) {
		this.gainsTransmissionFactor = gainsTransmissionFactor;
	}

	/**
	 * The proportion of the non-door opening area which is used by this kind of glazing.
	 * @return
	 */
	public double getGlazedProportion() {
		return glazedProportion;
	}

	public void setGlazedProportion(final double glazedProportion) {
		this.glazedProportion = glazedProportion;
	}

	public GlazingType getGlazingType() {
		return glazingType;
	}

	public void setGlazingType(final GlazingType glazingType) {
		this.glazingType = glazingType;
	}

	public double getFrameFactor() {
		return frameFactor;
	}

	public void setFrameFactor(final double frameFactor) {
		this.frameFactor = frameFactor;
	}

	public WindowInsulationType getInsulationType() {
		return insulationType;
	}

	public FrameType getFrameType() {
		return frameType;
	}

	public void setFrameType(final FrameType frameType) {
		this.frameType = frameType;
	}

	public void setInsulationType(final WindowInsulationType insulationType) {
		this.insulationType = insulationType;
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(frameFactor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((frameType == null) ? 0 : frameType.hashCode());
		temp = Double.doubleToLongBits(gainsTransmissionFactor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(glazedProportion);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((glazingType == null) ? 0 : glazingType.hashCode());
		result = prime * result
				+ ((insulationType == null) ? 0 : insulationType.hashCode());
		temp = Double.doubleToLongBits(lightTransmissionFactor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(uValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Glazing other = (Glazing) obj;
		if (Double.doubleToLongBits(frameFactor) != Double
				.doubleToLongBits(other.frameFactor))
			return false;
		if (frameType != other.frameType)
			return false;
		if (Double.doubleToLongBits(gainsTransmissionFactor) != Double
				.doubleToLongBits(other.gainsTransmissionFactor))
			return false;
		if (Double.doubleToLongBits(glazedProportion) != Double
				.doubleToLongBits(other.glazedProportion))
			return false;
		if (glazingType != other.glazingType)
			return false;
		if (insulationType != other.insulationType)
			return false;
		if (Double.doubleToLongBits(lightTransmissionFactor) != Double
				.doubleToLongBits(other.lightTransmissionFactor))
			return false;
		if (Double.doubleToLongBits(uValue) != Double
				.doubleToLongBits(other.uValue))
			return false;
		return true;
	}
}
