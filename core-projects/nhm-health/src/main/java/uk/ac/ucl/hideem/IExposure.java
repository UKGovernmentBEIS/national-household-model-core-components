package uk.ac.ucl.hideem;


/**
 * Everything HIDEEM needs to know about exposures.
 */
public interface IExposure {
    public enum Type {
        Radon,
        ETS,
        INPM2_5,
        OUTPM2_5,
        VPX,
        SIT,
        Mould,
        SIT2DayMax;
    }

    public enum ExposureBuiltForm {
        Flat1,
        Flat2,
        Flat3,
        House1,
        House2,
        House3,
        House4,
        House5,
        House6,
        House7;
    }

    public enum VentilationType {
        NOTE,
        T,
        E,
        TE;
    }

    public enum OccupancyType{
        //time living room, bedroom, kitchen
        H45_45_10,   //pensioner
        H55_45_0,    //small child
        W29_33_0,	 //school child
        W21_33_8;    //worker
    }

    public enum OverheatingAgeBands{
        Age0_64,
        Age65_74,
        Age75_85,
        Age85;
    }

    public void modify(
        // effect of change
        final double t1,
        final double t2,
        final double p1,
        final double p2,

        // details
        final boolean smoker,
        final int mainFloorLevel,
        final BuiltForm.Type builtFormType,
        final BuiltForm.Region region,

        // occupancy, outcome to modify
        final OccupancyType occupancy,
        final HealthOutcome result);
}
