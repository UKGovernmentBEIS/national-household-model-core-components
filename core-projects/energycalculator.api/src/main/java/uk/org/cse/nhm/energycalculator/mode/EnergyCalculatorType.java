package uk.org.cse.nhm.energycalculator.mode;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import uk.org.cse.nhm.energycalculator.api.IHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.IWeather;
import uk.org.cse.nhm.energycalculator.api.impl.DailyHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.impl.Weather;
import uk.org.cse.nhm.energycalculator.api.types.FloorConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.GlazingType;
import uk.org.cse.nhm.energycalculator.api.types.LightType;
import uk.org.cse.nhm.energycalculator.api.types.MonthType;
import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.energycalculator.api.types.RegionType.Country;
import uk.org.cse.nhm.energycalculator.api.types.RoofConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.RoofType;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WindowGlazingAirGap;
import uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType;
import uk.org.cse.nhm.energycalculator.api.types.Zone2ControlParameter;

public class EnergyCalculatorType {
	public enum ECUvalues {
		SAP2012, SCENARIO;

		public double getWall(double scenario, Country country, WallConstructionType constructionType,
				double externalOrInternalInsulationThickness, boolean hasCavityInsulation, Band band,
				double thickness) {
			switch (this) {
			case SCENARIO:
				return scenario;
			case SAP2012:
				return SAPTables.Walls.uValue(country, constructionType, externalOrInternalInsulationThickness,
						hasCavityInsulation, band, thickness);
			default:
				throw new RuntimeException(this + " has no defined wall u-values");
			}
		}

		public double getOutsideDoor(double uValue, Band ageBand, Country country) {
			switch (this) {
			case SCENARIO:
				return uValue;
			case SAP2012:
				return SAPTables.Doors.getOutside(ageBand, country);
			default:
				throw new RuntimeException(this + " has no defined door u-values");
			}
		}

		public double getCeiling(double uValue, RoofType type, RoofConstructionType roofConstructionType,
				Double roofInsulationThickness, Country country, Band ageBand) {
			switch (this) {
			case SCENARIO:
				return uValue;
			case SAP2012:
				return SAPTables.Roofs.get(type, roofConstructionType, roofInsulationThickness, country, ageBand);
			default:
				throw new RuntimeException(this + " has no defined roof u-values");
			}

		}

		public double getWindow(double uValue, FrameType frameType, GlazingType glazingType,
				WindowInsulationType insulationType, WindowGlazingAirGap airGap) {
			switch (this) {
			case SCENARIO:
				return uValue;
			case SAP2012:
				return SAPTables.Windows.uValue(frameType, glazingType, insulationType, airGap);
			default:
				throw new RuntimeException(this + " has no defined window u-values");
			}
		}

		public double getFloor(double uValue, boolean isPartyFloor, boolean isGroundFloor, double area,
				double exposedPerimeter, double wallThickness, FloorConstructionType groundFloorConstructionType,
				double insulationThickness, Band ageBand, Country country) {
			switch (this) {
			case SCENARIO:
				return uValue;
			case SAP2012:
				return SAPTables.Floors.get(isPartyFloor, isGroundFloor, area, exposedPerimeter, wallThickness,
						groundFloorConstructionType, insulationThickness, ageBand, country);
			default:throw new RuntimeException(this + " has no defined floor u-values");
			}
		}

		public double getWallAirChangeRate(double ach, final WallConstructionType wallType) {
			switch (this) {
			case SCENARIO: return ach;
			case SAP2012: return SAPTables.Walls.infiltration(wallType);
			default:throw new RuntimeException(this + " has no defined wall air change rate");
			}
		}

		public double getFrameFactor(double _frameFactor, FrameType frameType) {
			switch (this) {
			case SCENARIO: return _frameFactor;
			case SAP2012: return SAPTables.Windows.frameFactor(frameType);
			default:throw new RuntimeException(this + " has no defined frame factor");
			}
		}

		public double getVisibleLightTransmissivity(double _visibleLightTransmittivity, GlazingType glazingType) {
			switch (this) {
			case SCENARIO: return _visibleLightTransmittivity;
			case SAP2012: return SAPTables.Windows.visibleLightTransmittivity(glazingType);
			default:throw new RuntimeException(this + " has no defined transmissivity for visible light");
			}
		}

