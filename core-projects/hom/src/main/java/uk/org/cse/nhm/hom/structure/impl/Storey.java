package uk.org.cse.nhm.hom.structure.impl;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.ThermalMassLevel;
import uk.org.cse.nhm.energycalculator.api.types.AreaType;
import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;
import uk.org.cse.nhm.energycalculator.api.types.WallType;
import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;
import uk.org.cse.nhm.hom.components.fabric.types.FloorLocationType;
import uk.org.cse.nhm.hom.structure.IElevation;
import uk.org.cse.nhm.hom.structure.IMutableWall;
import uk.org.cse.nhm.hom.structure.IStorey;
import uk.org.cse.nhm.hom.structure.IWall;
import uk.org.cse.nhm.hom.structure.impl.Elevation.IDoorVisitor;
import uk.org.cse.nhm.hom.util.PhysicsUtil;

/**
 * Represents a storey of a building in the house model. To set up a storey, you will want to do something like
 * <pre>
 *  final Storey s = new Storey();
 *  s.setPerimeter(somePolygon);
 *  for (final IMutableWall wall : s.getWalls()) {
 *    wall.split();
 *    wall.setFoo();
 *    wall.setBar();
 *  }
 * </pre>
 * <br />
 * Calls to {@link #setPerimeter(Polygon)} will clear all existing state for the storey's walls, and 
 * replace them with new empty walls according to the given polygon. This is the only way to move the corners
 * of the storey around.
 * <br />
 * Once the perimeter polygon has been set, you can iterate over the walls in it with {@link #getWalls()}, and
 * then use the {@link IMutableWall} interface to alter the walls' attributes
 * <br />
 * The storey also has some other attributes, which are mostly self-explanatory:
 * <ul>
 * 	<li>{@link #getFloorKValue()}</li>
 *  <li>{@link #getFloorUValue()}</li>
 *  <li>{@link #getCeilingKValue()}</li>
 *  <li>{@link #getCeilingUValue()}</li>
 *  <li>{@link #getFloorLocationType()}</li>
 *  <li>{@link #getHeight()}</li>
 * </ul>
 * 
 * TODO internal dimensions vs. external dimensions.
 * TODO room in roof behaviour
 * TODO basement behaviour
 * 
 * @author hinton
 *
 */
