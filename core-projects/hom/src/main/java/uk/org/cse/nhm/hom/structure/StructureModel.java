package uk.org.cse.nhm.hom.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.types.AreaType;
import uk.org.cse.nhm.hom.ICopyable;
import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;
import uk.org.cse.nhm.hom.components.fabric.types.FloorConstructionType;
import uk.org.cse.nhm.hom.components.fabric.types.FloorLocationType;
import uk.org.cse.nhm.hom.components.fabric.types.RoofConstructionType;
import uk.org.cse.nhm.hom.structure.impl.Elevation;
import uk.org.cse.nhm.hom.structure.impl.Elevation.IDoorVisitor;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.hom.types.BuiltFormType;

/**
 * Describes the structural form of a house case. It is composed of two main parts
 * 
 * <ol>
 * 	<li> {@link Storey} instances - each of these describes one of the storeys of the house, in terms of its perimeter, height, u-values etc.</li>
 * 	<li> {@link Elevation} instances - each of these describes the apertures in one of the four elevations of the house, in terms of the percentage of exterior walls that is glazed,
 * or contains doors</li>
 * </ol>
 * 
 * The {@link #addStorey(Storey)} and {@link #setElevation(ElevationType, Elevation)} methods should be used to 
 * set up the storeys and elevations in this structure.
 * <br />
 * 
 * This class is intended to be easily serializable to MongoDB, so it oughtn't have any cross-references between fields.
 * <br />
 * 
 * At the moment it has no other information in it, but at some point it should probably hold some details like
 * draught lobby presence & other broad structural properties.
 * 
 * @author hinton
 *
 */
