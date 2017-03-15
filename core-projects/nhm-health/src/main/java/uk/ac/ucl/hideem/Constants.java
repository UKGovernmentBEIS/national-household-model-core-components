package uk.ac.ucl.hideem;

import java.util.EnumSet;
import java.util.Set;

import uk.ac.ucl.hideem.IExposure.Type;
import uk.ac.ucl.hideem.Person.Sex;
import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import static uk.org.cse.nhm.energycalculator.api.types.RegionType.*;
import uk.ac.ucl.hideem.IExposure.OccupancyType;
import uk.ac.ucl.hideem.IExposure.OverheatingAgeBands;

public class Constants {
	/**
	 * A risk constant ties together the two parameters for the log ratio with the exposure type.
	 *
	 * The useful part is {@link #riskDueTo(HealthOutcome)}, which computes the risk resulting from a change in exposure in the given health outcome.
	 */
	public enum RiskConstant {
        INPM_CP(Constants.REL_RISK_PM_CP,                Constants.INC_PM_CP,         IExposure.Type.INPM2_5),
        OUTPM_CP(Constants.REL_RISK_PM_CP,               Constants.INC_PM_CP,         IExposure.Type.OUTPM2_5),
        ETS_CA(Constants.REL_RISK_ETS_CA,                Constants.INC_ETS_CA,        IExposure.Type.ETS),
        INPM_LC(Constants.REL_RISK_PM_LC,                Constants.INC_PM_LC,         IExposure.Type.INPM2_5),
        OUTPM_LC(Constants.REL_RISK_PM_LC,               Constants.INC_PM_LC,         IExposure.Type.OUTPM2_5),
        RADON_LC(Constants.REL_RISK_RADON_LC,            Constants.INC_RADON_LC,      IExposure.Type.Radon),
        ETS_MI(Constants.REL_RISK_ETS_MI,                Constants.INC_ETS_MI,        IExposure.Type.ETS),
        SIT_CV(Constants.REL_RISK_SIT_CV,                Constants.INC_WINCV,         IExposure.Type.SIT),
        SIT_COPD(Constants.REL_RISK_SIT_COPD,            Constants.INC_WINCOPD,       IExposure.Type.SIT),
        SIT_CMD(Constants.REL_RISK_SIT_CMD,              Constants.INC_WINCMD,        IExposure.Type.SIT),
        MOULD_ASTHMA1(Constants.REL_RISK_MOULD_ASTHMA1,  Constants.INC_MOULD_ASTHMA1, IExposure.Type.Mould),
        MOULD_ASTHMA2(Constants.REL_RISK_MOULD_ASTHMA2,  Constants.INC_MOULD_ASTHMA2, IExposure.Type.Mould),
        MOULD_ASTHMA3(Constants.REL_RISK_MOULD_ASTHMA3,  Constants.INC_MOULD_ASTHMA3, IExposure.Type.Mould),
        SIT2DayMax_OVERHEAT(Constants.REL_RISK_OVERHEAT, Constants.INC_OVERHEAT,      IExposure.Type.SIT2DayMax);

		private final double logRatio;
		private final double ratio;
		private final Type exposureType;

        private RiskConstant(final double rel, final double inc, final IExposure.Type exposureType) {
			this.ratio = rel / inc;
			this.logRatio = Math.log(rel) / inc;
			this.exposureType = exposureType;
		}

		public double riskDueTo(final HealthOutcome result, final OccupancyType occupancy) {
			return Math.exp(logRatio * result.deltaExposure(exposureType, occupancy));
		}

		public double riskDueToCMD(final HealthOutcome result, final OccupancyType occupancy) {
			return Math.pow(ratio, result.deltaExposure(exposureType, occupancy));
		}

        public double riskDueToOverheating(final HealthOutcome result, final OccupancyType occupancy, final RegionType region, final OverheatingAgeBands ageBand){
			double risk = ratio;
            if (result.initialExposure(exposureType, occupancy)  > Constants.OVERHEAT_THRESH[region.ordinal()] &&
                result.finalExposure(exposureType, occupancy)  > Constants.OVERHEAT_THRESH[region.ordinal()]){
                risk += RR_PER_DEGREE_OVERHEAT[region.ordinal()][ageBand.ordinal()] * result.deltaExposure(exposureType, occupancy);
            }else if (result.initialExposure(exposureType, occupancy)  > Constants.OVERHEAT_THRESH[region.ordinal()] ) {
                risk += RR_PER_DEGREE_OVERHEAT[region.ordinal()][ageBand.ordinal()] * (Constants.OVERHEAT_THRESH[region.ordinal()] - result.initialExposure(exposureType, occupancy));
            }else if (result.finalExposure(exposureType, occupancy)  > Constants.OVERHEAT_THRESH[region.ordinal()] ) {
                risk += RR_PER_DEGREE_OVERHEAT [region.ordinal()][ageBand.ordinal()] * (result.finalExposure(exposureType, occupancy) - Constants.OVERHEAT_THRESH[region.ordinal()]);
			}

			return risk;
		}

	}

