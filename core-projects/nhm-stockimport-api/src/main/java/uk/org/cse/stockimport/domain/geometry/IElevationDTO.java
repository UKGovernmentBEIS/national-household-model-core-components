package uk.org.cse.stockimport.domain.geometry;

import com.google.common.base.Optional;
import com.google.common.collect.Table;

import uk.org.cse.nhm.energycalculator.api.types.DoorType;
import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.schema.Constraint;
import uk.org.cse.stockimport.domain.schema.Constraint.Type;
import uk.org.cse.stockimport.domain.schema.DTO;
import uk.org.cse.stockimport.domain.schema.DTOField;

/**
 * IElevationDTO.
 *
 * @author richardt
 * @version $Id: IElevationDTO.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
@DTO(
		description = "Used to define each dimension for a house case, it's expected that each house case would have 4 dimensions (FRONT, BACK, LEFT, and RIGHT).",
		value = "elevations",
		required=true
	)
public interface IElevationDTO extends IBasicDTO {
	public static final String ELEVATIONTYPE_FIELD = "elevationType";
	public static final String ATTACHED_FIELD = "tenthsAttached";
	public static final String OPEN_FIELD = "tenthsOpening";
	public static final String PARTY_FIELD = "tenthsPartyWall";
	public static final String EXTERNAL_WALL_TYPE_FIELD = "externalWallConstructionType";
	public static final String CAVITYINSULATED_FIELD = "cavityWallInsulation";
	public static final String INTERNALINSULATED_FIELD = "internalInsulation";
	public static final String EXTERNALINSULATED_FIELD = "externalWallInsulation";
	public static final String PERCENTAGE_DBLGLZD_FIELD = "percentageDoubleGlazed";
	public static final String FRAME_DBLGLZD_FIELD = "doubleGlazedWindowFrame";
	public static final String FRAME_SNGLGLZD_FIELD = "singleGlazedWindowFrame";
	public static final String NUM_WOOD_SOLID_DOOR = "doorFrame:Wood,doorType:Solid";
	public static final String NUM_WOOD_GLAZED_DOOR = "doorFrame:Wood,doorType:Glazed";
	public static final String NUM_METAL_SOLID_DOOR = "doorFrame:Metal,doorType:Solid";
	public static final String NUM_METAL_GLAZED_DOOR = "doorFrame:Metal,doorType:Glazed";
	public static final String NUM_UPVC_SOLID_DOOR = "doorFrame:uPVC,doorType:Solid";
	public static final String NUM_UPVC_GLAZED_DOOR = "doorFrame:uPVC,doorType:Glazed";
	public static final String ANGLE_FROM_NORTH_FIELD = "angleFromNorth";

	@DTOField(ELEVATIONTYPE_FIELD)
	public ElevationType getElevationType();
	public void setElevationType(final ElevationType elevationType);

	@DTOField(EXTERNAL_WALL_TYPE_FIELD)
	public Optional<WallConstructionType> getExternalWallConstructionType();
	public void setExternalWallConstructionType(final Optional<WallConstructionType> externalWallConstructionType);

	@DTOField(INTERNALINSULATED_FIELD)
	public Optional<Boolean> getInternalInsulation();
	public void setInternalInsulation(final Optional<Boolean> internalInsulation);

	@DTOField(EXTERNALINSULATED_FIELD)
	public Optional<Boolean> getExternalInsulation();
	public void setExternalInsulation(final Optional<Boolean> externalInsulation);

	@DTOField(CAVITYINSULATED_FIELD)
	public Optional<Boolean> getCavityInsulation();
	public void setCavityInsulation(final Optional<Boolean> cavityInsulation);

	@DTOField(ATTACHED_FIELD)
	public double getTenthsAttached();
	public void setTenthsAttached(final double tenthsAttached);

	@DTOField(OPEN_FIELD)
	public double getTenthsOpening();
	public void setTenthsOpening(final double tenthsOpening);

	@DTOField(PARTY_FIELD)
	public double getTenthsPartyWall();
	public void setTenthsPartyWall(final double tenthsPartyWall);

	@DTOField(PERCENTAGE_DBLGLZD_FIELD)
	public double getPercentageWindowDblGlazed();
	public void setPercentageWindowDblGlazed(final double percentageWindowDblGlazed);

	@DTOField(FRAME_SNGLGLZD_FIELD)
	public Optional<FrameType> getSingleGlazedWindowFrame();
	public void setSingleGlazedWindowFrame(final Optional<FrameType> singleGlazedWindowFrame);

	@DTOField(value = FRAME_DBLGLZD_FIELD, constraint=@Constraint(value=Type.OPTIONAL, missing="null"))
	public Optional<FrameType> getDoubleGlazedWindowFrame();
	public void setDoubleGlazedWindowFrame(final Optional<FrameType> doubleGlazedWindowFrame);

	@DTOField({"doorFrame", "doorType"})
	public Table<FrameType, DoorType, Integer> getDoors();
	public void setDoors(final Table<FrameType, DoorType, Integer> doors);

	public int getNumberOfDoors(final FrameType ft, final DoorType dt);
	public void setNumberOfDoors(final FrameType frameType, final int numOfDoors);
	public void addDoors(final FrameType frameType, final int count);
    /**
     * TODO.
     *
     * @return
     */
	@DTOField(value = ANGLE_FROM_NORTH_FIELD,
	        constraint=@Constraint(value=Type.OPTIONAL,missing="null"),
	        description="The angle from north that this elevation is facing.")
    public Optional<Double> getAngleFromNorth();
    public void setAngleFromNorth(final Optional<Double> angleFromNorth);
}