		public double getSolarGainTransmissivity(double _solarGainTransmissivity, GlazingType glazingType,
				WindowInsulationType insulationType) {
			switch (this) {
			case SCENARIO: return _solarGainTransmissivity;
			case SAP2012: return SAPTables.Windows.solarGainTransmissivity(glazingType, insulationType);
			default:throw new RuntimeException(this + " has no defined transmissivity for solar gains");
			}
		}
	}

	public enum ECHeatingSchedule {
		SAP2012, SCENARIO;

		public boolean isHeatingOn(MonthType m, Set<MonthType> heatingMonths) {
			if (this == SCENARIO) {
				return heatingMonths.contains(m);
			} else {
				return m.between(MonthType.October, MonthType.May);
			}
		}

		public IHeatingSchedule getZone1HeatingSchedule(MonthType m, Set<MonthType> heatingMonths,	IHeatingSchedule heatingSchedule) {
			if (this == SCENARIO) {
				if (isHeatingOn(m, heatingMonths)) {
					return heatingSchedule;
				} else {
					return DailyHeatingSchedule.OFF;
				}
			} else {
				return SAPTables.HeatingSchedule.weekdaySevenAndEightWeekendZeroAndEight;
			}
		}

		public Map<Zone2ControlParameter, IHeatingSchedule> getZone2HeatingSchedules(
				MonthType m,
				Set<MonthType> heatingMonths, IHeatingSchedule heatingSchedule) {
			if (this == SCENARIO) {
				final IHeatingSchedule schedule = getZone1HeatingSchedule(m, heatingMonths, heatingSchedule);
				final EnumMap<Zone2ControlParameter, IHeatingSchedule> map = new EnumMap<>(Zone2ControlParameter.class);
				for (final Zone2ControlParameter cp : Zone2ControlParameter.values()) {
					map.put(cp, schedule);
				}
				return map;
			} else {
				return SAPTables.HeatingSchedule.zoneTwo;
			}
		}

		public boolean useHeatingOnFactor() {
			return this == SCENARIO;
		}

	}

	public enum ECWeather {
		SAP2012, SCENARIO;

		public IWeather getWeather(IWeather weather) {
			if (this == SAP2012) {
				return Weather.SAP12;
			} else {
				return weather;
			}
		}

		public double getLatitude(double latitudeRadians) {
			if (this == SAP2012) return RegionType.UK_AVERAGE_LATITUDE_RADIANS;
			else return latitudeRadians;
		}
	}

	/*
	BEISDOC
	NAME: CFL Energy Consumption
	DESCRIPTION: The number of watts of electricity a CFL bulb must consume to emit a watt of light.
	TYPE: value
	UNIT: Dimensionless
	SAP: (L2)
        SAP_COMPLIANT: Yes
	BREDEM: 1C
        BREDEM_COMPLIANT: Yes
	DEPS: incandescent-energy-consumption
	NOTES: Worked out that this was half the incandescent efficiency by working backwards through the formula.
	ID: cfl-energy-consumption
	CODSIEB
	*/
	public enum ECLighting {
		SAP2012(new LightType []{LightType.Incandescent, LightType.CFL},
				new double[]    {1d,                     0.5}), 
		PRODUCTS_POLICY(
				new LightType[] {
						LightType.Incandescent, 
						LightType.CFL, 
						LightType.Halogen,
						LightType.LED, 
						LightType.LVHalogen, 
						LightType.APlusPlus
				},
				new double [] {1d, 0.166, 0.714, 0.166, 0.43, 0.076});

		private ECLighting(LightType [] types, double[] values) {
			this.factor = new double[LightType.values().length];
			double min = Double.MAX_VALUE;
			assert(types.length == values.length);
			for (int i = 0; i<types.length; i++) {
				min = Math.min(min, values[i]);
				factor[types[i].ordinal()] = INCANDESCENT_EFFICIENCY * values[i];
			}
			for (int i = 0; i<factor.length; i++) {
				if (factor[i] == 0) {
					factor[i] = min * INCANDESCENT_EFFICIENCY;
				}
			}
		}
		
		private double[] factor;
		
		/*
		BEISDOC
		NAME: Incandescent Energy Consumption
		DESCRIPTION: The number of watts of electricity an incandescent bulb must consume to emit a watt of light.
		TYPE: value
		UNIT: Dimensionless
		SAP: (L1)
	        SAP_COMPLIANT: Yes
		BREDEM: 1B
	        BREDEM_COMPLIANT: Yes
		CONVERSION: From kWh/year to W (1000 / (365.25 * 24))
	        NOTES: Derived from SAP formulas, also supplied from Roger Lampert as corresponding to 11.2 lumens per watt
		ID: incandescent-energy-consumption
		CODSIEB
		*/
		public static final double INCANDESCENT_EFFICIENCY = 6.8139; // watts		
		
