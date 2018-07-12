package uk.ac.ucl.hideem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Supplier;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import uk.ac.ucl.hideem.IExposure.ExposureBuiltForm;
import uk.ac.ucl.hideem.IExposure.OccupancyType;
import uk.ac.ucl.hideem.IExposure.OverheatingAgeBands;
import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.energycalculator.api.types.BuiltFormType;

public class HealthModule implements IHealthModule {

    private static final Logger log = LoggerFactory.getLogger(HealthModule.class);
    private final Table<IExposure.ExposureBuiltForm, IExposure.VentilationType, List<IExposure>> exposures = HashBasedTable.create();
    private final IExposure overheating = new OverheatingExposure();
    private final Table<Disease.Type, Person.Sex, List<Disease>> healthCoefficients
            = HashBasedTable.create();

    public HealthModule() {
        // read a csv table from the classpath.

        // There are about 8 million CSV libraries for Java, all of
        // which require some awful enterprise factory bean
        // configurator pattern. I have instead taken the liberty of
        // writing a trivial one which is in the CSV.java file.
        // let's say we are reading data.csv, which has three columns,
        // one with built form and the other two with numbers, and we
        // want to make a mapping from built form to the two
        // numbers. One way of doing this is to use an array indexed
        // on built form ordinals and then on the other column. A more
        // complex thing would require some better structure.
        //Read the exposure coefficients from the csv file
        log.debug("Reading exposure coefficients from: src/main/resources/uk/ac/ucl/hideem/NHM_exposure_coefs");

        try (final CSV.Reader reader = CSV.trimmedReader(bufferedReaderForResource("NHM_exposure_coefs.csv"))) {
            String[] row = reader.read(); // throw away header line, because we know what it is

            while ((row = reader.read()) != null) {
                final CoefficientsExposure e = CoefficientsExposure.readExposure(row);

                if (!exposures.contains(e.builtForm, e.ventType)) {
                    exposures.put(e.builtForm, e.ventType, new ArrayList<IExposure>());
                }

                exposures.get(e.builtForm, e.ventType).add(e);
            }
        } catch (final IOException ex) {
            // problem?
        }

        //Read the health coefficients from the csv file
        log.debug("Reading health coefficients from: src/main/resources/uk/ac/ucl/hideem/NHM_mortality_data.csv");

        //Not yet sure how to get as a resource?
        for (final Map<String, String> row : CSV.mapReader(bufferedReaderForResource("NHM_mortality_data.csv"))) {
            // each row maps to several diseases, so we must break them out
            for (final Disease.Type type : Disease.Type.values()) {
                switch (type) {
                    case copd:
                    case commonmentaldisorder:
                    case asthma1:
                    case asthma2:
                    case asthma3:
                    case overheating:
                        break;
                    default:
                        final Disease d = Disease.readDisease(row.get("age"), row.get("sex"), row.get(type.name()), Double.parseDouble(row.get("all")), row.get("population"), row.get(type.name() + "_ratio"));

                        if (!healthCoefficients.contains(type, d.sex)) {
                            healthCoefficients.put(type, d.sex, new ArrayList<Disease>());
                        }

                        healthCoefficients.get(type, d.sex).add(d);
                }
            }
        }

        //Need to have coefs for CMD and Asthma so the diseases can be calculated later. The values aren't important (not gender/age specific)
        putDummyDisease(Disease.Type.commonmentaldisorder, healthCoefficients);
        putDummyDisease(Disease.Type.copd, healthCoefficients);
        putDummyDisease(Disease.Type.asthma1, healthCoefficients);
        putDummyDisease(Disease.Type.asthma2, healthCoefficients);
        putDummyDisease(Disease.Type.asthma3, healthCoefficients);
        putDummyDisease(Disease.Type.overheating, healthCoefficients);

        // make sure that the coefficients are all in age order.
        for (final List<Disease> coefficientList : healthCoefficients.values()) {
            Collections.sort(coefficientList,
                    new Comparator<Disease>() {
                @Override
                public int compare(final Disease a, final Disease b) {
                    return Integer.compare(a.age, b.age);
                }
            });
        }
    }

