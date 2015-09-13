package uk.ac.ucl.hideem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.ucl.hideem.Exposure.ExposureBuiltForm;
import uk.ac.ucl.hideem.Exposure.OccupancyType;
import uk.ac.ucl.hideem.Exposure.OverheatingAgeBands;
import uk.ac.ucl.hideem.Exposure.Type;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

public class HealthModule implements IHealthModule {
	private static final Logger log = LoggerFactory.getLogger(HealthModule.class);
	private final ListMultimap<Exposure.Type, Exposure> exposureCoefficients;
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
        exposureCoefficients = ArrayListMultimap.create();

        log.debug("Reading exposure coefficients from: src/main/resources/uk/ac/ucl/hideem/NHM_exposure_coefs");

        try (final CSV.Reader reader = CSV.trimmedReader(bufferedReaderForResource("NHM_exposure_coefs.csv"))) {
           String[] row = reader.read(); // throw away header line, because we know what it is
           
           while ((row = reader.read()) != null) {
        	   final Exposure e = Exposure.readExposure(row);
        	   exposureCoefficients.put(Enum.valueOf(Exposure.Type.class, row[0]), e);
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
				case overheating:
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
		final Disease overheating = Disease.readDisease("-1", "FEMALE", "0", 0, "0","0");
		healthCoefficients.put(Disease.Type.overheating, overheating);
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
        final double e1,
        final double e2,
        // case number constituents
        final BuiltForm.Type form,
        final double floorArea,
        final int region,
        final int mainFloorLevel, // fdfmainn (for flats)
        // finkxtwk and finbxtwk
        final boolean hasWorkingExtractorFans, // per finwhatever
        final boolean hasTrickleVents,         // this is cooked up elsewhere
        final boolean rebate,
        final boolean  doubleGlaz,      //dblglazing80pctplus
        // who
        final List<Person> people,
        final int horizon) {
        
        final HealthOutcome result = new HealthOutcome(horizon, people.size());
        
        //perform the matching between NHM built form and ventilation and Hideem
        final Exposure.ExposureBuiltForm matchedBuiltForm = mapBuiltForm(form, floorArea, mainFloorLevel);
        final Exposure.VentilationType matchedVentilation = mapVentilation(hasWorkingExtractorFans, hasTrickleVents);
              
        //Get the correct exposures coefficients and calculate base and modified exposures for each individual

        //First loop over the exposure types
    	//There is probably a quicker/better way of doing this but this will do for now 
        for(final Exposure.Type matchedExposure : Exposure.Type.values()) {
        	
            //loop over occupancy types                
        	for(final Exposure.OccupancyType occupancy : Exposure.OccupancyType.values()){
        	
	        	// for each exposure in the exposure coefficients file which is under matchedExposure
	        	for (final Exposure exposure : exposureCoefficients.get(matchedExposure)) {
	        		if (matchedVentilation==exposure.ventType && matchedBuiltForm==exposure.builtForm) {
		        		//different calculation is used for mould and temperature and vpx is needed so do all together
	        			
	        			switch (matchedExposure) {	        				
	        			case VPX:
	        				setVPXSitAndMould(exposure, t1, t2, p1, p2, e2, occupancy,rebate, result);
	        				break;
	        			case SIT:
	        			case Mould:
	        				// SIT and Mould are both handled by VPX above
	        				break;
	        			case Radon:
	        				setRadonExposure(exposure, p1, p2, form, region, mainFloorLevel, occupancy, result);
	        				break;
	        			case ETS:
	        				//Find out if there is a smoker in the house
	        				boolean smoker = false;
	        				for(final Person p: people){
	        					if(p.smokes == true){
	        						smoker =  true;
	        					}
	        				}

	        				if(smoker == true){
	        					result.setInitialExposure(matchedExposure, occupancy, exposure.dueToPermeability(occupancy, p1));
	        					result.setFinalExposure(matchedExposure, occupancy, exposure.dueToPermeability(occupancy, p2));
	        				}else{
	        					result.setInitialExposure(matchedExposure, occupancy, 0);
	        					result.setFinalExposure(matchedExposure, occupancy, 0);
	        				}	
	        			default:
			        		result.setInitialExposure(matchedExposure, occupancy, exposure.dueToPermeability(occupancy, p1));
			        		result.setFinalExposure(matchedExposure, occupancy, exposure.dueToPermeability(occupancy, p2));
	        				break;
	        			}
		        	}        		
		        }
	                     	
	        	//Calculate the relative risks
		        for (final Disease.Type disease : Disease.Type.values()) {
		        	result.setRelativeRisk(disease, occupancy, disease.relativeRisk(result, occupancy));
		        }	
	        	
		      //Overheating Temp isn't dependent exposure coefs so out of loop
	        	if (matchedExposure == Exposure.Type.SIT2DayMax){
	        		final double initialSITMax = getSIT2DayMax(e1, doubleGlaz, region);
					final double finalSITMax   = getSIT2DayMax(e2, doubleGlaz, region);
					result.setInitialExposure(matchedExposure, occupancy, initialSITMax);
	        		result.setFinalExposure(matchedExposure, occupancy, finalSITMax);
					//RR for overheating is age dependent
					for (final Exposure.OverheatingAgeBands ageBand : OverheatingAgeBands.values()) {
						result.setRelativeRisk(Disease.Type.overheating, ageBand, 
								Disease.Type.overheating.relativeRisk(result, occupancy, region, ageBand));
					}	
	        	}
		        
	    	}//end of occupancy loop	
    	}//end of exposure loop

        // health calculation goes here. Probably be good to sanity check the inputs.
       
       	//Survival array here so that qaly calc is done cumulatively (need one for each person per disease)
    	final double[][][] impactSurvival = new double[people.size()][Disease.Type.values().length][horizon+1];
    	final double[][][] baseSurvival = new double[people.size()][Disease.Type.values().length][horizon+1];
    	for(final Person p: people){ //initialize to 1
    		for (final Disease.Type d : Disease.Type.values()) {
    			impactSurvival[people.indexOf(p)][d.ordinal()][0] = 1;
    			baseSurvival[people.indexOf(p)][d.ordinal()][0] = 1;
    		}    
    	}
    	
        //Loop over disease coefficients
        for(final Map.Entry<Disease.Type, Disease> d: healthCoefficients.entries()) {
        	        	
        	//loop over people in house to match them to coefficients
        	for(final Person p: people){
        	
        		//loop over time frame
        		for (int year = 0; year < horizon; year=year+1) {
        			int age = p.age+year;
        			//Need age ==-1 as ages not stored for CMD and Asthma 
	        		if ((age == d.getValue().age && p.sex == d.getValue().sex) || d.getValue().age==-1){
		        		
	        			final OccupancyType occupancy = Exposure.getOccupancyType(age);   		
	        			final OverheatingAgeBands ageBand = Exposure.getOverheatingAgeBand(age);	        			

	        			double riskChangeTime = 1;
	        			switch(d.getKey()){
	        			case overheating:
	        				riskChangeTime = result.relativeRisk(d.getKey(), ageBand);
	        				break;
	        			default:	
	        				riskChangeTime = result.relativeRisk(d.getKey(),occupancy);
	        				break;
	        			}
	        				
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
	        			case overheating:
	        				break;	//put here
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
    
	private void setRadonExposure(final Exposure exposure, final double p1,
			final double p2, final BuiltForm.Type form, final int region,
			final int mainFloorLevel, final Exposure.OccupancyType occupancy,
			final HealthOutcome result) {
		final double baseExposure = exposure.dueToPermeability(occupancy, p1);
		final double modifiedExposure = exposure.dueToPermeability(occupancy, p2);			        		
		
		//factors here!
		double floorFactor = 1;
		if((form ==BuiltForm.Type.ConvertedFlat || form==BuiltForm.Type.PurposeBuiltFlatLowRise || form==BuiltForm.Type.PurposeBuiltFlatHighRise) && mainFloorLevel==2){
			floorFactor = 0.5;
		}
		else if((form ==BuiltForm.Type.ConvertedFlat || form==BuiltForm.Type.PurposeBuiltFlatLowRise || form==BuiltForm.Type.PurposeBuiltFlatHighRise) && mainFloorLevel==3){
			floorFactor = 0.;
		}
		
		result.setInitialExposure(Type.Radon, occupancy, floorFactor*baseExposure*Constants.RADON_FACTS[region-1]);
		result.setFinalExposure(Type.Radon, occupancy, floorFactor*modifiedExposure*Constants.RADON_FACTS[region-1]);
	}
	
	private double getSIT2DayMax(
			final double eval,
			final boolean doubleGlaz,
			final int region) {
		
		double glz = 0;
		if (doubleGlaz == true) {
			glz = 0.37225874;
		}
		
		//Calculate using Ian's regression method
		final double SITMax = 17.45785434 + Constants.OVERHEAT_THRESH[region-1]*0.2945458 + -0.00158636*eval + Constants.OVERHEAT_COEFS[region-1] +glz;
		
		return SITMax;
	}
	
	
	private void setVPXSitAndMould(
			final Exposure exposure, 
			final double baseAverageSIT, 
			final double modifiedAverageSIT,
			final double p1, final double p2,
			final double e2,
			final Exposure.OccupancyType occupancy, final boolean rebate, final HealthOutcome result) {
		final double baseVPX = exposure.dueToPermeability(occupancy, p1);
		final double modifiedVPX = exposure.dueToPermeability(occupancy, p2);
		
		//set VPX
		result.setInitialExposure(Exposure.Type.VPX, occupancy,baseVPX);
		result.setFinalExposure(Exposure.Type.VPX, occupancy, modifiedVPX);
		
		//set SIT
		result.setInitialExposure(Exposure.Type.SIT, occupancy, baseAverageSIT);
		result.setFinalExposure(Exposure.Type.SIT, occupancy, modifiedAverageSIT);
		
		if(rebate == true) {
			//If rebate there will be an effect on the modified SIT
						
			//1st get heating design day		
			final double hddSIT = 890.97 + -203.97*(modifiedAverageSIT-2.1)+21.86*Math.pow(modifiedAverageSIT-2.1,2) + -0.27*Math.pow(modifiedAverageSIT-2.1,3);
			final double heatingHours = 24*hddSIT/(modifiedAverageSIT-5.0);
			final double deltaT = Constants.REBATE_AMMOUNT/(1E-3*Constants.REBATE_PRICE*heatingHours*e2);	
			
			//final double evalueRebate = e2-((Constants.REBATE_AMMOUNT/Constants.REBATE_PRICE)/hddSIT)/0.0024;
			
			final double modifiedRebateAverageSITPhil = modifiedAverageSIT+deltaT;
			//final double modifiedRebateAverageSIT = calcSIT(evalueRebate);
			result.setFinalExposure(Exposure.Type.SIT, occupancy, modifiedRebateAverageSITPhil);
			//System.out.println("E-val rebate ian " + hddSIT+ " , " + evalueRebate+ " , " + modifiedAverageSIT + " , " + modifiedRebateAverageSIT );
			//System.out.println("E-val rebate phil " + hddSIT+ " , " + e2 + " , " + modifiedAverageSIT+ " , " +  modifiedRebateAverageSITPhil);
		}
		
		//Now do the mould calc
		final double baseMould	= calcMould(baseAverageSIT, baseVPX);
		final double modifiedMould	= calcMould(modifiedAverageSIT, modifiedVPX);		           		
		
		//set Mould	
		result.setInitialExposure(Exposure.Type.Mould, occupancy, baseMould);
		result.setFinalExposure(Exposure.Type.Mould, occupancy, modifiedMould);
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
    	int feulPoverty = (p) ? 1 : 0;
    	
    	final double livingRoomSIT=(Constants.INTERCEPT_LR + eValue*Constants.E_COEF_LR + Constants.DW_AGE_LR[age.ordinal()]) + Constants.TENURE_LR[t.ordinal()] 
    			+ Constants.OC_AGE_LR[age.ordinal()] + Constants.CH_LR[children] + Constants.FP_LR[feulPoverty];
    	final double bedRoomSIT=(Constants.INTERCEPT_BR + eValue*Constants.E_COEF_BR + Constants.DW_AGE_BR[age.ordinal()]) 
    			+ Constants.OC_AGE_BR[age.ordinal()] + Constants.CH_BR[children] + Constants.FP_BR[feulPoverty];;
    	final double averageSIT = ((livingRoomSIT+bedRoomSIT)/2);    	
    	
    	return averageSIT;
    }
    
    
    @Override
    public double getInternalTemperature(boolean regressionSIT, double specificHeat, double efficiency, BuiltForm.DwellingAge dwellingAge, 
    		BuiltForm.Tenure tenure, BuiltForm.OwnerAge ownerAge, boolean children, boolean feulPoverty) {
    	if (efficiency <= 0) efficiency = 1;
    	final double eValue = specificHeat / efficiency;
    	
    	if (regressionSIT)
    		return calcSITRegression(eValue, dwellingAge, tenure, ownerAge, children, feulPoverty);
    	else
    		return calcSIT(eValue);
    }
    
    //Info on Mould calcs can be found in: http://www.iso.org/iso/catalogue_detail.htm?csnumber=51615
    private double calcMould(final double averageSIT, final double vpx) {
    	//initialisation
    	double mould = 0, srh=0, svp=0;

    	
    	//Calculate SVP
    	if(averageSIT >0) {
			svp = 610.78*Math.exp((17.269*averageSIT)/(237.3+averageSIT));
		}
		else {
			svp = 610.5*Math.exp((21.875*averageSIT)/(265.5+averageSIT));
		}

    	
    	//Calculate SRH
    	if(100*(vpx+(0.8*872.26))/svp >100){
    		srh=100;
    	}
    	else{
    		srh=100*(vpx+(0.8*872.26))/svp;
    	}  	

    	
    	//Calculate Mould
    	if(srh<=45){
    		mould = -1.741582244 +(0.697690596*Math.pow(srh,1))+(-0.023600847*Math.pow(srh,2))+(0.000278933*Math.pow(srh,3));
    	}
    	else if(srh>45 && srh <200){
    		mould = 4.687377282 +(-1.161195895*Math.pow(srh,1))+(0.037245523*Math.pow(srh,2)) +(-0.000223222*Math.pow(srh,3));
    	}
    	else{  //Maybe Ian made a mistake here?
    		mould = 23.42903107 +(-1.46645682*Math.pow(srh,1))+(0.027203495*Math.pow(srh,2))+(-7.89893e-05*Math.pow(srh,3));
    	}

    	
    	return mould;
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
    	double base = d.getValue().allHazard;
		double impact = base - d.getValue().hazard;
    	
		if (d.getKey() == Disease.Type.overheating) {
			//For overheating age dependence is in the RR and there are no mortality stats so we just use total pop (deaths/pop)
			base = Constants.TOT_BASE;
			impact = base + Constants.OVERHEAT_HAZARD* (riskChangeTime -1); //roughly 2000 overheating excess deaths (Indpendent newspaper)
		}else if (d.getKey() == Disease.Type.wincardiovascular || d.getKey() == Disease.Type.wincerebrovascular || d.getKey() == Disease.Type.winmyocardialinfarction) {
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

