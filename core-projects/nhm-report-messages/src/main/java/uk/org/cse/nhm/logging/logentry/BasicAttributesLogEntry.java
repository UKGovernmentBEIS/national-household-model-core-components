package uk.org.cse.nhm.logging.logentry;

import java.util.EnumMap;
import java.util.Set;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.hom.housepropertystore.IHouseProperties;
import uk.org.cse.hom.money.FinancialAttributes;
import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;
import uk.org.cse.nhm.energycalculator.api.types.WallType;
import uk.org.cse.nhm.hom.emf.technologies.EmitterType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.IBackBoiler;
import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IHeatPump;
import uk.org.cse.nhm.hom.emf.technologies.IHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IPrimarySpaceHeater;
import uk.org.cse.nhm.hom.emf.technologies.IRoomHeater;
import uk.org.cse.nhm.hom.emf.technologies.IStorageHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.ICombiBoiler;
import uk.org.cse.nhm.hom.emf.util.Efficiency;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.hom.people.People;
import uk.org.cse.nhm.hom.structure.IWall;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.hom.types.BuiltFormType;
import uk.org.cse.nhm.hom.types.MorphologyType;
import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.hom.types.TenureType;
import uk.org.cse.nhm.types.MainHeatingSystemType;

/**
 * This is a normalization for {@link StateChangeLogEntry}
 * 
 * @author hinton
 * 
 * @since 3.7.0
 */
@AutoProperty
public class BasicAttributesLogEntry extends AbstractLogEntry {
	public static final Set<String> REQUIRED_ADDITIONAL_PROPERTIES = ImmutableSet.of("EMPHRPX", "UNOC", "FPFLGF", "FPFLGB", "PYNGX");
	
	@AutoProperty
	public static class Details {
		private String aacode;
		private RegionType region;
		private BuiltFormType builtForm;
		private MorphologyType morphology;
		private TenureType tenure;
		private int buildYear;
		private Integer boilerInstallationYear;
		private FuelType fuelTypeOfMainHeatingSystem;
		private MainHeatingSystemType typeOfMainHeatingSystem;
		private EmitterType emitterType;
		private boolean centrallyHeated;
		private boolean mainHeatingCondensing;
		private double mainHeatingEfficiency;

		private boolean onGasGrid;
		private WallConstructionType predominantWallConstructionType;
		private String predominantWallInsulationType;
		@JsonProperty("loftInsulationThickness_mm")
		private double getLoftInsulationThickness_mm;
		private double totalFloorArea;

		private int numberOfBedrooms;

		private double annualFullIncome;
		private int yearMovedIn;
		private int chiefEarnersAge;
		private boolean longTermSickOrDisabled;
		private boolean onBenefits;
		private int numberOfChildren;
		private int numberOfAdults;
		private Boolean childrenUnder5Present;
		private String fuelPovertyBasicIncome;
		private String fuelPovertyFullIncome;
		private String underOccupied;
		private String normalWorkingHours;
		private Boolean childrenUnder16Present;
		private String workingStatus;
		

		/**
		 * Create an empty details - this is for mongo.
		 */
		public Details() {
		}