    private static void putDummyDisease(final Disease.Type type,
            final Table<Disease.Type, Person.Sex, List<Disease>> out) {
        final List<Disease> theDisease = new ArrayList<>();
        theDisease.add(Disease.readDisease("-1", "FEMALE", "0", 0, "0", "0"));
        // we need to put it in under both male and female.
        out.put(type, Person.Sex.FEMALE, theDisease);
        out.put(type, Person.Sex.MALE, theDisease);
    }

    private BufferedReader bufferedReaderForResource(final String resource) {
        return new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(resource)));
    }

    @SuppressWarnings("unused")
    @Override
    public <T extends HealthOutcome> T effectOf(
            final Supplier<T> factory,
            final double t1,
            final double t2,
            final double p1,
            final double p2,
            final double h1,
            final double h2,
            final BuiltFormType form,
            final double floorArea,
            final RegionType region,
            final int mainFloorLevel,
            final boolean hadWorkingExtractorFans, // per finwhatever
            final boolean hadTrickleVents, // this is cooked up elsewhere
            final boolean hasWorkingExtractorFans, // per finwhatever
            final boolean hasTrickleVents, // this is cooked up elsewhere

            final boolean wasDoubleGlazed,
            final boolean isDoubleGlazed,
            final List<Person> people) {
        final T result = factory.get();
        final int horizon = result.horizon();

        //perform the matching between NHM built form and ventilation and Hideem
        final IExposure.ExposureBuiltForm matchedBuiltForm = mapBuiltForm(form, floorArea);
        final IExposure.VentilationType matchedVentilation = mapVentilation(hadWorkingExtractorFans, hadTrickleVents);
        final IExposure.VentilationType matchedVentilation2 = mapVentilation(hasWorkingExtractorFans, hasTrickleVents);

        //Get the correct exposures coefficients and calculate base and modified exposures for each individual
        boolean smoker = false;
        for (final Person p : people) {
            if (p.smokes == true) {
                smoker = true;
                break;
            }
        }

        final Iterator<IExposure> it1 = exposures.get(matchedBuiltForm, matchedVentilation).iterator();
        final Iterator<IExposure> it2 = exposures.get(matchedBuiltForm, matchedVentilation2).iterator();

        while (it1.hasNext() && it2.hasNext()) {

            //}
            try {
                final IExposure exposure1 = it1.next();
                final IExposure exposure2 = it2.next();

                for (final IExposure.OccupancyType occupancy : IExposure.OccupancyType.values()) {
                    //get the coefs for new ventilation case
                    final double[] coefsVent1 = exposure1.getCoefs(occupancy);

                    //Have to read coeficients from ventialtion type one in as they are different
                    exposure2.modify(coefsVent1,
                            t1, t2,
                            p1, p2,
                            h1, h2,
                            smoker,
                            mainFloorLevel,
                            form,
                            region,
                            wasDoubleGlazed,
                            isDoubleGlazed,
                            occupancy,
                            result);
                }

            } catch (final Exception ex) {
                // problem?
            }
        }

        for (final IExposure.OccupancyType occupancy : IExposure.OccupancyType.values()) {
            // apply the overheating exposure. It applies for all ventilations and built forms
            // so we just need to bash it out here.
            final double[] coefsVent1 = overheating.getCoefs(occupancy);

            overheating.modify(coefsVent1,
                    t1, t2,
                    p1, p2,
                    h1, h2,
                    smoker,
                    mainFloorLevel,
                    form,
                    region,
                    wasDoubleGlazed,
                    isDoubleGlazed,
                    occupancy,
                    result);
        }

        // We need to loop through the people, and determine the
        // health impacts of the different types of disease that have
        // just been computed. The impact for a given person depends
        // on their related occupancy type.
        for (final Person person : people) {
            // we want to compute the survival impact for this person of each type of disease
            diseases:
            for (final Disease.Type disease : Disease.Type.values()) {
                // now we want the coefficients associated with this person's sex
                // and we want them in age order.

                double diseaseImpactSurvival = 1;
                double diseaseBaseSurvival = 1;

                final Iterator<Disease> coefficients = healthCoefficients.get(disease, person.sex).iterator();
                if (!coefficients.hasNext()) {
                    continue diseases;
                }

                Disease currentCoefficients = coefficients.next();
                // now we run over the years that we are considering.
                // most of the coefficient sets apply to a given age
                // however, some of them are the same for all ages,
                // and have an age field of '-1' to signify that.

                // first we must advance to the first row of
                // coefficients which works for this person.
                while ((currentCoefficients.age != -1)
                        && (currentCoefficients.age < person.age)
                        && (coefficients.hasNext())) {
                    currentCoefficients = coefficients.next();
                }

                if (currentCoefficients.age > person.age) {
                    continue diseases;
                }
                years:
                for (int year = 0; year < horizon; year++) {
                    // this is the age they will be N from the horizon
                    final int personAgeInYear = person.age + year;
                    // now we want to know what the disease coefficients say for this age and exposure

                    final double riskChangeTime
                            = (disease == Disease.Type.overheating)
                                    ? result.overheatingRisk(OverheatingAgeBands.forAge(personAgeInYear))
                                    : result.relativeRisk(disease, OccupancyType.forAge(personAgeInYear));

                    if (currentCoefficients.age == -1
                            || currentCoefficients.age >= personAgeInYear) {
                        // it is a special disease, which doesn't change over time, or this is the right year.

                        // Compute the different kinds of QALYs in year. The methods are different per disease.
                        // I guess in a future pass of tidying up this should be done in the disease definitions.
                        final double deaths;
                        final double mortalityChange;

                        {
                            final double qaly[] = calculateQaly(disease, currentCoefficients, riskChangeTime,
                                    diseaseImpactSurvival, diseaseBaseSurvival, year);
                            deaths = qaly[0];
                            mortalityChange = qaly[1];
                            diseaseImpactSurvival = qaly[2];
                            diseaseBaseSurvival = qaly[3];
                        }

                        double morbidityQalys = 0;
                        double cost = 0;

                        switch (disease) {
                            case overheating:
                                break;
                            case copd:
                                final double[] copdImp = calculateCOPDQaly(riskChangeTime, personAgeInYear, year);
                                cost = copdImp[0] * Constants.COST_PER_CASE(disease);
                                morbidityQalys = copdImp[1];
                                break;
                            case commonmentaldisorder:
                                final double[] cmdImp = calculateCMDQaly(riskChangeTime, personAgeInYear, year);
                                cost = cmdImp[0] * Constants.COST_PER_CASE(disease);
                                morbidityQalys = cmdImp[1];
                                break;
                            case asthma1:
                            case asthma2:
                            case asthma3:
                                final double[] asthmaImp = calculateAsthmaQaly(disease, riskChangeTime, personAgeInYear, year);
                                cost = asthmaImp[0] * Constants.COST_PER_CASE(disease);
                                morbidityQalys = asthmaImp[1];
                                break;
                            default:
                                morbidityQalys = mortalityChange * currentCoefficients.morbidity;
                                cost = Constants.INCIDENCE(disease, personAgeInYear, person.sex) * (deaths) * Constants.COST_PER_CASE(disease); // TODO is this correct?
                                break;
                        }

                        //Added to output qalys
                        result.setQaly(disease, mortalityChange);
                        result.setMorbQaly(disease, morbidityQalys);
                        result.setCost(disease, cost);

                        // TODO what API here? do we want person specific info?
                        result.addEffects(disease, year, person, mortalityChange, morbidityQalys, cost);

                        // move to next defined year of coefficients:
                        if (currentCoefficients.age != -1 && coefficients.hasNext()) {
                            currentCoefficients = coefficients.next();
                        }
                    }
                }
            }
        }

        return result;
    }

    //Temperature Calculations using Hamilton relation
    private static double calcSIT(final double eValue) {
        final double livingRoomSIT = (Constants.LR_SIT_CONSTS[4] + (Constants.LR_SIT_CONSTS[3] * Math.pow(eValue, 1)) + (Constants.LR_SIT_CONSTS[2] * Math.pow(eValue, 2))
                + (Constants.LR_SIT_CONSTS[1] * Math.pow(eValue, 3)) + (Constants.LR_SIT_CONSTS[0] * Math.pow(eValue, 4)));
        final double bedRoomSIT = (Constants.BR_SIT_CONSTS[4] + (Constants.BR_SIT_CONSTS[3] * Math.pow(eValue, 1)) + (Constants.BR_SIT_CONSTS[2] * Math.pow(eValue, 2))
                + (Constants.BR_SIT_CONSTS[1] * Math.pow(eValue, 3)) + (Constants.BR_SIT_CONSTS[0] * Math.pow(eValue, 4)));
        final double averageSIT = ((livingRoomSIT + bedRoomSIT) / 2);

        return averageSIT;
    }

    @Override
    public double getInternalTemperature(final double specificHeat,
            double efficiency) {
        if (efficiency <= 0) {
            efficiency = 1;
        }
        final double eValue = specificHeat / efficiency;

        return calcSIT(eValue);
    }

    //Methods to map the input built form and ventilation of NHM to that in Hideem.
    //Not sure if this should be here or elsewhere but works for now
    private IExposure.ExposureBuiltForm mapBuiltForm(final BuiltFormType form, final double floorArea) {
        //initialisation
        IExposure.ExposureBuiltForm matchedBuiltForm = null;

        switch (form) {
            case ConvertedFlat:
            case PurposeBuiltLowRiseFlat:
                if (floorArea > 50) {
                    matchedBuiltForm = ExposureBuiltForm.Flat1;
                } else {
                    matchedBuiltForm = ExposureBuiltForm.Flat2;
                }
                break;
            case PurposeBuiltHighRiseFlat:
                matchedBuiltForm = ExposureBuiltForm.Flat3;
                break;
            case Bungalow:
                matchedBuiltForm = ExposureBuiltForm.House5;
                break;
            case EndTerrace:
                matchedBuiltForm = ExposureBuiltForm.House1;
                break;
            case MidTerrace:
                if (floorArea > 131) {
                    matchedBuiltForm = ExposureBuiltForm.House4;
                } else {
                    matchedBuiltForm = ExposureBuiltForm.House2;
                }
                // house 6 is not used
                break;
            case SemiDetached:
                matchedBuiltForm = ExposureBuiltForm.House3;
                break;
            default:
                matchedBuiltForm = ExposureBuiltForm.House7;
                break;

        }

        return matchedBuiltForm;
    }

    //Match ventilation
    private IExposure.VentilationType mapVentilation(final boolean hasWorkingExtractorFans, final boolean hasTrickleVents) {
        //initialisation
        IExposure.VentilationType matchedVentilation = null;
        //Get the ventilation
        if (!hasWorkingExtractorFans && !hasTrickleVents) {
            matchedVentilation = IExposure.VentilationType.NOTE;
        } else if (hasWorkingExtractorFans && !hasTrickleVents) {
            matchedVentilation = IExposure.VentilationType.T;
        } else if (!hasWorkingExtractorFans && hasTrickleVents) {
            matchedVentilation = IExposure.VentilationType.E;
        } else {
            matchedVentilation = IExposure.VentilationType.TE;
        }

        return matchedVentilation;
    }

    private double[] calculateQaly(final Disease.Type disease, final Disease coefficients, final double riskChangeTime,
            final double impactStartPop, final double baseStartPop, final int year) {
        //Calculations based on Miller, Life table for quantitative impact assessment, 2003
        double base = coefficients.allHazard;
        double impact = base - coefficients.hazard;

        if (disease == Disease.Type.overheating) {
            //For overheating age dependence is in the RR and there are no mortality stats so we just use total pop (deaths/pop)
            base = Constants.TOT_BASE;
            impact = base + Constants.OVERHEAT_HAZARD * (riskChangeTime - 1); //roughly 2000 overheating excess deaths (Heatwave plan for England 2015 (NHS))
        } else if (disease == Disease.Type.wincardiovascular || disease == Disease.Type.wincerebrovascular || disease == Disease.Type.winmyocardialinfarction) {
            impact += coefficients.hazard * riskChangeTime;
        } else {
            //Disease impact depends on increased or decreased risk
            if (riskChangeTime >= 1d) {
                final NormalDistribution normDist = new NormalDistribution(Constants.TIME_FUNCTION(disease)[0], Constants.TIME_FUNCTION(disease)[1]);
                impact += coefficients.hazard * (1 + (riskChangeTime - 1) * normDist.cumulativeProbability(year + 1));
            } else {
                impact += coefficients.hazard * (1 - (1 - riskChangeTime) * (1 - Math.exp(-(year + 1) * Constants.TIME_FUNCTION(disease)[2])));
            }
        }

        final double impactEndPop = impactStartPop * ((2 - impact) / (2 + impact));
        final double baseEndPop = baseStartPop * ((2 - base) / (2 + base));

        final double deaths = impactStartPop - impactEndPop;
        final double lifeYears = impactStartPop - 0.5 * deaths;

        final double baseDeaths = baseStartPop - baseEndPop;
        final double baselifeYears = baseStartPop - 0.5 * baseDeaths;

        //doing change in the number of deaths instead
        final double deltaDeaths = deaths - baseDeaths;
        final double deltaQalys = lifeYears - baselifeYears;

        return new double[]{deltaDeaths, deltaQalys, impactEndPop, baseEndPop};
    }

    private double[] calculateCMDQaly(final double riskChangeTime, final int age, final int year) {
        double impact = 0;

        if (age >= 16) {
            //0.25 factor for winter months only
            impact = (1 - riskChangeTime) * (1 - Constants.WEIGHT_CMD) * 0.25 * Constants.PREV_CMD;
        }

        final double timeFunct = 1 / Math.pow(2.5, year);

        final double qalys = impact * timeFunct;
        final double cases = (age >= 16) ? Constants.PREV_CMD * (riskChangeTime - 1) * timeFunct * 0.25 : 0;

        final double vals[] = {cases, qalys};

        return vals;
    }

    private double[] calculateCOPDQaly(final double riskChangeTime, final int age, final int year) {
        double impact = 0;

        if (age >= 45) {
            impact = (1 - riskChangeTime) * (1 - Constants.WEIGHT_COPD) * 0.25 * Constants.PREV_COPD;
        }

        //reduces to 50% then 25%
        double timeFunct = 1 / Math.pow(2, year);
        //Constant 25% there after
        if (year >= 3) {
            timeFunct = 1 / Math.pow(2, 2);
        }

        final double qalys = impact * timeFunct;
        final double cases = (age >= 45) ? Constants.PREV_COPD * (riskChangeTime - 1) * timeFunct * 0.25 : 0;

        final double vals[] = {cases, qalys};

        return vals;
    }

    /**
     * @param riskChangeTime
     * @param age
     * @param year
     * @return an array containing [cases affected, qalys]
     */
    @SuppressWarnings("incomplete-switch")
    private double[] calculateAsthmaQaly(final Disease.Type athsmaType, final double riskChangeTime, final int age, final int year) {
        double impact = 0;
        double cases = 0;

        switch (athsmaType) {
            case asthma1:
                impact = (age <= 15) ? ((1 - Constants.WEIGHT_ASTHMA1) * Constants.PREV_ASTHMA1) * (1 - riskChangeTime) : 0;
                cases = (age <= 15) ? Constants.PREV_ASTHMA1 * (riskChangeTime - 1) : 0;
                break;
            case asthma2:
                impact = (age <= 15) ? ((1 - Constants.WEIGHT_ASTHMA2) * Constants.PREV_ASTHMA2) * (1 - riskChangeTime) : 0;
                cases = (age <= 15) ? Constants.PREV_ASTHMA2 * (riskChangeTime - 1) : 0;
                break;
            case asthma3:
                impact = (age <= 15) ? ((1 - Constants.WEIGHT_ASTHMA3) * Constants.PREV_ASTHMA3) * (1 - riskChangeTime) : 0;
                cases = (age <= 15) ? Constants.PREV_ASTHMA3 * (riskChangeTime - 1) : 0;
                break;
        }

        final double timeFunct = 1 / Math.pow(1.3, year);

        final double vals[] = {cases * timeFunct, impact * timeFunct};

        return vals;

    }

    private static final int CTC_COUNT = 500;
    static final double[] CTC_COST = new double[CTC_COUNT];
    static final double[] CTC_TEMPERATURE = new double[CTC_COUNT];

    static {
        final double baseExternalTemperature = 5;
        final double basePrice = 0.1;
        final double baseEValue = 100;
        final double heatingSeason = (270 * 24) / 1000;

        final double minimumPrice = 0d;
        final double maximumPrice = 2d;

        for (int i = 0; i < CTC_COUNT; i++) {
            final double price = minimumPrice + i * (maximumPrice - minimumPrice) / (double) CTC_COUNT;

            // this is the strong assumption: e-value is really a proxy for cost, so if we double the cost
            // it would be the same as doubling the e-value
            final double temperature = calcSIT(baseEValue * price / basePrice);

            // now we have a temperature at the given price, we can cook up a 'cost' using the simple model
            // for cost below
            final double costAtTemp = Math.max(0,
                    (temperature - baseExternalTemperature)
                    * // delta T
                    price
                    * // cost per kwh
                    baseEValue
                    * // watts per delta T
                    heatingSeason); // heating hours per year
            // now we have a temperature and a related cost!
            CTC_TEMPERATURE[CTC_COUNT - (i + 1)] = temperature;
            CTC_COST[i] = costAtTemp;
        }
    }

    /*
     * Determine the impact on the SIT of a certain rebate. This requires the conversion of the
     * SIT computation into a cost / temperature curve using particular assumptions.
     *
     * This is done on startup in the static block above; the rebate calculation then
     * interpolates the base temperature into the cost/temperature function to produce a base cost,
     * modifies the cost with the rebate, and then reads back off to get a revised temperature.
     *
     * This does not reflect the temperature -> cost function; we really want to solve the
     * pair of functions temperature -> cost (supply) and cost -> temperature (demand) for
     * an equilibrium, but that is too hard to do today.
     */
    @Override
    public double getRebateDeltaTemperature(final double baseTemperature, final double rebate) {
        // the curve is descending (cost goes up, temperature goes down)
        // however, to make this bit faster, I have stored temperature in reverse order.
        // this means both arrays are amenable to the fast binary search

        final double baseCost = interpolate(baseTemperature, CTC_COUNT, CTC_TEMPERATURE, CTC_COST);
        final double rebatedCost = baseCost - rebate;
        // so we have a new cost - now we need to turn that into a temperature
        final double rebatedTemperature = interpolate(rebatedCost, CTC_COUNT, CTC_COST, CTC_TEMPERATURE);
        return rebatedTemperature - baseTemperature;
    }

    protected static double interpolate(final double x, final int count, final double[] forwards, final double[] backwards) {
        final int baseIndex = Arrays.binarySearch(forwards, x);
        final double baseValue;
        if (baseIndex < 0) {
            final int upper = -(baseIndex + 1);
            if (upper == 0) {
                // this means that we are below the minimum temperature
                // so we ought to be at the maximum cost
                baseValue = backwards[count - 1];
            } else if (upper == count) {
                // the converse of the above
                baseValue = backwards[0];
            } else {
                // so we are between two temperatures
                final double upperX = forwards[upper];
                final double lowerX = forwards[upper - 1];
                // note that the upper cost will be less than the lower cost
                // this is because the curve points downward.
                final double upperY = backwards[count - (upper + 1)];
                final double lowerY = backwards[count - (upper)];

                // a linear interpolation
                baseValue = (lowerY + (upperY - lowerY)
                        * (x - lowerX) / (upperX - lowerX));
            }
        } else {
            baseValue = backwards[count - (baseIndex + 1)]; // count and temp are in reverse order.
        }
        return baseValue;
    }
}