@AutoProperty
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class StructureModel implements ICopyable<StructureModel> {
	//private static final Logger log = LoggerFactory.getLogger(StructureModel.class);
	private final Map<ElevationType, Elevation> elevations = new HashMap<ElevationType, Elevation>();
	private final List<Storey> storeys = new ArrayList<Storey>();
	private double livingAreaProportionOfFloorArea;
	private double interzoneSpecificHeatLoss;
	private boolean hasDraughtLobby;
	private double zoneTwoHeatedProportion;
	private double draughtStrippedProportion;
	private FloorConstructionType groundFloorConstructionType;
	private RoofConstructionType roofConstructionType;
	private double floorInsulationThickness;
	private double roofInsulationThickness;
    private BuiltFormType builtFormType;
    private double internalWallArea;
    private double internalWallKValue;
    
    private double frontPlotDepth, frontPlotWidth;
    private double backPlotDepth, backPlotWidth;
	private int numberOfShelteredSides;
	
    private boolean onGasGrid;

    private int numberOfBedrooms;
    private boolean hasAccessToOutsideSpace;
    private boolean ownsPartOfRoof;
	private boolean hasLoft;
    
    private int mainFloorLevel;
    
	public StructureModel() {
        super();
    }
	
    public StructureModel(final BuiltFormType builtFormType) {
		this.builtFormType = builtFormType;
	}

    
    
    public int getMainFloorLevel() {
        return mainFloorLevel;
    }
    
    public void setMainFloorLevel(int mainFloorLevel) {
        this.mainFloorLevel = mainFloorLevel;
    }

	
    /**
     * This is like the clone method, but I dislike the cloning interfaces for the usual reasons
     * 
     * @return a deep copy of this structure model
     */
    @Override
	public StructureModel copy() {
    	final StructureModel copy = new StructureModel(getBuiltFormType());
    	
    	copy.setLivingAreaProportionOfFloorArea(getLivingAreaProportionOfFloorArea());
    	copy.setInterzoneSpecificHeatLoss(getInterzoneSpecificHeatLoss());
    	copy.setHasDraughtLobby(hasDraughtLobby());
    	copy.setZoneTwoHeatedProportion(getZoneTwoHeatedProportion());
    	copy.setDraughtStrippedProportion(getDraughtStrippedProportion());
    	copy.setGroundFloorConstructionType(getGroundFloorConstructionType());
    	copy.setRoofConstructionType(getRoofConstructionType());
    	copy.setFloorInsulationThickness(getFloorInsulationThickness());
    	copy.setRoofInsulationThickness(getRoofInsulationThickness());
    	copy.setFrontPlotDepth(getFrontPlotDepth());
    	copy.setFrontPlotWidth(getFrontPlotWidth());
    	copy.setBackPlotDepth(getBackPlotDepth());
    	copy.setBackPlotWidth(getBackPlotWidth());
    	
    	copy.setNumberOfShelteredSides(getNumberOfShelteredSides());
    	copy.setOnGasGrid(isOnGasGrid());
    	copy.setNumberOfBedrooms(getNumberOfBedrooms());
    	copy.setHasAccessToOutsideSpace(hasAccessToOutsideSpace());
    	copy.setOwnsPartOfRoof(ownsPartOfRoof());
    	
    	copy.setInternalWallArea(getInternalWallArea());
    	copy.setInternalWallKValue(getInternalWallKValue());
    	copy.setHasLoft(getHasLoft());
    	
    	for (final ElevationType et : ElevationType.values()) {
    		copy.setElevation(et, getElevations().get(et).copy());
    	}
    	
    	for (final Storey storey : getStoreys()) {
    		copy.addStorey(storey.copy());
    	}

        copy.setMainFloorLevel(getMainFloorLevel());
        
    	return copy;
    }

	public void addStorey(final Storey storey) {
		storeys.add(storey);
	}
	
	public BuiltFormType getBuiltFormType() {
		return builtFormType;
	}

	public void setElevation(final ElevationType type, final Elevation elevation) {
		elevations.put(type, elevation);
	}
	
	/**
	 * This does a composition, going through each storey and asking it to accept the visitor and present its
	 * heat loss surfaces and windows and so on.
	 * <br />
	 * 
	 * 
	 * @assumption This method presumes that the maximum contact area is shared between each storey, so heat loss area above and below
	 * is minimal.
	 * <br />
	 * This assumption makes it much easier to work out heat loss through the roof and floor of a storey, as the alternative is to
	 * compute the non-intersecting area between each polygon. Also, houses typically do have this property (they usually don't look like this in plan:
	 * 
	 * <pre>
	 *[-------]
	 *    [-------]
	 *[-------]
	 *    [-------]
	 * </pre>
	 * 
	 * If you did have a house which looked like this, the heat loss from the roof of the ground storey,
	 * the roof and floor of the first storey, the roof and floor of the second storey and the floor of 
	 * the top storey would all be underestimated by this approach (they would be zero, because the assumption 
	 * makes a house which looks like this:
	 * 
	 * <pre>
	 *[-------]
	 *[-------]
	 *[-------]
	 *[-------]
	 * </pre>)
	 * 
	 * Since a normal house might look like this:
	 * 
	 * <pre>
	 *  [---]
	 * [------]
	 * [--------]
	 * </pre>
	 * 
	 * or maybe this, on Grand Designs
	 * 
	 * <pre>
	 * [--------]
	 * [----]
	 * [----]
	 * </pre>
	 * 
	 * this assumption will mostly be OK.
	 * 
	 * @param visitor the visitor to show heat loss surfaces to.
	 */
	public void accept(final IEnergyCalculatorVisitor visitor) {
		final int storeyCount = storeys.size();
		
		visitor.visitFabricElement(AreaType.InternalWall, internalWallArea, 0, internalWallKValue);
		
		final Map<ElevationType, IElevation> elmap = new EnumMap<ElevationType, IElevation>(elevations);
		final Map<ElevationType, IDoorVisitor> doors = new EnumMap<ElevationType, Elevation.IDoorVisitor>(ElevationType.class);
		
		for (final ElevationType et : ElevationType.values()) {
			doors.put(et, elmap.get(et).getDoorVisitor());
		}
		
		if (storeyCount > 0) {
			/**
			 * The area of this storey
			 */
			double areaHere = storeys.get(0).getArea();
			
			/**
			 * This holds the area of the floor below this one
			 */
			double areaBelow = 0;
			
			/**
			 * The area of the storey above this one, if there is such a storey.
			 */
			double areaAbove = 0;
			
			for (int i = 0; i<storeyCount; i++) {
				final Storey here = storeys.get(i);
				
				if (builtFormType.isFlat()) {
					//TODO some flats may have exposed roofs or floors
					areaAbove = areaBelow = here.getArea();
				} else {
					if (i + 1 == storeyCount) {
						areaAbove = 0; // this is the top floor, we are done
					} else {
						areaAbove = storeys.get(i+1).getArea(); // get area of next storey
					}
				}
				
				here.accept(visitor, elmap, doors, areaBelow, areaAbove);
				
				// shift areas around
				if (!builtFormType.isFlat()) {
					areaBelow = areaHere ;
					areaHere = areaAbove;
				}
			}
		}
	}
	
	public double getEnvelopeArea() {
		final double roof = getExternalRoofArea();
		final double floor = getExternalFloorArea();
		double wall = 0;
		for (final Storey s : storeys) {
			wall += s.getExposedPerimeter() * s.getHeight();
		}
		return floor + roof + wall;
	}
	
	public double getExternalFloorArea() {
		if (builtFormType.isFlat()) {
			//TODO some flats may have exposed roofs or floors
			return 0d;
		} else {
			double d = 0;
			for (final Storey s : getStoreys()) {
				if (s.getFloorLocationType() == FloorLocationType.BASEMENT ||
						s.getFloorLocationType() == FloorLocationType.GROUND) {
					d = Math.max(s.getArea(), d);
				}
			}
			return d;
		}
	}
	
	/**
	 * @since 1.3.4
	 * @return
	 */
	public double getExternalRoofArea() {
		if (builtFormType.isFlat()) {
			//TODO some flats may have exposed roofs or floors
			return 0d;
		} else {
			// the external roof area by the method above is the maximum floor area.
			double result = 0d;
			for (final Storey s : getStoreys()) {
				result = Math.max(result, s.getArea());
			}
			return result;
		}
	}
	
	public double getVolume() {
		double acc = 0;
		for (final Storey s : storeys) {
			acc += s.getVolume();
		}
		return acc;
	}
	
	public double getFloorArea() {
		double acc = 0;
		//TODO distinguish between internal and external floor area.
		for (final Storey s : storeys) {
			acc += s.getArea();
		}
		return acc;
	}

	public int getNumberOfStoreys() {
		return storeys.size();
				
	}

	public double getLivingAreaProportionOfFloorArea() {
		return livingAreaProportionOfFloorArea;
	}

	public double getInterzoneSpecificHeatLoss() {
		return interzoneSpecificHeatLoss;
	}

	public boolean hasDraughtLobby() {
		return hasDraughtLobby;
	}

	public double getZoneTwoHeatedProportion() {
		return zoneTwoHeatedProportion;
	}

	public double getDraughtStrippedProportion() {
		return draughtStrippedProportion;
	}

	public boolean isHasDraughtLobby() {
		return hasDraughtLobby;
	}

	public void setHasDraughtLobby(final boolean hasDraughtLobby) {
		this.hasDraughtLobby = hasDraughtLobby;
	}
	
	public void setLivingAreaProportionOfFloorArea(
			final double livingAreaProportionOfFloorArea) {
		this.livingAreaProportionOfFloorArea = livingAreaProportionOfFloorArea;
	}

	public void setInterzoneSpecificHeatLoss(final double interzoneSpecificHeatLoss) {
		this.interzoneSpecificHeatLoss = interzoneSpecificHeatLoss;
	}

	public void setZoneTwoHeatedProportion(final double zoneTwoHeatedProportion) {
		this.zoneTwoHeatedProportion = zoneTwoHeatedProportion;
	}

	public void setDraughtStrippedProportion(final double draughtStrippedProportion) {
		this.draughtStrippedProportion = draughtStrippedProportion;
	}

	public List<Storey> getStoreys() {
		return Collections.unmodifiableList(storeys);
	}

	public Map<ElevationType, Elevation> getElevations() {
		return Collections.unmodifiableMap(elevations);
	}

	public FloorConstructionType getGroundFloorConstructionType() {
		return groundFloorConstructionType;
	}

	public RoofConstructionType getRoofConstructionType() {
		return roofConstructionType;
	}

	public double getRoofInsulationThickness() {
		return roofInsulationThickness;
	}

	public double getFloorInsulationThickness() {
		return floorInsulationThickness;
	}

	public void setGroundFloorConstructionType(
			final FloorConstructionType groundFloorConstructionType) {
		this.groundFloorConstructionType = groundFloorConstructionType;
	}

	public void setRoofConstructionType(final RoofConstructionType roofConstructionType) {
		this.roofConstructionType = roofConstructionType;
	}

	public void setFloorInsulationThickness(final double floorInsulationThickness) {
		this.floorInsulationThickness = floorInsulationThickness;
	}

	public void setRoofInsulationThickness(final double roofInsulationThickness) {
		this.roofInsulationThickness = roofInsulationThickness;
		if (roofInsulationThickness > 0) setHasLoft(true);
	}

	public double getFrontPlotDepth() {
		return frontPlotDepth;
	}

	public void setFrontPlotDepth(final double frontPlotDepth) {
		this.frontPlotDepth = frontPlotDepth;
	}

	public double getFrontPlotWidth() {
		return frontPlotWidth;
	}

	public void setFrontPlotWidth(final double frontPlotWidth) {
		this.frontPlotWidth = frontPlotWidth;
	}

	public double getBackPlotDepth() {
		return backPlotDepth;
	}

	public void setBackPlotDepth(final double backPlotDepth) {
		this.backPlotDepth = backPlotDepth;
	}

	public double getBackPlotWidth() {
		return backPlotWidth;
	}

	public void setBackPlotWidth(final double backPlotWidth) {
		this.backPlotWidth = backPlotWidth;
	}

	public int getNumberOfShelteredSides() {
		return numberOfShelteredSides;
	}

	public void setNumberOfShelteredSides(final int numberOfShelteredSides) {
		this.numberOfShelteredSides = numberOfShelteredSides;
	}

	public double getFrontPlotArea() {
		return frontPlotDepth * frontPlotWidth;
	}

	public double getBackPlotArea() {
		return backPlotDepth * backPlotWidth;
	}

	public boolean isOnGasGrid() {
		return onGasGrid;
	}

	public void setOnGasGrid(final boolean onGasGrid) {
		this.onGasGrid = onGasGrid;
	}
	
	public double getInternalWallArea() {
		return internalWallArea;
	}

	public void setInternalWallArea(final double internalWallArea) {
		this.internalWallArea = internalWallArea;
	}

	public double getInternalWallKValue() {
		return internalWallKValue;
	}

	public void setInternalWallKValue(final double internalWallKValue) {
		this.internalWallKValue = internalWallKValue;
	}
	
	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

    /**
     * Return the numberOfBedrooms.
     * 
     * @return the numberOfBedrooms
     * @since 1.1.0
     */
    public int getNumberOfBedrooms() {
        return numberOfBedrooms;
    }

    /**
     * Set the numberOfBedrooms.
     * 
     * @param numberOfBedrooms the numberOfBedrooms
     * @since 1.1.0
     */
    public void setNumberOfBedrooms(final int numberOfBedrooms) {
        this.numberOfBedrooms = numberOfBedrooms;
    }
    
    /**
     * @since 1.1.0
     */
    public boolean hasAccessToOutsideSpace(){
    	return this.hasAccessToOutsideSpace;
    }
    
    /**
     * @since 1.1.0
     */
    public void setHasAccessToOutsideSpace(final boolean hasAccessToOutsideSpace) {
		this.hasAccessToOutsideSpace = hasAccessToOutsideSpace;
	}
    
    /**
     * @since 1.1.0
     */
    public boolean ownsPartOfRoof(){
    	return this.ownsPartOfRoof;
    }
    
    /**
     * @since 1.1.0
     */
    public void setOwnsPartOfRoof(final boolean ownsPartOfRoof) {
		this.ownsPartOfRoof = ownsPartOfRoof;
	}
    
    public boolean getHasLoft() {
    	return this.hasLoft;
    }

	public void setHasLoft(final boolean hasLoft) {
		if (!hasLoft && this.getRoofInsulationThickness() > 0) {
			//log.warn("Something tried to set a house to not have a loft, whilst it still had roof insulation; the request has been ignored");
		} else {
			this.hasLoft = hasLoft;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(backPlotDepth);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(backPlotWidth);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((builtFormType == null) ? 0 : builtFormType.hashCode());
		temp = Double.doubleToLongBits(draughtStrippedProportion);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((elevations == null) ? 0 : elevations.hashCode());
		temp = Double.doubleToLongBits(floorInsulationThickness);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(frontPlotDepth);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(frontPlotWidth);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime
				* result
				+ ((groundFloorConstructionType == null) ? 0
						: groundFloorConstructionType.hashCode());
		result = prime * result + (hasAccessToOutsideSpace ? 1231 : 1237);
		result = prime * result + (hasDraughtLobby ? 1231 : 1237);
		result = prime * result + (hasLoft ? 1231 : 1237);
		temp = Double.doubleToLongBits(internalWallArea);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(internalWallKValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(interzoneSpecificHeatLoss);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(livingAreaProportionOfFloorArea);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + numberOfBedrooms;
		result = prime * result + numberOfShelteredSides;
		result = prime * result + (onGasGrid ? 1231 : 1237);
		result = prime * result + (ownsPartOfRoof ? 1231 : 1237);
		result = prime
				* result
				+ ((roofConstructionType == null) ? 0 : roofConstructionType
						.hashCode());
		temp = Double.doubleToLongBits(roofInsulationThickness);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((storeys == null) ? 0 : storeys.hashCode());
		temp = Double.doubleToLongBits(zoneTwoHeatedProportion);
		result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + mainFloorLevel;
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
		final StructureModel other = (StructureModel) obj;
		if (Double.doubleToLongBits(backPlotDepth) != Double
				.doubleToLongBits(other.backPlotDepth))
			return false;
		if (Double.doubleToLongBits(backPlotWidth) != Double
				.doubleToLongBits(other.backPlotWidth))
			return false;
		if (builtFormType != other.builtFormType)
			return false;
		if (Double.doubleToLongBits(draughtStrippedProportion) != Double
				.doubleToLongBits(other.draughtStrippedProportion))
			return false;
		if (elevations == null) {
			if (other.elevations != null)
				return false;
		} else if (!elevations.equals(other.elevations))
			return false;
		if (Double.doubleToLongBits(floorInsulationThickness) != Double
				.doubleToLongBits(other.floorInsulationThickness))
			return false;
		if (Double.doubleToLongBits(frontPlotDepth) != Double
				.doubleToLongBits(other.frontPlotDepth))
			return false;
		if (Double.doubleToLongBits(frontPlotWidth) != Double
				.doubleToLongBits(other.frontPlotWidth))
			return false;
		if (groundFloorConstructionType != other.groundFloorConstructionType)
			return false;
		if (hasAccessToOutsideSpace != other.hasAccessToOutsideSpace)
			return false;
		if (hasDraughtLobby != other.hasDraughtLobby)
			return false;
		if (hasLoft != other.hasLoft)
			return false;
		if (Double.doubleToLongBits(internalWallArea) != Double
				.doubleToLongBits(other.internalWallArea))
			return false;
		if (Double.doubleToLongBits(internalWallKValue) != Double
				.doubleToLongBits(other.internalWallKValue))
			return false;
		if (Double.doubleToLongBits(interzoneSpecificHeatLoss) != Double
				.doubleToLongBits(other.interzoneSpecificHeatLoss))
			return false;
		if (Double.doubleToLongBits(livingAreaProportionOfFloorArea) != Double
				.doubleToLongBits(other.livingAreaProportionOfFloorArea))
			return false;
		if (numberOfBedrooms != other.numberOfBedrooms)
			return false;
		if (numberOfShelteredSides != other.numberOfShelteredSides)
			return false;
		if (onGasGrid != other.onGasGrid)
			return false;
		if (ownsPartOfRoof != other.ownsPartOfRoof)
			return false;
		if (roofConstructionType != other.roofConstructionType)
			return false;
		if (Double.doubleToLongBits(roofInsulationThickness) != Double
				.doubleToLongBits(other.roofInsulationThickness))
			return false;
		if (storeys == null) {
			if (other.storeys != null)
				return false;
		} else if (!storeys.equals(other.storeys))
			return false;
		if (Double.doubleToLongBits(zoneTwoHeatedProportion) != Double
				.doubleToLongBits(other.zoneTwoHeatedProportion))
			return false;

        if (mainFloorLevel != other.mainFloorLevel) return false;
        
		return true;
	}
}
