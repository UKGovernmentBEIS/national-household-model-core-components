package uk.org.cse.nhm.simulator.state.dimensions.energy;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.inject.Named;

import org.pojomatic.annotations.AutoProperty;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculationResult;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculator;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.ISeasonalParameters;
import uk.org.cse.nhm.energycalculator.api.impl.DailyHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.impl.ExternalParameters;
import uk.org.cse.nhm.energycalculator.api.impl.SeasonalParameters;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.SiteExposureType;
import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.util.EObjectWrapper;
import uk.org.cse.nhm.hom.people.People;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.state.dimensions.FuelServiceTable;
import uk.org.cse.nhm.simulator.state.dimensions.behaviour.IHeatingBehaviour;
import uk.org.cse.nhm.simulator.state.dimensions.weather.IWeather;
import uk.org.cse.nhm.types.MonthType;

/**
 * Glue that runs a energy calculator from within the simulator.
 * 
 * @author hinton
 *
 */
public class EnergyCalculatorBridge implements IEnergyCalculatorBridge {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EnergyCalculatorBridge.class);
	public static final double[] DECLINATION = { 
		-0.36128316, -0.22340214,
			-0.03141593, 0.17104227, 0.3281219, 0.40317106, 0.3700098,
			0.23911011, 0.05061455, -0.15184364, -0.32114058, -0.40142573 
	};

	private static final IHeatingSchedule OFF_SCHEDULE = new DailyHeatingSchedule();
	public static final String CACHE_SIZE = "CACHE_SIZE";
	
	private LoadingCache<Wrapper, Result> cache;
	private long requests;
	
	private static class Result implements IPowerTable {
		private static final float WATT_DAYS_TO_KWH = 24f / 1000f;

        private final FuelServiceTable table;

		private final float specificHeatLoss;
        private final float airChangeRate;
		private final float meanInternalTemperature;
        private final float[][] heatLoad = new float[MonthType.values().length][2];
        private final float hotWaterDemand;
        private final float primaryHeatDemand;
        private final float secondaryHeatDemand;

        Result(final IEnergyCalculationResult[] months) {
            float specificHeatLoss = 0;
            float airChangeRate = 0;
            float meanInternalTemperature = 0;
            
            final FuelServiceTable.Builder builder = FuelServiceTable.builder();
            
            float hotWaterDemandAccum = 0;
            float primaryHeatDemandAccum = 0;
            float secondaryHeatDemandAccum = 0;

            for (final MonthType m : MonthType.values()) {
                final IEnergyCalculationResult result = months[m.ordinal()];
                final IEnergyState energyState = result.getEnergyState();
                
                final double wattsPerDayToKWHPerMonth = m.getStandardDays() * WATT_DAYS_TO_KWH;
                
                hotWaterDemandAccum += wattsPerDayToKWHPerMonth * energyState.getTotalSupply(
                		EnergyType.DemandsHOT_WATER, 
                		ServiceType.WATER_HEATING);
                
                primaryHeatDemandAccum += wattsPerDayToKWHPerMonth * energyState.getTotalSupply(
                		EnergyType.DemandsHEAT, 
                		ServiceType.PRIMARY_SPACE_HEATING);
                
                secondaryHeatDemandAccum += wattsPerDayToKWHPerMonth * energyState.getTotalSupply(
                		EnergyType.DemandsHEAT, 
                		ServiceType.SECONDARY_SPACE_HEATING);
                
                for (final EnergyType et : EnergyType.allFuels) {
                    final FuelType ft = FuelType.of(et);
                    for (final ServiceType st : ServiceType.values()) {
                        final double kWhUsed = wattsPerDayToKWHPerMonth
							* (energyState.getTotalDemand(et, st) - energyState.getTotalSupply(et, st)); 
					
                        builder.add(ft, st, kWhUsed);
					
                        if (Double.isInfinite(kWhUsed) || Double.isNaN(kWhUsed)) {
                            log.error("Used {} kWh of {} for {} in {}", new Object[] {kWhUsed, ft, st, m});
                        }
                    }
                }
            

                specificHeatLoss += result.getHeatLosses().getSpecificHeatLoss() * m.getStandardDays();

                meanInternalTemperature += 
                    m.getStandardDays() * 
                    result.getEnergyState().getTotalSupply(EnergyType.HackMEAN_INTERNAL_TEMPERATURE);

                airChangeRate += result.getHeatLosses().getAirChangeRate() * m.getStandardDays();

                final float convert = m.getStandardDays() * WATT_DAYS_TO_KWH;
                heatLoad[m.ordinal()][0] = (float) (convert * result.getEnergyState().getTotalDemand(EnergyType.DemandsHEAT));
                heatLoad[m.ordinal()][1] = (float) (convert * result.getEnergyState().getTotalDemand(EnergyType.DemandsHOT_WATER));
            }

            this.specificHeatLoss = specificHeatLoss;
            this.airChangeRate = airChangeRate;
            this.meanInternalTemperature = meanInternalTemperature;
            
            this.primaryHeatDemand = primaryHeatDemandAccum;
            this.secondaryHeatDemand = secondaryHeatDemandAccum;
            this.hotWaterDemand = hotWaterDemandAccum;

            this.table = builder.build();
        }
        
		@Override
		public float getFuelUseByEnergyService(final ServiceType es, final FuelType ft) {
            return table.get(ft, es);
		}
		
		@Override
		public float getFuelUseByEnergyService(List<ServiceType> es, FuelType ft) {
			float accum = 0f;
			for (ServiceType serviceType : es) {
				accum += getFuelUseByEnergyService(serviceType, ft);
			}
			return accum;
		}

		@Override
		public float getPowerByFuel(final FuelType ft) {
            return table.get(ft);
		}
		
		@Override
		public float getSpecificHeatLoss() {
			return specificHeatLoss / 365f;
		}

		@Override
		public float getAirChangeRate() {
            return airChangeRate / 365f;
		}

        @Override
		public float getMeanInternalTemperature() {
			return meanInternalTemperature / 365f;
		}

        @Override
        public float getWeightedHeatLoad(final double[] weights, final boolean space, final boolean water) {
    	    float acc = 0;
    	    final double hw = space ? 1 : 0;
    	    final double ww = water ? 1 : 0;
            for (int i = 0; i<heatLoad.length && i < weights.length; i++) {
            	acc += weights[i] * (heatLoad[i][0] * hw + heatLoad[i][1] * ww);
            }
            return acc;
        }

		@Override
		public float getPrimaryHeatDemand() {
			return primaryHeatDemand;
		}

		@Override
		public float getSecondaryHeatDemand() {
			return secondaryHeatDemand;
		}

		@Override
		public float getHotWaterDemand() {
			return hotWaterDemand;
		}
	}
	
	@AutoProperty
	private static class Wrapper implements IEnergyCalculatorHouseCase {
		final StructureModel structure;
		final EObjectWrapper<ITechnologyModel> technology;
		public final IWeather weather;
		public final double people;
		private final int buildYear;
		private final double latitudeRadians;
		private final IHeatingBehaviour heatingBehaviour;
		private final SiteExposureType siteExposure;
		private final int hash;
		
		public Wrapper(final StructureModel structure, final ITechnologyModel technology, final BasicCaseAttributes attributes, final IWeather weather, final double npeople, final IHeatingBehaviour behaviour) {
			this.structure = structure;
			this.technology = new EObjectWrapper<ITechnologyModel>(technology);
			this.weather = weather;
			this.people = npeople;
			this.buildYear = attributes.getBuildYear();
			this.latitudeRadians = attributes.getRegionType().getLatitudeRadians();
			this.siteExposure = attributes.getSiteExposure();
			this.heatingBehaviour = behaviour;
			this.hash = _hashCode();
		}

		@Override
		public void accept(final IConstants constants, final IEnergyCalculatorParameters parameters, final IEnergyCalculatorVisitor visitor) {
			structure.accept(visitor);
			technology.unwrap().accept(constants, parameters, visitor, new AtomicInteger(), null);
		}

		@Override
		public double getFloorArea() {
			return structure.getFloorArea();
		}

		@Override
		public double getLivingAreaProportionOfFloorArea() {
			return structure.getLivingAreaProportionOfFloorArea();
		}

		@Override
		public double getInterzoneSpecificHeatLoss() {
			return structure.getInterzoneSpecificHeatLoss();
		}

		@Override
		public double getHouseVolume() {
			return structure.getVolume();
		}

		@Override
		public int getNumberOfStoreys() {
			return structure.getNumberOfStoreys();
		}

		@Override
		public boolean hasDraughtLobby() {
			return structure.hasDraughtLobby();
		}

		@Override
		public double getZoneTwoHeatedProportion() {
			/*
			BEISDOC
			NAME: Zone 2 Heated Proportion
			DESCRIPTION: The fraction of Zone 2 which is heated. This should be a number between 0 and 1. A dwelling with central heating usually has the value 1. 
			TYPE: value
			UNIT: Dimensionless
			BREDEM: Section 7 fz2htd Input
			NOTES: Always 100% in SAP 2012 mode.
			ID: zone-2-heated-proportion
			CODSIEB
			*/
			switch(heatingBehaviour.getEnergyCalculatorType()) {
			case SAP2012:
				return 1d;
			case BREDEM2012:
				return structure.getZoneTwoHeatedProportion();
			default:
				throw new RuntimeException("Unknown energy calculator type while working out zone 2 heated proportion " + heatingBehaviour.getEnergyCalculatorType());
			}
		}

		@Override
		public int getBuildYear() {
			return buildYear;
		}

		@Override
		public double getDraughtStrippedProportion() {
			return structure.getDraughtStrippedProportion();
		}

		@Override
		public int getNumberOfShelteredSides() {
			return structure.getNumberOfShelteredSides();
		}
		
		@Override
		public SiteExposureType getSiteExposure() {
			return siteExposure;
		}
		
		@Override
		public int hashCode() {
			return this.hash;
		}
		
		protected int _hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + buildYear;
			result = prime * result + ((heatingBehaviour == null) ? 0 : heatingBehaviour.hashCode());
			long temp;
			temp = Double.doubleToLongBits(latitudeRadians);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(people);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			result = prime * result + ((siteExposure == null) ? 0 : siteExposure.hashCode());
			result = prime * result + ((structure == null) ? 0 : structure.hashCode());
			result = prime * result + ((technology == null) ? 0 : technology.hashCode());
			result = prime * result + ((weather == null) ? 0 : weather.hashCode());
			return result;
		}
		
		/**
		 * equals generated using Eclipse source menu.
		 * Includes all fields except "hash".
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Wrapper other = (Wrapper) obj;
			if (buildYear != other.buildYear)
				return false;
			if (heatingBehaviour == null) {
				if (other.heatingBehaviour != null)
					return false;
			} else if (!heatingBehaviour.equals(other.heatingBehaviour))
				return false;
			if (Double.doubleToLongBits(latitudeRadians) != Double.doubleToLongBits(other.latitudeRadians))
				return false;
			if (Double.doubleToLongBits(people) != Double.doubleToLongBits(other.people))
				return false;
			if (siteExposure != other.siteExposure)
				return false;
			if (structure == null) {
				if (other.structure != null)
					return false;
			} else if (!structure.equals(other.structure))
				return false;
			if (technology == null) {
				if (other.technology != null)
					return false;
			} else if (!technology.equals(other.technology))
				return false;
			if (weather == null) {
				if (other.weather != null)
					return false;
			} else if (!weather.equals(other.weather))
				return false;
			return true;
		}

		public double getLatitudeRadians() {
			return latitudeRadians;
		}
	}
	
	@Inject
	public EnergyCalculatorBridge(final IEnergyCalculator calculator, @Named(CACHE_SIZE) final int cacheSize) {
		this.cache = CacheBuilder.newBuilder().
				softValues().
				recordStats().
				maximumSize(cacheSize).
				expireAfterAccess(20, TimeUnit.MINUTES).build(
					new CacheLoader<Wrapper, Result>() {
						@Override
						public Result load(final Wrapper key) throws Exception {
							final ExternalParameters parameters = new ExternalParameters();
							
							parameters.setZoneOneDemandTemperature(key.heatingBehaviour.getLivingAreaDemandTemperature());
							
							if (key.heatingBehaviour.getTemperatureDifference().isPresent()) {
								parameters.setInterzoneTemperatureDifference(key.heatingBehaviour.getTemperatureDifference().get());
							} else {
								parameters.setZoneTwoDemandTemperature(key.heatingBehaviour.getSecondAreaDemandTemperature().get());
							}
							
							parameters.setNumberOfOccupants(key.people); 
					
							//TODO get tariff type from somewhere
							parameters.setTarrifType(ElectricityTariffType.ECONOMY_7);
							
							final ISeasonalParameters[] climate = new ISeasonalParameters[MonthType.values().length];
							
							for (final MonthType m : MonthType.values()) {
								final double externalTemperature = key.weather.getExternalTemperature(m);
								final double heatingThresholdTemperature = key.heatingBehaviour.getHeatingOnThreshold();
								
								final boolean heatingShouldBeOn = externalTemperature < heatingThresholdTemperature;
								
								climate[m.ordinal()] = new SeasonalParameters(
										m.ordinal()+1, DECLINATION[m.ordinal()],
										externalTemperature,
										key.weather.getWindSpeed(m),
										key.weather.getHorizontalSolarFlux(m), 
										key.getLatitudeRadians(),
										
										heatingShouldBeOn ? key.heatingBehaviour.getHeatingSchedule() : OFF_SCHEDULE,
												
										Optional.<IHeatingSchedule>absent()
										);
							}
							
							parameters.setCalculatorType(key.heatingBehaviour.getEnergyCalculatorType());

                            return new Result(calculator.evaluate(key, parameters, climate));
						}
					}
				);
	}

	@Override
	public IPowerTable evaluate(
			final IWeather weather, 
			final StructureModel structure, 
			final ITechnologyModel technology, 
			final BasicCaseAttributes attributes, 
			final People people,
			final IHeatingBehaviour behaviour) {
		Preconditions.checkNotNull(weather, "Weather was null");
		Preconditions.checkNotNull(structure, "Structure was null");
		Preconditions.checkNotNull(technology, "Technology was null");
		Preconditions.checkNotNull(attributes, "Basic attributes were null");
		Preconditions.checkNotNull(people, "People was null");
		Preconditions.checkNotNull(behaviour, "Heating behaviour was null");
		
		requests++;
		final Wrapper w = new Wrapper(structure, technology, attributes, weather, people.getOccupancy(), behaviour);
		try {
			return cache.get(w);
		} catch (final Exception e) {
			throw new RuntimeException("Exception in energy calculator: " + e.getMessage() + " for "
					+attributes.getAacode()
					, e);
		} finally {
			if (requests % 10000 == 0) {
				log.debug("Cache details: {}", cache.stats());
			}
		}
	}
}
