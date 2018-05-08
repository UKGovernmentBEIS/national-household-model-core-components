package uk.org.cse.nhm.hom.emf.technologies.impl.util;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.hom.constants.CylinderConstants;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.IHeatPump;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirCirculator;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;

/**
 * Helper methods for things which can be connected to an
 * {@link ICentralWaterSystem} - because there are a few things like this which
 * have common logic ({@link IHeatPump}, {@link IWarmAirCirculator} and
 * {@link IBoiler}, for example) but should not share inheritance, I have moved
 * some methods to here.
 *
 * @author hinton
 *
 */
public class HotWaterUtilities {

    /**
     * Get the standard storage temperature factors for hot water tanks; this is
     * defined by SAP 2009 table 2b
     *
     * This doesn't cover special cases from that table like combis (the combi
     * implementation does that).
     *
     * @param parameters
     * @param store
     * @param storeInPrimaryCircuit
     * @return
     */
    public static final double getStorageTemperatureFactor(final IInternalParameters parameters,
            final IWaterTank store, final boolean storeInPrimaryCircuit, final boolean systemIsSeparatelyTimeControlled) {
        /*
		BEISDOC
		NAME: Storage Temperature Factor
		DESCRIPTION: A factor which depends on the temperature of the hot water store. It is a multiplier to storage losses.
		TYPE: formula
		UNIT: Dimensionless
		SAP: (53), Table 2b
                SAP_COMPLIANT: Yes
		BREDEM: 2.2B.C, Table 9
                BREDEM_COMPLIANT: Yes
		DEPS: basic-temperature-factor,temperature-factor-no-thermostat-multiplier,temperature-factor-separate-timer-multiplier
		ID: storage-temperature-factor
		CODSIEB
         */
        final IConstants constants = parameters.getConstants();
        if (storeInPrimaryCircuit) {
            return 1; //TODO don't return 1 - this is probably a hot water only thermal store
        } else {
            double factor = constants.get(CylinderConstants.TEMPERATURE_FACTOR_BASIC);

            if (store.isThermostatFitted() == false) {
                factor *= constants.get(CylinderConstants.TEMPERATURE_FACTOR_NO_THERMOSTAT_MULTIPLIER);
            }

            if (systemIsSeparatelyTimeControlled) {
                factor *= constants.get(CylinderConstants.TEMPERATURE_FACTOR_SEPARATE_HW_TIMER);
            }

            return factor;
        }
    }
}
