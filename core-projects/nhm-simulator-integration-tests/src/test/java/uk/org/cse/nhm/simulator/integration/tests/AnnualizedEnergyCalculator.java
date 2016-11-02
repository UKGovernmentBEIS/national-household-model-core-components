package uk.org.cse.nhm.simulator.integration.tests;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculationResult;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculator;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.impl.DailyHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.impl.ExternalParameters;
import uk.org.cse.nhm.energycalculator.api.impl.SeasonalParameters;
import uk.org.cse.nhm.energycalculator.api.types.AreaType;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.energycalculator.api.types.RegionType;

/**
 * @author hinton
 *
 */
public class AnnualizedEnergyCalculator {
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(AnnualizedEnergyCalculator.class);
	private static final int[] days = {
		31,28,31, 30, 31, 30, 31, 31, 30, 31, 30, 31
	};

	private static final double[] declination = { 
		-0.36128316, -0.22340214,
			-0.03141593, 0.17104227, 0.3281219, 0.40317106, 0.3700098,
			0.23911011, 0.05061455, -0.15184364, -0.32114058, -0.40142573 
	};

	private IEnergyCalculator calculator;
	
	private RegionalMonthlyTable externalTemperature;
	private RegionalMonthlyTable windSpeed;
	private RegionalMonthlyTable horizontalSolarFlux;
	
	private boolean[] heatingMonths;
	
	private double interzoneTemperatureDifference;

	private final IHeatingSchedule heatingOff = new DailyHeatingSchedule();

	private IHeatingSchedule heatingSchedule;

	private double zoneOneDemandTemperature;
	
	
	
	public IEnergyCalculator getCalculator() {
		return calculator;
	}

	public void setCalculator(final IEnergyCalculator calculator) {
		this.calculator = calculator;
	}

	public boolean[] getHeatingMonths() {
		return heatingMonths;
	}

	public void setHeatingMonths(final boolean[] heatingMonths) {
		this.heatingMonths = heatingMonths;
	}

	public IHeatingSchedule getHeatingSchedule() {
		return heatingSchedule;
	}

	public void setHeatingSchedule(final IHeatingSchedule heatingSchedule) {
		this.heatingSchedule = heatingSchedule;
	}

	public RegionalMonthlyTable getExternalTemperature() {
		return externalTemperature;
	}

	public void setExternalTemperature(final RegionalMonthlyTable externalTemperature) {
		this.externalTemperature = externalTemperature;
	}

	public RegionalMonthlyTable getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(final RegionalMonthlyTable windSpeed) {
		this.windSpeed = windSpeed;
	}

	public RegionalMonthlyTable getHorizontalSolarFlux() {
		return horizontalSolarFlux;
	}

	public void setHorizontalSolarFlux(final RegionalMonthlyTable horizontalSolarFlux) {
		this.horizontalSolarFlux = horizontalSolarFlux;
	}

	public double getInterzoneTemperatureDifference() {
		return interzoneTemperatureDifference;
	}

	public void setInterzoneTemperatureDifference(
			final double interzoneTemperatureDifference) {
		this.interzoneTemperatureDifference = interzoneTemperatureDifference;
	}

