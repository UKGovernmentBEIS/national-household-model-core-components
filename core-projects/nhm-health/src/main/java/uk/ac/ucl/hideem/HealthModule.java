package uk.ac.ucl.hideem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.ucl.hideem.Exposure.ExposureBuiltForm;
import uk.ac.ucl.hideem.Exposure.OccupancyType;
import uk.ac.ucl.hideem.Exposure.Type;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Table;
import com.google.common.collect.HashBasedTable;

public class HealthModule implements IHealthModule {
	private static final Logger log = LoggerFactory.getLogger(HealthModule.class);
    private final Table<Exposure.ExposureBuiltForm, Exposure.VentilationType, List<Exposure>> exposures =
        HashBasedTable.create();
	private final ListMultimap<Disease.Type, Disease> healthCoefficients;
    
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
               final Exposure e = Exposure.readExposure(row);

               if (!exposures.contains(e.builtForm, e.ventType)) {
                   exposures.put(e.builtForm, e.ventType, new ArrayList<Exposure>());
               }

               exposures.get(e.builtForm, e.ventType).add(e);
           }
        } catch (final IOException ex) {
                    // problem?
        }
        
        //Read the health coefficients from the csv file
        healthCoefficients = ArrayListMultimap.create();

        log.debug("Reading health coefficients from: src/main/resources/uk/ac/ucl/hideem/NHM_mortality_data.csv");
        
        //Not yet sure how to get as a resource?
		for (final Map<String, String> row : CSV.mapReader(bufferedReaderForResource("NHM_mortality_data.csv"))) {         
			   
			for (final Disease.Type type : Disease.Type.values()){
				switch(type){
				case copd:
				case commonmentaldisorder:
				case asthma1:
				case asthma2:
				case asthma3:
					break;
				default:
					final Disease d = Disease.readDisease(row.get("age"), row.get("sex"), row.get(type.name()), Double.parseDouble(row.get("all")), row.get("population"),row.get(type.name()+"_ratio"));
					healthCoefficients.put(Enum.valueOf(Disease.Type.class, type.name()), d);
				}
			}
		}
		
		//Need to have coefs for CMD and Asthma so the diseases can be calculated later. The values aren't important (not gender/age specific) 
		final Disease cmd = Disease.readDisease("-1", "FEMALE", "0", 0, "0","0");
		healthCoefficients.put(Disease.Type.commonmentaldisorder, cmd);
		final Disease copd = Disease.readDisease("-1", "FEMALE", "0", 0, "0","0");
		healthCoefficients.put(Disease.Type.copd, copd);
		final Disease asthma = Disease.readDisease("-1", "FEMALE", "0", 0, "0","0");
		healthCoefficients.put(Disease.Type.asthma1, asthma);
		healthCoefficients.put(Disease.Type.asthma2, asthma);
		healthCoefficients.put(Disease.Type.asthma3, asthma);
    }

	private BufferedReader bufferedReaderForResource(final String resource) {
		return new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(resource)));
	}

    @Override
	public HealthOutcome effectOf(
        // e-values & perm.s
        final double t1,
        final double t2,
        final double p1,
        final double p2,

        // case number constituents
        final BuiltForm.Type form,
        final double floorArea,
        final BuiltForm.Region region,
        final int mainFloorLevel, // fdfmainn (for flats)
        // finkxtwk and finbxtwk
        final boolean hasWorkingExtractorFans, // per finwhatever
        final boolean hasTrickleVents,         // this is cooked up elsewhere
        final boolean rebate,
        // who
        final List<Person> people,
        final int horizon) {
        
        final HealthOutcome result = new HealthOutcome(horizon, people.size());
        
        //perform the matching between NHM built form and ventilation and Hideem
        final Exposure.ExposureBuiltForm matchedBuiltForm = mapBuiltForm(form, floorArea, mainFloorLevel);
        final Exposure.VentilationType matchedVentilation = mapVentilation(hasWorkingExtractorFans, hasTrickleVents);
              
        //Get the correct exposures coefficients and calculate base and modified exposures for each individual

        boolean smoker = false;
        for(final Person p: people){
            if(p.smokes == true){
                smoker =  true;
            }
        }

    	for(final Exposure.OccupancyType occupancy : Exposure.OccupancyType.values()){
            for (final Exposure exposure : exposures.get(matchedBuiltForm, matchedVentilation)) {
                exposure.modify(t1, t2,
                                p1, p2,

                                smoker,
                                mainFloorLevel,
                                form,
                                region,

                                occupancy,
                                result);
            }
	                               
	        //Calculate the relative risks (independent of person) -> won't be any more due to diff occupancies
	        for (final Disease.Type disease : Disease.Type.values()) {
                result.setRelativeRisk(disease, occupancy, disease.relativeRisk(result, occupancy));
            }
        } //end of occupancy loop

       	//Survival array here so that qaly calc is done cumulatively (need one for each person per disease)
    	final double[][][] impactSurvival = new double[people.size()][Disease.Type.values().length][horizon+1];
    	final double[][][] baseSurvival = new double[people.size()][Disease.Type.values().length][horizon+1];
    	for(final Person p: people){ //initialize to 1
    		for (final Disease.Type d : Disease.Type.values()) {
    			impactSurvival[people.indexOf(p)][d.ordinal()][0] = 1;
    			baseSurvival[people.indexOf(p)][d.ordinal()][0] = 1;
    		}    
    	}

        //loop over people in house to match them to coefficients
        for(final Person p: people){
            //Loop over disease coefficients
            for(final Map.Entry<Disease.Type, Disease> d: healthCoefficients.entries()) {

        		//loop over time frame
        		for (int year = 0; year < horizon; year=year+1) {
        			int age = p.age+year;
        			//Need age ==-1 as ages not stored for CMD and Asthma 
	        		if ((age == d.getValue().age && p.sex == d.getValue().sex) || d.getValue().age==-1){
		        		final OccupancyType occupancy;
		        		
	        			if(age <= 5){
	        				occupancy = OccupancyType.H55_45_0; 
	        			} else if(age > 5 && age < 18){
	        				occupancy = OccupancyType.W29_33_0;
	        			} else if(age > 65){
	        				occupancy = OccupancyType.H45_45_10;
	        			} else{
	        				occupancy = OccupancyType.W21_33_8;
	        			}
	        			
	        			final double riskChangeTime = result.relativeRisk(d.getKey(),occupancy);
	        			

	        			//Set the occupant exposures so can print it out
	        			for(final Exposure.Type e : Exposure.Type.values()) {
	        				result.setInitialOccExposure(e, year, people.indexOf(p), occupancy);
	        				result.setFinalOccExposure(e, year, people.indexOf(p), occupancy);
	        			}
	        			
	        			//samplesize
	        			final int samplesize = 1;//p.samplesize;
	        			
	        			final double qaly[] = calculateQaly(d, riskChangeTime, impactSurvival, baseSurvival, people.indexOf(p), year);
	        			// calculateQaly returns array: [0] deaths [1] qaly changes
	        			result.setMortalityQalys(d.getKey(), year, qaly[1]*samplesize, people.indexOf(p));
	        			
	        			//Different cases for CMD and Asthma for morbidity qalys
	        			switch(d.getKey()){
	        			
	        			case copd:
	        				final double[] copdImp = calculateCOPDQaly(riskChangeTime, age, year);
	        				result.setMorbidityQalys(d.getKey(), year, copdImp[1]*samplesize, people.indexOf(p));
	        				result.setCost(d.getKey(), year, copdImp[0]*Constants.COST_PER_CASE(d.getKey())*samplesize, people.indexOf(p));
	        				break;	        			
	        			case commonmentaldisorder:
	        				final double[] cmdImp = calculateCMDQaly(riskChangeTime, age, year);
	        				result.setMorbidityQalys(d.getKey(), year, cmdImp[1]*samplesize, people.indexOf(p));
	        				result.setCost(d.getKey(), year, cmdImp[0]*Constants.COST_PER_CASE(d.getKey())*samplesize, people.indexOf(p));
	        				break;
	        			case asthma1:
	        			case asthma2:
	        			case asthma3:
	        				final double[] asthmaImp = calculateAsthmaQaly(d.getKey(), riskChangeTime, age, year);
	        				result.setMorbidityQalys(d.getKey(), year, asthmaImp[1]*samplesize, people.indexOf(p));
	        				result.setCost(d.getKey(), year, asthmaImp[0]*Constants.COST_PER_CASE(d.getKey())*samplesize, people.indexOf(p));
	        				break;
	        			default:
	        				result.setMorbidityQalys(d.getKey(), year, qaly[1]*d.getValue().morbidity*samplesize, people.indexOf(p));
		        			final double cases = Constants.INCIDENCE(d.getKey(), p.age, p.sex)*(qaly[0])*Constants.COST_PER_CASE(d.getKey()); 
		        			result.setCost(d.getKey(), year, cases*samplesize, people.indexOf(p));
	        				break;
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
    
    private double calcSITRegression(final double eValue, final BuiltForm.DwellingAge age, final BuiltForm.Tenure t, final BuiltForm.OwnerAge a, final boolean c, final boolean p) {
    	
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
    public double getInternalTemperature(boolean regressionSIT, double specificHeat, double efficiency, BuiltForm.DwellingAge dwellingAge, 
            BuiltForm.Tenure tenure, BuiltForm.OwnerAge ownerAge, boolean children, boolean fulePoverty) {
    	if (efficiency <= 0) efficiency = 1;
    	final double eValue = specificHeat / efficiency;
    	
    	if (regressionSIT)
            return calcSITRegression(eValue, dwellingAge, tenure, ownerAge, children, fulePoverty);
    	else
    		return calcSIT(eValue);
    }
    
    //Methods to map the input built form and ventilation of NHM to that in Hideem.
    //Not sure if this should be here or elsewhere but works for now
    private Exposure.ExposureBuiltForm mapBuiltForm(final BuiltForm.Type form, final double floorArea, final int mainFloorLevel) {
	    //initialisation
	    Exposure.ExposureBuiltForm matchedBuiltForm = null;
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
    private Exposure.VentilationType mapVentilation(final boolean hasWorkingExtractorFans, final boolean hasTrickleVents) {
    	//initialisation
	    Exposure.VentilationType matchedVentilation = null;
    	//Get the ventilation
	    if(!hasWorkingExtractorFans && !hasTrickleVents){
	    	matchedVentilation = Exposure.VentilationType.NOTE;
	    }
	    else if(hasWorkingExtractorFans && !hasTrickleVents){
	    	matchedVentilation = Exposure.VentilationType.T;
	    }
	    else if(!hasWorkingExtractorFans && hasTrickleVents){
	    	matchedVentilation = Exposure.VentilationType.E;
	    }
	    else{
	    	matchedVentilation = Exposure.VentilationType.TE;
	    }
    
    	return matchedVentilation;
    }

    private double[] calculateQaly(final Map.Entry<Disease.Type, Disease> d, final double riskChangeTime, final double[][][] impactSurvival, final double[][][] baseSurvival, final int personIndex, final int year) {
    	//Calculations based on Miller, Life table for quantitative impact assessment, 2003
    	final double base = d.getValue().allHazard;
		double impact = base - d.getValue().hazard;
    	
    	if (d.getKey() == Disease.Type.wincardiovascular || d.getKey() == Disease.Type.wincerebrovascular || d.getKey() == Disease.Type.winmyocardialinfarction) {
			impact += d.getValue().hazard * riskChangeTime;
		}
		else  {
   			//Disease impact depends on increased or decreased risk
			if (riskChangeTime >= 1.) {
				final NormalDistribution normDist;
				normDist = new NormalDistribution(Constants.TIME_FUNCTION(d.getKey())[0], Constants.TIME_FUNCTION(d.getKey())[1]);
				impact += d.getValue().hazard * (1 + (riskChangeTime - 1) * normDist.cumulativeProbability(year+1));						
				    			
			} else {			
				impact += d.getValue().hazard * (1 - (1 - riskChangeTime) * (1 - Math.exp(-(year+1) * Constants.TIME_FUNCTION(d.getKey())[2])));
			}
		}
    	
    	impactSurvival[personIndex][d.getKey().ordinal()][year+1] = impactSurvival[personIndex][d.getKey().ordinal()][year]*((2-impact)/(2+impact));
		baseSurvival[personIndex][d.getKey().ordinal()][year+1] = baseSurvival[personIndex][d.getKey().ordinal()][year]*((2-base)/(2+base));
		
		final double impactStartPop = impactSurvival[personIndex][d.getKey().ordinal()][year];
		final double baseStartPop = baseSurvival[personIndex][d.getKey().ordinal()][year];
				
		final double deaths = impactStartPop - impactSurvival[personIndex][d.getKey().ordinal()][year+1];
		final double lifeYears = impactStartPop - 0.5*deaths;
		
		final double baseDeaths = baseStartPop - baseSurvival[personIndex][d.getKey().ordinal()][year+1];
		final double baselifeYears = baseStartPop - 0.5*baseDeaths;
		
		//doing change in the number of deaths instead
		final double deltaDeaths = deaths-baseDeaths;
		final double deltaQalys = lifeYears-baselifeYears;
		final double vals[] = {deltaDeaths, deltaQalys};
		return vals;
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

