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
import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.StepRecorder;
import uk.org.cse.nhm.energycalculator.api.ThermalMassLevel;
import uk.org.cse.nhm.energycalculator.api.types.steps.EnergyCalculationStep;
import uk.org.cse.nhm.energycalculator.api.types.FloorConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.RoofConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.hom.ICopyable;
import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;
import uk.org.cse.nhm.hom.components.fabric.types.FloorLocationType;
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

	/*
	BEISDOC
	NAME: Living Area Proportion
	DESCRIPTION: The size of the living area of the house, divided by the total floor area of the house.
	TYPE: value
	UNIT: dimensionless
	SAP: (91)
	BREDEM: Input A1
	STOCK: basic.csv (livingareafaction)
	ID: living-area-proportion
	CODSIEB
	*/
	private double livingAreaProportionOfFloorArea;

	/*
	BEISDOC
	NAME: Interzone specific heat loss
	DESCRIPTION: description
	TYPE: value
	UNIT: W/℃
	BREDEM: 3J
	NOTES: Interzone specific heat loss is always treated as 0 in SAP 2012 mode.
	ID: interzone-specific-heat-loss
	CODSIEB
	*/
	private double interzoneSpecificHeatLoss;

	private boolean hasDraughtLobby;
	private double zoneTwoHeatedProportion = 1.0;

	/*
	BEISDOC
	NAME: Draught stripped proportion
	DESCRIPTION: The proportion of windows and doors in the dwelling which are draught stripped
	TYPE: value
	UNIT: dimensionless
	SAP: (14)
	BREDEM: 3D, Table 19
	SET: measure.install-draught-proofing
	STOCK: ventilation.csv (windowsanddoorsdraughtstrippedproportion)
	NOTES: We do not have the information required to implement the BREDEM 2012 algorithm, so we use the SAP 2012 algorithm in both energy calculator modes.
	ID: draught-stripped-proportion
	CODSIEB
	*/
	private double draughtStrippedProportion;

	private FloorConstructionType groundFloorConstructionType = FloorConstructionType.SuspendedTimberUnsealed;
	private RoofConstructionType roofConstructionType = RoofConstructionType.PitchedSlateOrTiles;
	private double floorInsulationThickness;
	private double roofInsulationThickness;
    private BuiltFormType builtFormType;
    private double internalWallArea;

    private double frontPlotDepth, frontPlotWidth;
    private double backPlotDepth, backPlotWidth;

    private int intermittentFans;
	private int passiveVents;

    /*
	BEISDOC
	NAME: Sheltered sides
	DESCRIPTION: The number of sides of the dwelling which are sheltered
	TYPE: value
	UNIT: Count of elevations
	SAP: (19)
	BREDEM: Table 22
	DEPS:
	GET:
	SET:
	STOCK: elevations.csv (if tenthsattached > 5, elevation is considered sheltered)
	ID: num-sheltered-sides
	CODSIEB
	*/
	private int numberOfShelteredSides;

    private boolean onGasGrid;

    private int numberOfBedrooms;
    private boolean hasAccessToOutsideSpace;
    private boolean ownsPartOfRoof;
	private boolean hasLoft;

    private int mainFloorLevel;

    /*
    BEISDOC
    NAME: Reduced Internal Gains
    DESCRIPTION: Whether or not the dwelling should be subject to reduced internal gains.
    Type: value
    Unit: true/false
    SAP: Table 5
    SET: action.reduced-internal-gains
    NOTES: Never applies in SAP 2012 mode.
    ID: reduced-internal-gains
    CODSIEB
    */

	private boolean reducedInternalGains;

    /*
	BEISDOC
	NAME: Thermal Bridging Coefficient
	DESCRIPTION: This is multiplied by the external area of the dwelling to produce the thermal bridging loss per degree of temperature difference.
	TYPE: value
	UNIT: W/℃/m^2
	BREDEM: 3A.b, see footnote vii
	SET: action.set-thermal-bridging-factor
	ID: thermal-bridging-coefficient
	CODSIEB
	*/
	private double thermalBridgingCoefficient = 0.15;

	public StructureModel() {
        super();
    }

    public StructureModel(final BuiltFormType builtFormType) {
		this.builtFormType = builtFormType;
	}



    public int getMainFloorLevel() {
        return mainFloorLevel;
    }

    public void setMainFloorLevel(final int mainFloorLevel) {
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
    	copy.setThermalBridigingCoefficient(getThermalBridgingCoefficient());
    	copy.setNumberOfShelteredSides(getNumberOfShelteredSides());
    	copy.setOnGasGrid(isOnGasGrid());
    	copy.setNumberOfBedrooms(getNumberOfBedrooms());
    	copy.setHasAccessToOutsideSpace(hasAccessToOutsideSpace());
    	copy.setOwnsPartOfRoof(ownsPartOfRoof());

    	copy.setInternalWallArea(getInternalWallArea());
    	copy.setHasLoft(getHasLoft());
    	copy.setReducedInternalGains(hasReducedInternalGains());
    	copy.setIntermittentFans(getIntermittentFans());
    	copy.setPassiveVents(getPassiveVents());

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
	 * This does\ a composition, going through each storey and asking it to accept the visitor and present its
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
		visitor.addFanInfiltration(getIntermittentFans());
		visitor.addVentInfiltration(getPassiveVents());
		visitor.addGroundFloorInfiltration(getGroundFloorConstructionType());
		visitor.setFloorType(groundFloorConstructionType, floorInsulationThickness);
		visitor.setRoofType(roofConstructionType, roofInsulationThickness);

		final int storeyCount = storeys.size();

		visitor.visitWall(
				WallConstructionType.Internal_Any,
				0,
				false,
				internalWallArea,
				0,
				0,
				Optional.<ThermalMassLevel>absent()
			);

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

			double areaThirdFloorAndAbove = 0;

			for (int i = 0; i<storeyCount; i++) {
				final Storey here = storeys.get(i);

				if (here.getFloorLocationType().getLevel() >= FloorLocationType.HIGHER_FLOOR.getLevel()) {
				    areaThirdFloorAndAbove += here.getArea();
                } else {
				    StepRecorder.recordStep(getFloorAreaStep(here.getFloorLocationType()), here.getArea());
                }

				if (builtFormType.isFlat()) {
					// Flats are different. Only the top and bottom floors count,
					// and only if we believe there are no other flats above or below them.
					if (i == 0 && here.getFloorLocationType().isInContactWithGround()) {
						areaBelow = 0;
					} else {
						areaBelow = here.getArea();
					}

					if (i == storeyCount - 1 && here.getFloorLocationType() == FloorLocationType.TOP_FLOOR) {
						areaAbove = 0;
					} else {
						areaAbove = here.getArea();
					}
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

            StepRecorder.recordStep(EnergyCalculationStep.FloorArea_Third_and_Above, areaThirdFloorAndAbove);
		}

		for (final IDoorVisitor v : doors.values()) {
			/*
			 * This has to happen after the storeys, because they tell the door visitors how much wall area there is to play with.
			 */
			v.visitDoors(visitor);
		}
	}

    private EnergyCalculationStep getFloorAreaStep(FloorLocationType floorLocationType) {
        switch(floorLocationType) {
            case BASEMENT:
                return EnergyCalculationStep.FloorArea_Basement;
            case GROUND:
                return EnergyCalculationStep.FloorArea_Ground;
            case FIRST_FLOOR:
                return EnergyCalculationStep.FloorArea_First;
            case SECOND_FLOOR:
                return EnergyCalculationStep.FloorArea_Second;
            default:
                throw new UnsupportedOperationException("Error trying to record floor area energy calculation step: floor " +
                        floorLocationType +
                        " is not in the normal list of floors, and should be accumulated into the third floor value.");
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
			final Storey bottom = getBottomStorey();

			// Flats only have an external floor area if they are a ground floor or basement flat.
			if (bottom.getFloorLocationType() == FloorLocationType.BASEMENT) {
				return bottom.getArea();
			} else {
				return 0d;
			}
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
			final Storey top = getTopStorey();

			// Flats only have an external roof area if they are a top floor flat.
			if (top.getFloorLocationType() == FloorLocationType.TOP_FLOOR) {
				return top.getArea();
			} else {
				return 0d;
			}
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
		/*
		BEISDOC
		ID: dwelling-volume
		NAME: Total volume of house
		DESCRIPTION: Calculates the total volume of the house
		TYPE: formula
		UNIT: m3
		SAP: (5)
		BREDEM: Input variable VT
		DEPS: storey-volume
		GET: house.volume
		SET:
		CODSIEB
		*/

		double acc = 0;
		for (final Storey s : storeys) {
			acc += s.getVolume();
		}
		return acc;
	}

	public double getFloorArea() {
		/*
		BEISDOC
		ID: dwelling-floor-area
		NAME: Total floor area
		DESCRIPTION: The floor area of the entire house
		TYPE: formula
		UNIT: m2
		SAP: (4)
		BREDEM: Input variable TFA
		DEPS: storey-floor-area
		GET: house.total-floor-area
		SET:
		CODSIEB
		*/

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

	public double getThermalBridgingCoefficient() {
		return thermalBridgingCoefficient;
	}

	public void setThermalBridigingCoefficient(final double thermalBridgingCoefficient) {
		this.thermalBridgingCoefficient = thermalBridgingCoefficient;
	}

	public boolean hasReducedInternalGains() {
		return this.reducedInternalGains;
	}

	public void setReducedInternalGains(final boolean reducedInternalGains) {
		this.reducedInternalGains = reducedInternalGains;
	}

    public int getIntermittentFans() {
		return intermittentFans;
	}

	public void setIntermittentFans(final int intermittentFans) {
		this.intermittentFans = intermittentFans;
	}

	public int getPassiveVents() {
		return passiveVents;
	}

	public void setPassiveVents(final int passiveVents) {
		this.passiveVents = passiveVents;
	}

	public boolean hasExternalRoof() {
		if (builtFormType.isFlat()) {
			return getTopStorey().getFloorLocationType() == FloorLocationType.TOP_FLOOR;
		} else {
			return true;
		}
	}

	public boolean hasExternalFloor() {
		if (builtFormType.isFlat()) {
			 return getBottomStorey().getFloorLocationType().isInContactWithGround();
		} else {
			return true;
		}
	}

	public Storey getTopStorey() {
		if (storeys.size() == 0) {
			throw new IllegalStateException("This structure model has no storeys.");
		}
		return storeys.get(storeys.size() - 1);
	}

	public Storey getBottomStorey() {
		if (storeys.size() == 0) {
			throw new IllegalStateException("This structure model has no storeys.");
		}
		return storeys.get(0);
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
		result = prime * result + ((builtFormType == null) ? 0 : builtFormType.hashCode());
		temp = Double.doubleToLongBits(draughtStrippedProportion);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((elevations == null) ? 0 : elevations.hashCode());
		temp = Double.doubleToLongBits(floorInsulationThickness);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(frontPlotDepth);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(frontPlotWidth);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((groundFloorConstructionType == null) ? 0 : groundFloorConstructionType.hashCode());
		result = prime * result + (hasAccessToOutsideSpace ? 1231 : 1237);
		result = prime * result + (hasDraughtLobby ? 1231 : 1237);
		result = prime * result + (hasLoft ? 1231 : 1237);
		result = prime * result + intermittentFans;
		temp = Double.doubleToLongBits(internalWallArea);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(interzoneSpecificHeatLoss);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(livingAreaProportionOfFloorArea);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + mainFloorLevel;
		result = prime * result + numberOfBedrooms;
		result = prime * result + numberOfShelteredSides;
		result = prime * result + (onGasGrid ? 1231 : 1237);
		result = prime * result + (ownsPartOfRoof ? 1231 : 1237);
		result = prime * result + passiveVents;
		result = prime * result + (reducedInternalGains ? 1231 : 1237);
		result = prime * result + ((roofConstructionType == null) ? 0 : roofConstructionType.hashCode());
		temp = Double.doubleToLongBits(roofInsulationThickness);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((storeys == null) ? 0 : storeys.hashCode());
		temp = Double.doubleToLongBits(thermalBridgingCoefficient);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(zoneTwoHeatedProportion);
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
		final StructureModel other = (StructureModel) obj;
		if (Double.doubleToLongBits(backPlotDepth) != Double.doubleToLongBits(other.backPlotDepth))
			return false;
		if (Double.doubleToLongBits(backPlotWidth) != Double.doubleToLongBits(other.backPlotWidth))
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
		if (Double.doubleToLongBits(frontPlotDepth) != Double.doubleToLongBits(other.frontPlotDepth))
			return false;
		if (Double.doubleToLongBits(frontPlotWidth) != Double.doubleToLongBits(other.frontPlotWidth))
			return false;
		if (groundFloorConstructionType != other.groundFloorConstructionType)
			return false;
		if (hasAccessToOutsideSpace != other.hasAccessToOutsideSpace)
			return false;
		if (hasDraughtLobby != other.hasDraughtLobby)
			return false;
		if (hasLoft != other.hasLoft)
			return false;
		if (intermittentFans != other.intermittentFans)
			return false;
		if (Double.doubleToLongBits(internalWallArea) != Double.doubleToLongBits(other.internalWallArea))
			return false;
		if (Double.doubleToLongBits(interzoneSpecificHeatLoss) != Double
				.doubleToLongBits(other.interzoneSpecificHeatLoss))
			return false;
		if (Double.doubleToLongBits(livingAreaProportionOfFloorArea) != Double
				.doubleToLongBits(other.livingAreaProportionOfFloorArea))
			return false;
		if (mainFloorLevel != other.mainFloorLevel)
			return false;
		if (numberOfBedrooms != other.numberOfBedrooms)
			return false;
		if (numberOfShelteredSides != other.numberOfShelteredSides)
			return false;
		if (onGasGrid != other.onGasGrid)
			return false;
		if (ownsPartOfRoof != other.ownsPartOfRoof)
			return false;
		if (passiveVents != other.passiveVents)
			return false;
		if (reducedInternalGains != other.reducedInternalGains)
			return false;
		if (roofConstructionType != other.roofConstructionType)
			return false;
		if (Double.doubleToLongBits(roofInsulationThickness) != Double.doubleToLongBits(other.roofInsulationThickness))
			return false;
		if (storeys == null) {
			if (other.storeys != null)
				return false;
		} else if (!storeys.equals(other.storeys))
			return false;
		if (Double.doubleToLongBits(thermalBridgingCoefficient) != Double
				.doubleToLongBits(other.thermalBridgingCoefficient))
			return false;
		if (Double.doubleToLongBits(zoneTwoHeatedProportion) != Double.doubleToLongBits(other.zoneTwoHeatedProportion))
			return false;
		return true;
	}
}