		/**
		 * Construct a basic attributes entry from the given dwelling parts -
		 * this will pull out suitable values for all its properties from the
		 * supplied parts.
		 * 
		 * TODO factor getter methods out into {@link ITechnologyOperations} and
		 * other similar classes, pass these in
		 * 
		 * @param executionID
		 * @param attributesID
		 * @param attributes
		 * @param technologies
		 * @param structure
		 * @param people
		 * @param finance
		 */
		public Details(final ITechnologyOperations operations, final BasicCaseAttributes attributes, final ITechnologyModel technologies, final StructureModel structure, final People people,
				final FinancialAttributes finance, final IHouseProperties additionalProperties) {

			setAacode(attributes.getAacode());
			setRegion(attributes.getRegionType());
			setBuiltForm(structure.getBuiltFormType());
			setMorphology(attributes.getMorphologyType());
			setTenure(attributes.getTenureType());
			setBuildYear(attributes.getBuildYear());
			setBoilerInstallationYear(getBoilerInstallationYear(technologies));
			setFuelTypeOfMainHeatingSystem(getFuelTypeOfMainHeatingSystem(operations, technologies));
			setTypeOfMainHeatingSystem(getTypeOfMainHeatingSystem(technologies));
			setEmitterType(getEmitterType(technologies));
			setCentrallyHeated(isCentrallyHeated(technologies));
			setMainHeatingCondensing(isCondensing(technologies));
			setMainHeatingEfficiency(getMainHeatingEfficiency(technologies).value);
			setOnGasGrid(structure.isOnGasGrid());
			setPredominantWallConstructionType(getPredominantWallConstructionType(structure));
			setPredominantWallInsulationType(getPredominantWallInsulationType(structure));
			setLoftInsulationThicknessInMM(structure.getRoofInsulationThickness());
			setTotalFloorArea(getTotalFloorArea(structure));

			setNumberOfBedrooms(structure.getNumberOfBedrooms());
			setNumberOfAdults(people.getAdults());
			setNumberOfChildren(people.getChildren());

			setAnnualFullIncome(finance.getHouseHoldIncomeBeforeTax() == null ? 0 : finance.getHouseHoldIncomeBeforeTax());
			setYearMovedIn(people.getDateMovedIn() == null ? 0 : people.getDateMovedIn().getYear());
			setChiefEarnersAge(finance.getAgeOfChiefIncomeEarner() == null ? 0 : finance.getAgeOfChiefIncomeEarner());
			setWorkingStatus(additionalProperties.get("EMPHRPX")); // ehs/derived/interview/EMPHRPX 
			setNormalWorkingHours(people.getNormalWorkingHours());
			setUnderOccupied(additionalProperties.get("UNOC")); // ehs/fuel_poverty/fuel_poverty_dataset/UNOC
			setLongTermSickOrDisabled(people.hasDisabledOrSickOccupant() == null ? false : people.hasDisabledOrSickOccupant());
			setOnBenefits(people.hasOccupantOnBenefits() == null ? false : people.hasOccupantOnBenefits());
			setFuelPovertyFullIncome(additionalProperties.get("FPFLGF")); // ehs/fuel_poverty/fuel_poverty_dataset/FPFLGF
			setFuelPovertyBasicIncome(additionalProperties.get("FPFLGB")); // ehs/fuel_poverty/fuel_poverty_dataset/FPFLGB
			setChildrenUnder16Present(areChildrenUnderNPresent(16, additionalProperties.get("PYNGX"))); // ehs/derived/interview/PYNGX
			setChildrenUnder5Present(areChildrenUnderNPresent(5, additionalProperties.get("PYNGX")));
		}

		private void setChildrenUnder5Present(Boolean childrenUnder5Present) {
			this.childrenUnder5Present = childrenUnder5Present;
		}
		
		public Boolean getChildrenUnder5Present() {
			return childrenUnder5Present;
		}

		public String getFuelPovertyBasicIncome() {
			return fuelPovertyBasicIncome;
		}

		public String getFuelPovertyFullIncome() {
			return fuelPovertyFullIncome;
		}

		public String getUnderOccupied() {
			return underOccupied;
		}

		public String getNormalWorkingHours() {
			return normalWorkingHours;
		}

		public Boolean getChildrenUnder16Present() {
			return childrenUnder16Present;
		}

		public String getWorkingStatus() {
			return workingStatus;
		}

		public void setPredominantWallInsulationType(String predominantWallInsulationType) {
			this.predominantWallInsulationType = predominantWallInsulationType;
		}

		public void setChildrenUnder16Present(Boolean childrenUnder16Present) {
			this.childrenUnder16Present = childrenUnder16Present;
		}

		public void setWorkingStatus(String workingStatus) {
			this.workingStatus = workingStatus;
		}

		private void setFuelPovertyBasicIncome(String fuelPovertyBasicIncome) {
			this.fuelPovertyBasicIncome = fuelPovertyBasicIncome;
		}

		private void setFuelPovertyFullIncome(String fuelPovertyFullIncome) {
			this.fuelPovertyFullIncome = fuelPovertyFullIncome;
		}

		private void setUnderOccupied(String underOccupied) {
			this.underOccupied = underOccupied;
		}

		private void setNormalWorkingHours(String normalWorkingHours) {
			this.normalWorkingHours = normalWorkingHours;
		}

		private Boolean areChildrenUnderNPresent(int age, String youngest) {
			try {
				double parsedYoungest = Double.parseDouble(youngest);
				return parsedYoungest < age;
			} catch(NumberFormatException ex) {
				return null;
			} catch(NullPointerException ex) {
				return null;
			}
		}

