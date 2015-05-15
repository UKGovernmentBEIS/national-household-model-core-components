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
        BuiltForm form,
        double floorArea,
        int mainFloorLevel, // fdfmainn (for flats)
        // finkxtwk and finbxtwk
        boolean hasWorkingExtractorFans, // per finwhatever
        boolean hasTrickleVents,         // this is cooked up elsewhere
        // who
        List<Person> people,
        int horizon);
    
    public double getInternalTemperature(double specificHeat, double envelope);
}
