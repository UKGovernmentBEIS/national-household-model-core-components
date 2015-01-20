package uk.ac.ucl.hideem;

public interface IHealthModule {
    // dwtypenx
    public enum BuiltForm {
        EndTerrace,
        MidTerrace,
        SemiDetached,
        Detached,
        Bungalow,
        ConvertedFlat,
        PurposeBuiltLowRiseFlat,
        PurposeBuiltHighRiseFlat;
    }

    public interface IPerson {
        public int age();
        public boolean female();
        public boolean smokes(); // cignow
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
        int horizon);

    public enum Exposure {
        Radon,
        ETS,
        INPM2_5,
        OUTPM2_5,
        VPX,
        SIT,
        Mould
    }

    public enum Disease {
        Cardiovascular,
        Cardiopulmonary,
        HeartAttack,
        Stroke,
        LungCancer,
        CommonMentalDisorder,
        Athsma,
        ChronicObstructivePulmonaryDisorder,
        OverheatingDeath
    }

    public enum HealthCost {
        GeneralPractitioner,
        Hospital,
        SocialCare
    }
    
    public interface IHealthOutcomes {
        public Map<Exposure, Double> intialExposures();
        public Map<Exposure, Double> finalExposures();
        public Map<Disease, double[]> qalys(); // by disease, then by year from now
        public Table<Disease, HealthCost, double[]> costs();
    }
}