	public Result evaluate(final SurveyCase surveyCase) {
		final Map<ServiceType, Map<EnergyType, Double>> fuelUse = createEmptyResult();
		final RegionType region = surveyCase.getBasicAttributes().getRegionType();
		double totalInternalTemperature = 0;
		double totalUnadjTemperature = 0;
		double totalHeatLoss = 0;

		double totalVentilationLoss = 0;

		double totalBridgingLoss = 0;
		
		double totalHeatDemand = 0;
		double totalHotWaterDemand = 0;
		
		Map<AreaType, Double> areas = null;
		Map<AreaType, Double> heatLosses = null;
		
		final ExternalParameters parameters = new ExternalParameters();
		parameters.setInterzoneTemperatureDifference(interzoneTemperatureDifference);
		parameters.setNumberOfOccupants(surveyCase.getPeople().getOccupancy()); 
		parameters.setTarrifType(ElectricityTariffType.ECONOMY_7); //TODO get tarriff type from somewhere
		parameters.setZoneOneDemandTemperature(zoneOneDemandTemperature);

		surveyCase.getStructure().setZoneTwoHeatedProportion(1d);
				
		
		final SeasonalParameters[] seasons = new SeasonalParameters[12];
		
		// set monthly varying parameters
		for (int month = 0; month < 12; month++) {
			seasons[month] = new SeasonalParameters(month+1, declination[month],
					externalTemperature.get(region, month), windSpeed.get(
							region, month), horizontalSolarFlux.get(region,
							month), region.getLatitudeRadians(),
					heatingMonths[month] ? heatingSchedule : heatingOff, Optional.<IHeatingSchedule>absent());
		}
					
			
		final IEnergyCalculationResult[] results = calculator.evaluate(surveyCase, parameters, seasons);
		int month = 0;
		for (final IEnergyCalculationResult result : results) {
			final IEnergyState es = result.getEnergyState();
			final double milliHoursInMonth = 24 * days[month] / 1000d;
			
			for (final ServiceType st : ServiceType.values()) {
				for (final EnergyType et : EnergyType.allFuels) {
					final double wattage = es.getTotalDemand(et, st);
					final double kWh = wattage * milliHoursInMonth;
					if (kWh > 0) {
						if (fuelUse.get(st)==null)fuelUse.put(st, new HashMap<EnergyType, Double>());
						final Double d = fuelUse.get(st).get(et);
						fuelUse.get(st).put(et, kWh + (d == null ? 0 : d));
					}
				}
			}
			
			totalInternalTemperature += es.getTotalSupply(EnergyType.HackMEAN_INTERNAL_TEMPERATURE) * days[month];
			totalUnadjTemperature += es.getTotalSupply(EnergyType.HackUNADJUSTED_TEMPERATURE) * days[month];
			totalHeatLoss += result.getHeatLosses().getSpecificHeatLoss();
			totalVentilationLoss += result.getHeatLosses().getVentilationLoss();
			totalBridgingLoss += result.getHeatLosses().getThermalBridgeEffect();
			
			totalHeatDemand += (es.getTotalDemand(EnergyType.DemandsHEAT) 
					-es.getTotalSupply(EnergyType.DemandsHEAT, ServiceType.INTERNALS))
					
					* milliHoursInMonth;
			totalHotWaterDemand += (es.getTotalSupply(EnergyType.GainsHOT_WATER_SYSTEM_GAINS) + es.getTotalDemand(EnergyType.DemandsHOT_WATER)) 
					* milliHoursInMonth;
			
			areas = result.getHeatLossAreas();
			heatLosses = result.getHeatLossByArea();
			month++;
		}
		
		return new Result(fuelUse, 
				totalInternalTemperature / 360, 
				totalUnadjTemperature / 360, 
				totalHeatLoss / 12, totalVentilationLoss /12,totalBridgingLoss /12, 
				totalHeatDemand, totalHotWaterDemand,
				
				areas, heatLosses);
	}

	public static class Result {
		public final Map<ServiceType, Map<EnergyType, Double>> energy;
		public final double meanInternalTemperature;
		public final double meanHeatLoss;
		public final Map<AreaType, Double> areas;
		public final Map<AreaType, Double> heatLosses;
		public final double meanVentilationLoss;
		public final double thermalBridgingLoss;
		public final double totalHeatDemand;
		public final double totalHotWaterDemand;
		public final double unadjustedTemperature;
		
		protected Result(final Map<ServiceType, Map<EnergyType, Double>> energy, final double meanInternalTemperature,
				final double unadjTemperature,
				final double meanHeatLoss, final double meanVentilationLoss, final double tbLoss, 
				final double totalHeatDemand, final double totalHotWaterDemand,
				final Map<AreaType, Double> areas, final Map<AreaType, Double> heatLosses) {
			this.energy = energy;
			this.meanInternalTemperature = meanInternalTemperature;
			this.meanHeatLoss = meanHeatLoss;
			this.areas = areas;
			this.heatLosses = heatLosses;
			this.meanVentilationLoss = meanVentilationLoss;
			this.thermalBridgingLoss = tbLoss;
			this.totalHeatDemand = totalHeatDemand;
			this.totalHotWaterDemand = totalHotWaterDemand;
			this.unadjustedTemperature = unadjTemperature;
		}
	}
	
	private Map<ServiceType, Map<EnergyType, Double>> createEmptyResult() {
		final Map<ServiceType, Map<EnergyType, Double>> r = new EnumMap<ServiceType, Map<EnergyType,Double>>(ServiceType.class);
		/*for (final ServiceType st : ServiceType.values()) {
			HashMap<EnergyType, Double> enumMap = new HashMap<EnergyType, Double>();
			r.put(st, enumMap);
			for (final EnergyType et : EnergyType.FuelallFuels()) {
				enumMap.put(et, 0d);
			}
		}*/
		return r;
	}

	public double getZoneOneDemandTemperature() {
		return zoneOneDemandTemperature;
	}

	public void setZoneOneDemandTemperature(final double zoneOneDemandTemperature) {
		this.zoneOneDemandTemperature = zoneOneDemandTemperature;
	}
}
