package uk.org.cse.nhm.hom.structure;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.GlazingType;
import uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType;
import uk.org.cse.nhm.hom.components.fabric.types.DoorType;

@AutoProperty
public class Door implements IGlazedElement {
	private DoorType doorType;
	private final Glazing glazing = new Glazing();

	private double uValue;
	private double area;

	public DoorType getDoorType() {
		return doorType;
	}

	public void setDoorType(final DoorType doorType) {
		this.doorType = doorType;
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

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

	public Door copy() {
		final Door other = new Door();

		other.setDoorType(getDoorType());
		other.setArea(getArea());
		other.setuValue(getuValue());

		other.setFrameFactor(getFrameFactor());
		other.setFrameType(getFrameType());
		other.setGainsTransmissionFactor(getGainsTransmissionFactor());
		other.setLightTransmissionFactor(getLightTransmissionFactor());
		other.setGlazingType(getGlazingType());


		return other;
	}

	@Override
	public GlazingType getGlazingType() {
		return glazing.getGlazingType();
	}

	@Override
	public void setGlazingType(final GlazingType type) {
		glazing.setGlazingType(type);
	}

	@Override
	public double getLightTransmissionFactor() {
		return glazing.getLightTransmissionFactor();
	}

	@Override
	public void setLightTransmissionFactor(final double lightTransmissionFactor) {
		glazing.setLightTransmissionFactor(lightTransmissionFactor);
	}

	@Override
	public double getGainsTransmissionFactor() {
		return glazing.getGainsTransmissionFactor();
	}

	@Override
	public void setGainsTransmissionFactor(final double gainsTransmissionFactor) {
		glazing.setGainsTransmissionFactor(gainsTransmissionFactor);
	}

	@Override
	public FrameType getFrameType() {
		return glazing.getFrameType();
	}

	@Override
	public void setFrameType(final FrameType frameType) {
		glazing.setFrameType(frameType);
	}

	@Override
	public double getFrameFactor() {
		return glazing.getFrameFactor();
	}

	@Override
	public void setFrameFactor(final double frameFactor) {
		glazing.setFrameFactor(frameFactor);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(area);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((doorType == null) ? 0 : doorType.hashCode());
		result = prime * result + ((glazing == null) ? 0 : glazing.hashCode());
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
		if (Double.doubleToLongBits(area) != Double.doubleToLongBits(other.area))
			return false;
		if (doorType != other.doorType)
			return false;
		if (glazing == null) {
			if (other.glazing != null)
				return false;
		} else if (!glazing.equals(other.glazing))
			return false;
		if (Double.doubleToLongBits(uValue) != Double.doubleToLongBits(other.uValue))
			return false;
		return true;
	}
}
