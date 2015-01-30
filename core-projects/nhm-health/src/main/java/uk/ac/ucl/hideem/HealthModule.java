package uk.ac.ucl.hideem;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import com.google.common.collect.*;

public class HealthModule implements IHealthModule {
	private final ListMultimap<Exposure.Type, Exposure> exposures;
    //private final double[][] healthCoefficients;
    
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

        exposures = ArrayListMultimap.create();

        System.out.println("Reading exposures from: src/main/resources/uk/ac/ucl/hideem/NHM_exposure_coefficients_141106.csv");

        try (final CSV.Reader reader = CSV.trimmedReader(
                new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("NHM_exposure_coefficients_141106.csv"))))) {
           String[] row = reader.read(); // throw away header line, because we know what it is
           
           while ((row = reader.read()) != null) {
        	   final Exposure e = Exposure.readExposure(row);
        	   exposures.put(Enum.valueOf(Exposure.Type.class, row[0]), e);
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
        final Exposure.ExposureBuiltForm matchedBuiltForm = MapBuiltForm(form, floorArea, mainFloorLevel);
        final Exposure.VentilationType matchedVentilation = MapVentilation(hasWorkingExtractorFans, hasTrickleVents);
              
        //Get the correct exposures coefficients and calculate base and modified exposures
        //First loop over the exposure types
    	//There is probably a quicker/better way of doing this but this will do for now 
        for(final Exposure.Type matchedExposure : Exposure.Type.values()) {

        	//Then need to loop over the exposures file to get the right values	
	        for(Map.Entry<Exposure.Type, Exposure> e: exposures.entries()) {
	        	if(e.getKey()== matchedExposure && matchedVentilation==e.getValue().ventType && matchedBuiltForm==e.getValue().builtForm) {
	        		//different calculation is used for mould and temperature and vpx is needed so do all together
	        		if(matchedExposure == Exposure.Type.VPX){
	        			//Calc VPX same as others
	        			double baseVPX     =(e.getValue().b0 + (e.getValue().b1*Math.pow(p1, 1)) + (e.getValue().b2*Math.pow(p1,2)) + (e.getValue().b3*Math.pow(p1, 3)) + (e.getValue().b4*Math.pow(p1, 4))); 
		        		double modifiedVPX =(e.getValue().b0 + (e.getValue().b1*Math.pow(p2, 1)) + (e.getValue().b2*Math.pow(p2,2)) + (e.getValue().b3*Math.pow(p2, 3)) + (e.getValue().b4*Math.pow(p2, 4))); 
		        		
		        		//set VPX
		        		result.setInitialExposure(e.getKey(), baseVPX);
		        		result.setFinalExposure(e.getKey(), modifiedVPX);
	            		
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
	        		else{ //rest of the exposures all the same		        		
		        		double baseExposure=(e.getValue().b0 + (e.getValue().b1*Math.pow(p1, 1)) + (e.getValue().b2*Math.pow(p1,2)) + (e.getValue().b3*Math.pow(p1, 3)) + (e.getValue().b4*Math.pow(p1, 4))); 
		        		double modifiedExposure =(e.getValue().b0 + (e.getValue().b1*Math.pow(p2, 1)) + (e.getValue().b2*Math.pow(p2,2)) + (e.getValue().b3*Math.pow(p2, 3)) + (e.getValue().b4*Math.pow(p2, 4))); 
		        		
		        		result.setInitialExposure(e.getKey(), baseExposure);
		        		result.setFinalExposure(e.getKey(), modifiedExposure);
	        		}
	        	}        		
	        }
    	}
        	
        
        // health calculation goes here. Probably be good to sanity check the inputs.
        //result.setQalys(Disease.Cardiovascular, 0, this.healthCoefficients[form.ordinal()][0] * floorArea);
        //result.setQalys(Disease.Cardiopulmonary, 0, this.healthCoefficients[form.ordinal()][1] * Math.pow(floorArea, 2));
        
        return result;
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
    private Exposure.ExposureBuiltForm MapBuiltForm(BuiltForm form, double floorArea, int mainFloorLevel) {
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
    
    private Exposure.VentilationType MapVentilation(boolean hasWorkingExtractorFans, boolean hasTrickleVents) {
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
}    

