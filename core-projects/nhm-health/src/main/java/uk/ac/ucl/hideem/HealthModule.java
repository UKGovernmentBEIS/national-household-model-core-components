package uk.ac.ucl.hideem;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import com.google.common.collect.*;

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

        System.out.println("Reading health coefficients from: src/main/resources/uk/ac/ucl/hideem/NHM_input_healthcalc_141106.csv");

        try (final CSV.Reader reader = CSV.trimmedReader(
                new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("NHM_input_healthcalc_141106.csv"))))) {
           String[] row = reader.read(); // throw away header line, because we know what it is
           
           while ((row = reader.read()) != null) {
        	   final Disease e = Disease.readDisease(row);
        	   healthCoefficients.put(Enum.valueOf(Disease.Type.class, row[0]), e);
           }
        } catch (final IOException ex) {
                    // problem?
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
        
        // health calculation goes here. Probably be good to sanity check the inputs.
        //Loop over disease coeficients
        for(Map.Entry<Disease.Type, Disease> d: healthCoefficients.entries()) {
        	
        	//initialise to 0 for each zero so that can be incremented for people
        	double tempMortQalys = 0;
        	double tempMorbQalys = 0;
        	
        	//loop over people in house to match them to coefficients
        	for(Person p: people){
        		if (p.age == d.getValue().age && p.sex == d.getValue().sex){
	        		if (d.getKey() == Disease.Type.CardiovascularCold) {
	           			double personMortQalys = calcMortQalys(result.deltaExposure(Exposure.Type.SIT), d.getValue().nA, d.getValue().nB, d.getValue().nC, d.getValue().pA, d.getValue().pB, d.getValue().pC);
	           			tempMortQalys += personMortQalys;
	           			tempMorbQalys += personMortQalys*d.getValue().morbitity;
	           			result.setMortalityQalys(Disease.Type.CardiovascularCold, tempMortQalys);
	           			result.setMorbidityQalys(Disease.Type.CardiovascularCold, tempMorbQalys);
	        		}
	        		else if (d.getKey() == Disease.Type.Stroke) {
	           			double personMortQalys = calcMortQalys(result.deltaExposure(Exposure.Type.OUTPM2_5), d.getValue().nA, d.getValue().nB, d.getValue().nC, d.getValue().pA, d.getValue().pB, d.getValue().pC);
	           			tempMortQalys += personMortQalys;
	           			tempMorbQalys += personMortQalys*d.getValue().morbitity;
	           			result.setMortalityQalys(Disease.Type.Stroke, tempMortQalys);
	           			result.setMorbidityQalys(Disease.Type.Stroke, tempMorbQalys);
	        		}
	        		else if (d.getKey() == Disease.Type.HeartAttack) {
	        			double personMortQalys = calcMortQalys(result.deltaExposure(Exposure.Type.OUTPM2_5), d.getValue().nA, d.getValue().nB, d.getValue().nC, d.getValue().pA, d.getValue().pB, d.getValue().pC);
	        			tempMortQalys += personMortQalys;
	           			tempMorbQalys += personMortQalys*d.getValue().morbitity;
	           			result.setMortalityQalys(Disease.Type.HeartAttack, tempMortQalys);
	           			result.setMorbidityQalys(Disease.Type.HeartAttack, tempMorbQalys);
	        		}
	        		else if (d.getKey() == Disease.Type.Cardiopulmonary) {
	        			//Due to internal PM2.5
	        			double personMortQalysInPM25 = calcMortQalys(result.deltaExposure(Exposure.Type.INPM2_5), d.getValue().nA, d.getValue().nB, d.getValue().nC, d.getValue().pA, d.getValue().pB, d.getValue().pC);         			
	        			tempMortQalys += personMortQalysInPM25;     			
	        			tempMorbQalys += personMortQalysInPM25*d.getValue().morbitity; 
	        			//Due to external PM2.5
	        			
	        			double personMortQalysOutPM25 = calcMortQalys(result.deltaExposure(Exposure.Type.OUTPM2_5), d.getValue().nA, d.getValue().nB, d.getValue().nC, d.getValue().pA, d.getValue().pB, d.getValue().pC);          			
	        			tempMortQalys += personMortQalysOutPM25;
	        			tempMorbQalys += personMortQalysOutPM25*d.getValue().morbitity;
	        			result.setMortalityQalys(Disease.Type.Cardiopulmonary, tempMortQalys);
	        			result.setMorbidityQalys(Disease.Type.Cardiopulmonary, tempMorbQalys);
	        		}
	        		else if (d.getKey() == Disease.Type.LungCancer) {
	        			//Due to internal PM2.5
	        			double personMortQalysInPM25 = calcMortQalys(result.deltaExposure(Exposure.Type.INPM2_5), d.getValue().nA, d.getValue().nB, d.getValue().nC, d.getValue().pA, d.getValue().pB, d.getValue().pC);         			
	        			tempMortQalys += personMortQalysInPM25;     			
	        			tempMorbQalys += personMortQalysInPM25*d.getValue().morbitity; 
	        			//Due to external PM2.5
	        			
	        			double personMortQalysOutPM25 = calcMortQalys(result.deltaExposure(Exposure.Type.OUTPM2_5), d.getValue().nA, d.getValue().nB, d.getValue().nC, d.getValue().pA, d.getValue().pB, d.getValue().pC);          			
	        			tempMortQalys += personMortQalysOutPM25;
	        			tempMorbQalys += personMortQalysOutPM25*d.getValue().morbitity;
	        			
	        			result.setMortalityQalys(Disease.Type.LungCancer, tempMortQalys);
	        			result.setMorbidityQalys(Disease.Type.LungCancer, tempMorbQalys);
	        		}
	        		else {
	        			System.out.println("Can't find the health coefficient for " + d.getKey());
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
    
    //Health calculation: Basic quadratic
    private double calcMortQalys(double deltaExposure, double negA, double negB, double negC, double posA, double posB, double posC) {
    	
    	double mortQALY = 0; 
    	if(deltaExposure < 0) {
    		mortQALY = negA + negB*deltaExposure + negB*Math.pow(deltaExposure, 2);
    	}
    	else{
    		mortQALY = posA + posB*deltaExposure + posB*Math.pow(deltaExposure, 2);
    	}
    	
    	return mortQALY;  
    }
    
}    

