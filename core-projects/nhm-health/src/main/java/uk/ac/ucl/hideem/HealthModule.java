package uk.ac.ucl.hideem;

import java.util.List;
import java.lang.Math;
import java.util.Map;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.file.Paths;
import com.google.common.collect.*;
import java.util.Map;
import org.apache.commons.math3.distribution.NormalDistribution;
import uk.ac.ucl.hideem.Constants;

public class HealthModule implements IHealthModule {
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

        System.out.println("Reading exposure coefficients from: src/main/resources/uk/ac/ucl/hideem/NHM_exposure_coefficients_141106.csv");

        try (final CSV.Reader reader = CSV.trimmedReader(
                new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("NHM_exposure_coefficients_141106.csv"))))) {
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

        System.out.println("Reading health coefficients from: src/main/resources/uk/ac/ucl/hideem/NHM_mortality_data.csv");
        
        //Not yet sure how to get as a resource?
		for (final Map<String, String> row : CSV.mapReader(Paths.get("src/main/resources/uk/ac/ucl/hideem/NHM_mortality_data.csv"))) {         
			   
			for (final Disease.Type type : Disease.Type.values()){
				
				final Disease d = Disease.readDisease(row.get("age"), row.get("sex"), row.get(type.name()), row.get("population"));
				healthCoefficients.put(Enum.valueOf(Disease.Type.class, type.name()), d);
			}
		}
    }

    public HealthOutcome effectOf(
        // e-values & perm.s
        double e1,
        double e2,
        double p1,
        double p2,
        // case number constituents
        BuiltForm form,
        double floorArea,
        int mainFloorLevel, // fdfmainn (for flats)
        // for vtype:
        int buildYear,
        // finkxtwk and finbxtwk
        boolean hasWorkingExtractorFans, // per finwhatever
        boolean hasTrickleVents,         // this is cooked up elsewhere
        int numberOfFansAndPassiveVents, // per SAP
        // who
        List<Person> people,
        int horizon) {
        
        final HealthOutcome result = new HealthOutcome(horizon);
        
        //perform the matching beetween NHM built form and ventilation and Hideem
        final Exposure.ExposureBuiltForm matchedBuiltForm = mapBuiltForm(form, floorArea, mainFloorLevel);
        final Exposure.VentilationType matchedVentilation = mapVentilation(hasWorkingExtractorFans, hasTrickleVents);
              
        //Get the correct exposures coefficients and calculate base and modified exposures
        //First loop over the exposure types
    	//There is probably a quicker/better way of doing this but this will do for now 
        for(final Exposure.Type matchedExposure : Exposure.Type.values()) {

        	//Then need to loop over the exposures file to get the right values	
	        for(Map.Entry<Exposure.Type, Exposure> e: exposureCoefficients.entries()) {
	        	if(e.getKey()== matchedExposure && matchedVentilation==e.getValue().ventType && matchedBuiltForm==e.getValue().builtForm) {
	        		//different calculation is used for mould and temperature and vpx is needed so do all together
	        		if(matchedExposure == Exposure.Type.VPX){
	        			//Calc VPX same as others
	        			double baseVPX     =calcExposure(p1, e.getValue().b0, e.getValue().b1, e.getValue().b2, e.getValue().b3, e.getValue().b4); 
		        		double modifiedVPX =calcExposure(p2, e.getValue().b0, e.getValue().b1, e.getValue().b2, e.getValue().b3, e.getValue().b4); 
		        		
		        		//set VPX
		        		result.setInitialExposure(Exposure.Type.VPX, baseVPX);
		        		result.setFinalExposure(Exposure.Type.VPX, modifiedVPX);
	            		
		        		//calc base temp
		        		double baseAverageSIT=calcSIT(e1);
		         		//same for modified case
		        		double modifiedAverageSIT=calcSIT(e2);
		        		
		        		//set SIT
		        		result.setInitialExposure(Exposure.Type.SIT, baseAverageSIT);
		        		result.setFinalExposure(Exposure.Type.SIT, modifiedAverageSIT);
	            		
	            		//Now do the mould calc
		           		double baseMould	= calcMould(baseAverageSIT, baseVPX);
		           		double modifiedMould	= calcMould(modifiedAverageSIT, modifiedVPX);		           		
		           		
		           		//set Mould	
		        		result.setInitialExposure(Exposure.Type.Mould, baseMould);
		        		result.setFinalExposure(Exposure.Type.Mould, modifiedMould);
		           		
	        		}
	        		else if(matchedExposure == Exposure.Type.SIT || matchedExposure == Exposure.Type.Mould){
	        			//Already calculated these when doing VPX so can break out of loop
	        			break;
	        		}
	        		else if (matchedExposure == e.getKey()) { //rest of the exposures all the same		        		
		        		double baseExposure=calcExposure(p1, e.getValue().b0, e.getValue().b1, e.getValue().b2, e.getValue().b3, e.getValue().b4); 
		        		double modifiedExposure =calcExposure(p2, e.getValue().b0, e.getValue().b1, e.getValue().b2, e.getValue().b3, e.getValue().b4); 
		        		
		        		result.setInitialExposure(e.getKey(), baseExposure);
		        		result.setFinalExposure(e.getKey(), modifiedExposure);
	        		}
	        		else {
	        	        System.out.println("Can't find the exposure coefficient for " + matchedExposure);
	        		}
	        	}        		
	        }
    	}
                               
        //Calculate the relative risks (independent of person)
        result.setRelativeRisk(Disease.Type.cardiopulmonary, 
        		(Math.exp((Math.log(Constants.REL_RISK_PM_CP) / Constants.INC_PM_CP) * result.deltaExposure(Exposure.Type.OUTPM2_5))) 
        		* (Math.exp((Math.log(Constants.REL_RISK_PM_CP) / Constants.INC_PM_CP) * result.deltaExposure(Exposure.Type.INPM2_5))));
        result.setRelativeRisk(Disease.Type.cerebrovascular, 
        		(Math.exp((Math.log(Constants.REL_RISK_PM_CP) / Constants.INC_PM_CP) * result.deltaExposure(Exposure.Type.OUTPM2_5))) 
        		* (Math.exp((Math.log(Constants.REL_RISK_PM_CP) / Constants.INC_PM_CP) * result.deltaExposure(Exposure.Type.INPM2_5))) 
        		* (Math.exp((Math.log(Constants.REL_RISK_ETS_CA) / Constants.INC_ETS_CA) * result.deltaExposure(Exposure.Type.ETS))));
        result.setRelativeRisk(Disease.Type.myocardialinfarction, 
        		(Math.exp((Math.log(Constants.REL_RISK_PM_CP) / Constants.INC_PM_CP) * result.deltaExposure(Exposure.Type.OUTPM2_5))) 
        		* (Math.exp((Math.log(Constants.REL_RISK_PM_CP) / Constants.INC_PM_CP) * result.deltaExposure(Exposure.Type.INPM2_5))) 
        		* (Math.exp((Math.log(Constants.REL_RISK_ETS_MI) / Constants.INC_ETS_MI) * result.deltaExposure(Exposure.Type.ETS))));
        result.setRelativeRisk(Disease.Type.wincerebrovascular, 
        		(Math.exp((Math.log(Constants.REL_RISK_PM_CP) / Constants.INC_PM_CP) * result.deltaExposure(Exposure.Type.OUTPM2_5))) 
        		* (Math.exp((Math.log(Constants.REL_RISK_PM_CP) / Constants.INC_PM_CP) * result.deltaExposure(Exposure.Type.INPM2_5)))
        		* (Math.exp((Math.log(Constants.REL_RISK_SIT_CV) / Constants.INC_WINCV) * result.deltaExposure(Exposure.Type.SIT)))
        		* (Math.exp((Math.log(Constants.REL_RISK_ETS_CA) / Constants.INC_ETS_CA) * result.deltaExposure(Exposure.Type.ETS))));                
        result.setRelativeRisk(Disease.Type.winmyocardialinfarction, 
        		(Math.exp((Math.log(Constants.REL_RISK_PM_CP) / Constants.INC_PM_CP) * result.deltaExposure(Exposure.Type.OUTPM2_5))) 
        		* (Math.exp((Math.log(Constants.REL_RISK_PM_CP) / Constants.INC_PM_CP) * result.deltaExposure(Exposure.Type.INPM2_5)))
        		* (Math.exp((Math.log(Constants.REL_RISK_SIT_CV) / Constants.INC_WINCV) * result.deltaExposure(Exposure.Type.SIT)))
        		* (Math.exp((Math.log(Constants.REL_RISK_ETS_MI) / Constants.INC_ETS_MI) * result.deltaExposure(Exposure.Type.ETS))));  
        result.setRelativeRisk(Disease.Type.wincardiovascular, 
        		(Math.exp((Math.log(Constants.REL_RISK_PM_CP) / Constants.INC_PM_CP) * result.deltaExposure(Exposure.Type.OUTPM2_5))) 
        		* (Math.exp((Math.log(Constants.REL_RISK_PM_CP) / Constants.INC_PM_CP) * result.deltaExposure(Exposure.Type.INPM2_5)))
        		* (Math.exp((Math.log(Constants.REL_RISK_SIT_CV) / Constants.INC_WINCV) * result.deltaExposure(Exposure.Type.SIT))));
        result.setRelativeRisk(Disease.Type.lungcancer, 
        		(Math.exp((Math.log(Constants.REL_RISK_PM_LC) / Constants.INC_PM_LC) * result.deltaExposure(Exposure.Type.OUTPM2_5))) 
        		* (Math.exp((Math.log(Constants.REL_RISK_PM_LC) / Constants.INC_PM_LC) * result.deltaExposure(Exposure.Type.INPM2_5)))
        		* (Math.exp((Math.log(Constants.REL_RISK_RADON_LC) / Constants.INC_RADON_LC) * result.deltaExposure(Exposure.Type.Radon))));  
        
        // health calculation goes here. Probably be good to sanity check the inputs.
       
       	//Survival array here so that qaly calc is done cumulatively (need one for each person)
    	double[][] impactSurvival = new double[people.size()][Disease.Type.values().length];
    	double[][] baseSurvival = new double[people.size()][Disease.Type.values().length];
    	for(Person p: people){ //initialize to 1
    		for (final Disease.Type d : Disease.Type.values()) {
    			impactSurvival[people.indexOf(p)][d.ordinal()] = 1;
    			baseSurvival[people.indexOf(p)][d.ordinal()] = 1;
    		}    
    	}
    	
        //Loop over disease coeficients
        for(Map.Entry<Disease.Type, Disease> d: healthCoefficients.entries()) {
        	        	
        	//loop over people in house to match them to coefficients
        	for(Person p: people){
        	
        		//loop over time frame
        		for (int year = 0; year < horizon; year=year+1) {
	        		if (p.age+year == d.getValue().age && p.sex == d.getValue().sex){
		        		if (d.getKey() == Disease.Type.wincardiovascular || d.getKey() == Disease.Type.wincerebrovascular || d.getKey() == Disease.Type.winmyocardialinfarction) {
		           			//Flat disease impact
		        			double impact = d.getValue().hazard * result.relativeRisk(d.getKey());
		        			double base = d.getValue().hazard;
		        			
		        			impactSurvival[people.indexOf(p)][d.getKey().ordinal()] = impactSurvival[people.indexOf(p)][d.getKey().ordinal()]*((2-impact)/(2+impact));
		        			baseSurvival[people.indexOf(p)][d.getKey().ordinal()] = baseSurvival[people.indexOf(p)][d.getKey().ordinal()]*((2-base)/(2+base));
		        			
		        			double qaly = calculateQaly(impactSurvival[people.indexOf(p)][d.getKey().ordinal()], baseSurvival[people.indexOf(p)][d.getKey().ordinal()]);		        			
		        			result.setMortalityQalys(d.getKey(), year, qaly);	
		        		}
		        		else  {
		           			//Disease impact depends on increased or decreased risk
		        			double riskChangeTime = result.relativeRisk(d.getKey());
		        			if (result.relativeRisk(d.getKey()) >= 1.) {
		        				final NormalDistribution normDist;
		        				normDist = new NormalDistribution(Constants.TIME_FUNCTION(d.getKey())[0], Constants.TIME_FUNCTION(d.getKey())[1]);
								double impact = d.getValue().hazard * (1 + (riskChangeTime - 1) * normDist.cumulativeProbability(year+1));						
								double base = d.getValue().hazard;
								
								impactSurvival[people.indexOf(p)][d.getKey().ordinal()] = impactSurvival[people.indexOf(p)][d.getKey().ordinal()]*((2-impact)/(2+impact));
			        			baseSurvival[people.indexOf(p)][d.getKey().ordinal()] = baseSurvival[people.indexOf(p)][d.getKey().ordinal()]*((2-base)/(2+base));
			        			
			        			double qaly = calculateQaly(impactSurvival[people.indexOf(p)][d.getKey().ordinal()], baseSurvival[people.indexOf(p)][d.getKey().ordinal()]);		        			
			        			result.setMortalityQalys(d.getKey(), year, baseSurvival[people.indexOf(p)][d.getKey().ordinal()]);
			        			
		        			} else {
		        				double impact = d.getValue().hazard * (1 - (riskChangeTime - 1) * (1 - Math.exp(-(year+1) * Constants.TIME_FUNCTION(d.getKey())[2])));	        				
		        				double base = d.getValue().hazard;
								
			        			impactSurvival[people.indexOf(p)][d.getKey().ordinal()] = impactSurvival[people.indexOf(p)][d.getKey().ordinal()]*((2-impact)/(2+impact));
			        			baseSurvival[people.indexOf(p)][d.getKey().ordinal()] = baseSurvival[people.indexOf(p)][d.getKey().ordinal()]*((2-base)/(2+base));
			        			
			        			double qaly = calculateQaly(impactSurvival[people.indexOf(p)][d.getKey().ordinal()], baseSurvival[people.indexOf(p)][d.getKey().ordinal()]);		        			
			        			result.setMortalityQalys(d.getKey(), year, qaly);
		        			}
		        			
		        		}
		        		
	        		}

	        	}
        	}
        }
        	
        return result;
    }
    
    private double calcExposure(double permeability, double b0, double b1, double b2, double b3, double b4){
    	double exposure = 0;
    	exposure = b0 + (b1*Math.pow(permeability, 1)) + (b2*Math.pow(permeability,2)) + (b3*Math.pow(permeability, 3)) + (b4*Math.pow(permeability, 4)); 
    	
    	return exposure;
    }

    
    
    //Temperature Calculations as needed quite a lot
    private double calcSIT(double eValue){
    	//Put these into private functions so that less mess
		double livingRoomSIT=(19.97883737 + (-0.003177483*Math.pow(eValue,1)) + (3.95406E-07*Math.pow(eValue,2)) + (-3.10552E-11*Math.pow(eValue,3)));
		double bedRoomSIT=(18.60539276 + (-0.003972248*Math.pow(eValue,1)) + (6.50441E-07*Math.pow(eValue,2)) + (-3.63348E-11*Math.pow(eValue,3)));
		double averageSIT=((livingRoomSIT+bedRoomSIT)/2);
		
		return averageSIT;
    }
    
    private double calcMould(double averageSIT, double vpx) {
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
    private Exposure.ExposureBuiltForm mapBuiltForm(BuiltForm form, double floorArea, int mainFloorLevel) {
	    //initialisation
	    Exposure.ExposureBuiltForm matchedBuiltForm = null;
	    //Will have to put lots of if statements in here somewhere...
	    //Get the dwelling type in exposures
	    //mainFloorLevel==1 is <=ground floor 
	    //mainFloorLevel==2 is 1st floor
	    //mainFloorLevel==3 is >1st floor
	    if((form==BuiltForm.ConvertedFlat || form==BuiltForm.PurposeBuiltLowRiseFlat) && floorArea>50 && mainFloorLevel==1) { 
	    	matchedBuiltForm = Exposure.ExposureBuiltForm.Flat1a;
	    }
	    else if((form==BuiltForm.ConvertedFlat || form==BuiltForm.PurposeBuiltLowRiseFlat) && floorArea>50 && mainFloorLevel==2) {
	    	matchedBuiltForm = Exposure.ExposureBuiltForm.Flat1b;
	    }
	    else if((form==BuiltForm.ConvertedFlat || form==BuiltForm.PurposeBuiltLowRiseFlat) && floorArea>50 && mainFloorLevel==3) {
	    	matchedBuiltForm = Exposure.ExposureBuiltForm.Flat1c;
	    }
	    else if((form==BuiltForm.ConvertedFlat || form==BuiltForm.PurposeBuiltLowRiseFlat) && floorArea<=50 && mainFloorLevel==1) { 
	    	matchedBuiltForm = Exposure.ExposureBuiltForm.Flat2a;
	    }
	    else if((form==BuiltForm.ConvertedFlat || form==BuiltForm.PurposeBuiltLowRiseFlat) && floorArea<=50 && mainFloorLevel==2) {
	    	matchedBuiltForm = Exposure.ExposureBuiltForm.Flat2b;
	    }
	    else if((form==BuiltForm.ConvertedFlat || form==BuiltForm.PurposeBuiltLowRiseFlat) && floorArea<=50 && mainFloorLevel==3) {
	    	matchedBuiltForm = Exposure.ExposureBuiltForm.Flat2c;
	    }
	    else if(form==BuiltForm.PurposeBuiltHighRiseFlat && mainFloorLevel==1) {
	    	matchedBuiltForm = Exposure.ExposureBuiltForm.Flat3a;
	    }
	    else if(form==BuiltForm.PurposeBuiltHighRiseFlat && mainFloorLevel==2) {
	    	matchedBuiltForm = Exposure.ExposureBuiltForm.Flat3b;
	    }
	    else if(form==BuiltForm.PurposeBuiltHighRiseFlat && mainFloorLevel==3) {
	    	matchedBuiltForm = Exposure.ExposureBuiltForm.Flat3c;
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
    private Exposure.VentilationType mapVentilation(boolean hasWorkingExtractorFans, boolean hasTrickleVents) {
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

    private double calculateQaly(double impactSurvival, double baseSurvival) {

		double deaths = 1 - impactSurvival;
		double lifeYears = 1 - 0.5*deaths;
		
		double baseDeaths = 1 - baseSurvival;
		double baselifeYears = 1 - 0.5*baseDeaths;
    	
    	return lifeYears-baselifeYears;
    }
}    

