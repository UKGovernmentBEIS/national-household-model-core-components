package uk.ac.ucl.hideem;

import java.util.List;

public interface IHealthModule {
    public HealthOutcome effectOf(
        // e-values & perm.s
        double t1,
        double t2,
        double p1,
        double p2,
        // case number constituents
        BuiltForm.Type form,
        double floorArea,
        int region,
        int mainFloorLevel, // fdfmainn (for flats)
        // finkxtwk and finbxtwk
        boolean hasWorkingExtractorFans, // per finwhatever
        boolean hasTrickleVents,         // this is cooked up elsewhere
        // who
        List<Person> people,
        int horizon);
    
    public double getInternalTemperature(boolean regressionSIT, double specificHeat, double efficiency, BuiltForm.DwellingAge dwellingAge, 
    		BuiltForm.Tenure tenure, BuiltForm.OwnerAge ownerAge, boolean children, boolean feulPoverty);
}