    //Health coefficients
	public static final double REL_RISK_SIT_CV 		= 	0.913043;
	public static final double REL_RISK_ETS_CA 		= 	1.25;
    public static final double REL_RISK_ETS_MI 		= 	1.3;
    public static final double REL_RISK_PM_CP 		= 	1.082;
    public static final double REL_RISK_PM_LC		= 	1.059;
    public static final double REL_RISK_RADON_LC 	= 	1.16;
    public static final double REL_RISK_SIT_COPD 	= 	0.9;    //Done
    public static final double REL_RISK_SIT_CMD 	= 	0.902952;
    public static final double REL_RISK_MOULD_ASTHMA1 	= 	1.83;
    public static final double REL_RISK_MOULD_ASTHMA2 	= 	1.53;
    public static final double REL_RISK_MOULD_ASTHMA3 	= 	1.53;
    public static final double REL_RISK_OVERHEAT 	= 	1.;//intialize
    public static final double WEIGHT_COPD 			= 	0.88;//Done
    public static final double WEIGHT_CMD 			= 	0.751;
    public static final double WEIGHT_ASTHMA1 		= 	0.97;
    public static final double WEIGHT_ASTHMA2 		= 	0.85;
    public static final double WEIGHT_ASTHMA3 		= 	0.669;
    public static final double PREV_ASTHMA1  		= 	0.093;
    public static final double PREV_ASTHMA2	 		=	0.016;
    public static final double PREV_ASTHMA3	 		= 	0.001;
    public static final double PREV_COPD	 		= 	0.059;
    public static final double PREV_CMD	 			= 	0.15;
    public static final double INC_WINCV			= 	1;
    public static final double INC_PM_CP			= 	10;
    public static final double INC_PM_LC 			= 	10;
    public static final double INC_ETS_CA 			= 	1;
    public static final double INC_ETS_MI 			= 	1;
    public static final double INC_RADON_LC	 		= 	100;
    public static final double INC_WINCMD	 		= 	1;
    public static final double INC_WINCOPD	 		= 	1;
    public static final double INC_MOULD_ASTHMA1 	= 	100;
    public static final double INC_MOULD_ASTHMA2 	=   100;
    public static final double INC_MOULD_ASTHMA3 	= 	100;
    public static final double INC_OVERHEAT = 1; //intialize
    //Overheating
    public static final double TOT_BASE 	= 9E-3; // tot death/pop = 491119/54808600;
    public static final double OVERHEAT_HAZARD = 3.65E-5; //2000/54808600

    //Costs
    public static final double COST_PER_CASE(final Disease.Type disease) {
    	final double[] cost = new double[Disease.Type.values().length];
    	cost[Disease.Type.cerebrovascular.ordinal()] = 860.83;
    	cost[Disease.Type.cardiopulmonary.ordinal()] = 1194.10;
    	cost[Disease.Type.myocardialinfarction.ordinal()] = 765.55; //CHD
    	cost[Disease.Type.lungcancer.ordinal()] = 4951.47;
    	cost[Disease.Type.wincerebrovascular.ordinal()] = cost[Disease.Type.cerebrovascular.ordinal()];
    	cost[Disease.Type.wincardiovascular.ordinal()] = 1047.09;
    	cost[Disease.Type.winmyocardialinfarction.ordinal()] = cost[Disease.Type.myocardialinfarction.ordinal()];
    	cost[Disease.Type.commonmentaldisorder.ordinal()] = 2248.77;
    	cost[Disease.Type.copd.ordinal()] = 820.39;
    	cost[Disease.Type.asthma1.ordinal()]=
    		cost[Disease.Type.asthma2.ordinal()]=
    		cost[Disease.Type.asthma3.ordinal()] = 312.08;

    	return cost[disease.ordinal()];
    }

