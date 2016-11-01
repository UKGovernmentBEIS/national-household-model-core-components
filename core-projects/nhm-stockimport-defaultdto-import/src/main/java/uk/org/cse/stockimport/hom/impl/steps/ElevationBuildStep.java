package uk.org.cse.stockimport.hom.impl.steps;

import static uk.org.cse.stockimport.util.OptionalUtil.get;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.components.fabric.types.DoorType;
import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.GlazingType;
import uk.org.cse.nhm.hom.structure.Door;
import uk.org.cse.nhm.hom.structure.Glazing;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Elevation;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.geometry.IElevationDTO;
import uk.org.cse.stockimport.hom.ISurveyCaseBuildStep;
import uk.org.cse.stockimport.repository.IHouseCaseSources;

/**
 * ElevationBuilderStep.
 * 
 * @author richardt
 * @version $Id: ElevationBuilderStep.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public class ElevationBuildStep implements ISurveyCaseBuildStep {
	/**
	 * The number of tenths attached above which a side is considered sheltered
	 */
    private static final int SHELTERED_SIDE_THRESHOLD = 5;
    
	private static final Logger logger = LoggerFactory.getLogger(ElevationBuildStep.class);
	/** @since 1.0 */
    public static final String IDENTIFIER = ElevationBuildStep.class.getCanonicalName();

    /** @since 1.0 */
    public static final double _100_PERCENT = 100.00;
    /** @since 1.0 */
    public static final double ONE = 1.00;

    /** Default door area is as per SAP 2005 RdSAP S3.4 of 1.852m&sup2; height set to 1.85 
     * @since 1.0*/
    public static final double STD_DOOR_HEIGHT = 1.85;
    /** Default door area is as per SAP 2005 RdSAP S3.4 of 1.852m&sup2; width set to 1.0 
     * @since 1.0*/
    public static final double STD_DOOR_WIDTH = ONE;

    /**
     * @return
     * @see uk.org.cse.stockimport.hom.ISurveyCaseBuildStep#getIdentifier()
     */
    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

    /**
     * @return
     * @see uk.org.cse.stockimport.hom.ISurveyCaseBuildStep#getDependencies()
     */
    @Override
    public Set<String> getDependencies() {
        return ImmutableSet.of(BasicAttributesBuildStep.IDENTIFIER, 
                StructureInitializingBuildStep.IDENTIFIER);
    }

    /**
     * @param model
     * @param dtoProvider
     * @see uk.org.cse.stockimport.hom.ISurveyCaseBuildStep#build(uk.org.cse.nhm.hom.SurveyCase,
     *      uk.org.cse.stockimport.repository.IHouseCaseSources)
     */
    @Override
    public void build(final SurveyCase model, final IHouseCaseSources<IBasicDTO> dtoProvider) {
        final StructureModel structure = model.getStructure();

        final List<IElevationDTO> elevations = dtoProvider.getAll(IElevationDTO.class);

        Elevation elevation;
        
        int numberOfShelteredSides = 0;
        
        for (final IElevationDTO elevationDTO : elevations) {
            elevation = new Elevation();
            
            elevation.setAngleFromNorth(Math.PI/4); 
            // this is east/west, which is the angle we set for all walls in the absence of more information
            
            structure.setElevation(elevationDTO.getElevationType(), elevation);
            elevation.setOpeningProportion(elevationDTO.getTenthsOpening() / 10);

            buildDoors(elevationDTO, elevation);
            buildGlazing(elevationDTO, elevation);
            
            if (isSheltered(elevationDTO)) {
            	numberOfShelteredSides++;
            }
        }
        
        structure.setNumberOfShelteredSides(numberOfShelteredSides);
    }

    /**
     * @param elevationDTO
     * @return true if the side should be considered sheltered, per CAR p.11
     */
    private boolean isSheltered(final IElevationDTO elevationDTO) {
		return elevationDTO.getTenthsAttached() > SHELTERED_SIDE_THRESHOLD;
	}

	/**
     * <p> 1. Iterate through all {@link FrameType}, then through each {@link DoorType}, get the number of doors for a
     * specific frame/door type from {@link IElevationDTO#getNumOfDoors(FrameType, DoorType)}.<br/><br/> 
     * 
     * 2. Create the doors of that type for the elevation, with a standard area.<br/><br/> 
     * 
     * 3. Set's the area of all doors to  1.852m&sup2.<br/><br/>
     * 
     * 4. Adds the door to the elevation.</p>
     * 
     * @param model
     * @param dto
     * @since 0.0.1-SNAPSHOT
     */
    protected void buildDoors(final IElevationDTO dto, final Elevation elevation) {
        final double doorArea = STD_DOOR_HEIGHT * STD_DOOR_WIDTH;
        final GlazingType doorGlazingType = dto.getPercentageWindowDblGlazed() > 0.5 ? GlazingType.Double : GlazingType.Single;  

        for (final FrameType frameType : FrameType.values()) {
            for (final DoorType doorType : DoorType.values()) {
                final int numOfDoors = dto.getNumberOfDoors(frameType, doorType);

                if (numOfDoors > 0) {
                    for (int ct = 1; ct <= numOfDoors; ct++) {
                        final Door door = new Door();
                        door.setDoorType(doorType);
                        door.setFrameType(frameType);
                        door.setArea(doorArea);
                        
                        if (doorType == DoorType.Glazed) {
                        	door.setGlazingType(doorGlazingType);
                        }

                        elevation.addDoor(door);
                    }
                }
            }
        }
    }

    /**
     * The final total area for glazing will be dependent on the area of unattached opening that is not doors available,
     * this final setting of the area will be done in the imputation phase so all we need to to is create windows of the
     * specific type in the correct proportion, i.e summing to an area of 1.
     * 
     * @param dto
     * @param elevation
     * @since 0.0.1-SNAPSHOT
     */
    protected void buildGlazing(final IElevationDTO dto, final Elevation elevation) {
        final Double percentDoubleGlazed = dto.getPercentageWindowDblGlazed();
        
        if (percentDoubleGlazed.equals(_100_PERCENT)) {
            elevation.addGlazing(new Glazing(
                    ONE,
                    GlazingType.Double,
                    get(dto.getDoubleGlazedWindowFrame(), "double glazing frame type")));
            logger.debug("buildWindowsForElevation(): 100% double glazed building window", elevation);
        } else if (percentDoubleGlazed > 0) {
            final double areaOfDoubleGlazedWindow = ONE * (percentDoubleGlazed / 100);
            final double areaOfSingleGlazedWindow = ONE - areaOfDoubleGlazedWindow;

            elevation.addGlazing(new Glazing(
                    areaOfSingleGlazedWindow,
                    GlazingType.Single,
                    get(dto.getSingleGlazedWindowFrame(), "single glazing frame type")));

            elevation.addGlazing(new Glazing(
                    areaOfDoubleGlazedWindow,
                    GlazingType.Double,
                    get(dto.getDoubleGlazedWindowFrame(), "double glazing frame type")
                    ));
        } else {
            elevation.addGlazing(new Glazing(
                    ONE,
                    GlazingType.Single,
                    get(dto.getSingleGlazedWindowFrame(), "single glazing frame type")
                    ));
        }
    }
}
