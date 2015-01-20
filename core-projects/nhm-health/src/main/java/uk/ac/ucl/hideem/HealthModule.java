package uk.ac.ucl.hideem;

public class HealthModule implements IHealthModule {
    private final Thing table1;
    
    public HealthModule() {
        try (final InputStream in = getClass().getResourceAsStream("hideem/table1.csv")) {
            this.table1 = csv.read(in);
        }
    }

    public IHealthOutcomes effectOf(
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
        List<IPerson> people,
        int horizon) {
        // do health calculation here
        table1.get(somethign, something);
        return answer;
    }
}