		public double getMultiplier(LightType lightType) {
			return factor[lightType.ordinal()];
		}
	}

	public enum ECAppliances {
		SAP2012, BREDEM2012, PRODUCTS_POLICY
	}

	public enum ECCookers {
		SAP2012, BREDEM2012, PRODUCTS_POLICY
	}

	public enum ECHotWater {
		SAP2012, BREDEM2012
	}

	public enum ECHeating {
		SAP2012, BREDEM2012
	}

	public enum ECOccupancy {
		SAP2012, SCENARIO
	}

	public enum ECCalibration {
		DISABLED, SCENARIO
	}
	
	public enum ECGeneration {
		SCENARIO, SAP2012
	}
	
	public final ECLighting lighting;
	public final ECWeather weather;
	public final ECHeatingSchedule heatingSchedule;
	public final ECUvalues uvalues;
	public final ECAppliances appliances;
	public final ECCookers cookers;
	public final ECHotWater hotWater;
	public final ECHeating heating;
    public final ECOccupancy occupancy;
    public final ECCalibration calibration;
    public final ECGeneration generation;
	
	public EnergyCalculatorType(
			ECLighting lighting, 
			ECWeather weather,
			ECHeatingSchedule heatingSchedule,
			ECUvalues uvalues, 
			ECAppliances appliances,
			ECCookers cookers, 
			ECHotWater hotWater, 
			ECHeating heating,
			ECOccupancy occupancy, 
			ECCalibration calibration,
			ECGeneration generation) {
		super();
		this.lighting = lighting;
		this.weather = weather;
		this.heatingSchedule = heatingSchedule;
		this.uvalues = uvalues;
		this.appliances = appliances;
		this.cookers = cookers;
		this.hotWater = hotWater;
		this.heating = heating;
		this.occupancy = occupancy;
		this.calibration = calibration;
		this.generation = generation;
	}
	
	public static final EnergyCalculatorType BREDEM2012 = 
			new EnergyCalculatorType(
					ECLighting.SAP2012,
					ECWeather.SCENARIO, 
					ECHeatingSchedule.SCENARIO,
					ECUvalues.SCENARIO,
					ECAppliances.BREDEM2012,
					ECCookers.BREDEM2012,
					ECHotWater.BREDEM2012,
					ECHeating.BREDEM2012, 
					ECOccupancy.SCENARIO,
					ECCalibration.SCENARIO,
					ECGeneration.SCENARIO);
	
	public static final EnergyCalculatorType SAP2012 = 
			new EnergyCalculatorType(
					ECLighting.SAP2012,
					ECWeather.SAP2012, 
					ECHeatingSchedule.SAP2012,
					ECUvalues.SAP2012,
					ECAppliances.SAP2012,
					ECCookers.SAP2012,
					ECHotWater.SAP2012,
					ECHeating.SAP2012, 
					ECOccupancy.SAP2012,
					ECCalibration.DISABLED,
					ECGeneration.SAP2012)
			;
	public static final EnergyCalculatorType SAP2012_UVALUES = 
			new EnergyCalculatorType(
					ECLighting.SAP2012,
					ECWeather.SCENARIO, 
					ECHeatingSchedule.SCENARIO,
					ECUvalues.SCENARIO, 
					ECAppliances.BREDEM2012, 
					ECCookers.BREDEM2012, 
					ECHotWater.BREDEM2012, 
					ECHeating.BREDEM2012, 
					ECOccupancy.SCENARIO,
					ECCalibration.SCENARIO,
					ECGeneration.SCENARIO);
	
	public static final EnergyCalculatorType BEIS = 
			new EnergyCalculatorType(
					ECLighting.PRODUCTS_POLICY,
					ECWeather.SCENARIO, 
					ECHeatingSchedule.SCENARIO,
					ECUvalues.SCENARIO, 
					ECAppliances.PRODUCTS_POLICY, 
					ECCookers.PRODUCTS_POLICY, 
					ECHotWater.BREDEM2012, 
					ECHeating.BREDEM2012, 
					ECOccupancy.SCENARIO,
					ECCalibration.SCENARIO,
					ECGeneration.SCENARIO);
			;
	
}