		private void setNumberOfChildren(int children) {
			this.numberOfChildren = children;
		}
		
		public int getNumberOfChildren() {
			return numberOfChildren;
		}

		private void setNumberOfAdults(int adults) {
			this.numberOfAdults = adults;
		}
		
		public int getNumberOfAdults() {
			return numberOfAdults;
		}

		@JsonIgnore
		private void setPredominantWallInsulationType(Optional<WallInsulationType> predominantWallInsulationType) {
			if(predominantWallInsulationType.isPresent()) {
				this.predominantWallInsulationType = predominantWallInsulationType.get().toString();
			} else {
				this.predominantWallInsulationType = "None";
			}
		}
		
		public String getPredominantWallInsulationType() {
			return predominantWallInsulationType;
		}
		
		private Optional<WallInsulationType> getPredominantWallInsulationType(StructureModel structure) {
			final EnumMap<WallInsulationType, Double> areaCoveredByInsulation = new EnumMap<WallInsulationType, Double>(WallInsulationType.class);
			for (final Storey storey : structure.getStoreys()) {
				for (final IWall wall : storey.getImmutableWalls()) {
					for(WallInsulationType insulationType : wall.getWallInsulationTypes()) {
						if(areaCoveredByInsulation.containsKey(insulationType)) {
							areaCoveredByInsulation.put(insulationType, areaCoveredByInsulation.get(insulationType) + wall.getArea());
						} else {
							areaCoveredByInsulation.put(insulationType, wall.getArea());
						}
					}
				}
			}

			double maxArea = 0;
			WallInsulationType predominantInsulation = null;
			for (final WallInsulationType insulationType : areaCoveredByInsulation.keySet()) {
				if (areaCoveredByInsulation.get(insulationType) > maxArea) {
					maxArea = areaCoveredByInsulation.get(insulationType);
					predominantInsulation = insulationType;
				}
			}
			return Optional.fromNullable(predominantInsulation);
		}

		private double getTotalFloorArea(StructureModel structure) {
			return structure.getFloorArea();
		}

		private WallConstructionType getPredominantWallConstructionType(StructureModel structure) {
			final EnumMap<WallConstructionType, Double> counters = new EnumMap<WallConstructionType, Double>(WallConstructionType.class);
			for (final WallConstructionType wt : WallConstructionType.values()) {
				if (wt.getWallType() == WallType.External) {
					counters.put(wt, 0d);
				}
			}
			for (final Storey storey : structure.getStoreys()) {
				for (final IWall wall : storey.getImmutableWalls()) {
					WallConstructionType wallConstructionType = wall.getWallConstructionType();
					if (wallConstructionType.getWallType() == WallType.External) {
						counters.put(wallConstructionType, wall.getArea() + counters.get(wallConstructionType));
					}
				}
			}
			double maxArea = 0;
			WallConstructionType maxAreaWt = null;
			for (final WallConstructionType wt : counters.keySet()) {
				if (counters.get(wt) >= maxArea) {
					maxArea = counters.get(wt);
					maxAreaWt = wt;
				}
			}
			return maxAreaWt;
		}

		private Efficiency getMainHeatingEfficiency(ITechnologyModel technologies) {
			IPrimarySpaceHeater primarySpaceHeater = technologies.getPrimarySpaceHeater();
			if (primarySpaceHeater instanceof ICentralHeatingSystem) {
				final IHeatSource heatSource = ((ICentralHeatingSystem) primarySpaceHeater).getHeatSource();
				if (heatSource instanceof IBoiler) {
					return ((IBoiler) heatSource).getWinterEfficiency();
				} else if (heatSource instanceof IHeatPump) {
					return ((IHeatPump) heatSource).getCoefficientOfPerformance();
				} else if (heatSource instanceof ICommunityHeatSource) {
					return ((ICommunityHeatSource) heatSource).getHeatEfficiency();
				} else {
					return Efficiency.ZERO;
				}
			} else if (primarySpaceHeater instanceof IStorageHeater) {
				return Efficiency.ONE;
			} else if (primarySpaceHeater instanceof IWarmAirSystem) {
				return ((IWarmAirSystem) primarySpaceHeater).getEfficiency();
			} else {
				final IRoomHeater rh = technologies.getSecondarySpaceHeater();
				if (rh == null) {
					return Efficiency.ZERO;
				} else {
					return rh.getEfficiency();
				}
			}
		}

