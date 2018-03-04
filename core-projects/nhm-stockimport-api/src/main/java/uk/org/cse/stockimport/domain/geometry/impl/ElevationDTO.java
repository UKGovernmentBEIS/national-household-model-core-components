package uk.org.cse.stockimport.domain.geometry.impl;

import java.util.List;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

import com.google.common.base.Optional;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Table;

import uk.org.cse.nhm.energycalculator.api.types.DoorType;
import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;
import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.stockimport.domain.geometry.IElevationDTO;

/**
 * ElevationDTO. 
 *
 * @assumption CHM does not compute roof windows are either not applicable or zero.
 * @assumption CHM has default imputation values for window orientation(east/west) and over shading(average/unknown)
 * 
 * @author richardt
 * @version $Id: ElevationDTO.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
/**
 * @author hinton
 *
 */
@AutoProperty
public class ElevationDTO extends AbsDTO implements IElevationDTO {
    private ElevationType elevationType = ElevationType.FRONT;

    private Optional<WallConstructionType> externalWallConstructionType = Optional.absent();
    private Optional<Boolean> internalInsulation = Optional.absent();
    private Optional<Boolean> externalInsulation = Optional.absent();
    private Optional<Boolean> cavityInsulation = Optional.absent();

    private double tenthsAttached = 0.00;
    private double tenthsOpening = 0.00;
    private double tenthsPartyWall = 0.00;
    private double percentageWindowDblGlazed = 0.00;

    private Optional<FrameType> singleGlazedWindowFrame = Optional.of(FrameType.Metal);
    private Optional<FrameType> doubleGlazedWindowFrame = Optional.absent();
    
    private Optional<Double> angleFromNorth = Optional.absent();
    
    @Property(policy=PojomaticPolicy.NONE)
    private Table<FrameType, DoorType, Integer> doors = HashBasedTable.create();
    
    /** Default door type used when non-specified 
     * @since 1.0
     * 
     */
    public static final DoorType DEFAULT_DOORTYPE = DoorType.Solid;
    
    
	public ElevationDTO() {
		for (final FrameType ft : FrameType.values()) {
			for (final DoorType dt : DoorType.values()) {
				doors.put(ft, dt, 0);
			}
		}
	}    

	@Override
	public ElevationType getElevationType() {
		return elevationType;
	}

	@Override
	public void setElevationType(final ElevationType elevationType) {
		this.elevationType = elevationType;
	}

	@Override
	public Optional<WallConstructionType> getExternalWallConstructionType() {
		return externalWallConstructionType;
	}

	@Override
	public void setExternalWallConstructionType(
			final Optional<WallConstructionType> externalWallConstructionType) {
		this.externalWallConstructionType = externalWallConstructionType;
	}

	@Override
	public Optional<Boolean> getInternalInsulation() {
		return internalInsulation;
	}

	@Override
	public void setInternalInsulation(final Optional<Boolean> internalInsulation) {
		this.internalInsulation = internalInsulation;
	}

	@Override
	public Optional<Boolean> getExternalInsulation() {
		return externalInsulation;
	}

	@Override
	public void setExternalInsulation(final Optional<Boolean> externalInsulation) {
		this.externalInsulation = externalInsulation;
	}

	@Override
	public Optional<Boolean> getCavityInsulation() {
		return cavityInsulation;
	}

	@Override
	public void setCavityInsulation(final Optional<Boolean> cavityInsulation) {
		this.cavityInsulation = cavityInsulation;
	}

	@Override
	public double getTenthsAttached() {
		return tenthsAttached;
	}

	@Override
	public void setTenthsAttached(final double tenthsAttached) {
		this.tenthsAttached = tenthsAttached;
	}

	@Override
	public double getTenthsOpening() {
		return tenthsOpening;
	}

	@Override
	public void setTenthsOpening(final double tenthsOpening) {
		this.tenthsOpening = tenthsOpening;
	}

	@Override
	public double getTenthsPartyWall() {
		return tenthsPartyWall;
	}

	@Override
	public void setTenthsPartyWall(final double tenthsPartyWall) {
		this.tenthsPartyWall = tenthsPartyWall;
	}

