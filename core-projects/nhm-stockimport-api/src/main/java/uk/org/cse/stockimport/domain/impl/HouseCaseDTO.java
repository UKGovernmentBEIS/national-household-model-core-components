package uk.org.cse.stockimport.domain.impl;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.google.common.base.Optional;

import uk.org.cse.nhm.hom.components.fabric.types.FloorConstructionType;
import uk.org.cse.nhm.hom.types.BuiltFormType;
import uk.org.cse.nhm.hom.types.MorphologyType;
import uk.org.cse.nhm.hom.types.RegionType;
import uk.org.cse.nhm.hom.types.TenureType;
import uk.org.cse.stockimport.domain.IHouseCaseDTO;
import uk.org.cse.stockimport.domain.geometry.impl.AbsDTO;

/**
 * HouseCaseDTO.
 *
 * @author richardt
 * @version $Id: cse-eclipse-codetemplates.xml 94 2010-09-30 15:39:21Z richardt $
 * @since 1.0
 */
@AutoProperty
public class HouseCaseDTO extends AbsDTO implements IHouseCaseDTO {
    double householdCaseWeight;
    double dwellingCaseWeight;
    RegionType regionType = RegionType.London;
    TenureType tenureType = TenureType.HousingAssociation;
    BuiltFormType builtFormType = BuiltFormType.Bungalow;
    Optional<Integer> adultOccupants = Optional.absent();
    Optional<Integer> childOccupants = Optional.absent();
    FloorConstructionType floorConstructionType = FloorConstructionType.Solid;
    double livingAreaFaction = 0;
    int numOfHabitalRooms = 0;
    boolean hasDraftLoby = false;
    MorphologyType morphologyType = MorphologyType.HamletsAndIsolatedDwellings;
    boolean onGasGrid;
    boolean hasAccessToOutsideSpace;
    boolean ownsPartOfRoof;
    boolean hasLoft;
   
	double area;
    int numberOfBedrooms;

    private double frontPlotDepth, frontPlotWidth;
    private double backPlotDepth, backPlotWidth;
	private int buildYear;
	
    private int mainFloorLevel;

    @Override
    public int getMainFloorLevel() {
        return mainFloorLevel;
    }

    @Override
    public void setMainFloorLevel(int mainFloorLevel) {
        this.mainFloorLevel = mainFloorLevel;
    }
    
	@Override
	public double getHouseholdCaseWeight() {
		return householdCaseWeight;
	}
	@Override
	public void setHouseholdCaseWeight(final double householdCaseWeight) {
		this.householdCaseWeight = householdCaseWeight;
	}
	@Override
	public double getDwellingCaseWeight() {
		return dwellingCaseWeight;
	}
	@Override
	public void setDwellingCaseWeight(final double dwellingCaseWeight) {
		this.dwellingCaseWeight = dwellingCaseWeight;
	}

	@Override
	public RegionType getRegionType() {
		return regionType;
	}

	@Override
	public void setRegionType(final RegionType regionType) {
		this.regionType = regionType;
	}

	@Override
	public TenureType getTenureType() {
		return tenureType;
	}

	@Override
	public void setTenureType(final TenureType tenureType) {
		this.tenureType = tenureType;
	}

	@Override
	public BuiltFormType getBuiltFormType() {
		return builtFormType;
	}

	@Override
	public void setBuiltFormType(final BuiltFormType builtFormType) {
		this.builtFormType = builtFormType;
	}

	@Override
	public Optional<Integer> getAdultOccupants() {
		return adultOccupants;
	}

	@Override
	public void setAdultOccupants(final Optional<Integer> adultOccupants) {
		this.adultOccupants = adultOccupants;
	}

	@Override
	public Optional<Integer> getChildOccupants() {
		return childOccupants;
	}

	@Override
	public void setChildOccupants(final Optional<Integer> childOccupants) {
		this.childOccupants = childOccupants;
	}

	@Override
	public FloorConstructionType getFloorConstructionType() {
		return floorConstructionType;
	}

	@Override
	public void setFloorConstructionType(final FloorConstructionType floorConstructionType) {
		this.floorConstructionType = floorConstructionType;
	}

	@Override
	public double getLivingAreaFaction() {
		return livingAreaFaction;
	}

	@Override
	public void setLivingAreaFaction(final double livingAreaFaction) {
		this.livingAreaFaction = livingAreaFaction;
	}

	@Override
	public int getNumOfHabitalRooms() {
		return numOfHabitalRooms;
	}

	@Override
	public void setNumOfHabitalRooms(final int numOfHabitalRooms) {
		this.numOfHabitalRooms = numOfHabitalRooms;
	}

	@Override
	public boolean isHasDraftLoby() {
		return hasDraftLoby;
	}

	@Override
	public void setHasDraftLoby(final boolean hasDraftLoby) {
		this.hasDraftLoby = hasDraftLoby;
	}

	@Override
	public MorphologyType getMorphologyType() {
		return morphologyType;
	}

	@Override
	public void setMorphologyType(final MorphologyType morphologyType) {
		this.morphologyType = morphologyType;
	}

	@Override
	public boolean isOnGasGrid() {
		return onGasGrid;
	}

	@Override
	public void setOnGasGrid(final boolean onGasGrid) {
		this.onGasGrid = onGasGrid;
	}

	@Override
	public boolean isHasAccessToOutsideSpace() {
		return hasAccessToOutsideSpace;
	}

	@Override
	public void setHasAccessToOutsideSpace(final boolean hasAccessToOutsideSpace) {
		this.hasAccessToOutsideSpace = hasAccessToOutsideSpace;
	}

	@Override
	public boolean isOwnsPartOfRoof() {
		return ownsPartOfRoof;
	}

	@Override
	public void setOwnsPartOfRoof(final boolean ownsPartOfRoof) {
		this.ownsPartOfRoof = ownsPartOfRoof;
	}

	@Override
	public boolean isHasLoft() {
		return hasLoft;
	}

	@Override
	public void setHasLoft(final boolean hasLoft) {
		this.hasLoft = hasLoft;
	}

	@Override
	public double getArea() {
		return area;
	}

	@Override
	public void setArea(final double area) {
		this.area = area;
	}

	@Override
	public int getNumberOfBedrooms() {
		return numberOfBedrooms;
	}

	@Override
	public void setNumberOfBedrooms(final int numberOfBedrooms) {
		this.numberOfBedrooms = numberOfBedrooms;
	}

	@Override
	public double getFrontPlotDepth() {
		return frontPlotDepth;
	}

	@Override
	public void setFrontPlotDepth(final double frontPlotDepth) {
		this.frontPlotDepth = frontPlotDepth;
	}

	@Override
	public double getFrontPlotWidth() {
		return frontPlotWidth;
	}

	@Override
	public void setFrontPlotWidth(final double frontPlotWidth) {
		this.frontPlotWidth = frontPlotWidth;
	}

	@Override
	public double getBackPlotDepth() {
		return backPlotDepth;
	}

	@Override
	public void setBackPlotDepth(final double backPlotDepth) {
		this.backPlotDepth = backPlotDepth;
	}

	@Override
	public double getBackPlotWidth() {
		return backPlotWidth;
	}

	@Override
	public void setBackPlotWidth(final double backPlotWidth) {
		this.backPlotWidth = backPlotWidth;
	}

	@Override
	public int getBuildYear() {
		return buildYear;
	}

	@Override
	public void setBuildYear(final int buildYear) {
		this.buildYear = buildYear;
	}


	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

	@Override
	public boolean equals(final Object obj) {
		return Pojomatic.equals(this, obj);
	}
}
