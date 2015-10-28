package uk.ac.ucl.hideem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Iterator;
import java.util.Collections;
import java.util.Comparator;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.ucl.hideem.IExposure.ExposureBuiltForm;
import uk.ac.ucl.hideem.IExposure.OccupancyType;
import uk.ac.ucl.hideem.IExposure.OverheatingAgeBands;
import uk.ac.ucl.hideem.IExposure.Type;
import uk.ac.ucl.hideem.BuiltForm.Region;

import com.google.common.base.Supplier;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Table;
import com.google.common.collect.HashBasedTable;

public class HealthModule implements IHealthModule {
    private static final Logger log = LoggerFactory.getLogger(HealthModule.class);
    private final Table<IExposure.ExposureBuiltForm, IExposure.VentilationType, List<IExposure>> exposures = HashBasedTable.create();
    private final IExposure overheating = new OverheatingExposure();
    private final Table<Disease.Type, Person.Sex, List<Disease>> healthCoefficients =
        HashBasedTable.create();

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
				switch(type){
				case copd:
				case commonmentaldisorder:
				case asthma1:
				case asthma2:
				case asthma3:
				case overheating:
					break;
                default:
                    final Disease d = Disease.readDisease(row.get("age"), row.get("sex"), row.get(type.name()), Double.parseDouble(row.get("all")), row.get("population"),row.get(type.name()+"_ratio"));

                    if (!healthCoefficients.contains(type, d.sex)) {
                        healthCoefficients.put(type, d.sex, new ArrayList<Disease>());
                    }

                    healthCoefficients.get(type, d.sex).add(d);
				}
			}
		}
		
        //Need to have coefs for CMD and Asthma so the diseases can be calculated later. The values aren't important (not gender/age specific)
        putDummyDisease(Disease.Type.commonmentaldisorder, healthCoefficients);
        putDummyDisease(Disease.Type.copd,                 healthCoefficients);
        putDummyDisease(Disease.Type.asthma1,              healthCoefficients);
        putDummyDisease(Disease.Type.asthma2,              healthCoefficients);
        putDummyDisease(Disease.Type.asthma3,              healthCoefficients);
        putDummyDisease(Disease.Type.overheating,          healthCoefficients);

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
        theDisease.add(Disease.readDisease("-1", "FEMALE", "0", 0, "0","0"));
        // we need to put it in under both male and female.
        out.put(type, Person.Sex.FEMALE, theDisease);
        out.put(type, Person.Sex.MALE, theDisease);
    }

	private BufferedReader bufferedReaderForResource(final String resource) {
		return new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(resource)));
	}

    @Override
    public <T extends HealthOutcome> T effectOf(
        final Supplier<T> factory,

        final double t1,
        final double t2,

        final double p1,
        final double p2,

        final double h1,
        final double h2,

        final BuiltForm.Type form,
        final double floorArea,
        final BuiltForm.Region region,
        final int mainFloorLevel,

        final boolean hasWorkingExtractorFans, // per finwhatever
        final boolean hasTrickleVents,         // this is cooked up elsewhere

        final boolean wasDoubleGlazed,
        final boolean isDoubleGlazed,

        final List<Person> people) {
        final T result = factory.get();
        final int horizon = result.horizon();
        
        //perform the matching between NHM built form and ventilation and Hideem
        final IExposure.ExposureBuiltForm matchedBuiltForm = mapBuiltForm(form, floorArea, mainFloorLevel);
        final IExposure.VentilationType matchedVentilation = mapVentilation(hasWorkingExtractorFans, hasTrickleVents);
              
        //Get the correct exposures coefficients and calculate base and modified exposures for each individual

        boolean smoker = false;
        for(final Person p: people){
            if(p.smokes == true){
                smoker =  true;
                break;
            }
        }

        for (final IExposure exposure : exposures.get(matchedBuiltForm, matchedVentilation)) {
            for(final IExposure.OccupancyType occupancy : IExposure.OccupancyType.values()){
                exposure.modify(t1, t2,
                                p1, p2,

                                h1, h2,

                                smoker,
                                mainFloorLevel,
                                form,
                                region,
                                isDoubleGlazed,

                                occupancy,
                                result);
            }
        }

        for (final IExposure.OccupancyType occupancy : IExposure.OccupancyType.values()){
            // apply the overheating exposure. It applies for all ventilations and built forms
            // so we just need to bash it out here.
            overheating.modify(t1, t2,
                               p1, p2,

                               h1, h2,

                               smoker,
                               mainFloorLevel,
                               form,
                               region,
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
                double diseaseBaseSurvival   = 1;

                final Iterator<Disease> coefficients = healthCoefficients.get(disease, person.sex).iterator();
                if (!coefficients.hasNext()) continue diseases;

                Disease currentCoefficients = coefficients.next();
                // now we run over the years that we are considering.
                // most of the coefficient sets apply to a given age
                // however, some of them are the same for all ages,
                // and have an age field of '-1' to signify that.

            years:
                for (int year = 0; year < horizon; year++) {
                    // this is the age they will be N from the horizon
                    final int personAgeInYear = person.age + year;
                    // now we want to know what the disease coefficients say for this age and exposure

                    final double riskChangeTime =
                        (disease == Disease.Type.overheating) ?
                        result.overheatingRisk(OverheatingAgeBands.forAge(personAgeInYear)) :
                        result.relativeRisk(disease, OccupancyType.forAge(personAgeInYear));

                    if (currentCoefficients.age == -1 || currentCoefficients.age == personAgeInYear) {
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

                        // TODO what API here? do we want person specific info?

                        result.addEffects(disease, year, person, mortalityChange, morbidityQalys, cost);

                        // move to next defined year of coefficients:
                        if (coefficients.hasNext()) {
                            if (currentCoefficients.age != -1) currentCoefficients = coefficients.next();
                        } else {
                            // there are no coefficients defined any more for this disease, so we can stop looping.
                            break years;
                        }
                    }
                }
            }
        }

        return result;
    }

    //Temperature Calculations using Hamilton relation
    private double calcSIT(final double eValue){
		final double livingRoomSIT=(Constants.LR_SIT_CONSTS[4] + (Constants.LR_SIT_CONSTS[3]*Math.pow(eValue,1)) + (Constants.LR_SIT_CONSTS[2]*Math.pow(eValue,2)) 
				+ (Constants.LR_SIT_CONSTS[1]*Math.pow(eValue,3)) + (Constants.LR_SIT_CONSTS[0]*Math.pow(eValue,4)));
		final double bedRoomSIT=(Constants.BR_SIT_CONSTS[4] + (Constants.BR_SIT_CONSTS[3]*Math.pow(eValue,1)) + (Constants.BR_SIT_CONSTS[2]*Math.pow(eValue,2))
				+ (Constants.BR_SIT_CONSTS[1]*Math.pow(eValue,3))  + (Constants.BR_SIT_CONSTS[0]*Math.pow(eValue,4)));
		final double averageSIT=((livingRoomSIT+bedRoomSIT)/2);
		
		return averageSIT;
    }
    
    private double calcSITRegression(final double eValue,
                                     final BuiltForm.DwellingAge age,
                                     final BuiltForm.Tenure t,
                                     final BuiltForm.OwnerAge a,
                                     final boolean c,
                                     final boolean p) {
    	
    	int children = (c) ? 1 : 0;
        int fuelPoverty = (p) ? 1 : 0;
    	
    	final double livingRoomSIT=(Constants.INTERCEPT_LR + eValue*Constants.E_COEF_LR + Constants.DW_AGE_LR[age.ordinal()]) + Constants.TENURE_LR[t.ordinal()] 
                + Constants.OC_AGE_LR[age.ordinal()] + Constants.CH_LR[children] + Constants.FP_LR[fuelPoverty];
    	final double bedRoomSIT=(Constants.INTERCEPT_BR + eValue*Constants.E_COEF_BR + Constants.DW_AGE_BR[age.ordinal()]) 
                + Constants.OC_AGE_BR[age.ordinal()] + Constants.CH_BR[children] + Constants.FP_BR[fuelPoverty];;
    	final double averageSIT = ((livingRoomSIT+bedRoomSIT)/2);    	
    	
    	return averageSIT;
    }
    
    
    @Override
    public double getInternalTemperature(final boolean regressionSIT,
                                         final double specificHeat,
                                         double efficiency,
                                         final BuiltForm.DwellingAge dwellingAge,
                                         final BuiltForm.Tenure tenure,
                                         final BuiltForm.OwnerAge ownerAge,
                                         final boolean children,
                                         final boolean fuelPoverty) {
    	if (efficiency <= 0) efficiency = 1;
    	final double eValue = specificHeat / efficiency;
    	
    	if (regressionSIT)
            return calcSITRegression(eValue, dwellingAge, tenure, ownerAge, children, fuelPoverty);
    	else
    		return calcSIT(eValue);
    }
    
    //Methods to map the input built form and ventilation of NHM to that in Hideem.
    //Not sure if this should be here or elsewhere but works for now
    private IExposure.ExposureBuiltForm mapBuiltForm(final BuiltForm.Type form, final double floorArea, final int mainFloorLevel) {
	    //initialisation
        IExposure.ExposureBuiltForm matchedBuiltForm = null;
	    //Will have to put lots of if statements in here somewhere...
	    //Get the dwelling type in exposures
	    //mainFloorLevel==1 is <=ground floor 
	    //mainFloorLevel==2 is 1st floor
	    //mainFloorLevel==3 is >1st floor
	    
	    switch (form) {
	    case ConvertedFlat:
	    case PurposeBuiltFlatLowRise:
	    	if (floorArea > 50) {
	    		matchedBuiltForm = ExposureBuiltForm.Flat1;
	    	} else {
	    		matchedBuiltForm = ExposureBuiltForm.Flat2;
	    	}
	    	break;
	    case PurposeBuiltFlatHighRise:
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
	    if(!hasWorkingExtractorFans && !hasTrickleVents){
            matchedVentilation = IExposure.VentilationType.NOTE;
	    }
	    else if(hasWorkingExtractorFans && !hasTrickleVents){
            matchedVentilation = IExposure.VentilationType.T;
	    }
	    else if(!hasWorkingExtractorFans && hasTrickleVents){
            matchedVentilation = IExposure.VentilationType.E;
	    }
	    else{
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
                impact += coefficients.hazard * (1 - (1 - riskChangeTime) * (1 - Math.exp(-(year+1) * Constants.TIME_FUNCTION(disease)[2])));
			}
		}

        final double impactEndPop   = impactStartPop * ( (2-impact) / (2+impact) );
        final double baseEndPop     = baseStartPop   * ( (2-base)   / (2+base)   );

        final double deaths = impactStartPop - impactEndPop;
		final double lifeYears = impactStartPop - 0.5*deaths;
		
        final double baseDeaths = baseStartPop - baseEndPop;
		final double baselifeYears = baseStartPop - 0.5*baseDeaths;
		
		//doing change in the number of deaths instead
		final double deltaDeaths = deaths-baseDeaths;
		final double deltaQalys = lifeYears-baselifeYears;

        return new double[] {deltaDeaths, deltaQalys, impactEndPop, baseEndPop};
    }
    
    private double[] calculateCMDQaly(final double riskChangeTime, final int age, final int year) {
    	double impact = 0;
    	
    	if (age >= 16) {
    		//0.25 factor for winter months only
    		impact = (1 - riskChangeTime)*(1-Constants.WEIGHT_CMD)*0.25*Constants.PREV_CMD;
    	} 

    	final double timeFunct = 1/Math.pow(2.5,year);
    	
    	final double qalys = impact*timeFunct;
    	final double cases = Constants.PREV_CMD*(riskChangeTime-1)*timeFunct*0.25;
    	
    	final double vals[] = {cases, qalys};
    	
    	return vals;
    }

    private double[] calculateCOPDQaly(final double riskChangeTime, final int age, final int year) {
    	double impact = 0;
    	
    	if (age >= 45) {
    		impact = (1 - riskChangeTime)*(1-Constants.WEIGHT_COPD)*0.25*Constants.PREV_COPD;
    	} 
    	
    	//reduces to 50% then 25%
    	double timeFunct = 1/Math.pow(2,year);
    	//Constant 25% there after    	
    	if (year >= 3) {
    		timeFunct = 1/Math.pow(2,2);
    	}
    	
    	final double qalys = impact*timeFunct;
    	final double cases = Constants.PREV_COPD*(riskChangeTime-1)*timeFunct*0.25;
    	
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
			impact = (age <= 15) ? ((1 - Constants.WEIGHT_ASTHMA1) * Constants.PREV_ASTHMA1)*(1 - riskChangeTime) : 0;
			cases = Constants.PREV_ASTHMA1*(riskChangeTime-1);
			break;
		case asthma2:
			impact = (age <= 15) ? ((1 - Constants.WEIGHT_ASTHMA2) * Constants.PREV_ASTHMA2)*(1 - riskChangeTime) : 0;
			cases = Constants.PREV_ASTHMA2*(riskChangeTime-1);
			break;
		case asthma3:
			impact = (age <= 15) ? ((1 - Constants.WEIGHT_ASTHMA3) * Constants.PREV_ASTHMA3)*(1 - riskChangeTime) : 0;
			cases = Constants.PREV_ASTHMA3*(riskChangeTime-1);
			break;
		}
    	
    	final double timeFunct = 1/Math.pow(1.3,year);
    	    	
    	final double vals[] = {cases * timeFunct, impact * timeFunct};
    	
    	return vals;
    			
    }
    
}    

