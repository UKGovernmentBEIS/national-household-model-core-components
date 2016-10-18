package uk.org.cse.nhm.hom.structure.impl;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;

import uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType;
import uk.org.cse.nhm.hom.components.fabric.types.WallInsulationType;
import uk.org.cse.nhm.hom.components.fabric.types.WallType;

/**
 * This represents the segment of a {@link Storey}'s perimeter which <em>ends</em> at a given coordinate pair.
 * @author hinton
 *
 */
@AutoProperty
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.ANY, setterVisibility = Visibility.ANY)
public class SegmentData {
	private double x, y;
	private double airChangeRate;
	private WallConstructionType wallConstructionType;
    
    private final double[] wallInsulation = new double[WallInsulationType.values().length];
	
	private double kValue;
	private double uValue;
	private double basicThickness;
	
    /**
     * Default Constructor.
     */
    public SegmentData() {
    }

    public SegmentData(final double i, final double j) {
		setX(i);
		setY(j);
	}

	public SegmentData(final SegmentData other) {
		setX(other.getX());
		setY(other.getY());
		setAirChangeRate(other.getAirChangeRate());
		setUValue(other.getUValue());
		setKValue(other.getKValue());
		setWallConstructionType(other.getWallConstructionType());
		setBasicThickness(other.getBasicThickness());
		for (final WallInsulationType wit : WallInsulationType.values()) {
			setWallInsulationThickness(wit, other.getWallInsulationThickness(wit));
		}
	}

    public Map<WallInsulationType, Double> getWallInsulation() {
        final Map<WallInsulationType, Double> out = new java.util.HashMap<>();
        for (final WallInsulationType wit : WallInsulationType.values()) {
            if (getWallInsulationThickness(wit) > 0) {
                out.put(wit, getWallInsulationThickness(wit));
            }
        }
        return out;
    }

    public void setWallInsulation(Map<WallInsulationType, Double> wallInsulation) {
        for (final WallInsulationType wit : wallInsulation.keySet()) {
            setWallInsulationThickness(wit, wallInsulation.get(wit));
        }
    }
    
    @JsonIgnore
	public double getWallInsulationThickness(final WallInsulationType wit) {
        return wallInsulation[wit.ordinal()];
	}

	public double getX() {
		return x;
	}

	public void setX(final double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(final double y) {
		this.y = y;
	}

	public double getAirChangeRate() {
		return this.airChangeRate;
	}

    @JsonIgnore
	public boolean isAttached() {
		return getWallConstructionType().getWallType() == WallType.Party;
	}

	public double getUValue() {
		return this.uValue;
	}

	public double getKValue() {
		return this.kValue;
	}

	public WallConstructionType getWallConstructionType() {
		return this.wallConstructionType;
	}

	public void setWallConstructionType(final WallConstructionType type) {
		this.wallConstructionType = type;
	}

	public void setUValue(final double u) {
		this.uValue = u;
	}

	public void setKValue(final double k) {
		this.kValue = k;
	}

	public double getkValue() {
		return kValue;
	}

	public void setkValue(final double kValue) {
		this.kValue = kValue;
	}

	public double getuValue() {
		return uValue;
	}

	public void setuValue(final double uValue) {
		this.uValue = uValue;
	}

	public void setAirChangeRate(final double airChangeRate) {
		this.airChangeRate = airChangeRate;
	}
	
	public double getBasicThickness() {
		return basicThickness;
	}

	public void setBasicThickness(final double basicThickness) {
		this.basicThickness = basicThickness;
	}

    @JsonIgnore
	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

    @JsonIgnore
	public void setWallInsulationThickness(final WallInsulationType type, final double thickness) {
        wallInsulation[type.ordinal()] = Math.max(0, thickness);
	}

    @JsonIgnore
	public Set<WallInsulationType> getWallInsulationTypes() {
		final EnumSet<WallInsulationType> types = EnumSet.noneOf(WallInsulationType.class);
        for (final WallInsulationType wit : WallInsulationType.values()) {
            if (wallInsulation[wit.ordinal()] > 0) types.add(wit);
        }
        return types;
	}
	
	@Override
	public boolean equals(final Object obj) {
		return Pojomatic.equals(this, obj);
	}
	
	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}

    @JsonIgnore
	public SegmentData copy() {
		return new SegmentData(this);
	}
}
