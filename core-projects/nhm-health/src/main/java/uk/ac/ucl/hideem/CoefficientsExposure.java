package uk.ac.ucl.hideem;

public class CoefficientsExposure implements IExposure {
    public final ExposureBuiltForm builtForm;
    public final VentilationType ventType;
    public final Type type;

    //array of values for different exposure occupancies
	public double[][] coefs = new double[4][5];
	
    public CoefficientsExposure(final IExposure.Type type, final ExposureBuiltForm builtForm, final VentilationType ventType, final double b4, final double b3, final double b2,
                    final double b1, final double b0, final double c4, final double c3, final double c2, final double c1, final double c0, final double d4,
                    final double d3, final double d2, final double d1, final double d0, final double e4, final double e3, final double e2, final double e1,
                    final double e0) {
        this.type = type;
		this.builtForm = builtForm;
		this.ventType = ventType;
		this.coefs[OccupancyType.H45_45_10.ordinal()] = new double[]{b0, b1, b2, b3, b4};
		this.coefs[OccupancyType.H55_45_0.ordinal()] = new double[]{c0, c1, c2, c3, c4};
		this.coefs[OccupancyType.W21_33_8.ordinal()] = new double[]{d0, d1, d2, d3, d4};
		this.coefs[OccupancyType.W29_33_0.ordinal()] = new double[]{e0, e1, e2, e3, e4};
    }

    public double dueToPermeability(final OccupancyType occupancy, final double p) {
		final double[] coefs = this.coefs[occupancy.ordinal()];
		double acc = 0;
		for (int i = 0; i<coefs.length; i++) {
			acc += coefs[i] * Math.pow(p, i);
		}
		return acc;
	}
	
    public static CoefficientsExposure readExposure(final String[] row) {
        return new CoefficientsExposure(
            Enum.valueOf(Type.class, row[0]),
            Enum.valueOf(ExposureBuiltForm.class, row[1]),
            Enum.valueOf(VentilationType.class, row[2]),
            Double.parseDouble(row[3]),
            Double.parseDouble(row[4]),
            Double.parseDouble(row[5]),
            Double.parseDouble(row[6]),
            Double.parseDouble(row[7]),
            Double.parseDouble(row[8]),
            Double.parseDouble(row[9]),
            Double.parseDouble(row[10]),
            Double.parseDouble(row[11]),
            Double.parseDouble(row[12]),
            Double.parseDouble(row[13]),
            Double.parseDouble(row[14]),
            Double.parseDouble(row[15]),
            Double.parseDouble(row[16]),
            Double.parseDouble(row[17]),
            Double.parseDouble(row[18]),
            Double.parseDouble(row[19]),
            Double.parseDouble(row[20]),
            Double.parseDouble(row[21]),
            Double.parseDouble(row[22])
            );
	}
	

    @Override
    public void modify(
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
        final BuiltForm.Type builtFormType,
        final BuiltForm.Region region,
        final boolean isDoubleGlazed,

        // occupancy, outcome to modify
        final OccupancyType occupancy,
        final HealthOutcome result) {

        switch (type) {
        case SIT:
        case Mould:
        case VPX:
            // the dataset conatins only lines for VPX, so this is OK.
            setVPXSitAndMould(t1, t2, p1, p2, occupancy, result);
            break;
        case Radon:
            setRadonExposure(p1, p2, builtFormType, region, mainFloorLevel, occupancy, result);
            break;
        case ETS:
            //Find out if there is a smoker in the house

            if (smoker == true){
                result.setInitialExposure(type, occupancy, dueToPermeability(occupancy, p1));
                result.setFinalExposure(type, occupancy, dueToPermeability(occupancy, p2));
            } else {
                result.setInitialExposure(type, occupancy, 0);
                result.setFinalExposure(type, occupancy, 0);
            }
            break;

        case SIT2DayMax:
            throw new UnsupportedOperationException("SIT 2 Day Max Exposure is supposed to be computed by OverheatingExposure class.");

        default:
            result.setInitialExposure(type, occupancy, dueToPermeability(occupancy, p1));
            result.setFinalExposure(type, occupancy, dueToPermeability(occupancy, p2));
            break;
        }
    }

    private void setVPXSitAndMould(
        final double baseAverageSIT,
        final double modifiedAverageSIT,

        final double p1,
        final double p2,

        final OccupancyType occupancy,
        final HealthOutcome result) {

        final double baseVPX = dueToPermeability(occupancy, p1);
        final double modifiedVPX = dueToPermeability(occupancy, p2);

        //set VPX
        result.setInitialExposure(Type.VPX, occupancy,baseVPX);
        result.setFinalExposure(Type.VPX, occupancy, modifiedVPX);

        //set SIT
        result.setInitialExposure(Type.SIT, occupancy, baseAverageSIT);
        result.setFinalExposure(Type.SIT, occupancy, modifiedAverageSIT);

        //Now do the mould calc
        final double baseMould	= calcMould(baseAverageSIT, baseVPX);
        final double modifiedMould	= calcMould(modifiedAverageSIT, modifiedVPX);

        //set Mould
        result.setInitialExposure(Type.Mould, occupancy, baseMould);
        result.setFinalExposure(Type.Mould, occupancy, modifiedMould);
    }

    private void setRadonExposure(
        final double p1, final double p2,
        final BuiltForm.Type form, final BuiltForm.Region region, final int mainFloorLevel,
        final OccupancyType occupancy,
        final HealthOutcome result) {

        final double baseExposure = dueToPermeability(occupancy, p1);
        final double modifiedExposure = dueToPermeability(occupancy, p2);

        final double floorFactor;
        if ((form == BuiltForm.Type.ConvertedFlat) ||
            (form == BuiltForm.Type.PurposeBuiltFlatLowRise) ||
            (form == BuiltForm.Type.PurposeBuiltFlatHighRise)) {
            if (mainFloorLevel == 2) {
                floorFactor = 0.5;
            } else if (mainFloorLevel == 3) {
                floorFactor = 0;
            } else {
                floorFactor = 1;
            }
        } else {
            floorFactor = 1;
        }

        result.setInitialExposure(Type.Radon, occupancy, floorFactor*baseExposure*Constants.RADON_FACTS[region.ordinal()]);
        result.setFinalExposure(Type.Radon, occupancy, floorFactor*modifiedExposure*Constants.RADON_FACTS[region.ordinal()]);
    }

    //Info on Mou ld calcs can be found in: http://www.iso.org/iso/catalogue_detail.htm?csnumber=51615
    private double calcMould(final double averageSIT, final double vpx) {
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
        else{  //Maybe Ian made a mistake here?
            mould = 23.42903107 +(-1.46645682*Math.pow(srh,1))+(0.027203495*Math.pow(srh,2))+(-7.89893e-05*Math.pow(srh,3));
        }


        return mould;
    }

}
