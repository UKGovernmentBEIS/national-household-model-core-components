package uk.org.cse.nhm.simulator.state.dimensions.energy;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.inject.Named;

import org.pojomatic.annotations.AutoProperty;

import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculationResult;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculationResultWithSteps;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculationSteps;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculator;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.ISeasonalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.IWeather;
import uk.org.cse.nhm.energycalculator.api.impl.BredemExternalParameters;
import uk.org.cse.nhm.energycalculator.api.impl.DailyHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.impl.SAPExternalParameters;
import uk.org.cse.nhm.energycalculator.api.impl.SAPOccupancy;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.MonthType;
import uk.org.cse.nhm.energycalculator.api.types.RegionType.Country;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.SiteExposureType;
import uk.org.cse.nhm.energycalculator.api.types.steps.EnergyCalculationStep;
import uk.org.cse.nhm.energycalculator.impl.BREDEMHeatingSeasonalParameters;
import uk.org.cse.nhm.energycalculator.impl.SAPHeatingSeasonalParameters;
import uk.org.cse.nhm.energycalculator.mode.EnergyCalculatorType;
import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.util.EObjectWrapper;
import uk.org.cse.nhm.hom.people.People;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.guice.EnergyCalculationRequestedSteps;
import uk.org.cse.nhm.simulator.state.dimensions.FuelServiceTable;
import uk.org.cse.nhm.simulator.state.dimensions.behaviour.IHeatingBehaviour;

/**
 * Glue that runs a energy calculator from within the simulator.
 *
 * @author hinton
 *
 */
