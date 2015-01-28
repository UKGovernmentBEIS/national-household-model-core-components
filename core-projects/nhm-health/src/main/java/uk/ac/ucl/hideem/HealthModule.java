package uk.ac.ucl.hideem;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.util.Arrays;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.file.Paths;
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

        System.out.println("Reading exposures from: src/main/resources/uk/ac/ucl/hideem/NHM_input_radon_141106.csv");

        try (final CSV.Reader reader = CSV.trimmedReader(
                new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("NHM_input_radon_141106.csv"))))) {
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
        
        //initialisation
        Exposure.ExposureBuild e_match = null;
        Exposure.Vtype v_match = null;
        //Will have to put lots of if statements in here somewhere...
        //Get the dwelling type in exposures
        //mainFloorLevel==1 is <=ground floor 
        //mainFloorLevel==2 is 1st floor
        //mainFloorLevel==3 is >1st floor
        if((form==BuiltForm.ConvertedFlat || form==BuiltForm.PurposeBuiltLowRiseFlat) && floorArea>50 && mainFloorLevel==1) { 
        	e_match = Exposure.ExposureBuild.Flat1a;
        }
        else if((form==BuiltForm.ConvertedFlat || form==BuiltForm.PurposeBuiltLowRiseFlat) && floorArea>50 && mainFloorLevel==2) {
        	e_match = Exposure.ExposureBuild.Flat1b;
        }
        else if((form==BuiltForm.ConvertedFlat || form==BuiltForm.PurposeBuiltLowRiseFlat) && floorArea>50 && mainFloorLevel==3) {
        	e_match = Exposure.ExposureBuild.Flat1c;
        }
        else if((form==BuiltForm.ConvertedFlat || form==BuiltForm.PurposeBuiltLowRiseFlat) && floorArea<=50 && mainFloorLevel==1) { 
        	e_match = Exposure.ExposureBuild.Flat2a;
        }
        else if((form==BuiltForm.ConvertedFlat || form==BuiltForm.PurposeBuiltLowRiseFlat) && floorArea<=50 && mainFloorLevel==2) {
        	e_match = Exposure.ExposureBuild.Flat2b;
        }
        else if((form==BuiltForm.ConvertedFlat || form==BuiltForm.PurposeBuiltLowRiseFlat) && floorArea<=50 && mainFloorLevel==3) {
        	e_match = Exposure.ExposureBuild.Flat2c;
        }
        else if(form==BuiltForm.PurposeBuiltHighRiseFlat && mainFloorLevel==1) { //
        	e_match = Exposure.ExposureBuild.Flat3a;
        }
        else if(form==BuiltForm.PurposeBuiltHighRiseFlat && mainFloorLevel==2) {
        	e_match = Exposure.ExposureBuild.Flat3b;
        }
        else if(form==BuiltForm.PurposeBuiltHighRiseFlat && mainFloorLevel==3) {
        	e_match = Exposure.ExposureBuild.Flat3c;
        }
        else if(form==BuiltForm.EndTerrace) {
        	e_match = Exposure.ExposureBuild.House1;
        }
        else if(form==BuiltForm.MidTerrace && floorArea<=131) {
        	e_match = Exposure.ExposureBuild.House2; // mid-terrace small
        }
        else if(form==BuiltForm.MidTerrace && floorArea>131) {
        	e_match = Exposure.ExposureBuild.House4; // mid-terrace big
        }
        else if(form==BuiltForm.SemiDetached) {
        	e_match = Exposure.ExposureBuild.House3;
        }
        else if(form==BuiltForm.Bungalow) {
        	e_match = Exposure.ExposureBuild.House5;
        }  //House 6 not used
        else {  //(form==BuiltForm.Detached)
        	e_match = Exposure.ExposureBuild.House7;
        }
        
        //Get the ventilation
        if(!hasWorkingExtractorFans && !hasTrickleVents){
        	v_match = Exposure.Vtype.NOTE;
        }
        else if(hasWorkingExtractorFans && !hasTrickleVents){
        	v_match = Exposure.Vtype.T;
        }
        else if(!hasWorkingExtractorFans && hasTrickleVents){
        	v_match = Exposure.Vtype.E;
        }
        else{
        	v_match = Exposure.Vtype.TE;
        }
              
        //Get the exposure
        for(Map.Entry<Exposure.Type, Exposure> e: exposures.entries()) {
        	if(v_match==e.getValue().vtype && e_match==e.getValue().built) {
        		double radon_base=(e.getValue().b0 + (e.getValue().b1*Math.pow(p1, 1)) + (e.getValue().b2*Math.pow(p1,2)) + (e.getValue().b3*Math.pow(p1, 3)) + (e.getValue().b4*Math.pow(p1, 4))); 
        		double radon_mod =(e.getValue().b0 + (e.getValue().b1*Math.pow(p2, 1)) + (e.getValue().b2*Math.pow(p2,2)) + (e.getValue().b3*Math.pow(p2, 3)) + (e.getValue().b4*Math.pow(p2, 4))); 
        		
        		result.setInitialExposure(e.getKey(), radon_base);
        		result.setFinalExposure(e.getKey(), radon_mod);
        		
        	}        		
        }
        
        
        //result.setInitialExposure(Radon, 22);
        
        // health calculation goes here. Probably be good to sanity check the inputs.
        //result.setQalys(Disease.Cardiovascular, 0, this.healthCoefficients[form.ordinal()][0] * floorArea);
        //result.setQalys(Disease.Cardiopulmonary, 0, this.healthCoefficients[form.ordinal()][1] * Math.pow(floorArea, 2));
        
        return result;
    }
}