    //Incidence factor to get cases is incidence/deaths from Global Heath Statistics book
    //Global burden of disease and injury (1990s data)
    public static final double INCIDENCE (final Disease.Type disease, final int age, final Sex sex) {

    	int a,s = 0;

    	final double[][][] incidence = new double[Disease.Type.values().length][2][5];
    	//male					   //female
    	incidence[Disease.Type.cerebrovascular.ordinal()] = new double[][]{{0, 0, 37/8, 79/24, 465/289},{0, 0, 29/5, 69/15, 603/467}};  //page 655
    	incidence[Disease.Type.cardiopulmonary.ordinal()] = new double[][]{{0, 0, 60/18, 282/98, 857/713},{0, 0, 17/5, 78/29, 921/805}}; //Ischemic heart disease p646
    	incidence[Disease.Type.myocardialinfarction.ordinal()] = incidence[Disease.Type.cardiopulmonary.ordinal()]; //p646
    	incidence[Disease.Type.lungcancer.ordinal()] = new double[][]{{0, 0, 8/6, 57/50, 241/223},{0, 0, 4/3, 24/17, 97/81}}; //p556
    	incidence[Disease.Type.wincerebrovascular.ordinal()] = incidence[Disease.Type.cerebrovascular.ordinal()];	//p646
    	incidence[Disease.Type.wincardiovascular.ordinal()] = incidence[Disease.Type.cardiopulmonary.ordinal()];	//p646
    	incidence[Disease.Type.winmyocardialinfarction.ordinal()] = incidence[Disease.Type.cardiopulmonary.ordinal()];	//p646

    	if (age < 5) {
    		a = 0;
    	} else if (age >= 5 && age < 15){
    		a = 1;
    	} else if (age >= 15 && age < 45){
    		a = 2;
    	} else if (age >= 45 && age < 60){
    		a = 3;
    	} else {
    		a = 4;
    	}

    	if (sex == Sex.MALE){
    		s = 0;
    	} else{
    		s = 1;
    	}

    	return incidence[disease.ordinal()][s][a];
    }

    //Time functions
    //public final double[][] timeFunction;// = new double[4][3];
    public static final double[] TIME_FUNCTION (final Disease.Type disease) {
        double[] tFunc = new double[]{1, 1, 1};

    	if (disease == Disease.Type.cerebrovascular || disease == Disease.Type.myocardialinfarction) {
    		tFunc = new double[]{5, 2, 0.4};
        } else if (disease == Disease.Type.cardiopulmonary) {
        	tFunc = new double[]{5, 2, 0.3};
        } else if (disease == Disease.Type.lungcancer) {
        	tFunc = new double[]{15, 2, 0.2};
        }

        return tFunc;
    }

    public static <T extends Enum<T>, U extends Enum<U>> double[][] forEnums(final Class<T> row,
                                                                            final Class<U> col,
                                                                            final T[] rowkeys,
                                                                            final U[] columnkeys,
                                                                            final double[][] values) {
        final double[][] result = new double[row.getEnumConstants().length][];

        final Set<T> keysUsed = EnumSet.allOf(row);
        for (int i = 0; i<rowkeys.length; i++) {
            if (!keysUsed.contains(rowkeys[i])) {
                throw new IllegalArgumentException("Key used twice: " + rowkeys[i]);
            } else {
                keysUsed.remove(rowkeys[i]);
            }
            result[rowkeys[i].ordinal()] = forEnum(col, columnkeys, values[i]);
        }

        if (!keysUsed.isEmpty()) {
            throw new IllegalArgumentException("Some keys not supplied for " + row + ": " + keysUsed);
        }

        return result;
    }

    public static <T extends Enum<T>> double[] forEnum(final Class<T> type,
                                                       final T[] keys,
                                                       final double[] values) {
        final Set<T> keysUsed = EnumSet.allOf(type);
        final double[] result = new double[type.getEnumConstants().length];
        for (int i = 0 ;i<keys.length; i++) {
            if (!keysUsed.contains(keys[i])) {
                throw new IllegalArgumentException("Key used twice: " + keys[i]);
            } else {
                keysUsed.remove(keys[i]);
            }

            result[keys[i].ordinal()] = values[i];
        }

        if (!keysUsed.isEmpty()) {
            throw new IllegalArgumentException("Some keys not supplied for " + type + ": " + keysUsed);
        }

        return result;
    }


