package uk.org.cse.nhm.hom.structure;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.GlazingType;
import uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType;
import uk.org.cse.nhm.hom.components.fabric.types.DoorType;

@AutoProperty
public class Door implements IGlazedElement {
	private DoorType doorType;
	private Optional<Glazing> glazing = Optional.absent();

	private double uValue;
	private double area;

	public DoorType getDoorType() {
		return doorType;
	}

	public void setDoorType(final DoorType doorType) {
		this.doorType = doorType;
	}

	private void ensureGlazing() {
		if (doorType == DoorType.Glazed) {
			if (!glazing.isPresent()) {
				glazing = Optional.of(new Glazing());
			}

		} else {
			throw new UnsupportedOperationException("Tried to set a glazing property on an unglazed door.");
		}
	}

	private void setGlazing(final Optional<Glazing> glazing) {
		this.glazing = glazing;
	}

	private Optional<Glazing> getGlazing() {
		return glazing;
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

		other.setArea(getArea());
		other.setDoorType(getDoorType());
		other.setGlazing(getGlazing());
		other.setuValue(getuValue());

		return other;
	}

	@Override
	public GlazingType getGlazingType() {
		if (glazing.isPresent()) {
			return glazing.get().getGlazingType();
		} else {
			throw new RuntimeException("Called getGlazingType on an unglazed door " + getDoorType());
		}
	}

	@Override
	public void setGlazingType(final GlazingType type) {
		ensureGlazing();
		glazing.get().setGlazingType(type);
	}

	@Override
	public double getLightTransmissionFactor() {
		if (glazing.isPresent()) {
			return glazing.get().getLightTransmissionFactor();
		} else {
			throw new RuntimeException("Called getLightTransmissionFactor on an unglazed door " + getDoorType());
		}
	}

	@Override
	public void setLightTransmissionFactor(final double lightTransmissionFactor) {
		ensureGlazing();
		glazing.get().setLightTransmissionFactor(lightTransmissionFactor);
	}

	@Override
	public double getGainsTransmissionFactor() {
		if (glazing.isPresent()) {
			return glazing.get().getGainsTransmissionFactor();
		} else {
			throw new RuntimeException("Called getGainsTransmissionFactor on an unglazed door " + getDoorType());
		}
	}

	@Override
	public void setGainsTransmissionFactor(final double gainsTransmissionFactor) {
		ensureGlazing();
		glazing.get().setGainsTransmissionFactor(gainsTransmissionFactor);
	}

	@Override
	public FrameType getFrameType() {
		if (glazing.isPresent()) {
			return glazing.get().getFrameType();
		} else {
			throw new RuntimeException("Called getFrameType on an unglazed door " + getDoorType());
		}
	}

	@Override
	public void setFrameType(final FrameType frameType) {
		ensureGlazing();
		glazing.get().setFrameType(frameType);
	}

	@Override
	public double getFrameFactor() {
		if (glazing.isPresent()) {
			return glazing.get().getFrameFactor();
		} else {
			throw new RuntimeException("Called getFrameFactor on an unglazed door " + getDoorType());
		}
	}

	@Override
	public void setFrameFactor(final double frameFactor) {
		ensureGlazing();
		glazing.get().setFrameFactor(frameFactor);
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