public class EnergyCalculatorBridge implements IEnergyCalculatorBridge {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EnergyCalculatorBridge.class);

	public static final String CACHE_SIZE = "CACHE_SIZE";

	//TODO get tariff type from somewhere
	private static final ElectricityTariffType tariffType = ElectricityTariffType.ECONOMY_7;

	private final LoadingCache<Wrapper, Result> cache;
	private long requests;

	private static class Result implements IPowerTable {
		private static final float WATT_DAYS_TO_KWH = 24f / 1000f;

        private final FuelServiceTable table;

        private final float specificHeatLoss, fabricHeatLoss, ventilationHeatLoss, thermalBridgingHeatLoss;
        private final float airChangeRate;
        private final float airChangeRateWithoutDeliberate;
		private final float meanInternalTemperature;
        private final float[][] heatLoad = new float[MonthType.values().length][2];
        private final float hotWaterDemand;
        private final float primaryHeatDemand;
        private final float secondaryHeatDemand;
        private final IEnergyCalculationSteps intermediateSteps;

        Result(final IEnergyCalculationResultWithSteps resultWithSteps) {
            this(resultWithSteps.getResults(), resultWithSteps.getSteps());
        }

        Result(final IEnergyCalculationResult[] months, IEnergyCalculationSteps intermediateSteps) {
            this.intermediateSteps = intermediateSteps;
            float specificHeatLoss = 0;
            float fabricHeatLoss = 0;
            float ventilationHeatLoss = 0;
            float thermalBridgingHeatLoss = 0;

            float airChangeRate = 0;
            float airChangeRateWithoutDeliberate = 0;
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

                {
                    final ISpecificHeatLosses losses = result.getHeatLosses();
                    specificHeatLoss        += losses.getSpecificHeatLoss()    * m.getStandardDays();
                    fabricHeatLoss          += losses.getFabricLoss()          * m.getStandardDays();
                    ventilationHeatLoss     += losses.getVentilationLoss()     * m.getStandardDays();
                    thermalBridgingHeatLoss += losses.getThermalBridgeEffect() * m.getStandardDays();
                }

                meanInternalTemperature +=
                    m.getStandardDays() *
                    result.getEnergyState().getTotalSupply(EnergyType.HackMEAN_INTERNAL_TEMPERATURE);

                airChangeRate += result.getHeatLosses().getAirChangeRate() * m.getStandardDays();
                airChangeRateWithoutDeliberate += result.getHeatLosses().getAirChangeExcludingDeliberate() * m.getStandardDays();

                final float convert = m.getStandardDays() * WATT_DAYS_TO_KWH;
                heatLoad[m.ordinal()][0] = (float) (convert * result.getEnergyState().getTotalDemand(EnergyType.DemandsHEAT));
                heatLoad[m.ordinal()][1] = (float) (convert * result.getEnergyState().getTotalDemand(EnergyType.DemandsHOT_WATER));
            }

            this.specificHeatLoss        = specificHeatLoss        / 365f;
            this.fabricHeatLoss          = fabricHeatLoss          / 365f;
            this.ventilationHeatLoss     = ventilationHeatLoss     / 365f;
            this.thermalBridgingHeatLoss = thermalBridgingHeatLoss / 365f;

            this.airChangeRate = airChangeRate;
            this.airChangeRateWithoutDeliberate = airChangeRateWithoutDeliberate;
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
		public float getFuelUseByEnergyService(final List<ServiceType> es, final FuelType ft) {
			float accum = 0f;
			for (final ServiceType serviceType : es) {
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
            return specificHeatLoss;
		}

        @Override
        public float getFabricHeatLoss() {
            return fabricHeatLoss;
        }

        @Override
        public float getVentilationHeatLoss() {
            return ventilationHeatLoss;
        }

        @Override
        public float getThermalBridgingHeatLoss() {
            return thermalBridgingHeatLoss;
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

		@Override
		public float getAirChangeRateWithoutDeliberate() {
			return airChangeRateWithoutDeliberate;
		}

        @Override
        public double readStepAnnual(EnergyCalculationStep step) {
            return intermediateSteps.readStepAnnual(step);
        }

        @Override
        public double readStepMonthly(EnergyCalculationStep step, int month) {
            return intermediateSteps.readStepMonthly(step, month);
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
		private final Country country;
		private final int hash;


		public Wrapper(final StructureModel structure, final ITechnologyModel technology, final BasicCaseAttributes attributes, final IWeather weather, final double npeople, final IHeatingBehaviour behaviour) {
			this.structure = structure;
			this.technology = new EObjectWrapper<ITechnologyModel>(technology);
			this.weather = weather;
			this.buildYear = attributes.getBuildYear();
			this.latitudeRadians = attributes.getRegionType().getLatitudeRadians();
			this.siteExposure = attributes.getSiteExposure();
			this.heatingBehaviour = behaviour;
			this.country = attributes.getRegionType().getCountry();

			switch(behaviour.getEnergyCalculatorType().occupancy) {
			case SAP2012:
				/*
				 * This field is derived from floor area in SAP 2012 mode, so we set it to a dummy value.
				 * See {@link SAPExternalParameters}
				 */
				this.people = SAPOccupancy.calculate(structure.getFloorArea());
				break;
			case SCENARIO:
				this.people = npeople;
				break;
			default:
				throw new UnsupportedOperationException("Unknown energy calculator type when constructing EnergyCalcujlatorBridge.Wrapper " + behaviour.getEnergyCalculatorType());
			}

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
                        SAP_COMPLIANT: SAP mode only
			BREDEM: Section 7 fz2htd Input
                        BREDEM_COMPLIANT: Yes
			NOTES: Always 100% in SAP 2012 mode.
			ID: zone-2-heated-proportion
			CODSIEB
			*/
			switch(heatingBehaviour.getEnergyCalculatorType().heatingSchedule) {
			case SAP2012:
				return 1d;
			case SCENARIO:
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

		public double getLatitudeRadians() {
			return latitudeRadians;
		}

		@Override
		public double getThermalBridgingCoefficient() {
			return structure.getThermalBridgingCoefficient();
		}

		@Override
		public boolean hasReducedInternalGains() {
			switch (heatingBehaviour.getEnergyCalculatorType().heatingSchedule) {
			case SAP2012:
				return false;
			case SCENARIO:
				return structure.hasReducedInternalGains();
			default:
				throw new UnsupportedOperationException("Unknown energy calculator type when trying to determine if we should use reduced internal gains " + heatingBehaviour.getEnergyCalculatorType());
			}
		}

		@Override
		public Country getCountry() {
			return country;
		}

		@Override
		public int hashCode() {
			return this.hash;
		}

		protected int _hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + buildYear;
			result = prime * result + ((country == null) ? 0 : country.hashCode());
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
		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final Wrapper other = (Wrapper) obj;
			if (buildYear != other.buildYear)
				return false;
			if (country != other.country)
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
	}

	@Inject
	public EnergyCalculatorBridge(final IEnergyCalculator calculator, final EnergyCalculationRequestedSteps requestedSteps, @Named(CACHE_SIZE) final int cacheSize) {
		this.cache = CacheBuilder.newBuilder().
				softValues().
				recordStats().
				maximumSize(cacheSize).
				expireAfterAccess(20, TimeUnit.MINUTES).build(
					new CacheLoader<Wrapper, Result>() {
						@Override
						public Result load(final Wrapper key) throws Exception {
							final EnergyCalculatorType mode = key.heatingBehaviour.getEnergyCalculatorType();

							final IEnergyCalculatorParameters parameters = createParameters(key.heatingBehaviour, key.structure.getFloorArea(), key.people);
							final ISeasonalParameters[] climate = new ISeasonalParameters[MonthType.values().length];

							final IWeather weather = mode.weather.getWeather(key.weather);
							final double latitude = mode.weather.getLatitude(key.getLatitudeRadians());

							switch (mode.heatingSchedule) {
							case SAP2012:
								for (final MonthType m : MonthType.values()) {
									climate[m.ordinal()] = new SAPHeatingSeasonalParameters(m, weather, latitude);
								}
								break;
								case SCENARIO:
									for (final MonthType m : MonthType.values()) {
										if (key.heatingBehaviour.getHeatingMonths().contains(m)) {
											climate[m.ordinal()] = new BREDEMHeatingSeasonalParameters(m, weather,
													latitude, key.heatingBehaviour.getHeatingSchedule());
										} else {
											climate[m.ordinal()] = new BREDEMHeatingSeasonalParameters(m, weather,
													latitude, DailyHeatingSchedule.OFF
												);
									}
								}
								break;
							default:
								throw new IllegalArgumentException("Unknown energy calculator type for heating schedule");
							}


                            return new Result(calculator.evaluate(key, parameters, climate, requestedSteps.getRequestedSteps()));
						}

						private IEnergyCalculatorParameters createParameters(final IHeatingBehaviour heatingBehaviour, final double floorArea, final double occupancy) {
							switch (heatingBehaviour.getEnergyCalculatorType().heatingSchedule) {
							case SCENARIO:
								return new BredemExternalParameters(
										heatingBehaviour.getEnergyCalculatorType(),
										tariffType,
										heatingBehaviour.getLivingAreaDemandTemperature(),
										heatingBehaviour.getSecondAreaDemandTemperature(),
										heatingBehaviour.getTemperatureDifference(),
										occupancy
									);
							case SAP2012:
								return new SAPExternalParameters(heatingBehaviour.getEnergyCalculatorType(), tariffType, floorArea);
							default:
								throw new IllegalArgumentException("Unknown energy calculator type when creating energy calculator parameters " + heatingBehaviour.getEnergyCalculatorType());
							}
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
