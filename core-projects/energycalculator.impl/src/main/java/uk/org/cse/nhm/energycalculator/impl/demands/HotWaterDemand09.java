package uk.org.cse.nhm.energycalculator.impl.demands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.energycalculator.api.*;
import uk.org.cse.nhm.energycalculator.api.types.steps.EnergyCalculationStep;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.TransducerPhaseType;
import uk.org.cse.nhm.energycalculator.constants.HotWaterConstants09;

/**
 * hot water demand
 * @author hinton
 *
 */
public class HotWaterDemand09 implements IEnergyTransducer {
    private static final Logger log = LoggerFactory.getLogger(HotWaterDemand09.class);
    private final double CONSTANT;
    private final double PERSON;
    private final double FACTOR;

    private final double BREDEM_BATHS_BASE = 0.19;
    private final double BREDEM_BATHS_BASE_NO_SHOWER = 0.5;
    private final double BREDEM_BATHS_OCCUPANCY_FACTOR = 0.13;
    private final double BREDEM_BATHS_OCCUPANCY_FACTOR_NO_SHOWER = 0.35;

    private final double BREDEM_BATH_VOLUME = 50.8;
    private final double BREDEM_OTHER_BASE = 14;
    private final double BREDEM_OTHER_OCCUPANCY_FACTOR = 9.8;
    private final IBredemShower shower;

    public HotWaterDemand09(final IConstants constants, IBredemShower shower) {
	this.shower = shower;
	this.CONSTANT = constants.get(HotWaterConstants09.BASE_VOLUME);
	this.PERSON = constants.get(HotWaterConstants09.PERSON_DEPENDENT_VOLUME);
	this.FACTOR = constants.get(HotWaterConstants09.ENERGY_PER_VOLUME);
    }

    @Override
    public int getPriority() {
	return 0;
    }

    @Override
    public ServiceType getServiceType() {
	return ServiceType.INTERNALS;
    }

    @Override
    public void generate(final IEnergyCalculatorHouseCase house,
			 final IInternalParameters parameters, final ISpecificHeatLosses losses,
			 final IEnergyState state) {
	final IConstants constants = parameters.getConstants();
	/**
	 * Zero-indexed month number
	 */
	final int monthNumber = parameters.getClimate().getMonthOfYear()-1;

	final double USAGE_FACTOR = constants.get(HotWaterConstants09.USAGE_FACTOR, monthNumber);
	final double RISE_TEMPERATURE = constants.get(HotWaterConstants09.RISE_TEMPERATURE, monthNumber);

	/*
	  BEISDOC
	  NAME: Usage Adjusted Water Volume
	  DESCRIPTION: description
	  TYPE: type
	  UNIT: litres
	  SAP: (44)
	  BREDEM: 2.1G
	  DEPS: sap-water-volume,bredem-water-volume,monthly-water-usage-factor
	  ID: usage-adjusted-water-volume
	  CODSIEB
	*/
	final double usageAdjustedVolume = getHotWaterVolume(house, parameters) * USAGE_FACTOR;
	StepRecorder.recordStep(EnergyCalculationStep.WaterHeating_Usage_MonthAdjusted, usageAdjustedVolume);

	/*
	  BEISDOC
	  NAME: Water heating power
	  DESCRIPTION: The power required to provide hot water.
	  TYPE: formula
	  UNIT: W
	  SAP: (45,46), Tables
	  BREDEM: 2.1H, 2.1I,2.2A
	  DEPS: usage-adjusted-water-volume,monthly-water-temperature-factor,energy-per-hot-water
	  NOTES: SAP includes distribution losses in the amount demanded. We exclude that here and add it on later.
	  ID: water-heating-power
	  CODSIEB
	*/
	final double power = usageAdjustedVolume * RISE_TEMPERATURE * FACTOR;
	StepRecorder.recordStep(EnergyCalculationStep.WaterHeating_EnergyContent, power);

	log.debug("Hot water demand: {} W, {} l", power, usageAdjustedVolume);

	state.increaseDemand(EnergyType.DemandsHOT_WATER_VOLUME, usageAdjustedVolume);
	state.increaseDemand(EnergyType.DemandsHOT_WATER, power);
    }

    /**
     * @param house
     * @param parameters
     * @return The volume of hot water demanded by the dwelling.
     */
    protected double getHotWaterVolume(final IEnergyCalculatorHouseCase house, final IInternalParameters parameters) {
        final double result;
	    switch (parameters.getCalculatorType()) {
	    case SAP2012:
            /*
              BEISDOC
              NAME: SAP Water volume
              DESCRIPTION: The volume of hot water required by the house according to SAP, calculated as a base amount plus an amount per occupant.
              TYPE: formula
              UNIT: litres
              SAP: (43)
              DEPS: base-hot-water-demand,person-hot-water-demand,occupancy
              NOTES: We omit the 5% reduction in hot water usage for dwellings with hot water targets. We have no information about this.
              ID: sap-water-volume
              CODSIEB
            */
            result = CONSTANT + (PERSON * parameters.getNumberOfOccupants());
            break;
	    case BREDEM2012:
            /*
              BEISDOC
              NAME: BREDEM Water Volume
              DESCRIPTION: The volume of hot water required by the house according to BREDEM.
              TYPE: formula
              UNIT: litres
              BREDEM: 2.1A-2.2F
              DEPS:
              ID: bredem-water-volume
              CODSIEB
            */

            final double showerVolume;
            final double numBaths;

            if (shower != null) {
                final double numShowers = shower.numShowers(parameters.getNumberOfOccupants());
                showerVolume = numShowers * shower.hotWaterVolumePerShower();

                numBaths = BREDEM_BATHS_BASE +
                    (BREDEM_BATHS_OCCUPANCY_FACTOR * parameters.getNumberOfOccupants());
            } else {
		showerVolume = 0;

                numBaths = BREDEM_BATHS_BASE_NO_SHOWER +
                    (BREDEM_BATHS_OCCUPANCY_FACTOR_NO_SHOWER * parameters.getNumberOfOccupants());
            }

            final double bathVolume = numBaths * BREDEM_BATH_VOLUME;

            final double otherVolume = BREDEM_OTHER_BASE +
                (BREDEM_OTHER_OCCUPANCY_FACTOR * parameters.getNumberOfOccupants());

	    result = showerVolume + bathVolume + otherVolume;
	    break;

        default:
            throw new UnsupportedOperationException("Unknown energy calculator type when calculating hot water volume " + parameters.getCalculatorType());
        }
        StepRecorder.recordStep(EnergyCalculationStep.WaterHeating_Usage_Initial, result);
	    return result;
    }

    @Override
    public String toString() {
	return "Hot Water Demand";
    }

    @Override
    public TransducerPhaseType getPhase() {
	return TransducerPhaseType.BeforeEverything;
    }
}
