package uk.org.cse.nhm.hom.structure;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import uk.org.cse.nhm.hom.components.fabric.types.DoorType;
import uk.org.cse.nhm.hom.components.fabric.types.FrameType;

@AutoProperty
public class Door {
	private DoorType doorType;
    private FrameType frameType;
	private double uValue;
	
	private double area;

	public DoorType getDoorType() {
		return doorType;
	}

	public void setDoorType(final DoorType doorType) {
		this.doorType = doorType;
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
    
	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
	
	public Door copy() {
		final Door other = new Door();
		
		other.setArea(getArea());
		other.setDoorType(getDoorType());
		other.setFrameType(getFrameType());
		other.setuValue(getuValue());
		
		return other;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(area);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((doorType == null) ? 0 : doorType.hashCode());
		result = prime * result
				+ ((frameType == null) ? 0 : frameType.hashCode());
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
		final Door other = (Door) obj;
		if (Double.doubleToLongBits(area) != Double
				.doubleToLongBits(other.area))
			return false;
		if (doorType != other.doorType)
			return false;
		if (frameType != other.frameType)
			return false;
		if (Double.doubleToLongBits(uValue) != Double
				.doubleToLongBits(other.uValue))
			return false;
		return true;
	}
}