		private boolean isCondensing(ITechnologyModel technologies) {
			IPrimarySpaceHeater primarySpaceHeater = technologies.getPrimarySpaceHeater();

			if (primarySpaceHeater instanceof ICentralHeatingSystem) {
				final IHeatSource heatSource = ((ICentralHeatingSystem) primarySpaceHeater).getHeatSource();
				if (heatSource instanceof IBoiler) {
					return ((IBoiler) heatSource).isCondensing();
				}
			}

			return false;
		}

		private boolean isCentrallyHeated(ITechnologyModel technologies) {
			return technologies.getPrimarySpaceHeater() instanceof ICentralHeatingSystem;
		}

		private EmitterType getEmitterType(ITechnologyModel technologies) {
			IPrimarySpaceHeater primarySpaceHeater = technologies.getPrimarySpaceHeater();

			if (primarySpaceHeater instanceof ICentralHeatingSystem) {
				return ((ICentralHeatingSystem) primarySpaceHeater).getEmitterType();
			} else {
				return null;
			}
		}

		private MainHeatingSystemType getTypeOfMainHeatingSystem(ITechnologyModel technologies) {
			IPrimarySpaceHeater primarySpaceHeater = technologies.getPrimarySpaceHeater();
			if (primarySpaceHeater instanceof ICentralHeatingSystem) {
				final IHeatSource heatSource = ((ICentralHeatingSystem) primarySpaceHeater).getHeatSource();
				if (heatSource instanceof IBackBoiler) {
					return MainHeatingSystemType.BackBoiler;
				} else if (heatSource instanceof ICombiBoiler) {
					return MainHeatingSystemType.CombiBoiler;
				} else if (heatSource instanceof IBoiler) {
					return MainHeatingSystemType.StandardBoiler;
				} else if (heatSource instanceof ICommunityHeatSource) {
					return MainHeatingSystemType.Community;
				} else if (heatSource instanceof IHeatPump) {
					return MainHeatingSystemType.HeatPump;
				} else {
					return MainHeatingSystemType.StandardBoiler;
				}
			} else if (primarySpaceHeater instanceof IStorageHeater) {
				return MainHeatingSystemType.StorageHeater;
			} else if (primarySpaceHeater instanceof IWarmAirSystem) {
				return MainHeatingSystemType.WarmAirSystem;
			} else {
				return MainHeatingSystemType.RoomHeater;
			}
		}

		private FuelType getFuelTypeOfMainHeatingSystem(ITechnologyOperations operations, ITechnologyModel technologies) {
			return operations.getMainHeatingFuel(technologies);
		}

		private Integer getBoilerInstallationYear(ITechnologyModel technologies) {
			if (technologies.getIndividualHeatSource() != null) {
				return technologies.getIndividualHeatSource().getInstallationYear();
			} else if (technologies.getCommunityHeatSource() != null) {
				return technologies.getCommunityHeatSource().getInstallationYear();
			} else {
				return null;
			}
		}

		public String getAacode() {
			return aacode;
		}

		public void setAacode(String aacode) {
			this.aacode = aacode;
		}

		public RegionType getRegion() {
			return region;
		}

		public void setRegion(RegionType region) {
			this.region = region;
		}

		public BuiltFormType getBuiltForm() {
			return builtForm;
		}

		public void setBuiltForm(BuiltFormType builtForm) {
			this.builtForm = builtForm;
		}

		public MorphologyType getMorphology() {
			return morphology;
		}

		public void setMorphology(MorphologyType morphology) {
			this.morphology = morphology;
		}

		public TenureType getTenure() {
			return tenure;
		}

		public void setTenure(TenureType tenure) {
			this.tenure = tenure;
		}

		public int getBuildYear() {
			return buildYear;
		}

		public void setBuildYear(int buildYear) {
			this.buildYear = buildYear;
		}

		public Integer getBoilerInstallationYear() {
			return boilerInstallationYear;
		}

		public void setBoilerInstallationYear(Integer boilerInstallationYear) {
			this.boilerInstallationYear = boilerInstallationYear;
		}

		public FuelType getFuelTypeOfMainHeatingSystem() {
			return fuelTypeOfMainHeatingSystem;
		}

		public void setFuelTypeOfMainHeatingSystem(FuelType fuelTypeOfMainHeatingSystem) {
			this.fuelTypeOfMainHeatingSystem = fuelTypeOfMainHeatingSystem;
		}