@AutoProperty
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class Storey implements IStorey {
	private static final Logger log = LoggerFactory.getLogger(Storey.class);
	/**
	 * The segment data for each segment of this storey's perimeter. Each segment data describes the end of a segment (so the
	 * segment's line goes from the previous segment data in the list to the given one)
	 */
	protected List<SegmentData> segments = new ArrayList<SegmentData>();

	/*
	BEISDOC
	ID: storey-height
	NAME: Storey height
	DESCRIPTION: The variable which contains the height of one storey
	TYPE: variable
	UNIT: m
	SAP: (2a,2b...)
	BREDEM: Input variable
	DEPS: 
	GET: 
	SET: 
	STOCK: storeys.csv (storyheight)
	CODSIEB
	*/
	
	/**
	 * The ceiling height of this storey.
	 */
	private double height;
	
	/**
	 * What kind of storey this is - this affects how we present area to the calculator.
	 */
	private FloorLocationType floorLocationType;

	/**
	 * The u-value for heat loss from this floor to void space or ground below it
	 */
	private double floorUValue;
	
	/**
	 * The u-value for heat loss from this floor to void space above it.
	 */
	private double ceilingUValue;
	
	private transient double exposedPerimeterCache = -1;
	
	public Storey() {
		
	}
	
	/**
	 * This will destroy all the existing information about the storey, and replace it with a new polygon with
	 * default values for all the segments. Use {@link #getWalls()} to iterate through the walls and set their
	 * u-values and construction types. Actually, that stuff could live in the elevation as well. hmm.
	 * 
	 * @param polygon
	 */
    public void setPerimeter(final Polygon polygon, final double scalingFactor) {
		segments.clear();
		for (int i = 0; i < polygon.npoints; i++) {
            segments.add(new SegmentData(polygon.xpoints[i] / scalingFactor, polygon.ypoints[i] / scalingFactor));
		}
	}
    
    protected void copySegments(final List<SegmentData> segmentsToCopy) {
		segments.clear();
		for (final SegmentData sd : segmentsToCopy) {
			segments.add(sd.copy());
		}
    }
	
    public void setPerimeter(final Polygon polygon) {
        this.setPerimeter(polygon, 1);
    }

	/* (non-Javadoc)
	 * @see uk.org.cse.nhm.hom.structure.IStorey#getHeight()
	 */
	@Override
	public double getHeight() {
		return height;
	}

	/* (non-Javadoc)
	 * @see uk.org.cse.nhm.hom.structure.IStorey#setHeight(double)
	 */
	@Override
	public void setHeight(final double height) {
		this.height = height;
	}

	/* (non-Javadoc)
	 * @see uk.org.cse.nhm.hom.structure.IStorey#getFloorLocationType()
	 */
	@Override
	public FloorLocationType getFloorLocationType() {
		return floorLocationType;
	}

	/* (non-Javadoc)
	 * @see uk.org.cse.nhm.hom.structure.IStorey#setFloorLocationType(uk.org.cse.nhm.hom.components.fabric.types.FloorLocationType)
	 */
	@Override
	public void setFloorLocationType(final FloorLocationType floorLocationType) {
		this.floorLocationType = floorLocationType;
	}
	
	/* (non-Javadoc)
	 * @see uk.org.cse.nhm.hom.structure.IStorey#getFloorUValue()
	 */
	@Override
	public double getFloorUValue() {
		return floorUValue;
	}

	/* (non-Javadoc)
	 * @see uk.org.cse.nhm.hom.structure.IStorey#setFloorUValue(double)
	 */
	@Override
	public void setFloorUValue(final double floorUValue) {
		this.floorUValue = floorUValue;
	}

	/* (non-Javadoc)
	 * @see uk.org.cse.nhm.hom.structure.IStorey#getCeilingUValue()
	 */
	@Override
	public double getCeilingUValue() {
		return ceilingUValue;
	}

	/* (non-Javadoc)
	 * @see uk.org.cse.nhm.hom.structure.IStorey#setCeilingUValue(double)
	 */
	@Override
	public void setCeilingUValue(final double ceilingUValue) {
		this.ceilingUValue = ceilingUValue;
	}
	
	/**
	 * Present the heat loss surfaces from this storey to the given {@link IEnergyCalculatorVisitor}.
	 * 
	 * @param visitor the visitor
	 * @param elevations a map from elevation type to elevation, which should be populated for all four elevations
	 * @param areaBelow the heated area underneath this storey (i.e. the area of the storey below)
	 * @param areaAbove the heated area above this storey (i.e. the area of the storey above)
	 */
	public void accept(
			final IEnergyCalculatorVisitor visitor, 
			final Map<ElevationType, IElevation> elevations, 
			final Map<ElevationType, IDoorVisitor> doors,
			final double areaBelow, 
			final double areaAbove) {
		// present heat loss areas on the perimeter
		switch (floorLocationType) {
		case ROOM_IN_ROOF:
		case BASEMENT:
			//TODO RIR behaviour.
			//TODO basement behaviour
			log.debug("Floor location type {} may need special handling, but I am treating it as a normal floor", floorLocationType);
		default:
			showVisitorHeatLossWalls(visitor, elevations, doors);
		}
		
				// present heat loss areas above and below
		final double area = getArea();
		if (area > areaBelow) {
			/*
			BEISDOC
			NAME: Floor element
			DESCRIPTION: The area, u-value and k-value for a section of floor which contributes to heat loss.
			TYPE: formula
			UNIT: area m^2, u-value W/m^2/℃, k-value kJ/m^2/℃
			SAP: 28b, 32a
			BREDEM: 3B
			DEPS: 
			GET: house.u-value
			SET: action.reset-floors,action.set-floor-insulation
			STOCK: cases.csv (grndfloortype), storeys.csv (shape of polygons), imputation schema (floors)
			ID: floor-element
			NOTES: Part floor's u-value is always 0.
			CODSIEB
			*/
			//TODO does area below into ground count as External Area for thermal bridging purposes?
			// there is a heat loss area pointing downwards, whose area is area - areaBelow
			final double heatLossAreaBelow = area - areaBelow;
			visitor.visitFabricElement(AreaType.ExternalFloor, heatLossAreaBelow, floorUValue, Optional.<ThermalMassLevel>absent());
			visitor.visitFabricElement(AreaType.PartyFloor, areaBelow, 0, Optional.<ThermalMassLevel>absent());
			
			visitor.visitFabricElement(AreaType.PartyFloor, areaBelow, 0, Optional.<ThermalMassLevel>absent());
		}
		
		if (area > areaAbove) {
			/*
			BEISDOC
			NAME: Ceiling element
			DESCRIPTION: The area, u-value and k-value for a section of ceiling which contributes to heat loss
			TYPE: formula
			UNIT: area m^2, u-value W/m^2/℃, k-value kJ/m^2/℃
			SAP: (30,32b)
			BREDEM: 3B
			DEPS: 
			GET: house.u-value
			SET: action.reset-roofs
			STOCK: roofs.csv (all fields), imputation schema (roofs)
			ID: ceiling-element
			NOTES: Party ceiling's u-value is always 0.
			CODSIEB
			*/
			// there is a heat loss area pointing upwards, whose area is area - areaAbove
			final double heatLossAreaAbove = area - areaAbove;
			visitor.visitFabricElement(AreaType.ExternalCeiling, heatLossAreaAbove, ceilingUValue, Optional.<ThermalMassLevel>absent());
			visitor.visitFabricElement(AreaType.PartyCeiling, areaAbove, 0, Optional.<ThermalMassLevel>absent());
		}
	}
	
	/**
	 * Modify {@link #getCeilingUValue()} by adding a layer of insulation which has the stated r-value
	 * @param rValue
	 * @since 1.3.4
	 */
	public void addCeilingInsulation(final double rValue) {
		setCeilingUValue(PhysicsUtil.addRValueToUValue(getCeilingUValue(), rValue));
	}
	
	/* (non-Javadoc)
	 * @see uk.org.cse.nhm.hom.structure.IStorey#getVolume()
	 */
	@Override
	public double getVolume() {
		/*
		BEISDOC
		ID: storey-volume
		NAME: Storey volume
		DESCRIPTION: The volume of a single storey
		TYPE: formula
		UNIT: m3
		SAP: (3a,3b..)
		BREDEM: Input variable
		DEPS: storey-floor-area,storey-height
		GET: house.volume
		SET: 
		CODSIEB
		*/
		return getArea() * height;
	}
	
	/* (non-Javadoc)
	 * @see uk.org.cse.nhm.hom.structure.IStorey#getArea()
	 */
	@Override
	public double getArea() {
		/*
		BEISDOC
		ID: storey-floor-area
		NAME: Floor area
		DESCRIPTION: Calculates the area of a single storey
		TYPE: formula
		UNIT: m2
		SAP: (1a,1b...)
		BREDEM: Input variable
		DEPS: 
		GET: 
		SET: 
		STOCK: stories.csv (polygon shape)
		CODSIEB
		*/		
        double accumulator = 0;

        final int count = segments.size();

        int j = count - 1;
        for (int i = 0; i < count; i++) {
            final SegmentData previousPoint = segments.get(j);
            final SegmentData thisPoint = segments.get(i);

            accumulator += (previousPoint.getX() + thisPoint.getX()) *
                    (previousPoint.getY() - thisPoint.getY());

            j = i;
        }

        return Math.abs(accumulator / 2d);
	}
	
	/* (non-Javadoc)
	 * @see uk.org.cse.nhm.hom.structure.IStorey#getPerimeter()
	 */
	@Override
	public double getPerimeter() {
		double accumulator = 0;
		for (final IWall wall : getImmutableWalls()) {
			accumulator += wall.getLength();
		}
		return accumulator;
	}
	
	/* (non-Javadoc)
	 * @see uk.org.cse.nhm.hom.structure.IStorey#getLength(uk.org.cse.nhm.hom.components.fabric.types.ElevationType)
	 */
	@Override
	public double getLength(final ElevationType elevationType) {
		double accumulator = 0;
		for (final IWall wall : getImmutableWalls()) {
			if (wall.getElevationType() == elevationType) accumulator += wall.getLength();
		}
		return accumulator;
	}

	/**
	 * Iterates through all the walls in this storey, showing them to the visitor as heat loss areas.
	 * @param visitor
	 * @param elevations
	 */
	private void showVisitorHeatLossWalls(
			final IEnergyCalculatorVisitor visitor,
			final Map<ElevationType, IElevation> elevations,
			final Map<ElevationType, IDoorVisitor> doors) {
		for (final IWall segment : getImmutableWalls()) {
			final double basicArea = segment.getArea();
			final double wallArea;
			
			if (segment.isPartyWall()) {
				/*
				BEISDOC
				NAME: Party wall element
				DESCRIPTION: The area, u-value, and thermal mass for a section of party wall.
				TYPE: formula
				UNIT: area m^2, u-value W/m^2/℃, k-value kJ/m^2/℃
				SAP: (32)
				BREDEM: 3B
				GET: house.u-value
				SET: action.reset-walls
				STOCK: elevations.csv (tenthspartywall), imputation schema (walls)
				NOTES: In SAP 2012 mode, the u-value of the wall will be overridden by one of tables S6, S7 or S8.
				ID: party-wall-element
				CODSIEB
				*/
				// party walls cannot contain windows or doors, and they have no infiltration effects
				// so we just skip over all that stuff here
				wallArea = basicArea;
				visitor.visitWall(
						segment.getWallConstructionType(),
						segment.getWallInsulationThickness(WallInsulationType.InternalOrExternal),
						segment.hasWallInsulation(WallInsulationType.FilledCavity),
						wallArea,
						segment.getUValue(),
						Optional.<ThermalMassLevel>absent()
					);
			} else {				
				// non-attached walls can hold windows and doors, so we need to talk to the elevation about that.
				final IElevation e = elevations.get(segment.getElevationType());
				final double doorArea;
				// if we are visiting doors (i.e. we are on the ground floor), we need to net them off the area.
				if (doors != null) {
					doorArea = doors.get(segment.getElevationType()).offerPotentialDoorArea(basicArea);
				} else {
					doorArea = 0;
				}
				
				final double glazedArea = e.visitGlazing(visitor, basicArea, doorArea);
				
				/*
				BEISDOC
				NAME: External Wall Area
				DESCRIPTION: The area of an external wall excluding glazing and doors.
				TYPE: formula
				UNIT: m^2
				SAP: (11)
				BREDEM: Table 19
				DEPS: elevation-glazed-proportion,opening-proportion,storey-height
				STOCK: storeys.csv (polygons)
				ID: external-wall-area
				CODSIEB
				*/
				wallArea = basicArea - (glazedArea + doorArea);
				
				/*
				 *  Confirmed that area of openings (windows and doors) should be netted off here.
				 *  See SAP{ 2012 step (11).
				 */
				visitor.addWallInfiltration(basicArea, segment.getWallConstructionType(), segment.getAirChangeRate());

				/*
				BEISDOC
				NAME: External wall element
				DESCRIPTION: The area, u-value and k-value for a section of external wall.
				TYPE: formula
				UNIT: area m^2, u-value W/m^2/℃, k-value kJ/m^2/℃
				SAP: (29a)
				BREDEM: 3B
				DEPS:
				GET: house.u-value
				SET: action.reset-walls
				STOCK: storeys.csv (polygon shape), imputation schemas (walls)
				ID: external-wall-element
				CODSIEB
				*/
				visitor.visitWall(
						segment.getWallConstructionType(),
						segment.getWallInsulationThickness(WallInsulationType.InternalOrExternal),
						segment.hasWallInsulation(WallInsulationType.FilledCavity),
						wallArea,
						segment.getUValue(),
						segment.getThermalMassLevel()
					);
			}
		}
	}
	
	/**
	 * This is used by the {@link SegmentWrapper} class to implment {@link IMutableWall#split(double)}; it inserts
	 * a new point into the polygon after the given existing point.
	 * @param after
	 * @param inserted
	 */
	protected void insertSegment(final SegmentData after, final SegmentData inserted) {
		final int afterIndex = segments.indexOf(after);
        // if (afterIndex == 0) {
        // segments.add(segments.size(), inserted);
        // } else {
		if (afterIndex == 0) {
			segments.add(inserted);
		} else {
			segments.add(afterIndex, inserted);
		}
        
		// }
	}
	
	@Property(policy=PojomaticPolicy.NONE)
	private transient List<IWall> immutableWalls = new ArrayList<IWall>();
	
	@Property(policy=PojomaticPolicy.NONE)
	public  Iterable<IWall> getImmutableWalls() {
		synchronized (this) {
			if (immutableWalls.isEmpty()) {
				for (final IMutableWall w : getWalls()) {
					immutableWalls.add(w);
				}
			}
		}
		return immutableWalls;
	}
	
	/* (non-Javadoc)
	 * @see uk.org.cse.nhm.hom.structure.IStorey#getWalls()
	 */
	@Override
	@Property(policy=PojomaticPolicy.NONE)
	public Iterable<IMutableWall> getWalls() {
		synchronized (this) {
			immutableWalls.clear();
		}
		return new Iterable<IMutableWall>() {
			@Override
			public Iterator<IMutableWall> iterator() {
				return new Iterator<IMutableWall>() {
					int index = 1;
					private SegmentData previous = segments.get(0);

					@Override
					public boolean hasNext() {
						return (index) <= segments.size();
					}

					@Override
					public IMutableWall next() {
						final SegmentData current = segments.get(index++ % segments.size());
						
						final SegmentWrapper result = new SegmentWrapper(previous.getX(), previous.getY(), height, current, Storey.this);
						
						previous = current;
						
						return result;
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException("Cannot remove walls this way");
					}
				};
			}
		};
	}

	public double getExposedPerimeter() {
		if (exposedPerimeterCache < 0) {
			double exposedPerimeter = 0;
			for (final IWall wall : getImmutableWalls()) {
				if (wall.getWallConstructionType().getWallType() == WallType.External) exposedPerimeter += wall.getLength();
			}
			exposedPerimeterCache = exposedPerimeter;
		}
		return exposedPerimeterCache;
	}

	public Storey copy() {
		final Storey other = new Storey();
		
		// copy simple properties
		other.setCeilingUValue(getCeilingUValue());
		other.setFloorLocationType(getFloorLocationType());
		other.setFloorUValue(getFloorUValue());
		other.setHeight(getHeight());
		
		// poke segment data in directly
		other.copySegments(segments);
		
		return other;
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
		temp = Double.doubleToLongBits(ceilingUValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((floorLocationType == null) ? 0 : floorLocationType.hashCode());
		temp = Double.doubleToLongBits(floorUValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(height);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((segments == null) ? 0 : segments.hashCode());
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
		Storey other = (Storey) obj;
		if (Double.doubleToLongBits(ceilingUValue) != Double.doubleToLongBits(other.ceilingUValue))
			return false;
		if (floorLocationType != other.floorLocationType)
			return false;
		if (Double.doubleToLongBits(floorUValue) != Double.doubleToLongBits(other.floorUValue))
			return false;
		if (Double.doubleToLongBits(height) != Double.doubleToLongBits(other.height))
			return false;
		if (segments == null) {
			if (other.segments != null)
				return false;
		} else if (!segments.equals(other.segments))
			return false;
		return true;
	}
}
