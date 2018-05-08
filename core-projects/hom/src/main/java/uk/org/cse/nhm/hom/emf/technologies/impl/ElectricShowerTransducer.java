package uk.org.cse.nhm.hom.emf.technologies.impl;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.TransducerPhaseType;
import uk.org.cse.nhm.hom.constants.SplitRateConstants;

public class ElectricShowerTransducer implements IEnergyTransducer {

    private final double numShowers;
    private final double BREDEM_ELECTRICITY_PER_SHOWER = 0.93;

    public ElectricShowerTransducer(final double numShowers) {
        this.numShowers = numShowers;
    }

    @Override
    public ServiceType getServiceType() {
        return ServiceType.WATER_HEATING;
    }

    @Override
    public void generate(final IEnergyCalculatorHouseCase house, final IInternalParameters parameters, final ISpecificHeatLosses losses,
            final IEnergyState state) {

        /*
		 *  In BREDEM 2012, electric showers do not create any hot water demand.
		 *  Nor do they supply any hot water when consuming electricity.
         */
        state.increaseElectricityDemand(
                /*
				 *  BREDEM doesn't have any concept of peak and off-peak electricity.
				 *  We've used the default split rate from SAP instead.
                 */
                parameters.getConstants().get(SplitRateConstants.DEFAULT_FRACTIONS, double[].class)[parameters.getTarrifType().ordinal()],
                numShowers * BREDEM_ELECTRICITY_PER_SHOWER
        );
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public TransducerPhaseType getPhase() {
        return TransducerPhaseType.HotWater;
    }

    @Override
    public String toString() {
        return "Electric Shower";
    }
}