  //Radon regional factors
    public static final double[] RADON_FACTS =
        forEnum(RegionType.class,
                new RegionType[] {NorthEast, NorthWest, YorkshireAndHumber, EastMidlands, WestMidlands, EastOfEngland, London, SouthWest, SouthEast,
        		Wales, WesternScotland, EasternScotland, NorthernScotland, NorthernIreland}, //Estimates for now (TO BE UPDATED)
                new double[] {0.77, 0.91, 0.92, 1.47, 1.08, 0.62, 0.43, 1.72, 1.11,
        		1.72,1.08,1.11,1.47,1.47}//Estimates for now (TO BE UPDATED)
            );

  //Regional overheating info
    public static final double[] OVERHEAT_THRESH =
        forEnum(RegionType.class,
                new RegionType[] {NorthEast, NorthWest, YorkshireAndHumber, EastMidlands, WestMidlands, EastOfEngland, London,   SouthWest, SouthEast,
        		Wales, WesternScotland, EasternScotland, NorthernScotland, NorthernIreland}, //Estimates for now (TO BE UPDATED)
                new double[] {23.28746,  23.67835,   24.53434,           24.39385,     24.10412,     24.87814,      25.26228, 24.57729,  23.55555,
        		24.1, 23.67835, 23.28746, 23.28746, 23.28746}//estimates for now (TO BE UPDATED)
            );

    public static final double[] OVERHEAT_COEFS =
        forEnum(RegionType.class,
                new RegionType[] {NorthEast,   NorthWest, YorkshireAndHumber, EastMidlands, WestMidlands, EastOfEngland, London, SouthEast, SouthWest,
        		Wales, WesternScotland, EasternScotland, NorthernScotland, NorthernIreland}, //Estimates for now (TO BE UPDATED)
                new double[] {-0.1085839,  0.13299919, 0.57157228, 0.36532503, 0.19491544, 0.64106725, 0.76364547, 0.53149218, 0,
        		0.065, -0.1085839, -0.1085839, -0.1085839, -0.1085839}//estimates for now (TO BE UPDATED)
            );

    //SIT E-value coefficients
    public static final double[] LR_SIT_CONSTS = new double[]{0,-3.10552E-11, 3.95406E-07, -0.003177483,19.97883737};
    public static final double[] BR_SIT_CONSTS = new double[]{0,-3.63348E-11, 6.50441E-07, -0.003972248,18.60539276};

    //Fuel rebate info
    public static final double REBATE_AMMOUNT 	= 	200;  //£
    public static final double REBATE_PRICE 	=  	0.031;  //£/kWh


  //Overheating constants
    public static final double SIT_MAX_INTERCEPT = 	17.28343088;
    public static final double AVG_2DAY_P95 = 	0.29417342;
    public static final double HEAT_LOSS = -0.00235936;

    public static final double DBL_GLAZ = -0.36023931;


    public static final double[][] RR_PER_DEGREE_OVERHEAT =
            forEnums(
                RegionType.class,
                IExposure.OverheatingAgeBands.class,
                // row indices
                new RegionType[] {NorthEast, NorthWest, Wales, YorkshireAndHumber, EastMidlands, WestMidlands, EastOfEngland, London, SouthWest, SouthEast,
                	WesternScotland, EasternScotland, NorthernScotland, NorthernIreland}, //Estimates for now (TO BE UPDATED)
                // column indices
                new OverheatingAgeBands[] {OverheatingAgeBands.Age0_64, OverheatingAgeBands.Age65_74, OverheatingAgeBands.Age75_85, OverheatingAgeBands.Age85},
                new double[][]
                {
                    {0.5        ,0.60000002 ,0.80000001 ,1.1},
                    {0.80000001 ,0.89999998 ,1.3        ,1.9}       ,
                    {1.2        ,1.4        ,2          ,2.9000001} ,
                    {1.1        ,1.2        ,1.7        ,2.4000001} ,
                    {1.4        ,1.6        ,2.3        ,3.3}       ,
                    {1.4        ,1.6        ,2.2        ,3.0999999} ,
                    {1.5        ,1.7        ,2.4000001  ,3.4000001} ,
                    {2.4000001  ,2.7        ,3.8        ,5.4000001} ,
                    {1.6        ,1.9        ,2.5999999  ,3.7}       ,
                    {1.3        ,1.5        ,2.0999999  ,3}			,
                    {0.80000001 ,0.89999998 ,1.3        ,1.9}       ,//Estimates
                    {0.5        ,0.60000002 ,0.80000001 ,1.1}		,//Estimates
                    {0.5        ,0.60000002 ,0.80000001 ,1.1}		,//Estimates
                    {0.5        ,0.60000002 ,0.80000001 ,1.1}		 //Estimates
                }
                );
}