		public MainHeatingSystemType getTypeOfMainHeatingSystem() {
			return typeOfMainHeatingSystem;
		}

		public void setTypeOfMainHeatingSystem(MainHeatingSystemType typeOfMainHeatingSystem) {
			this.typeOfMainHeatingSystem = typeOfMainHeatingSystem;
		}

		public EmitterType getEmitterType() {
			return emitterType;
		}

		public void setEmitterType(EmitterType emitterType) {
			this.emitterType = emitterType;
		}

		public boolean isCentrallyHeated() {
			return centrallyHeated;
		}

		public void setCentrallyHeated(boolean centrallyHeated) {
			this.centrallyHeated = centrallyHeated;
		}

		public boolean isMainHeatingCondensing() {
			return mainHeatingCondensing;
		}

		public void setMainHeatingCondensing(boolean condensing) {
			this.mainHeatingCondensing = condensing;
		}

		public double getMainHeatingEfficiency() {
			return mainHeatingEfficiency;
		}

		public void setMainHeatingEfficiency(double mainHeatingEfficiency) {
			this.mainHeatingEfficiency = mainHeatingEfficiency;
		}

		public boolean isOnGasGrid() {
			return onGasGrid;
		}

		public void setOnGasGrid(boolean onGasGrid) {
			this.onGasGrid = onGasGrid;
		}

		public WallConstructionType getPredominantWallConstructionType() {
			return predominantWallConstructionType;
		}

		public void setPredominantWallConstructionType(WallConstructionType predominantWallConstructionType) {
			this.predominantWallConstructionType = predominantWallConstructionType;
		}

		public double getLoftInsulationThickness_mm() {
			return getLoftInsulationThickness_mm;
		}

		public void setLoftInsulationThicknessInMM(double thickness) {
			this.getLoftInsulationThickness_mm = thickness;
		}

		public double getTotalFloorArea() {
			return totalFloorArea;
		}

		public void setTotalFloorArea(double totalFloorArea) {
			this.totalFloorArea = totalFloorArea;
		}

		public int getNumberOfBedrooms() {
			return numberOfBedrooms;
		}

		public void setNumberOfBedrooms(int numberOfBedrooms) {
			this.numberOfBedrooms = numberOfBedrooms;
		}

		public double getAnnualFullIncome() {
			return annualFullIncome;
		}

		public void setAnnualFullIncome(double annualFullIncome) {
			this.annualFullIncome = annualFullIncome;
		}

		public int getYearMovedIn() {
			return yearMovedIn;
		}

		public void setYearMovedIn(int yearMovedIn) {
			this.yearMovedIn = yearMovedIn;
		}

		public int getChiefEarnersAge() {
			return chiefEarnersAge;
		}

		public void setChiefEarnersAge(int chiefEarnersAge) {
			this.chiefEarnersAge = chiefEarnersAge;
		}

		@JsonProperty("longTermSickOrDisabled")
		public boolean isLongTermSickOrDisabled_HHLTSICK() {
			return longTermSickOrDisabled;
		}

		public void setLongTermSickOrDisabled(boolean longTermSickOrDisabled) {
			this.longTermSickOrDisabled = longTermSickOrDisabled;
		}

		@JsonProperty("onBenefits")
		public boolean isOnBenefits_HHVULX() {
			return onBenefits;
		}

		public void setOnBenefits(boolean onBenefits) {
			this.onBenefits = onBenefits;
		}

		@Override
		public String toString() {
			return Pojomatic.toString(this);
		}

		@Override
		public boolean equals(Object obj) {
			return Pojomatic.equals(this, obj);
		}

		@Override
		public int hashCode() {
			return Pojomatic.hashCode(this);
		}
	}

	 @JsonCreator
	 public BasicAttributesLogEntry(@JsonProperty("attributesID") int attributesID, @JsonProperty("details") Details details) {
		this.details = details;
		this.attributesID = attributesID;
	}

	private Details details;
	private final int attributesID;

	public int getAttributesID() {
		return attributesID;
	}

	@JsonIgnore
	public int getStructureID() {
		return attributesID;
	}

	public Details getDetails() {
		return details;
	}

	public void setDetails(Details details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

	@Override
	public boolean equals(Object obj) {
		return Pojomatic.equals(this, obj);
	}

	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}
}
