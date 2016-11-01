package uk.org.cse.nhm.hom.structure;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.GlazingType;
import uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType;
import uk.org.cse.nhm.hom.components.fabric.types.DoorType;

@AutoProperty
public class Door {
	private DoorType doorType;
	private GlazingType glazingType;
	
	private FrameType frameType;
	private double uValue;

	private double frameFactor = 1;
	private double lightTransmissionFactor = 0;
	private double gainsTransmissionFactor = 0;
	
	private double area;

	public DoorType getDoorType() {
		return doorType;
	}

	public void setDoorType(final DoorType doorType) {
		this.doorType = doorType;
	}
	
    public GlazingType getGlazingType() {
		return glazingType;
	}

	public void setGlazingType(GlazingType glazingType) {
		this.glazingType = glazingType;
	}
	
	public WindowInsulationType getWindowInsulationType() {
		return WindowInsulationType.Air;
	}

	public double getuValue() {
		return uValue;
	}

	public void setuValue(final double uValue) {
		this.uValue = uValue;
	}

	public double getArea() {
		return area;
	}

	public void setArea(final double area) {
		this.area = area;
	}

    /**
     * Return the frameTYpe.
     * 
     * @return the frameTYpe
     */
    public FrameType getFrameType() {
        return frameType;
    }

    /**
     * Set the frameTYpe.
     * 
     * @param frameType the frameTYpe
     */
    public void setFrameType(final FrameType frameType) {
        this.frameType = frameType;
    }
    
	public double getFrameFactor() {
		return frameFactor;
	}

	public void setFrameFactor(double frameFactor) {
		this.frameFactor = frameFactor;
	}

	public double getLightTransmissionFactor() {
		return lightTransmissionFactor;
	}

	public void setLightTransmissionFactor(double lightTransmissionFactor) {
		this.lightTransmissionFactor = lightTransmissionFactor;
	}

	public double getGainsTransmissionFactor() {
		return gainsTransmissionFactor;
	}

	public void setGainsTransmissionFactor(double gainsTransmissionFactor) {
		this.gainsTransmissionFactor = gainsTransmissionFactor;
	}
    
	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
	
	public Door copy() {
		final Door other = new Door();
		
		other.setArea(getArea());
		other.setDoorType(getDoorType());
		other.setGlazingType(getGlazingType());
		other.setFrameType(getFrameType());
		other.setuValue(getuValue());
		other.setFrameFactor(getFrameFactor());
		other.setLightTransmissionFactor(getLightTransmissionFactor());
		other.setGainsTransmissionFactor(getGainsTransmissionFactor());
		
		return other;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(area);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((doorType == null) ? 0 : doorType.hashCode());
		temp = Double.doubleToLongBits(frameFactor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((frameType == null) ? 0 : frameType.hashCode());
		temp = Double.doubleToLongBits(gainsTransmissionFactor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((glazingType == null) ? 0 : glazingType.hashCode());
		temp = Double.doubleToLongBits(lightTransmissionFactor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(uValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Door other = (Door) obj;
		if (Double.doubleToLongBits(area) != Double.doubleToLongBits(other.area))
			return false;
		if (doorType != other.doorType)
			return false;
		if (Double.doubleToLongBits(frameFactor) != Double.doubleToLongBits(other.frameFactor))
			return false;
		if (frameType != other.frameType)
			return false;
		if (Double.doubleToLongBits(gainsTransmissionFactor) != Double.doubleToLongBits(other.gainsTransmissionFactor))
			return false;
		if (glazingType != other.glazingType)
			return false;
		if (Double.doubleToLongBits(lightTransmissionFactor) != Double.doubleToLongBits(other.lightTransmissionFactor))
			return false;
		if (Double.doubleToLongBits(uValue) != Double.doubleToLongBits(other.uValue))
			return false;
		return true;
	}
}