	@Override
	public double getPercentageWindowDblGlazed() {
		return percentageWindowDblGlazed;
	}

	@Override
	public void setPercentageWindowDblGlazed(final double percentageWindowDblGlazed) {
		this.percentageWindowDblGlazed = percentageWindowDblGlazed;
	}

	@Override
	public Optional<FrameType> getSingleGlazedWindowFrame() {
		return singleGlazedWindowFrame;
	}

	@Override
	public void setSingleGlazedWindowFrame(final Optional<FrameType> singleGlazedWindowFrame) {
		this.singleGlazedWindowFrame = singleGlazedWindowFrame;
	}

	@Override
	public Optional<FrameType> getDoubleGlazedWindowFrame() {
		return doubleGlazedWindowFrame;
	}

	@Override
	public void setDoubleGlazedWindowFrame(final Optional<FrameType> doubleGlazedWindowFrame) {
		this.doubleGlazedWindowFrame = doubleGlazedWindowFrame;
	}

	@Override
	public Table<FrameType, DoorType, Integer> getDoors() {
		return doors;
	}

	@Override
	public void setDoors(final Table<FrameType, DoorType, Integer> doors) {
		this.doors = doors;
	}

	@Override
	public int getNumberOfDoors(final FrameType ft, final DoorType dt) {
		return Optional.fromNullable(doors.get(ft, dt)).or(0);
	}
	
	@Override
	public void setNumberOfDoors(final FrameType frameType, final int numOfDoors) {
		doors.put(frameType, DEFAULT_DOORTYPE, numOfDoors);
	}
	
	@Override
	public void addDoors(final FrameType frameType, final int count) {
		setNumberOfDoors(frameType, 
				count + 
				getNumberOfDoors(frameType, DEFAULT_DOORTYPE));
	}
	
	@Override
	public List<String> validate() {
		final ImmutableList.Builder<String> b = ImmutableList.builder();
		b.addAll(super.validate());
		
		b.addAll(checkTenths("attached", tenthsAttached).asSet());
		b.addAll(checkTenths("opening", tenthsOpening).asSet());
		
		if (tenthsAttached == 10) {
			if (externalWallConstructionType.isPresent()) {
				b.add("ten tenths are attached, and external wall construction type is present");
			}
			
			if (cavityInsulation.isPresent() && cavityInsulation.get()) {
				b.add("ten tenths are attached, and cavity insulation is present");
			}
			
			if (externalInsulation.isPresent() && externalInsulation.get()) {
				b.add("ten tenths are attached, and external insulation is present");
			}
			
			if (internalInsulation.isPresent() && internalInsulation.get()) {
				b.add("ten tenths are attached, and internal insulation is present");
			}
		}
		
		return b.build();
	}

	private static Optional<String> checkTenths(final String label, final double tenths) {
		if (tenths < 0) {
			return Optional.of(String.format("Tenths %s %f is less than zero", label, tenths));
		} else if (tenths > 10) {
			return Optional.of(String.format("Tenths %s %f exceeds ten", label, tenths));
		} else {
			return Optional.absent();
		}
	}
	
	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}

	@Override
	public boolean equals(final Object other) {
		if(Pojomatic.equals(this, other)){
			//Override pojomatic for door mao as it struggles doing a diff
			if (other instanceof ElevationDTO){
				final ElevationDTO otherElv = (ElevationDTO) other;
				for(final FrameType frameType : FrameType.values()){
					for(final DoorType doorType : DoorType.values()){
						if (this.getNumberOfDoors(frameType, doorType) != otherElv.getNumberOfDoors(frameType, doorType)) {
							return false;
						}
					}
				}	
			} else {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

    /**
     * @return
     * @see uk.org.cse.stockimport.domain.geometry.IElevationDTO#getAngleFromNorth()
     */
    @Override
    public Optional<Double> getAngleFromNorth() {
        return angleFromNorth;
    }

    /**
     * @param angleFromNorth
     * @see uk.org.cse.stockimport.domain.geometry.IElevationDTO#setAngleFromNorth(com.google.common.base.Optional)
     */
    @Override
    public void setAngleFromNorth(Optional<Double> angleFromNorth) {
        this.angleFromNorth = angleFromNorth;
    }
}
