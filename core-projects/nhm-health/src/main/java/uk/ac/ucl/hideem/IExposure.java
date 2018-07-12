package uk.ac.ucl.hideem;

import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.energycalculator.api.types.BuiltFormType;

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

    public enum OccupancyType {
        //time living room, bedroom, kitchen
        H45_45_10, //pensioner
        H55_45_0, //small child
        W29_33_0, //school child
        W21_33_8;    //worker

        public static OccupancyType forAge(final int age) {
            final OccupancyType occupancy;
            //move elsewhere
            if (age <= 5) {
                occupancy = OccupancyType.H55_45_0;
            } else if (age > 5 && age < 18) {
                occupancy = OccupancyType.W29_33_0;
            } else if (age > 65) {
                occupancy = OccupancyType.H45_45_10;
            } else {
                occupancy = OccupancyType.W21_33_8;
            }

            return occupancy;
        }
    }

    public enum OverheatingAgeBands {
        Age0_64,
        Age65_74,
        Age75_85,
        Age85;

        public static OverheatingAgeBands forAge(final int age) {
            final OverheatingAgeBands ageBand;
            //move elsewhere
            if (age <= 65) {
                ageBand = OverheatingAgeBands.Age0_64;
            } else if (age >= 65 && age < 75) {
                ageBand = OverheatingAgeBands.Age65_74;
            } else if (age >= 75 && age < 85) {
                ageBand = OverheatingAgeBands.Age75_85;
            } else {
                ageBand = OverheatingAgeBands.Age85;
            }

            return ageBand;
        }

    }

    //array of values for different exposure occupancies
    public double[] getCoefs(final OccupancyType occupancy);

    public void modify(
            //
            final double[] coefsV1,
            // effect of change
            final double t1,
            final double t2,
            final double p1,
            final double p2,
            final double e1,
            final double e2,
            // details
            final boolean smoker,
            final int mainFloorLevel,
            final BuiltFormType builtFormType,
            final RegionType region,
            boolean hadDoubleGlazing,
            boolean hasDoubleGlazing,
            // occupancy, outcome to modify
            final OccupancyType occupancy,
            final HealthOutcome result);

}
