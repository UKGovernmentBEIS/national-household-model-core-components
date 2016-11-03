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
	private Optional<Glazing> glazing;
	
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

	public Optional<Glazing> getGlazing() {
		return glazing;
	}

	public void setGlazing(Optional<Glazing> glazing) {
		this.glazing = glazing;
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

	public GlazingType getGlazingType() {
		if (glazing.isPresent()) {
			return glazing.get().getGlazingType();
		} else {
			throw new RuntimeException("Called getGlazingType on an unglazed door " + getDoorType());
		}
	}
	
	public void setGlazingType(GlazingType type) {
		if (glazing.isPresent()) {
			glazing.get().setGlazingType(type);
		} else {
			throw new RuntimeException("Called setGlazingType on an unglazed door " + getDoorType());
		}
	}

	public double getLightTransmissionFactor() {
		if (glazing.isPresent()) {
			return glazing.get().getLightTransmissionFactor();
		} else {
			throw new RuntimeException("Called getLightTransmissionFactor on an unglazed door " + getDoorType());
		}
	}
	
	public void setLightTransmissionFactor(double lightTransmissionFactor) {
		if (glazing.isPresent()) {
			glazing.get().setLightTransmissionFactor(lightTransmissionFactor);
		} else {
			throw new RuntimeException("Called setLightTransmissionFactor on an unglazed door " + getDoorType());
		}
		
	}

	public double getGainsTransmissionFactor() {
		if (glazing.isPresent()) {
			return glazing.get().getGainsTransmissionFactor();
		} else {
			throw new RuntimeException("Called getGainsTransmissionFactor on an unglazed door " + getDoorType());
		}
	}
	
	public void setGainsTransmissionFactor(double gainsTransmissionFactor) {
		if (glazing.isPresent()) {
			glazing.get().setGainsTransmissionFactor(gainsTransmissionFactor);
		} else {
			throw new RuntimeException("Called setGainsTransmissionFactor on an unglazed door " + getDoorType());
		}
		
	}

	public FrameType getFrameType() {
		if (glazing.isPresent()) {
			return glazing.get().getFrameType();
		} else {
			throw new RuntimeException("Called getFrameType on an unglazed door " + getDoorType());
		}
	}
	
	public void setFrameType(FrameType frameType) {
		if (glazing.isPresent()) {
			glazing.get().setFrameType(frameType);
		} else {
			throw new RuntimeException("Called setFrameType on an unglazed door " + getDoorType());
		}
	}

	public double getFrameFactor() {
		if (glazing.isPresent()) {
			return glazing.get().getFrameFactor();
		} else {
			throw new RuntimeException("Called getFrameFactor on an unglazed door " + getDoorType());
		}
	}

	public void setFrameFactor(double frameFactor) {
		if (glazing.isPresent()) {
			glazing.get().setFrameFactor(frameFactor);
		} else {
			throw new RuntimeException("Called setFrameFactor on an unglazed door " + getDoorType());
		}
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
