package uk.ac.ucl.hideem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        try (final CSV.Reader reader = CSV.trimmedReader(
                new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("NHM_exposure_coefs.csv"))))) {
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
		for (final Map<String, String> row : CSV.mapReader(Paths.get("src/main/resources/uk/ac/ucl/hideem/NHM_mortality_data.csv"))) {         
			   
			for (final Disease.Type type : Disease.Type.values()){
				
				final Disease d = Disease.readDisease(row.get("age"), row.get("sex"), row.get(type.name()), Double.parseDouble(row.get("all")), row.get("population"));
				healthCoefficients.put(Enum.valueOf(Disease.Type.class, type.name()), d);
			}
		}
    }

    @Override
	public HealthOutcome effectOf(
        // e-values & perm.s
        final double e1,
        final double e2,
        final double p1,
        final double p2,
        // case number constituents
        final BuiltForm form,
        final double floorArea,
        final int mainFloorLevel, // fdfmainn (for flats)
        // for vtype:
        final int buildYear,
        // finkxtwk and finbxtwk
        final boolean hasWorkingExtractorFans, // per finwhatever
        final boolean hasTrickleVents,         // this is cooked up elsewhere
        final int numberOfFansAndPassiveVents, // per SAP
        // who
        final List<Person> people,
        final int horizon) {
        
        final HealthOutcome result = new HealthOutcome(horizon);
        
        //perform the matching between NHM built form and ventilation and Hideem
        final Exposure.ExposureBuiltForm matchedBuiltForm = mapBuiltForm(form, floorArea, mainFloorLevel);
        final Exposure.VentilationType matchedVentilation = mapVentilation(hasWorkingExtractorFans, hasTrickleVents);
              
        //Get the correct exposures coefficients and calculate base and modified exposures for each individual
        
        //loop over people in house to match them to coefficients
    	for(final Exposure.OccupancyType occupancy : Exposure.OccupancyType.values()){
        
	        //First loop over the exposure types
	    	//There is probably a quicker/better way of doing this but this will do for now 
	        for(final Exposure.Type matchedExposure : Exposure.Type.values()) {
	
	        	//Then need to loop over the exposures file to get the right values	
		        for(final Map.Entry<Exposure.Type, Exposure> e: exposureCoefficients.entries()) {
		        	if(e.getKey()== matchedExposure && matchedVentilation==e.getValue().ventType && matchedBuiltForm==e.getValue().builtForm) {
		        		//different calculation is used for mould and temperature and vpx is needed so do all together
		        		if(matchedExposure == Exposure.Type.VPX){
		        			//Calc VPX same as others
		        			
		        			final double baseVPX     =calcExposure(p1, e.getValue().coefs[occupancy.ordinal()][0], e.getValue().coefs[occupancy.ordinal()][1], 
		        					e.getValue().coefs[occupancy.ordinal()][2], e.getValue().coefs[occupancy.ordinal()][3], e.getValue().coefs[occupancy.ordinal()][4]); 
			        		final double modifiedVPX =calcExposure(p2, e.getValue().coefs[occupancy.ordinal()][0], e.getValue().coefs[occupancy.ordinal()][1], 
		        					e.getValue().coefs[occupancy.ordinal()][2], e.getValue().coefs[occupancy.ordinal()][3], e.getValue().coefs[occupancy.ordinal()][4]); 
		        			
			        		//set VPX
			        		result.setInitialExposure(Exposure.Type.VPX, baseVPX);
			        		result.setFinalExposure(Exposure.Type.VPX, modifiedVPX);
		            		
			        		//calc base temp
			        		final double baseAverageSIT=calcSIT(e1);
			         		//same for modified case
			        		final double modifiedAverageSIT=calcSIT(e2);
			        		
			        		//set SIT
			        		result.setInitialExposure(Exposure.Type.SIT, baseAverageSIT);
			        		result.setFinalExposure(Exposure.Type.SIT, modifiedAverageSIT);
		            		
		            		//Now do the mould calc
			           		final double baseMould	= calcMould(baseAverageSIT, baseVPX);
			           		final double modifiedMould	= calcMould(modifiedAverageSIT, modifiedVPX);		           		
			           		
			           		//set Mould	
			        		result.setInitialExposure(Exposure.Type.Mould, baseMould);
			        		result.setFinalExposure(Exposure.Type.Mould, modifiedMould);
			           		
		        		}
		        		else if(matchedExposure == Exposure.Type.SIT || matchedExposure == Exposure.Type.Mould){
		        			//Already calculated these when doing VPX so can break out of loop
		        			break;
		        		}
		        		else if (matchedExposure == Exposure.Type.Radon) { //rest of the exposures all the same		        		
			        		final double baseExposure=calcExposure(p1, e.getValue().coefs[occupancy.ordinal()][0], e.getValue().coefs[occupancy.ordinal()][1], 
		        					e.getValue().coefs[occupancy.ordinal()][2], e.getValue().coefs[occupancy.ordinal()][3], e.getValue().coefs[occupancy.ordinal()][4]);
			        		final double modifiedExposure =calcExposure(p2, e.getValue().coefs[occupancy.ordinal()][0], e.getValue().coefs[occupancy.ordinal()][1], 
		        					e.getValue().coefs[occupancy.ordinal()][2], e.getValue().coefs[occupancy.ordinal()][3], e.getValue().coefs[occupancy.ordinal()][4]);
	        					        		
			        		
			        		//factors here!
			        		double floorFactor = 1;
			        		if((form ==BuiltForm.ConvertedFlat || form==BuiltForm.PurposeBuiltFlat) && mainFloorLevel==2){
			        			floorFactor = 0.5;
			        		}
			        		else if((form ==BuiltForm.ConvertedFlat || form==BuiltForm.PurposeBuiltFlat) && mainFloorLevel==3){
			        			floorFactor = 0.;
			        		}
			        		
			        		result.setInitialExposure(e.getKey(), floorFactor*baseExposure);
			        		result.setFinalExposure(e.getKey(), floorFactor*modifiedExposure);
		        		}
		        		else if (matchedExposure == e.getKey()) { //rest of the exposures all the same		        		
			        		
		        			final double baseExposure=calcExposure(p1, e.getValue().coefs[occupancy.ordinal()][0], e.getValue().coefs[occupancy.ordinal()][1], 
		        					e.getValue().coefs[occupancy.ordinal()][2], e.getValue().coefs[occupancy.ordinal()][3], e.getValue().coefs[occupancy.ordinal()][4]);
			        		final double modifiedExposure =calcExposure(p2, e.getValue().coefs[occupancy.ordinal()][0], e.getValue().coefs[occupancy.ordinal()][1], 
		        					e.getValue().coefs[occupancy.ordinal()][2], e.getValue().coefs[occupancy.ordinal()][3], e.getValue().coefs[occupancy.ordinal()][4]);
			        		
			        		result.setInitialExposure(e.getKey(), baseExposure);
			        		result.setFinalExposure(e.getKey(), modifiedExposure);
		        		}
		        		else {
		        	        System.out.println("Can't find the exposure coefficient for " + matchedExposure);
		        		}
		        	}        		
		        }
	    	}
	                               
	        //Calculate the relative risks (independent of person) -> won't be any more due to diff occupancies
	        result.setRelativeRisk(Disease.Type.cardiopulmonary, occupancy.ordinal(),
	        		(Math.exp((Math.log(Constants.REL_RISK_PM_CP) / Constants.INC_PM_CP) * result.deltaExposure(Exposure.Type.OUTPM2_5))) 
	        		* (Math.exp((Math.log(Constants.REL_RISK_PM_CP) / Constants.INC_PM_CP) * result.deltaExposure(Exposure.Type.INPM2_5))));
	        result.setRelativeRisk(Disease.Type.cerebrovascular, occupancy.ordinal(),
	        		(Math.exp((Math.log(Constants.REL_RISK_PM_CP) / Constants.INC_PM_CP) * result.deltaExposure(Exposure.Type.OUTPM2_5))) 
	        		* (Math.exp((Math.log(Constants.REL_RISK_PM_CP) / Constants.INC_PM_CP) * result.deltaExposure(Exposure.Type.INPM2_5))) 
	        		* (Math.exp((Math.log(Constants.REL_RISK_ETS_CA) / Constants.INC_ETS_CA) * result.deltaExposure(Exposure.Type.ETS))));
	        result.setRelativeRisk(Disease.Type.myocardialinfarction, occupancy.ordinal(),
	        		(Math.exp((Math.log(Constants.REL_RISK_PM_CP) / Constants.INC_PM_CP) * result.deltaExposure(Exposure.Type.OUTPM2_5))) 
	        		* (Math.exp((Math.log(Constants.REL_RISK_PM_CP) / Constants.INC_PM_CP) * result.deltaExposure(Exposure.Type.INPM2_5))) 
	        		* (Math.exp((Math.log(Constants.REL_RISK_ETS_MI) / Constants.INC_ETS_MI) * result.deltaExposure(Exposure.Type.ETS))));
	        result.setRelativeRisk(Disease.Type.wincerebrovascular, occupancy.ordinal(),
	        		(Math.exp((Math.log(Constants.REL_RISK_PM_CP) / Constants.INC_PM_CP) * result.deltaExposure(Exposure.Type.OUTPM2_5))) 
	        		* (Math.exp((Math.log(Constants.REL_RISK_PM_CP) / Constants.INC_PM_CP) * result.deltaExposure(Exposure.Type.INPM2_5)))
	        		* (Math.exp((Math.log(Constants.REL_RISK_SIT_CV) / Constants.INC_WINCV) * result.deltaExposure(Exposure.Type.SIT)))
	        		* (Math.exp((Math.log(Constants.REL_RISK_ETS_CA) / Constants.INC_ETS_CA) * result.deltaExposure(Exposure.Type.ETS))));                
	        result.setRelativeRisk(Disease.Type.winmyocardialinfarction, occupancy.ordinal(),
	        		(Math.exp((Math.log(Constants.REL_RISK_PM_CP) / Constants.INC_PM_CP) * result.deltaExposure(Exposure.Type.OUTPM2_5))) 
	        		* (Math.exp((Math.log(Constants.REL_RISK_PM_CP) / Constants.INC_PM_CP) * result.deltaExposure(Exposure.Type.INPM2_5)))
	        		* (Math.exp((Math.log(Constants.REL_RISK_SIT_CV) / Constants.INC_WINCV) * result.deltaExposure(Exposure.Type.SIT)))
	        		* (Math.exp((Math.log(Constants.REL_RISK_ETS_MI) / Constants.INC_ETS_MI) * result.deltaExposure(Exposure.Type.ETS))));  
	        result.setRelativeRisk(Disease.Type.wincardiovascular, occupancy.ordinal(),
	        		(Math.exp((Math.log(Constants.REL_RISK_PM_CP) / Constants.INC_PM_CP) * result.deltaExposure(Exposure.Type.OUTPM2_5))) 
	        		* (Math.exp((Math.log(Constants.REL_RISK_PM_CP) / Constants.INC_PM_CP) * result.deltaExposure(Exposure.Type.INPM2_5)))
	        		* (Math.exp((Math.log(Constants.REL_RISK_SIT_CV) / Constants.INC_WINCV) * result.deltaExposure(Exposure.Type.SIT))));
	        result.setRelativeRisk(Disease.Type.lungcancer, occupancy.ordinal(),
	        		(Math.exp((Math.log(Constants.REL_RISK_PM_LC) / Constants.INC_PM_LC) * result.deltaExposure(Exposure.Type.OUTPM2_5))) 
	        		* (Math.exp((Math.log(Constants.REL_RISK_PM_LC) / Constants.INC_PM_LC) * result.deltaExposure(Exposure.Type.INPM2_5)))
	        		* (Math.exp((Math.log(Constants.REL_RISK_RADON_LC) / Constants.INC_RADON_LC) * result.deltaExposure(Exposure.Type.Radon))));  
	    
    	}//end of person loop
	        
	    
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
	        		if (p.age+year == d.getValue().age && p.sex == d.getValue().sex){
		        		
	        			int occupancy = 0;
	        			
	        			if(p.age+year <= 5){
	        				occupancy = 1;
	        			}
	        			else if(p.age+year > 5 && p.age+year < 18){
	        				occupancy = 3;
	        			}
	        			else{
	        				occupancy = 2;
	        			}
	        			
	        			final double riskChangeTime = result.relativeRisk(d.getKey(),occupancy);
	        			
	        			final double qaly = calculateQaly(d, riskChangeTime, impactSurvival, baseSurvival, people.indexOf(p), year);
	        			result.setMortalityQalys(d.getKey(), year, p.samplesize*qaly);
	        			
	        			final double cost = p.samplesize*qaly*Constants.COST(d.getKey());
	        			result.setCost(d.getKey(), year, cost);		        		
	        		}

	        	}
        	}
        }
        	
        return result;
    }
    
    //Use fitted values using python polyfit to CONTAM simulated data
    private double calcExposure(final double permeability, final double b0, final double b1, final double b2, final double b3, final double b4){
    	double exposure = 0;
    	exposure = b0 + (b1*Math.pow(permeability, 1)) + (b2*Math.pow(permeability,2)) + (b3*Math.pow(permeability, 3)) + (b4*Math.pow(permeability, 4)); 
    	
    	return exposure;
    }

    
    
    //Temperature Calculations using Hamilton relation
    private double calcSIT(final double eValue){
    	//Put these into private functions so that less mess
		final double livingRoomSIT=(19.97883737 + (-0.003177483*Math.pow(eValue,1)) + (3.95406E-07*Math.pow(eValue,2)) + (-3.10552E-11*Math.pow(eValue,3)));
		final double bedRoomSIT=(18.60539276 + (-0.003972248*Math.pow(eValue,1)) + (6.50441E-07*Math.pow(eValue,2)) + (-3.63348E-11*Math.pow(eValue,3)));
		final double averageSIT=((livingRoomSIT+bedRoomSIT)/2);
		
		return averageSIT;
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
    	else{
    		mould = 23.42903107 +(-1.46645682*Math.pow(srh,1))+(0.027203495*Math.pow(srh,2))+(-7.89893e-05*Math.pow(srh,3));
    	}
    	
    	return mould;
    }
    
    //Methods to map the input built form and ventilation of NHM to that in Hideem. 
    //Not sure if this should be here or elsewhere but works for now
    private Exposure.ExposureBuiltForm mapBuiltForm(final BuiltForm form, final double floorArea, final int mainFloorLevel) {
	    //initialisation
	    Exposure.ExposureBuiltForm matchedBuiltForm = null;
	    //Will have to put lots of if statements in here somewhere...
	    //Get the dwelling type in exposures
	    //mainFloorLevel==1 is <=ground floor 
	    //mainFloorLevel==2 is 1st floor
	    //mainFloorLevel==3 is >1st floor
	    if((form==BuiltForm.ConvertedFlat || form==BuiltForm.PurposeBuiltFlat) && floorArea>50) { 
	    	matchedBuiltForm = Exposure.ExposureBuiltForm.Flat1;
	    }
	    else if((form==BuiltForm.ConvertedFlat || form==BuiltForm.PurposeBuiltFlat) && floorArea<=50) { 
	    	matchedBuiltForm = Exposure.ExposureBuiltForm.Flat2;
	    }
	    else if(form==BuiltForm.PurposeBuiltFlat) {
	    	//There are no flat3s at the moment
	    	matchedBuiltForm = Exposure.ExposureBuiltForm.Flat3;
	    }
	    else if(form==BuiltForm.EndTerrace) {
	    	matchedBuiltForm = Exposure.ExposureBuiltForm.House1;
	    }
	    else if(form==BuiltForm.MidTerrace && floorArea<=131) {
	    	matchedBuiltForm = Exposure.ExposureBuiltForm.House2; // mid-terrace small
	    }
	    else if(form==BuiltForm.MidTerrace && floorArea>131) {
	    	matchedBuiltForm = Exposure.ExposureBuiltForm.House4; // mid-terrace big
	    }
	    else if(form==BuiltForm.SemiDetached) {
	    	matchedBuiltForm = Exposure.ExposureBuiltForm.House3;
	    }
	    else if(form==BuiltForm.Bungalow) {
	    	matchedBuiltForm = Exposure.ExposureBuiltForm.House5;
	    }  //House 6 not used
	    else {  //(form==BuiltForm.Detached)
	    	matchedBuiltForm = Exposure.ExposureBuiltForm.House7;
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

    private double calculateQaly(final Map.Entry<Disease.Type, Disease> d, final double riskChangeTime, final double[][][] impactSurvival, final double[][][] baseSurvival, final int personIndex, final int year) {
    	
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
    	
    	return lifeYears-baselifeYears;
    }
}    

