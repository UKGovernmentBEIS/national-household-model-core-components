package uk.org.cse.nhm.hom.structure.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.ThermalMassLevel;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;
import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;
import uk.org.cse.nhm.hom.structure.IMutableWall;
import uk.org.cse.nhm.hom.util.PhysicsUtil;

/**
 * Utility class which wraps a {@link SegmentData} together with a height and a start point, to give an IMutableWall.
 * 
 * @author hinton
 */
class SegmentWrapper implements IMutableWall {
    private static final Logger log = LoggerFactory.getLogger(SegmentWrapper.class);
    private final double fromX, fromY, height;

    private SegmentData endpoint;

    private final Storey owner;

    public SegmentWrapper(final double fromX, final double fromY, final double height, final SegmentData endpoint, final Storey owner) {
        super();
        this.fromX = fromX;
        this.fromY = fromY;
        this.height = height;
        this.endpoint = endpoint;
        this.owner = owner;
    }

    @Override
	public double getAirChangeRate() {
        return endpoint.getAirChangeRate();
    }
    
    @Override
    public double getThicknessWithInsulation() {
    	return endpoint.getBasicThickness() +
    			internalOrExternalInsulationThickness();
    }
    
    private double internalOrExternalInsulationThickness() {
		return endpoint.getWallInsulationThickness(WallInsulationType.Internal) +
				endpoint.getWallInsulationThickness(WallInsulationType.External);
    }
    
    @Override
    public double getThicknessWithoutInsulation() {
    	return endpoint.getBasicThickness();
    }
    
    @Override
    public void setThicknessWithExistingInsulation(final double newThickness) {
    	endpoint.setBasicThickness(
    			Math.max(0, 
    					newThickness - internalOrExternalInsulationThickness()
    					));
    }

    @Override
	public ElevationType getElevationType() {
    	final double changeInX = endpoint.getX() - fromX;
    	final double changeInY = endpoint.getY() - fromY;
    	
    	if (changeInX > 0) return ElevationType.LEFT;
    	else if (changeInX < 0) return ElevationType.RIGHT;
    	else if (changeInY > 0) return ElevationType.BACK;
    	else return ElevationType.FRONT;
    }

    @Override
	public boolean isPartyWall() {
        return endpoint.isAttached();
    }

    @Override
	public double getArea() {
        return height * getLength();
    }

    @Override
	public double getUValue() {
        return endpoint.getUValue();
    }

    @Override
	public WallConstructionType getWallConstructionType() {
        return endpoint.getWallConstructionType();
    }

    @Override
	public void setWallConstructionType(final WallConstructionType type) {
        endpoint.setWallConstructionType(type);
    }

    @Override
	public void setUValue(final double u) {
        endpoint.setUValue(u);
    }


    @Override
    public void split(final double proportion) {
        log.debug("split {} at {}", this, proportion);
        // OK, so what we want to do is to move the endpoint to the given position along the line
        // and then insert a new segment data into the storey in front of the endpoint

        // the order of these statements is important:

        // first we want to make a new segment data which is identical to our endpoint (so it has the right coordinates)
        final SegmentData insert = new SegmentData(endpoint);

        // now we interpolate our new position
        final double x = fromX + (endpoint.getX() - fromX) * proportion;
        final double y = fromY + (endpoint.getY() - fromY) * proportion;

        insert.setX(x);
        insert.setY(y);
       
        
        // update our coordinates
//        endpoint.setX(x);
//        endpoint.setY(y);

        // finally put the new point in before our endpoint
        owner.insertSegment(endpoint, insert);
        this.endpoint = insert;
        log.debug("became {}", this);
    }

    @Override
    public String toString() {
        return String.format("Wall (%.2f %.2f) -> (%.2f %.2f)", fromX, fromY, endpoint.getX(), endpoint.getY());
    }

    @Override
    public double getLength() {
        return Math.sqrt(Math.pow(fromX - endpoint.getX(), 2) + Math.pow(fromY - endpoint.getY(), 2));
    }

    @Override
    public void setAirChangeRate(final double airChangeRate) {
        endpoint.setAirChangeRate(airChangeRate);
    }

    @Override
    public Set<WallInsulationType> getWallInsulationTypes() {
        return endpoint.getWallInsulationTypes();
    }

	@Override
	public void setWallInsulationThicknessAndAddOrRemoveInsulation(final WallInsulationType type,
			final double thickness) {
		endpoint.setWallInsulationThickness(type, thickness);
	}

	@Override
	public double getWallInsulationThickness(final WallInsulationType type) {
		return endpoint.getWallInsulationThickness(type);
	}

	@Override
	public double getWallInsulationThickness(Set<WallInsulationType> types) {
		double accum = 0;

		for (WallInsulationType t : types) {
			accum += getWallInsulationThickness(t);
		}

		return accum;
	}

	@Override
	public void addInsulation(final WallInsulationType type, final double thickness, final double rValue) {
		endpoint.setWallInsulationThickness(type, thickness + endpoint.getWallInsulationThickness(type));
		
		endpoint.setUValue(PhysicsUtil.addRValueToUValue(endpoint.getUValue(), thickness * rValue));
	}

	@Override
	public Optional<ThermalMassLevel> getThermalMassLevel() {
		/*
		BEISDOC
		NAME: Wall Thermal Mass Category
		DESCRIPTION: Lookup the thermal mass level of the wall based on its construction type and whether it has insulation.
		TYPE: lookup
		UNIT: Thermal Mass Level
		DEPS: thermal-mass-level
		NOTES: Specified by BRE.
		ID: wall-thermal-mass-category
		CODSIEB
		*/

		switch (getWallConstructionType().getWallType()) {
		case Internal:
		case Party:
			return Optional.absent();

		case External:
			switch(getWallConstructionType()) {
			case SolidBrick:
			case GraniteOrWhinstone:
			case Sandstone:
				return Optional.of(
						(internalOrExternalInsulationThickness() > 0) ? ThermalMassLevel.LOW : ThermalMassLevel.HIGH);

			case TimberFrame:
				return Optional.of(ThermalMassLevel.LOW);

			default:
				return Optional.of(ThermalMassLevel.MEDIUM);
			}
		default:
			throw new IllegalArgumentException("Unknown wall type " + getWallConstructionType().getWallType());
		}
	}

	@Override
	public boolean hasWallInsulation(WallInsulationType type) {
		return endpoint.getWallInsulationTypes().contains(type);
	}
}
